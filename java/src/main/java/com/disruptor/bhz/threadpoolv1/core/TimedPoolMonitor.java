package com.disruptor.bhz.threadpoolv1.core;

import java.util.Arrays;

public class TimedPoolMonitor implements PoolMonitor{

    private final  boolean isSingleProducer;

    private final String connectorName;

    private final int connectorBufferSize;

    private volatile int  connectorMaxRemainingCapacity = Integer.MIN_VALUE;

    private volatile int  connectorMinRemainingCapacity = Integer.MAX_VALUE;

    private volatile int  connectorCurrentRemainingCapacity = 0;

    private final String acceptorName;

    private final int acceptorBufferSize;

    private final int acceptorSplitSize;

    private final  int[] acceptorsMaxRemainingCapacity;

    private final  int[] acceptorsMinRemainingCapacity;

    private final int[]  acceptorsCurrentRemainingCapacity;

    public TimedPoolMonitor(boolean isSingleProducer, String connectorName, int connectorBufferSize, String acceptorName, int acceptorBufferSize, int acceptorSplitSize) {
        this.isSingleProducer = isSingleProducer;
        this.connectorName = connectorName;
        this.connectorBufferSize = connectorBufferSize;
        this.acceptorName = acceptorName;
        this.acceptorBufferSize = acceptorBufferSize;
        this.acceptorSplitSize = acceptorSplitSize;
        this.acceptorsMaxRemainingCapacity = new int[acceptorSplitSize];
        this.acceptorsMinRemainingCapacity = new int[acceptorSplitSize];
        for(int i=0;i<acceptorSplitSize;i++){
            acceptorsMaxRemainingCapacity[i]=Integer.MIN_VALUE;
            acceptorsMinRemainingCapacity[i]=Integer.MAX_VALUE;
        }
        this.acceptorsCurrentRemainingCapacity = new int[acceptorSplitSize];
    }

    @Override
    public boolean isSingleProducer() {
        return isSingleProducer;
    }

    @Override
    public String getConnectorName() {
        return connectorName;
    }

    @Override
    public int getConnectorBufferSize() {
        return connectorBufferSize;
    }

    @Override
    public int getConnectorMaxRemainingCapacity() {
        return connectorMaxRemainingCapacity;
    }

    @Override
    public int getConnectorMinRemainingCapacity() {
        return connectorMinRemainingCapacity;
    }

    protected void updateConnectorRemainingCapacity(int connectorRemainingCapacity) {
        this.connectorCurrentRemainingCapacity=connectorRemainingCapacity;
        connectorMaxRemainingCapacity=Math.max(connectorMaxRemainingCapacity,connectorRemainingCapacity);
        connectorMinRemainingCapacity=Math.min(connectorMinRemainingCapacity,connectorRemainingCapacity);
    }

    @Override
    public String getAcceptorName() {
        return acceptorName;
    }

    @Override
    public int getAcceptorBufferSize() {
        return acceptorBufferSize;
    }

    @Override
    public int getAcceptorSplitSize() {
        return acceptorSplitSize;
    }

    @Override
    public int[] getAcceptorsMaxRemainingCapacity() {
        return Arrays.copyOf(acceptorsMaxRemainingCapacity,acceptorsMaxRemainingCapacity.length);
    }

    @Override
    public int[] getAcceptorsMinRemainingCapacity() {
        return Arrays.copyOf(acceptorsMinRemainingCapacity,acceptorsMinRemainingCapacity.length);
    }
    @Override
    public int getConnectorCurrentRemainingCapacity() {
        return connectorCurrentRemainingCapacity;
    }
    @Override
    public int[] getAcceptorsCurrentRemainingCapacity() {
        return Arrays.copyOf(acceptorsCurrentRemainingCapacity,acceptorsCurrentRemainingCapacity.length);
    }

    protected void updateAcceptorsRemainingCapacity(int[] remainingCapacity){
        for(int i=0;i<remainingCapacity.length;i++) {
            int remainingCapacityCount = remainingCapacity[i];
            acceptorsCurrentRemainingCapacity[i]=remainingCapacityCount;
            acceptorsMaxRemainingCapacity[i] = Math.max(acceptorsMaxRemainingCapacity[i], remainingCapacityCount);
            acceptorsMinRemainingCapacity[i] = Math.min(acceptorsMinRemainingCapacity[i], remainingCapacityCount);
        }
    }
}
