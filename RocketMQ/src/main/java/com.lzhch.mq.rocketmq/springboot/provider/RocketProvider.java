package com.lzhch.mq.rocketmq.springboot.provider;

import com.alibaba.fastjson.JSONObject;
import com.lzhch.mq.rocketmq.springboot.config.RocketMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @packageName： com.lzhch.mq.rocketmq.provider
 * @className: RocketProvider
 * @description: 基于 springboot 的 RocketMq 生产者
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

    /**
     * 同步发送消息: 阻塞发送 消息体可以是 Object 和 Message 可批量发送
     * producer 向 broker 发送消息, 执行 API 时同步等待, 直到 broker 服务器返回发送结果.
     * 默认执行 2 次, 不指定超时时间则默认用 produce 全局的超时时间(3 秒)
     *
     * @param params
     */
    @PostMapping("/syncSend")
    public void syncSend(@RequestBody Map<String, Object> params) {
        Assert.notNull(params, "MQ 入参不能为空");
        log.info("rocketmq syncSend params :{} " + params.toString());
        String defaultTopic = RocketMqConfig.DEFAULT_TOPIC;
        SendResult objectSendResult = this.rocketMQTemplate.syncSend(defaultTopic, params);
        log.info("rocketmq syncSend result :{} " + JSONObject.toJSONString(objectSendResult));

        /**
         *  Message 类型的消息可以带一些自定义的头部的信息, 比如 Tags Keys ...
         */
        params.put("type", "message");
        Message<Map<String, Object>> message = MessageBuilder.withPayload(params)
                .setHeader(RocketMQHeaders.KEYS, "selfKeys")
                .setHeader(RocketMQHeaders.TAGS, "syncMessage")
                .build();
        log.info("rocketmq syncSend message :{} " + JSONObject.toJSONString(message));
        // params: 主题  消息内容   超时时间    延迟等级
        SendResult messageSendResult = this.rocketMQTemplate.syncSend(defaultTopic, message, 3000, 1);
        log.info("rocketmq syncSend result :{} " + JSONObject.toJSONString(messageSendResult));

        /**
         *  批量发送: 消息体只能是 Message 类型
         */
        List<Message> list = this.initList(params).get();
        log.info("rocket batch syncSend params :{} " + JSONObject.toJSONString(list));
        SendResult syncSend = this.rocketMQTemplate.syncSend(defaultTopic, list);
        log.info("batch syncSend result :{} " + JSONObject.toJSONString(syncSend));

        /**
         *  顺序发送: 可发送单个消息, 也可以批量发送
         */
        log.info("orderly syncSend params :{} " + JSONObject.toJSONString(list));
        // 批量发送: 第三个参数是指定发送的队列, 循环发送时可指定为 index
        // 顺序发送可以保证消息有序的被消费, 比如一个完整的订单: 创建订单-->支付-->完成 则需要保证消费消息的顺序, 所以要将这一组消息(三条) 发送到同一个队列进行有序消费
        SendResult syncSendOrderly = this.rocketMQTemplate.syncSendOrderly(defaultTopic, list, "1", 3000);
        log.info("orderly syncSend result :{} " + JSONObject.toJSONString(syncSendOrderly));

        log.info("for orderly syncSend params :{} " + JSONObject.toJSONString(message));
        for (int i = 0; i < 3; i++) {
            SendResult orderly = this.rocketMQTemplate.syncSendOrderly(defaultTopic, message, i + "");
            log.info("foreach orderly syncSend result :{} " + JSONObject.toJSONString(orderly));
        }
    }

    /**
     * 异步发送消息: 非阻塞 消息体可以是 Object 和 Message 可批量发送
     * producer 向 broker 发送消息时指定消息发送成功和发送异常的回调方法, 调用 API 后立即返回
     * producer 发送消息线程不阻塞, 消息发送成功或者失败的回调任务在一个新的线程中执行
     *
     * @param params
     */
    @PostMapping("/asyncSend")
    public void asyncSend(@RequestBody Map<String, Object> params) {
        Assert.notNull(params, "MQ 入参不能为空");
        log.info("rocketmq asyncSend params :{} " + params.toString());
        String defaultTopic = RocketMqConfig.DEFAULT_TOPIC;
        /**
         *  指定异步发送成功或者失败之后的处理方法
         */
        SendCallback callback = new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("异步 MQ 发送成功, result :{} " + JSONObject.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("异步 MQ 发送失败! result :{} " + throwable.getMessage());
                System.out.println(throwable.getStackTrace());
            }
        };
        this.rocketMQTemplate.asyncSend(defaultTopic, params, callback, 3000);
        log.info("rocketmq asyncSend params end :{} ");

        /**
         *  Message 包装
         */
        params.put("type", "message");
        Message<Map<String, Object>> message = MessageBuilder.withPayload(params)
                .setHeader(RocketMQHeaders.TAGS, "asyncMessage")
                .build();
        log.info("rocketmq asyncSend message :{} " + JSONObject.toJSONString(message));
        this.rocketMQTemplate.asyncSend(defaultTopic, message, callback);
        log.info("rocketmq asyncSend message end :{} ");

        /**
         *  批量发送, 消息体只能是 Message 类型
         */
        List<Message> list = this.initList(params).get();
        log.info("rocket batch asyncSend params :{} " + JSONObject.toJSONString(list));
        this.rocketMQTemplate.asyncSend(defaultTopic, list, callback, 3000);
        log.info("batch asyncSend result :{} ");

        /**
         *  顺序发送
         */
        log.info("orderly asyncSend params :{} " + JSONObject.toJSONString(message));
        this.rocketMQTemplate.asyncSendOrderly(defaultTopic, message, "1", callback, 3000);
        log.info("orderly asyncSend result :{} ");

        log.info("for orderly asyncSend params :{} " + JSONObject.toJSONString(list));
        for (int i = 0; i < 3; i++) {
            this.rocketMQTemplate.asyncSendOrderly(defaultTopic, list, i + "", callback, 3000);
            log.info("for orderly asyncSend result :{} ");
        }
    }

    /**
     * 单向消息: 非阻塞消息  消息体可以是 Object 和 Message 可批量发送
     * producer 向 broker 发送消息, 执行 API 时直接返回, 不等待服务器返回结果
     *
     * @param params
     */
    @PostMapping("/sendOneWay")
    public void sendOneWay(@RequestBody Map<String, Object> params) {
        Assert.notNull(params, "MQ 入参不能为空");
        String defaultTopic = RocketMqConfig.DEFAULT_TOPIC;
        log.info("rocketMQ sendOneWay params :{} " + params.toString());
        this.rocketMQTemplate.sendOneWay(defaultTopic + ":sendOneWayObject", params);
        log.info("rocketMQ sendOneWay end :{}");

        params.put("type", "message");
        Message<Map<String, Object>> message = MessageBuilder.withPayload(params)
                .setHeader(RocketMQHeaders.TAGS, "sendOneWayMessage")
                .build();
        log.info("rocketMQ sendOneWay message :{} " + JSONObject.toJSONString(message));
        this.rocketMQTemplate.sendOneWay(defaultTopic, message);
        log.info("rocketMQ sendOneWay end :{} ");
    }

    /**
     * convertAndSend: 底层也是调用了 syncSend
     *
     * @param params
     */
    @PostMapping("/convertAndSend")
    public void convertAndSend(@RequestBody Map<String, Object> params) {
        log.info("convertAndSend params :{} " + JSONObject.toJSONString(params));
        String defaultTopic = RocketMqConfig.DEFAULT_TOPIC;
        this.rocketMQTemplate.convertAndSend(defaultTopic, params);

        Message<Map<String, Object>> message = MessageBuilder.withPayload(params).build();
        this.rocketMQTemplate.convertAndSend(defaultTopic, message);
        log.info("convertAndSend result :{} ");
    }

    /**
     * 事务消息:
     * @param params
     */
    @PostMapping("/sendMessageInTransaction")
    public void sendMessageInTransaction(@RequestBody Map<String, Object> params) {
        log.info("sendMessageInTransaction params :{} " + JSONObject.toJSONString(params));
        String defaultTopic = RocketMqConfig.DEFAULT_TOPIC;

        Message<Map<String, Object>> message = MessageBuilder.withPayload(params).build();
        TransactionSendResult transactionSendResult = this.rocketMQTemplate.sendMessageInTransaction(defaultTopic, message, params);
        log.info("transactionSendResult :{} " + JSONObject.toJSONString(transactionSendResult));
    }


    /**
     * @description: 初始化列表
     * @param: [params]
     * @return: java.util.Optional<java.util.List < org.springframework.messaging.Message>>
     * @author: liuzhichao 2022/6/21 15:47
     */
    private Optional<List<Message>> initList(Map<String, Object> params) {
        List<Message> list = new ArrayList<>(10);
        Message message1 = MessageBuilder.withPayload(params).build();
        list.add(message1);
        Map<String, Object> param1 = new HashMap<>(3);
        param1.put("content", 1002);
        Message message2 = MessageBuilder.withPayload(param1).build();
        list.add(message2);
        Map<String, Object> param2 = new HashMap<>(3);
        param2.put("content", 1003);
        Message message3 = MessageBuilder.withPayload(param2).build();
        list.add(message3);
        Map<String, Object> param3 = new HashMap<>(3);
        param3.put("content", 1004);
        Message message4 = MessageBuilder.withPayload(param3).build();
        list.add(message4);
        Map<String, Object> param4 = new HashMap<>(3);
        param4.put("content", 1005);
        Message message5 = MessageBuilder.withPayload(param4).build();
        list.add(message5);

        return Optional.ofNullable(list);
    }


}
