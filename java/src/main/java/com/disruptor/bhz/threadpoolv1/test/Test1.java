package com.disruptor.bhz.threadpoolv1.test;

import com.disruptor.bhz.threadpoolv1.RingExecutorService;
import com.disruptor.bhz.threadpoolv1.core.ConnectorBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class Test1 {

    private static ExecutorService executorService;


    public static void main(String[] args) throws Exception {
        System.out.println("开始ring ");
        initRingEs();
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            cycleCall();
        }

        long endTime1 = System.currentTimeMillis();
        System.out.println("ring结束 cost:  " + (endTime1 - time1));
        System.out.println("avg  :" + (endTime1 - time1) / 5);



//        System.out.println("开始jdk ");
//        initJDKEs();
//
//        long time = System.currentTimeMillis();
//        for (int i = 0; i < 5; i++) {
//            cycleCall();
//        }
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("jdk结束 cost:  " + (endTime - time));
//        System.out.println("avg  :" + (endTime - time) / 5);


        executorService.shutdown();
        executorService.shutdownNow();


    }


    private static void initRingEs() {
        executorService = new RingExecutorService(
                ConnectorBuilder.newBuilder()
                        .createConnector("connector")
//                        .isSingleProducer()
                        .isMultipleProducer()
                        .directConnect().connectAcceptor("acceptor", 65536, 1)
                        .sequenceDistributor()
                        .runWhithDirectActuator().usePool(20)
                        .build());

    }


    private static void initJDKEs() throws Exception {

        executorService = Executors.newWorkStealingPool(100);
//        executorService = new ThreadPoolExecutor(100, 100, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10000000),
//                new ThreadFactoryBuilder().setNameFormat("operator-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());

    }


    public static void cycleCall() {
        final long time = System.currentTimeMillis();

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 100000; i++) {
            list.add(i);
        }

        List<Future<String>> list1 = list.stream().map(iterm -> CompletableFuture.supplyAsync(() -> {
            if (iterm.equals(Integer.valueOf(99998)) || iterm.equals(Integer.valueOf(1000))) {
                System.out.println(Thread.currentThread().getName() + "  idx:" + iterm + " cost:" + (System.currentTimeMillis() - time));
            }
            return "";
        }, executorService)).collect(Collectors.toList());


        CompletableFuture.allOf(list1.toArray(new CompletableFuture[list1.size()])).join();
        list = null;
        list1 = null;


    }
}
