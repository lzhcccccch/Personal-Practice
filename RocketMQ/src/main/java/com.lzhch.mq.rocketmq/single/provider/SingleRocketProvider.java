package com.lzhch.mq.rocketmq.single.provider;

import com.lzhch.mq.rocketmq.single.config.SingleRocketConfig;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @packageName： com.lzhch.mq.rocketmq.single
 * @className: SingleRocketProvider
 * @description: 单体 RocketMq 生产者
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2022/6/17 15:56
 */

public class SingleRocketProvider {

    public static void main(String[] args) throws MQClientException {
        //创建一个生产者
        DefaultMQProducer producer = new DefaultMQProducer(SingleRocketConfig.DEFAULT_PROVIDER_GROUP);
        //设置NameServer地址
        producer.setNamesrvAddr(SingleRocketConfig.NAME_SERVER_ADDRESS);
        //设置生产者实例名称
        producer.setInstanceName(SingleRocketConfig.DEFAULT_INSTANCE_NAME);
        //启动生产者
        producer.start();

        try {
            //发送消息
            for (int i = 0; i < 10; i++) {
                //模拟网络延迟，每秒发送一次MQ
                Thread.sleep(1000);
                //创建消息，topic主题名称  tags临时值代表小分类， body代表消息体
                Message message = new Message(SingleRocketConfig.DEFAULT_TOPIC, SingleRocketConfig.DEFAULT_TAGS, ("singleRocketSendMessage-" + i).getBytes());
                //发送消息
                SendResult sendResult = producer.send(message);
                System.out.println("singleRocketSendMessageResult :{} " + sendResult.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }

}
