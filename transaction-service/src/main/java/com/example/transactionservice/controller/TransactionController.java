package com.example.transactionservice.controller;

import com.example.transactionservice.model.TransactionDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Value(value = "${messaging.properties.main.exchange}")
    private String exchange;

    @Value(value = "${messaging.properties.transaction.routing.key}")
    private String routingKey;

    @Autowired
    private RabbitTemplate transTemplate;

    @PostMapping("/transaction")
    public String insertTransaction(@RequestBody TransactionDto transaction) {

        if (transaction != null) {
            transTemplate.convertAndSend(exchange, routingKey, transaction);
            return "publishing to queue";
        } else {
            return "transaction invalid";
        }
    }
}
