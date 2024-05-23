package com.zhuicat.zhuirpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.zhuicat.zhuirpc.RpcApplication;
import com.zhuicat.zhuirpc.config.RpcConfig;
import com.zhuicat.zhuirpc.constant.RpcConstant;
import com.zhuicat.zhuirpc.model.RpcRequest;
import com.zhuicat.zhuirpc.model.RpcResponse;
import com.zhuicat.zhuirpc.model.ServiceMetaInfo;
import com.zhuicat.zhuirpc.registry.Registry;
import com.zhuicat.zhuirpc.registry.RegistryFactory;
import com.zhuicat.zhuirpc.serializer.JdkSerializer;
import com.zhuicat.zhuirpc.serializer.Serializer;
import com.zhuicat.zhuirpc.serializer.SerializerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ServiceLoader;

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
//        Serializer serializer = new JdkSerializer();
//        Serializer serializer = null;
//        ServiceLoader<Serializer> serviceLoader = ServiceLoader.load(Serializer.class);
//        for (Serializer s : serviceLoader) {
//            serializer = s;
//        }

        Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 发请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();


        try {
            // 序列化
            byte[] bodyBytes = serializer.serializer(rpcRequest);

            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if(CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            // 暂时先去第一个
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);

            byte[] result;
            // 发送请求
            // todo 注意，这里地址被硬编码了（需要使用注册中心和服务发现机制解决）
//            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8081")
//            try (HttpResponse httpResponse = HttpRequest.post("http://" + RpcApplication.getRpcConfig().getServerHost() + ":" + RpcApplication.getRpcConfig().getServerPort())
            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
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
