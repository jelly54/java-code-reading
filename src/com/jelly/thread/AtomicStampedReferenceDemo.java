package com.jelly.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author ：zhangguodong
 * @since ：2021/10/10 15:44
 */
public class AtomicStampedReferenceDemo {
    private static AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<>(1, 0);

    public static void main(String[] args) {

        // 开启两个线程
        // 第一个线程先获取状态标记(stamp)、打印状态，然后sleep1.5s，之后尝试使用线程1的状态标记(stamp)将数据1 cas 到 2
        // 第二个线程，在sleep 1s后开启第二个线程，先将数据自增加一，然后自减减一
        // 第一个线程，在sleep 1.5s期间，第二个线程就执行完了，然后第一个线程尝试用最开始获取的标记(stamp)将数据1 cas 到 2
        new Thread(() -> {
            System.out.println("操作线程" + Thread.currentThread() + ",初始值 a = " + atomicStampedRef.getReference());
            int stamp = atomicStampedRef.getStamp(); //获取当前标识别
            try {
                Thread.sleep(1500); //等待1秒 ，以便让干扰线程执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean isCASSuccess = atomicStampedRef.compareAndSet(1, 2, stamp, stamp + 1);  //此时expectedReference未发生改变，但是stamp已经被修改了,所以CAS失败
            System.out.println("操作线程" + Thread.currentThread() + ",CAS操作结果: " + isCASSuccess);
        }, "主操作线程").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            int stamp = atomicStampedRef.getStamp(); //获取当前标识别
            atomicStampedRef.compareAndSet(1, 2, stamp, stamp + 1);
            System.out.println("操作线程" + Thread.currentThread() + ",【increment】 ,值 = " + atomicStampedRef.getReference());

            int newStamp = atomicStampedRef.getStamp(); //获取当前标识别
            atomicStampedRef.compareAndSet(2, 1, newStamp, newStamp + 1);
            System.out.println("操作线程" + Thread.currentThread() + ",【decrement】 ,值 = " + atomicStampedRef.getReference());
        }, "干扰线程").start();

    }
}
