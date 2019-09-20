package com.zhaojm.common.config.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE = "direct_queue";

    @Bean
    public Queue directQueue(){
        return new Queue(QUEUE, true);
    }
}
