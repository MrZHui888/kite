package com.disruptor.bhz.threadpoolv1.core;

import com.lmax.disruptor.EventFactory;

public class RunEventFactory implements EventFactory<RunEvent> {

    @Override
    public RunEvent newInstance() {
        return new RunEvent();
    }

}
