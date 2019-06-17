package com.kite.thread.th1;

public class ThreadA extends Thread {

    private ObjectService os;

    public ThreadA(ObjectService os) {
        os = os;
    }

    @Override
    public void run() {
        super.run();
        os.serviceMethod();
    }
}
