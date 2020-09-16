package com.lzhch.fileupload.server.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @packageName： com.lzhch.fileupload.server.infrastructure.persistence.entity
 * @className: SlicesFileEntity
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-27 16:36
 */
@TableName("file_slices_fdfs")
@Data
public class SlicesFileEntity {

    @TableId
    private int id;

    @TableField("uuid")
    private String uuid;

    @TableField("file_slices_index")
    private int fileSlicesIndex;

    @TableField("file_slices_size")
    private int fileSlicesSize;

    @TableField("file_fdfs_path")
    private String fileFdfsPath;

    @TableField("file_full_path")
    private String fileFullPath;

    @TableLogic
    private int isDelete;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

}
