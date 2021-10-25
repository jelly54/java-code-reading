package com.jelly.base;

/**
 * 1。无论try中的代码是否发生异常，finally中的代码一定会执行。try -- 异常 -- finally 或者 try -- finally
 * 2。try中发生了异常，catch中代码会执行，finally中也会执行。catch和finally中都有return，会执行finally中的return；finally中没有return，finally代码也会执行，但是会返回catch中的值
 * 3。try中未发生异常，会执行finally中的代码。catch和finally中都有return，会执行finally中的return；finally中没有return，finally代码也会执行，但是会返回try中的值
 *
 * 总结一下就是，只要finally中有return，如果finally对返回值修改了，会返回修改后的值；如果finally中无返回值，虽然代码会执行，但是不影响正常的返回
 * @author ：zhangguodong
 * @since ：2021/10/25 21:21
 */
public class TryCacheDemo {
    public Integer tryTest() {
        Integer a = 10;
        try {
            a = new Integer(30);
//            int b = a / 0;
            return a;
        } catch (Exception e) {
            a = new Integer(50);
            System.out.println("exception");
            return a;
        } finally {
            System.out.println("finally");
            a = new Integer(60);
            return a;
        }
//        return a;
    }

    public static void main(String[] args) {
        TryCacheDemo tcd = new TryCacheDemo();
        System.out.println(tcd.tryTest());
    }
}
