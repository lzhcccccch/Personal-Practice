package com.lzhch.mq.rocketmq.springboot.consumer;

import com.alibaba.fastjson.JSONObject;
import com.lzhch.mq.rocketmq.springboot.config.RocketMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @packageName： com.lzhch.mq.rocketmq.springboot.consumer
 * @className: RocketReplyConsume
 * @description:  回传消息的消费者
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2022/6/21 17:15
 */

@Slf4j
@Component
@RocketMQMessageListener(topic = RocketMqConfig.DEFAULT_TOPIC, consumerGroup = RocketMqConfig.DEFAULT_CONSUMER_GROUP)
public class RocketReplyConsume implements RocketMQReplyListener<Map<String, Object>, Map<String, Object>> {

    @Override
    public Map<String, Object> onMessage(Map<String, Object> message) {
        log.info("rocket reply receive message :{} " + JSONObject.toJSONString(message));

        // 观察设置回调函数与不设置的执行顺序
        // try {
        //     log.info("睡眠 500 毫秒");
        //     Thread.sleep(1500);
        // } catch (InterruptedException e) {
        //     throw new RuntimeException(e);
        // }

        Map<String, Object> result = new HashMap<>(3);
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", JSONObject.toJSONString(message));
        return result;
    }

}
