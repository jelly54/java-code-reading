package com.jelly.thread;

import java.util.concurrent.TimeUnit;

/**
 * 没有设置默认的 Handler，也没有对 thread 指定 Handler，因此 thread 出现异常时，会向上寻找 Group 的 uncaughtException 方法
 * <p>
 * |                             --> System Group --> System.err
 * |             --> Main Group ⬆
 * | 线程出现异常 ⬆
 *
 * @author : zhangguodong
 * @since : 2022/9/20 14:13
 */
public class UncaughtExceptionEmptyDemo {

    public static void main(String[] args) {
        // get current thread's thread group
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        System.out.println(mainGroup.getName());
        System.out.println(mainGroup.getParent());
        System.out.println(mainGroup.getParent().getParent());

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // here will throw unchecked exception
            System.out.println(1 / 0);
        }, "subThread").start();
    }
}
