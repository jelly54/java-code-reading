package com.jelly.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author : zhangguodong
 * @since : 2022/9/20 14:05
 */
public class UncaughtExceptionDemo {

    public static void main(String[] args) {
        // 设置接口回调函数
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.print("thread [" + t.getName() + "] has error: " + e.getMessage() + " \n");
            e.printStackTrace();
        });

        // 启动一个子线程
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 出现 unchecked 异常
            System.out.println(1 / 0);
        }, "subThread").start();
    }
}
