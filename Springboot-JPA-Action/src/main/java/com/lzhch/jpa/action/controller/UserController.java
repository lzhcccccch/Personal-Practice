package com.lzhch.jpa.action.controller;

import com.alibaba.fastjson.JSON;
import com.bcp.sdp.exception.controller.MyAbstractController;
import com.lzhch.jpa.action.entity.User;
import com.lzhch.jpa.action.repository.UserDeleteRepository;
import com.lzhch.jpa.action.repository.UserRepository;
import com.lzhch.jpa.action.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @packageName： com.lzhch.jpa.action.controller
 * @className: UserController
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-12-29 16:03
 */
@RestController
@RequestMapping("/users")
public class UserController extends MyAbstractController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserDeleteRepository deleteRepository;

    @PostMapping
    public ResponseResult AddUser(User user) {
        // JPA 在新增时返回的是新增的对象的数据, 并不是受影响的行数
        /**
         *  save() 和 saveAndFlush() 的区别:
         *      save() 方法: 除非直到显式调用 flush() 或 commit() 方法 否则与 save 操作相关联的数据将不会刷新到 DB
         *      saveAndFlush() : 在执行期间立即刷新数据到 DB
         */
        //User result = repository.save(user);
        User result = repository.saveAndFlush(user);
        System.out.println("添加用户成功, 用户信息:  " + result.toString());
        return ResponseResult.success(result);
    }

    @PostMapping("/batch")
    public ResponseResult addUsers(List<User> list) {
        List<User> result = repository.saveAll(list);
        System.out.println("insert batch: {} " + JSON.toJSONString(result));
        return ResponseResult.success(list);
    }

    /**
     *  如果要使用 JPA 进行实现修改, 是需要调用 save()方法, 即再次保存, 此时需要 @ID 注解的字段相同即可实现更新 否则新增
     *  JPA 自带方法实现更新操作并不方便, 所以使用 jql 可以实现根据条件实现修改, 但 jql 也有一定弊端
     *  具体见 introduce.txt 问题 7
     *  用 save() 进行更新操作 默认是全覆盖 即不想修改的字段会为 null 然后覆盖数据库
     */
    @PutMapping
    public ResponseResult update(User user) {
        //User user = repository.findUserByUserId(userId);
        //user.setAddress("xxxxxx");
        //repository.save(user);
        //int result = repository.update(user);
        User result = repository.save(user);
        return ResponseResult.success(result);
    }

    /**
     *  通过 deleteById() 方式进行删除时, id 对应的记录存在时正常删除, id 对应记录不存在时报错
     *  因为底层代码是先根据 id 进行 select, 如果没有相应记录则报错
     *  解决方法:
     *      1. 自己先进行查询, 如果不存在则不进行 delete 操作, 但是如果 id 对应记录存在则会造成两次 select 操作
     *      2. 自己进行仓库层改造, 定义一个类实现 SimpleJpaRepository 重写 deleteById() 方法, 如: UserDeleteRepository
     */
    @DeleteMapping
    public ResponseResult deleteById(Long id) {
        //repository.deleteById(id);
        deleteRepository.deleteById(id);
        return ResponseResult.success();
    }

    /**
     *  入参是对象也是按照 id 进行删除, 如果不指定 id 那么就不会执行任何 SQL
     *  仍然是先进行 select 如果记录存在则进行 delete 否则不会继续执行 不会出现 select 不到就报错的情况
     *  举一反三: 其他的由 JPA 提供的删除方法应该都是类似原理 其实是根据 id 进行删除
     */
    @DeleteMapping("/entity")
    public ResponseResult delete(User user) {
        repository.delete(user);
        return ResponseResult.success();
    }

    /**
     *  条件删除 删除 id 在 [low, high] 内的数据
     *  实现: 先进行查询 得出符合条件的所有记录 然后根据 id 进行 delete 操作 有多少条记录就进行多少次 delete 详见 introdu.txt 问题9
     */
    @DeleteMapping("/batch/range")
    public ResponseResult deleteByUserIdBetween(int low, int high) {
        int result = repository.deleteByUserIdBetween(low, high);
        return ResponseResult.success(result);
    }

    /**
     *  该删除方法是 JpaRepository 接口中的 并不是 CrudRepository
     *  该接口中的删除是只执行一句 SQL  Hibernate: delete from user where id=? or id=?
     */
    @DeleteMapping("/batch/list")
    public ResponseResult deleteBatch(List<User> list) {
        repository.deleteInBatch(list); // Hibernate: delete from user where id=? or id=?
        //repository.deleteAllInBatch(); // Hibernate: delete from user
        return ResponseResult.success();
    }

    @GetMapping
    public ResponseResult findUserByUserId(Integer userId) {
        // 仓库层的方法名字 get/find/save 等都可以 只要后面的 By 等条件遵守规约即可
        User user = repository.findUserByUserId(userId);
        return ResponseResult.success(user);
    }

}
