package com.disruptor.bhz.threadpoolv1.core;

import com.lmax.disruptor.dsl.Disruptor;

import java.util.Comparator;
import java.util.stream.Stream;

/**
 * 空闲选择器
 */
public class RemainingDistributor implements Distributor {
    private static final Comparator<Disruptor> findMaxRemainingCapacity = (a, b) -> (int) (a.getRingBuffer().remainingCapacity() - b.getRingBuffer().remainingCapacity());

    @Override
    public Disruptor<RunEvent> selectOne(Disruptor<RunEvent>[] disruptors, int splitSize, int splitFactor, long sequence, boolean endOfBatch) {
        return Stream.of(disruptors).max(findMaxRemainingCapacity).get();
    }
}
