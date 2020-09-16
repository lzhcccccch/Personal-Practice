package com.lzhch.fileupload.web.utils;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @packageName： com.lzhch.fileupload.web.utils
 * @className: FastdfsClientUtil
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-24 15:16
 */
@Component
public class FastdfsClientUtil {

    private final Logger logger = LoggerFactory.getLogger(FastdfsClientUtil.class);

    @Autowired
    private FastFileStorageClient storageClient;

    //private DefaultAppendFileStorageClient defaultAppendFileStorageClient;
    @Autowired
    private AppendFileStorageClient defaultAppendFileStorageClient;

    @Autowired
    private FdfsWebServer fdfsWebServer;

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return getResAccessUrl(storePath);
    }

    /**
     * @description: 分片上传至 fastdfs 服务器
     * @param: [file, fileType]
     * @return: java.lang.String
     * @author: liuzhichao 2020-07-29 $time
     */
    public String uploadFile(MultipartFile file, String fileType) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), fileType,null);
        return getResAccessUrl(storePath);
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream (file);
        StorePath storePath = storageClient.uploadFile(inputStream,file.length(), FilenameUtils.getExtension(file.getName()),null);
        return getResAccessUrl(storePath);
    }

    /**
     * 将一段字符串生成一个文件上传
     * @param content 文件内容
     * @param fileExtension
     * @return
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        return getResAccessUrl(storePath);
    }

    // 封装图片完整URL地址
    private String getResAccessUrl(StorePath storePath) {
        // fdfsWebServer.getWebServerUrl() 需要在配置文件中配置 fdfs.web-server-url (配置为 nginx 地址)
        //String fileUrl = fdfsWebServer.getWebServerUrl() + storePath.getFullPath();
        String fileUrl = storePath.getFullPath();
        // 返回的路径可以在浏览器中直接进行访问
        return fileUrl;
    }

    /**
     * 下载文件
     * @param fileUrl 文件url
     * @return
     */
    public byte[]  download(String fileUrl) {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        byte[] bytes = storageClient.downloadFile(group, path, new DownloadByteArray());
        return bytes;
    }

    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
    }

}
