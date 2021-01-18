package com.disruptor.bhz.threadpoolv1.test;

import com.disruptor.bhz.threadpoolv1.RingExecutorService;
import com.disruptor.bhz.threadpoolv1.core.ConnectorBuilder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class Test1 {

    private static ExecutorService executorService;


    public static void main(String[] args) throws Exception {
        long time = System.currentTimeMillis();
        System.out.println("开始jdk ");
        initJDKEs();

        for (int i = 0; i < 10; i++) {
            cycleCall(executorService);
        }

        System.out.println("jdk结束 cost:  " + (System.currentTimeMillis() - time));


        long time1 = System.currentTimeMillis();
        System.out.println("开始ring ");
        initRingEs();

        for (int i = 0; i < 10; i++) {
            cycleCall(executorService);
        }

        System.out.println("ring结束 cost:  " + (System.currentTimeMillis() - time1));

    }


    private static void initRingEs() {
        executorService = new RingExecutorService(
                ConnectorBuilder.newBuilder()
                        .createConnector("connector")
//                        .isSingleProducer()
                        .isMultipleProducer()
                        .directConnect().connectAcceptor("acceptor", 65536, 2)
                        .sequenceDistributor()
                        .runWhithDirectActuator().usePool(50)
                        .build());

    }


    private static void initJDKEs() throws Exception {
//        executorService =  Executors.newFixedThreadPool(100);

        executorService = new ThreadPoolExecutor(100, 100, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(65536),
                new ThreadFactoryBuilder().setNameFormat("operator-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());

    }


    public static void cycleCall(ExecutorService es) {
        final long time = System.currentTimeMillis();
        for (int i = 1; i < 1999999; i++) {
            final String idx = String.valueOf(i);
            final int count = i;
            es.execute(() -> {
                String aa = "idx=" + idx + "currentThread=" + Thread.currentThread() + " costtime=" + (System.currentTimeMillis() - time);
                int b = aa.length();
                if (count == 1999997) {
                    System.out.println(b + "idx=" + idx + "currentThread=" + Thread.currentThread() + " costtime=" + (System.currentTimeMillis() - time));
                }
            });
        }
    }
}
