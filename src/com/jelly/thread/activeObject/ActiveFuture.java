package com.jelly.thread.activeObject;

import com.jelly.thread.future.FutureTask;

/**
 * @author : zhangguodong
 * @since : 2022/10/17 13:26
 */
public class ActiveFuture<T> extends FutureTask<T> {

    @Override
    public void finish(T result) {
        super.finish(result);
    }
}
