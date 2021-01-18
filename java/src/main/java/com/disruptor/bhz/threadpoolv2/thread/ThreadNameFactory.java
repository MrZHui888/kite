package com.disruptor.bhz.threadpoolv2.thread;

import java.util.concurrent.ThreadFactory;

public class ThreadNameFactory {

    public static ThreadFactory reName(String name) {
        return (r) -> {
            Thread t = new Thread(r);
            t.setName(name);
            return t;
        };
    }

    private ThreadNameFactory() {
    }
}
