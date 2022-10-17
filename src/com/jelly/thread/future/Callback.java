package com.jelly.thread.future;

/**
 * @author : zhangguodong
 * @since : 2022/10/17 13:19
 */
@FunctionalInterface
public interface Callback<T> {

    /**
     * 任务完成后会调用该方法
     *
     * @param t 任务执行后的结果
     */
    void call(T t);
}
