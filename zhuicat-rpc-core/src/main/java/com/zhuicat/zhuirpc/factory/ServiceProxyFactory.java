package com.zhuicat.zhuirpc.factory;

import com.zhuicat.zhuirpc.RpcApplication;
import com.zhuicat.zhuirpc.proxy.MockServiceProxy;
import com.zhuicat.zhuirpc.proxy.ServiceProxy;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂（用于创建代理对象）
 *
 * @author zhuicat
 * @since 2024/5/22 13:05
 */
public class ServiceProxyFactory {
    public static <T> T getService(Class<T> serviceClass) {
        if (RpcApplication.getRpcConfig().isMock()) {
            return getMockProxy(serviceClass);
        }
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass},
                new ServiceProxy());
    }

    private static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass},
                new MockServiceProxy());
    }
}
