package com.jelly.bytecode.i03;

/**
 * @author zhangguodong
 * @since 2021/11/30 12:09
 */
public class Iadd2Demo {
    public static void foo() {
        int i = 0;
        for (int j = 0; j < 50; j++) {
            i = ++i;
        }
        System.out.println(i);
    }

    public static void main(String[] args) {
        foo();
    }
}
