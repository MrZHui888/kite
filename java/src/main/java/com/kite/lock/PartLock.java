package com.kite.lock;

/**
 * @author : Guzh
 * @since : 2018/11/16
 * 可重入锁
 */
public class PartLock {


    private synchronized void partOne() {
        System.out.println("现在进入 one");

    }


    private synchronized void partTWo() {
        System.out.println("现在进入  two");
    }


    public synchronized void part() {
        System.out.println("现在进入  ");

        this.partOne();

        this.partTWo();
    }


    public static void main(String[] args) {
        new PartLock().part();
    }

}
