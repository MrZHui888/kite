package com.disruptor.bhz.threadpoolv2.handler;

public interface ThreadHandle {

    ThreadHandle run(RunEventHandle runEventHandle);

    ThreadHandle runPool(RunEventHandle runEventHandle, int poolSize);

    ThreadHandle run(RunEventHandle[] runEventHandles);

    ThreadHandle runPool(RunEventHandle[] runEventHandles, int poolSize);


}
