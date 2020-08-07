package com.lzhch.fileupload.feign.dto.res;

import lombok.Data;

import java.util.Date;

/**
 * @packageName： com.lzhch.fileupload.feign.dto.res
 * @className: FileDownloadRes
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-30 14:45
 */
@Data
public class FileDownloadRes {

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
