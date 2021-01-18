package com.disruptor.bhz.threadpoolv2.handler;

import com.disruptor.bhz.threadpoolv2.event.RunEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.Executor;

public interface RunWorkHandler extends EventHandler<RunEvent>, WorkHandler<RunEvent>, Executor {

    void shutdown();

}
