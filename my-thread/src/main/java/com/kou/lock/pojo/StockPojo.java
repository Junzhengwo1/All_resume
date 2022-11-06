package com.kou.lock.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("db_stock")
@Data
public class StockPojo {


    @TableField("id")
    private Integer id;

    @TableField("product_code")
    private String productCode;

    @TableField("count")
    private Integer count;



}
