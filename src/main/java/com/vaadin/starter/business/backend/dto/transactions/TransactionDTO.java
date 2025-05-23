package com.vaadin.starter.business.backend.dto.transactions;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for transaction data.
 */
public class TransactionDTO {
    private String id;
    private String type;
    private String status;
    private double amount;
    private String currency;
    private String sourceAccount;
    private String destinationAccount;
    private LocalDateTime timestamp;
    private String reference;
    private String description;

    public TransactionDTO() {
    }

    public TransactionDTO(String id, String type, String status, double amount, String currency,
                        String sourceAccount, String destinationAccount, LocalDateTime timestamp,
                        String reference, String description) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.currency = currency;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.timestamp = timestamp;
        this.reference = reference;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}