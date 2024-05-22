package com.zhuicat.zhuirpc.utils;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.setting.dialect.Props;

/**
 * 配置工具类
 *
 * @author zhuicat
 * @since 2024/5/22 14:01
 */
public class ConfigUtils {
    /**
     * 加载配置对象
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    private static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StrBuilder configFileBuilder = new StrBuilder("application");
        if (CharSequenceUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".properties");
        Props props = new Props(configFileBuilder.toString());
        return props.toBean(tClass, prefix);
    }
}
