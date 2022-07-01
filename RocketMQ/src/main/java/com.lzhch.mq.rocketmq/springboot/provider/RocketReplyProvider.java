package com.lzhch.mq.rocketmq.springboot.provider;

import com.alibaba.fastjson.JSONObject;
import com.lzhch.mq.rocketmq.springboot.config.RocketMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQLocalRequestCallback;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @packageName： com.lzhch.mq.rocketmq.springboot.provider
 * @className: RocketReplyProvider
 * @description:  发送具有回传消息的生产者
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2022/6/21 17:06
 */

@Slf4j
@RestController
@RequestMapping("/rocket/reply")
public class RocketReplyProvider {

    private final RocketMQTemplate rocketMQTemplate;

    public RocketReplyProvider(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     *  发送具有回传消息的 Message:
     *      该方式适用于 producer 发送了消息之后需要根据 broker 的消费拿到一些参数或者要根据结果进行业务处理的场景
     *  有两种形式:
     *      1. 不指定回调函数: 该方式更像同步发送, 会返回发送结果(成功与否)以及自定义的返回参数, 可以在拿到结果后进行处理
     *      2. 指定回调函数: 该方式更像是异步发送, broker 的处理结果通过回调函数返回, 可以在指定的回调函数中处理相应的逻辑
     * @param params
     */
    @PostMapping("/sendAndReceive")
    public void sendAndReceive(@RequestBody Map<String, Object> params) {
        String defaultTopic = RocketMqConfig.DEFAULT_TOPIC;
        /**
         *  不指定回调函数的方法更像是同步执行, 要等待 consumer 返回结果才继续执行
         *  provider 发送-->consumer 接收-->consumer 返回结果-->provider 拿到结果
         */
        log.info("rocket sendAndReceive params :{} " + JSONObject.toJSONString(params));
        Map receive = this.rocketMQTemplate.sendAndReceive(defaultTopic, params, Map.class, 5000);
        log.info("rocket sendAndReceive result :{} " + JSONObject.toJSONString(receive));

        Message<Map<String, Object>> message = MessageBuilder.withPayload(params).build();
        log.info("rocket sendAndReceive message :{} " + JSONObject.toJSONString(message));
        Map messageReceive = this.rocketMQTemplate.sendAndReceive(defaultTopic, message, Map.class, 5000);
        log.info("rocket sendAndReceive result :{} " + JSONObject.toJSONString(messageReceive));

        /**
         *  指定回调函数更像是异步发送, 不必等待 consumer 返回结果便继续执行下面代码
         */
        params.put("method", "callback");
        log.info("sendAndReceive callback params :{} " + JSONObject.toJSONString(params));
        this.rocketMQTemplate.sendAndReceive(defaultTopic, params, new RocketMQLocalRequestCallback() {
            @Override
            public void onSuccess(Object message) {
                log.info("具有回传消息的 MQ 发送成功, result :{} " + JSONObject.toJSONString(message));
            }

            @Override
            public void onException(Throwable e) {
                log.info("具有回传消息的 MQ 发送失败! result :{} " + e.getMessage());
            }
        }, 6000);
        log.info("sendAndReceive callback result :{} ");

        params.put("type", "message");
        Message<Map<String, Object>> mapMessage = MessageBuilder.withPayload(params).build();
        log.info("sendAndReceive callback message :{} " + JSONObject.toJSONString(mapMessage));
        this.rocketMQTemplate.sendAndReceive(defaultTopic, mapMessage, new RocketMQLocalRequestCallback() {
            @Override
            public void onSuccess(Object message) {
                log.info("具有回传消息的 MQ 发送成功, result :{} " + JSONObject.toJSONString(message));
            }

            @Override
            public void onException(Throwable e) {
                log.info("具有回传消息的 MQ 发送失败! result :{} " + e.getMessage());
            }
        }, 6000);
        log.info("sendAndReceive callback result :{} ");
    }

}
