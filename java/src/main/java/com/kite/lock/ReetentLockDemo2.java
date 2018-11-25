package com.kite.lock;

/**
 * @author : Guzh
 * @since : 2018/11/24
 * lock 是否可重入
 */
public class ReetentLockDemo2 {

    private MyLock lock = new MyLock();

    public void a() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " 进入A方法");
        b();
        lock.unlock();
    }

    public void b() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " 进入B方法");
        lock.unlock();
    }


    public static void main(String[] args) {
        ReetentLockDemo2 demo2 = new ReetentLockDemo2();

        new Thread(new Runnable() {
            @Override
            public void run() {
                demo2.a();
            }
        }).start();



    }


}
