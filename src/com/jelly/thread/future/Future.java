package com.jelly.thread.future;

/**
 * @author : zhangguodong
 * @since : 2022/10/2 15:11
 */
public interface Future<T> {

    /**
     * 返回计算后的结果，该方法会陷入阻塞状态
     *
     * @return 计算结果
     * @throws InterruptedException 被打断异常
     */
    T get() throws InterruptedException;

    /**
     * 判断任务是否已经执行完成
     *
     * @return 是否完成
     */
    boolean done();
}
