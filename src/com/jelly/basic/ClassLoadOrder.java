package com.jelly.basic;

/**
 * 类加载顺序：
 * 父类静态代码块
 * 子类静态代码块
 * 父类普通代码块 :null
 * 父类构造方法
 * 子类普通代码块:33
 * 子类构造方法
 * main 执行完毕
 *
 * @author zhangguodong
 * @since 2021/10/21 19:12
 */
public class ClassLoadOrder {
    public static void main(String[] args) {
        Father s = new Son();
        System.out.println("main 执行完毕");
    }
}

class Father {
    Integer fa;

    static {
        System.out.println("父类静态代码块");
    }

    {
        System.out.println("父类普通代码块 :" + fa);
    }

    public Father() {
        this.fa = 33;
        System.out.println("父类构造方法");
    }

}

class Son extends Father {
    static {
        System.out.println("子类静态代码块");
    }

    {
        System.out.println("子类普通代码块:" + fa);
    }

    public Son() {
        System.out.println("子类构造方法");
    }
}