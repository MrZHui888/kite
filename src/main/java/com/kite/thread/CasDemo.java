package com.kite.thread;

/**
 * @author : Guzh
 * @since : 2018/11/22
 */
public class CasDemo {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static void main(String[] args) {
        CasDemo casDemo=new CasDemo();
        new Thread(()->{
            int i=0;
            while (i<100){
                casDemo.setCount(i);
                i++;
            }
        }).start();

        new Thread(()->{
            int i=0;
            while (i<100){
                casDemo.getCount();
                i++;
            }
        }).start();


    }
}
