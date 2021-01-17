package com.disruptor.bhz.threadpool;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public abstract class AbstractExecutorService implements ExecutorService{

    /**
     * 将runnable任务包装（包装还是执行呢？）成FutureTask
     * @param runnable
     * @param value
     * @param <T>
     * @return
     */
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value){
        return new FutureTask<T>(runnable, value);
    }

    /**
     * 将callable任务包装（包装还是执行呢？）成FutureTask
     * @param callable
     * @param <T>
     * @return
     */
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable){
        return new FutureTask<T>(callable);
    }

    @Override
    public <T> Future submit(Callable<T> task) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<T> ftask = newTaskFor(task);
        execute(ftask);
        return ftask;
    }

    @Override
    public Future<?> submit(Runnable task) {
        if(task == null) throw new NullPointerException();
        //将Runnable任务包装成FutureTask
        RunnableFuture<Void> ftask = newTaskFor(task,null);
        //执行execute方法
        execute(ftask);
        return ftask;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<T> ftask = newTaskFor(task, result);
        execute(ftask);
        return ftask;
    }

}
