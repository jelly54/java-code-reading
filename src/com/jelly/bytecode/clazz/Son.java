package com.jelly.bytecode.clazz;

class Father {

    private int i = test();
    private static int j = method();

    static {
        System.out.println("(1) father static");
    }

    Father() {
        System.out.println("(2) father constructor");
    }

    {
        System.out.println("(3) father init");
    }

    public int test() {
        System.out.println("(4) father test");
        return 1;
    }

    public static int method() {
        System.out.println("(5) father static method");
        return 1;
    }

}

public class Son extends Father {

    private int i = test();
    private static int j = method();

    static {
        System.out.println("(6) son static");
    }

    Son() {
        System.out.println("(7) son constructor");
    }

    {
        System.out.println("(8) son init");
    }

    @Override
    public int test() {
        System.out.println("(9) son test");
        return 1;
    }

    public static int method() {
        System.out.println("(10) son static method");
        return 1;
    }

    public static void main(String[] args) {
        // 父类静态属性、静态代码块、子类静态属性、子类静态代码块
        // 父类非静态属性(子类重写了方法，加载子类方法)、父类代码块、父类构造方法
        // 子类非静态属性、子类代码块、子类构造方法
        Son s1 = new Son();

        System.out.println();

        // 父类非静态属性(子类重写了方法，加载子类方法)、父类代码块、父类构造方法
        // 子类非静态属性、子类代码块、子类构造方法
        Son s2 = new Son();
    }

}