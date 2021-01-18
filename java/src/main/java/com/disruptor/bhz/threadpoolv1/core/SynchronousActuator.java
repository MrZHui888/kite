package com.disruptor.bhz.threadpoolv1.core;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;

public class SynchronousActuator implements Actuator {

    private final SynchronousQueue<Runnable> runnables;

    private final ThreadFactory threadFactory;

    private final Thread[] threads;

    private final boolean isStart;

    private final int size;

    private final String name;

    private final boolean fair;

    public SynchronousActuator(String name, int size, boolean fair) {
        this.runnables = new SynchronousQueue(fair);
        this.threadFactory = (r) -> {
            Thread t = new Thread(r);
            t.setName(name);
            return t;
        };
        this.threads = new Thread[size];
        this.size = size;
        this.name = name;
        this.fair = fair;
        for (int i = 0; i < size; i++) {
            this.threads[i] = threadFactory.newThread(() ->
            {
                while (true) {
                    try {
                        runnables.take().run();
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    } catch (Exception e) {
                    }
                }
            });
            this.threads[i].setName(this.threads[i].getName() + "-" + (i + 1));
        }
        this.isStart = false;
    }

    private SynchronousActuator(String name, int size, boolean fair, SynchronousQueue<Runnable> runnables, ThreadFactory threadFactory, Thread[] threads, boolean isStart) {
        this.size = size;
        this.name = name;
        this.fair = fair;
        this.runnables = runnables;
        this.threadFactory = threadFactory;
        this.threads = threads;
        this.isStart = isStart;
    }

    public SynchronousActuator start() {
        if (this.isStart) {
            throw new IllegalStateException();
        }
        SynchronousActuator synchronousActuator = new SynchronousActuator(name, size, fair, runnables, threadFactory, threads, true);
        for (int i = 0; i < synchronousActuator.size; i++) {
            synchronousActuator.threads[i].start();
        }
        return synchronousActuator;
    }

    public SynchronousActuator(String name, int size) {
        this(name, size, true);
    }

    @Override
    public void onEvent(RunEvent runEvent, long l, boolean b) throws Exception {
        execute(runEvent.getRunnable());
    }

    @Override
    public void execute(Runnable command) {
        try {
            runnables.put(command);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void onEvent(RunEvent runEvent) throws Exception {
        execute(runEvent.getRunnable());
    }
}
