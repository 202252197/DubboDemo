package com.lvshihao.jvmtest;


/**
 * 功能描述
 * 对于数组实例来说,其类型是由JVM在运行期间动态生成的,表示为[Lcom.lvshihao.jvmtest.MyParent4
 * 这种形式。动态生成的类型,其父类型就是Object
 * 对于数组来说,JavaDoc经常将其构造成数组的元素为Component,实际上就是将数组降低一个维度后的类型。
 * 助记符:
 * anewarray:表示创建一个引用类型的(如类,接口,数组)数组,并将其引用值压入栈顶
 * newarray:表示
 *
 * @author 吕世昊
 * @date $
 * @describe 想成为世界最厉害的程序员
 * @email 202252197@qq.com
 * @signature 我的梦想---兰博基尼{奋斗}
 */
public class Test7 {
    public static void main(String[] args) {
        MyParent4[] myParent4s=new MyParent4[1];
        System.out.println(myParent4s.getClass());
        MyParent4[][] myParent4s1=new MyParent4[1][1];
        System.out.println(myParent4s1.getClass());
        System.out.println(myParent4s.getClass().getSuperclass());
        System.out.println(myParent4s1.getClass().getSuperclass());
        System.out.println("======");
        int[] ints=new int[1];
        System.out.println(ints.getClass());
        System.out.println(ints.getClass().getSuperclass());
    }
}
class MyParent4{
    static {
        System.out.println("此类被初始化了");
    }
}
