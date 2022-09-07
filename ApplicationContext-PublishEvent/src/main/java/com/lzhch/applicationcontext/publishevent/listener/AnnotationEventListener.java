package com.lzhch.applicationcontext.publishevent.listener;

import com.lzhch.applicationcontext.publishevent.event.SelfEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 *  基于注解监听类 可以以业务类别进行分类监听
 *
 * @author: lzhch
 * @version: v1.0
 * @date: 2022/9/7 11:07
 */

@Component
public class AnnotationEventListener {

    /**
     * 基于注解实现的监听 可以在一个监听类中实现对多个事件的监听 适合某一类业务
     * 比如: 记录日志信息, 有多种日志格式, 多个事件, 但是是同一类业务
     */

    @Async
    @EventListener(SelfEvent.class)
    public void typeOne(SelfEvent event) {
        HashMap<String, Object> source = (HashMap<String, Object>) event.getSource();

        System.out.println("基于注解的监听 :{} " + source.toString());
    }

    // 可以根据 typeOne 进行对其他事件进行监听 建议根据业务分类:比如不同的日志可以用同一个监听类
    public void typeTwo() {
        return;
    }

}
