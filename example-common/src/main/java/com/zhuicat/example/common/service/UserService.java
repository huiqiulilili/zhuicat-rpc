package com.zhuicat.example.common.service;

import com.zhuicat.example.common.model.User;

/**
 * 用户服务
 *
 * @author zhuicat
 * @since 2024/5/22 10:58
 */
public interface UserService {

    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);

}
