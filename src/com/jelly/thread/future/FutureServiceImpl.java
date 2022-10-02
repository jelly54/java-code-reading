package com.jelly.thread.future;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * FutureServiceImpl 的主要作用在于当提交任务时创建一个新的线程来受理该任务，进而达到任务异步执行的效果
 *
 * @author : zhangguodong
 * @since : 2022/10/2 15:35
 */
public class FutureServiceImpl<IN, OUT> implements FutureService<IN, OUT> {

    // 为执行的线程指定名字前缀（再三强调，为线程起一个特殊的名字是一个非常好的编程习惯）
    private final static String FUTURE_THREAD_PREFIX = "FUTURE-";
    private final AtomicInteger nextCounter = new AtomicInteger(0);

    private String getNextName() {
        return FUTURE_THREAD_PREFIX + nextCounter.getAndIncrement();
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        final FutureTask<Void> future = new FutureTask<>();
        new Thread(() -> {
            runnable.run();
            // 任务执行结束之后将 null 作为无返回值的 submit 方法的返回结果 传递给 future
            future.finish(null);
        }, getNextName()).start();
        return future;
    }

    @Override
    public Future<OUT> submit(Task<IN, OUT> task, IN in) {
        final FutureTask<OUT> future = new FutureTask<>();
        new Thread(() -> {
            OUT out = task.get(in);
            // 任务执行结束之后将结果 作为有返回值的 submit 方法的返回结果 传递给 future
            future.finish(out);
        }, getNextName()).start();
        return future;
    }
}
