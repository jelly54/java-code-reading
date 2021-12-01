package com.jelly.bytecode.synchronized05;

/**
 * @author zhangguodong
 * @since 2021/11/30 17:23
 */
public class SynchronizedDemo {
    private Object lock = new Object();
    public void foo() {
        synchronized (lock) {
            bar();
        }
    }
    public void bar() { }
}
