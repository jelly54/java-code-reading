package com.jelly.thread.observable;

/**
 * 重写父类的 run 方法，并且将其修饰为 final 类型，不允许子类再次对其进行重写，
 * run 方法在线程的运行期间，可监控任务在执行过程中的各个生命周期阶段，任务每经过一个阶段相当于发生了一次事件。
 * <p>
 * <p>
 * update 方法用于通知事件的监听者，此时任务在执行过程中发生了什么，最主要的通知是异常的处理。
 * 如果监听者也就是 TaskLifecycle，在响应某个事件的过程中出现了意外，则会导致任务的正常执行受到影响，
 * <p>
 * 因此需要进行异常捕获，并忽略这些异常信息以保证 TaskLifecycle 的实现不影响任务的正确执行，但是如果任务执行过程中出现错误并且抛出了异常，
 * 那么 update 方法就不能忽略该异常，需要继续抛出异常，保持与 call 方法同样的意图。
 *
 * @author : zhangguodong
 * @since : 2022/10/1 15:41
 */
public class ObservableThread<T> extends Thread implements Observable {
    private Cycle cycle;
    private final TaskLifecycle<T> lifecycle;
    private final Task<T> task;

    /**
     * 指定 Task 的实现，默认情况下使用 EmptyLifecycle
     *
     * @param task task
     */
    public ObservableThread(Task<T> task) {
        this(new TaskLifecycle.EmptyLifecycle<>(), task);
    }

    public ObservableThread(TaskLifecycle<T> lifecycle, Task<T> task) {
        super();
        if (task == null) {
            throw new IllegalArgumentException("The task is required.");
        }
        this.lifecycle = lifecycle;
        this.task = task;
    }

    @Override
    public final void run() {
        // 在执行线程逻辑单元时，分别触发对应事件
        this.update(Cycle.STARTED, null, null);
        try {
            this.update(Cycle.RUNNING, null, null);
            T result = this.task.call();
            this.update(Cycle.DONE, result, null);
        } catch (Exception e) {
            this.update(Cycle.ERROR, null, e);
        }
    }

    @Override
    public Cycle getCycle() {
        return this.cycle;
    }

    private void update(Cycle cycle, T result, Exception e) {
        this.cycle = cycle;
        if (lifecycle == null) {
            return;
        }

        try {
            switch (cycle) {
                case STARTED:
                    this.lifecycle.onStart(currentThread());
                    break;
                case RUNNING:
                    this.lifecycle.onRunning(currentThread());
                    break;
                case DONE:
                    this.lifecycle.onFinish(currentThread(), result);
                    break;
                case ERROR:
                    this.lifecycle.onError(currentThread(), e);
                    break;
            }
        } catch (Exception ex) {
            if (cycle == Cycle.ERROR) {
                throw ex;
            }
        }
        // 忽略 LifeCycle 产生的异常
    }
}
