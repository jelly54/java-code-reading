package com.jelly.thread.workThread;

/**
 * 代表组装产品说明书。
 * <p>
 * 经过流水线传送带的产品通过 create() 方法加工，firstProcess() secondProcess() 代表加工每个产品的步骤
 *
 * @author : zhangguodong
 * @since : 2022/10/16 18:01
 */
public abstract class InstructionBook {

    public final void create() {
        this.firstProcess();
        this.secondProcess();
    }

    protected abstract void firstProcess();

    protected abstract void secondProcess();
}
