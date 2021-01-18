package com.disruptor.bhz.threadpoolv1.core.step;

public interface Step5 {

    Step6 sequenceDistributor();

    @Deprecated
        //效率低下 阻塞connector
    Step6 remainingDistributor();

}
