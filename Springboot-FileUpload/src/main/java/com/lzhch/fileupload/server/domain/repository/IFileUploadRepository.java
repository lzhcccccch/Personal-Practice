package com.lzhch.fileupload.server.domain.repository;

import com.lzhch.fileupload.server.domain.SlicesFileUploadRoot;

import java.util.List;

/**
 * @packageName： com.lzhch.fileupload.server.domain
 * @className: IFileUploadRepository
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-27 16:15
 */
public interface IFileUploadRepository {

    public int addFile(SlicesFileUploadRoot root);

    public int delFile(SlicesFileUploadRoot root);

    public int addSlicesFile(SlicesFileUploadRoot root);

    public int delSlicesFile(SlicesFileUploadRoot root);

    public SlicesFileUploadRoot downloadFile(SlicesFileUploadRoot root);

    public List<SlicesFileUploadRoot> downloadFile();

    public List<SlicesFileUploadRoot> downloadSlicesFile(String uuid);

}
