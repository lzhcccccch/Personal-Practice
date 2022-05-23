package com.lzhch.stream.lambda.init;

import java.util.ArrayList;
import java.util.List;

/**
 * @packageName： com.lzhch.stream.lambda.init
 * @className: InitList
 * @description:  初始化列表
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2021-12-01 16:14
 */
public class InitList {

    public static List<Person> initList() {
        List<Person> resList = new ArrayList<>();
        resList.add(new Person("xxxxxx", "man", 20, 9, "address1"));
        resList.add(new Person("yyyyyyyyy", "man", 22, 10, "address2"));
        resList.add(new Person("zzz", "women", 40, 15, "address3"));
        resList.add(new Person("aaaaa", "women", 60, 6, "address4"));

        return resList;
    }

}
