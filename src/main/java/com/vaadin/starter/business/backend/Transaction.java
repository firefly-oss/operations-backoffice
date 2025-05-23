package com.vaadin.starter.business.backend;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a financial transaction in the system.
 */
public class Transaction {
    private String id;
    private String type;
    private String status;
    private Double amount;
    private String currency;
    private String sourceAccount;
    private String destinationAccount;
    private LocalDateTime timestamp;
    private String reference;
    private String description;

    public Transaction() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }

    public Transaction(String id, String type, String status, Double amount, String currency,
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

    /**
     * Enum for transaction types.
     */
    public enum Type {
        PAYMENT("Payment"),
        TRANSFER("Transfer"),
        DEPOSIT("Deposit"),
        WITHDRAWAL("Withdrawal"),
        FEE("Fee"),
        REFUND("Refund"),
        ADJUSTMENT("Adjustment");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for transaction statuses.
     */
    public enum Status {
        PENDING("Pending"),
        PROCESSING("Processing"),
        COMPLETED("Completed"),
        FAILED("Failed"),
        CANCELLED("Cancelled"),
        REJECTED("Rejected");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}