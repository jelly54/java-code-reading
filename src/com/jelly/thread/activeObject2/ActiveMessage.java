package com.jelly.thread.activeObject2;

import com.jelly.thread.activeObject.ActiveFuture;
import com.jelly.thread.future.Future;

import java.lang.reflect.Method;

/**
 * 包可见，ActiveMessage 只在框架内部使用，不会对外暴露
 *
 * @author : zhangguodong
 * @since : 2022/10/17 10:53
 */
class ActiveMessage {

    // 接口方法参数
    private final Object[] objects;
    // 接口方法
    private final Method method;
    // 有返回值的方法，会返回 ActiveFuture<?> 类型
    private final ActiveFuture<Object> future;
    // 具体的 Service 接口
    private final Object service;

    /**
     * 构造 ActiveMessage 是 Builder 来完成的
     *
     * @param builder builder
     */
    private ActiveMessage(Builder builder) {
        this.objects = builder.objects;
        this.method = builder.method;
        this.future = builder.future;
        this.service = builder.service;
    }

    /**
     * ActiveMessage 的方法通过反射的方式调用执行的具体实现
     */
    public void execute() {
        try {
            // 执行接口的方法
            Object result = method.invoke(service, objects);
            if (future != null) {
                // 如果是有返回值的接口方法，则需要通过get方法获得最终的结果
                Future<?> realFuture = (Future<?>) result;
                Object realResult = realFuture.get();
                // 将结果交给 ActiveFuture 接口方法的线程会的到返回
                future.finish(realResult);
            }
        } catch (Exception e) {
            // 如果发生异常，那么有返回值的方法将会显示地指定结果为 null
            // 无返回值的接口方法则会忽略该异常
            if (future != null) {
                future.finish(null);
            }
        }
    }

    //Builder 主要负责对 ActiveMessage 的构建，是一种典型的 Gof Builder 设计模式
    static class Builder {
        private Object[] objects;
        private Method method;
        private ActiveFuture<Object> future;
        private Object service;

        public Builder useMethod(Method method) {
            this.method = method;
            return this;
        }

        public Builder returnFuture(ActiveFuture<Object> future) {
            this.future = future;
            return this;
        }

        public Builder withObjects(Object[] objects) {
            this.objects = objects;
            return this;
        }

        public Builder forService(Object service) {
            this.service = service;
            return this;
        }

        public ActiveMessage build() {
            return new ActiveMessage(this);
        }
    }
}
