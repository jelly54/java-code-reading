package com.jelly.thread;

import java.util.concurrent.*;

/**
 * @author zhangguodong
 * @date 2021/10/12 17:27
 */
public class FutureTaskDemo {
    public static void main(String[] args) {
//        useCallableFuture();
        useCallableFutureTask();
    }

    public static void useCallableFutureTask() {
        // 创建线程池
        ExecutorService es = Executors.newSingleThreadExecutor();
        // 创建callable对象任务
        CallableDemo cd = new CallableDemo();
        // 创建futureTask
        FutureTask<Integer> futureTask = new FutureTask<>(cd);
        // 提交任务并获取执行结果
        es.submit(futureTask);
        // 关闭线程池
        System.out.println("主线程关闭线程池");
        es.shutdown();
        try {
            System.out.println("主线程sleep 2s");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("主线程sleep 2s 结束");
            if (futureTask.get() != null) {
                System.out.println("futureTask get(): " + futureTask.get());
            } else {
                System.out.println("futureTask get(): get nothing!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("主线程执行完成！");

    }

    public static void useCallableFuture() {
        // 创建线程池
        ExecutorService es = Executors.newSingleThreadExecutor();
        // 创建callable对象任务
        CallableDemo cd = new CallableDemo();
        // 提交任务并获取执行结果
        Future<Integer> future = es.submit(cd);
        // 关闭线程池
        System.out.println("主线程关闭线程池");
        es.shutdown();
        try {
            System.out.println("主线程sleep 2s");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("主线程sleep 2s 结束");
            if (future.get() != null) {
                System.out.println("future get(): " + future.get());
            } else {
                System.out.println("future get(): get nothing!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("主线程执行完成！");
    }
}

class CallableDemo implements Callable<Integer> {

    private int sum;

    @Override
    public Integer call() throws Exception {
        System.out.println("子线程开始工作");
        TimeUnit.SECONDS.sleep(2);
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        System.out.println("子线程计算完成： sum=" + sum);
        return sum;
    }
}