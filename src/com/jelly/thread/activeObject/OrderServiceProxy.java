package com.jelly.thread.activeObject;

import com.jelly.thread.future.Future;

import java.util.HashMap;

/**
 * 其主要作用是将 OrderService 接口定义的方法封装成 MethodMessage，然后 offer 给 Active-MessageQueue。
 * 若是无返回值的方法，则只需要提交 Message 到 ActiveMessageQueue 中即可，
 * 但若是有返回值的方法，findOrderDetails 是比较特殊的，它需要返回一个 Active-Future，该 Future 的作用是可以立即返回，当调用线程获取结果时将进人阻塞状态
 *
 * @author : zhangguodong
 * @since : 2022/10/17 13:24
 */
public class OrderServiceProxy implements OrderService {
    private final OrderService orderService;
    private final ActiveMessageQueue activeMessageQueue;

    public OrderServiceProxy(OrderService orderService, ActiveMessageQueue activeMessageQueue) {
        this.orderService = orderService;
        this.activeMessageQueue = activeMessageQueue;
    }

    @Override
    public Future<String> findOrderDetails(long orderId) {
        // 定义一个 ActiveFuture 并且可支持立即返回
        final ActiveFuture<String> activeFuture = new ActiveFuture<>();

        // 收集方法入参以及返回的 ActiveFuture 封装成 MethodMessage
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("activeFuture", activeFuture);
        MethodMessage message = new FindOrderDetailsMessage(params, orderService);

        // 将 MethodMessage 保存至 activeMessageQueue 中
        activeMessageQueue.offer(message);
        return activeFuture;
    }

    @Override
    public void order(String account, long orderId) {
        // 收集方法参数，并且封装成 MethodMessage，然后 offer 至队列中
        HashMap<String, Object> params = new HashMap<>();
        params.put("account", account);
        params.put("orderId", orderId);
        MethodMessage message = new OrderMessage(params, orderService);

        activeMessageQueue.offer(message);
    }
}
