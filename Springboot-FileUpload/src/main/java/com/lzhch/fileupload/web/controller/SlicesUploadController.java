package com.lzhch.fileupload.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.lzhch.fileupload.feign.IFileUpload;
import com.lzhch.fileupload.feign.dto.req.FileSlicesFdfsReq;
import com.lzhch.fileupload.feign.dto.req.FileUploadReq;
import com.lzhch.fileupload.feign.dto.res.FileRes;
import com.lzhch.fileupload.feign.dto.res.SlicesFileRes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @packageName： com.lzhch.fileupload.web.controller
 * @className: SlicesUploadController
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-27 16:13
 */
@RestController
@RequestMapping("/slices/file")
public class SlicesUploadController {

    @Autowired
    private IFileUpload iFileUpload;

    @RequestMapping("/upload")
    public String upload(String fileInfo) {
        JSONObject jsonObject = JSONObject.parseObject(fileInfo);
        FileUploadReq fileReq = JSONObject.toJavaObject(jsonObject, FileUploadReq.class);
        String result = "";
        if (fileReq!=null) {
            String fileType = fileReq.getFileOriginalName().substring(fileReq.getFileOriginalName().lastIndexOf(".")+1);
            fileReq.setFileType(fileType);
            int index = fileReq.getFileSlicesIndex();
            int total = fileReq.getFileSlicesTotal();
            FileSlicesFdfsReq slicesReq = new FileSlicesFdfsReq();
            BeanUtils.copyProperties(fileReq, slicesReq);
            iFileUpload.uploadSlicesFile(slicesReq);
            result = "------add slicesFile success, index=" + index;
            System.out.println(result);
            if (index == total) {
                iFileUpload.uploadFile(fileReq);
                System.out.println("------add File success, name=" + fileReq.getFileOriginalName());
            }
        }
        return result;
    }

    @RequestMapping("/getFile")
    public List<FileRes> getFile() {
        List<FileRes> list = iFileUpload.downloadFile();
        changePaths(list);
        return list;
    }

    /**
     * @description:  改变分片数据的路径, 与下载对应["index,url,size;index,url,size;index,url,size"]
     * @param: [list]
     * @return: java.lang.String
     * @author: liuzhichao 2020-09-02 10:03
     */
    public void changePaths(List<FileRes> list) {
        if (list!=null && list.size()>0) {
            for ( FileRes item : list) {
                StringBuffer buffer = new StringBuffer();
                //JSONArray jsonArray = JSONObject.parseArray(item.getSlicesFileJsonStr());
                //List<SlicesFileRes> slicesFileRes = JSONArray.toJavaObject(jsonArray, List.class);
                List<SlicesFileRes> slicesFileRes = JSONObject.parseArray(item.getSlicesFileJsonStr(), SlicesFileRes.class);
                for (int i = 0; i < slicesFileRes.size(); i++) {
                    SlicesFileRes slicesItem = slicesFileRes.get(i);
                    buffer.append(slicesItem.getFileSlicesIndex());
                    buffer.append(",");
                    buffer.append(slicesItem.getFileFdfsPath());
                    buffer.append(",");
                    buffer.append(slicesItem.getFileSlicesSize());
                    if (i < list.size()-1) {
                        buffer.append(";");
                    }
                }
                String path = new String(buffer);
                item.setSlicesFileJsonStr(path);
            }
        }
    }

}
