package com.jelly.thread.activeObject;

import java.util.Map;

/**
 * MethodMessage 的主要作用是收集每一个接口的方法参数，并且提供 execute 方法供ActiveDaemon Thread 直接调用，
 * 该对象就是典型的 Worker Thread 模型中的 Product （附有使用说明书的半成品，等待流水线工人的加工)，execute 方法则是加工该产品的说明书。
 *
 * @author : zhangguodong
 * @since : 2022/10/17 13:48
 */
public abstract class MethodMessage {
    // 用于收集方法参数，如果又返回 Future 类型则一并收集
    protected final Map<String, Object> params;
    // 具体的接口实现，每一个方法会被拆分成不同的 Message
    protected final OrderService orderService;

    public MethodMessage(Map<String, Object> params, OrderService orderService) {
        this.params = params;
        this.orderService = orderService;
    }

    /**
     * 抽象方法，扮演 work thread 的说明书
     */
    public abstract void execute();

}
