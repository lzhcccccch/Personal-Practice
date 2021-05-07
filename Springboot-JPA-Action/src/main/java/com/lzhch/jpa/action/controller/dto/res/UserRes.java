package com.lzhch.jpa.action.controller.dto.res;

import com.bcp.sdp.global.common.dto.res.ResDto;
import lombok.Data;

/**
 * @packageName： com.lzhch.jpa.action.controller.dto.res
 * @className: UserRes
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2021-01-22 15:27
 */
@Data
public class UserRes extends ResDto<Long> {

    private int userId;

    private String userName;

    private String password;

    private Long phone;

    private String address;

}
