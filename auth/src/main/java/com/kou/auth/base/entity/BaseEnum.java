package com.kou.auth.base.entity;


import java.util.Arrays;
import java.util.Map;

/**
 * 枚举类型基类
 *
 */
public interface BaseEnum {



    /**
     * 编码重写
     *
     * @return
     */
    default String getCode() {
        return toString();
    }

    /**
     * 描述
     *
     * @return
     */
    String getDesc();
}
