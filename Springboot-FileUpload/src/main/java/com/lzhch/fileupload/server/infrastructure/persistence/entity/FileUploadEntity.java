package com.lzhch.fileupload.server.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @packageName： com.lzhch.fileupload.server.infrastructure.persistence.entity
 * @className: FileUploadEntity
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-27 16:30
 */
@TableName("file_upload")
@Data
public class FileUploadEntity {

    @TableId
    private int id;

    @TableField("file_original_name")
    private String fileOriginalName;

    @TableField("file_size")
    private int fileSize;

    @TableField("file_type")
    private String fileType;

    @TableField("uuid")
    private String uuid;

    @TableField("file_slices_total")
    private int fileSlicesTotal;

    @TableLogic
    private int isDelete;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

}
