package com.lzhch.jpa.action.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @packageName： com.lzhch.jpa.action.vo
 * @className: ResponseResult
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-12-29 16:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult implements Serializable {

    private int code;

    private String msg;

    private Object data;

    public static ResponseResult success(Object data) {
        ResponseResult result = new ResponseResult();
        result.setCode(200);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    public static ResponseResult success() {
        ResponseResult result = new ResponseResult();
        result.setCode(200);
        result.setMsg("success");
        return result;
    }

}
