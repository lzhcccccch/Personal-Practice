package com.lzhch.applicationcontext.publishevent.publisher;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *  事件发布类
 *
 * @author: lzhch
 * @version: v1.0
 * @date: 2022/9/7 11:00
 */

@Component
public class SelfEventPublisher {

    @Resource
    private ApplicationContext applicationContext;

    // 直接以 ApplicationContext 为入参 统一发布路径
    public void publish(ApplicationEvent applicationEvent) {
        applicationContext.publishEvent(applicationEvent);
    }

}
