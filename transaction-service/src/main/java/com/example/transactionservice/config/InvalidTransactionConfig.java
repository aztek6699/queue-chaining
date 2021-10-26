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
public class InvalidTransactionConfig {

    @Value(value = "${messaging.properties.invalid.transaction.queue}")
    private String queue;

    @Value(value = "${messaging.properties.invalid.exchange}")
    private String exchange;

    @Value(value = "${messaging.properties.invalid.transaction.routing.key}")
    private String routingKey;

    @Autowired
    private MessageConverter converter;

    @Bean
    public Queue invalidQueue() {
        return new Queue(queue);
    }

    @Bean
    public DirectExchange invalidExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding invalidBinding(Queue invalidQueue, DirectExchange invalidExchange) {
        return BindingBuilder.bind(invalidQueue).to(invalidExchange).with(routingKey);
    }

    @Bean
    public AmqpTemplate invalidTransactionTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }
}
