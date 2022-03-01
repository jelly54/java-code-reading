package com.jelly.basic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 基于Java反射机制实现，必须要实现了接口的业务类才能用这种办法生成代理对象。
 * <p>
 * 为了解决静态代理中，生成大量的代理类造成的冗余；
 * JDK 动态代理只需要实现 InvocationHandler 接口，重写 invoke 方法便可以完成代理的实现，
 * jdk的代理是利用反射生成代理类 $ProxyXXX.class 代理类字节码，并生成对象
 * jdk动态代理之所以只能代理接口是因为代理类本身已经 extends了Proxy，而java是不允许多重继承的，但是允许实现多个接口
 * 优点：解决了静态代理中冗余的代理实现类问题。
 * 缺点：JDK 动态代理是基于接口设计实现的，如果没有接口，会抛异常。
 *
 * @author zhangguodong
 * @since 2022/1/30 09:47
 */
public class JDKProxy implements InvocationHandler {
    // 代理对象
    private Object target;

    // 获取到代理对象
    public Object getInstance(Object target) {
        this.target = target;
        // 取得代理对象，这里的 this 会将 InvocationHandler 传递给 Proxy 代理中心，
        // 后续调用对象的方法时，实际上是调用的代理对象的 invoke 方法，invoke 方法通过
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    /**
     * 执行代理方法
     *
     * @param proxy  代理对象
     * @param method 代理方法
     * @param args   方法的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws InvocationTargetException, IllegalAccessException {
        System.out.println("动态代理之前的业务处理.");

        // 执行调用方法（此方法执行前后，可以进行相关业务处理）
        Object result = method.invoke(target, args);

        System.out.println("动态代理之后的业务处理.");
        return result;
    }
}
