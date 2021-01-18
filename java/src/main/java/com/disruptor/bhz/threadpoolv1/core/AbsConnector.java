package com.disruptor.bhz.threadpoolv1.core;

public abstract class AbsConnector implements Connector {
    protected abstract int getRemainingCapacity();
}
