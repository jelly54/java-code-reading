package com.jelly.thread.observable;

/**
 * @author : zhangguodong
 * @since : 2022/10/1 10:34
 */
public interface Observable {
    enum Cycle {
        STARTED, RUNNING, DONE, ERROR;
    }

    // 获取当前任务的生命周期状态
    Cycle getCycle();

    // 定义启动线程的方法，主要作用时为了屏蔽 Thread 的其他方法
    void start();

    // 定义现成打断方法，作用与 start 方法一样，也是为了屏蔽 Thread 的其他方法
    void interrupt();
}
