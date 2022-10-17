package com.jelly.thread.activeObject;


import com.jelly.thread.future.Future;

/**
 * @author : zhangguodong
 * @since : 2022/10/17 10:37
 */
public interface OrderService {

    Future<String> findOrderDetails(long orderId);

    void order(String account,long orderId);
}
