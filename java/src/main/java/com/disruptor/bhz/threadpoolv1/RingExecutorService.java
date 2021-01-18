package com.disruptor.bhz.threadpoolv1;


import com.disruptor.bhz.threadpoolv1.core.Connector;
import com.disruptor.bhz.threadpoolv1.core.ConnectorBuilder;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 环形队列的线程池
 */
public class RingExecutorService extends AbstractExecutorService implements ExecutorService {

    private final Connector connector;

    private volatile boolean isShutdown = false;


    public RingExecutorService() {
        this(false, 1048576, Runtime.getRuntime().availableProcessors(), 65536);
    }

    public RingExecutorService(Connector connector) {
        this.connector = connector;
    }


    /**
     * @param singleProducer    一个线程生产    （单个生产者大大提高性能）
     * @param producerCacheSize 生产者缓冲区大小  （必须满足 2的n次方）
     * @param consumerSize      消费者个数 （cpu个数）
     * @param consumerCacheSize 消费者缓冲区大小 （必须满足 2的n次方，建议 生产者缓冲区大小=消费者个数*消费者缓冲区大小）
     */
    public RingExecutorService(boolean singleProducer, int producerCacheSize, int consumerSize, int consumerCacheSize) {
        connector = ConnectorBuilder.newBuilder()
                .createConnector("connector")
                .isSingleProducer(singleProducer)
                .channelConnect(producerCacheSize)
                .connectAcceptor("acceptor", consumerCacheSize, consumerSize)
                .sequenceDistributor()
                .runWhithSynchronousActuator("actuator", 2, false)
                .build();
    }

    @Override
    public synchronized void shutdown() {
        if (!isShutdown) {
            connector.shutdown();
        }
        isShutdown = true;
    }

    @Override
    public List<Runnable> shutdownNow() {
        shutdown();
        return null;
    }

    @Override
    public boolean isShutdown() {
        return isShutdown;
    }

    @Override
    public boolean isTerminated() {
        return isShutdown;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void execute(Runnable command) {
        if (!isShutdown) {
            this.connector.transfer(command);
        }
    }

}
