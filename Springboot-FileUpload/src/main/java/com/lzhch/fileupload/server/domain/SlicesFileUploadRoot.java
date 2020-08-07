package com.lzhch.fileupload.server.domain;

import lombok.Data;

import java.util.Date;

/**
 * @packageName： com.lzhch.fileupload.server.domain.repository
 * @className: SlicesFileUploadRoot
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-27 17:05
 */
@Data
public class SlicesFileUploadRoot {

    private int id;

    private String fileOriginalName;

    private int fileSize;

    private String fileType;

    private String uuid;

    private int fileSlicesTotal;

    private int isDelete;

    private Date createTime;

    private Date updateTime;

    private int fileSlicesIndex;

    private int fileSlicesSize;

    private String fileFdfsPath;

    private String fileFullPath;

}
