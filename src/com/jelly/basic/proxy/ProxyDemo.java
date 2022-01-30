package com.jelly.basic.proxy;

import com.jelly.basic.proxy.base.Bus;
import com.jelly.basic.proxy.base.Car;
import com.jelly.basic.proxy.base.Taxi;

/**
 * @author zhangguodong
 * @since 2022/1/30 09:44
 */
public class ProxyDemo {
    public static void main(String[] args) {
//        jdkProxy();
        cglibProxy();
    }

    private static void cglibProxy() {
        CGLIBProxy cglibProxy = new CGLIBProxy();
        Car instance = (Car) cglibProxy.getInstance(new Bus());
        instance.running();
        instance.name("理想");
    }

    public static void jdkProxy() {
        JDKProxy jdkProxy = new JDKProxy();
        Car instance = (Car) jdkProxy.getInstance(new Taxi());
        instance.running();
        instance.name("大众");
    }
}
