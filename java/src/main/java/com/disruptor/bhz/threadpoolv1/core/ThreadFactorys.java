package com.disruptor.bhz.threadpoolv1.core;

import java.util.concurrent.ThreadFactory;

public class ThreadFactorys {

    static final ThreadFactory DAEMON_THREAD_FACTORY = (r)-> {
        Thread t = new Thread(r);
        t.setName("threadpool.daemon");
        t.setDaemon(true);
        return t;
    };

    private ThreadFactorys(){};
}
