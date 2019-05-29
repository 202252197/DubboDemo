package com.lvshihao;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 功能描述
 *
 * @author 吕世昊
 * @date $
 * @describe 想成为世界最厉害的程序员
 * @email 202252197@qq.com
 * @signature 我的梦想---兰博基尼{奋斗}
 */

public class ProviderApp {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext cpxac=new ClassPathXmlApplicationContext("dubbo-provider.xml");
        cpxac.start();
        System.in.read();
    }
}
