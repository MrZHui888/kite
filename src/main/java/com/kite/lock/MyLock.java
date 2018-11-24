package com.kite.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author : Guzh
 * @since : 2018/11/24
 * Lock的实现
 */
public class MyLock implements Lock {

    private boolean isLocked = false;

    @Override
    public synchronized void lock() {
        //...  第一个不用等待，其他线程等待
        // while  自旋锁
        while (isLocked) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isLocked = true;
        }
    }

    @Override
    public synchronized void unlock() {
        isLocked = false;
        notify();
    }


    @Override
    public void lockInterruptibly() throws InterruptedException {

    }


    public MyLock() {
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
