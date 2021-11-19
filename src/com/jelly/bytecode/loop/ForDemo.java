package com.jelly.bytecode.loop;

/**
 * @author ：zhangguodong
 * @since ：2021/11/16 16:15
 */
public class ForDemo {
    public void printNum(Integer i) {
        System.out.println(i);
    }

    public static void main(String[] args) {
        int[] numbers = new int[]{1, 2, 3};
        ForDemo forDemo = new ForDemo();
        for (int i = 0; i < numbers.length; i++) {
            forDemo.printNum(numbers[i]);
        }
    }
}
