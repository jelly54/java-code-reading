package com.jelly.thread.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 多个线程执行自己的任务，执行到某个节点是阻塞等待其他线程也执行到这里，
 * 当所有子线程都执行到某个节点之后，多个子线程继续执行每个线程自己的后续内容
 *
 * @author zhangguodong
 * @since 2022/1/29 09:31
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        int subTaskCount = 4;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(subTaskCount);
        for (int i = 0; i < subTaskCount; i++) {
            threadInstance(cyclicBarrier, "Thread-" + i).start();
        }

        System.out.println(Thread.currentThread().getName() + " 任务下发完成");

    }

    private static Thread threadInstance(CyclicBarrier cyclicBarrier, String name) {
        return new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " 开始执行");
                TimeUnit.SECONDS.sleep(4);
                System.out.println(Thread.currentThread().getName() + " 执行完成，等待其他线程");

                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + " 执行下一个阶段");
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        }, name);
    }
}
