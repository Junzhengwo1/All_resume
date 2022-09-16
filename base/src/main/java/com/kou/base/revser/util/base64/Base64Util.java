//package revser.util.base64;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.*;
//
///**
// * 图片 与 Base64 互相转换
// * @author wangsong
// */
//@Slf4j
//public class Base64Util {
//
//    public static void main(String[] args) {
//        String imgBase64Str = Base64Util.imageToBase64ByLocal("C:\\Users\\wasong\\Desktop\\picture\\th.jpg");
//        System.out.println(imgBase64Str);
//
//        boolean b = Base64Util.base64ToImage(imgBase64Str,"C:/base64_temp/toImage.jpg");
//        System.out.println(b);
//    }
//
//
//
//
//    /**
//     * 本地图片转换成base64字符串
//     * @param imgFile	本地图片全路径 （注意：带文件名）
//     *  (将图片文件转化为字节数组字符串，并对其进行Base64编码处理)
//     * @return String base64
//     */
//    public static String imageToBase64ByLocal(String imgFile) {
//
//
//        byte[] data = null;
//
//        // 读取图片字节数组
//        try {
//            InputStream in = new FileInputStream(imgFile);
//
//            data = new byte[in.available()];
//            in.read(data);
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // 返回Base64编码过的字节数组字符串
//        return Base64Utils.encodeToString(data);
//    }
//
//
//
//
//    /**
//     * base64字符串转换成图片 (对字节数组字符串进行Base64解码并生成图片)
//     * @param imgStr		base64字符串
//     * @param imgFilePath	指定图片存放路径  （注意：带文件名）
//     * @return Boolean
//     */
//    public static boolean base64ToImage(String imgStr, String imgFilePath) {
//        // 图像数据为空
//        if (StringUtils.isEmpty(imgStr))
//        {
//            return false;
//        }
//
//        try {
//            // Base64解码
//            byte[] b = Base64Utils.decodeFromString(imgStr);
//            for (int i = 0; i < b.length; ++i) {
//                // 调整异常数据
//                if (b[i] < 0) {
//                    b[i] += 256;
//                }
//            }
//
//            OutputStream out = new FileOutputStream(imgFilePath);
//            out.write(b);
//            out.flush();
//            out.close();
//
//            return true;
//        } catch (Exception e) {
//            log.error("错误信息是:-->>{}", e.getMessage());
//            return false;
//        }
//
//    }
//
//
//
//}
