package com.kou.excel.ecxel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author JIAJUN KOU
 * //这个监听器不是我们spring容器中的
 */
@Slf4j
@Component
public class ExcelDataListener extends AnalysisEventListener<Object> {


    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        log.info("解析到一条记录：{}",o);
        //执行读取操作
        System.out.println(o);

    }

    /**
     * 所有数据读取之后的收尾工作
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("存储剩余数据");
        log.info("全部数据解析完成");

    }
}
