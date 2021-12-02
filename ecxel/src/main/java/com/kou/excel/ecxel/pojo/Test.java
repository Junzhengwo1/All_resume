package com.kou.excel.ecxel.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Test {

    @ExcelProperty("姓名")
    private String userName;

    @ExcelProperty("手机号")
    private String phone;


    @ExcelProperty("提交时间")
    private String submitTime;

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    private LocalDateTime createTime;
//
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
//    private LocalDateTime updateTime;

}
