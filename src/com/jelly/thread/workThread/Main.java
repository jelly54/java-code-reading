package com.jelly.thread.workThread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * @author : zhangguodong
 * @since : 2022/10/16 21:28
 */
public class Main {

    /**
     * (1) Producer-Consumer 模式
     * 首先 Producer、Consumer 对 Queue 都是依赖关系，
     * 其次 Producer 要做的就是不断地往 Queue 中生产数据，而 Consumer则是不断地从 Queue 中获取数据，
     * Queue 既不知道Producer 的存在也不知道 Consumer 的存在，最后 Consumer 对 Queue 中数据的消费并不依赖于数据本身的方法（使用说明书）。
     * <p>
     * --------------------                              ---------------------------
     * ｜ Thread、Thread、 ｜ (produce)        (consume)  ｜ Thread、Thread、
     * ｜ Thread、        ｜  -->     Queue      <--      ｜ Thread、
     * ｜                 ｜                             ｜
     * -------------------                               ---------------------------
     * (2) Worker-Thread 模式
     * 是 Worker-Thread 模式关键角色之间的关系图。左侧的线程，也就是传送带上游的线程，同样在不断地往传送带（Queue）中生产数据，
     * 而当 Channel 被启动的时候，就会同时创建并启动若干数量的 Worker 线程，因此我们可以看出 Worker 于 Channel 来说并不是单纯的依赖关系，
     * 而是聚合关系，Channel 必须知道Worker 的存在。
     * <p>
     * --------------------           ---------------------------
     * ｜ Thread、Thread、 ｜          ｜          Queue          ｜
     * ｜ Thread、         ｜          ｜------------------------ ｜ 从 Queue 取出 由 Worker 并执行
     * ｜                 ｜          ｜  Worker、Worker、Worker  ｜
     * ｜                 ｜          ｜                         ｜
     * -------------------            ---------------------------
     */
    public static void main(String[] args) {
        final ProductionChannel channel = new ProductionChannel(5);
        AtomicInteger productionNo = new AtomicInteger();

        IntStream.range(1, 8).forEach(i -> new Thread(() -> {
            while (true) {
                channel.offerProduction(new Production(productionNo.getAndIncrement()));
                try {
                    TimeUnit.SECONDS.sleep(current().nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start());
    }
}
