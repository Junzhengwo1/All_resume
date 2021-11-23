package com.kou.eg.sensitive;

import com.kou.eg.sensitive.entity.SensitiveWordEntity;
import com.kou.eg.sensitive.service.SensitiveWordFilterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
class SensitiveWordsApplicationTests {

    @Autowired
    private SensitiveWordFilterService sensitiveWordFilterService;

    @Test
    void contextLoads() {
        List<SensitiveWordEntity> list = sensitiveWordFilterService.list();
        list.forEach(System.out::println);

    }

    @Test
    void test(){
        Set<String> set = sensitiveWordFilterService.sensitiveWordFiltering("鞠冀是傻逼");
        set.forEach(System.out::println);
    }

}
