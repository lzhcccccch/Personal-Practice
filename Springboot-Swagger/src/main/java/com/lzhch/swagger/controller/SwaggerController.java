package com.lzhch.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



/**
 * @packageName： com.lzhch.swagger.controller
 * @className: SwaggerController
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-05-20 15:25
 */

@RestController
@RequestMapping("/swagger")
@Api("swagger 测试")
public class SwaggerController {

    @ApiOperation("是否成功")
    @RequestMapping(value = "/isSuccess", method = RequestMethod.GET)
    public String isSuccess(String param) {
        return param;
    }

}
