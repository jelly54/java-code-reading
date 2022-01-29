package com.jelly.thread.juc;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 利用 RecursiveTask#compute 进行任务的拆分还是直接计算逻辑编写，
 * 利用 forkJoinPool#submit 计算有返回值的任务
 *
 * @author zhangguodong
 * @since 2022/1/29 13:42
 */
public class ForkJoinDemo {
    public static void main(String[] args) {
        // 构建任务
        List<Integer> ret = IntStream.rangeClosed(1, 20000).boxed().collect(Collectors.toList());
        ForkJoinTask task = new ForkJoinTask(ret, 100);
        // 创建分支合并池对象
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(task);

        try {
            // 获取最终合并的结果
            Integer res = task.get();
            System.out.println("结果：" + res);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // 关闭池对象
            forkJoinPool.shutdown();
        }
    }

    public static class ForkJoinTask extends RecursiveTask<Integer> {
        private List<Integer> lists;
        private int start;
        private int end;
        private int max;

        public ForkJoinTask() {
        }

        public ForkJoinTask(List<Integer> lists, int start, int end, int max) {
            this.lists = lists;
            this.start = start;
            this.end = end;
            this.max = max;
        }

        public ForkJoinTask(List<Integer> lists, int max) {
            this.lists = lists;
            this.start = 0;
            this.end = lists.size();
            this.max = max;
        }

        @Override
        protected Integer compute() {
            // 单线程计算
            int res = 0;
            if ((end - start) < max) {
                System.out.println("当前线程为：" + Thread.currentThread().getName() + "：开始：" + start + "；结束：" + end);
                for (int i = start; i < end; i++) {
                    res += lists.get(i);
                }
            } else {
                // 任务分解
//                System.out.println("当前线程为：" + Thread.currentThread().getName() + "：任务分解");
                int avg = (start + end) / 2;
                ForkJoinTask task1 = new ForkJoinTask(lists, start, avg, max);
                ForkJoinTask task2 = new ForkJoinTask(lists, avg + 1, end, max);
                task1.fork();
                task2.fork();
                res = task1.join() + task2.join();
            }
            return res;
        }
    }
}
