package com.jelly.base;

/**
 * @author zhangguodong
 * @since 2021/11/19 20:38
 */
public class StaticProfile {

    public static void main(String[] args) {
        St sp= null;
        System.out.println(sp.a);
    }
}

class St{
    public static String a = "static a";
}
