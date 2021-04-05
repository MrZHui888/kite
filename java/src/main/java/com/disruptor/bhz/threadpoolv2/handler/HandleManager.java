package com.disruptor.bhz.threadpoolv2.handler;

import com.disruptor.bhz.threadpoolv1.core.Actuator;
import com.disruptor.bhz.threadpoolv2.event.RunEvent;
import com.disruptor.bhz.threadpoolv2.event.RunEventFactory;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * handle  处理器
 */
public class HandleManager implements ThreadHandle {


    private String name;

    private int bufferSize;

    private int splitSize;

    private int splitFactor;

    private RunEventHandle runEventHandle;

    private final Disruptor<RunEvent>[] disruptors;

    private final boolean isRuning;

    /**
     * //size 1 or splitSize
     */
    private final Actuator[] actuators;


    public HandleManager(String name, int bufferSize, int splitSize, int splitFactor, RunEventHandle runEventHandle, Disruptor<RunEvent>[] disruptors, boolean isRuning, Actuator[] actuators) {
        this.name = name;
        this.bufferSize = bufferSize;
        this.splitSize = splitSize;
        this.splitFactor = splitFactor;
        this.runEventHandle = runEventHandle;
        this.disruptors = disruptors;
        this.isRuning = isRuning;
        this.actuators = actuators;
    }

    public HandleManager(String name, int bufferSize, int splitSize, boolean singleProducer, Disruptor distributor, Disruptor<RunEvent>[] disruptors) {
        this.name = name;
        this.bufferSize = bufferSize;
        this.splitSize = splitSize;
        this.splitFactor = splitSize - 1;
        this.runEventHandle =null;
//                new RunEventHandle(distributor,disruptors,splitSize,splitFactor);
        this.disruptors = new Disruptor[splitSize];
        this.actuators = null;
        for (int i = 0; i < splitSize; i++) {
            final String tName = name + "-" + (i + 1);
            disruptors[i] = new Disruptor(new RunEventFactory(), bufferSize, (r) -> {
                return new Thread(r, tName);
            }, singleProducer ? ProducerType.SINGLE : ProducerType.MULTI, new SleepingWaitStrategy());
        }
        this.isRuning = false;
    }

    @Override
    public ThreadHandle run(RunEventHandle runEventHandle) {
        if (isRuning) {
            throw new IllegalStateException();
        }
        RunEventHandle[] ats = new RunEventHandle[1];
        ats[0] = runEventHandle;
        HandleManager acceptor =null;
//                new HandleManager(name, bufferSize, splitSize, splitFactor, runEventHandle, disruptors, true, ats);
        for (int i = 0; i < acceptor.splitSize; i++) {
            acceptor.disruptors[i].handleEventsWith(runEventHandle);
            acceptor.disruptors[i].start();
        }
        return acceptor;
    }

    @Override
    public ThreadHandle runPool(RunEventHandle runEventHandle, int poolSize) {
        return null;
    }

    @Override
    public ThreadHandle run(RunEventHandle[] runEventHandles) {
        return null;
    }

    @Override
    public ThreadHandle runPool(RunEventHandle[] runEventHandles, int poolSize) {
        return null;
    }
}
