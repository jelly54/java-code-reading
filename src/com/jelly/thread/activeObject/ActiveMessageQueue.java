package com.jelly.thread.activeObject;

import java.util.LinkedList;

/**
 * @author : zhangguodong
 * @since : 2022/10/17 14:11
 */
public class ActiveMessageQueue {
    private final LinkedList<MethodMessage> messages = new LinkedList<>();

    /**
     * 在创建 ActiveMessageQueue 的同时启动 ActiveDaemonThread 线程，ActiveDaemon-Thread 主要用来进行异步的方法执行
     */
    public ActiveMessageQueue() {
        // 启动 Worker 线程
        new ActiveDaemonThread(this).start();
    }

    /**
     * 执行 offer 方法没有进行 limit 的判断，允许提交无限个 MethodMessage（直到发生堆内存溢出)，
     * 并且当有新的 Message 加入时会通知 ActiveDaemonThread 线程。
     *
     * @param methodMessage mm
     */
    public void offer(MethodMessage methodMessage) {
        synchronized (this) {
            messages.addLast(methodMessage);

            // 因为只有一个线程负责 take 数据，因此没有必要使用 notifyAll 方法
            this.notify();
        }
    }

    /**
     * take 方法主要是被 ActiveDaemonThread 线程使用，当 message 队列为空时 Active-DaemonThread 线程将会被挂起(Guarded Suspension)
     *
     * @return mm
     */
    protected MethodMessage take() {
        synchronized (this) {
            // 当 MethodMessage 队列中没有 Message 的时候，执行线程进入阻塞
            while (messages.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 获取其中一个 MethodMessage 并且从队列中移除
            return messages.removeFirst();
        }
    }
}
