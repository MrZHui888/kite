package com.disruptor.bhz.threadpoolv1.core.step;

public interface Step6 {
    Step7 runWhithDirectActuator();

    Step7 runWhithSynchronousActuator(String name, int size, boolean fair);
}
