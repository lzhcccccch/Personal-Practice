package com.lzhch.jpa.action.controller;

import com.bcp.sdp.exception.controller.MyAbstractController;
import com.bcp.sdp.global.common.dto.res.PageResult;
import com.bcp.sdp.global.common.dto.res.ResResult;
import com.lzhch.jpa.action.controller.dto.req.QueryDto;
import com.lzhch.jpa.action.controller.dto.req.UpdateDto;
import com.lzhch.jpa.action.controller.dto.res.UserRes;
import com.lzhch.jpa.action.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @packageName： com.lzhch.jpa.action.controller
 * @className: PackServiceController
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2021-01-25 22:58
 */
@Slf4j
@RestController
@RequestMapping("/pack/service/")
@Api(description = "Service 层封装方法测试 Controller")
public class PackServiceController extends MyAbstractController {

    @Autowired
    private UserService service;

    /***************************** Service 层封装方法 ***************************/

    @PutMapping
    @ApiOperation("更新操作")
    public ResResult<UserRes> update(UpdateDto dto) {
        ResResult<UserRes> result = service.update(dto);
        //log.info("update: {}" + JSON.toJSONString(result));
        return result;
    }

    @GetMapping("page/exam1")
    @ApiOperation("分页查询--通过 and 条件封装 Example")
    public ResResult<PageResult<UserRes>> getPageByAndExam(QueryDto dto) {
        ResResult<PageResult<UserRes>> result = service.getPageByAndExam(dto);
        //log.info("getPageByAndExam: {}" + JSON.toJSONString(result));
        return result;
    }

    @GetMapping("page/spec")
    @ApiOperation("分页查询--通过 and 条件封装 Specification")
    public ResResult<PageResult<UserRes>> getPageByAndSpec(QueryDto dto) {
        ResResult<PageResult<UserRes>> result = service.getPageByAndSpec(dto);
        //log.info("getPageByAndSpec: {}" + JSON.toJSONString(result));
        return result;
    }

    @GetMapping("page/exam2")
    @ApiOperation("分页查询--通过 or 条件封装 Example")
    public ResResult<PageResult<UserRes>> getPageByOrExam(QueryDto dto) {
        ResResult<PageResult<UserRes>> result = service.getPageByOrExam(dto);
        //log.info("getPageByOrExam: {}" + JSON.toJSONString(result));
        return result;
    }

    @GetMapping("page/spec2")
    @ApiOperation("分页查询--通过 or 条件封装 Specification")
    public ResResult<PageResult<UserRes>> getPageByOrSpec(QueryDto dto) {
        ResResult<PageResult<UserRes>> result = service.getPageByOrSpec(dto);
        //log.info("getPageByOrSpec: {}" + JSON.toJSONString(result));
        return result;
    }

    @GetMapping("page/fields/like")
    @ApiOperation("分页查询--对多条件进行模糊查询 or 连接")
    public ResResult<PageResult<UserRes>> getPageByFieldsLike(QueryDto dto, String... fields) {
        ResResult<PageResult<UserRes>> result = service.getPageByFieldsLike(dto, fields);
        //log.info("getPageByFieldsLike: {}" + JSON.toJSONString(result));
        return result;
    }

}
