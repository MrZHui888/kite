package com.disruptor.bhz.threadpoolv2.handler;

import com.disruptor.bhz.threadpoolv2.event.RunEvent;

/**
 * 缓冲队列
 */
public class SynchronousEventHandler implements RunWorkHandler {

    private SynchronousHandlerConfig synchronousHandlerConfig;

    public SynchronousEventHandler(SynchronousHandlerConfig synchronousHandlerConfig) {
        this.synchronousHandlerConfig = synchronousHandlerConfig;
    }


    public SynchronousEventHandler(String name, int size, boolean fair) {
        this.synchronousHandlerConfig = SynchronousHandlerConfig.SynchronousHandlerConfigBuilder
                .aSynchronousHandlerConfig()
                .withFair(fair)
                .withName(name)
                .withSize(size)
                .build();
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void onEvent(RunEvent runEvent, long l, boolean b) throws Exception {

    }

    @Override
    public void onEvent(RunEvent runEvent) throws Exception {

    }

    @Override
    public void execute(Runnable command) {
        try {
            synchronousHandlerConfig.getRunnables().put(command);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

}
