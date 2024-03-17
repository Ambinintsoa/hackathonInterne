package com.apollo.art.chat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Component
public class FileHelper {

    private String uploadFolder = "D:\\hackathon\\src\\main\\resources\\static\\";

    public final static String PNG = "png";
    public final static String JPG = "jpg";
    public final static String JPEG = "jpeg";

    public final static String PDF = "pdf";

    public final static String MP3 = "mp3";
    public final static String MP4 = "mp4";

    public String upload(String base64Image) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
        System.out.println(uploadFolder);
        File folder = new File(uploadFolder);
        String fileName = folder.getName() + System.currentTimeMillis() + "." + PNG;
        File destinationFile = new File(folder, fileName);
        try (FileOutputStream fos = new FileOutputStream(destinationFile)) {
            fos.write(decodedBytes);
            System.out.println("File uploaded successfully: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();

            throw e;
        }

        return fileName;
    }
}
