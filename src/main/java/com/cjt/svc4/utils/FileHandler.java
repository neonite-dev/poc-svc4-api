package com.cjt.svc4.utils;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.*;


public class FileHandler {

    public static String saveFile(MultipartFile file, String type, String userFilePath) throws IOException {
        //String dir = System.getProperty("user.dir");
        String temPath = "";

            if (type.equals("image")) {
                    temPath = "webdata/image";
            } else {
                    temPath = "webdata/board/"+ userFilePath;
            }

        File uploadDir = new File(temPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

         try {
             File uploadedFile = new File(uploadDir, file.getOriginalFilename());

            /*
            try (FileOutputStream fos = new FileOutputStream(uploadedFile)) {
                fos.write(file.getBytes());
            } */

            Path filePath = Paths.get(uploadedFile.getAbsolutePath());
            Files.write(filePath, file.getBytes());

            String url = "http://localhost:8080/"+ temPath + "/" + file.getOriginalFilename(); 
            return url;

        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}

