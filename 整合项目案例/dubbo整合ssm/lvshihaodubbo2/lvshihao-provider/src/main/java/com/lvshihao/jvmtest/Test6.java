package com.lvshihao.jvmtest;

import java.util.UUID;

/**
 * 功能描述 当一个常量的值并非编译期间可以确定的时候,那么这个值就不会放到调用类的常量池中,
 * 这时在程序运行时,会导致主动使用这个常量所在的类,显然会导致这个类被初始化
 *
 * @author 吕世昊
 * @date $
 * @describe 想成为世界最厉害的程序员
 * @email 202252197@qq.com
 * @signature 我的梦想---兰博基尼{奋斗}
 */
public class Test6 {
    public static void main(String[] args) {
        System.out.println(MyParent3.name);
    }
}

class MyParent3{
    public static final  String name= UUID.randomUUID().toString();
    static {
        System.out.println("此类被初始化了");
    }
}
