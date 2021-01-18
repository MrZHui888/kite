package com.disruptor.bhz.threadpoolv1.core;


import com.lmax.disruptor.dsl.Disruptor;

/**
 * 顺序选择器 根据序号
 */
public class SequenceDistributor implements Distributor {

    @Override
    public Disruptor<RunEvent> selectOne(Disruptor<RunEvent>[] disruptors, int splitSize, int splitFactor, long sequence, boolean endOfBatch) {
        return disruptors[(int) (sequence & splitFactor)];
    }

}
