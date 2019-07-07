package com.kite.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Guzh
 * @since : 2019-06-19
 */
public class ABC2 {
	private static Lock lock = new ReentrantLock();
	private static int count = 0;
	private static Condition A = lock.newCondition();
	private static Condition B = lock.newCondition();
	private static Condition C = lock.newCondition();

	public static class A implements Runnable {

		@Override
		public void run() {
			lock.lock();
			try {
				for (int i = 0; i < 10; i++) {
					while (count % 3 != 0) {
						A.await();
					}
					System.out.print("A");
					count++;
					B.signal();
				}
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
		}
	}
	public static class B implements Runnable {

		@Override
		public void run() {
			lock.lock();
			try {
				for (int i = 0; i < 10; i++) {
					while (count % 3 !=1) {
						B.await();
					}
					System.out.print("B");
					count++;
					C.signal();
				}
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
		}
	}
	public static class C implements Runnable {

		@Override
		public void run() {
			lock.lock();
			try {
				for (int i = 0; i < 10; i++) {
					while (count % 3 != 2) {
						C.await();
					}
					System.out.println("C");
					count++;
					A.signal();
				}
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		Thread a=new Thread(new A());
		a.start();
		Thread b=new Thread(new B());
		b.start();
		Thread c=new Thread(new C());
		c.start();
	}
}
