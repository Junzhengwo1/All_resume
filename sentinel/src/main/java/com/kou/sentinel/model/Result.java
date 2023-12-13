package com.kou.sentinel.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private Integer id;
    private String name;
    private Integer age;
}
