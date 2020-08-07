package com.lzhch.fileupload.server.infrastructure.persistence;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzhch.fileupload.server.domain.SlicesFileUploadRoot;
import com.lzhch.fileupload.server.domain.repository.IFileUploadRepository;
import com.lzhch.fileupload.server.infrastructure.persistence.entity.FileSlicesFdfsEntity;
import com.lzhch.fileupload.server.infrastructure.persistence.entity.FileUploadEntity;
import com.lzhch.fileupload.server.infrastructure.persistence.mapper.FileSlicesFdfsMapper;
import com.lzhch.fileupload.server.infrastructure.persistence.mapper.FileUploadMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        FileUploadEntity entity = new FileUploadEntity();
        BeanUtils.copyProperties(root, entity);
        entity.setIsDelete(1);
        int result = fileMapper.insert(entity);
        return result;
    }

    @Override
    public SlicesFileUploadRoot downloadFile(SlicesFileUploadRoot root) {
        QueryWrapper<FileUploadEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("file_original_name", root.getFileOriginalName());
        wrapper.eq("uuid", root.getUuid());
        FileUploadEntity entity = fileMapper.selectOne(wrapper);
        SlicesFileUploadRoot result = new SlicesFileUploadRoot();
        BeanUtils.copyProperties(entity, result);
        return result;
    }

    @Override
    public List<SlicesFileUploadRoot> downloadFile() {
        List<FileUploadEntity> list = fileMapper.selectList(null);
        if (list!=null && list.size()>0) {
            List<SlicesFileUploadRoot> result = new ArrayList<>(list.size());
            for (FileUploadEntity item : list) {
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
        FileSlicesFdfsEntity entity = new FileSlicesFdfsEntity();
        BeanUtils.copyProperties(root, entity);
        entity.setIsDelete(1);
        int result = slicesMapper.insert(entity);
        return result;
    }

    @Override
    public List<SlicesFileUploadRoot> downloadSlicesFile(String uuid) {
        // QueryWrapper<>的泛型只传单体的即可 不可传 list 类型
        QueryWrapper<FileSlicesFdfsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("uuid", uuid);
        List<FileSlicesFdfsEntity> list = slicesMapper.selectList(wrapper);
        if (list!=null && list.size()>0) {
            List<SlicesFileUploadRoot> result = new ArrayList<>(list.size());
            for (FileSlicesFdfsEntity item : list) {
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
