package com.lzhch.mq.rocketmq.springboot.consumer;

import com.alibaba.fastjson.JSONObject;
import com.lzhch.mq.rocketmq.springboot.config.RocketMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @packageName： com.lzhch.mq.rocketmq.consumer
 * @className: RocketConsumer
 * @description:  基于 springboot 的 RocketMq 生产者
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2022/5/30 10:52
 */

@Slf4j
@Component
@RocketMQMessageListener(
        // topic: 消息的发送者使用同一个topic
        topic = RocketMqConfig.DEFAULT_TOPIC,
        // group: 不用和生产者group相同(在 RocketMQ 中消费者和发送者组没有关系)
        consumerGroup = RocketMqConfig.DEFAULT_CONSUMER_GROUP,
        // tag: 设置为 * 时, 表示全部
        selectorExpression = "*",
        // selectorExpression = "sendOneWayObject", // 过滤的不是 Message 类型的 Header 中的 TAG, 而是过滤发送的时候以 topic:tag 形式的 tag
        // 消费模式: 默认 CLUSTERING(CLUSTERING: 负载均衡)(BROADCASTING: 广播机制)
        messageModel = MessageModel.CLUSTERING)
public class RocketConsumer implements RocketMQListener<Map<String, Object>> {

    @Override
    public void onMessage(Map<String, Object> message) {
        log.info("rocketMQ receive message params :{} " + JSONObject.toJSONString(message));
    }

}
