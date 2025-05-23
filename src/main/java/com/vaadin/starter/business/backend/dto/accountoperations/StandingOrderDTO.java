package com.vaadin.starter.business.backend.dto.accountoperations;

import java.time.LocalDate;

/**
 * Data Transfer Object for Standing Order information.
 */
public class StandingOrderDTO {
    private String orderId;
    private String accountNumber;
    private String beneficiary;
    private String beneficiaryAccount;
    private Double amount;
    private String frequency;
    private LocalDate nextExecutionDate;
    private String status;

    public StandingOrderDTO(String orderId, String accountNumber, String beneficiary, String beneficiaryAccount,
                          Double amount, String frequency, LocalDate nextExecutionDate, String status) {
        this.orderId = orderId;
        this.accountNumber = accountNumber;
        this.beneficiary = beneficiary;
        this.beneficiaryAccount = beneficiaryAccount;
        this.amount = amount;
        this.frequency = frequency;
        this.nextExecutionDate = nextExecutionDate;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
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

    public LocalDate getNextExecutionDate() {
        return nextExecutionDate;
    }

    public void setNextExecutionDate(LocalDate nextExecutionDate) {
        this.nextExecutionDate = nextExecutionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Enum for standing order frequencies
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
     * Enum for standing order statuses
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