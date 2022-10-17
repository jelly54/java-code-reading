package com.jelly.thread.activeObject;

/**
 * ActiveDaemonThread是一个守护线程，主要是从 queue 中获取 Message 然后执行execute 方法
 * （注意：保持为线程命名的习惯是一个比较好的编程习惯）
 *
 * @author : zhangguodong
 * @since : 2022/10/17 14:18
 */
public class ActiveDaemonThread extends Thread {
    private final ActiveMessageQueue queue;

    public ActiveDaemonThread(ActiveMessageQueue queue) {
        super("ActiveDaemonThread");
        this.queue = queue;
        // 守护线程
        setDaemon(true);
    }

    @Override
    public void run() {
        for (; ; ) {
            //  从MethodMessage 队列中获取一个 MethodMessage 然后执行 execute 方法
            MethodMessage methodMessage = this.queue.take();
            methodMessage.execute();
        }
    }
}
