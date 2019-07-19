package com.roocon.thread.td4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
<<<<<<< HEAD
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo {

	public static void main(String[] args) {
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 50, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), new ThreadPoolExecutor.CallerRunsPolicy());
		AtomicInteger count = new AtomicInteger();
		for (int i = 0; i < 100; i++) {
=======
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;

public class Demo {
	
	public static void main(String[] args) {
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 50, 10, TimeUnit.DAYS, new ArrayBlockingQueue<>(10),new ThreadPoolExecutor.CallerRunsPolicy());
		AtomicInteger count = new AtomicInteger();
		for(int i = 0; i < 100 ;i ++) {
>>>>>>> 14cc92fe984ad08805e170b82c2299b24f147edc
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName());
					count.getAndIncrement();
				}
			});
		}
<<<<<<< HEAD

		threadPool.shutdown();


		while (Thread.activeCount() > 1) {
		//	Thread.currentThread().getThreadGroup().list();
			System.out.println(Thread.activeCount());
			System.out.println(Thread.currentThread().getThreadGroup().activeCount());

=======
		
		threadPool.shutdown();
		
		
		while(Thread.activeCount() > 1) {
			
>>>>>>> 14cc92fe984ad08805e170b82c2299b24f147edc
		}
		System.out.println(count.get());
	}

}
