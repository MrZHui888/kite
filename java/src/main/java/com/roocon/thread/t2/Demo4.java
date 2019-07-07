package com.roocon.thread.t2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Demo4 implements Callable<Integer> {
	
	
	public static void main(String[] args) throws Exception {
		Demo4 d = new Demo4();
		
		FutureTask<Integer> task = new FutureTask<>(d);
		
		Thread t = new Thread(task);
		
		t.start();
		
		System.out.println("?????????????");
		
		Integer result = task.get();
		System.out.println("?????§Ö??????" + result);
	}

	@Override
	public Integer call() throws Exception {
		System.out.println("??????§ß???????....");
		Thread.sleep(3000);
		return 1;
	}

}
