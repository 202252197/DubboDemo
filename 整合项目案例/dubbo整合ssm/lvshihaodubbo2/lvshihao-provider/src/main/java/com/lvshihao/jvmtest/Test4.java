package com.lvshihao.jvmtest;


/**
 * 功能描述 测试静态字段对于类的加载情况
 *
 * @author 吕世昊
 * @date $
 * @describe 想成为世界最厉害的程序员
 * @email 202252197@qq.com
 * @signature 我的梦想---兰博基尼{奋斗}
 */
public class Test4 {
    /**
     * 对于静态字段来说,只有直接定义了该字段的类才会被初始化
     * 当一个类在初始化时,要求其父类全部都已经初始化完毕了
     * JVM参数:-XX:+TraceClassLoading 用于追踪类的加载信息并打印出来
     */
    public static void main(String[] args) {
//        System.out.println(MyChild1.str);//虽然调用的是MyChild1.str但是MyChild类并不会被初始化,因为只有定了该字段的类才会被初始化
        System.out.println(MyChild1.str2);
    }
}
class MyParent1{
    public static String str="hello world";
    static{
        System.out.println("MyParent1 static block");
    }
}
class MyChild1 extends MyParent1{
    public static String str2="welocme";
    static{
        System.out.println("MyChild1 static block");
    }
}