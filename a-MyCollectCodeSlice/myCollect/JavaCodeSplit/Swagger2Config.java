package com.csin.dh.common.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JIAJUN KOU
 *
 * Swagger2配置类
 */
@Slf4j
@Configuration
@EnableSwagger2
@EnableWebMvc
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class Swagger2Config implements WebMvcConfigurer, ApplicationListener<WebServerInitializedEvent> {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("敦煌接口文档")
                .description("操作手册")
                .contact(new Contact("koujiajun","http:localhost:9081/doc.html","xxx@qq.com"))
                .version("1.0")
                .build();
    }


    @SneakyThrows
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        //获取IP
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();
        //获取端口号
        int port = event.getWebServer().getPort();
        log.info("项目启动启动成功！swagger2接口文档地址: http://"+hostAddress+":"+port+"/doc.html");
    }
}
