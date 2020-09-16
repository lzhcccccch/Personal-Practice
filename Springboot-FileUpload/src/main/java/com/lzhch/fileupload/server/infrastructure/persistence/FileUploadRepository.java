package com.lzhch.fileupload.server.infrastructure.persistence;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzhch.fileupload.server.domain.SlicesFileUploadRoot;
import com.lzhch.fileupload.server.domain.repository.IFileUploadRepository;
import com.lzhch.fileupload.server.infrastructure.persistence.entity.FileEntity;
import com.lzhch.fileupload.server.infrastructure.persistence.entity.SlicesFileEntity;
import com.lzhch.fileupload.server.infrastructure.persistence.mapper.FileSlicesFdfsMapper;
import com.lzhch.fileupload.server.infrastructure.persistence.mapper.FileUploadMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @packageName： com.lzhch.fileupload.server.infrastructure.persistence
 * @className: FileUploadRepository
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-27 16:29
 */
@Service
public class FileUploadRepository implements IFileUploadRepository {

    @Autowired
    private FileUploadMapper fileMapper;
    @Autowired
    private FileSlicesFdfsMapper slicesMapper;

    @Override
    public int addFile(SlicesFileUploadRoot root) {
        FileEntity entity = new FileEntity();
        BeanUtils.copyProperties(root, entity);
        entity.setIsDelete(1);
        entity.setCreateTime(new Date());
        int result = fileMapper.insert(entity);
        return result;
    }

    @Override
    public SlicesFileUploadRoot downloadFile(SlicesFileUploadRoot root) {
        QueryWrapper<FileEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("file_original_name", root.getFileOriginalName());
        wrapper.eq("uuid", root.getUuid());
        FileEntity entity = fileMapper.selectOne(wrapper);
        SlicesFileUploadRoot result = new SlicesFileUploadRoot();
        BeanUtils.copyProperties(entity, result);
        return result;
    }

    @Override
    public List<SlicesFileUploadRoot> downloadFile() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("is_delete", 1);
        List<FileEntity> list = fileMapper.selectList(wrapper);
        if (list!=null && list.size()>0) {
            List<SlicesFileUploadRoot> result = new ArrayList<>(list.size());
            for (FileEntity item : list) {
                SlicesFileUploadRoot root = new SlicesFileUploadRoot();
                BeanUtils.copyProperties(item, root);
                result.add(root);
            }
            return result;
        }
        return null;
    }

    @Override
    public int delFile(SlicesFileUploadRoot root) {
        return 0;
    }

    @Override
    public int addSlicesFile(SlicesFileUploadRoot root) {
        SlicesFileEntity entity = new SlicesFileEntity();
        BeanUtils.copyProperties(root, entity);
        entity.setIsDelete(1);
        entity.setCreateTime(new Date());
        int result = slicesMapper.insert(entity);
        return result;
    }

    @Override
    public List<SlicesFileUploadRoot> downloadSlicesFile(String uuid) {
        // QueryWrapper<>的泛型在查询 List 时不需写成 List 形式, 写单体类型即可
        QueryWrapper<SlicesFileEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("uuid", uuid);
        List<SlicesFileEntity> list = slicesMapper.selectList(wrapper);
        if (list!=null && list.size()>0) {
            List<SlicesFileUploadRoot> result = new ArrayList<>(list.size());
            for (SlicesFileEntity item : list) {
                SlicesFileUploadRoot root = new SlicesFileUploadRoot();
                BeanUtils.copyProperties(item, root);
                result.add(root);
            }
            return result;
        }
        return null;
    }

    @Override
    public int delSlicesFile(SlicesFileUploadRoot root) {
        return 0;
    }
}
