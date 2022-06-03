package com.lzhch.mq.rocketmq.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @packageName： com.lzhch.mq.rocketmq.consumer
 * @className: RocketConsumer
 * @description:  RocketMQ 消费者
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2022/5/30 10:52
 */

@Component
@RocketMQMessageListener(topic = "topic_oms_production_order_dev", consumerGroup = "ORDER_OMS_PRODUCTION_DEV")
public class RocketConsumer implements RocketMQListener<String> {


    @Override
    public void onMessage(String s) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("receive message :{} " + s);

        return ;
    }


    // public static void main(String[] args) throws MQClientException {
    //     //创建消费者
    //     DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("ORDER_OMS_PRODUCTION_DEV");
    //     //设置NameServer地址
    //     consumer.setNamesrvAddr("10.206.106.1:9876");
    //     //设置实例名称
    //     consumer.setInstanceName("consumer_lzhchLocal");
    //     //订阅topic
    //     consumer.subscribe("topic_oms_production_order_dev", "testServer_lzhch");
    //
    //     //监听消息
    //     consumer.registerMessageListener(new MessageListenerConcurrently() {
    //         @Override
    //         public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
    //             //获取消息
    //             for (MessageExt messageExt:list){
    //                 //RocketMQ由于是集群环境，所有产生的消息ID可能会重复
    //                 System.out.println("receive message :{} " + messageExt.getMsgId()+":"+new String(messageExt.getBody()));
    //             }
    //             //接受消息状态 1.消费成功    2.消费失败   队列还有
    //             return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    //         }
    //     });
    //     //启动消费者
    //     consumer.start();
    //     System.out.println("-------- Consumer Started! --------------");
    // }

}
