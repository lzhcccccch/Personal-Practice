package com.lzhch.mq.rocketmq.provider;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @packageName： com.lzhch.mq.rocketmq.provider
 * @className: RocketProvider
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2022/5/30 10:44
 */

@Slf4j
@RestController
public class RocketProvider {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @PostMapping("/test")
    public String test() {
        return "success";
    }

    @PostMapping("/syncSend")
    public void syncSend(String msg) {
        String destination = "topic_oms_production_order_dev";
        Message<String> message = MessageBuilder.withPayload(msg).build();

        SendResult sendResult = this.rocketMQTemplate.syncSend(destination, message);
        log.info("send message :{} " + JSONObject.toJSONString(sendResult));
    }

    // public static void main(String[] args) throws MQClientException {
    //     //创建一个生产者
    //     DefaultMQProducer producer = new DefaultMQProducer("ORDER_OMS_PRODUCTION_DEV");
    //     //设置NameServer地址
    //     producer.setNamesrvAddr("10.206.106.1:9876");
    //     //设置生产者实例名称
    //     producer.setInstanceName("broker-a");
    //     //启动生产者
    //     producer.start();
    //
    //     try {
    //         //发送消息
    //         for (int i = 1; i <= 3; i++) {
    //             //模拟网络延迟，每秒发送一次MQ
    //             Thread.sleep(1000);
    //             //创建消息，topic主题名称  tags临时值代表小分类， body代表消息体
    //             Message message = new Message("topic_oms_production_order_dev", "testServer_lzhch", ("sendMsg-" + i).getBytes());
    //             //发送消息
    //             SendResult sendResult = producer.send(message);
    //             System.out.println("result :{} " + sendResult.toString());
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     producer.shutdown();
    // }

}
