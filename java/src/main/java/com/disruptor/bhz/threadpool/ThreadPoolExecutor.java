package com.disruptor.bhz.threadpool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutor extends AbstractExecutorService {

    /**
     * ctl :包含两个概念
     * 1、workerCount：当前状态worker数
     * 2、runState：表明当前线程池状态：RUNNING、SHUTDOWN、STOP、TIDYING、TERMINATED
     * ctl32位，前3位表示状态，后29位表示workerCount
     * <p>
     * runStateOf：取得运行状态
     * workerCountOf：取得worker数
     */
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;
    /**
     * 线程池五种状态：
     * 一、RUNNING：运行状态
     * 二、SHUTDOWN：执行shutdown（）方法，队列为空，并线程池中worker也为空
     * 三、STOP：执行shutdownNow（）方法，线程池中worker数为0
     * 四、TIDYING：SHUTDOWN、STOP完成后，转变为TIDING状态
     * 五、TERMINATED：线程池彻底终止，即调用terminated()
     */
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    //线程池总锁
    private final ReentrantLock mainLock = new ReentrantLock();

    private final BlockingQueue<Runnable> workQueue;

    private final HashSet<Worker> workers = new HashSet<Worker>();

    //记录线程池中worker最多存在数量
    private int largestPoolSize;
    //线程池总任务完成数
    private int completedTaskCount;

    private volatile ThreadFactory threadFactory;
    /**
     * 当队列满了，线程池执行的处理策略：
     * 一、AbortPolicy：丢弃该任务并抛出RejectedExecutionException（默认）
     * 二、DiscardPolicy：丢弃该任务，不抛出异常
     * 三、DiscardOldestPolicy：丢弃老的任务，并尝试重新入队
     * 四、CallerRunsPolicy：主线程自己执行该任务
     * 五、Custom（自定义）：实现RejectedExecutionHandler接口，并实现rejectedExecution方法即可
     */
    private volatile RejectedExecutionHandler handler;
    //worker存活时间
    private volatile long keepAliveTime;

    /**
     * “是否允许核心线程空闲状态超时后结束”
     * 默认（false），worker保持存活直到生命周期结束
     * true，当worker存活时间超过keepAliveTime时结束工作。
     */
    private volatile boolean allowCoreThreadTimeOut;

    private volatile int corePoolSize;

    private volatile int maximumPoolSize;

    private static final boolean ONLY_ONE = true;

    private final Condition termination = mainLock.newCondition();

    private static final RejectedExecutionHandler defaultHandler =
            new AbortPolicy();

    private static boolean runStateAtLeast(int c, int s) {
        return c >= s;
    }

    private boolean compareAndIncrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect + 1);
    }

    private boolean compareAndDecrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect - 1);
    }

    private static boolean runStateLessThan(int c, int s) {
        return c < s;
    }

    private static final RuntimePermission shutdownPerm =
            new RuntimePermission("modifyThread");

    private void decrementWorkerCount() {
        do {
        } while (!compareAndDecrementWorkerCount(ctl.get()));
    }

    /**
     * 检查当前调用线程是否允许修改子线程参数
     */
    private void checkShutdownAccess() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(shutdownPerm);
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                for (Worker w : workers)
                    security.checkAccess(w.thread);
            } finally {
                mainLock.unlock();
            }
        }
    }

    /**
     * 处理策略
     *
     * @param command
     */
    final void reject(Runnable command) {
        handler.rejectedExecution(command, this);
    }

    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }

    /**
     * 修改当前运行状态
     *
     * @param targetState
     */
    private void advanceRunState(int targetState) {
        for (; ; ) {
            int c = ctl.get();
            if (runStateAtLeast(c, targetState) ||
                    ctl.compareAndSet(c, ctlOf(targetState, workerCountOf(c))))
                break;
        }
    }

    /**
     * drain : 流干，排干
     * drainQueue：清空workQueue所有任务，返回任务列表。
     *
     * @return
     */
    private List<Runnable> drainQueue() {
        BlockingQueue<Runnable> q = workQueue;
        ArrayList<Runnable> taskList = new ArrayList<Runnable>();

        /**
         * BlockingQueue.drainTo(): 一次性从队列中获取所有可用的任务，提高效率，无需多次加锁
         */
        q.drainTo(taskList);
        /**
         * 清空workQueue中任务
         */
        if (!q.isEmpty()) {
            for (Runnable r : q.toArray(new Runnable[0])) {
                if (q.remove(r))
                    taskList.add(r);
            }
        }
        return taskList;
    }

    void onShutdown() {
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void execute(Runnable command) {
        if (command == null){
            throw new NullPointerException();
        }
        /**
         * 三个选择:
         * 一、如果当前线程数 < 核心线程数，添加新的worker并执行任务
         * 二、当前线程数 >= 核心线程数，将任务放入workerQueue
         * 三、当前线程数 <= 最大允许线程数，添加新的worker并执行任务，否则执行处理策略
         */
        int c = ctl.get();
        if (workerCountOf(c) < corePoolSize) {
            if (addWorker(command, true)){
                return;
            }
            c = ctl.get();
        }

        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (!isRunning(recheck) && remove(command)){
                reject(command);
            }
            else if (workerCountOf(recheck) == 0){
                addWorker(null, false);
            }
        } else if (!addWorker(command, false)){
            reject(command);
        }
    }

    private final class Worker
            extends AbstractQueuedSynchronizer
            implements Runnable {

        final Thread thread;

        Runnable firstTask;

        volatile long completedTasks;

        Worker(Runnable firstTask) {
            //设置Worker状态：single？？？
            setState(-1);
            this.firstTask = firstTask;
            this.thread = getThreadFactory().newThread(this);
        }

        @Override
        public void run() {
            runWorker(this);
        }

        /**
         * Lock状态
         * 0 ：未锁定状态
         * 1 ：锁定状态
         *
         * @return ture:锁定
         * false:未锁定
         */
        protected boolean isHeldExclusively() {
            return getState() != 0;
        }

        /**
         * 尝试锁定当前线程，成功返回true
         *
         * @param unused
         * @return
         */
        protected boolean tryAcquire(int unused) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 释放锁，将线程状态设置为 0（未锁定状态）
         *
         * @param unused
         * @return
         */
        protected boolean tryRelease(int unused) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        public void lock() {
            acquire(1);
        }

        public boolean tryLock() {
            return tryAcquire(1);
        }

        public void unlock() {
            release(1);
        }

        public boolean isLocked() {
            return isHeldExclusively();
        }

        /**
         * 中断worker中thread线程
         */
        void interruptIfStarted() {
            Thread t;
            if (getState() >= 0 && (t = thread) != null && !t.isInterrupted()) {
                try {
                    t.interrupt();
                } catch (SecurityException ignore) {
                }
            }
        }
    }

    final void runWorker(Worker w) {
        Thread wt = Thread.currentThread();
        Runnable task = w.firstTask;
        w.firstTask = null;
        //允许被中断(interrupIdleWorkers()方法中判断worker是否lock，并有可能打断worker)
        w.unlock();
        boolean completedAbruptly = true;
        try {
            while (task != null || (task = getTask()) != null) {
                /**
                 * w.lock() 保证在任务执行过程中（即while循环里）无法调用
                 * interruptIdleWokers()方法中断。
                 * 只有调用interruptWorkers（）或者自己退出才能结束
                 */
                w.lock();
                /**
                 * 当调用shutdownNow()时，需要中断当前工作线程
                 * 如果线程池停止，确保线程被中断
                 * 如果线程池没有停止，确保线程不能被中断
                 *
                 * 判断条件：运行状态 >= STOP : 即不是RUNNING状态
                 */
                if ((runStateAtLeast((ctl.get()), STOP) ||
                        (Thread.interrupted() &&
                                runStateAtLeast(ctl.get(), STOP))) &&
                        !wt.isInterrupted()){
                    //中断当前线程
                    wt.interrupt();
                }

                try {
                    /**
                     * 执行任务前 ：未实现具体方法
                     */
                    beforeExecute(wt, task);
                    Throwable thrown = null;
                    try {
                        task.run();
                    } catch (RuntimeException x) {
                        thrown = x;
                        throw x;
                    } catch (Error x) {
                        thrown = x;
                        throw x;
                    } catch (Throwable x) {
                        thrown = x;
                        throw new Error(x);
                    } finally {
                        afterExecute(task, thrown);
                    }
                } finally {
                    task = null;
                    w.completedTasks++;
                    w.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            //worker线程结束
            processWorkerExit(w, completedAbruptly);
        }
    }

    /**
     * 从队列中获取任务，该方法可以阻塞
     *
     * @return
     */
    private Runnable getTask() {
        boolean timedOut = false;

        for (; ; ) {
            int c = ctl.get();
            int rs = runStateOf(c);

            /**
             * 如果线程池SHUTOWN或者STOP，或者workQueue（任务队列）为空
             * worker减一，返回空任务，使得worker跳出while循环，线程结束
             */
            if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
                decrementWorkerCount();
                return null;
            }

            int wc = workerCountOf(c);
            /**
             * wc:worker线程数
             * 当允许 线程请求超时停止时，如果workQueue.poll超时后，将会返回null任务，使得worker结束
             * 当前worker数 > corePoolSize 时，也会进入上一阶段
             * 如果workQueue已经满了，且worker不大于最大线程数，就不会被关闭。
             * 当前worker数已经达到最大线程数，将关闭该线程
             */
            boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;

            if ((wc > maximumPoolSize || (timed && timedOut))
                    && (wc > 1 || workQueue.isEmpty())) {
                if (compareAndDecrementWorkerCount(c))
                    return null;
                continue;
            }

            try {
                Runnable r = timed ?
                        workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
                        workQueue.take();
                if (r != null)
                    return r;
                timedOut = true;
            } catch (InterruptedException entry) {
                timedOut = false;
            }
        }
    }

    /**
     * completedAbruptly : 线程是否在运行中异常退出
     *
     * @param w
     * @param completedAbruptly
     */
    private void processWorkerExit(Worker w, boolean completedAbruptly) {
        /**
         * 如果线程是异常退出，则workercount-1
         */
        if (completedAbruptly)
            decrementWorkerCount();
        //mainLock应当是worker中thread.mainLock
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            completedTaskCount += w.completedTasks;
            workers.remove(w);
        } finally {
            mainLock.unlock();
        }

        tryTerminate();

        int c = ctl.get();
        /**
         * 线程池状态为RUNNING，且任务线程是正常退出
         * 允许线程超时停止（allowCoreThreadTimeOut == ture）
         * 目的是 动态平衡worker线程数量
         */
        if (runStateLessThan(c, STOP)) {
            if (!completedAbruptly) {
                int min = allowCoreThreadTimeOut ? 0 : corePoolSize;
                if (min == 0 && !workQueue.isEmpty()) {
                    min = 1;
                }
                if (workerCountOf(c) >= min) {
                    return;
                }
            }
            addWorker(null, false);
        }
    }

    /**
     * 添加worker
     * retry : 标志符 ，与continue retry共同使用。
     *
     * @param firstTask
     * @param core      布尔值，定义线程池最大保留worker数，
     *                  true代表最大核心数，false代表最大maximumPoolSize
     * @return
     */
    private boolean addWorker(Runnable firstTask, boolean core) {
        retry:
        for (; ; ) {
            int c = ctl.get();
            int rs = runStateOf(c);
            /**
             * 如果线程池不在运行状态，将不添加worker
             */
            if (rs >= SHUTDOWN &&
                    !(rs == SHUTDOWN &&
                            firstTask == null &&
                            !workQueue.isEmpty())){
                return false;
            }
            for (; ; ) {
                int wc = workerCountOf(c);
                /**
                 * worker数  >=  线程池容器，不添加worker
                 * worker数  >=  corePoolSize 或 maximumPoolSize, 不添加worker
                 */
                if (wc >= CAPACITY ||
                        wc >= (core ? corePoolSize : maximumPoolSize))
                    return false;
                /**
                 * CAS、增加workerCount值
                 */
                if (compareAndIncrementWorkerCount(c)){
                    break retry;
                }
                //重新读ctl
                c = ctl.get();
                if (runStateOf(c) != rs)
                    continue retry;
            }
        }

        boolean workerStarted = false;
        boolean workerAdded = false;
        Worker w = null;
        try {
            w = new Worker(firstTask);
            final Thread t = w.thread;
            if (t != null) {
                final ReentrantLock mianLock = this.mainLock;
                mainLock.lock();
                try {
                    int rs = runStateOf(ctl.get());

                    if (rs < SHUTDOWN ||
                            (rs == SHUTDOWN && firstTask == null)) {
                        if (t.isAlive())
                            throw new IllegalThreadStateException();
                        workers.add(w);
                        int s = workers.size();
                        if (s > largestPoolSize)
                            largestPoolSize = s;
                        workerAdded = true;
                    }
                } finally {
                    mainLock.unlock();
                }
                if (workerAdded) {
                    t.start();
                    workerStarted = true;
                }
            }
        } finally {
            if (!workerStarted) {
                //如果worker中 thread为null或者thread.start()启动失败
                //调用addWorkerFailed方法
                addWorkerFailed(w);
            }
        }
        return workerStarted;
    }

    private void addWorkerFailed(Worker w) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            if (w != null)
                workers.remove(w);
            decrementWorkerCount();
            //末期
            tryTerminate();
        } finally {
            mainLock.unlock();
        }
    }

    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                Executors.defaultThreadFactory(), defaultHandler);
    }

    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                threadFactory, defaultHandler);
    }

    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              RejectedExecutionHandler handler) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                Executors.defaultThreadFactory(), handler);
    }

    /**
     * 核心构造方法
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     * @param handler
     */
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
        /**
         * 1、判断corePoolSize、maximumPoolSize、keepAliveTIme 参数正确性
         */
        if (corePoolSize < 0 ||
                maximumPoolSize < 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0)
            throw new IllegalArgumentException();

        /**
         * 2、判断 workQueue、threadFactory、handler 参数是否为null
         */
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();

        /**
         * 3、赋值操作
         */
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }


    protected void beforeExecute(Thread t, Runnable r) {
    }

    protected void afterExecute(Runnable r, Throwable t) {
    }

    public void setThreadFactory(ThreadFactory threadFactory) {
        if (threadFactory == null)
            throw new NullPointerException();
        this.threadFactory = threadFactory;
    }

    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    /**
     * 移除workQueue中的任务，并会调用tryTerminate（）
     *
     * @param task
     * @return
     */
    public boolean remove(Runnable task) {
        boolean removed = workQueue.remove(task);
        tryTerminate();
        return removed;
    }

    /**
     * 线程池尝试进入Terminate状态
     * 1、如果当前状态是RUNNING,返回
     * 2、如果当前状态是TIDYING或者TERMINATE，返回
     * 3、如果当前状态是SHUTDOWN、并且任务队列部不为空，返回
     */
    final void tryTerminate() {
        for (; ; ) {
            int c = ctl.get();
            if (isRunning(c) ||
                    runStateAtLeast(c, TIDYING) ||
                    (runStateOf(c) == SHUTDOWN && !workQueue.isEmpty())) {
                return;
            }
            if (workerCountOf(c) != 0) {
                interruptIdleWorkers(ONLY_ONE);
                return;
            }

            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                if (ctl.compareAndSet(c, ctlOf(TIDYING, 0))) {
                    try {
                        terminated();
                    } finally {
                        ctl.set(ctlOf(TERMINATED, 0));
                        termination.signalAll();
                    }
                    return;
                }
            } finally {
                mainLock.unlock();
            }
        }
    }

    protected void terminated() {
    }

    /**
     * 默认执行策略：拒绝任务，抛出异常
     */
    public static class AbortPolicy implements RejectedExecutionHandler {

        public AbortPolicy() {
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            throw new RejectedExecutionException("Task " + r.toString() +
                    "  rejected from " +
                    executor.toString());
        }
    }

    /**
     * 设置 核心工作线程在超时后关闭
     * 需要保证keepAliveTime大于零，否则抛出异常
     *
     * @param value
     */
    public void allowCoreThreadTimeOut(Boolean value) {
        if (value && keepAliveTime <= 0)
            throw new IllegalArgumentException("keepAliveTime 必须大于零，且设置值必须为true ！");
        if (value != allowCoreThreadTimeOut) {
            allowCoreThreadTimeOut = value;
            /**
             * value为true后，中断所有空闲线程，重新生成新worker
             */
            if (value)
                interruptIdleWorkers();
        }
    }

    private void interruptIdleWorkers() {
        interruptIdleWorkers(false);
    }

    /**
     * 功能：中断workers中处于unlock且interrupted == false的空闲worker
     *
     * @param onlyOne
     */
    private void interruptIdleWorkers(boolean onlyOne) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (Worker w : workers) {
                Thread t = w.thread;
                if (!t.isInterrupted() && w.tryLock()) {
                    try {
                        t.interrupt();
                    } catch (SecurityException ignore) {
                    } finally {
                        w.unlock();
                    }
                }
                if (onlyOne) {
                    break;
                }
            }
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * 中断线程池中所有worker.thread线程
     */
    private void interruptWorkers() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (Worker w : workers) {
                w.interruptIfStarted();
            }
        } finally {
            mainLock.unlock();
        }

    }

    /**
     * 工作线程处理完所有任务，包括队列任务
     */
    @Override
    public void shutdown() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            /**
             * 检查当前主线程是否可以修改线程参数
             */
            checkShutdownAccess();
            /**
             * 修改当前线程池运行状态
             */
            advanceRunState(SHUTDOWN);
            /**
             * 中断所有空闲线程
             */
            interruptIdleWorkers();
            onShutdown();
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
    }

    @Override
    public List<Runnable> shutdownNow() {
        List<Runnable> tasks;
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            /**
             * 检查调用线程权限
             */
            checkShutdownAccess();
            /**
             * 修改当前线程池状态到 STOP
             */
            advanceRunState(STOP);
            /**
             * 中断所有worker，包括工作中的worker
             */
            interruptWorkers();
            /**
             * 清空workQueue，并得到队列任务
             */
            tasks = drainQueue();
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
        return tasks;
    }

}
