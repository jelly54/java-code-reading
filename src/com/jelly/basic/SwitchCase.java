package com.jelly.basic;

/**
 * @author zhangguodong
 * @since 2021/10/24 16:42
 */
public class SwitchCase {
    /**
     * switch 支持byte、char、short、int
     * 支持 enum、string
     * <p>
     * 不支持 long、double
     */
    public void typeCase() {
        byte byteType = 96;
        switch (byteType) {
            case 95:
                System.out.println("is byte type: 95");
                break;
            case 96:
                System.out.println("is byte type: 96");
                break;
            default:
                break;
        }


        char charType = 'a';
        switch (charType) {
            case 'a':
                System.out.println("is char type: a");
                break;
            case 'b':
                System.out.println("is char type: b");
                break;
            default:
                break;
        }
        //////// 编译错误
//        long longType = 89l;
//        switch (longType) {
//            case 89l:
//                System.out.println("is long type: a");
//                break;
//            default:
//                break;
//        }
        //// 编译错误
//        double doubleType = 89d;
//        switch (doubleType) {
//            case 89l:
//                System.out.println("is long type: a");
//                break;
//            default:
//                break;
//        }
    }

    public static void main(String[] args) {
        SwitchCase sc = new SwitchCase();
        sc.typeCase();
    }
}
