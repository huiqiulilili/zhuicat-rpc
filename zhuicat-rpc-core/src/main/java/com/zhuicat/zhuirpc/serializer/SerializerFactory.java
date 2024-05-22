package com.zhuicat.zhuirpc.serializer;

import java.util.Map;

/**
 * 序列化器工厂
 *
 * @author zhuicat
 * @since 2024/5/22 15:01
 */
public class SerializerFactory {
    /**
     * 序列化映射（用于实现单例）
     */
    private static final Map<String, Serializer> KEY_SERIALIZER_MAP = Map.of(
            SerializerKeys.JDK, new JdkSerializer(),
            SerializerKeys.JSON, new JsonSerializer(),
            SerializerKeys.KRYO, new KryoSerializer(),
            SerializerKeys.HESSIAN, new HessianSerializer()
    );

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = KEY_SERIALIZER_MAP.get(SerializerKeys.JDK);

    /**
     * 获取实例
     */
    public static Serializer getInstance(String key) {
        return KEY_SERIALIZER_MAP.getOrDefault(key, DEFAULT_SERIALIZER);
    }

}
