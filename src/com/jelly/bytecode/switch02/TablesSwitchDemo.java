package com.jelly.bytecode.switch02;

/**
 * @author ：zhang guo dong
 * @since 2021/11/24 15:52
 */
public class TablesSwitchDemo {
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
