package com.disruptor.bhz.threadpool;

public interface ThreadFactory {

    Thread newThread(Runnable r);
}