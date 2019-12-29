package com.zhaojm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTransportTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZipTransportTest.class);

    private static final byte[] BUF = new byte[1024];

    public static void main(String[] args) {
        byte[] result = FileToByte();
        System.out.println(result.length);

        try(FileOutputStream fos = new FileOutputStream(new File("E://test1222.zip"));){
            FileCopyUtils.copy(result, fos);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static byte[] FileToByte() {
        List<String> sourcePathList = new ArrayList<>();
        sourcePathList.add("D:\\Workspaces\\IDEASpace\\springboot-con\\springboot-hello\\src\\main\\resources\\static\\13.txt");
        sourcePathList.add("D:\\Workspaces\\IDEASpace\\springboot-con\\springboot-hello\\src\\main\\resources\\static\\14.txt");
        File zipFile = new File("E://test11.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile)) {
            createZip(sourcePathList, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fileInputStream = new FileInputStream(zipFile);) {
            int len;
            while ((len = fileInputStream.read(BUF)) > 0){
                baos.write(BUF, 0, len);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void createZip(List<String> sourcePathList, FileOutputStream sos) {
        try (ZipOutputStream zos = new ZipOutputStream(sos)) {
            for (String sourcePath : sourcePathList) {
                writeZip(new File(sourcePath), "", zos);
            }
        } catch (IOException e) {
            LOGGER.error("下载客户端失败", e);
        }
    }

    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.exists()) {
            if (file.isDirectory()) {//处理文件夹
                parentPath += file.getName() + File.separator;
                File[] files = file.listFiles();
                if (null != files && files.length != 0) {
                    for (File f : files) {
                        writeZip(f, parentPath, zos);
                    }
                } else { //空目录则创建当前目录
                    try {
                        zos.putNextEntry(new ZipEntry(parentPath));
                    } catch (IOException e) {
                        LOGGER.error("压缩失败", e);
                    }
                }
            } else {
                try(FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte[] content = new byte[1024];
                    int len;
                    while ((len = fis.read(content)) != -1) {
                        zos.write(content, 0, len);
                        zos.flush();
                    }
                } catch (IOException e) {
                    LOGGER.error("创建ZIP文件失败", e);
                }
            }
        }else {
            LOGGER.info("未找到文件{}", file.getPath());
        }
    }

}
