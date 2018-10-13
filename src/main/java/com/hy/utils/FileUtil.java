package com.hy.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception
    {
        File targetFile = new File(filePath);
        if(!targetFile.exists())
        {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+"/"+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
    public static String upload(MultipartFile[] file, String UPLOAD_URL, String location){
        String fileName = file[0].getOriginalFilename();
        try {
            File path = new File(ResourceUtils.getURL("file:").getPath()
                    + location + UPLOAD_URL);
            String filePath = path.getAbsolutePath();
            FileUtil.uploadFile(file[0].getBytes(), filePath, fileName);
            return UPLOAD_URL + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
