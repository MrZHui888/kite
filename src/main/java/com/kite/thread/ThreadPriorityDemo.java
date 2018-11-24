package com.kite.thread;

/**
 * @author : Guzh
 * @since : 2018/11/9
 * 线程优先级
 */
public class ThreadPriorityDemo {

    public static void main(String[] args) {
        PriorityRunnable priorityRunnable = new PriorityRunnable();
        priorityRunnable.setPriority(1);
        priorityRunnable.start();
    }
}

class PriorityRunnable extends Thread {

    @Override public void run() {
        System.out.println("获取当前线程的优先级" + this.getPriority());
    }
}
