package com.disruptor.bhz.threadpoolv2.distribute;

import com.disruptor.bhz.threadpoolv2.event.RunEvent;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Comparator;
import java.util.stream.Stream;

/**
 * 空闲选择器
 */
public class RemainingDistributor implements RunEventDistributor {

    private static final Comparator<Disruptor> findMaxRemainingCapacity = (a, b) -> (int) (a.getRingBuffer().remainingCapacity() - b.getRingBuffer().remainingCapacity());

    @Override
    public Disruptor<RunEvent> selectOne(Disruptor<RunEvent>[] disruptors, int splitSize, int splitFactor, long sequence, boolean endOfBatch) {
        return Stream.of(disruptors).max(findMaxRemainingCapacity).get();
    }
}
