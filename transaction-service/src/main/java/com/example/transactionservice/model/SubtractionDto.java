package com.example.transactionservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subtraction")
public class SubtractionDto {

    @Id
    @GeneratedValue
    private Long id;

    private int status;
    private int senderId;
    private int amount;

    public SubtractionDto() {
    }

    public SubtractionDto(int status, int senderId, int amount) {
        this.status = status;
        this.senderId = senderId;
        this.amount = amount;
    }

    public SubtractionDto(Long id, int status, int senderId, int amount) {
        this.id = id;
        this.status = status;
        this.senderId = senderId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
