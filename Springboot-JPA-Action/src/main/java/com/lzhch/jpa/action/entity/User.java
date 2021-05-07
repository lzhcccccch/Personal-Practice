package com.lzhch.jpa.action.entity;

import com.bcp.sdp.jpa.base.domain.BaseEntity;
import com.bcp.sdp.jpa.common.annotations.Repeat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * @packageName： com.lzhch.jpa.action.entity
 * @className: User
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-12-29 16:05
 */
//@Entity(name = "user") //指定表名, 和下面的 @Table 作用一样. 不指定则默认用类名
@Table(name = "my_user")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert // 动态插入, 即值为空时, SQL 中不写入此字段
@DynamicUpdate
@Builder
@EntityListeners(AuditingEntityListener.class) // 持久化前做的操作，可填充字段如date
//@Where(clause = "is_delete = 1") // 添加在类上实现查询自动过滤 不能添加在字段上
public class User extends BaseEntity<Long> {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;

    //@Column(name = "user_id", unique = true, length = 4)
    @Column(name = "user_id", length = 4)
    @Repeat(message = "用户 id 已存在, 不能重复添加.")
    private Integer userId;

    @Column(name = "user_name", length = 20)
    private String userName;

    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "phone", length = 13)
    private Long phone;

    @Column(name = "address")
    private String address;

    //@CreationTimestamp // 该注解可单独使用, 无需其他配置
    //@Column(name = "create_time", updatable = false)
    //@CreatedDate // 可注解不可单独, 需要在启动类加 @EnableJpaAuditing 注解, 实体类上加 @EntityListeners(AuditingEntityListener.class)
    //@JsonFormat(
    //        shape = JsonFormat.Shape.STRING,
    //        pattern = "yyyy-MM-dd HH:mm:ss",
    //        timezone = "GMT+8" // 上海时区
    //)
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    //private Date createTime;

    //@UpdateTimestamp // 该注解可单独使用, 无需其他配置
    //@Column(name = "update_time" )
    //@LastModifiedDate // 可注解不可单独, 需要在启动类加 @EnableJpaAuditing 注解, 实体类上加 @EntityListeners(AuditingEntityListener.class)
    //@JsonFormat(
    //        shape = JsonFormat.Shape.STRING,
    //        pattern = "yyyy-MM-dd HH:mm:ss",
    //        timezone = "GMT+8" // 上海时区
    //)
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    //private Date updateTime;

}
