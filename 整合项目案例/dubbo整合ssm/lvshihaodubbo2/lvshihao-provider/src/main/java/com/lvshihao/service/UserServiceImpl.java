package com.lvshihao.service;

import com.lvshihao.dao.UserMapper;
import com.lvshihao.userApi.UserService;
import com.lvshihao.userEntity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述
 *
 * @author 吕世昊
 * @date $
 * @describe 想成为世界最厉害的程序员
 * @email 202252197@qq.com
 * @signature 我的梦想---兰博基尼{奋斗}
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int InsertUser(User user) {
        System.out.println(user);
        return userMapper.insertSelective(user);
    }
}
