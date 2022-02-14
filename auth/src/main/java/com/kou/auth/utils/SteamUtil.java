package com.kou.auth.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SteamUtil {

    public static InputStream bufferedImageToInputStream(BufferedImage image) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", os);
        return new ByteArrayInputStream(os.toByteArray());
    }
}
