package com.lzhch.mq.rocketmq.single.consumer;

import com.lzhch.mq.rocketmq.single.config.SingleRocketConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @packageName： com.lzhch.mq.rocketmq.single.consumer
 * @className: SingleRocketConsumer
 * @description:  单体 RocketMq 消费者端
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2022/6/17 15:55
 */


public class SingleRocketConsumer {

    public static void main(String[] args) throws MQClientException {
        //创建消费者
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer(SingleRocketConfig.DEFAULT_CONSUME_GROUP);
        //设置NameServer地址
        consumer.setNamesrvAddr(SingleRocketConfig.NAME_SERVER_ADDRESS);
        //设置实例名称
        consumer.setInstanceName(SingleRocketConfig.DEFAULT_INSTANCE_NAME);
        //订阅topic
        consumer.subscribe(SingleRocketConfig.DEFAULT_TOPIC, SingleRocketConfig.DEFAULT_TAGS);

        //监听消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                //获取消息
                for (MessageExt messageExt:list){
                    //RocketMQ由于是集群环境，所有产生的消息ID可能会重复
                    System.out.println("single rocketmq receive message :{} " + messageExt.getMsgId()+":"+new String(messageExt.getBody()));
                }
                //接受消息状态 1.消费成功    2.消费失败   队列还有
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者
        consumer.start();
        System.out.println("-------- Consumer Started! --------------");
    }

}
