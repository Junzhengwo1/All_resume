package com.kou.eg.sensitive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kou.eg.sensitive.entity.SensitiveWordEntity;
import com.kou.eg.sensitive.mapper.SensitiveWordMapper;
import com.kou.eg.sensitive.util.SensitiveWordInit;
import com.kou.eg.sensitive.util.SensitiveWordUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 敏感词过滤service
 */
@Service
public class SensitiveWordFilterService extends ServiceImpl<SensitiveWordMapper, SensitiveWordEntity> {


    public Set<String> sensitiveWordFiltering(String text){
        SensitiveWordInit sensitiveWordInit = new SensitiveWordInit();
        List<SensitiveWordEntity> list = this.list();
        Map map = sensitiveWordInit.initKeyWord(list);
        SensitiveWordUtil.sensitiveWordMap=map;
        Set<String> set = SensitiveWordUtil.getSensitiveWord(text, 2);
        return set;
    }

}
