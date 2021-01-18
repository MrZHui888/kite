package com.disruptor.bhz.threadpoolv2.distribute;

import com.disruptor.bhz.threadpoolv1.core.RunEvent;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 顺序选择器
 */
public class SequenceDistributor implements RunEventDistributor {

    @Override
    public Disruptor<RunEvent> selectOne(Disruptor<RunEvent>[] disruptors, int splitSize, int splitFactor, long sequence, boolean endOfBatch) {
        return disruptors[(int) (sequence & splitFactor)];
    }
}
