package com.jelly.base;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author zhangguodong
 * @since 2022/2/3 10:59
 */
public class UnSafeDemo {

    private int num = 8;

    public int getNum() {
        return num;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = getUnsafeInstance();
        memoryTest(unsafe);
//        casObjectField(unsafe);
    }


    /**
     * 获取unsafe实例
     */
    public static Unsafe getUnsafeInstance() throws NoSuchFieldException, IllegalAccessException {
        Class<?> klass = Unsafe.class;
        Field field = klass.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        return (Unsafe) field.get(null);
    }

    /**
     * 内存操作
     * <p>
     * //分配新的本地空间
     * public native long allocateMemory(long bytes);
     * //重新调整内存空间的大小
     * public native long reallocateMemory(long address, long bytes);
     * //将内存设置为指定值
     * public native void setMemory(Object o, long offset, long bytes, byte value);
     * //内存拷贝
     * public native void copyMemory(Object srcBase, long srcOffset,Object destBase, long destOffset,long bytes);
     * //清除内存
     * public native void freeMemory(long address);
     */
    public static void memoryTest(Unsafe unsafe) {
        int size = 4;
        // 使用allocateMemory方法申请4字节长度的内存空间
        long addr = unsafe.allocateMemory(size);
        // 这里是扩容
//        long addr3 = unsafe.reallocateMemory(addr, size * 2);
        long addr3 = unsafe.allocateMemory(size * 2);
        System.out.println("allocate  addr: " + addr);
        System.out.println("allocate addr3: " + addr3);

        try {
            // 调用setMemory方法向每个字节写入内容为byte类型的1
            unsafe.setMemory(null, addr, size, (byte) 1);
            // 偏移量每动1位，二进制表示 变动8个0；后边 put 的字节数的二进制表示，放在偏移量前边
            // unsafe.putByte(addr+1, (byte) 1); // 1  00000000
            // unsafe.putByte(addr+1, (byte) 2); // 10 00000000
            // unsafe.putByte(addr+1, (byte) 3); // 11 00000000
            // unsafe.putByte(addr+1, (byte) 4); // 100 00000000

            // unsafe.putByte(addr+2, (byte) 2); // 10 00000000 00000000
            System.out.println(unsafe.getInt(addr));

            // 从 addr 向 addr3 + size * i 拷贝4字节内存
            for (int i = 0; i < 2; i++) {
                unsafe.copyMemory(null, addr, null, addr3 + size * i, 4);
            }
            System.out.println(unsafe.getLong(addr3));

            // 修改addr3
            unsafe.setMemory(null, addr3, size, (byte) 2);
            System.out.println(unsafe.getInt(addr));
            System.out.println(unsafe.getInt(addr3));
        } finally {
            unsafe.freeMemory(addr);
            unsafe.freeMemory(addr3);
        }
    }

    /**
     * 对象操作
     * cas 修改对象属性值
     */
    public static void casObjectField(Unsafe unsafe) throws NoSuchFieldException, IllegalAccessException {
        UnSafeDemo unSafeDemo = new UnSafeDemo();
        Field numField = unSafeDemo.getClass().getDeclaredField("num");
        numField.setAccessible(true);

        // 计算count的内存偏移量
        long fieldOffset = unsafe.objectFieldOffset(numField);
        System.out.println("field-offset: " + fieldOffset);

        System.out.println("before-swap: " + unsafe.getInt(unSafeDemo, fieldOffset));
        // 原子性的更新指定偏移量的值（将count的值 由 8 修改为3）
        boolean swapInt = unsafe.compareAndSwapInt(unSafeDemo, fieldOffset, 8, 3);
        System.out.println("compare-and-swap: " + swapInt);
        // 获取指定偏移量的int值
        System.out.println("after-swap: " + unsafe.getInt(unSafeDemo, fieldOffset));

        System.out.println("实例的num值: " + unSafeDemo.getNum());
    }
}

