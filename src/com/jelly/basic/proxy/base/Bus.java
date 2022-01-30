package com.jelly.basic.proxy.base;

/**
 * @author zhangguodong
 * @since 2022/1/30 09:46
 */
public class Bus implements Car {

    @Override
    public void name(String company) {
        System.out.println(company + "的 bus");
    }

    @Override
    public void running() {
        System.out.println("Bus is running.");
    }
}
