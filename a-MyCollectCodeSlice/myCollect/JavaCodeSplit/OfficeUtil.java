package com.csin.dh.common.utils;

import com.alibaba.excel.EasyExcel;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.j256.simplemagic.ContentInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.RFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Office 工具类
 */
@Slf4j
public class OfficeUtil {


    /**
     * 导出Excel(一个sheet)
     *
     * @param response  HttpServletResponse
     * @param list      数据list
     * @param fileName  导出的文件模板
     * @param sheetName 导入文件的sheet名
     * @param clazz     实体类
     */
    public static <T> void writeExcel(HttpServletResponse response,
                                      List<T> list,
                                      String fileName,
                                      String sheetName,
                                      Class<T> clazz) throws IOException {
        OutputStream outputStream = getOutputStream(response, fileName);
        EasyExcel.write(outputStream,
                        clazz)
                .sheet(sheetName)
                .doWrite(list);
        outputStream.close();
    }

    /**
     * 导出word ** 老版本的.doc 不支持|| 只支持 .docx 2007新版
     *
     * @param response HttpServletResponse
     * @param map      替换 word 中的内容
     * @param fileName 模版文件名
     * @param config   表格循环策略
     */
    public static void writeWord(HttpServletResponse response,
                                 Configure config,
                                 String fileName,
                                 Map<String, Object> map) throws Exception {
        OutputStream outputStream = getOutputStream(response, fileName);
        XWPFTemplate template = writeWord(config, fileName, map);
        template.writeAndClose(outputStream);
    }

    public static XWPFTemplate writeWord(Configure config, String fileName, Map<String, Object> map) throws Exception {
        InputStream inputStream = new ClassPathResource("/template/word/" + fileName).getInputStream();
        config = null == config ? Configure.createDefault() : config;
        return XWPFTemplate.compile(inputStream, config).render(map);
    }

    /**
     * word合并
     */
    public static void writeWord(List<NiceXWPFDocument> documents, HttpServletResponse response, String fileName) throws Exception {
        NiceXWPFDocument document = documents.get(0);

        CTDocument1 document1 = document.getDocument();
        CTBody body = document1.getBody();
        // 绑定命名空间
        CTP ctp = body.addNewP();
        CTR ctr = ctp.addNewR();
        CTText ctText = ctr.addNewT();
        ctText.setNil();
        ctr.setNil();
        ctp.setNil();
        ctp.addNewR().addNewT().setStringValue(Strings.EMPTY);

        documents.remove(document);
        NiceXWPFDocument merge = document.merge(documents, document.getXWPFDocument().createParagraph().createRun());
        document.close();
        documents.forEach(o -> {
            try {
                o.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

      /*  AtomicReference<NiceXWPFDocument> document = new AtomicReference<>();
        AtomicInteger index = new AtomicInteger(0);
        documents.forEach(doc -> {
            if (index.getAndIncrement() == 0) {
                doc.insertNewTbl()
                document.set(doc);
                return;
            }
            try {
                document.get().merge(doc);
            } catch (Exception e) {
                throw new BusinessException("word文档合并失败");
            }
        });*/
        OutputStream outputStream = getOutputStream(response, fileName);
        merge.write(outputStream);
        outputStream.close();
    }

    /**
     * 直接pdf导出为流
     *
     * @param response    HttpServletResponse
     * @param fileName    word模板文件名
     * @param pdfFileName 导出 pdf 文件 可以不传 不传则是模板文件名.pdf
     * @param config      表格循环策略
     * @param map         填充参数
     * @throws Exception e
     */
    public static void writePdfForResponse(HttpServletResponse response,
                                           Configure config,
                                           String fileName,
                                           Map<String, Object> map, String pdfFileName) throws Exception {
        config = null == config ? Configure.createDefault() : config;
        String[] split = fileName.split("\\.");
        pdfFileName = StringUtils.isBlank(pdfFileName) ? split[0] + ".pdf" : pdfFileName;
        ByteArrayOutputStream byteArrayOutputStream = writeWordToByteArrayOutputStream(config, fileName, map);
        wordOutputStreamToPdfResponse(byteArrayOutputStream, response, pdfFileName);
        byteArrayOutputStream.close();
    }

    /**
     * 写到缓存流
     *
     * @param config   表格循环策略
     * @param fileName 模版文件名
     * @param map      替换 word 中的内容
     * @return 输出流
     */
    public static ByteArrayOutputStream writeWordToByteArrayOutputStream(Configure config,
                                                                         String fileName,
                                                                         Map<String, Object> map) throws Exception {
        InputStream inputStream = new ClassPathResource("/template/word/" + fileName).getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        XWPFTemplate template = XWPFTemplate.compile(inputStream, config).render(map);
        template.write(byteArrayOutputStream);
        return byteArrayOutputStream;
    }


    /**
     * 通过 word 输出流
     *
     * @param wordOutputStream word 输出流
     * @param response         返回前端
     * @param pdfFileName      pdf 文件名
     */
    public static void wordOutputStreamToPdfResponse(ByteArrayOutputStream wordOutputStream,
                                                     HttpServletResponse response,
                                                     String pdfFileName) throws Exception {
        OutputStream outputStream = getOutputStream(response, pdfFileName);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(wordOutputStream.toByteArray());
        WordprocessingMLPackage mlPackage = getWordprocessingMLPackage(byteArrayInputStream);
        //docx4j  docx转pdf
        FOSettings foSettings = Docx4J.createFOSettings();
        foSettings.setWmlPackage(mlPackage);
        Docx4J.toFO(foSettings, outputStream, Docx4J.FLAG_EXPORT_PREFER_XSL);
        byteArrayInputStream.close();
        outputStream.close();
    }


    /**
     * 导出时生成OutputStream
     */
    public static OutputStream getOutputStream(HttpServletResponse response,
                                               String fileName) throws IOException {
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //流形式
        response.setContentType(ContentInfoUtil.findExtensionMatch(suffix).getMimeType());
        //防止中文乱码
        response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        return response.getOutputStream();
    }

    /**
     * word 字体
     *
     * @param is 输入流
     */
    private static WordprocessingMLPackage getWordprocessingMLPackage(InputStream is) throws Exception {
        WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(is);
        Mapper fontMapper = new IdentityPlusMapper();
        RFonts rfonts = Context.getWmlObjectFactory().createRFonts();
        rfonts.setAscii("SimSun");
        rfonts.setHAnsi("SimSun");
        // 非windows 环境 宋体
        PhysicalFonts.addPhysicalFonts("SimSun",OfficeUtil.class.getResource("/font/simsun.ttc"));
        PhysicalFonts.addPhysicalFonts("FangSong_GB2312",OfficeUtil.class.getResource("/font/simfang.ttf"));
        fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
        fontMapper.put("宋体", PhysicalFonts.get("SimSun"));
        fontMapper.put("微软雅黑", PhysicalFonts.get("Microsoft Yahei"));
        fontMapper.put("黑体", PhysicalFonts.get("SimHei"));
        fontMapper.put("楷体", PhysicalFonts.get("KaiTi"));
        fontMapper.put("新宋体", PhysicalFonts.get("NSimSun"));
        fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
        fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
        fontMapper.put("仿宋", PhysicalFonts.get("FangSong"));
        fontMapper.put("幼圆", PhysicalFonts.get("YouYuan"));
        fontMapper.put("华文宋体", PhysicalFonts.get("STSong"));
        fontMapper.put("华文中宋", PhysicalFonts.get("STZhongsong"));
        fontMapper.put("等线", PhysicalFonts.get("SimSun"));
        fontMapper.put("等线 Light", PhysicalFonts.get("SimSun"));
        fontMapper.put("华文琥珀", PhysicalFonts.get("STHupo"));
        fontMapper.put("华文隶书", PhysicalFonts.get("STLiti"));
        fontMapper.put("华文新魏", PhysicalFonts.get("STXinwei"));
        fontMapper.put("华文彩云", PhysicalFonts.get("STCaiyun"));
        fontMapper.put("方正姚体", PhysicalFonts.get("FZYaoti"));
        fontMapper.put("方正舒体", PhysicalFonts.get("FZShuTi"));
        fontMapper.put("华文细黑", PhysicalFonts.get("STXihei"));
        fontMapper.put("宋体扩展", PhysicalFonts.get("simsun-extB"));
        fontMapper.put("仿宋_GB2312", PhysicalFonts.get("FangSong_GB2312"));
        PhysicalFonts.put("PMingLiU",PhysicalFonts.get("SimSun"));
        mlPackage.setFontMapper(fontMapper);
        return mlPackage;
    }


    public static void writeWordForLocalTest(OutputStream response,
                                             String fileName,
                                             Map<String, Object> map) throws Exception {
        File file = new ClassPathResource("/template/word/" + fileName).getFile();
        String path = file.getPath();
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder()
                .bind("list", policy).build();
        XWPFTemplate template = XWPFTemplate.compile(path, config).render(map);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        template.write(byteArrayOutputStream);
        template.write(response);
        response.close();

    }


    public static void main(String[] args) throws Exception {
        InputStream inputStream = new ClassPathResource("/template/word/" + "test.docx").getInputStream();
        List<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> param = new HashMap<>();
        HashMap<String, Object> param1 = new HashMap<>();
        param1.put("Name", "king");
        param1.put("Age", 2);
        HashMap<String, Object> param2 = new HashMap<>();
        param2.put("Name", "king2");
        param2.put("Age", 3);
        HashMap<String, Object> param3 = new HashMap<>();
        param3.put("Name", "king3");
        param3.put("Age", 4);
        list.add(param1);
        list.add(param2);
        list.add(param3);
        param.put("abc", "ni_hao");
        param.put("list", list);
        File tempFile = new File(System.getProperty("user.dir"));
        File tempWordFile = new File(tempFile, "temp.docx");
        FileOutputStream tempOuPutStream = new FileOutputStream(tempWordFile);
        writeWordForLocalTest(tempOuPutStream, "test.docx", param);
    }


}
