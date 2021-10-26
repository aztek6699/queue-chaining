package com.example.subtractionservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorTransactionMessagingConfig {
    @Value(value = "${messaging.properties.error.transaction.queue}")
    private String queue;

    @Value(value = "${messaging.properties.error.exchange}")
    private String exchange;

    @Value(value = "${messaging.properties.error.transaction.routing.key}")
    private String routingKey;

    @Autowired
    private MessageConverter converter;

    @Bean
    public Queue errorTransQueue() {
        return new Queue(queue);
    }

    @Bean
    public DirectExchange errorTransExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding errorTransBinding(Queue errorTransQueue, DirectExchange errorTransExchange) {
        return BindingBuilder.bind(errorTransQueue).to(errorTransExchange).with(routingKey);
    }

    @Bean
    public AmqpTemplate errorTransTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }
}
