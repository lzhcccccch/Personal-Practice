// package com.lzhch.mq.rocketmq.springboot.provider;
//
// import org.apache.rocketmq.client.producer.SendCallback;
// import org.apache.rocketmq.client.producer.SendResult;
// import org.apache.rocketmq.spring.core.RocketMQTemplate;
// import org.apache.rocketmq.spring.support.RocketMQHeaders;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.Message;
// import org.springframework.messaging.MessageHeaders;
// import org.springframework.messaging.support.MessageBuilder;
// import org.springframework.util.MimeTypeUtils;
// import org.springframework.web.bind.annotation.GetMapping;
//
// import javax.annotation.Resource;
// import java.util.ArrayList;
// import java.util.List;
//
// /**
//  * @packageName： com.lzhch.mq.rocketmq.springboot.provider
//  * @className: TestController
//  * @description: TODO
//  * @version: v1.0
//  * @author: liuzhichao
//  * @date: 2022/6/17 17:03
//  */
//
//
// public class TestController {
//
//     @Autowired
//     private RocketMQTemplate rocketMQTemplate;
//
//     /**
//      * 如果使用其他数据源的rocketTemplate，需要指定名称
//      */
//     @Resource(name = "extRocketMQTemplate")
//     private RocketMQTemplate extRocketMQTemplate;
//
//     @GetMapping("/test1")
//     public String test1() {
//         sendBatch();
//         return "test1";
//     }
//
//     @GetMapping("/test2")
//     public String test2() {
//         sendString();
//         return "test2";
//     }
//
//     @GetMapping("/test3")
//     public String test3() {
//         sendSelfObject();
//         return "test3";
//     }
//
//     @GetMapping("/test4")
//     public String test4() {
//         testRocketMQTemplateTransaction();
//         return "test4";
//     }
//
//     @GetMapping("/test5")
//     public String test5() {
//         testSendAndReceive();
//         return "test5";
//     }
//
//     @GetMapping("/test6")
//     public String test6() {
//         testSelfAck();
//         return "test6";
//     }
//
//     /**
//      * 测试手动回复应答消息
//      */
//     private void testSelfAck() {
//         String stringSelfAck = MQConstants.STRING_SELF_ACK;
//         for (int i = 0; i < 10; i++) {
//             rocketMQTemplate.convertAndSend(stringSelfAck + ":tag" + i, "this is self ack " + i);
//         }
//     }
//
//     /**
//      * 发送具有回传消息的消息。 要求消费者实现 RocketMQReplyListener 接收并回复消息
//      *
//      * @see ObjectConsumerWithReply
//      */
//     private void testSendAndReceive() {
//         String objectRequestTopic = MQConstants.SPRING_RECEIVE_OBJ;
//         // Send request in async mode and receive a reply of User type.
//         rocketMQTemplate.sendAndReceive(objectRequestTopic, new User().setUserAge((byte) 9).setUserName("requestUserName"), new RocketMQLocalRequestCallback<User>() {
//             @Override
//             public void onSuccess(User message) {
//                 System.out.printf("send user object and receive %s %n", message.toString());
//             }
//
//             @Override
//             public void onException(Throwable e) {
//                 e.printStackTrace();
//             }
//         }, 5000);
//     }
//
//     private void testRocketMQTemplateTransaction() throws MessagingException {
//         String springTransTopic = MQConstants.SPRING_TRANS_TOPIC;
//         String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
//         for (int i = 0; i < 10; i++) {
//             try {
//                 Message msg = MessageBuilder.withPayload("rocketMQTemplate transactional message " + i).
//                         setHeader(RocketMQHeaders.TRANSACTION_ID, "KEY_" + i).build();
//                 SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(
//                         springTransTopic + ":" + tags[i % tags.length], msg, null);
//                 System.out.printf("------rocketMQTemplate send Transactional msg body = %s , sendResult=%s %n",
//                         msg.getPayload(), sendResult.getSendStatus());
//                 Thread.sleep(10);
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         }
//     }
//
//     /**
//      * 发送自定义对象携带自定义的header
//      */
//     private void sendSelfObject() {
//         Message<User> build = MessageBuilder.withPayload(
//                         new User().setUserAge((byte) 21).setUserName("sendSelfObject"))
//                 .setHeader("MY_HEADER", "MY_VALUE")
//                 .setHeader(RocketMQHeaders.KEYS, "key1")
//                 .build();
//         rocketMQTemplate.syncSend(MQConstants.SELF_TOPIC, build);
//     }
//
//     private void sendBatch() {
//         /**
//          * 发送批量消息
//          */
//         String topic = MQConstants.STRING_BATCH;
//         String tags[] = new String[]{"tag0", "tag1", "tag2", "tag3"};
//         List<Message> msgs1 = new ArrayList<Message>();
//         for (int i = 0; i < 10; i++) {
//             msgs1.add(MessageBuilder.withPayload("Hello RocketMQ Batch Msg#" + i)
//                     .setHeader(RocketMQHeaders.KEYS, "KEY_" + i)
//                     .build());
//         }
//         SendResult sr = rocketMQTemplate.syncSend(topic, msgs1, 60000);
//         System.out.println("--- Batch messages send result :" + sr);
//
//         /**
//          * 发送批量有序性消息， 根据key 来选择队列
//          */
//         topic = MQConstants.STRING_BATCH_ORDER;
//         for (int q = 0; q < 4; q++) {
//             // send to 4 queues
//             List<Message> msgs = new ArrayList<Message>();
//             for (int i = 0; i < 10; i++) {
//                 int msgIndex = q * 10 + i;
//                 String msg = String.format("Hello RocketMQ Batch Msg#%d to queue: %d", msgIndex, q);
//                 msgs.add(MessageBuilder.withPayload(msg).
//                         setHeader(RocketMQHeaders.KEYS, "KEY_" + msgIndex).build());
//             }
//             sr = rocketMQTemplate.syncSendOrderly(topic, msgs, q + "", 60000);
//             System.out.println("--- Batch messages orderly to queue :" + sr.getMessageQueue().getQueueId() + " send result :" + sr);
//         }
//     }
//
//     private void sendString() {
//         String springTopic = MQConstants.STRING_TOPIC;
//         String delayTopic = MQConstants.DELAY_TOPIC;
//         String userTopic = MQConstants.USER_TOPIC;
//
//         /***********第一种发送方式*********/
//         // 同步发送消息。默认重复两次。不指定超时时间会拿producer 全局的默认超时时间(默认3s)
//         SendResult sendResult = rocketMQTemplate.syncSend(springTopic, "Hello, World!");
//         System.out.printf("syncSend1 to topic %s sendResult=%s %n", springTopic, sendResult);
//         // 指定超时时间是10 s
//         sendResult = rocketMQTemplate.syncSend(springTopic, "Hello, World2!", 10 * 1000);
//         System.out.printf("syncSend2 to topic %s sendResult=%s %n", springTopic, sendResult);
//         // 发送消息并且指定tag
//         sendResult = rocketMQTemplate.syncSend(springTopic + ":tag0", "Hello, World! tag0!");
//         System.out.printf("syncSend1 to topic %s sendResult=%s %n", springTopic, sendResult);
//         // 发送自定义的对象，默认会转为JSON串进行发送
//         sendResult = rocketMQTemplate.syncSend(userTopic, new User().setUserAge((byte) 18).setUserName("Kitty"));
//         System.out.printf("syncSend3 to topic %s sendResult=%s %n", userTopic, sendResult);
//         // 单方向发送消息
//         rocketMQTemplate.sendOneWay(springTopic, "Hello, World! sendOneWay!");
//         // 延迟消息。发送 spring message 对象， 指定超时时间是10 s, 并且指定延迟等级
//         sendResult = rocketMQTemplate.syncSend(delayTopic, MessageBuilder.withPayload(
//                 new User().setUserAge((byte) 21).setUserName("Delay")).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build(), 10 * 1000, 3);
//         System.out.printf("syncSend5 to topic %s sendResult=%s %n", delayTopic, sendResult);
//         // 异步发送, 指定回调与超时时间
//         rocketMQTemplate.asyncSend(springTopic, new User().setUserAge((byte) 180).setUserName("asyncSend"), new SendCallback() {
//             @Override
//             public void onSuccess(SendResult sendResult) {
//                 System.out.println("asyncSend onSuccess:" + sendResult);
//             }
//
//             @Override
//             public void onException(Throwable throwable) {
//                 System.out.println("asyncSend error:" + throwable.getMessage());
//             }
//         }, 10 * 1000);
//
//         /***********第二种发送方式*********/
//         // convertAndSend 方法发送，其底层也是调用的syncSend 方法，只是没有返回结果
//         String stringExtTopic = MQConstants.STRING_EXT_TOPIC;
//         rocketMQTemplate.convertAndSend(stringExtTopic + ":tag0", "I'm from tag0");
//         System.out.printf("syncSend topic %s tag %s %n", springTopic, "tag0");
//         rocketMQTemplate.convertAndSend(stringExtTopic + ":tag1", "I'm from tag1");
//         System.out.printf("syncSend topic %s tag %s %n", springTopic, "tag1");
//     }
//
// }
