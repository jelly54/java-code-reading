package com.jelly.bytecode.loop;

/**
 * @author ：zhang guo dong
 * @date ：Created in 2021/3/29 下午5:08
 */
public class SwitchTest {
    public static void main(String[] args) {
        int i = chooseNear(200);
        System.out.println(i);
    }

    public static int chooseNear(int i) {
        switch (i) {
            case 100:
                return 0;
            case 101:
                return 1;
            case 104:
                return 4;
            default:
                return -1;
        }
    }
}
