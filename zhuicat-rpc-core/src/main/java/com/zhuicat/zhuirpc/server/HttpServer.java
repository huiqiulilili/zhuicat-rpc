package com.zhuicat.zhuirpc.server;

/**
 * HTTP 服务器接口
 *
 * @author zhuicat
 * @since 2024/5/22 11:18
 */
public interface HttpServer {
    /**
     * 启动服务
     */
    void doStart(int port);
}
