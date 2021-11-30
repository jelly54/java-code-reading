package com.jelly.bytecode.try04;

/**
 * @author zhangguodong
 * @since 2021/11/30 16:51
 */
public class TryCatchFinallyDemo {
    public void foo() {
        try {
            tryItOut1();
        } catch (RuntimeException e) {
            handleException(e);
        } finally {
            handleFinally();
        }
    }

    private void handleFinally() {
        System.out.println("handleFinally");
    }

    private void handleException(RuntimeException e) {
        System.out.println("handleRuntimeException");
    }

    private void tryItOut1() {
        System.out.println("tryItOut1");
    }
}
