package com.jelly.thread;

/**
 * @author : zhangguodong
 * @since : 2022/9/27 13:59
 */
public class ClassLoadDemo {
    /**
     * JVM虚拟机规范规定了，每个类或者接口被 Java 程序首次主动使用时才会对其进行初始化，当然随着 JIT技术越来越成熟，JVM运行期间的编译也越来越智能，不排除JVM在
     * 运行期间提前预判并且初始化某个类。
     *
     * JVM 同时规范了以下6种主动使用类的场景，具体如下。
     * 口 通过 new 关键字会导致类的初始化：这种是大家经常采用的初始化一个类的方式，它肯定会导致类的加载并且最终初始化。
     * 口 访问类的静态变量，包括读取和更新会导致类的初始化，这种情况的示例代码如下：
     *    # public class Simple {
     *    #    static {
     *    #       System.out.println("I will be initialized");
     *    #    }
     *    #    public static int x = 10;
     *    # }
     *    这段代码中 x 是一个简单的静态变量，其他类即使不对 Simple 进行 new 的创建，直接访问变量x也会导致类的初始化。
     * 口 访问类的静态方法，会导致类的初始化，这种情况的示例代码如下：
     *    # public class Simple {
     *    #    static {
     *    #       System.out.println("I will be initialized");
     *    #    }
     *    #    //静态方法
     *    #    public static void test(){
     *    #    }
     *    # }
     *    同样，在其他类中直接调用 test静态方法也会导致类的初始化。
     * 口 对某个类进行反射操作，会导致类的初始化，这种情况的示例代码如下：
     *    # public static void main(String[〕 args) throws ClassNotFoundException {
     *    #     Class.forName("com. wangwenjun.concurrent.chapter09.Simple" ）;
     *    # }
     *    运行上面的代码，同样会看到静态代码块中的输出语句执行。
     * 口 初始化子类会导致父类的初始化，这种情况的示例代码如下：
     *    # public class Parent {
     *    #     static {
     *    #         System.out.printin( "The parent is initialized")；
     *    #     }
     *    #
     *    #     public static int y = 100;
     *    # }
     *    #
     *    # public class Child extends Parent {
     *    #     static {
     *    #         System.out.println("The child wil1 be initialized");
     *    #     }
     *    #     public static int x = 10;
     *    # }
     *    #
     *    # public class ActiveLoadTest {
     *    #     public static void main(String[] args) {
     *    #         System.out.println(Child.x);
     *    #     }
     *    # }
     *    在 ActiveLoadTest 中，我们调用了 Child 的静态变量，根据前面的知识可以得出 Child 类被初始化了，Child 类又是 Parent 类的子类，
     *    子类的初始化会进一步导致父类的初始化，
     *    当然这里需要注意的一点是，通过子类使用父类的静态变量只会导致父类的初始化，子类则不会被初始化，示例代码如下：
     *    # public class ActiveLoadTest {
     *    #     public static void main(String[] args) {
     *    #         System.out.println(Child.y);
     *    #     }
     *    # }
     *    改写后的 ActiveLoadTest，直接用 Child 访问父类的静态变量y，并不会导致 Child 的初始化，仅仅会导致 Parent 的初始化。
     * 口 启动类：也就是执行 main 函数所在的类会导致该类的初始化，比如使用java 命令运行上文中的 ActiveLoadTest 类。
     *
     * 除了上述6 种情况，其余的都称为被动使用，不会导致类的加载和初始化。
     *
     *
     * 关于类的主动引用和被动引用，下面有几个容易引起大家混淆的例子，我们来看一看。
     * 口 构造某个类的数组时并不会导致该类的初始化，比如下面的例子：
     *    # public static void main(String[〕 args) {
     *    #     Simple[] simples = new Simple[10];
     *    #     System.out. println(simples. length)；
     *    # }
     *    上面的代码中 new 方法新建了一个 Simple 类型的数组，但是它并不能导致 Simple 类的初始化，因此它是被动使用，
     *    不要被前面的 new 关键字所误导，事实上该操作只不过是在堆内存中开辟了一段连续的地址空间 4byte × 10。
     * 口 引用类的静态常量不会导致类的初始化，请看下面的例子：
     *    # package com. wangwenjun.concurrent.chapter09;
     *    # import java.util.Random;
     *    # public class GlobalConstants {
     *    #     static {
     *    #         System.out.println("The GlobalConstants wil1 be initialized.");
     *    #     }
     *    #     // 在其他类中使用 MAX 不会导致 GlobalConstants 的初始化，静态代码块不会输出
     *    #     public final static int MAX = 100;
     *    #
     *    #     // 虽然 RANDOM 是静态常量，但是由于计算复杂，只有初始化之后才能得到结果，因此在其他类中使用 RANDOM 会导致 GlobalConstants 的初始化
     *    #     public final static int RANDOM = new Random( ).nextInt();
     *    # }
     *    这段代码中，MAX 是一个被 final 修饰的静态变量，也就是一个静态常量，在其他类中直接访问 MAX 不会导致 GlobalConstants 的初始化，
     *    虽然它也是被 static 修饰的;
     *    但是如果在其他类中访问 RANDOM 则会导致类的初始化，因为 RANDOM 是需要进行随机函数计算的，在类的加载、连接阶段是无法对其进行计算的，
     *    需要进行初始化后才能对其赋予准确的值。
     */
}
