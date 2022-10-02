package com.jelly.thread.future;

import java.util.concurrent.TimeUnit;

/**
 * @author : zhangguodong
 * @since : 2022/10/2 15:44
 */
public class Main {
    public static void main(String[] args) {
        try {
//            exampleNoReturn();
            exampleReturn();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void exampleReturn() throws InterruptedException {
        FutureService<String, Integer> service = FutureService.newService();

        Future<Integer> future = service.submit(in -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + ": I am finish done.");

            return in.length();
        }, "InputValue");

        System.out.println(Thread.currentThread().getName() + ": submit task done.");

        Object o = future.get();
        System.out.println(Thread.currentThread().getName() + ": get task result: " + o);
    }

    private static void exampleNoReturn() throws InterruptedException {
        FutureService<Void, Void> service = FutureService.newService();

        Future<?> future = service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + ": I am finish done.");
        });

        System.out.println(Thread.currentThread().getName() + ": submit task done.");

        Object o = future.get();
        System.out.println(Thread.currentThread().getName() + ": get task result: " + o);
    }
}
