package com.kou.eg.sensitive.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("sensitive_word")
public class SensitiveWordEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("word_val")
    private String wordVal;

    @TableField("content")
    private String content;
}
