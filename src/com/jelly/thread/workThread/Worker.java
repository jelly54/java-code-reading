package com.jelly.thread.workThread;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author : zhangguodong
 * @since : 2022/10/16 21:01
 */
public class Worker extends Thread {
    // 主要用于获取一个随机值，模拟加工一个产品需要花费一定的时间，当然每个工人操作时所花费的时间也可能不一样
    private final static Random random = new Random(System.currentTimeMillis());
    private final ProductionChannel channel;

    public Worker(String workName, ProductionChannel channel) {
        super(workName);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 从传送带上获取产品
                Production production = channel.takeProduction();
                System.out.printf("%s process the %s %n", getName(), production);
                // 对产品进行加工
                production.create();
                TimeUnit.SECONDS.sleep(random.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
