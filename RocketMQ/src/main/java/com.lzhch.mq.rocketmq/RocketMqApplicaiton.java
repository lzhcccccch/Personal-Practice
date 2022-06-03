package com.lzhch.mq.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @packageName： com.lzhch.mq.rocketmq
 * @className: RocketMqApplicaiton
 * @description:  RocketMQ 启动类
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2022/5/30 10:42
 */

@SpringBootApplication
public class RocketMqApplicaiton {

    public static void main(String[] args) {
        SpringApplication.run(RocketMqApplicaiton.class, args);
        System.out.println("--------- start ----------");
    }

}
