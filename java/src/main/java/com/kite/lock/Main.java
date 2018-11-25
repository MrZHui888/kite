package com.kite.lock;

/**
 * @author : Guzh
 * @since : 2018/11/25
 */
public class Main {

    private int value;

    private MyLock2 lock2 = new MyLock2();

    // 看看是否可以重入
    public int getValue() {
        lock2.lock();
        int result = 0;
        try {
            Thread.sleep(10);
            result = value++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock2.unlock();
        }
        return result;

    }

    public void a() {
        lock2.lock();
        System.out.println("a");
        b();
        lock2.unlock();
    }

    private void b() {
        lock2.lock();
        System.out.println("b");
        lock2.unlock();
    }


    public static void main(String[] args) {
        Main main = new Main();
        main.a();


//        new Thread(() -> {
//            while (true) {
//                System.out.println(Thread.currentThread().getName() + "  :" + main.getValue());
//            }
//        }).start();
//
//        new Thread(() -> {
//            while (true) {
//                System.out.println(Thread.currentThread().getName() + "  :" + main.getValue());
//            }
//        }).start();
    }
}
