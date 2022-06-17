package com.lzhch.mq.rocketmq.springboot.provider;

import com.alibaba.fastjson.JSONObject;
import com.lzhch.mq.rocketmq.springboot.config.RocketMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @packageName： com.lzhch.mq.rocketmq.provider
 * @className: RocketProvider
 * @description:  基于 springboot 的 RocketMq 生产者
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2022/5/30 10:44
 */

@Slf4j
@RestController
@RequestMapping("/rocketmq")
public class RocketProvider {

    private final RocketMQTemplate rocketMQTemplate;

    public RocketProvider(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @PostMapping("/syncSend")
    public void syncSend(String msg) {
        log.info("rocketmq syncSend params :{} " + msg);
        String defaultTopic = RocketMqConfig.DEFAULT_TOPIC;
        SendResult sendResult = this.rocketMQTemplate.syncSend(defaultTopic, msg);

        msg = msg + "message";
        Message<String> message = MessageBuilder.withPayload(msg)
                .setHeader(RocketMQHeaders.KEYS, "selfKeys")
                .setHeader(RocketMQHeaders.TAGS, "selfTags")
                .build();
        this.rocketMQTemplate.syncSend(defaultTopic, message, 30000, 1);
        log.info("rocketmq syncSend result :{} " + JSONObject.toJSONString(sendResult));
    }

}
