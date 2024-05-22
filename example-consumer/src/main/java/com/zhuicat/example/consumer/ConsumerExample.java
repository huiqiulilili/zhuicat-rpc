package com.zhuicat.example.consumer;

import com.zhuicat.example.common.model.User;
import com.zhuicat.example.common.service.UserService;
import com.zhuicat.zhuirpc.RpcApplication;
import com.zhuicat.zhuirpc.config.RpcConfig;
import com.zhuicat.zhuirpc.factory.ServiceProxyFactory;
import com.zhuicat.zhuirpc.utils.ConfigUtils;

/**
 * 服务消费者示例
 *
 * @author zhuicat
 * @since 2024/5/22 11:15
 */
public class ConsumerExample {
    public static void main(String[] args) {
        // todo 需要获取 UserService 的实现类对象
//        UserService userService = new UserServiceProxy();
        System.out.println(RpcApplication.getRpcConfig());
        UserService userService = ServiceProxyFactory.getService(UserService.class);
        User user = new User();
        user.setName("zhuicat");

        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println("================= >>>> " + newUser.getName());
        } else {
            System.out.println("user == null");
        }

//        short number = userService.getNumber();
//        System.out.println("================= >>>> " + number);
    }
}
