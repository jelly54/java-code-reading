package com.jelly.thread;

import java.util.concurrent.TimeUnit;

/**
 * “lock；〞前缀实际上相当于是一个内存屏障，该内存屏障会为指令的执行提供如下几个保障。
 * 口 确保指令重排序时不会将其后面的代码排到内存屏障之前。
 * 口 确保指令重排序时不会将其前面的代码排到内存屏障之后。
 * 口 确保在执行到内存屏障修饰的指令时前面的代码全部执行完成。
 * 口 强制将线程工作内存中值的修改刷新至主内存中。
 * 口 如果是写操作，则会导致其他线程工作内存(CPU Cache） 中的缓存数据失效。
 * <p>
 * 虽然 volatile 有部分 synchronized 关键字的语义，但不能完全替代 synchronized 关键字，因为 volatile 关键字不具备原子性操作
 * volatile 关键字的时候也是充分利用它的可见性以及有序性（防止重排序)特点。
 * <p>
 * <p>
 * 通过对 volatile 关键字的学习和之前对 synchronized 关键字的学习，我们在这里总结一下两者之间的区别。
 * (1）使用上的区别
 * 口 volatile 关键字只能用于修饰实例变量或者类变量，不能用于修饰方法以及方法参数和局部变量、常量等。
 * 口 synchronized 关键字不能用于对变量的修饰，只能用于修饰方法或者语句块。
 * 口 volatile 修饰的变量可以为 null， synchronized 关键字同步语句块的 monitor 对象不能为 null。
 * <p>
 * （2）对原子性的保证
 * 口 volatile 无法保证原子性。
 * 口 由于 synchronized 是一种排他的机制，因此被 synchronized 关键字修饰的同步代码是无法被中途打断的，因此其能够保证代码的原子性。
 * <p>
 * （3）对可见性的保证
 * 口 两者均可以保证共享资源在多线程间的可见性，但是实现机制完全不同。
 * 口 synchronized 借助于 JVM指令 monitor enter 和 monitor exit 对通过排他的方式使得同步代码串行化，在 monitor exit 时所有共享资源都将会被刷新到主内存中。
 * 口 相比较于 synchronized 关键字 volatile 使用机器指令（偏硬件）“lock；”的方式迫使其他线程工作内存中的数据失效，不得到主内存中进行再次加载。
 * <p>
 * （4）对有序性的保证
 * 口 volatile 关键字禁止JVM编译器以及处理器对其进行重排序，所以它能够保证有序性。
 * 口 虽然 synchronized 关键字所修饰的同步方法也可以保证顺序性，但是这种顺序性是以程序的串行化执行换来的，
 * 在 synchronized 关键字所修饰的代码块中代码指令也会发生指令重排序的情况，比如：
 * # synchronized(this){
 * #    int x = 10;
 * #    int Y= 20;
 * #    x++;
 * #    y = y+1;
 * # }
 * x 和y谁最先定义以及谁最先进行运算，对程序来说没有任何的影响，另外×和y之间也没有依赖关系，但是由于 synchronized 关键字同步的作用，
 * 在 synchronized 的作用域结束时 x必定是11，y必定是21，也就是说达到了最终的输出结果和代码编写顺序的一致性。
 * <p>
 * （5）其他
 * 口 volatile 不会使线程陷入阻塞。
 * 口 synchronized 关键字会使线程进人阻塞状态。
 *
 * @author : zhangguodong
 * @since : 2022/9/22 15:59
 */
public class VolatileDemo {

    final static int MAX = 5;
    static volatile int int_value = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            int localValue = int_value;
            while (localValue < MAX) {
                if (int_value != localValue) {
                    System.out.printf("The int_value is update for [%d]\n", int_value);
                    localValue = int_value;
                }
            }
        }, "create-thread").start();

        new Thread(() -> {
            int localValue = int_value;
            while ((localValue < MAX)) {
                System.out.printf("The int_value will be changed [%d]\n", ++localValue);
            }
            int_value = localValue;

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "update-thread").start();
    }
}
