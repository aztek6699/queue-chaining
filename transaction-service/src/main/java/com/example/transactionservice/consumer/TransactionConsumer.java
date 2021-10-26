package com.example.transactionservice.consumer;

import com.example.transactionservice.model.ErrorDto;
import com.example.transactionservice.model.TransactionDto;
import com.example.transactionservice.repo.TransactionRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {

    @Value(value = "${messaging.properties.main.exchange}")
    private String mainExchange;
    @Value(value = "${messaging.properties.subtraction.routing.key}")
    private String subtractionrk;
    @Value(value = "${messaging.properties.invalid.transaction.queue}")
    private String invalidExchange;
    @Value(value = "${messaging.properties.invalid.transaction.routing.key}")
    private String invalidrk;

    @Autowired
    private TransactionRepo repo;

    @Autowired
    private RabbitTemplate transTemplate;

    @Autowired
    private RabbitTemplate subtractionTemplate;

    @Autowired
    private RabbitTemplate invalidTransactionTemplate;

    @RabbitListener(queues = "${messaging.properties.transaction.queue}")
    private void consumeTransactionQueue(TransactionDto transaction) {
        try {
            transaction.setId(System.currentTimeMillis()); // set id as current time
            repo.save(transaction);
            System.out.println("TransactionConsumer:consumeTransactionQueue: inserted into db");
            subtractionTemplate.convertAndSend(
                    mainExchange,
                    subtractionrk,
                    transaction);
            System.out.println("TransactionConsumer:consumeTransactionQueue: published to subtraction queue");
        } catch (Exception e) {
            invalidTransactionTemplate.convertAndSend(
                    invalidExchange,
                    invalidrk,
                    new ErrorDto(0L, e.getMessage(), transaction));
            System.out.println(e.getMessage());
        }
    }

    @RabbitListener(queues = "${messaging.properties.error.transaction.queue}")
    private void consumeErrorTransactionQueue(ErrorDto errorDto) {
        try {
            repo.deleteById(errorDto.getId());
            System.out.println("TransactionConsumer: consumeErrorTransactionQueue: deleted from db");
            invalidTransactionTemplate.convertAndSend(
                    invalidExchange,
                    invalidrk,
                    errorDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
