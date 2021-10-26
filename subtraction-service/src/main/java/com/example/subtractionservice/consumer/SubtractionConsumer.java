package com.example.subtractionservice.consumer;

import com.example.subtractionservice.model.ErrorDto;
import com.example.subtractionservice.model.SubtractionDto;
import com.example.subtractionservice.model.TransactionDto;
import com.example.subtractionservice.repo.SubtractionRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SubtractionConsumer {
    @Value(value = "${messaging.properties.main.exchange}")
    private String mainExchange;
    @Value(value = "${messaging.properties.addition.routing.key}")
    private String additionrk;
    @Value(value = "${messaging.properties.error.exchange}")
    private String errorExchange;
    @Value(value = "${messaging.properties.error.transaction.routing.key}")
    private String errorTransactionrk;

    @Autowired
    private SubtractionRepo repo;

    @Autowired
    private RabbitTemplate errorTransTemplate;

    @Autowired
    private RabbitTemplate additionTemplate;

    @RabbitListener(queues = "${messaging.properties.subtraction.queue}")
    private void consumeSubtractionQueue(TransactionDto transactionDto) {
        try {
            SubtractionDto subtractionDto = new SubtractionDto(
                    transactionDto.getId(),
                    transactionDto.getSenderId(),
                    transactionDto.getAmount());

            repo.save(subtractionDto);
            System.out.println("SubtractionConsumer: consumeSubtractionQueue: inserted into db");

            additionTemplate.convertAndSend(
                    mainExchange,
                    additionrk,
                    transactionDto
            );
            System.out.println("SubtractionConsumer: consumeSubtractionQueue: published to addition queue");

        } catch (Exception e) {
            errorTransTemplate.convertAndSend(
                    errorExchange,
                    errorTransactionrk,
                    new ErrorDto(transactionDto.getId(), e.getMessage(), transactionDto)
            );
            System.out.println("SubtractionConsumer: consumeSubtractionQueue: published to error transaction queue");
        }
    }

    @RabbitListener(queues = "${messaging.properties.error.subtraction.queue}")
    private void consumeErrorSubtractionQueue(ErrorDto errorDto) {
        try {
            repo.deleteById(errorDto.getId());
            errorTransTemplate.convertAndSend(
                    errorExchange,
                    errorTransactionrk,
                    errorDto
            );
            System.out.println("Subtractiononsumer: consumeErrorSubtractionQueue: deleted from db and published to error transaction queue");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
