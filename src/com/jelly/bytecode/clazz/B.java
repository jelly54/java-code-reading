package com.jelly.bytecode.clazz;
public class B extends A {
    public static String a = "bb- static";

    static {
        System.out.println("B init");
    }
    public B() {
        System.out.println("B Instance" );
    }
}