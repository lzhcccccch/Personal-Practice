package com.lzhch.applicationcontext.publishevent.listener;

import com.lzhch.applicationcontext.publishevent.event.SelfEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

import java.util.HashMap;

/**
 *  监听类 该方式只能一个类监听一个事件
 *
 * @author: lzhch
 * @version: v1.0
 * @date: 2022/9/7 10:54
 */

// @Component
public class SelfEventListener implements ApplicationListener<SelfEvent> {

    /**
     *  类定义方式 只能针对于一个事件进行处理
     */

    @Override
    @Async
    public void onApplicationEvent(SelfEvent selfEvent) {
        HashMap<String, Object> source = (HashMap<String, Object>) selfEvent.getSource();

        System.out.println("类形式监听: {} " + source.toString());
    }

}
