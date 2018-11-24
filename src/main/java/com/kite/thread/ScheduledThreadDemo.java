package com.kite.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author : Guzh
 * @since : 2018/11/13
 * 定时任务
 * 大多数的基础信息埋点用的多
 */
public class ScheduledThreadDemo {
    private ScheduledFuture<?> scheduledFuture;

    private ScheduledExecutorService scheduledExecutor;

    public void start() {
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory();
        scheduledExecutor = new ScheduledThreadPoolExecutor(7, namedThreadFactory);
        final Random random = new Random();

        scheduledFuture = scheduledExecutor.scheduleAtFixedRate(() -> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                System.out.print(Thread.currentThread().getName() + "  " + sdf.format(new Date()) + " 开始执行, ");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "  " + sdf.format(new Date()) + "结束执行");

            } catch (Throwable e) {
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void stop() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        if (scheduledExecutor != null) {
            scheduledExecutor.shutdown();
        }
    }

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println(Thread.currentThread().getName() + " 开始" + sdf.format(new Date()));

        ScheduledThreadDemo demo = new ScheduledThreadDemo();
        for (int i = 0; i < 20; i++) {
            demo.start();

            Thread.sleep(1200);

            demo.stop();
        }

        System.out.println(Thread.currentThread().getName() + " 停止" + sdf.format(new Date()));
    }

    class NamedThreadFactory implements ThreadFactory {
        private ThreadFactory target;

        private final ThreadFactoryBuilder builder = new ThreadFactoryBuilder();

        @Override public Thread newThread(Runnable r) {
            if (target == null) {
                synchronized (this) {
                    if (target == null) {
                        target = builder.build();
                    }
                }
            }
            return target.newThread(r);
        }
    }
}
