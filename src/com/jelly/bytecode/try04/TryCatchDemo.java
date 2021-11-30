package com.jelly.bytecode.try04;

/**
 * @author zhangguodong
 * @since 2021/11/30 16:51
 */
public class TryCatchDemo {
    public void foo() {
        try {
            tryItOut1();
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        System.out.println("handleException");
    }

    private void tryItOut1() {
        System.out.println("tryItOut1");
    }
}
