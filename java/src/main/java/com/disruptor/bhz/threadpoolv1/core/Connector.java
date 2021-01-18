package com.disruptor.bhz.threadpoolv1.core;


public interface Connector {

    Connector connect(Acceptor acceptor);

    void transfer(Runnable command);

    void shutdown();

}
