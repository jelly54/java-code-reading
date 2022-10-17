package com.jelly.thread.workThread;

/**
 * 产品
 * <p>
 * 传送带上除了说明书还有产品自身，产品继承说明书，每个产品都有产品编号
 *
 * @author : zhangguodong
 * @since : 2022/10/16 18:04
 */
public class Production extends InstructionBook {

    private final int prodId;

    public Production(int prodId) {
        this.prodId = prodId;
    }

    @Override
    protected void firstProcess() {
        System.out.printf("execute the %d first process%n", prodId);
    }

    @Override
    protected void secondProcess() {
        System.out.printf("execute the %d second process%n", prodId);
    }

    @Override
    public String toString() {
        return "Production{" +
                "prodId=" + prodId +
                '}';
    }
}
