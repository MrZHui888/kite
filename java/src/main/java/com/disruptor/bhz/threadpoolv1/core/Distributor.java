package com.disruptor.bhz.threadpoolv1.core;

import com.lmax.disruptor.dsl.Disruptor;

/**
 * 任务分配器
 */
public interface Distributor {

    Disruptor<RunEvent> selectOne(Disruptor<RunEvent>[] disruptors, int splitSize, int splitFactor, long sequence, boolean endOfBatch);

}
