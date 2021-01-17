package com.thread.arrange.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantLock 可重入锁
 */
public class ReentrantLockDemo {

    static Lock lock = new ReentrantLock();

    static ReentrantReadWriteLock w = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        lock.lock();
        try {
        } catch (Exception E) {

        } finally {
            lock.unlock();
        }
    }
}
