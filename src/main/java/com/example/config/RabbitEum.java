package com.example.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName QueueEum
 * @Author Simon
 * @Date 2021/1/7 18:01
 * @Description
 */
@Configuration
public class RabbitEum {

    //延迟队列实现
    @Bean("delayqueue")
    public Queue delayqueue() {
        Queue queue = QueueBuilder.durable("delay.1").build();
        queue.setActualName("delay.1");
        return queue;
    }

    @Bean("delayExchange")
    public FanoutExchange delayExange() {
        return ExchangeBuilder.fanoutExchange("train.fanout").delayed().build();
    }

    @Bean
    public Binding bindDelayExchange(@Qualifier("delayExchange") FanoutExchange exchange, @Qualifier("delayqueue") Queue queue) {
        return  BindingBuilder.bind(queue).to(exchange);
    }

}
