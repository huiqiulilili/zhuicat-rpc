package com.zhuicat.zhuirpc;

import com.zhuicat.zhuirpc.config.RegistryConfig;
import com.zhuicat.zhuirpc.config.RpcConfig;
import com.zhuicat.zhuirpc.constant.RpcConstant;
import com.zhuicat.zhuirpc.registry.Registry;
import com.zhuicat.zhuirpc.registry.RegistryFactory;
import com.zhuicat.zhuirpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * RPC 框架应用
 * 相当于 holder，存放了项目全局用到的变量。双检锁单例模式实现
 *
 * @author zhuicat
 * @since 2024/5/22 14:06
 */
@Slf4j
public class RpcApplication {
    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持传入自定义配置
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init,config = {}", newRpcConfig.toString());

        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init，config = {}", registryConfig.toString());

        // 创建并注册 Shutdown Hook，JVM 退出时执行操作
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    /**
     * 初始化
     */
    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            // 配置加载失败，使用默认值
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcConfig.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
