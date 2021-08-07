package myselfUtils.StreamUtils;

import sun.misc.BASE64Encoder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Stream {


    /**
     * 流转化成byte
     */

    public static byte[] steamToByte(InputStream input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = input.read(b, 0, b.length)) != -1) {
            baos.write(b, 0, len);
        }
        byte[] buffer =  baos.toByteArray();
        return buffer;
    }
    /**
     * 将流直接生成Base64
     * @param msg
     * @return
     */
//    public static String generateBase64(String msg) throws IOException {
//        ByteArrayOutputStream ous = new ByteArrayOutputStream();
//        BufferedImage generate = generate(msg, ous);
//        InputStream inputStream = Base64Utils.bufferedImageToInputStream(generate);
//        byte[] out = steamToByte(inputStream);
//        BASE64Encoder encoder = new BASE64Encoder();
//        String encode = encoder.encode(out);
//        return encode;
//    }



}
