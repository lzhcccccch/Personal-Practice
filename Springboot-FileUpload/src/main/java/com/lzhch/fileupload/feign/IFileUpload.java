package com.lzhch.fileupload.feign;

import com.lzhch.fileupload.feign.dto.req.FileSlicesFdfsReq;
import com.lzhch.fileupload.feign.dto.req.FileUploadReq;
import com.lzhch.fileupload.feign.dto.res.FileDownloadRes;
import com.lzhch.fileupload.feign.dto.res.FileSlicesFdfsRes;

import java.util.List;

/**
 * @packageName： com.lzhch.fileupload.feign.dto
 * @className: IFileUpload
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-28 11:01
 */
public interface IFileUpload {

    public int uploadFile(FileUploadReq req);

    public int uploadSlicesFile(FileSlicesFdfsReq req);

    public FileDownloadRes downloadFile(FileUploadReq req);

    public List<FileDownloadRes> downloadFile();

    public List<FileSlicesFdfsRes> downloadSlicesFile(String uuid);

}
