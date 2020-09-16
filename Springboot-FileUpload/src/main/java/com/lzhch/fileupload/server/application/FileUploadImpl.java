package com.lzhch.fileupload.server.application;

import com.alibaba.fastjson.JSONObject;
import com.lzhch.fileupload.feign.IFileUpload;
import com.lzhch.fileupload.feign.dto.req.FileSlicesFdfsReq;
import com.lzhch.fileupload.feign.dto.req.FileUploadReq;
import com.lzhch.fileupload.feign.dto.res.FileRes;
import com.lzhch.fileupload.feign.dto.res.SlicesFileRes;
import com.lzhch.fileupload.server.domain.SlicesFileUploadRoot;
import com.lzhch.fileupload.server.domain.repository.IFileUploadRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @packageName： com.lzhch.fileupload.server.application
 * @className: FileUploadImpl
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-28 14:24
 */
@Service
public class FileUploadImpl implements IFileUpload {

    @Autowired
    private IFileUploadRepository repository;

    @Override
    public int uploadFile(FileUploadReq req) {
        SlicesFileUploadRoot root = new SlicesFileUploadRoot();
        BeanUtils.copyProperties(req, root);
        int result = repository.addFile(root);
        return result;
    }

    @Override
    public int uploadSlicesFile(FileSlicesFdfsReq req) {
        SlicesFileUploadRoot root = new SlicesFileUploadRoot();
        BeanUtils.copyProperties(req, root);
        int result = repository.addSlicesFile(root);
        return result;
    }

    @Override
    @Transactional
    public List<FileRes> downloadFile() {
        List<SlicesFileUploadRoot> list = repository.downloadFile();
        if (list!=null) {
            List<FileRes> resList = new ArrayList<>(list.size());
            for (SlicesFileUploadRoot item : list) {
                List<SlicesFileUploadRoot> slicesList = repository.downloadSlicesFile(item.getUuid());
                String slicesStr = JSONObject.toJSONString(slicesList);
                FileRes res = new FileRes();
                BeanUtils.copyProperties(item, res);
                res.setSlicesFileJsonStr(slicesStr);
                resList.add(res);
            }
            return resList;
        }
        return null;
    }

    @Override
    public FileRes downloadFile(FileUploadReq req) {
        SlicesFileUploadRoot root = new SlicesFileUploadRoot();
        BeanUtils.copyProperties(req, root);
        SlicesFileUploadRoot result = repository.downloadFile(root);
        FileRes res = new FileRes();
        BeanUtils.copyProperties(result, res);
        return res;
    }

    @Override
    public List<SlicesFileRes> downloadSlicesFile(String uuid) {
        List<SlicesFileUploadRoot> list = repository.downloadSlicesFile(uuid);
        if (list!=null && list.size()>0) {
            List<SlicesFileRes> resList = new ArrayList<>(list.size());
            for (SlicesFileUploadRoot item : list) {
                SlicesFileRes res = new SlicesFileRes();
                BeanUtils.copyProperties(item, res);
                resList.add(res);
            }
            return resList;
        }
        return null;
    }
}
