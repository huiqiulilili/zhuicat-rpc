package com.zhuicat.example.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.zhuicat.example.common.model.User;
import com.zhuicat.example.common.service.UserService;
import com.zhuicat.zhuirpc.model.RpcRequest;
import com.zhuicat.zhuirpc.model.RpcResponse;
import com.zhuicat.zhuirpc.serializer.JdkSerializer;
import com.zhuicat.zhuirpc.serializer.Serializer;

import java.io.IOException;

/**
 * 静态代理
 *
 * @author zhuicat
 * @since 2024/5/22 11:43
 */
public class UserServiceProxy implements UserService {
    @Override
    public User getUser(User user) {
        // 指定序列化器
        Serializer serializer = new JdkSerializer();

        // 发请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();


        try {
            byte[] bodyBytes = serializer.serializer(rpcRequest);
            byte[] result;
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8081")
                    .body(bodyBytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
                RpcResponse rpcResponse = serializer.deserializer(result, RpcResponse.class);
                return (User) rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
