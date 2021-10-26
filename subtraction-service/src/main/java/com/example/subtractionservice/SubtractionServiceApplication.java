package com.example.subtractionservice;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SubtractionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubtractionServiceApplication.class, args);
	}

	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}
}
