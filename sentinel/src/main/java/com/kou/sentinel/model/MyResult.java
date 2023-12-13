package com.kou.sentinel.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MyResult<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

}

