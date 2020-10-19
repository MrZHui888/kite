package com.jmm;


/**
 * 线程可见性的问题
 * 其他线程 修改了共享变量 initFlag
 */
public class VolatileVisibilitySample {

    private volatile boolean initFlag = false;

//    private static final Object lockObj = new Object();


    public void refresh() {
        this.initFlag = true;
        String threadName = Thread.currentThread().getName();
        System.out.println("线程" + threadName + ";修改了共享变量的initFlag");
    }


    public void load() {
        String threadName = Thread.currentThread().getName();

        int i =0;
        while (!initFlag) {
            /*synchronized(lockObj){
              // 新的知识点 synchroized 引起了线程的上下文的切换, 各个线程的数据刷回主存

                System.out.println(i++);
            }*/
        }
        System.out.println("线程" + threadName + ";嗅探到共享变量的initFlag的状态的改变");

    }

    public static void main(String[] args) {
        VolatileVisibilitySample sample = new VolatileVisibilitySample();


        Thread threadA = new Thread(() -> {
            sample.refresh();
        }, " ThreadA");


        Thread threadB = new Thread(() -> {
            sample.load();
        }, "ThreadB");


        threadB.start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        threadA.start();


    }

}
