package com.jelly.bytecode.for01;

/**
 * @author zhangguodong
 * @since 2021/11/24 15:48
 */
public class ForeachDemo {
    public void printNum(Integer i) {
        System.out.println(i);
    }

    public static void main(String[] args) {
        int[] numbers = new int[]{1, 2, 3};
        ForeachDemo forDemo = new ForeachDemo();
        for (int number : numbers) {
            forDemo.printNum(number);
        }
    }
}
