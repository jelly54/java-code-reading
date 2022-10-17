package com.jelly.thread.activeObject2;

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
            ActiveMessage methodMessage = this.queue.take();
            methodMessage.execute();
        }
    }
}