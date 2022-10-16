package com.jelly.thread.workThread;

/**
 * 产品传送带
 * 在传送带上除了负责产品加工的工人之外，还有在传送带上等待加工的产品
 *
 * @author : zhangguodong
 * @since : 2022/10/16 20:48
 */
public class ProductionChannel {
    // 传送带上最多可以有多少个待加工的产品
    private final static int MAX_PROD = 100;
    // 主要用来存放待加工的产品，也就是传送带
    private final Production[] productionQueue;
    // 队列尾
    private int tail;
    // 队列头
    private int head;
    // 当前在流水线上有多少个待加工的产品
    private int total;

    // 在流水线上工作的人
    private final Worker[] workers;
    // 创建 ProductionChannel 时应指定需要多少个流水线工人

    public ProductionChannel(int workerSize) {
        this.workers = new Worker[workerSize];
        this.productionQueue = new Production[MAX_PROD];
        for (int i = 0; i < workerSize; i++) {
            workers[i] = new Worker("Worker-" + i, this);
            workers[i].start();
        }
    }

    /**
     * 接受上游的半成品（待加工的产品）
     *
     * @param production p
     */
    public void offerProduction(Production production) {
        synchronized (this) {
            // 当传送带上待加工的产品超过了最大值时，需要阻塞上游再次传送产品
            while (total >= productionQueue.length) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    // ignore
                }
            }
            // 将产品放到传送带，并且通知工人线程工作
            productionQueue[tail] = production;
            tail = (tail + 1) % productionQueue.length;
            total++;
            this.notifyAll();
        }
    }

    /**
     * 工人线程（Worker）从传送带上获取产品并进行加工
     */
    public Production takeProduction() {
        synchronized (this) {
            // 传送带上没有产品时，工人等待着产品从上游输送到传送带上
            while (total <= 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    // ignore
                }
            }
            // 获取产品
            Production prod = productionQueue[head];
            head = (head + 1) % productionQueue.length;
            total--;
            this.notifyAll();
            return prod;
        }
    }
}
