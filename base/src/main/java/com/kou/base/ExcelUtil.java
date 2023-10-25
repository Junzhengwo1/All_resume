package com.csin.platform.core.util;

import com.csin.platform.conf.SystemConfig;
import com.csin.platform.core.common.ApiException;
import com.csin.platform.core.common.ApiResponseCode;
import com.csin.platform.core.common.Constants;
import com.csin.platform.core.common.CustomConstants;
import com.csin.platform.core.common.SpringBeanContext;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.csin.platform.core.util.TimeUtil.DATE_FORMAT;

/**
 * @Author wuxh
 * @Date 2022/6/17 14:34
 * @description
 */
public class ExcelUtil {

    public static void write(ExcelTitle[] titles, List<Map<String, Object>> data, OutputStream outputStream) {
        write(titles, data, outputStream, Lists.newArrayList());
    }

    /**
     * 设置图片单元格
     */
    private static void setImgCell(String urls, Row row, XSSFDrawing drawing, Workbook workbook, Sheet sheet, int start) {
        SystemConfig config = SpringBeanContext.getBean(SystemConfig.class);
        int startRow = row.getRowNum();
        int endRow = startRow + 1;
        String[] uris = urls.split(CustomConstants.SEMICOLON);
        AtomicInteger i = new AtomicInteger(0);
        for (String uri : uris) {
            Cell cell = row.createCell(start + i.getAndIncrement());
            int startCol = cell.getColumnIndex();
            int endCol = startCol + 1;
            try {
                File file = new File(config.getAttachment() + uri.replace(config.getAttachUrl(), Strings.EMPTY));
                BufferedImage image = ImageIO.read(file);
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                ImageIO.write(image, "png", byteArrayOut);
                XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, startCol, startRow, endCol, endRow);
                anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_DONT_RESIZE);
                int index = workbook.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PICT);
                drawing.createPicture(anchor, index);
                row.setHeight((short) 2000);
                sheet.setColumnWidth(startCol, 40 * 256);
            } catch (Exception e) {
                cell.setCellValue(config.getAttachUrl() + uri.replace(config.getAttachUrl(), Strings.EMPTY));
            }
        }
    }

    public static void write(ExcelTitle[] titles, List<Map<String, Object>> data, HttpServletResponse response, String fileName) {
        write(titles, data, response, fileName, Lists.newArrayList());
    }

    public static void write(ExcelTitle[] titles, List<Map<String, Object>> data, HttpServletResponse response, String fileName, List<String> imgFields) {
        response.setContentType("application/octet-stream");
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader(Constants.HEAD_CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            write(titles, data, response.getOutputStream(), imgFields);
        } catch (Exception e) {
            throw new ApiException(ApiResponseCode.SERVER_ERROR.getCode(), "导出失败！");
        }
    }

    public static void write(ExcelTitle[] titles, List<Map<String, Object>> data, OutputStream outputStream, List<String> imgFields) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            //设置excel表头字段
            Row row = sheet.createRow(CustomConstants.ZERO);
            AtomicInteger index = new AtomicInteger(CustomConstants.ZERO);
            AtomicInteger i = new AtomicInteger(CustomConstants.ZERO);
            while (index.get() < titles.length) {
                ExcelTitle title = titles[index.getAndIncrement()];
                int start = i.getAndAdd(title.num);
                Cell cell = row.createCell(start);
                if (title.num > 1) {
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, start, start + title.num - 1));
                }
                cell.setCellValue(title.title);
            }
            //设置业务数据
            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            AtomicInteger vIndex = new AtomicInteger(CustomConstants.ZERO);
            while (vIndex.get() < data.size()) {
                int vi = vIndex.getAndIncrement();
                Row dataRow = sheet.createRow(vi + CustomConstants.ONE);
                index.set(CustomConstants.ZERO);
                i.set(CustomConstants.ZERO);
                while (index.get() < titles.length) {
                    ExcelTitle title = titles[index.getAndIncrement()];
                    int start = i.getAndAdd(title.num);
                    Object o = data.get(vi).get(title.field);
                    if (Objects.isNull(o)) {
                        dataRow.createCell(start).setCellValue("--");
                        continue;
                    }
                    if (imgFields.contains(title.field)) {
                        setImgCell((String) o, dataRow, drawing, workbook, sheet, start);
                        continue;
                    }
                    String value = o instanceof String ? (String) o : o.toString();
                    dataRow.createCell(start).setCellValue(StringUtils.isBlank(value) ? "--" : value);
                }
            }
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            throw new ApiException(ApiResponseCode.SERVER_ERROR.getCode(), "导出失败！");
        }
    }

    public static void write(ExcelTitle[] titles, List<Map<String, Object>> data, OutputStream outputStream, String title) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Sheet sheet = workbook.createSheet();
        int start = StringUtils.isBlank(title) ? CustomConstants.ZERO : CustomConstants.ONE;
        if (StringUtils.isNotBlank(title)) {
            Row row = sheet.createRow(CustomConstants.ZERO);
            Cell cell = row.createCell(CustomConstants.ZERO);
            cell.setCellValue(title);
            cell.setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(CustomConstants.ZERO, CustomConstants.ZERO, CustomConstants.ZERO, titles.length - CustomConstants.ONE));
        }
        //设置excel表头字段
        Row row = sheet.createRow(start);
        AtomicInteger index = new AtomicInteger(CustomConstants.ZERO);
        while (index.get() < titles.length) {
            int i = index.getAndIncrement();
            Cell cell = row.createCell(i);
            cell.setCellValue(titles[i].getTitle());
            cell.setCellStyle(cellStyle);
        }
        //设置业务数据
        index.set(CustomConstants.ZERO);
        while (index.get() < data.size()) {
            int i = index.getAndIncrement();
            Row dataRow = sheet.createRow(i + start + CustomConstants.ONE);
            setDataRow(dataRow, data.get(i), titles, cellStyle, sheet);
        }
        //导出数据流
        try {
            workbook.write(outputStream);
        } finally {
            workbook.close();
        }
    }

    /**
     * 设置数据行
     */
    private static void setDataRow(Row row, Map<String, Object> data, ExcelTitle[] titles, CellStyle cellStyle, Sheet sheet) {
        AtomicInteger index = new AtomicInteger(CustomConstants.ZERO);
        while (index.get() < titles.length) {
            int i = index.getAndIncrement();
            Cell cell = row.createCell(i);
            Object value = data.get(titles[i].getField());
            if (Objects.isNull(value)) {
                continue;
            }
            cell.setCellValue(value.toString());
            cell.setCellStyle(cellStyle);

            int width = sheet.getColumnWidth(i);
            if (width < value.toString().getBytes().length * 2 * 256) {
                sheet.autoSizeColumn(i, true);
            }
        }
    }

    /**
     * 小程序生成excel表
     */
    public static Map<String, String> writeExcel(ExcelTitle[] titles, List<Map<String, Object>> data, String module, String filename) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        for (int i = 0; i < titles.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titles[i].getTitle());
            sheet.getRow(0).getCell(i).setCellStyle(cellStyle);
        }
        for (int i = 0; i < data.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = dataRow.createCell(j);
                Object o = data.get(i).get(titles[j].getField());
                sheet.getRow(i + 1).getCell(j).setCellStyle(cellStyle);
                if (o == null) {
                    continue;
                }
                if (o instanceof String) {
                    cell.setCellValue((String) o);
                } else {
                    cell.setCellValue(o.toString());
                }
            }
        }
        for (int k = 0; k < titles.length; k++) {
            sheet.autoSizeColumn(k);
        }
        // 处理中文不能自动调整列宽的问题
        setSizeColumn(sheet, titles.length);
        return FileUtil.uploadExcel(workbook, module, filename);
    }

    /**
     * 自适应宽度(中文支持)
     */
    private static void setSizeColumn(Sheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum);
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length * 276;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, Math.min(columnWidth, 10000));
        }
    }

    /**
     * 解析excel
     */
    private static List<Map<String, Object>> read(ExcelTitle[] titles, InputStream inputStream, int start) {
        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheetAt = workbook.getSheetAt(0);
            Row row1 = sheetAt.getRow(start);
            AtomicInteger index = new AtomicInteger(0);
            while (index.get() < titles.length) {
                int i = index.getAndIncrement();
                String title = titles[i].getTitle();
                Cell cell = row1.getCell(i);
                if (title.equals(cell.getStringCellValue())) {
                    continue;
                }
                throw new RuntimeException("列名称对不上！");
            }
            index.set(0);
            List<Map<String, Object>> rows = new ArrayList<>();
            for (Row cells : sheetAt) {
                if (index.getAndIncrement() <= start) {
                    continue;
                }
                LinkedHashMap<String, Object> row = new LinkedHashMap<>();
                for (int j = 0; j < titles.length; j++) {
                    String field = titles[j].getField();
                    Cell cell = cells.getCell(j);
                    if (Objects.isNull(cell)) {
                        row.put(field, Strings.EMPTY);
                    } else if (cell.getCellType() == CellType.NUMERIC && cell.toString().length() > 7) {
                        row.put(field, TimeUtil.format(cell.getLocalDateTimeCellValue(), DATE_FORMAT));
                    } else {
                        row.put(field, new DataFormatter().formatCellValue(cell));
                    }
                }
                rows.add(row);
            }
            return rows;
        } catch (Exception e) {
            throw new ApiException(ApiResponseCode.SERVER_ERROR.getCode(), "导入失败！模板不正确");
        }
    }

    /**
     * 解析excel
     */
    public static List<Map<String, Object>> read(ExcelTitle[] titles, InputStream inputStream) {
        return read(titles, inputStream, 0);
    }

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static ExcelTitle title(String title, String field) {
        return new ExcelTitle(title, field, 1);
    }

    public static ExcelTitle title(String title, String field, int num) {
        return new ExcelTitle(title, field, num);
    }

    @Data
    public static class ExcelTitle {
        private String title;
        private String field;
        /**
         * 单元格数量
         */
        private int num;

        ExcelTitle(String title, String field, int num) {
            this.title = title;
            this.field = field;
            this.num = num;
        }
    }

    public static String parseEmptyValue(String value) {
        return StringUtils.isBlank(value) ? "--" : value;
    }

    public static List<Map<String, Object>> read(ExcelTitle[] titles, MultipartFile excel) {
        return read(titles, excel, 0);
    }

    public static List<Map<String, Object>> read(ExcelTitle[] titles, MultipartFile excel, int start) {
        try {
            return read(titles, excel.getInputStream(), start);
        } catch (IOException e) {
            throw new ApiException(ApiResponseCode.SERVER_ERROR.getCode(), "导入失败！模板不正确");
        }
    }

    /**
     * 设置图片数量
     */
    public static void setImageSize(String img, AtomicInteger size) {
        if (StringUtils.isBlank(img)) {
            return;
        }
        int num = img.split(CustomConstants.SEMICOLON).length;
        if (size.get() < num) {
            size.set(num);
        }
    }
}

