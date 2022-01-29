package com.jelly.thread.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量，多个线程共同争夺n个信号量，只有获取到信号量的线程才可以执行任务，否则将会阻塞
 *
 * @author zhangguodong
 * @since 2022/1/29 12:00
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        int subTaskCount = 3;
        Semaphore semaphore = new Semaphore(subTaskCount);
        for (int i = 0; i < subTaskCount * 2; i++) {
            threadInstance(semaphore, "Thread-" + i).start();
        }

        System.out.println("主线程执行完成");
    }

    private static Thread threadInstance(Semaphore semaphore, String name) {
        return new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " 尝试获取信号量，当前剩余：" + semaphore.availablePermits());
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " 获取信号量成功，开始执行任务");

                TimeUnit.SECONDS.sleep(4);
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + " 执行完成，释放信号量");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, name);
    }
}
