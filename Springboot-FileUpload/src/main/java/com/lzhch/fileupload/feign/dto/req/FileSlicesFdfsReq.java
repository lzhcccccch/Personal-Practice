package com.lzhch.fileupload.feign.dto.req;

import lombok.Data;

import java.util.Date;

/**
 * @packageName： com.lzhch.fileupload.dto.req
 * @className: FileSlicesFdfsReq
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-27 16:44
 */
@Data
public class FileSlicesFdfsReq {

    private int id;

    private String uuid;

    private int fileSlicesIndex;

    private int fileSlicesSize;

    private String fileFdfsPath;

    private String fileFullPath;

    private int isDelete;

    private Date createTime;

    private Date updateTime;
}
