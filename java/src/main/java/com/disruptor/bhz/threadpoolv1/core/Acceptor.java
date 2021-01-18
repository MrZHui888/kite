package com.disruptor.bhz.threadpoolv1.core;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * 任务接收器
 */
class Acceptor {

    private final String name;

    private final int bufferSize;

    private final int splitSize;

    private final int splitFactor;

    final AcceptorHandle acceptorHandle;

    private final Disruptor<RunEvent>[] disruptors;

    private final boolean isRuning;

    /**
     * //size 1 or splitSize
     */
    private final Actuator[] actuators;

    public Acceptor(String name, int bufferSize, int splitSize, boolean singleProducer, Distributor distributor) {
        this.name = name;
        this.bufferSize = bufferSize;
        this.splitSize = splitSize;
        this.splitFactor = splitSize - 1;
        this.acceptorHandle = new AcceptorHandle(distributor);
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


    private Acceptor(String name, int bufferSize, int splitSize, int splitFactor, AcceptorHandle acceptorHandle, Disruptor<RunEvent>[] disruptors, boolean isRuning, Actuator[] actuators) {
        this.name = name;
        this.bufferSize = bufferSize;
        this.splitSize = splitSize;
        this.splitFactor = splitFactor;
        this.acceptorHandle = acceptorHandle;
        this.disruptors = disruptors;
        this.isRuning = isRuning;
        this.actuators = actuators;
    }

    final class AcceptorHandle implements EventHandler<RunEvent> {

        private final Distributor distributor;

        public AcceptorHandle(Distributor distributor) {
            this.distributor = distributor;
        }

        @Override
        public void onEvent(RunEvent event, long sequence, boolean endOfBatch) throws Exception {
            Disruptor<RunEvent> disruptor = distributor.selectOne(disruptors, splitSize, splitFactor, sequence, endOfBatch);
            disruptor.publishEvent((e, s) -> e.setRunnable(event.getRunnable()));
        }
    }

    public Acceptor runWhith(Actuator actuator) {
        if (isRuning) {
            throw new IllegalStateException();
        }
        Actuator[] ats = new Actuator[1];
        ats[0] = actuator;
        Acceptor acceptor = new Acceptor(name, bufferSize, splitSize, splitFactor, acceptorHandle, disruptors, true, ats);
        for (int i = 0; i < acceptor.splitSize; i++) {
            acceptor.disruptors[i].handleEventsWith(actuator);
            acceptor.disruptors[i].start();
        }
        return acceptor;
    }

    public Acceptor runWhithPool(Actuator actuator, final int poolSize) {
        if (isRuning) {
            throw new IllegalStateException();
        }
        Actuator[] ats = new Actuator[poolSize];
        for (int i = 0; i < poolSize; i++) {
            ats[i] = actuator;
        }
        Acceptor acceptor = new Acceptor(name, bufferSize, splitSize, splitFactor, acceptorHandle, disruptors, true, ats);
        for (int i = 0; i < acceptor.splitSize; i++) {
            acceptor.disruptors[i].handleEventsWithWorkerPool(ats);
            acceptor.disruptors[i].start();
        }
        return acceptor;
    }


    public Acceptor runWhith(Actuator[] actuators) {
        if (isRuning) {
            throw new IllegalStateException();
        }
        if (actuators.length != splitSize) {
            throw new IllegalArgumentException();
        }
        Acceptor acceptor = new Acceptor(name, bufferSize, splitSize, splitFactor, acceptorHandle, disruptors, true, actuators);
        for (int i = 0; i < acceptor.splitSize; i++) {
            acceptor.disruptors[i].handleEventsWith(acceptor.actuators[i]);
            acceptor.disruptors[i].start();
        }
        return acceptor;
    }

    public Acceptor runWhithPool(Actuator[] actuators, final int poolSize) {
        if (isRuning) {
            throw new IllegalStateException();
        }
        if (actuators.length != splitSize * poolSize) {
            throw new IllegalArgumentException();
        }
        Acceptor acceptor = new Acceptor(name, bufferSize, splitSize, splitFactor, acceptorHandle, disruptors, true, actuators);
        int ind = 0;
        for (int i = 0; i < acceptor.splitSize; i++) {
            Actuator[] ats = new Actuator[poolSize];
            for (int j = 0; j < poolSize; j++) {
                ats[j] = actuators[ind];
                ind++;
            }
            acceptor.disruptors[i].handleEventsWithWorkerPool(ats);
            acceptor.disruptors[i].start();
        }
        return acceptor;
    }

    public void shutdown() {
        for (int i = 0; i < splitSize; i++) {
            disruptors[i].shutdown();
        }
        for (Actuator actuator : actuators) {
            actuator.shutdown();
        }
    }

    public int[] getRemainingCapacity() {
        int[] rc = new int[splitSize];
        for (int i = 0; i < splitSize; i++) {
            rc[i] = (int) disruptors[i].getRingBuffer().remainingCapacity();
        }
        return rc;
    }
}