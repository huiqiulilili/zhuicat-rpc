package com.zhuicat.zhuirpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地注册中心
 *
 * @author zhuicat
 * @since 2024/5/22 11:26
 */
public class LocalRegistry {
    private static Map<String, Class<?>> map = new ConcurrentHashMap<>();

    /**
     * 注册服务
     */
    public static void register(String serviceName, Class<?> implCLass) {
        map.put(serviceName, implCLass);
    }

    /**
     * 获取服务
     */
    public static Class<?> get(String serviceName) {
        return map.get(serviceName);
    }

    /**
     * 删除服务
     */
    public static void remove(String serviceName) {
        map.remove(serviceName);
    }
}
