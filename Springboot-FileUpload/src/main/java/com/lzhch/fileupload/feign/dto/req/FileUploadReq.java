package com.lzhch.fileupload.feign.dto.req;

import lombok.Data;

import java.util.Date;

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

    private int id;

    private String fileOriginalName;

    private int fileSize;

    private String fileType;

    private String uuid;

    private int fileSlicesTotal;

    private int isDelete;

    private Date createTime;

    private Date updateTime;
}
