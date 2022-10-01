package com.jelly.thread.observable;

/**
 * @author : zhangguodong
 * @since : 2022/10/1 10:34
 */
public interface TaskLifecycle<T> {

    // 任务启动时触发 onStart 方法
    void onStart(Thread thread);

    // 任务正在运行时触发 onRunning 方法
    void onRunning(Thread thread);

    // 任务运行结束时触发 onFinish 方法，其中 result 时任务执行结束后的结果
    void onFinish(Thread thread, T result);

    // 任务执行报错时会触发 onError 方法
    void onError(Thread thread, Exception ex);

    /**
     * 生命周期接口的空实现 (adapter)
     *
     * @param <T> T
     */
    class EmptyLifecycle<T> implements TaskLifecycle<T> {

        @Override
        public void onStart(Thread thread) {
            // do nothing
        }

        @Override
        public void onRunning(Thread thread) {
            // do nothing
        }

        @Override
        public void onFinish(Thread thread, T result) {
            // do nothing
        }

        @Override
        public void onError(Thread thread, Exception ex) {
            // do nothing
        }
    }
}
