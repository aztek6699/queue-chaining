package com.example.additionservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "addition")
public class AdditionDto {

    @Id
    private Long id;

    private int receiverId;
    private int amount;

    public AdditionDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public AdditionDto(int receiverId, int amount) {
        this.receiverId = receiverId;
        this.amount = amount;
    }

    public AdditionDto(Long id, int receiverId, int amount) {
        this.id = id;
        this.receiverId = receiverId;
        this.amount = amount;
    }
}