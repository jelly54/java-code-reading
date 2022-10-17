package com.jelly.thread.activeObject2;

import com.jelly.thread.activeObject.OrderService;
import com.jelly.thread.future.Future;

/**
 * 总结：
 * Active Objects 模式既能够完整地保留接口方法的调用形式，又能让方法的执行异步化，
 * 这也是其他接口异步调用模式（Future 模式：只提供了任务的异步执行方案，但是无法保留接口原有的调用形式）无法同时做到的。
 * <p>
 * Active Objects模式中使用了很多其他设计模式，代理类的生成（代理设计模式 ）、ActiveMessageQueue
 * ( Guarded Suspension Pattern 以 及 Worker Thread Pattern ）、findOrderDetails 方法（Future 设计模式)
 *
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
