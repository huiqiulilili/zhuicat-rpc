package com.zhuicat.zhuirpc.registry;

import com.zhuicat.zhuirpc.config.RegistryConfig;
import com.zhuicat.zhuirpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心
 *
 * @author zhuicat
 * @since 2024/5/23 11:27
 */
public interface Registry {
    /**
     * 初始化
     */
    void init(RegistryConfig config);

    /**
     * 注册服务（服务端）
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

}
