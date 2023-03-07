package com.myomi.s3;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.tika.Tika;

public class FileUtils {

    private static final Tika tika = new Tika();

    public static boolean validImgFile(InputStream inputStream) {
        try {
            List<String> notValidTypeList = Arrays.asList("image/jpeg", "image/pjpeg", "image/png", "image/gif", "image/bmp", "image/x-windows-bmp");

            String mimeType = tika.detect(inputStream);

            boolean isValid = notValidTypeList.stream().anyMatch(notValidType -> notValidType.equalsIgnoreCase(mimeType));

            return isValid;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}