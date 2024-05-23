package com.zhuicat.zhuirpc.registry;

import com.zhuicat.zhuirpc.serializer.JdkSerializer;
import com.zhuicat.zhuirpc.serializer.Serializer;
import com.zhuicat.zhuirpc.spi.SpiLoader;

/**
 * 注册中心工厂（用于获取注册中心对象）
 *
 * @author zhuicat
 * @since 2024/5/22 15:01
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认序列化器
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }

}
