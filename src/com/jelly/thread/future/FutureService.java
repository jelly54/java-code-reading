package com.jelly.thread.future;

/**
 * @author : zhangguodong
 * @since : 2022/10/2 15:13
 */
public interface FutureService<IN, OUT> {

    /**
     * 提交不需要返回值的任务，Future.get 方法返回的将是 null
     *
     * @param runnable 可运行任务
     * @return Future
     */
    Future<?> submit(Runnable runnable);

    /**
     * 提交需要返回值的任务，其中 Task 接口代替了 Runnable 接口
     *
     * @param task 具有返回值的可运行任务
     * @param in   入参数
     * @return Future<OUT>
     */
    Future<OUT> submit(Task<IN, OUT> task, IN in);

    /**
     * 使用静态方法创建一个 FutureService 的实现
     *
     * @param <T> T
     * @param <R> R
     * @return FutureService
     */
    static <T, R> FutureService<T, R> newService() {
        return new FutureServiceImpl<>();
    }
}
