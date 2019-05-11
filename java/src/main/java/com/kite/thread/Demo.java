package com.kite.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Guzh
 * @since : 2019-01-13
 */
public class Demo {
    private int singal;
    private Lock lock = new ReentrantLock();
    private Condition a = lock.newCondition();
    private Condition b = lock.newCondition();
    private Condition c = lock.newCondition();


    public void a() {
        lock.lock();
        while (singal != 0) {
            try {
                a.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("执行A");
        singal++;
        b.signal();
        lock.unlock();
    }

    public void b() {
        lock.lock();
        while (singal != 1) {
            try {
                b.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("执行B");
        singal++;
        c.signal();
        lock.unlock();
    }

    public void c() {
        lock.lock();

        while (singal != 2) {
            try {
                c.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("执行C");
        singal = 0;
        lock.unlock();
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        A a = new A(demo);
        B b = new B(demo);
        C c = new C(demo);

        new Thread(a).start();
        new Thread(b).start();
        new Thread(c).start();

    }

}

class A implements Runnable {
    private Demo demo;

    public A(Demo demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        while (true) {
            demo.a();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class B implements Runnable {
    private Demo demo;

    public B(Demo demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        while (true) {
            demo.b();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class C implements Runnable {
    private Demo demo;

    public C(Demo demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        while (true) {
            demo.c();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


