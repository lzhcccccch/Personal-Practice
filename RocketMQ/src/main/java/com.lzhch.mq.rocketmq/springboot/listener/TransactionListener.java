package com.lzhch.mq.rocketmq.springboot.listener;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

import java.util.Map;

/**
 * @packageName： com.lzhch.mq.rocketmq.springboot.consumer
 * @className: TransactionConsumer
 * @description:  RocketMQ 事务监听类
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2022/6/30 16:23
 */

@Slf4j
@RocketMQTransactionListener
public class TransactionListener implements RocketMQLocalTransactionListener {

    /**
     * 1. producer 向 broker 发送消息
     * 2. broker 将消息存储到 RMQ_SYS_TRANS_HALF_TOPIC 主题下之后, 向 producer 发送 ACK 消息确认, 此时消息成为半消息
     * 3. producer 开始执行本地事务逻辑
     * 4. producer 根据本地事务执行结果向 broker 提交二次确认(Commit 或者 Rollback) :
     *  4.1 broker 接收到 Commit 状态则将半消息标记为可投递, 重新发送到原 Topic 下, broker 重新进行消费
     *  4.2 broker 接收到 Rollback 状态则将消息丢弃
     *
     * 在断网或者是应用重启的特殊情况下, 上述步骤 4 提交的二次确认最终未到达 broker，经过固定时间后相关 broker 将对该消息发起消息回查
     * producer 收到消息回查后, 需要检查对应消息的本地事务执行的最终结果
     * producer 根据检查得到的本地事务的最终状态再次提交二次确认, broker 仍按照步骤 4 对半消息进行操作。
     */

    /**
     * 执行本地事务
     * @param msg
     * @param arg
     * @return 通过返回结果来判断是提交还是回滚还是未知状态
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("RocketMQ 事务消息消费者 - 执行本地事务... params :{} " + JSONObject.toJSONString(msg));
        /** msg 的 payload 消息格式为 base64 加密后的, 但是用 base64 进行解密失败 */
        // String json = Base64.getDecoder().decode(msg.getPayload().toString()).toString();
        // Map<String, Object> params = JSONObject.toJavaObject(JSONObject.parseObject(json), Map.class);
        // Integer content = (Integer) params.get("content");

        Map<String, Object> params = (Map<String, Object>) arg;
        Integer content = (Integer) params.get("content");
        if (content > 1000 && content < 2000) {
            log.info("executeLocalTransaction commit params :{} " + content);
            // 消息通过, 允许消费者消费消息
            return RocketMQLocalTransactionState.COMMIT;
        }
        if (content > 2000) {
            log.info("executeLocalTransaction rollback params :{} " + content);
            // 明确回复回滚操作, 消息将会被删除, 不允许被消费
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        log.info("executeLocalTransaction unknown params :{} " + content);
        // 消息无响应, 代表需要回查本地事务状态来决定是提交还是回滚事务
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    /**
     * 回查本地事务, 根据检查结果(return)来判断是提交还是回滚
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("RocketMQ 事务消息消费者 - 回查本地事务... params :{} " + JSONObject.toJSONString(msg));

        /** msg 消息的 payload 为字节码, 不知道该怎么解析 */
        // String json = Base64.getDecoder().decode(msg.getPayload().toString()).toString();
        // Map<String, Object> params = JSONObject.toJavaObject(JSONObject.parseObject(json), Map.class);
        // Integer content = (Integer) params.get("content");
        //
        // if (content > 1000 && content < 2000) {
        //     log.info("checkLocalTransaction commit params :{} " + content);
        //     return RocketMQLocalTransactionState.COMMIT;
        // }
        // if (content > 2000) {
        //     log.info("checkLocalTransaction rollback params :{} " + content);
        //     return RocketMQLocalTransactionState.ROLLBACK;
        // }

        log.info("checkLocalTransaction unknown params :{} ");
        return RocketMQLocalTransactionState.UNKNOWN;
    }

}
