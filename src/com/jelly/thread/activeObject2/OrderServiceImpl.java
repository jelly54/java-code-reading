package com.jelly.thread.activeObject2;

import com.jelly.thread.activeObject.OrderService;
import com.jelly.thread.future.Future;
import com.jelly.thread.future.FutureService;

import java.util.concurrent.TimeUnit;

/**
 * @author : zhangguodong
 * @since : 2022/10/17 10:42
 */
public class OrderServiceImpl implements OrderService {

    @ActiveMethod
    @Override
    public Future<String> findOrderDetails(long orderId) {
        return FutureService.<Long, String>newService().submit(input -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "The order Details Information";
        }, orderId, null);
    }

    @ActiveMethod
    @Override
    public void order(String account, long orderId) {
        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.printf("process the order for account %s, orderId %d", account, orderId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
