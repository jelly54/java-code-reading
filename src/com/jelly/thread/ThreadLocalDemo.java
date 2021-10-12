package com.jelly.thread;

import java.util.Date;

/**
 * @author ：zhangguodong
 * @since ：2021/10/10 10:37
 */
public class ThreadLocalDemo {
    /**
     * 1. 内部使用 ThreadLocalMap 将ThreadLocal作为key，来缓存每个线程不同的数据
     * 2. ThreadLocalMap 中只有一个 Entry extends WeakReference<ThreadLocal<?>> 结构
     * 3. ThreadLocalMap 和hashMap 类似，也使用hash散列，数据存在不同的entry数组中，但是数组中并没有存储链表而是直接的值。
     * 4. ThreadLocalMap 初始容量也是16，在set时，清除一些无用数据后，容量达到2/3时会触发rehash，
     * 如果容量 >= threshold - threshold / 4 则会触发扩容（newLen = oldLen * 2）
     * 5. 注意：如果使用线程池，同时使用threadLocal，可能会造成内存泄露。
     * 线程池中的核心线程多数情况是会复用的，如果不手动清除，threadLocal会越来越大
     */
    private static final ThreadLocal<Long> local = ThreadLocal.withInitial(() -> new Date().getTime());


//// 阿里 java 开发手册推荐使用
//  import java.text.DateFormat;
//  import java.text.SimpleDateFormat;
//
//public class DateUtils {
//    public static final ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>(){
//        @Override
//        protected DateFormat initialValue() {
//            return new SimpleDateFormat("yyyy-MM-dd");
//        }
//    };
//}
}
