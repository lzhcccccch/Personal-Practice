package com.lzhch.applicationcontext.publishevent.controller;

import com.lzhch.applicationcontext.publishevent.event.SelfEvent;
import com.lzhch.applicationcontext.publishevent.publisher.SelfEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 事件推送控制器
 *
 * @author: lzhch
 * @version: v1.0
 * @date: 2022/9/7 14:02
 */

@RestController
@RequestMapping("/publishEvent")
public class PublishEventController {

    @Resource
    private SelfEventPublisher eventPublisher;

    @PostMapping("/classListener")
    public void classListener(@RequestParam Map<String, Object> params) {
        this.eventPublisher.publish(new SelfEvent(params));
    }

    @PostMapping("/annotationListener")
    public void annotationListener(Map<String, Object> params) {
        this.eventPublisher.publish(new SelfEvent(params));
    }

}
