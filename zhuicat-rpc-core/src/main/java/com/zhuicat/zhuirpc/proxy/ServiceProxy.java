package com.zhuicat.zhuirpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.zhuicat.zhuirpc.RpcApplication;
import com.zhuicat.zhuirpc.model.RpcRequest;
import com.zhuicat.zhuirpc.model.RpcResponse;
import com.zhuicat.zhuirpc.serializer.JdkSerializer;
import com.zhuicat.zhuirpc.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 服务代理（JDK 动态代理）
 *
 * @author zhuicat
 * @since 2024/5/22 13:02
 */
public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        Serializer serializer = new JdkSerializer();

        // 发请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();


        try {
            // 序列化
            byte[] bodyBytes = serializer.serializer(rpcRequest);
            byte[] result;
            // 发送请求
            // todo 注意，这里地址被硬编码了（需要使用注册中心和服务发现机制解决）
//            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8081")
            try (HttpResponse httpResponse = HttpRequest.post("http://"+ RpcApplication.getRpcConfig().getServerHost()+":"+RpcApplication.getRpcConfig().getServerPort())
                    .body(bodyBytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse rpcResponse = serializer.deserializer(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
