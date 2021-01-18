package com.disruptor.bhz.threadpoolv1.test;

import com.disruptor.bhz.threadpoolv1.RingExecutorService;
import com.disruptor.bhz.threadpoolv1.core.ConnectorBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test {


    @Test //448 398 413 433 383
    public void testDefault() throws Exception {
        ExecutorService ringExecutorService = new RingExecutorService();
        callTestCode1(ringExecutorService);
        ringExecutorService.shutdown();
    }

    @Test //394  334  338 356 390
    public void testSingleProducer() throws Exception {
        ExecutorService ringExecutorService = new RingExecutorService(true,65536,8,8192);
        callTestCode1(ringExecutorService);
        ringExecutorService.shutdown();
    }

    @Test //276 284 275 296 274
    public void testFast() throws Exception {
        ExecutorService ringExecutorService = new RingExecutorService(
                ConnectorBuilder.newBuilder()
                        .createConnector("connector")
                        .isSingleProducer()
                        .directConnect().connectAcceptor("acceptor",65536,8)
                        .sequenceDistributor()
                        .runWhithDirectActuator().build()
        );
        callTestCode1(ringExecutorService);
        ringExecutorService.shutdown();
    }

    @Test //275 285 277 279 296
    public void testFastUsePool() throws Exception {
        ExecutorService ringExecutorService = new RingExecutorService(
                ConnectorBuilder.newBuilder()
                        .createConnector("connector")
                        .isSingleProducer()
                        .directConnect().connectAcceptor("acceptor",65536,4)
                        .sequenceDistributor()
                        .runWhithDirectActuator().usePool(2)
                        .build()
        );
        callTestCode1(ringExecutorService);
        ringExecutorService.shutdown();
    }



    @Test //726  730  740  729 726
    public void testJdk1() throws Exception {
        ExecutorService jdkExecutorService = Executors.newFixedThreadPool(8);
        callTestCode1(jdkExecutorService);
        Thread.sleep(1000L);
        jdkExecutorService.shutdown();
    }

    @Test //330  347  366  416 398
    public void testJdk2() throws Exception {
        ExecutorService jdkExecutorService = Executors.newWorkStealingPool(8);
        callTestCode1(jdkExecutorService);
        Thread.sleep(1000L);
        jdkExecutorService.shutdown();
    }

    @Test //264 260 259 272 275
    public void testCall(){
        final long time= System.currentTimeMillis();
        for(int i=1;i<999999;i++) {
            final String idx = String.valueOf(i);
            final int count = i;
            String aa = "idx="+idx+"currentThread=" + Thread.currentThread() + " costtime=" + (System.currentTimeMillis() - time);
            int b = aa.length();
            if(count==999998){
                System.out.println(b+"idx="+idx+"currentThread=" + Thread.currentThread() + " costtime=" + (System.currentTimeMillis() - time));
            }
        }
    }


    public void callTestCode1(ExecutorService es){
        final long time= System.currentTimeMillis();
        for(int i=1;i<999999;i++) {
            final String idx = String.valueOf(i);
            final int count = i;
            es.execute(() -> {
                String aa = "idx="+idx+"currentThread=" + Thread.currentThread() + " costtime=" + (System.currentTimeMillis() - time);
                int b = aa.length();
                if(count==999998){
                    System.out.println(b+"idx="+idx+"currentThread=" + Thread.currentThread() + " costtime=" + (System.currentTimeMillis() - time));
                }
            });
        }
    }
}
