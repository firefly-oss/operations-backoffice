package com.vaadin.starter.business.backend.dto.accountoperations;

import java.time.LocalDate;

/**
 * Data Transfer Object for Direct Debit information.
 */
public class DirectDebitDTO {
    private String mandateId;
    private String accountNumber;
    private String creditor;
    private String creditorId;
    private Double amount;
    private String frequency;
    private LocalDate nextCollectionDate;
    private String status;

    public DirectDebitDTO(String mandateId, String accountNumber, String creditor, String creditorId,
                        Double amount, String frequency, LocalDate nextCollectionDate, String status) {
        this.mandateId = mandateId;
        this.accountNumber = accountNumber;
        this.creditor = creditor;
        this.creditorId = creditorId;
        this.amount = amount;
        this.frequency = frequency;
        this.nextCollectionDate = nextCollectionDate;
        this.status = status;
    }

    public String getMandateId() {
        return mandateId;
    }

    public void setMandateId(String mandateId) {
        this.mandateId = mandateId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public String getCreditorId() {
        return creditorId;
    }

    public void setCreditorId(String creditorId) {
        this.creditorId = creditorId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalDate getNextCollectionDate() {
        return nextCollectionDate;
    }

    public void setNextCollectionDate(LocalDate nextCollectionDate) {
        this.nextCollectionDate = nextCollectionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Enum for direct debit frequencies
     */
    public enum Frequency {
        DAILY("Daily"),
        WEEKLY("Weekly"),
        MONTHLY("Monthly"),
        QUARTERLY("Quarterly"),
        ANNUALLY("Annually");

        private final String name;

        Frequency(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for direct debit statuses
     */
    public enum Status {
        ACTIVE("Active"),
        SUSPENDED("Suspended"),
        CANCELLED("Cancelled");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}