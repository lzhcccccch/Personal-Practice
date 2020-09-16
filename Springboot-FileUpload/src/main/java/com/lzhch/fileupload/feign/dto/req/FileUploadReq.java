package com.lzhch.fileupload.feign.dto.req;

import lombok.Data;

/**
 * @packageName： com.lzhch.fileupload.dto.req
 * @className: FileUploadReq
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-27 16:44
 */
@Data
public class FileUploadReq {

    private String uuid;

    private String fileOriginalName;

    private String fileFdfsPath;

    private int fileSlicesIndex;

    private int fileSlicesSize;

    private int fileSlicesTotal;

    private int fileSize;

    private String fileType;

    private String fileFullPath;

}
