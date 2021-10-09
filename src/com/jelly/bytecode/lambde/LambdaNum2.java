package com.jelly.bytecode.lambde;

/**
 * @author ：zhang guo dong
 * @date ：Created in 2021/3/30 下午9:10
 */
public class LambdaNum2 {

    public void tt() {
        Runnable r1 = () -> {
            System.out.println("hello, lambda");
        };
        r1.run();

        Runnable r2 = () -> {
            System.out.println("hello, lambda");
        };
        r2.run();
    }
}
