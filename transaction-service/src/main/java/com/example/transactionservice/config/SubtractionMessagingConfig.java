package com.example.transactionservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubtractionMessagingConfig {
    @Value(value = "${messaging.properties.subtraction.queue}")
    private String queue;

    @Value(value = "${messaging.properties.main.exchange}")
    private String exchange;

    @Value(value = "${messaging.properties.subtraction.routing.key}")
    private String routingKey;

    @Autowired
    private MessageConverter converter;

    @Bean
    public Queue subtractionQueue() {
        return new Queue(queue);
    }

    @Bean
    public DirectExchange subtractionExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding subtractionBinding(Queue subtractionQueue, DirectExchange subtractionExchange) {
        return BindingBuilder.bind(subtractionQueue).to(subtractionExchange).with(routingKey);
    }

    @Bean
    public AmqpTemplate subtractionTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }
}
