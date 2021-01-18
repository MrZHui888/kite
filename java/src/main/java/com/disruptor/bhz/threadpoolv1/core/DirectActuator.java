package com.disruptor.bhz.threadpoolv1.core;


/**
 * 直接执行器
 */
public class DirectActuator implements Actuator {

    @Override
    public void onEvent(RunEvent runEvent, long l, boolean b) throws Exception {
        execute(runEvent.getRunnable());
    }

    @Override
    public void execute(Runnable command) {
        try {
            command.run();
        } catch (Exception e) {
            throw e;
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
