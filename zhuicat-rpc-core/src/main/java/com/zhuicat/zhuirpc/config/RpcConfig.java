package com.zhuicat.zhuirpc.config;

import lombok.Data;

/**
 * RPC 框架配置
 *
 * @author zhuicat
 * @since 2024/5/22 13:59
 */
@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "zhui-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private int serverPort = 8081;

    /**
     * 模拟调用
     */
    private boolean mock = false;
}
