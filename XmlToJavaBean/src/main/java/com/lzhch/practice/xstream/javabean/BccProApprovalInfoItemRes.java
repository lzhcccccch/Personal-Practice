package com.lzhch.practice.xstream.javabean;

import lombok.Data;

import java.io.Serializable;

/**
 * Bcc立项明细子项出参
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2023/5/25 14:13
 */

@Data
public class BccProApprovalInfoItemRes implements Serializable {

    /**
     * 立项号
     */
    private String CLXCODE;

    /**
     * 项目名称
     */
    private String CLXNAME;

    /**
     * 立项部门编码
     */
    private String CDEPCODE;

    /**
     * 立项部门名称
     */
    private String CDEPNAME;

    /**
     * 预算类型编码
     */
    private String CBUDGETTYPECODE;

    /**
     * 销售类型
     */
    private String CSALETYPE;

}
