package com.jelly.bytecode.switch02;

/**
 * @author zhangguodong
 * @since 2021/11/24 15:52
 */
public class LookupSwitchDemo {
    public static void main(String[] args) {
        int i = chooseNear(100);
        System.out.println(i);
    }

    public static int chooseNear(int i) {
        switch (i) {
            case 1:
                return 1;
            case 10:
                return 10;
            case 100:
                return 100;
            default:
                return -1;
        }
    }
}
