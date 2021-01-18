package com.disruptor.bhz.threadpoolv1.core.step;

public interface Step2 {
    Step3 isSingleProducer();

    Step3 isMultipleProducer();

    Step3 isSingleProducer(boolean isSingleProducer);

}
