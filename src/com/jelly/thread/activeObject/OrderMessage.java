package com.jelly.thread.activeObject;

import java.util.Map;

/**
 * @author : zhangguodong
 * @since : 2022/10/17 14:09
 */
public class OrderMessage extends MethodMessage {

    public OrderMessage(Map<String, Object> params, OrderService orderService) {
        super(params, orderService);
    }

    @Override
    public void execute() {
        // 获取参数
        String account = (String) params.get("account");
        long orderId = (long) params.get("orderId");

        // 执行真正的order方法
        orderService.order(account, orderId);
    }
}
