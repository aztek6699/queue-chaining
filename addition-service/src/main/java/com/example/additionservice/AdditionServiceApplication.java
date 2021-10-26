package com.example.additionservice;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdditionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdditionServiceApplication.class, args);
	}

	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}
}
