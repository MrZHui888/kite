package com.kite.gc;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.List;

/**
 * @author : Guzh
 * @since : 2018/11/12
 */
public class STWDemo {
    public static class MyThread extends Thread {
        HashMap hashMap = new HashMap();

        @Override
        public void run() {
            try {
                while (true) {
                    if (hashMap.size() * 512 / 1024 / 1024 >= 400) {
                        hashMap.clear();

                        System.out.println("clean map");
                    }

                    byte[] b1;

                    for (int i = 0; i < 100; i++) {
                        b1 = new byte[512];
                        hashMap.put(System.nanoTime(), b1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class PrintThread extends Thread {
        public final static long startTime = System.currentTimeMillis();

        @Override
        public void run() {

            try {
                while (true) {
                    long t = System.currentTimeMillis() - startTime;
                    System.out.println(t / 1000 + "." + t % 1000);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static class GCDemo extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory
                            .getGarbageCollectorMXBeans();
                    for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
                        String metricName = garbageCollectorMXBean.getName();

                        System.out.println(metricName + "_Count:  " + garbageCollectorMXBean.getCollectionCount() + ",   " + metricName
                                + "_Time: " + garbageCollectorMXBean.getCollectionTime());

                    }

                    OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
                    System.out.println("System_Load_Avg :" + operatingSystemMXBean.getSystemLoadAverage());

                    Thread.sleep(1000);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();

        PrintThread printThread = new PrintThread();

        GCDemo gcDemo = new GCDemo();

        myThread.start();

        printThread.start();

        gcDemo.start();

    }

}
