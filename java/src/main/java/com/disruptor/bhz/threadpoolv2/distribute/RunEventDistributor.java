package com.disruptor.bhz.threadpoolv2.distribute;

import com.disruptor.bhz.threadpoolv1.core.RunEvent;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 线程分发
 */
public interface RunEventDistributor {

    Disruptor<RunEvent> selectOne(Disruptor<RunEvent>[] disruptors, int splitSize, int splitFactor, long sequence, boolean endOfBatch);

}
