package com.lzhch.thread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2023/6/12 15:54
 */

@SpringBootApplication
public class ThreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadApplication.class, args);
        System.out.println("启动了-------------");
    }

}
