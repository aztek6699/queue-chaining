package com.example.subtractionservice.model;

public class ErrorDto {
    private Long id;
    private String errorMessage;
    private TransactionDto invalidTransaction;

    public ErrorDto(Long id, String errorMessage) {
        this.id = id;
        this.errorMessage = errorMessage;
    }

    public ErrorDto(Long id, String errorMessage, TransactionDto invalidTransaction) {
        this.id = id;
        this.errorMessage = errorMessage;
        this.invalidTransaction = invalidTransaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public TransactionDto getInvalidTransaction() {
        return invalidTransaction;
    }

    public void setInvalidTransaction(TransactionDto invalidTransaction) {
        this.invalidTransaction = invalidTransaction;
    }
}
