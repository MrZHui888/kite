package com.disruptor.bhz.threadpool;

/**
 * 线程池根接口
 */
public interface Executor {

    void execute(Runnable command);
}