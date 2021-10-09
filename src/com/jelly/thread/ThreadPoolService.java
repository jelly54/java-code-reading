package com.jelly.thread;

/**
 * @author ：zhang guo dong
 * @date ：Created in 2020/8/24 20:24
 * @description ：
 */

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池
 *
 * @author zgd
 */
public class ThreadPoolService {

    /**
     * 自定义线程名称,方便的出错的时候溯源
     */
    static ThreadFactory springThreadFactory = new CustomThreadFactory("customThreadFactory-");

    /**
     * 4个核心线程，最大16个线程，空闲时存活10毫秒，阻塞队列最大24个任务，abortPolicy 拒绝并抛出异常
     * <p>
     * corePoolSize    线程池核心池的大小
     * maximumPoolSize 线程池中允许的最大线程数量
     * keepAliveTime   当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
     * unit            keepAliveTime 的时间单位
     * workQueue       用来储存等待执行任务的队列
     * threadFactory   创建线程的工厂类
     * handler         拒绝策略类,当线程池数量达到上线并且workQueue队列长度达到上限时就需要对到来的任务做拒绝处理
     */
    private static ExecutorService service = new ThreadPoolExecutor(
            4,
            16,
            10L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(24),
            springThreadFactory,
            new ThreadPoolExecutor.AbortPolicy()
    );

    /**
     * 获取线程池
     *
     * @return 线程池
     */
    public static ExecutorService getEs() {
        return service;
    }

    /**
     * 使用线程池创建线程并异步执行任务
     *
     * @param r 任务
     */
    public static void newTask(Runnable r) {
        service.execute(r);
    }

    public static void status(String threadName) {
        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) service);

        int poolSize = tpe.getPoolSize();
        int corePoolSize = tpe.getCorePoolSize();

        long taskCount = tpe.getTaskCount();
//        System.out.println(threadName + " 总线程数：" + taskCount);

        int activeCount = tpe.getActiveCount();
//        System.out.println(threadName + " 当前活动线程数：" + activeCount);

        long completedTaskCount = tpe.getCompletedTaskCount();
//        System.out.println(threadName + " 执行完成线程数：" + completedTaskCount);

        int queueSize = tpe.getQueue().size();
//        System.out.println(threadName + " 当前排队线程数：" + queueSize);

        ThreadStatus threadStatus = new ThreadStatus(threadName, poolSize, corePoolSize, taskCount, activeCount, completedTaskCount, queueSize);
        System.out.println(threadStatus);
    }

    /**
     * 线程工厂
     */
    private static class CustomThreadFactory implements ThreadFactory {
        private String poolName;
        private AtomicInteger count;

        private CustomThreadFactory(String poolName) {
            this.poolName = poolName;
            this.count = new AtomicInteger(0);
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            // 线程名，利于排查
            thread.setName(poolName + "-[线程" + count.incrementAndGet() + "]");
            return thread;
        }
    }

    /**
     * 仅用来打印状态
     */
    static class ThreadStatus {
        private String threadName;
        private Integer poolSize;
        private Integer corePoolSize;
        private Long taskCount;
        private Integer activeCount;
        private Long completedCount;
        private Integer queueSize;

        public ThreadStatus(String threadName, Integer poolSize, Integer corePoolSize, Long taskCount, Integer activeCount, Long completedCount, Integer queueSize) {
            this.threadName = threadName;
            this.poolSize = poolSize;
            this.corePoolSize = corePoolSize;
            this.taskCount = taskCount;
            this.activeCount = activeCount;
            this.completedCount = completedCount;
            this.queueSize = queueSize;
        }

        @Override
        public String toString() {
            return threadName + " {" +
                    "curPoolSize=" + poolSize +
                    ", corePoolSize=" + corePoolSize +
                    ", taskCount=" + taskCount +
                    ", activeCount=" + activeCount +
                    ", completedCount=" + completedCount +
                    ", queueSize=" + queueSize +
                    '}';
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) service);
        // 允许核心线程销毁，这里设置为true 则最后 curPoolSize 会为 0，核心线程也被销毁
        tpe.allowCoreThreadTimeOut(true);

        // 启动30个任务，先打印当前线程池的状态，然后sleep3秒 -- 观察
        for (int i = 0; i < 30; i++) {
            ThreadPoolService.newTask(() -> {
                status(Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // 每5s打印一次线程池状态-- 观察curPoolSize 是否会小与 corePoolSize，即核心线程是否会销毁
        for (int i = 0; i < 30; i++) {
            status("core");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
