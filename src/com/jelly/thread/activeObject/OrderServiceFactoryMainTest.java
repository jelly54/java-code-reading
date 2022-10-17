package com.jelly.thread.activeObject;

import static java.lang.Thread.currentThread;

/**
 * @author : zhangguodong
 * @since : 2022/10/17 14:28
 */
public class OrderServiceFactoryMainTest {
    // 运行测试代码会立即得到返回，10 秒之后，
    // order方法运行结束，调用 order 方法的线程是主线程，但是执行该方法的线程却是其他线程(ActiveDaemonThread)，
    // 这也正是 Active Objects 可接受异步消息的意思
    public static void main(String[] args) throws InterruptedException {
        OrderService orderService = OrderServiceFactory.toActiveObject(new OrderServiceImpl());
        orderService.order("hello", 453453);
        System.out.println("Return immediately");

        currentThread().join();
    }
}
