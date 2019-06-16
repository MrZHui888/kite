package com.kite.thread.th1;

public class ObjectService {
    public void serviceMethod() {
        try {
            synchronized (this) {
                System.out.println("begin time " + System.currentTimeMillis());
                Thread.sleep(100);
                System.out.println("end Time" + System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
