package com.disruptor.bhz.threadpoolv1.test;

import com.disruptor.bhz.threadpoolv1.RingExecutorService;
import com.disruptor.bhz.threadpoolv1.core.Connector;
import com.disruptor.bhz.threadpoolv1.core.ConnectorBuilder;
import org.apache.commons.lang3.RandomUtils;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) throws Exception {
        Test test = new Test();


        for (int i = 0; i < 10; i++) {
            test.testJdk2();
        }
    }

    public void testDefault() throws Exception {
        ExecutorService ringExecutorService = new RingExecutorService();
        callTestCode1(ringExecutorService);
        ringExecutorService.shutdown();
    }

    public void testSingleProducer() throws Exception {
        ExecutorService ringExecutorService = new RingExecutorService(true, 65536, 8, 8192);
        callTestCode1(ringExecutorService);
        ringExecutorService.shutdown();
    }

    public void testFast() throws Exception {
        Connector connector = ConnectorBuilder.newBuilder()
                .createConnector("connector")
//                .isSingleProducer()
                .isMultipleProducer()
                .directConnect().connectAcceptor("acceptor", 65536, 100)
                .sequenceDistributor()
                .runWhithDirectActuator().build();

        ExecutorService ringExecutorService = new RingExecutorService(connector
        );
        cycleCall(ringExecutorService);
        ringExecutorService.shutdown();
    }

    public void testFastUsePool() throws Exception {
        ExecutorService ringExecutorService = new RingExecutorService(
                ConnectorBuilder.newBuilder()
                        .createConnector("connector")
                        .isSingleProducer()

                        .directConnect().connectAcceptor("acceptor", 65536, 10)
                        .sequenceDistributor()
                        .runWhithDirectActuator().usePool(10)
                        .build()
        );
        cycleCall(ringExecutorService);
        ringExecutorService.shutdown();
    }


    public void testJdk1() throws Exception {
        ExecutorService jdkExecutorService = Executors.newFixedThreadPool(100);
        cycleCall(jdkExecutorService);
        Thread.sleep(1000L);
        jdkExecutorService.shutdown();
    }

    public void testJdk2() throws Exception {
        ExecutorService jdkExecutorService = Executors.newWorkStealingPool(100);
//        callTestCode1(jdkExecutorService);
        cycleCall(jdkExecutorService);
        Thread.sleep(1000L);
        jdkExecutorService.shutdown();
    }

    public void testCall() throws Exception {
        final long time = System.currentTimeMillis();
        for (int i = 1; i < 1999999; i++) {
            final String idx = String.valueOf(i);
            final int count = i;
            String aa = "idx=" + idx + "currentThread=" + Thread.currentThread() + " costtime=" + (System.currentTimeMillis() - time);
            int b = aa.length();
            if (count == 1999998) {
                System.out.println(b + "idx=" + idx + "currentThread=" + Thread.currentThread() + " costtime=" + (System.currentTimeMillis() - time));
            }
            Thread.sleep(RandomUtils.nextInt(0, 10));
        }
    }


    public void callTestCode1(ExecutorService es) {
        final long time = System.currentTimeMillis();
        for (int i = 1; i < 1999999; i++) {
            final String idx = String.valueOf(i);
            final int count = i;
            es.execute(() -> {
                String aa = "idx=" + idx + "currentThread=" + Thread.currentThread() + " costtime=" + (System.currentTimeMillis() - time);
                int b = aa.length();
                if (count == 1999998) {
                    System.out.println(b + "idx=" + idx + "currentThread=" + Thread.currentThread() + " costtime=" + (System.currentTimeMillis() - time));
                }
            });
        }
    }


    public void cycleCall(ExecutorService es) {
        final long time = System.currentTimeMillis();
        for (int i = 1; i < 1999999; i++) {
            final String idx = String.valueOf(i);
            final int count = i;
            es.execute(() -> {
                String aa = "idx=" + idx + "currentThread=" + Thread.currentThread() + " costtime=" + (System.currentTimeMillis() - time);
                int b = aa.length();
                if (count == 1999998) {
                    System.out.println(b + "idx=" + idx + "currentThread=" + Thread.currentThread() + " costtime=" + (System.currentTimeMillis() - time));
                }
            });
        }

    }
}
