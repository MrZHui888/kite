package com.kite.lock;

/**
 * @author : Guzh
 * @since : 2018/11/24
 */
public class Sequence {

    private MyLock lock = new MyLock();

    private int value;


    public int getValue() {
        lock.lock();

        this.value++;

        lock.unlock();

        return this.value;
    }

    public static void main(String[] args) {
        Sequence sequence = new Sequence();


        new Thread(() -> {
            while (true) {
                int num=sequence.getValue();
                if (num > 100) {
                    return;
                }
                System.out.println(Thread.currentThread().getName() + ": " + num);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                int num=sequence.getValue();
                if (num > 100) {
                    return;
                }
                System.out.println(Thread.currentThread().getName() + ": " + num);
            }
        }).start();


    }
}
