package com.jelly.basic.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 基于ASM机制实现，通过生成业务类的子类作为代理类。
 * <p>
 * CGLIB 创建动态代理类的模式是：
 * 查找目标类上的所有非final 的public类型的方法定义；
 * 将这些方法的定义转换成字节码；
 * 将组成的字节码转换成相应的代理的class对象；
 * 实现 MethodInterceptor接口，用来处理对代理类上所有方法的请求
 *
 *
 * CGLib 采用了非常底层的字节码技术，其原理是通过字节码技术为一个类创建子类，并在子类中采用方法拦截的技术拦截所有父类方法的调用，顺势织入横切逻辑，来完成动态代理的实现。
 * 实现方式实现 MethodInterceptor 接口，重写 intercept 方法，通过 Enhancer 类的回调方法来实现。
 *
 * 但是CGLib在创建代理对象时所花费的时间却比JDK多得多，所以对于单例的对象，因为无需频繁创建对象，用CGLib合适，反之，使用JDK方式要更为合适一些。
 * 同时，由于CGLib由于是采用动态创建子类的方法，对于final方法，无法进行代理。
 * 优点：没有接口也能实现动态代理，而且采用字节码增强技术，性能也不错。
 * 缺点：技术实现相对难理解些。
 *
 * @author zhangguodong
 * @since 2022/1/30 10:28
 */
public class CGLIBProxy implements MethodInterceptor {

    // 代理对象
    private Object target;

    // 获取到代理对象
    public Object getInstance(Object target) {
        this.target = target;
        // 通过 Enhancer 对象把代理对象设置为 被代理类的子类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("方法调用前业务处理.");

        // 执行方法调用
        // 注意这里是调用 invokeSuper 而不是 invoke，否则死循环，
        // methodProxy.invokeSuper 执行的是原始类的方法，method.invoke 执行的是子类的方法
        Object result = methodProxy.invokeSuper(o, objects);

        System.out.println("动态代理之后的业务处理.");
        return result;
    }
}
