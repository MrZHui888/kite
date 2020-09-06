package com.thread.arrange.async.callback;


import com.thread.arrange.async.worker.WorkResult;

/**
 * 每个执行单元执行完毕后，会回调该接口</p>
 * 需要监听执行结果的，实现该接口即可
 * @author gzh  on 2019-11-19.
 */
public interface ICallback<T, V> {

    void begin();

    /**
     * 耗时操作执行完毕后，就给value注入值
     *
     */
    void result(boolean success, T param, WorkResult<V> workResult);
}
