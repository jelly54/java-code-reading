package com.jelly.thread.observable;

import java.util.concurrent.TimeUnit;

/**
 * @author : zhangguodong
 * @since : 2022/10/1 16:21
 */
public class Main {
    public static void main(String[] args) {
        // 和正常使用 Thread 没有太大区别，只不过 ObservableThread 是一个泛型类，定义为 void 表示不关心返回值
//        exampleNoReturn();

        // 使用默认的 EmptyLifecycle 定义关心的生命周期中的节点
        exampleReturn();
    }

    /**
     * 口 在接口 Observable 中定义与 Thread 同样的方法用于屏蔽 Thread 的其他 API，在使用的过程中使用 Observable 声明 ObservableThread 的类型，
     * 如果使用者还想知道更多的关于 Thread 的 API，只需要在 Observable 接口中增加即可。
     *
     * 口 将 ObservableThread 中的 run 方法修饰为 final，或者将 ObservableThread 类修饰为 final，防止子类继承重写，导致整个生命周期的监控失效，
     * 我们都知道，任务的逻辑执行单元是存在于 run 方法之中的，而在 ObservableThread 中我们摒弃了这一点，
     * 让它专门监控业务执行单元的生命周期，而将真正的业务選辑执行单元交给了一个可返回计算结果的接口 Task。
     *
     * 口 ObservableThread 本身的 run 方法充当了事件源的发起者，而 TaskLifecycle 则扮演了事件回调的响应者。
     */
    private static void exampleReturn() {
        final TaskLifecycle<String> lifecycle = new TaskLifecycle.EmptyLifecycle<String>() {
            @Override
            public void onFinish(Thread thread, String result) {
                System.out.println(Thread.currentThread().getName() + ": The result is: " + result);
            }
        };


        Observable observableThread = new ObservableThread<>(lifecycle, () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + ": finished done.");
            return "Thread return value.";
        });
        observableThread.start();
    }

    private static void exampleNoReturn() {
        Observable observableThread = new ObservableThread<>(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": finished done.");
            return null;
        });
        observableThread.start();
    }
}
