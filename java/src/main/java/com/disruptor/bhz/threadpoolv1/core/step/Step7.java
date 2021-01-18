package com.disruptor.bhz.threadpoolv1.core.step;

import com.disruptor.bhz.threadpoolv1.core.Connector;

public interface Step7 {
    Step8 usePool(int poolSize);

    Connector build();
}
