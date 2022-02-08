package com.kou.auth;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@Slf4j
@EnableTransactionManagement
@EnableDiscoveryClient
@MapperScan("com.kou.auth.authority.biz.dao")
@EnableSwagger2
@ComponentScan("com.kou.auth.authority.config")
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(AuthApplication.class, args);
        ConfigurableEnvironment environment = run.getEnvironment();
        String appName = environment.getProperty("spring.application.name");
        String port = environment.getProperty("server.port");
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();
        log.info("应用{}启动成功！Swagger地址：http://{}:{}/doc.html",appName,hostAddress,port);

    }

}
