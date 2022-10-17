package com.jelly.thread.activeObject;

import com.jelly.thread.future.Future;

import java.util.Map;

/**
 * @author : zhangguodong
 * @since : 2022/10/17 13:53
 */
public class FindOrderDetailsMessage extends MethodMessage {

    public FindOrderDetailsMessage(Map<String, Object> params, OrderService orderService) {
        super(params, orderService);
    }

    @Override
    public void execute() {
        // 1⃣
        Future<String> realFuture = orderService.findOrderDetails((Long) params.get("orderId"));
        ActiveFuture<String> activeFuture = (ActiveFuture<String>) params.get("activeFuture");

        try {
            // 2⃣  阻塞，直到 findOrderDetails 方法完全执行结束
            String result = realFuture.get();
            // 3⃣  结束后
            activeFuture.finish(result);
        } catch (InterruptedException e) {
            activeFuture.finish(null);
        }

    }
}
