package com.thread.arrange.test.parallel;


import com.thread.arrange.async.callback.ICallback;
import com.thread.arrange.async.callback.IWorker;
import com.thread.arrange.async.executor.timer.SystemClock;
import com.thread.arrange.async.worker.WorkResult;

/**
 * @author gzh  on 2019-11-20.
 */
public class ParWorker3 implements IWorker<String, String>, ICallback<String, String> {
    private long sleepTime = 1000;

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }
    @Override
    public String action(String object) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "result = " + SystemClock.now() + "---param = " + object + " from 3";
    }


    @Override
    public String defaultValue() {
        return "worker3--default";
    }

    @Override
    public void begin() {
        //System.out.println(Thread.currentThread().getName() + "- start --" + System.currentTimeMillis());
    }

    @Override
    public void result(boolean success, String param, WorkResult<String> workResult) {
        if (success) {
            System.out.println("callback worker3 success--" + SystemClock.now() + "----" + workResult.getResult()
                    + "-threadName:" +Thread.currentThread().getName());
        } else {
            System.err.println("callback worker3 failure--" + SystemClock.now() + "----"  + workResult.getResult()
                    + "-threadName:" +Thread.currentThread().getName());
        }
    }

}
