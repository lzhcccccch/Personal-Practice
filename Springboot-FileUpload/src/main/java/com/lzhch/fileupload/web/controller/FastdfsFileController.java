package com.lzhch.fileupload.web.controller;

import com.lzhch.fileupload.web.utils.FastdfsClientUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @packageName： com.lzhch.fileupload.web.controller
 * @className: FastdfsFileController
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-24 13:59
 */
@RestController
@RequestMapping("/fastdfs/file/api")
public class FastdfsFileController {

    @Autowired
    private FastdfsClientUtil fdfsClient;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/upload")
    public Map<String,Object> upload(MultipartFile file) throws Exception{

        String url = fdfsClient.uploadFile(file);

        Map<String,Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "上传成功");
        result.put("url", url);

        return result;
    }

    /**
     * 文件下载
     * @param fileUrl  url 开头从组名开始
     * @param response
     * @throws Exception
     */
    @RequestMapping("/download")
    public void  download(String fileUrl, HttpServletResponse response) throws Exception{

        byte[] data = fdfsClient.download(fileUrl);

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("test.jpg", "UTF-8"));

        // 写出
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }

}
