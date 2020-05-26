package com.lzhch.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @packageName： com.lzhch.mybatisplus
 * @className: MybatisPlusApplication
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-05-20 16:40
 */
@SpringBootApplication
@MapperScan("com.lzhch.mybatisplus")
public class MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
        System.out.println("<-------start-------->");
    }
}
