package com.jelly.thread.activeObject2;

import com.jelly.thread.activeObject.OrderService;
import com.jelly.thread.future.Future;

/**
 * @author : zhangguodong
 * @since : 2022/10/17 15:10
 */
public class OrderServiceFactoryMainTest {
    //  运行上面的测试代码，future 将会立即返回，但是 get 方法会进入阻塞，10 秒钟以后订
    //  单的详细信息将会返回，同样，OrderService 接口的调用线程和具体的执行线程不是同一个，
    //  OrderServiceImpl 通过 active 方法具备了可接受异步消息的能力。
    public static void main(String[] args) throws InterruptedException {
        OrderService orderService = ActiveServiceFactory.active(new OrderServiceImpl());
        Future<String> future = orderService.findOrderDetails(23423);

        System.out.println("i will be returned immediately");
        System.out.println(future.get());
    }
}
