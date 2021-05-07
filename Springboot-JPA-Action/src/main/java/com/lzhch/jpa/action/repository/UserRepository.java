package com.lzhch.jpa.action.repository;

import com.bcp.sdp.jpa.base.persistence.BaseRepository;
import com.lzhch.jpa.action.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @packageName： com.lzhch.jpa.action.repository
 * @className: UserRepository
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-12-29 16:04
 */
@Repository
//public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
public interface UserRepository extends BaseRepository<User, Long> {
    /**
     *  nativeQuery = true 表示是原生 SQL 那么就得写 u.user_id 对应数据库
     *  如果 nativeQuery = false(默认) 那么就得写 u.userId 对应类的字段
     *  该修改必须修改全部值, 否则会将原有的值修改为空, 除非写动态 SQL
     *  修改和删除操作必须显式的添加事务
     */
    @Transactional
    @Modifying
    @Query(value = "update User u set u.user_id = :#{#user.userId}, u.user_name = :#{#user.userName}, u.password = :#{#user.password}, u.phone = :#{#user.phone}, u.address = :#{#user.address}, u.create_time = :#{#user.createTime}, u.update_time = :#{#user.updateTime} where u.id = :#{#user.id}", nativeQuery = true)
    public int updateUser(User user);

    /**
     *  update/delete 操作都必须显式的添加事务
     */
    @Transactional
    public int deleteByUserIdBetween(int low, int high);

    /**
     *  自定义条件查询 find/save/get 等都可 只要 By 后面遵守规约即可
     */
    public User findUserByUserId(int userId);

}
