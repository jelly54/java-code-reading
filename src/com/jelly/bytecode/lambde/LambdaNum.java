package com.jelly.bytecode.lambde;

/**
 * @author ：zhang guo dong
 * @date ：Created in 2021/3/30 下午9:02
 */
public class LambdaNum {
    public void test(){
        for (int i = 0; i < 10; i++) {
            Runnable r = () -> {
                System.out.println("hello, lambda");
            };
            r.run();
        }
    }
}
