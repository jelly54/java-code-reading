package com.jelly.thread.activeObject2;

/**
 * 若方法不符合则其被转换为 Active 方法时会抛出该异常
 *
 * @author : zhangguodong
 * @since : 2022/10/17 15:04
 */
public class IllegalActiveMethod extends Exception {

    public IllegalActiveMethod(String message) {
        super(message);
    }
}
