package com.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FooBar {

    private int n;


    private Lock lock = new ReentrantLock();
    private Condition conditionFoo = lock.newCondition();
    private Condition conditionBar = lock.newCondition();

    private boolean tmp = true;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        lock.lock();

        for (int i = 0; i < n; i++) {

            // printFoo.run() outputs "foo". Do not change or remove this line.

            if (!tmp) {
                conditionFoo.await();
                conditionBar.signal();
            }
            printFoo.run();
            tmp = false;
            conditionBar.signal();


        }
        lock.unlock();

    }

    public void bar(Runnable printBar) throws InterruptedException {
        lock.lock();
        for (int i = 0; i < n; i++) {

            // printBar.run() outputs "bar". Do not change or remove this line.
            if (tmp) {
                conditionBar.await();
                conditionFoo.signal();
            }
            printBar.run();
            tmp = true;
            conditionFoo.signal();

        }
        lock.unlock();
    }

    public static void main(String[] args) throws Exception {
        FooBar fooBar = new FooBar(9);

        new Thread(() -> {
            try {
                fooBar.foo(() -> {
                    System.out.print("foo");
                });
            } catch (Exception e) {

            }

        }).start();

        Thread.sleep(100);

        new Thread(() -> {
            try {
                fooBar.bar(() -> {
                    System.out.print("bar");
                });
            } catch (Exception e) {

            }

        }).start();

    }
}
