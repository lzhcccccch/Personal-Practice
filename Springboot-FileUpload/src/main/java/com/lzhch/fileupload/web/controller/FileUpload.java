package com.lzhch.fileupload.web.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * @packageName： com.lzhch.fileupload.controller
 * @className: FileUpload
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-20 15:11
 */
@RestController
@RequestMapping("/upload")
public class FileUpload {

    @Value("${common.file.native.fileUploadTempDir}")
    //private static String fileUploadTempDir = "/Users/liuzhichao/fileupload";
    private String fileUploadTempDir;
    @Value("${common.file.native.fileUploadDir}")
    //private static String fileUploadDir = "/Users/liuzhichao/fileupload";
    private String fileUploadDir;

    @RequestMapping("/doPost")
    public Map fragmentation(HttpServletRequest req, HttpServletResponse resp) {
        // 设置跨域访问, * 表示允许所有域名
        resp.addHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> map = new HashMap<>();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;

        // 获得文件分片数据
        MultipartFile file = multipartRequest.getFile("data");
//        分片第几片
        int index = Integer.parseInt(multipartRequest.getParameter("index"));
//        总片数
        int total = Integer.parseInt(multipartRequest.getParameter("total"));
//        获取文件名
        String fileName = multipartRequest.getParameter("name");
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        String fileEnd = fileName.substring(fileName.lastIndexOf("."));
//        前端uuid，作为标识
        String uuid = multipartRequest.getParameter("uuid");

        File uploadFile = new File(fileUploadTempDir + "/" + uuid, uuid + name + index + ".tem");

        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }

        if (index < total) {
            try {
                file.transferTo(uploadFile);
                // 上传的文件分片名称
                map.put("status", 201);
                return map;
            } catch (IOException e) {
                e.printStackTrace();
                map.put("status", 502);
                return map;
            }
        } else {
            try {
                file.transferTo(uploadFile);
                // 上传的文件分片名称
                map.put("status", 200);
                return map;
            } catch (IOException e) {
                e.printStackTrace();
                map.put("status", 502);
                return map;
            }
        }
    }
    @RequestMapping(value = "/merge", method = RequestMethod.GET)
    //@ResponseBody
    public Map merge(String uuid, String newFileName) {
        Map retMap = new HashMap();
        // 临时文件的文件夹目录
        String tempFileDir = fileUploadTempDir + "/" + uuid;
        try {
            File dirFile = new File(tempFileDir);
            if (!dirFile.exists()) {
                throw new RuntimeException("文件不存在！");
            }
            // 分片上传的文件已经位于同一个文件夹下，方便寻找和遍历（当文件数大于十的时候记得排序用冒泡排序确保顺序是正确的）
            String[] fileNames = dirFile.list();

            // 创建空的合并文件
            File targetFile = new File(fileUploadDir, newFileName);
            RandomAccessFile writeFile = new RandomAccessFile(targetFile, "rw");

            int position = 0;
            for (String fileName : fileNames) {
                System.out.println(fileName);
                File sourceFile = new File(tempFileDir, fileName);
                RandomAccessFile readFile = new RandomAccessFile(sourceFile, "rw");
                int chunksize = 1024 * 3;
                byte[] buf = new byte[chunksize];
                writeFile.seek(position);
                int byteCount = 0;
                while ((byteCount = readFile.read(buf)) != -1) {
                    if (byteCount != chunksize) {
                        byte[] tempBytes = new byte[byteCount];
                        System.arraycopy(buf, 0, tempBytes, 0, byteCount);
                        buf = tempBytes;
                    }
                    writeFile.write(buf);
                    position = position + byteCount;
                }
                readFile.close();
                FileUtils.deleteQuietly(sourceFile);//删除缓存的临时文件
            }
            writeFile.close();
            File tempFileDirectory = new File(fileUploadTempDir + "/" + uuid);
            if (tempFileDirectory.exists()) {
                FileUtils.deleteDirectory(tempFileDirectory);
            }
            retMap.put("code", "200");
        }catch (IOException e){
            e.printStackTrace();
            retMap.put("code", "500");
        }
        return retMap;
    }

}
