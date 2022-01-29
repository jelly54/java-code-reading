package com.jelly.thread.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 多个线程执行自己的任务，主线程阻塞等待
 * 当所有子线程都执行完成之后，主线程继续执行
 *
 * @author zhangguodong
 * @since 2022/1/29 09:16
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        int subTaskCount = 4;
        CountDownLatch downLatch = new CountDownLatch(subTaskCount);
        for (int i = 0; i < subTaskCount; i++) {
            threadInstance(downLatch, "Thread-" + i).start();
        }

        try {
            System.out.println(Thread.currentThread().getName() + " 等待子线程执行完成");
            downLatch.await();
            System.out.println(Thread.currentThread().getName() + " 子线程全部执行完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " all is ok !!!");
    }

    private static Thread threadInstance(CountDownLatch downLatch, String name) {
        return new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start...");
            try {
                TimeUnit.SECONDS.sleep(4);
                System.out.println(Thread.currentThread().getName() + " end.");

                downLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, name);
    }
}
