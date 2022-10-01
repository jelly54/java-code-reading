package com.jelly.thread.observable;

/**
 * @author : zhangguodong
 * @since : 2022/10/1 15:39
 */
@FunctionalInterface
public interface Task<T> {
    /**
     * 任务执行接口，该接口允许有返回值
     *
     * @return T
     */
    T call();
}
