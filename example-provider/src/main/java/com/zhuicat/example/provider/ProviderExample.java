package com.zhuicat.example.provider;

import com.zhuicat.example.common.service.UserService;
import com.zhuicat.zhuirpc.RpcApplication;
import com.zhuicat.zhuirpc.config.RpcConfig;
import com.zhuicat.zhuirpc.registry.LocalRegistry;
import com.zhuicat.zhuirpc.server.HttpServer;
import com.zhuicat.zhuirpc.server.VertxHttpServer;

/**
 * 服务提供者示例
 *
 * @author zhuicat
 * @since 2024/5/22 11:14
 */
public class ProviderExample {
    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();
        System.out.println(RpcApplication.getRpcConfig());

        // 注册服务
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);

        // 提供服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
