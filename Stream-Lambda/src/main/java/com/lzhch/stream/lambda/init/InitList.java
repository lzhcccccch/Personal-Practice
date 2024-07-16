package com.lzhch.stream.lambda.init;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化列表
 * version: v1.0
 * author: liuzhichao
 * date: 2021-12-01 16:14
 */
public class InitList {

    public static List<Person> initList() {
        List<Person> resList = new ArrayList<>();
        resList.add(new Person("xxx", "man", 20, 9, "address1"));
        resList.add(new Person("xxx-xxx", "man", 20, 9, "address1"));
        resList.add(new Person("zzz", "women", 40, 15, "address3"));
        resList.add(new Person("zzz", "women", 60, 6, "address4"));

        return resList;
    }

    public static List<Address> initAddressList() {
        List<Address> resList = new ArrayList<>();
        resList.add(new Address("address1", "China", "Shanxi", "Taiyuan", "Xinghualing"));
        resList.add(new Address("address2", "China", "Shanxi", "Taiyuan", "Xinghualing"));
        resList.add(new Address("address3", "China", "Shanxi", "Taiyuan", "Xinghualing"));
        resList.add(new Address("address3", "China-3", "Shanxi-3", "Taiyuan3", "Xinghualing"));

        return resList;
    }

}
