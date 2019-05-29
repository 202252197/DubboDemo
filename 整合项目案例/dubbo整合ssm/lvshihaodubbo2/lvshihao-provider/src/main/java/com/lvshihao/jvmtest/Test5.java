package com.lvshihao.jvmtest;

/**
 * 功能描述 测试调用一个类的常量静态属性的时候类会不会初始化
 *
 * @author 吕世昊
 * @date $
 * @describe 想成为世界最厉害的程序员
 * @email 202252197@qq.com
 * @signature 我的梦想---兰博基尼{奋斗}
 */
public class Test5 {
    /**
     * 常量在编译阶段会存入到调用这个常量的方法所在的类的常量池中
     * 本质上,调用类并没有直接引用到定义常量的类,因此并不会触发定义常量的类的初始化
     * 注意:这里指的是将常量存放到Test5的常量池中,之后Test5与MyParent2就没有任何关了
     * 甚至,我们可以将MyParent2的class文件删除,照样可以正确运行
     * 助记符:
     *  ldc表示将int,float或是String类型的常量值从常量池中推送栈顶
     *  bipush表示将单字节(-128~127)的常量值推送至栈顶
     *  sipush表示将一个短整型常量值(-32768~32767)推送至栈顶
     *  iconst_3表示将int类型1推送至栈顶(iconst_0~iconst_5)
     *  iconst_m1表示将int类型-1推送至栈顶
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(MyParent2.age);
    }
}

class MyParent2{
    public static final  String name="lvshihao";
    public static final  int age=-1;
    static {
        System.out.println("此类被初始化了");
    }
}
