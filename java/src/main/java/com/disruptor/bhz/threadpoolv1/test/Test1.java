package com.disruptor.bhz.threadpoolv1.test;

import com.disruptor.bhz.threadpoolv1.RingExecutorService;
import com.disruptor.bhz.threadpoolv1.core.ConnectorBuilder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Test1 {

    private static ExecutorService executorService;


    public static void main(String[] args) throws Exception {
        long time = System.currentTimeMillis();
        System.out.println("开始jdk ");
        initJDKEs();

        for (int i = 0; i < 5; i++) {
            cycleCall(executorService);
        }
        long endTime = System.currentTimeMillis();

        System.out.println("jdk结束 cost:  " + (endTime - time));
        System.out.println("avg  :" + (endTime - time) / 5);

        long time1 = System.currentTimeMillis();
        System.out.println("开始ring ");
        initRingEs();

        for (int i = 0; i < 5; i++) {
            cycleCall(executorService);
        }

        long endTime1 = System.currentTimeMillis();
        System.out.println("ring结束 cost:  " + (endTime1 - time1));
        System.out.println("avg  :" + (endTime1 - time1) / 5);
    }


    private static void initRingEs() {
        executorService = new RingExecutorService(
                ConnectorBuilder.newBuilder()
                        .createConnector("connector")
//                        .isSingleProducer()
                        .isMultipleProducer()
                        .directConnect().connectAcceptor("acceptor", 65536, 5)
                        .sequenceDistributor()
                        .runWhithDirectActuator().usePool(20)
                        .build());

    }


    private static void initJDKEs() throws Exception {

//        executorService = Executors.newWorkStealingPool(100);
        executorService = new ThreadPoolExecutor(100, 100, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10000000),
                new ThreadFactoryBuilder().setNameFormat("operator-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());

    }


    public static void cycleCall(ExecutorService es) {
        final long time = System.currentTimeMillis();

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10000000; i++) {
            list.add(i);
        }

        List<Future<String>> list1 = list.stream().map(iterm -> CompletableFuture.supplyAsync(() -> {
            if (iterm.equals(Integer.valueOf(9999998))) {
                System.out.println(Thread.currentThread().getName() + "  idx:" + iterm + " cost:" + (System.currentTimeMillis() - time));
            }
            return "";
        }, es)).collect(Collectors.toList());


        CompletableFuture.allOf(list1.toArray(new CompletableFuture[list1.size()])).join();


    }
}
