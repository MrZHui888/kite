package com.disruptor.bhz.threadpoolv2;

import com.disruptor.bhz.threadpoolv2.distribute.DistributeManage;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * distributor线程池入口
 * step1: 设置消费方式
 * step2: 设置选择器
 * step3: 设置消费类型
 *
 * @author gzh
 */
public class DistributorExecutorService extends AbstractExecutorService implements ExecutorService {


    private DistributeManage distributeManage;


    private volatile boolean isShutdown = false;

    public DistributorExecutorService(DistributeManage distributeManage) {
        this.distributeManage = distributeManage;
    }

    public DistributorExecutorService() {
    }

    @Override
    public synchronized void shutdown() {
        if (!isShutdown) {
            distributeManage.shutdown();
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
            this.distributeManage.exe(command);
        }
    }
}
