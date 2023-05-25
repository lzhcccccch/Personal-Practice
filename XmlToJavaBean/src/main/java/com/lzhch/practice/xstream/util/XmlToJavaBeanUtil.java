package com.lzhch.practice.xstream.util;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzhch.practice.xstream.javabean.BccProApprovalInfoItemRes;
import com.lzhch.practice.xstream.javabean.BccProApprovalInfoRes;
import com.lzhch.practice.xstream.xml.XmlData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Xml转换JavaBean工具类
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2023/5/25 15:57
 */

public class XmlToJavaBeanUtil {

    public static BccProApprovalInfoRes xmlToJavaBean(String xmlData) {
        // 处理成上述可以转换的报文(只保留基本定义和数据)
        xmlData = xmlData.substring(0, 38) + xmlData.substring(xmlData.indexOf("<LXQueryResult>"), xmlData.indexOf("</LXQueryResponse>"));

        // DomDriver 可以不引入其他依赖
        XStream xs = new XStream(new DomDriver());
        xs.allowTypesByRegExp(new String[]{".*"});
        // XML和JavaBean的映射
        xs.alias("LXQueryResult", BccProApprovalInfoRes.class);
        xs.alias("LXINFO", BccProApprovalInfoItemRes.class);
        // 进行转换
        Object object = xs.fromXML(xmlData);

        ObjectMapper objectMapper = new ObjectMapper();
        // 将Object转换为想要的JavaBean类型
        BccProApprovalInfoRes res = objectMapper.convertValue(object, BccProApprovalInfoRes.class);

        return res;
    }

    public static void main(String[] args) {
        BccProApprovalInfoRes bccProApprovalInfoRes = xmlToJavaBean(XmlData.DATA);
        System.out.println("res :{}" + JSONObject.toJSONString(bccProApprovalInfoRes));
    }


}
