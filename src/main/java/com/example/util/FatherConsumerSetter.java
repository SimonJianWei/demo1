package com.example.util;

import org.apache.commons.collections4.MapUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenjianwei
 * @Date: 2020/11/27/10:08
 * @Description:
 */
public class FatherConsumerSetter {
    /**
     * 初始化消费者
     * @param mapParams
     * @return
     */
    public static KafkaConsumer<String, String> SetConsumer(Map<String, Object> mapParams){
        //主题参数
        String topic = MapUtils.getString(mapParams, "topic");
        List<String> list = Arrays.asList(topic.split(","));
        //组ID参数
        String group = MapUtils.getString(mapParams, "group");
        //kafka服务地址
        String KAFKA_SERVER = MapUtils.getString(mapParams, "server");
        Properties props = new Properties();
        //地址和端口号
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        //组ID
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        //自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        //自动提交时间间隔
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //超时配置
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        //设置KEY的类型
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //设置VALUE的类型
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //设置每个分区最大拉取容量10 * 1024 * 1024，默认1 * 1024 * 1024
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 10 * 1024 * 1024);
        //设置每个分区最大拉取个数为1000，默认500
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        //初始化消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        //为消费者设置主题
        consumer.subscribe(list);

        return consumer;
    }
}
