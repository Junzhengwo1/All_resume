//package myselfUtils.excelUtils;
//
//import cn.hutool.core.collection.CollectionUtil;
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.poi.excel.ExcelReader;
//import cn.hutool.poi.excel.ExcelUtil;
//import com.alibaba.excel.annotation.ExcelProperty;
//import com.capgemini.estate.framework.aop.Converter;
//import com.capgemini.estate.framework.base.exception.ServiceException;
//import com.capgemini.estate.framework.util.excel.MyExcelUtil;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Field;
//import java.util.*;
//
//@Service("CustomizeExcelService")
//public class CustomizeExcelService {
//    /**
//     * 通用导入,读取数据返回
//     * @param file
//     * @param object
//     * @return
//     */
//    public List<?> read(MultipartFile file,Object object) {
//        List<?> list=null;
//        try {
//            List<InputStream> inputStream = getInputStream(file.getInputStream(), 2);
//            String[] header = getHeader(inputStream.get(0));
//            Map<String, String> importAlias = getImportAlias(object.getClass(), header);
//            list = MyExcelUtil.readExcel(inputStream.get(1), object.getClass(), importAlias);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    /**
//     * 通用导出
//     * @param response
//     * @param noExport
//     * @param object
//     * @param data
//     */
//    public  void write(HttpServletResponse response,List<String> noExport,Object object,List<?> data,Integer maxRow) {
//        if (data.size()> maxRow){
//            throw new ServiceException("出错了,数据总数超出可导出数据范围");
//        }else{
//            try {
//                Map<String, String> alias = getAlias(noExport, object);
//                List<Map<String, Object>> dataList = getDataList(data);
//                MyExcelUtil.excelExport(response,"导出文件","数据",alias,dataList);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    /**
//     * 通用多sheet导出
//     * @param response
//     * @param noExport
//     * @param object
//     * @param data
//     */
//    public void writeMore(HttpServletResponse response,List<String> noExport,Object object,List<?> data,Integer maxRow,Integer maxSheet) {
//        double dataCount = data.size()+0.0;
//        int sheetCount = (int) Math.ceil(data.size()/maxRow);
//        if (sheetCount > maxSheet){
//            throw new ServiceException("出错了,总页数大于最大sheet页");
//        }else if (dataCount > (maxRow*maxSheet)){
//            throw new ServiceException("出错了,数据总数超出可导出数据范围");
//        }else{
//            try {
//                Map<String, String> alias = getAlias(noExport, object);
//                List<Map<String, Object>> dataList = getDataList(data);
//                MyExcelUtil.excelMoreExport(response,"导出文件",alias,dataList,maxRow,maxSheet);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 把list<obj> 转换成list<map>集合
//     * @param list
//     * @return
//     */
//    public List<Map<String, Object>> getDataList(List<?> list) {
//        List<Map<String, Object>> maps = new ArrayList<>();
//        for (Object object : list) {
//            Field[] declaredFields = object.getClass().getDeclaredFields();
//            Map<String, Object> map = new HashMap<>();
//            for (Field field : declaredFields) {
//                try {
//                    //设置允许访问private属性
//                    field.setAccessible(true);
//                    map.put(field.getName(), field.get(object));
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//            maps.add(map);
//        }
//        return maps;
//    }
//
//    /**
//     * 通过类获取别名
//     * @param alias
//     * @param object
//     * @return
//     */
//    public Map<String,String> getAlias(List<String> alias,Object object){
//        Map<String,String> map=new LinkedHashMap<>();
//        Field[] declaredFields = object.getClass().getDeclaredFields();
//        for (Field field : declaredFields) {
//            field.setAccessible(true);
//            String name=null;
//            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
//            if (!ObjectUtil.isNull(annotation)){
//                name=annotation.value()[0];
//                if (ObjectUtil.isNull(alias) ||  alias.size()==0){
//                    if (!name.equals("主键") && StrUtil.isNotEmpty(name)){
//                        map.put(field.getName(),name);
//                    }
//                }else{
//                    for (String s : alias) {
//                        if (s.equals(field.getName())){
//                            map.put(field.getName(),name);
//                        }
//                    }
//                }
//            }
//        }
//        return map;
//    }
//
//    /**
//     * 获取表头
//     * @param inputStream
//     * @return
//     */
//    public  String[] getHeader(InputStream inputStream){
//        ExcelReader reader= ExcelUtil.getReader(inputStream);
//        Sheet sheet = reader.getSheet();
//        Row row = sheet.getRow(0);
//
//        int colNum = row.getPhysicalNumberOfCells();
//        String[] title = new String[colNum];
//        for (int i = 0; i < colNum; i++) {
//            Cell cell = row.getCell(i);
//            title[i] = cell.getStringCellValue();
//        }
//        return title;
//    }
//
//    /**
//     * 获取别名映射集合
//     * 例如  商品名称  productName
//     * @param cls
//     * @param headers
//     * @return
//     */
//    public  Map<String,String> getImportAlias(Class cls,String[] headers){
//        List<String> list = Arrays.asList(headers);
//        Field[] declaredFields = cls.getDeclaredFields();
//        Map<String,String > alias=new HashMap<>();
//        for (Field field : declaredFields) {
//            field.setAccessible(true);
//            String name=null;
//            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
//            if (ObjectUtil.isNotNull(annotation)){
//                name=annotation.value()[0];
//                if (list.contains(name)){
//                    alias.put(name,field.getName());
//                }
//            }
//        }
//        return alias;
//    }
//
//    /**
//     * 读取数据流写入字节数组流,多次重用
//     * @param input
//     * @return
//     * @throws IOException
//     */
//    public List<InputStream> getInputStream(InputStream input,Integer count){
//        List<InputStream> inputStreams=new ArrayList<>();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int len;
//        try {
//            while ((len = input.read(buffer)) > -1 ) {
//                baos.write(buffer, 0, len);
//            }
//            baos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        InputStream stream=null;
//        for (Integer integer = 0; integer < count; integer++) {
//            stream= new ByteArrayInputStream(baos.toByteArray());
//            inputStreams.add(stream);
//        }
//        return inputStreams;
//    }
//
//    /**
//     * 通过类获取对应属性映射中文
//     * @param object  example  {"storeNum":"店铺编号"}
//     * @return
//     */
//    public Map<String,String> getMapAlias(Object object){
//        Map<String,String> map=new LinkedHashMap<>();
//        Field[] declaredFields = object.getClass().getDeclaredFields();
//        for (Field field : declaredFields) {
//            field.setAccessible(true);
//            String value=null;
//            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
//            if (ObjectUtil.isNotNull(annotation)){
//                value=annotation.value()[0];
//                map.put(field.getName(),value);
//            }
//        }
//        return map;
//    }
//
//    /**
//     * 数据校验必填字段(公共校验方法)
//     *
//     * @param list        数据集
//     * @param requiredStr 非必填字段数组
//     */
//    public void verifyEmpty(List<?> list, String[] requiredStr) {
//        List<String> requiredList = Arrays.asList(requiredStr);
//        for (Object obj : list) {
//            Class<?> objClass = obj.getClass();
//            Field[] fields = objClass.getDeclaredFields();
//            for (Field field : fields) {
//                String fieldName = field.getName();
//                if (!requiredList.contains(fieldName)) {
//                    field.setAccessible(true);
//                    try {
//                        Object o = field.get(obj);
//                        if (ObjectUtil.isNull(o)) throw new ServiceException("导入失败,必填字段不能为空");
//                        if (StrUtil.isEmpty(o.toString().trim())) throw new ServiceException("导入失败,必填字段不能为空");
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//
//    public static void replaceAll(List<String> list,String oldObject,String newObject) {
//            list.remove(oldObject);
//            list.add(newObject);
//    }
//
//    public static void verifyExcel(MultipartFile file){
//        String filename = file.getOriginalFilename();
//        int lastIndexOf = filename.lastIndexOf(".")+1;
//        String suffix = filename.substring(lastIndexOf, filename.length());
//        if (!suffix.equals("xlsx") && !suffix.equals("xls")){
//             throw new ServiceException("导入失败,请使用Excel模板导入");
//        }
//    }
//
//    public static void converter( List<?> data){
//        if (CollectionUtil.isNotEmpty(data)){
//            for (Object datum : data) {
//                Field[] fields = datum.getClass().getDeclaredFields();
//                for (Field field : fields) {
//                    field.setAccessible(true);
//                    Converter annotation = field.getAnnotation(Converter.class);
//                    if (ObjectUtil.isNotNull(annotation)){
//                        try {
//                            Object object = field.get(datum);
//                            if (ObjectUtil.isNotNull(object)){
//                                //获得属性值
//                                String o=object.toString().trim();
//                                //获得注解值
//                                String[] format = annotation.format();
//                                if (format.length!=0 && o.length()!=0){
//                                    for (String s : format) {
//                                        String[] split = s.split("=");
//                                        if (s.contains(o)){
//                                            try {
//                                                field.set(datum,split[1]);
//                                            } catch (IllegalAccessException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
