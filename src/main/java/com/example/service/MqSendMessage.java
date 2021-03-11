package com.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MqSendMessage
 * @Author Simon
 * @Date 2021/1/6 11:38
 * @Description
 */
@Service
public class MqSendMessage {
    @Autowired
    private RabbitTemplate  rabbitTemplate;

    @Autowired
    private ObjectMapper mapper;
    //topic
    public void sendTopic(Map mess) throws JsonProcessingException {
        rabbitTemplate.convertAndSend("train.topic", "train.topic.test", mapper.writeValueAsString(mess));
    }


    //fanout  delay 队列
    public void sendDelay() throws JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("fanout", "广播交换机");
//        Message message = MessageBuilder.withBody(str.getBytes())
//                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
//                .setContentEncoding("utf-8")
//                .setMessageId(UUID.randomUUID()+"")
//                .build();
        //延迟队列
        rabbitTemplate.convertAndSend("train.fanout","",mapper.writeValueAsString(map),(Message message) ->{
             message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
             message.getMessageProperties().setDelay(30*1000);
             return message;
        } );

    }
}
