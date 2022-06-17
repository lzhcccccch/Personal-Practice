package com.lzhch.mq.rocketmq.springboot.consumer;

import com.lzhch.mq.rocketmq.springboot.config.RocketMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

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
@RocketMQMessageListener(topic = RocketMqConfig.DEFAULT_TOPIC, consumerGroup = RocketMqConfig.DEFAULT_CONSUMER_GROUP)
public class RocketConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println("receive message :{} " + s);
        return ;
    }

}
