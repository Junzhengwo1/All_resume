package com.kou.sentinel.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class A implements Serializable {

    private Integer code;
    private String msg;
    private B data;

}

