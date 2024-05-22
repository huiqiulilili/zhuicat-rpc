package com.zhuicat.zhuirpc.serializer;

import java.io.IOException;

/**
 * 序列化器接口
 *
 * @author zhuicat
 * @since 2024/5/22 11:29
 */
public interface Serializer {
    /**
     * 序列化
     */
    <T> byte[] serializer(T object) throws IOException;

    /**
     * 反序列化
     */
    <T> T deserializer(byte[] bytes, Class<T> type) throws IOException;
}
