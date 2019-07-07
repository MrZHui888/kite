package com.kite.jvm;

public class VolatileTest {
    private static boolean flage = false;

    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            System.out.println("数据准备了");

            while (!flage) {
            }

            System.out.println("数据业务结束");

        }).start();
        Thread.sleep(111);
        new Thread(() -> {
            flage = true;
            System.out.println("设置数据为" + flage);
        }).start();

    }
}
