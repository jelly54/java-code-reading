package com.jelly.bytecode.try04;

/**
 * @author zhangguodong
 * @since 2021/11/30 17:13
 */
public class FinallyReturnDemo {
    public static int foo() {
        int x = 0;
        try {
            return x;
        } finally {
            ++x;
        }
    }

    public static void main(String[] args) {
        int res = foo();
        System.out.println(res);
    }
}
