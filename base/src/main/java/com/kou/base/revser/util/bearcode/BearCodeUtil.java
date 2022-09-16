//package revser.util.bearcode;
//
//import com.capgemini.estate.framework.util.base64.Base64Utils;
//import org.krysalis.barcode4j.HumanReadablePlacement;
//import org.krysalis.barcode4j.impl.code128.Code128Bean;
//import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
//import org.krysalis.barcode4j.tools.UnitConv;
//import sun.misc.BASE64Encoder;
//
//import java.awt.image.BufferedImage;
//import java.io.*;
//
//public class BearCodeUtil {
//
//    /**
//     * 生成文件
//     *
//     * @param msg
//     * @param path
//     * @return
//     */
//    public static File generateFile(String msg, String path) {
//        File file = new File(path);
//        try {
//            generate(msg, new FileOutputStream(file));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        return file;
//    }
//
//    /**
//     * 流转化成byte
//     */
//
//    public static byte[] steamToByte(InputStream input) throws IOException{
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int len = 0;
//        byte[] b = new byte[1024];
//        while ((len = input.read(b, 0, b.length)) != -1) {
//            baos.write(b, 0, len);
//        }
//        byte[] buffer =  baos.toByteArray();
//        return buffer;
//    }
//    /**
//     * 将流直接生成Base64
//     * @param msg
//     * @return
//     */
//    public static String generateBase64(String msg) throws IOException {
//        ByteArrayOutputStream ous = new ByteArrayOutputStream();
//        BufferedImage generate = generate(msg, ous);
//        InputStream inputStream = Base64Utils.bufferedImageToInputStream(generate);
//        byte[] out = steamToByte(inputStream);
//        BASE64Encoder encoder = new BASE64Encoder();
//        String encode = encoder.encode(out);
//        return encode;
//    }
//
//
//
//
//    /**
//     * 生成到流
//     * @param msg
//     * @param ous
//     */
//    public static BufferedImage generate(String msg, OutputStream ous) {
//        if (ous == null) {
//            return null;
//        }
//        Code128Bean bean = new Code128Bean();//128格式
//        // 精细度
//        final int dpi = 128;
//        // module宽度
//        final double moduleWidth = UnitConv.in2mm(2.0f / dpi);
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        //((Code39Bean) bean).setWideFactor(3);
//        bean.doQuietZone(true);//保留两边空白区
//        bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);//不显示数值
//        String format = "image/png";
//
//        try {
//            // 输出到流
//            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
//                    BufferedImage.TYPE_BYTE_BINARY, false, 0);
//            // 生成条形码
//            bean.generateBarcode(canvas, msg);
//            // 结束绘制
//            BufferedImage bufferedImage = canvas.getBufferedImage();
//            canvas.finish();
//            return bufferedImage;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    /**
//     * 生成到流
//     * @param msg
//     * @param ous
//     */
//    public static void generate2(String msg, OutputStream ous) {
//        if (ous == null) {
//            return;
//        }
//        Code128Bean bean = new Code128Bean();//128格式
//        // 精细度
//        final int dpi = 128;
//        // module宽度
//        final double moduleWidth = UnitConv.in2mm(2.0f / dpi);
//        // 配置对象
//        bean.setModuleWidth(moduleWidth);
//        //((Code39Bean) bean).setWideFactor(3);
//        bean.doQuietZone(true);//保留两边空白区
//        bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);//不显示数值
//        String format = "image/png";
//        try {
//            // 输出到流
//            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
//             BufferedImage.TYPE_BYTE_BINARY, false, 0);
//            // 生成条形码
//            bean.generateBarcode(canvas, msg);
//            // 结束绘制
//            canvas.finish();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    //    /**
////     * 生成字节
////     * @param msg
////     * @return
////     */
////    public static byte[] generate(String msg) {
////        ByteArrayOutputStream ous = new ByteArrayOutputStream();
////        generate(msg, ous);
////        return ous.toByteArray();
////
////    }
////    public static void main(String[] args) throws IOException {
//////        String path = "C:\\TEM2\\barcode.png";
//////        File file = generateFile("PC202107190001", path);
////        String path2 = "C:\\TEM233\\barcode.png";
////        BufferedImage image = generate("PC202107190001", new ByteArrayOutputStream());
////        InputStream inputStream = Base64Utils.bufferedImageToInputStream(image);
////        byte[] out = steamToByte(inputStream);
////        BASE64Encoder encoder = new BASE64Encoder();
////        String encode = encoder.encode(out);
////       boolean b = Base64Utils.generateImage(encode, path2);
////
//////        String imageStr = Base64Utils.getImageStr(file.getAbsolutePath());
//////        boolean b = Base64Utils.generateImage(imageStr, path2);
//////        System.out.println(b);
//////        System.out.println(file);
////
////    }
//
////    public static void main(String[] args) throws IOException {
////        String base64 = generateBase64("PC202107190001");
////        String path = "C:\\TEM233\\barcode.png";
////        boolean b = Base64Utils.generateImage(base64, path);
////
////    }
//
//
//}
