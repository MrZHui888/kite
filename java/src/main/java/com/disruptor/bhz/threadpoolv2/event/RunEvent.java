package com.disruptor.bhz.threadpoolv2.event;


public class RunEvent {

    private Runnable runnable;

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
}
