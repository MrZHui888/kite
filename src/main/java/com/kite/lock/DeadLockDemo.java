package com.kite.lock;

/**
 * @author : Guzh
 * @since : 2018/11/21
 * <p>
 * 死锁测试
 */
public class DeadLockDemo {

    private Object object = new Object();

    private Object object1 = new Object();

    public void a() {
        synchronized (object) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (object1) {
                System.out.println("执行A方法");
            }
        }
    }

    public void b() {
        synchronized (object1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (object) {
                System.out.println("执行b方法");
            }
        }
    }

    public static void main(String[] args) {
        DeadLockDemo deadLockDemo=new DeadLockDemo();

         new Thread(()->deadLockDemo.a()).start();

         new Thread(()->deadLockDemo.b()).start();


         System.out.println();
    }
}
