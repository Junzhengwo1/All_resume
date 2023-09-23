package com.kou.selfann;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SuppressWarnings("all")
@SpringBootApplication
public class SelfannApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SelfannApplication.class, args);
    }

}
