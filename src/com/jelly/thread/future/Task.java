package com.jelly.thread.future;

/**
 * @author : zhangguodong
 * @since : 2022/10/2 15:18
 */
public interface Task<IN, OUT> {
    /**
     * 给定一个参数，经过计算返回结果
     *
     * @param in 入参数
     * @return 返回结果
     */
    OUT get(IN in);
}
