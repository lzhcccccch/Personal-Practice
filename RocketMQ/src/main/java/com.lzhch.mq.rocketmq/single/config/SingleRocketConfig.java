package com.lzhch.mq.rocketmq.single.config;

/**
 * @packageName： com.lzhch.mq.rocketmq.single.config
 * @className: SingleRocketConfig
 * @description:  单体 RocketMq 配置类
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2022/6/17 15:59
 */

public class SingleRocketConfig {

    /**
     *  nameserver 地址
     */
    public static final String NAME_SERVER_ADDRESS = "150.158.170.34:9876";

    /**
     *  broker 名称
     */
    public static final String DEFAULT_INSTANCE_NAME = "broker-zachary";

    /**
     * 默认生产者群组
     */
    public static final String DEFAULT_PROVIDER_GROUP = "GROUP_ZACHARY_SINGLE_TEST";

    /**
     * 默认topic
     */
    public static final String DEFAULT_TOPIC = "TOPIC_ZACHARY_SINGLE_TEST";

    /**
     *  默认 tags
     *  tag 是基于 topic 下面的二级分类, 可以更加精确的查询
     */
    public static final String DEFAULT_TAGS = "TAGS_SINGLE";

    /**
     *  默认消费者群组
     */
    public static final String DEFAULT_CONSUME_GROUP = "GROUP_ZACHARY_SINGLE_TEST";

}
