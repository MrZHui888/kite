package com.disruptor.bhz.threadpoolv2.handler;

import com.disruptor.bhz.threadpoolv1.core.Distributor;
import com.disruptor.bhz.threadpoolv2.event.RunEvent;
import com.lmax.disruptor.EventHandler;

/**
 * 事件处理器
 */
public class RunEventHandle implements EventHandler<RunEvent> {

    private final Distributor distributor;

    public RunEventHandle(Distributor distributor) {

        this.distributor = distributor;

    }

    @Override
    public void onEvent(RunEvent event, long sequence, boolean endOfBatch) throws Exception {

    }
}
