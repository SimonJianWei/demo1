package com.example.service;

import com.rabbitmq.client.*;

import java.io.IOException;



/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenjianwei
 * @Date: 2020/11/27/10:45
 * @Description:
 */

public class MqConsumer  extends DefaultConsumer {

//    @Autowired
//    @Qualifier("MQ_Connection")
    private Connection mqConnect;
    private Channel channel=null;

    public MqConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.err.println("-----------consume message----------");
        System.err.println("consumerTag: " + consumerTag);
        System.err.println("envelope: " + envelope);
        System.err.println("properties: " + properties);
        System.err.println("body: " + new String(body));
    }
}
