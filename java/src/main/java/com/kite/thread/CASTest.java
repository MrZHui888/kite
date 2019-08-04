package com.kite.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Guzh
 * @since : 2019-07-07
 */
public class CASTest {
	private AtomicInteger atomicInteger = new AtomicInteger();  // 需要保证多个线程使用的是同一个AtomicInteger

	public static void main(String[] args) {
		CASTest casTest = new CASTest();
		casTest.atomicInteger.incrementAndGet();

		Thread.interrupted();

		ReentrantLock reentrantLock = new ReentrantLock();
		reentrantLock.isFair();

	}

}
