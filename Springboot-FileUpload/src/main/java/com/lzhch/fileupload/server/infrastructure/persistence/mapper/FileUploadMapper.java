package com.lzhch.fileupload.server.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzhch.fileupload.server.infrastructure.persistence.entity.FileUploadEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @packageName： com.lzhch.fileupload.server.infrastructure.persistence.mapper
 * @className: FileUploadMapper
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-27 16:40
 */
@Mapper
//@Component
public interface FileUploadMapper extends BaseMapper<FileUploadEntity> {
}
