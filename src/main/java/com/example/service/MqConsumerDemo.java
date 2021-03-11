package com.example.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName MqConsumerDemo
 * @Author Simon
 * @Date 2021/1/6 10:59
 * @Description
 */
@Component
public class MqConsumerDemo {

    @RabbitListener(queues = "dead.1")
    public  void deadConsumer(/*@Payload String message, @Header(required =false)  String head,*/Message message, Channel channel){
        System.out.println("死信队列");
        System.out.println("消息是"+message);
//        System.out.println("消息头"+head);
    }

    @RabbitListener(queues ="topic.1")
    public  void  topicConsumer(/*@Payload String message, @Header (required =false) String head*/Message message, Channel channel){
        System.out.println("topic队列");
        System.out.println("消息是"+message.getBody().toString());
        System.out.println(message.getMessageProperties().getHeaders());
//        System.out.println("消息头"+head);
        try {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "delay.1")
    public  void fanoutConsumer(/*@Payload String message, @Header(required =false)  String head,*/Message message, Channel channel) throws IOException {
        System.out.println("广播队列");
        System.out.println("消息是"+message);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        System.out.println("消息头"+head);
    }
}
