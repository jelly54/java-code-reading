package com.jelly.bytecode.try04;

/**
 * @author zhangguodong
 * @since 2021/11/30 16:51
 */
public class TryTwoCatchDemo {
    public void foo() {
        try {
            tryItOut1();
        } catch (RuntimeException e) {
            handleException(e);
        } catch (Exception e) {
            handleException2(e);
        }
    }

    private void handleException2(Exception e) {
        System.out.println("handleException");
    }

    private void handleException(RuntimeException e) {
        System.out.println("handleRuntimeException");
    }

    private void tryItOut1() {
        System.out.println("tryItOut1");
    }
}
