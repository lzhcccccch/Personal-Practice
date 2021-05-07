package com.lzhch.jpa.action.service.impl;

import com.bcp.sdp.jpa.base.application.impl.BaseServiceImpl;
import com.lzhch.jpa.action.controller.dto.res.UserRes;
import com.lzhch.jpa.action.entity.User;
import com.lzhch.jpa.action.repository.UserRepository;
import com.lzhch.jpa.action.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @packageName： com.lzhch.jpa.action.service.impl
 * @className: UserServiceImpl
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2021-01-22 11:20
 */
@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<UserRes, Long, User, UserRepository> implements UserService {
}
