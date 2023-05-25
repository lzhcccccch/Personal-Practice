package com.lzhch.practice.xstream.javabean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Bcc立项明细出参
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2023/5/25 10:35
 */

@Data
public class BccProApprovalInfoRes implements Serializable {

    /**
     * 是否成功(Y:成功 N:失败
     */
    private String BSUCCESS;

    /**
     * 返回信息
     */
    private String MSG;

    /**
     * 立项明细
     */
    private List<Object> LXINFOS;

}
