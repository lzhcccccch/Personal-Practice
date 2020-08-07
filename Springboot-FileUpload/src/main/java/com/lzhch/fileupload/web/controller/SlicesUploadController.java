package com.lzhch.fileupload.web.controller;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.lzhch.fileupload.feign.IFileUpload;
import com.lzhch.fileupload.feign.dto.req.FileSlicesFdfsReq;
import com.lzhch.fileupload.feign.dto.req.FileUploadReq;
import com.lzhch.fileupload.feign.dto.res.FileDownloadRes;
import com.lzhch.fileupload.feign.dto.res.FileSlicesFdfsRes;
import com.lzhch.fileupload.web.utils.FastdfsClientUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @packageName： com.lzhch.fileupload.web.controller
 * @className: SlicesUploadController
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-27 16:13
 */
@RestController
@RequestMapping("/slices/file/upload/api")
public class SlicesUploadController {

    @Autowired
    private FastdfsClientUtil fdfsClient;
    @Autowired
    private IFileUpload iFileUpload;
    @Autowired
    private FdfsWebServer fdfsWebServer;

    @RequestMapping("/upload")
    public Map upload(HttpServletRequest request, HttpServletResponse response) {

        // 设置跨域访问, * 表示允许所有域名
        response.addHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> map = new HashMap<>();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件分片数据
        MultipartFile file = multipartRequest.getFile("data");
        // 分片第几片
        int index = Integer.parseInt(multipartRequest.getParameter("index"));
        // 总片数
        int total = Integer.parseInt(multipartRequest.getParameter("total"));
        // 获取文件名
        String fileFullName = multipartRequest.getParameter("name");
        int lastIndexOf = fileFullName.lastIndexOf(".");
        // 文件名字, 不带类型
        String fileName = fileFullName.substring(0, lastIndexOf);
        String fileType = fileFullName.substring(lastIndexOf+1);
        /**
         *  是否可以将上传至 fastdfs 的过程写作多线程, 将上传 fastdfs 和 MySQL 存储操作进行分离???
         */
        // fastdfs 存储路径
        String url = "";
        if (index < total) {
            try {
                url = fdfsClient.uploadFile(file, fileType);
                System.out.println("----slicesUrl[" + index + "]:" + url);
                map.put("status", 201);
            } catch (IOException e) {
                e.printStackTrace();
                map.put("status", 502);
            }
        } else {
            try {
                url = fdfsClient.uploadFile(file, fileType);
                System.out.println("----slicesUrl[" + index + "]:" + url);
                map.put("status", 200);
            } catch (IOException e) {
                e.printStackTrace();
                map.put("status", 502);
            }
        }

        // 分片上传至 fastdfs 之后, 存储至 MySQL

        // 前端uuid, 作为标识
        String uuid = multipartRequest.getParameter("uuid");
        // 文件总大小
        int totalSize = Integer.parseInt(multipartRequest.getParameter("totalSize"));
        // 当前分片文件大小
        int slicesSize = Integer.parseInt(multipartRequest.getParameter("slicesSize"));

        FileSlicesFdfsReq slicesFdfdReq = new FileSlicesFdfsReq();
        slicesFdfdReq.setFileSlicesIndex(index);
        slicesFdfdReq.setFileFdfsPath(url);
        String fileFullPath = fdfsWebServer.getWebServerUrl() + url;
        slicesFdfdReq.setFileFullPath(fileFullPath);
        slicesFdfdReq.setUuid(uuid);
        slicesFdfdReq.setFileSlicesSize(slicesSize);
        slicesFdfdReq.setCreateTime(new Date());

        iFileUpload.uploadSlicesFile(slicesFdfdReq);
        System.out.println("------add slicesFile success, index=" + index);

        if (index == total) {
            FileUploadReq fileReq = new FileUploadReq();
            fileReq.setFileOriginalName(fileFullName);
            fileReq.setFileType(fileType);
            fileReq.setFileSize(totalSize);
            fileReq.setFileSlicesTotal(total);
            fileReq.setUuid(uuid);
            fileReq.setCreateTime(new Date());
            iFileUpload.uploadFile(fileReq);
            System.out.println("------add File success, name=" + fileFullName);
        }

        return map;
    }

    //@RequestMapping(value = "/download", method = RequestMethod.POST)
    @RequestMapping("/download")
    public Map<String, Object> download(String uuid, String fileOriginalName, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>(1);
        List<FileSlicesFdfsRes> list = iFileUpload.downloadSlicesFile(uuid);
        // 设置跨域访问, * 表示允许所有域名
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (list!=null && list.size()>0) {
            listSort(list);
            for (FileSlicesFdfsRes item: list) {
                byte[] data = fdfsClient.download(item.getFileFdfsPath());

                // 写出
                try {
                    //ServletOutputStream outputStream = response.getOutputStream();
                    //response.reset();
                    response.setCharacterEncoding("UTF-8");
                    // URLEncoder.encode(name, "UTF-8") 第一个参数是浏览器下载时文件的默认名字, 第二个是编码类型
                    response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileOriginalName, "UTF-8"));
                    // 为防止文件名出现乱码
                    //response.setHeader("Content-Type", "application/octet-stream");
                    ServletOutputStream outputStream = response.getOutputStream();
                    IOUtils.write(data, outputStream); // 此处浏览器弹出下载框
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            result.put("status", 200);
            return result;
        }
        result.put("status", 502);
        return result;
    }

    @RequestMapping("/download1")
    public ResponseEntity<byte[]> download(String uuid, String fileOriginalName) {
        List<FileSlicesFdfsRes> list = iFileUpload.downloadSlicesFile(uuid);
        if (list!=null && list.size()>0) {
            listSort(list);
            int chunkSize = 0;
            for (int i = 0; i < list.size(); i++) {
                chunkSize += list.get(i).getFileSlicesSize();
            }
            byte[] allData = new byte[chunkSize];
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            int destPos = 0;
            for (FileSlicesFdfsRes item: list) {
                try {
                    // URLEncoder.encode(name, "UTF-8") 第一个参数是浏览器下载时文件的默认名字, 第二个是编码类型
                    headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileOriginalName, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                byte[] data = fdfsClient.download(item.getFileFdfsPath());
                int dataLength = data.length;
                // 将一个或者多个 byte[] 的内容拷贝到另一个 byte[] 中
                // data: 源数据; 0: 从源数据的什么位置开始拷贝; allData: 目标数据源;
                // destPos: 往目标数据源写数据时从哪个位置开始; dataLength: 往目标数据源里写多少字节的数据
                System.arraycopy(data, 0, allData, destPos, dataLength);
                destPos += dataLength;
            }
            return new ResponseEntity<>(allData, headers, HttpStatus.CREATED);
        }
        return null;
    }

    @RequestMapping("/getFile")
    public List<FileDownloadRes> getFile() {
        List<FileDownloadRes> list = iFileUpload.downloadFile();
        return list;
    }

    public void listSort(List list) {
        // 根据 index 进行排序, 为了能够顺序整合文件
        Collections.sort(list, new Comparator<FileSlicesFdfsRes>() {
            @Override
            public int compare(FileSlicesFdfsRes o1, FileSlicesFdfsRes o2) {
                if (o1==null && o2==null) {
                    return 0;
                }
                if (o1==null) {
                    return -1;
                }
                if (o2==null) {
                    return 1;
                }
                if (o1.getFileSlicesIndex() > o2.getFileSlicesIndex()) {
                    return 1;
                } else if (o1.getFileSlicesIndex() < o2.getFileSlicesIndex()) {
                    return -1;
                }
                return 0;
            }
        });
    }

}
