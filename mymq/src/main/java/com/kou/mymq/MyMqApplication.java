package com.kou.mymq;

import com.kou.util.EnableMyUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMyUtil
public class MyMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMqApplication.class, args);
    }

}
