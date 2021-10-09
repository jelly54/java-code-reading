package com.jelly.bytecode.loop;

/**
 * @author ：zhang guo dong
 * @date ：Created in 2021/3/29 下午5:08
 */
public class SwitchTest {
    public static void main(String[] args) {
        String i = "cc";
        switch (i) {
            case "a":
                System.out.println("case 1");
                break;
            case "aa":
                System.out.println("case 10");
                break;
            case "aaa":
                System.out.println("case 300");
                break;
            case "gg":
                System.out.println("case 100");
                break;
            default:
                System.out.println("case -1");
                break;
        }
    }

//    public void intSwitch() {
//        int i = 30;
//        switch (i) {
//            case 1:
//                System.out.println("case 1");
//                break;
//            case 10:
//                System.out.println("case 10");
//                break;
//            case 300:
//                System.out.println("case 300");
//                break;
//            case 100:
//                System.out.println("case 100");
//                break;
//            default:
//                System.out.println("case -1");
//                break;
//        }
//    }
}
