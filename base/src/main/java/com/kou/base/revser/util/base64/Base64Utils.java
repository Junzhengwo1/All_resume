//package revser.util.base64;
//
//import cn.hutool.core.img.ImgUtil;
//import cn.hutool.core.util.StrUtil;
//import com.capgemini.estate.external.oa.mapper.FileMapper;
//import com.capgemini.estate.external.oa.serviceImpl.OaFileService;
//import com.capgemini.estate.framework.base.exception.ServiceException;
//import com.google.common.collect.ImmutableMap;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Service;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
//
//import javax.annotation.Resource;
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Base64;
//import java.util.Collections;
//import java.util.Date;
//import java.util.Map;
//
///**
// * base64转图片
// * 注意: 该方法和第三方系统对接后可用!
//
// */
//@Service
//@Slf4j
//public class Base64Utils {
//
//    @Resource
//    OaFileService oafileService;
//
//    @Resource
//    FileMapper fileMapper;
//
//    /**
//     * 图片转化成base64字符串
//     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
//     * @param imgPath 图片路径
//     * @return String
//     */
//    @SuppressWarnings("all")
//    public static String getImageStr(String imgPath) {
//        // 待处理的图片
//        InputStream in = null;
//        byte[] data = null;
//        // 返回Base64编码过的字节数组字符串
//        String encode = null;
//        // 对字节数组Base64编码
//        BASE64Encoder encoder = new BASE64Encoder();
//        try {
//            // 读取图片字节数组
//            in = new FileInputStream(imgPath);
//            data = new byte[in.available()];
//            in.read(data);
//            encode = encoder.encode(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return encode;
//    }
//
//    /**
//     * base64字符串转化成图片
//     *
//     * @param imgData 图片base64编码
//     * @param imgFilePath 存放到本地路径
//     * @return Boolean
//     * @throws IOException 文件存储异常
//     */
//    @SuppressWarnings("all")
//    public static boolean generateImage(String imgData, String imgFilePath) throws IOException { // 对字节数组字符串进行Base64解码并生成图片
//        // 图像数据为空
//        if (imgData == null)
//        {
//            return false;
//        }
//        BASE64Decoder decoder = new BASE64Decoder();
//        OutputStream out = null;
//        try {
//            out = new FileOutputStream(imgFilePath);
//            // Base64解码
//            byte[] b = decoder.decodeBuffer(imgData);
//            for (int i = 0; i < b.length; ++i) {
//                // 调整异常数据
//                if (b[i] < 0) {
//                    b[i] += 256;
//                }
//            }
//            out.write(b);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            out.flush();
//            out.close();
//            return true;
//        }
//    }
//
//
//    @SuppressWarnings("all")
//    public String base64ToImage(String fileId) throws IOException {
//
//        //base64转图片
//        Integer integer = fileMapper.selectCount(null);
//        System.out.println("sit环境下的数据条数-->" + integer);
//
//        //===第三方系统接入后 放开注释===
//        String imageStr = oafileService.getBase64Str(fileId);
////        String imageStr  = null;
//        log.warn("取到的base64字符串是===>>>{}", imageStr);
//        if (StringUtils.isBlank(imageStr)) {
//            throw new ServiceException("获取第三方系统数据为空");
//        }
//
//        String fileName = this.pathGenerate();
//        String tempImagePath = "C:\\base64_temp\\" + fileName + ".jpg";
//        boolean flag = Base64Utils.generateImage(imageStr, tempImagePath);
//
//        if (!flag) {
//            throw  new ServiceException("base64转图片失败");
//        }
//        return tempImagePath;
//    }
//
//    /**
//     * 生成文件名称
//     * @return String
//     */
//    private String pathGenerate() {
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date();
//        String fileName = format.format(date);
//        log.info("----->{}", fileName);
//        return fileName;
//    }
//
//    public String getBase64Str(String fileId){
//        return oafileService.getBase64Str(fileId);
//    }
//    public static Map<Integer,Integer> getBase64ImgWithHeight(String base64Str){
//        if(StrUtil.isBlank(base64Str))return Collections.emptyMap();
//        OutputStream ot = new ByteArrayOutputStream();
//
//        Base64.decodeToStream(base64Str,ot,false);
//        ByteArrayInputStream it = parse(ot);
//        BufferedImage bi = ImgUtil.read(it);
//
//        return ImmutableMap.of(0,bi.getWidth(),1,bi.getHeight());
//    }
//    public static String base64ImgResize2(String base64Str, int resize2) throws IOException {
//        if(StringUtils.isBlank(base64Str))return "";
//        OutputStream ot = new ByteArrayOutputStream();
//
//        Base64.decodeToStream(base64Str,ot,false);
//        ByteArrayInputStream it = parse(ot);
//        BufferedImage bi = ImgUtil.read(it);
//        BufferedImage bi2 = resize(resize2, bi);
//        InputStream resInpt = bufferedImageToInputStream(bi2);
//        String res =Base64.encode(resInpt);
//
//        System.out.println(res);
//        return res;
//
//
//    }
//    public static InputStream bufferedImageToInputStream(BufferedImage image) throws IOException {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        ImageIO.write(image, "PNG", os);
//        return new ByteArrayInputStream(os.toByteArray());
//    }
//    public static ByteArrayInputStream parse(final OutputStream out) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        baos = (ByteArrayOutputStream) out;
//        byte[] b = baos.toByteArray();
//        final ByteArrayInputStream swapStream = new ByteArrayInputStream(b);
//        return swapStream;
//    }
//    public static BufferedImage resize(int faceWidth,BufferedImage srcImg){
//
//        int imgWidth = 0;
//        int widthOrigin = srcImg.getWidth();
//        int heightOrigin = srcImg.getHeight();
//        if(widthOrigin<faceWidth){
//            imgWidth=widthOrigin;
//        }else{
//            imgWidth=faceWidth;
//        }
//        int imgHeight = new BigDecimal(String.valueOf(imgWidth)).divide(new BigDecimal(String.valueOf(widthOrigin)),5,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(String.valueOf(heightOrigin))).intValue();
//        //构建新的图片
//        BufferedImage resizedImg = new BufferedImage(imgWidth,imgHeight,BufferedImage.TYPE_INT_RGB);
//        //将原图放大或缩小后画下来:并且保持png图片放大或缩小后背景色是透明的而不是黑色
//        Graphics2D resizedG = resizedImg.createGraphics();
//        resizedImg = resizedG.getDeviceConfiguration().createCompatibleImage(imgWidth,imgHeight,Transparency.TRANSLUCENT);
//        resizedG.dispose();
//        resizedG = resizedImg.createGraphics();
//        Image from = srcImg.getScaledInstance(imgWidth, imgHeight, srcImg.SCALE_AREA_AVERAGING);
//        resizedG.drawImage(from, 0, 0, null);
//        resizedG.dispose();
//
//        return resizedImg;
//    }
//
//}
//
//
//
//
