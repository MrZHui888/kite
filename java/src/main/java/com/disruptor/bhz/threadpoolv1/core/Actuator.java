package com.disruptor.bhz.threadpoolv1.core;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.Executor;

/**
 * 任务执行器
 */
public interface Actuator extends EventHandler<RunEvent>, WorkHandler<RunEvent>, Executor {

    void shutdown();

}
