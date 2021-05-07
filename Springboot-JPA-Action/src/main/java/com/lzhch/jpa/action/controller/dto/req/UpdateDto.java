package com.lzhch.jpa.action.controller.dto.req;

import com.bcp.sdp.global.common.dto.req.UpdateReq;
import lombok.Data;

/**
 * @packageName： com.lzhch.jpa.action.controller.dto
 * @className: UpdateDto
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2021-01-22 14:09
 */
@Data
public class UpdateDto extends UpdateReq<Long> {

    private Integer userId;

    private String userName;

    private String password;

    private Long phone;

    private String address;

}
