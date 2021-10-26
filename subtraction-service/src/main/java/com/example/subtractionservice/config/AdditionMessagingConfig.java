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
public class AdditionMessagingConfig {

        @Value(value = "${messaging.properties.addition.queue}")
        private String queue;

        @Value(value = "${messaging.properties.main.exchange}")
        private String exchange;

        @Value(value = "${messaging.properties.addition.routing.key}")
        private String routingKey;

        @Autowired
        private MessageConverter converter;

        @Bean
        public Queue additionQueue() {
            return new Queue(queue);
        }

        @Bean
        public DirectExchange additionExchange() {
            return new DirectExchange(exchange);
        }

        @Bean
        public Binding additionBinding(Queue additionQueue, DirectExchange additionExchange) {
            return BindingBuilder.bind(additionQueue).to(additionExchange).with(routingKey);
        }

        @Bean
        public AmqpTemplate additionTemplate(ConnectionFactory connectionFactory) {
            final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
            rabbitTemplate.setMessageConverter(converter);
            return rabbitTemplate;
        }
}
