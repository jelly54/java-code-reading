package com.jelly.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author : zhangguodong
 * @since : 2022/9/28 14:28
 */
public class SynchronizedClassDemo {

    public synchronized void method1() {
        System.out.println(Thread.currentThread().getName() + "  enter method1");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "  end method1");
    }

    public synchronized void method2() {
        System.out.println(Thread.currentThread().getName() + "  enter method2");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "  end method2");
    }

    public static void main(String[] args) {
        /**
         * 多线程访问同一个对象中的 多个被 synchronized 修饰的方法时，会锁住同一个 monitor 的 lock
         *
         * synchronized 方法 和 synchronized this 效果一致，都会锁住当前对象的 monitor 的 lock
         */
        SynchronizedClassDemo sd = new SynchronizedClassDemo();
        new Thread(sd::method1, "Thread-1").start();
        new Thread(sd::method2, "Thread-2").start();

        // ####

        /**
         * 多线程访问同一个类中的 多个被 synchronized 修饰的 静态 方法时，会锁住同一个 类对象
         */
    }
}
