package com.thread.arrange.async.callback;


import com.thread.arrange.async.wrapper.WorkerWrapper;

import java.util.List;

/**
 * @author gzh  on 2019-12-27
 * @version 1.0
 */
public class DefaultGroupCallback implements com.thread.arrange.async.callback.IGroupCallback {
    @Override
    public void success(List<WorkerWrapper> workerWrappers) {

    }

    @Override
    public void failure(List<WorkerWrapper> workerWrappers, Exception e) {

    }
}
