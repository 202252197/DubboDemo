package com.lvshihao.controller;

import com.lvshihao.userApi.UserService;
import com.lvshihao.userEntity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.Contended;

/**
 * 功能描述
 *
 * @author 吕世昊
 * @date $
 * @describe 想成为世界最厉害的程序员
 * @email 202252197@qq.com
 * @signature 我的梦想---兰博基尼{奋斗}
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/insertUser")
    public void insertUser(){
        User user=new User("吕世昊","瘦肉","大");
        userService.InsertUser(user);
    }
}
