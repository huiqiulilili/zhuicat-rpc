package com.zhuicat.example.provider;

import com.zhuicat.example.common.model.User;
import com.zhuicat.example.common.service.UserService;

/**
 * 用户服务实现类
 *
 * @author zhuicat
 * @since 2024/5/22 11:13
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
