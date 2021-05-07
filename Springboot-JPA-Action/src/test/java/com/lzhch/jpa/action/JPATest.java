package com.lzhch.jpa.action;

import com.alibaba.fastjson.JSON;
import com.lzhch.jpa.action.controller.UserController;
import com.lzhch.jpa.action.entity.User;
import com.lzhch.jpa.action.repository.UserRepository;
import com.lzhch.jpa.action.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @packageName： com.lzhch.jpa.action
 * @className: JPATest
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2021-01-05 10:59
 */
@SpringBootTest(classes = JPAApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class JPATest {

    @Autowired
    private UserController controller;

    @Autowired
    private UserRepository repository;

    /**
     *  单元测试的一些坑:
     *      1. @Test 应该导入 import org.junit.Test 下的, 导入另一个会报错
     *      2. JSON 转换时尽量使用阿里的 fastjson, spring 的会报类型不支持
     *      3. 除了导入 spring-boot-starter-test 依赖, 还需要导入 junit-platform-launcher
     */
    @Test
    public void update() {
        User user = User.builder().userId(1005)
                .userName("111")
                .password("222")
                .phone(1234562L)
                //.address("xxx")
                .build();
        user.setId(27L);
        User result = repository.save(user);
        log.info("update: {} " + JSON.toJSONString(result));
    }

    @Test
    public void save() {
        User user1 = new User();
        user1.setUserId(1001);
        user1.setUserName("111");
        user1.setPassword("111");
        user1.setPhone(123456L);
        user1.setAddress("xxxx");

        User user = repository.save(user1);
        log.info("insert: {} " + JSON.toJSONString(user));
    }

    @Test
    public void saveAll() {
        User user1 = new User();
        user1.setUserId(1004);
        user1.setUserName("111");
        user1.setPassword("111");
        user1.setPhone(123456L);
        user1.setAddress("xxxx");

        User user2 = new User();
        user2.setUserId(1005);
        user2.setUserName("1222");
        user2.setPassword("222");
        user2.setPhone(123456L);
        user2.setAddress("xxxx");

        ResponseResult result = controller.addUsers(Arrays.asList(user1, user2));
        log.info("insert batch: {} " + JSON.toJSONString(result.getData()));
    }

    @Test
    public void findByUserId() {
        ResponseResult result = controller.findUserByUserId(1018);
        log.info("select: {} " + JSON.toJSONString(result.getData()));
    }

    @Test
    public void deleteById() {
        Long id = 3L;
        ResponseResult result = controller.deleteById(id);
        log.info("delete: {}" + JSON.toJSONString(result.success()));
    }

    @Test
    public void delete() {
        User user = new User();
        user.setId(4L);
        user.setUserId(1004);
        user.setUserName("ccc");
        ResponseResult result = controller.delete(user);
        log.info("delete: {}" + JSON.toJSONString(result.success()));
    }

    @Test
    public void deleteByIdBetween() {
        int low = 1006;
        int high = 1008;
        ResponseResult result = controller.deleteByUserIdBetween(low, high);
        log.info("delete: {}" + JSON.toJSONString(result));
    }

    @Test
    public void deleteBatch() {
        User user1 = User.builder()
                .userId(1010)
                .userName("111")
                .password("222")
                .phone(1234562L)
                .address("333")
                .build();
        user1.setId(12L);
        User user2 = User.builder()
                .userId(1011)
                .userName("111")
                .password("222")
                .phone(1234562L)
                .address("xxx")
                .build();
        user2.setId(13L);
        ResponseResult result = controller.deleteBatch(Arrays.asList(user1, user2));
        log.info("delete batch: {} " + JSON.toJSONString(result));
    }
}
