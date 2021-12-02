package com.kou.excel.ecxel.read;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.kou.excel.ecxel.listener.ExcelDataListener;
import com.kou.excel.ecxel.pojo.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 文件的读取操作
 */
public class ReadAndWriteFile {

    public static void main(String[] args) {
//        ExcelReaderBuilder builder = EasyExcel.read();

        //ExcelReaderBuilder file = builder.file("C:\\Users\\jiakou\\Desktop\\12312312º.xlsx");
//        builder.autoCloseStream(true);
//        builder.excelType(ExcelTypeEnum.XLSX);
//        //监听器进行数据读取
//        builder.registerReadListener(new ExcelDataListener());
//        ExcelReader reader = builder.build();
//        reader.readAll();
//        reader.finish();
        /**
         * 简化版本
         */
//        List<Map<Integer,String>> list =new LinkedList<>();
//        EasyExcel.read("C:\\Users\\jiakou\\Desktop\\12312312º.xlsx")
//                 .sheet()
//                 .registerReadListener(new AnalysisEventListener<Map<Integer,String>>() {
//
//                     @Override
//                     public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
//                            list.add(integerStringMap);
//                     }
//
//                     @Override
//                     public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//                         System.out.println("读取完成……");
//                     }
//                 }).doRead();
//
//        list.forEach(o->{
////            o.forEach((k,v)->{
////                System.out.println(k+"值；"+v);
////            });
//            System.out.println(o);
//        });

        /**
         * 封装对应类型版本 读数据
         */
        List<Test> list =new LinkedList<>();
        EasyExcel.read("C:\\Users\\jiakou\\Desktop\\12312312º.xlsx")
                 .head(Test.class)
                 .sheet()
                 .registerReadListener(new AnalysisEventListener<Test>() {
                     @Override
                     public void invoke(Test test, AnalysisContext analysisContext) {
                         list.add(test);
                     }
                     @Override
                     public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                         System.out.println("读取完成……");
                     }
                 }).doRead();

        list.forEach(o->{
//            o.forEach((k,v)->{
//                System.out.println(k+"值；"+v);
//            });
            System.out.println(o);
        });


        /**
         * 写数据、
         *
         */
        EasyExcel.write("C:\\Users\\jiakou\\Desktop\\123.xlsx")
         .head(Test.class)
        .excelType(ExcelTypeEnum.XLSX)
        .sheet("test")
        .doWrite(list);  //前提是这个list里面属性 注解了excel的注解



    }

}
