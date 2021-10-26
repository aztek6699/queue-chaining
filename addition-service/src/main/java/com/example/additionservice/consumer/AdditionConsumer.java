package com.example.additionservice.consumer;

import com.example.additionservice.model.AdditionDto;
import com.example.additionservice.model.ErrorDto;
import com.example.additionservice.model.TransactionDto;
import com.example.additionservice.repo.AdditionRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdditionConsumer {

    @Value(value = "${messaging.properties.main.exchange}")
    private String mainExchange;

    @Value(value = "${messaging.properties.error.exchange}")
    private String errorExchange;

    @Value(value = "${messaging.properties.subtraction.routing.key}")
    private String errorSubtractionrk;

    @Autowired
    private AdditionRepo repo;

    @Autowired
    private RabbitTemplate errorSubtractionTemplate;

    @RabbitListener(queues = "${messaging.properties.addition.queue}")
    private void consumeAdditionQueue(TransactionDto transactionDto) {
        try {
            AdditionDto additionDto = new AdditionDto(
                    transactionDto.getId(),
                    transactionDto.getReceiverId(),
                    transactionDto.getAmount()
            );
            repo.save(additionDto);
            System.out.println("AdditionConsumer: consumeAdditionQueue: inserted in db");
        } catch (Exception e) {
            errorSubtractionTemplate.convertAndSend(
                    errorExchange,
                    errorSubtractionrk,
                    new ErrorDto(transactionDto.getId(), e.getMessage(), transactionDto)
            );
        }
    }
}
