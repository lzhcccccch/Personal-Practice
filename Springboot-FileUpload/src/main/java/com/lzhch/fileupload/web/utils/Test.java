//package com.lzhch.fileupload.web.utils;
//
//import cn.hutool.core.convert.Convert;
//import cn.hutool.core.io.FileUtil;
//import cn.hutool.core.util.StrUtil;
//import com.github.tobato.fastdfs.domain.fdfs.StorePath;
//import freemarker.template.utility.StringUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// * @packageName： com.lzhch.fileupload.web.utils
// * @className: Test
// * @description: TODO
// * @version: v1.0
// * @author: liuzhichao
// * @date: 2020-07-29 09:49
// */
//public class Test {
//
//    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
//
//    public RespMsgBean checkFile(Long userId, String fileMd5) {
//        RespMsgBean backInfo = new RespMsgBean();
//        if (StrUtil.isEmpty(fileMd5)) {
//            return backInfo.failure("fileMd5不能为空");
//        }
//        if (userId == null) {
//            return backInfo.failure("userId不能为空");
//        }
//        String userIdStr = userId.toString();
//        CheckFileDto checkFileDto = new CheckFileDto();
//
//        // 查询是否有相同md5文件已存在,已存在直接返回
//        FileDo fileDo = fileDao.findOneByColumn("scode", fileMd5);
//        if (fileDo != null) {
//            FileVo fileVo = doToVo(fileDo);
//            return backInfo.success("文件已存在", fileVo);
//        } else {
//            // 查询锁占用
//            String lockName = UpLoadConstant.currLocks + fileMd5;
//            Long lock = JedisConfig.incrBy(lockName, 1);
//            String lockOwner = UpLoadConstant.lockOwner + fileMd5;
//            String chunkCurrkey = UpLoadConstant.chunkCurr + fileMd5;
//            if (lock > 1) {
//                checkFileDto.setLock(1);
//                // 检查是否为锁的拥有者,如果是放行
//                String oWner = JedisConfig.getString(lockOwner);
//                if (StrUtil.isEmpty(oWner)) {
//                    return backInfo.failure("无法获取文件锁拥有者");
//                } else {
//                    if (oWner.equals(userIdStr)) {
//                        String chunkCurr = JedisConfig.getString(chunkCurrkey);
//                        if (StrUtil.isEmpty(chunkCurr)) {
//                            return backInfo.failure("无法获取当前文件chunkCurr");
//                        }
//                        checkFileDto.setChunk(Convert.toInt(chunkCurr));
//                        return backInfo.success("", null);
//                    } else {
//                        return backInfo.failure("当前文件已有人在上传,您暂无法上传该文件");
//                    }
//                }
//            } else {
//                // 初始化锁.分块
//                JedisConfig.setString(lockOwner, userIdStr);
//                // 第一块索引是0,与前端保持一致
//                JedisConfig.setString(chunkCurrkey, "0");
//                checkFileDto.setChunk(0);
//                return backInfo.success("", null);
//            }
//        }
//    }
//
//    public RespMsgBean uploadBigFileChunk(MultipartFile file, Long userId, String fileMd5, String fileName, Integer chunks, Integer chunk, Integer chunkSize, String bizId, String bizCode) {
//        RespMsgBean backInfo = new RespMsgBean();
//        ServiceAssert.isTrue(!file.isEmpty(), 0, "文件不能为空");
//        ServiceAssert.notNull(userId, 0, "用户id不能为空");
//        ServiceAssert.isTrue(StringUtil.isNotBlank(fileMd5), 0, "文件fd5不能为空");
//        ServiceAssert.isTrue(!"undefined".equals(fileMd5), 0, "文件fd5不能为undefined");
//        ServiceAssert.isTrue(StringUtil.isNotBlank(fileName), 0, "文件名称不能为空");
//        ServiceAssert.isTrue(chunks != null && chunk != null && chunkSize != null, 0, "文件块数有误");
//        // 存储在fastdfs不带组的路径
//        String noGroupPath = "";
//        logger.info("当前文件的Md5:{}", fileMd5);
//        String chunkLockName = UpLoadConstant.chunkLock + fileMd5;
//
//        // 真正的拥有者
//        boolean currOwner = false;
//        Integer currentChunkInFront = 0;
//        try {
//            if (chunk == null) {
//                chunk = 0;
//            }
//            if (chunks == null) {
//                chunks = 1;
//            }
//
//            Long lock = JedisConfig.incrBy(chunkLockName, 1);
//            if (lock > 1){
//                logger.info("请求块锁失败");
//                return backInfo.failure("请求块锁失败");
//            }
//            // 写入锁的当前拥有者
//            currOwner = true;
//
//            // redis中记录当前应该传第几块(从0开始)
//            String currentChunkKey = UpLoadConstant.chunkCurr + fileMd5;
//            String currentChunkInRedisStr =  JedisConfig.getString(currentChunkKey);
//            Integer currentChunkSize = chunkSize;
//            logger.info("当前块的大小:{}", currentChunkSize);
//            if (StrUtil.isEmpty(currentChunkInRedisStr)) {
//                logger.info("无法获取当前文件chunkCurr");
//                return backInfo.failure("无法获取当前文件chunkCurr");
//            }
//            Integer currentChunkInRedis = Convert.toInt(currentChunkInRedisStr);
//            currentChunkInFront = chunk;
//
//            if (currentChunkInFront < currentChunkInRedis) {
//                logger.info("当前文件块已上传");
//                return backInfo.failure("当前文件块已上传", "001");
//            } else if (currentChunkInFront > currentChunkInRedis) {
//                logger.info("当前文件块需要等待上传,稍后请重试");
//                return backInfo.failure("当前文件块需要等待上传,稍后请重试");
//            }
//
//            logger.info("***********开始上传第{}块**********", currentChunkInRedis);
//            StorePath path = null;
//            if (!file.isEmpty()) {
//                try {
//                    if (currentChunkInFront == 0) {
//                        JedisConfig.setString(currentChunkKey, Convert.toStr(currentChunkInRedis + 1));
//                        logger.info("{}:redis块+1", currentChunkInFront);
//                        try {
//                            path = defaultAppendFileStorageClient.uploadAppenderFile(UpLoadConstant.DEFAULT_GROUP, file.getInputStream(),
//                                    file.getSize(), FileUtil.extName(fileName));
//                            // 记录第一个分片上传的大小
//                            JedisConfig.setString(UpLoadConstant.fastDfsSize + fileMd5, String.valueOf(currentChunkSize));
//                            logger.info("{}:更新完fastDfs", currentChunkInFront);
//                            if (path == null) {
//                                JedisConfig.setString(currentChunkKey, Convert.toStr(currentChunkInRedis));
//                                logger.info("获取远程文件路径出错");
//                                return backInfo.failure("获取远程文件路径出错");
//                            }
//                        } catch (Exception e) {
//                            JedisConfig.setString(currentChunkKey, Convert.toStr(currentChunkInRedis));
//                            logger.error("初次上传远程文件出错", e);
//                            return new RespMsgBean().failure("上传远程服务器文件出错");
//                        }
//                        noGroupPath = path.getPath();
//                        JedisConfig.setString(UpLoadConstant.fastDfsPath + fileMd5, path.getPath());
//                        logger.info("上传文件 result = {}", path);
//                    } else {
//                        JedisConfig.setString(currentChunkKey, Convert.toStr(currentChunkInRedis + 1));
//                        logger.info("{}:redis块+1", currentChunkInFront);
//                        noGroupPath = JedisConfig.getString(UpLoadConstant.fastDfsPath + fileMd5);
//                        if (noGroupPath == null) {
//                            logger.info("无法获取已上传服务器文件地址");
//                            return new RespMsgBean().failure("无法获取已上传服务器文件地址");
//                        }
//                        try {
//                            String alreadySize = JedisConfig.getString(UpLoadConstant.fastDfsSize + fileMd5);
//                            // 追加方式实际实用如果中途出错多次,可能会出现重复追加情况,这里改成修改模式,即时多次传来重复文件块,依然可以保证文件拼接正确
//                            defaultAppendFileStorageClient.modifyFile(UpLoadConstant.DEFAULT_GROUP, noGroupPath, file.getInputStream(),
//                                    file.getSize(), Long.parseLong(alreadySize));
//                            // 记录分片上传的大小
//                            JedisConfig.setString(UpLoadConstant.fastDfsSize + fileMd5, String.valueOf(Long.parseLong(alreadySize) + currentChunkSize));
//                            logger.info("{}:更新完fastdfs", currentChunkInFront);
//                        } catch (Exception e) {
//                            JedisConfig.setString(currentChunkKey, Convert.toStr(currentChunkInRedis));
//                            logger.error("更新远程文件出错", e);
//                            return new RespMsgBean().failure("更新远程文件出错");
//                        }
//                    }
//                    if (currentChunkInFront + 1 == chunks) {
//                        // 最后一块,清空upload,写入数据库
//                        Long size = Long.parseLong(JedisConfig.getString(UpLoadConstant.fastDfsSize + fileMd5));
//                        // 持久化上传完成文件,也可以存储在mysql中
//                        noGroupPath = JedisConfig.getString(UpLoadConstant.fastDfsPath + fileMd5);
//                        String url = UpLoadConstant.DEFAULT_GROUP + "/" + noGroupPath;
//                        FileDo fileDo = new FileDo(fileName, url, "", size, bizId, bizCode);
//                        fileDo.setCreateUser(userId);
//                        fileDo.setUpdateUser(userId);
//                        FileVo fileVo = saveFileDo4BigFile(fileDo, fileMd5);
//                        String[] deleteKeys = new String[]{UpLoadConstant.chunkCurr + fileMd5,
//                                UpLoadConstant.fastDfsPath + fileMd5,
//                                UpLoadConstant.currLocks + fileMd5,
//                                UpLoadConstant.lockOwner + fileMd5,
//                                UpLoadConstant.fastDfsSize + fileMd5
//                        };
//                        JedisConfig.delKeys(deleteKeys);
//                        logger.info("***********正常结束**********");
//                        return new RespMsgBean().success(fileVo);
//                    }
//                } catch (Exception e) {
//                    logger.error("上传文件错误", e);
//                    return new RespMsgBean().failure("上传错误 " + e.getMessage());
//                }
//            }
//        } finally {
//            // 锁的当前拥有者才能释放块上传锁
//            if (currOwner) {
//                JedisConfig.setString(chunkLockName, "0");
//            }
//        }
//        logger.info("***********第{}块上传成功**********", currentChunkInFront);
//        return backInfo.success("第" + currentChunkInFront + "块上传成功");
//    }
//
//}