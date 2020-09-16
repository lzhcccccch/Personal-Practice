package com.lzhch.fileupload.feign.dto.res;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @packageName： com.lzhch.fileupload.feign.dto.res
 * @className: FileRes
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-30 14:45
 */
@Data
public class FileRes {

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

    /**
     *  分片文件的 json 格式
     */
    private String slicesFileJsonStr;

    private List<SlicesFileRes> slicesFileRes;

}
