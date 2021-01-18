package com.disruptor.bhz.threadpoolv2.event;

/**
 * 携带trace信息的run
 */
public class TraceRunEvent {


    private Runnable runnable;

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
}
