package com.zhuicat.example.common.model;

import java.io.Serializable;

/**
 * @author zhuicat
 * @since 2024/5/22 10:57
 */
public class User  implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
