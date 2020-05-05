package com.test;

public class FooBar1 {

    private int n;

    private Object oFoo = new Object();

    private Object oBar = new Object();

    private boolean tmp = true;


    public FooBar1(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {


        for (int i = 0; i < n; i++) {

            // printFoo.run() outputs "foo". Do not change or remove this line.

            if (tmp) {
                oFoo.wait();
                oBar.notify();
            }
            printFoo.run();

            tmp = true;
            oBar.notify();


        }

    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {

            // printBar.run() outputs "bar". Do not change or remove this line.

            if (!tmp) {
                oBar.wait();
                oFoo.notify();
            }
            printBar.run();

            tmp = false;

            oFoo.notify();


        }
    }

    public static void main(String[] args) throws Exception {
        FooBar fooBar = new FooBar(1000220);

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
