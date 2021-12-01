package com.jelly.bytecode.clazz;

public class A {
    public static String a = "aa- static";
    public String b = "cc";
    static {
        System.out.println("A init");
    }
    public A() {
        System.out.println("A Instance" );
    }
}
