package com.jelly.thread.activeObject;

import com.sun.tools.corba.se.idl.constExpr.Or;

/**
 * @author : zhangguodong
 * @since : 2022/10/17 14:22
 */
public final class OrderServiceFactory {

    // 将 ActiveMessageQueue 定义成 static 的目的是，保持其在整个 JVM 进程中是唯一的，并且 ActiveDaemonThread 会在此刻启动
    private final static ActiveMessageQueue activeMessageQueue = new ActiveMessageQueue();

    /**
     * 不允许外部通过 new 的方式构建
     */
    private OrderServiceFactory() {
    }

    /**
     * 返回 OrderServiceProxy
     *
     * @param orderService orderService
     * @return OrderServiceProxy
     */
    public static OrderService toActiveObject(OrderService orderService) {
        return new OrderServiceProxy(orderService, activeMessageQueue);
    }


}
