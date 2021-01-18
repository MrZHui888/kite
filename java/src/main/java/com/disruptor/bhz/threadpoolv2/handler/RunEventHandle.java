package com.disruptor.bhz.threadpoolv2.handler;

import com.disruptor.bhz.threadpoolv2.distribute.RunEventDistributor;
import com.disruptor.bhz.threadpoolv2.event.RunEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 事件处理器
 */
public class RunEventHandle implements EventHandler<RunEvent> {

    private final RunEventDistributor distributor;

    private final Disruptor<RunEvent>[] disruptors;

    private final int splitSize;

    private final int splitFactor;

    public RunEventHandle(RunEventDistributor distributor, Disruptor<RunEvent>[] disruptors, int splitSize, int splitFactor) {
        this.distributor = distributor;
        this.disruptors = disruptors;
        this.splitSize = splitSize;
        this.splitFactor = splitFactor;
    }

    @Override
    public void onEvent(RunEvent event, long sequence, boolean endOfBatch) throws Exception {
        Disruptor<RunEvent> disruptor = distributor.selectOne(disruptors, splitSize, splitFactor, sequence, endOfBatch);
        disruptor.publishEvent((e, s) -> e.setRunnable(event.getRunnable()));
    }
}
