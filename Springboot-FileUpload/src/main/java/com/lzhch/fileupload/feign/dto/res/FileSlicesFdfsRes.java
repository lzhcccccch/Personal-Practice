package com.lzhch.fileupload.feign.dto.res;

import lombok.Data;

import java.util.Date;

/**
 * @packageName： com.lzhch.fileupload.feign.dto.res
 * @className: FileSlicesFdfsRes
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-30 14:43
 */
@Data
public class FileSlicesFdfsRes {

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
