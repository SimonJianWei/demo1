package com.example.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenjianwei
 * @Date: 2020/11/18/10:23
 * @Description:
 */
@Configuration
public class KafkaProduce {

    @Value("${kafka_server}")
    private String kafka_Server;

    @Bean("KafkaProduce")
    public KafkaProducer<String, String> setProduce(){
        Properties props = new Properties();
        //获取配置文件生产者信息
        props.put("bootstrap.servers", kafka_Server);
        //序列化 key value
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> Producer = new KafkaProducer<>(props);
        return new KafkaProducer<String, String>(props);
    }
}
