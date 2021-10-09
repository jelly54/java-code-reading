package com.jelly.lambde;

/**
 * @author ：zhang guo dong
 * @date ：Created in 2021/3/30 下午7:08
 */
public class InnerClass {
    Runnable r = new Runnable() {
        @Override
        public void run() {
            System.out.println("runnable run");
        }
    };
}
