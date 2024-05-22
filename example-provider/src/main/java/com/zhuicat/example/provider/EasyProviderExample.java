package com.zhuicat.example.provider;

import com.zhuicat.example.common.service.UserService;
import com.zhuicat.zhuirpc.registry.LocalRegistry;
import com.zhuicat.zhuirpc.server.HttpServer;
import com.zhuicat.zhuirpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 *
 * @author zhuicat
 * @since 2024/5/22 11:14
 */
public class EasyProviderExample {
    public static void main(String[] args) {

        // 注册服务
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);

        // 提供服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8081);
    }
}
