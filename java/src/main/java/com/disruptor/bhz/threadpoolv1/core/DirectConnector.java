package com.disruptor.bhz.threadpoolv1.core;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 生产线程直接链接任务接收器
 */
public class DirectConnector extends AbsConnector {

    private final String name;

    private final AtomicLong count = new AtomicLong(1);

    private final boolean isConnect;

    private final Acceptor acceptor;

    private final boolean singleProducer;

    public DirectConnector(String name) {
        this(name,true);
    }

    public DirectConnector(String name,boolean singleProducer){//singleProducer? ProducerType.SINGLE:ProducerType.MULTI
        this.name = name;
        this.singleProducer = singleProducer;
        this.acceptor = null;
        this.isConnect = false;
    }

    private DirectConnector(String name,boolean singleProducer, boolean isConnect, Acceptor acceptor) {
        this.name = name;
        this.singleProducer = singleProducer;
        this.isConnect = isConnect;
        this.acceptor = acceptor;
    }

    @Override
    public DirectConnector connect(Acceptor acceptor){
        if (isConnect){
            throw new IllegalStateException();
        }
        DirectConnector connector = new DirectConnector(name,singleProducer,true,acceptor);
        return connector;
    }

    @Override
    public void transfer(Runnable command){
        if(isConnect){
            RunEvent runEvent = new RunEvent();
            runEvent.setRunnable(command);
            try {
                acceptor.acceptorHandle.onEvent(runEvent ,count.incrementAndGet(), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            throw new IllegalStateException();
        }
    }

    @Override
    public void shutdown() {
        acceptor.shutdown();
    }

    @Override
    protected int getRemainingCapacity() {
        return 0;
    }
}
