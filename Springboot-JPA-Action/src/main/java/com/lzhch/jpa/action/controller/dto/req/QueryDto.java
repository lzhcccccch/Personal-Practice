package com.lzhch.jpa.action.controller.dto.req;

import com.bcp.sdp.global.common.dto.req.QueryReq;
import lombok.Data;

/**
 * @packageName： com.lzhch.jpa.action.controller.dto
 * @className: QueryDto
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2021-01-22 14:14
 */
@Data
public class QueryDto extends QueryReq<Long> {

    private Integer userId;

    private String userName;

    private String password;

    private Long phone;

    private String address;

}
