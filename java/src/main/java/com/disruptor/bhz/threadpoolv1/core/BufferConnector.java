package com.disruptor.bhz.threadpoolv1.core;


import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;
/**
 * 带缓冲的任务连接器
 */
public class BufferConnector extends AbsConnector {

    private final String name;

    private final int bufferSize;//1048576

    private final boolean isConnect;

    private final Acceptor acceptor;

    private final Disruptor<RunEvent> disruptor;

    private final ThreadFactory threadFactory;

    public BufferConnector(String name, int bufferSize) {
        this(name,bufferSize,true);
    }

    public BufferConnector(String name, int bufferSize, boolean singleProducer) {
        this.name = name;
        this.bufferSize = bufferSize;
        this.threadFactory = (r)->{Thread t = new Thread(r,name);t.setPriority(Thread.MAX_PRIORITY);return t;};
        this.disruptor = new Disruptor(new RunEventFactory(),bufferSize,threadFactory,singleProducer?ProducerType.SINGLE:ProducerType.MULTI,new YieldingWaitStrategy());
        this.acceptor = null;
        this.isConnect = false;
    }

    private BufferConnector(String name, int bufferSize, boolean isConnect, Acceptor acceptor, Disruptor<RunEvent> disruptor, ThreadFactory threadFactory) {
        this.name = name;
        this.bufferSize = bufferSize;
        this.isConnect = isConnect;
        this.acceptor = acceptor;
        this.disruptor = disruptor;
        this.threadFactory = threadFactory;
    }

    @Override
    public BufferConnector connect(Acceptor acceptor){
        if (isConnect){
            throw new IllegalStateException();
        }
        BufferConnector connector = new BufferConnector(name,bufferSize,true,acceptor,disruptor,threadFactory);
        connector.disruptor.handleEventsWith(acceptor.acceptorHandle);
        connector.disruptor.start();
        return connector;
    }

    @Override
    public void transfer(Runnable command){
        if(isConnect){
            disruptor.publishEvent((e,s)->e.setRunnable(command));
        }else{
            throw new IllegalStateException();
        }
    }

    @Override
    public void shutdown() {
        disruptor.shutdown();
        acceptor.shutdown();
    }

    @Override
    public int getRemainingCapacity() {
        return (int)disruptor.getRingBuffer().remainingCapacity();
    }

}
