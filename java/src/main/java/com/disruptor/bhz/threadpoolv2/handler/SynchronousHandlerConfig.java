package com.disruptor.bhz.threadpoolv2.handler;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;

public class SynchronousHandlerConfig {

    private final Thread[] threads;

    private final ThreadFactory threadFactory;

    private final SynchronousQueue<Runnable> runnables;

    private final boolean isStart;

    private final int size;

    private final String name;

    private final boolean fair;


    public SynchronousHandlerConfig(Thread[] threads, ThreadFactory threadFactory, SynchronousQueue<Runnable> runnables, boolean isStart, int size, String name, boolean fair) {
        this.threads = threads;
        this.threadFactory = threadFactory;
        this.runnables = runnables;
        this.isStart = isStart;
        this.size = size;
        this.name = name;
        this.fair = fair;
    }




    /**
     * 获取默认的配置
     *
     * @return 缓冲队列
     */
    public static SynchronousHandlerConfig getDefaultConfig() {
        return null;
    }

    public Thread[] getThreads() {
        return threads;
    }

    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    public boolean isStart() {
        return isStart;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public boolean isFair() {
        return fair;
    }


    public SynchronousQueue<Runnable> getRunnables() {
        return runnables;
    }


    public static final class SynchronousHandlerConfigBuilder {
        private Thread[] threads;
        private ThreadFactory threadFactory;
        private SynchronousQueue<Runnable> runnables;
        private boolean isStart;
        private int size;
        private String name;
        private boolean fair;

        private SynchronousHandlerConfigBuilder() {
        }

        public static SynchronousHandlerConfigBuilder aSynchronousHandlerConfig() {
            return new SynchronousHandlerConfigBuilder();
        }

        public SynchronousHandlerConfigBuilder withThreads(Thread[] threads) {
            this.threads = threads;
            return this;
        }

        public SynchronousHandlerConfigBuilder withThreadFactory(ThreadFactory threadFactory) {
            this.threadFactory = threadFactory;
            return this;
        }

        public SynchronousHandlerConfigBuilder withRunnables(SynchronousQueue<Runnable> runnables) {
            this.runnables = runnables;
            return this;
        }

        public SynchronousHandlerConfigBuilder withIsStart(boolean isStart) {
            this.isStart = isStart;
            return this;
        }

        public SynchronousHandlerConfigBuilder withSize(int size) {
            this.size = size;
            return this;
        }

        public SynchronousHandlerConfigBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SynchronousHandlerConfigBuilder withFair(boolean fair) {
            this.fair = fair;
            return this;
        }

        public SynchronousHandlerConfig build() {
            return new SynchronousHandlerConfig(threads, threadFactory, runnables, isStart, size, name, fair);
        }
    }
}
