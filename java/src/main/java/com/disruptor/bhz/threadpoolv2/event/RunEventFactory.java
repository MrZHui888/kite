package com.disruptor.bhz.threadpoolv2.event;

import com.disruptor.bhz.threadpoolv1.core.RunEvent;
import com.lmax.disruptor.EventFactory;

/**
 * 线程事件的生成工程
 */
public class RunEventFactory implements EventFactory<RunEvent> {

    @Override
    public RunEvent newInstance() {
        return new RunEvent();
    }
}
