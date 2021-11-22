//package revser.util.image;
//
//import cn.hutool.core.util.IdUtil;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//
///**
// * @Author: ChangYu
// * @Date: 2021/4/14 14:46
// * @Version 1.0
// */
//public class ImageUtil {
//
//    public static final String path="/home/kwj/images/";
//
//    /**
//     * 图片上传
//     * @param file
//     * @throws IOException
//     */
//    public void upload(MultipartFile file) throws IOException {
//        InputStream inputStream=file.getInputStream();
//        OutputStream outputStream = new FileOutputStream(path+file.getOriginalFilename());
//        int byteRead = 0;
//        byte[] buffer = new byte[8192];
//        while ((byteRead = inputStream.read(buffer)) > 0) {
//            outputStream.write(buffer, 0, byteRead);
//        }
//        outputStream.close();
//        inputStream.close();
//    }
//
//
//    /**
//     * 预览图片 直接输出到浏览器  非下载
//     * @param filename
//     * @param response
//     */
//    public static void preview(String filename, HttpServletResponse response) {
//        try {
//            InputStream io = new FileInputStream(path+filename);
//            int length;
//            byte[] buff = new byte[8096];
//            response.reset();
//            response.setContentType("image/png;charset=utf-8");
//            response.setHeader("Content-Disposition", "inline;filename=" + IdUtil.fastSimpleUUID());
//            ServletOutputStream outputStream = response.getOutputStream();// 将文件流写入响应中
//            while ((length = io.read(buff)) > 0) {
//                outputStream.write(buff, 0, length);
//            }
//            outputStream.flush();
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
