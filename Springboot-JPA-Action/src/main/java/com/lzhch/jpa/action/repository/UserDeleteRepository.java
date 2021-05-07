package com.lzhch.jpa.action.repository;

import com.lzhch.jpa.action.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * @packageName： com.lzhch.jpa.action.repository
 * @className: UserDeleteRepository
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2021-01-07 15:17
 */
@Repository
@Transactional(readOnly = true)
public class UserDeleteRepository extends SimpleJpaRepository<User, Long> {

    /**
     *  继承 SimpleJpaRepository 这个类就要注入
     */
    @Autowired
    public UserDeleteRepository(EntityManager em) {
        this(JpaEntityInformationSupport.getEntityInformation(User.class, em), em);
    }

    public UserDeleteRepository(JpaEntityInformation<User, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public UserDeleteRepository(Class<User> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> rec = findById(id);
        rec.ifPresent(super::delete);
    }


}
