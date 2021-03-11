package com.example.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenjianwei
 * @Date: 2020/11/27/10:34
 * @Description:MQ消费者案例
 */
@Component
public class MqDemo {
//    @Autowired
//    @Qualifier("MQ_Connection")
    private Connection mqConnect;
    private Channel channel=null;
    private Consumer consumer=null;
//    @Value("${mq.queueName}")
    private String QUEUE_NAME;

    //@PostConstruct//修饰非静态方法 执行顺序在构造函数之后
    public void doinit() throws IOException {
        this.channel = mqConnect.createChannel();
        channel.basicQos(0,5,false);
        this.consumer = new MqConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false,consumer);
    }

    @PreDestroy//修饰非静态方法 执行顺序在 sevlet destory函数之后 不得有任何参数 不得抛异常
    public void dodestroy(){

    }

}
