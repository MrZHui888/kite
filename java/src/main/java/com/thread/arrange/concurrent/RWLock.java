package com.thread.arrange.concurrent;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLock {

    static ReentrantReadWriteLock rtrw = new ReentrantReadWriteLock();

    static Lock r = rtrw.readLock();

    static Lock w = rtrw.writeLock();

    static Map<String, Object> cache = Maps.newHashMap();

    public static final Object get(String key) {
        r.lock();// 此处是会阻塞的
        try {
            return cache.get(key);
        } finally {
            r.unlock();
        }
    }

    public static final void put(String key, Object value) {
        w.lock();
        try {
            cache.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public static void main(String[] args) {


        // 读 读 可以共享
        // 读 写 互斥（阻塞）
        // 写 写 互斥 （阻塞）
        // 适用读多写少的场景

        rtrw.readLock();


        rtrw.writeLock();
    }
}
