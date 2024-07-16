package com.lzhch.stream.lambda.init;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 用户类
 * version: v1.0
 * author: liuzhichao
 * date: 2021-12-01 16:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String name;

    private String sex;

    private int age;

    private int salary;

    private String address;

}
