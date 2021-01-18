package com.disruptor.bhz.threadpoolv1.core.step;

public interface Step3 {
    Step4 directConnect();

    Step4 channelConnect(int bufferSize);

}
