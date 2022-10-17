package com.jelly.thread.activeObject2;

import com.jelly.thread.activeObject.ActiveFuture;
import com.jelly.thread.future.Future;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author : zhangguodong
 * @since : 2022/10/17 14:47
 */
public class ActiveServiceFactory {
    // 定义 ActiveMessageQueue 用于存放 ActiveMessage
    private final static ActiveMessageQueue queue = new ActiveMessageQueue();

    /**
     * 会根据 ActiveService 实例生成一个动态代理实例，其中会用到 ActiveInvocationHandler 作为 newProxyInstance 的 InvocationHandler。
     */
    public static <T> T active(T instance) {
        // 生产 Service 代理类
        Object proxy = Proxy.newProxyInstance(instance.getClass().getClassLoader(), instance.getClass().getInterfaces(),
                new ActiveInvocationHandler<>(instance));
        return (T) proxy;
    }

    /**
     * ActiveInvocationHandler 是 InvocationHandler 的子类，生成 Proxy 时需要使用到
     *
     * @param <T> T
     */
    private static class ActiveInvocationHandler<T> implements InvocationHandler {

        private final T instance;

        private ActiveInvocationHandler(T instance) {
            this.instance = instance;
        }

        /**
         * 口 首先会判断该方法是否被 @ActiveMethod 标记，如果没有则被当作正常方法来使用。
         * 口 如果接口方法被 @ActiveMethod 标记，则需要判断方法是否符合规范：有返回类型，必须是 Future 类型。
         * 口 定义 ActiveMessage.Builder 分别使用 method、方法参数数组以及 Active Service 实例，如果该方法是 Future 的返回类型，则还需要定义 ActiveFuture。
         * 口 最后将 ActiveMessage 插入 ActiveMessageQueue 中，并且返回 method 方法 invoke 结果。
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 如果接口方法被 @ActiveMethod 标记，则会转换为 ActiveMessage
            if (method.isAnnotationPresent(ActiveMethod.class)) {
                // 坚持该方法是否符合规范
                this.checkMethod(method);
                ActiveMessage.Builder builder = new ActiveMessage.Builder();
                builder.useMethod(method).withObjects(args).forService(instance);
                Object result = null;
                if (this.isReturnFutureType(method)) {
                    result = new ActiveFuture<>();
                    builder.returnFuture((ActiveFuture) result);
                }
                // 将 ActiveMessage 加入队列中
                queue.offer(builder.build());
                return result;
            } else {
                // 如果是普通方法（没有使用 @ActiveMethod 标记）则会正常执行
                return method.invoke(instance, args);
            }
        }

        //检查有返回值的方法是否为 Future，否则将会拋出 IllegalActiveMethod 异常
        private void checkMethod(Method method) throws IllegalActiveMethod {
            // 有返回值，必须是 ActiveFuture 类型的返回值
            if (!isReturnVoidType(method) && !isReturnFutureType(method)) {
                throw new IllegalActiveMethod("the method [" + method.getName() + " return type must be void/Future");
            }
        }

        /**
         * 判断方法是否为 Future 返回类型
         */
        private boolean isReturnFutureType(Method method) {
            return method.getReturnType().isAssignableFrom(Future.class);
        }

        /**
         * 判断方法是否无返回类型
         */
        private boolean isReturnVoidType(Method method) {
            return method.getReturnType().equals(Void.TYPE);
        }
    }
}