package com.kite.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author : Guzh
 * @since : 2018/11/25
 */
public class MyLock2 implements Lock {

    private Helper helper = new Helper();

    private class Helper extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            //如果第一个线程进来，可以拿到锁，返回true
            // 如果第二个线程进来拿不到锁  返回false ，有种特例如果当前进来的和当前保存的是同一个线程，则允许拿到锁，
            //  但是有代价需要更新状态值
            // 如何判断是第一个线程  还是其他线程

            int state = getState();
            Thread t = Thread.currentThread();
            //初始值
            if (state == 0) {
                if (compareAndSetState(0, arg)) {
                    setExclusiveOwnerThread(t);
                    return true;
                }
            } else if (getExclusiveOwnerThread() == t) {
                setState(state + 1);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            //锁的获取和释放是一一对应的，那么调用此方法的线程一定是当前线程
            if (Thread.currentThread() != getExclusiveOwnerThread()) {
                throw new RuntimeException();
            }
            int state = getState() - arg;
            boolean flag = false;
            if (getState() == 0) {
                setExclusiveOwnerThread(null);
                flag = true;
            }

            setState(state);
            return flag;
        }


        Condition newCondition() {
            return new ConditionObject();
        }
    }


    @Override
    public void lock() {
        helper.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        helper.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return helper.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return helper.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        helper.release(1);
    }

    @Override
    public Condition newCondition() {
        return helper.newCondition();
    }


}
