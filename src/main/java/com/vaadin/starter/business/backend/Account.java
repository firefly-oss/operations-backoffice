package com.vaadin.starter.business.backend;

import java.time.LocalDate;

/**
 * Account entity representing a bank account.
 */
public class Account {
    private String accountNumber;
    private String accountName;
    private String accountType;
    private String status;
    private String customerId;
    private Double balance;
    private LocalDate openDate;

    public Account(String accountNumber, String accountName, String accountType, String status, String customerId, Double balance, LocalDate openDate) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountType = accountType;
        this.status = status;
        this.customerId = customerId;
        this.balance = balance;
        this.openDate = openDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    /**
     * Enum for account types
     */
    public enum AccountType {
        CHECKING("Checking"),
        SAVINGS("Savings"),
        CREDIT_CARD("Credit Card"),
        LOAN("Loan"),
        INVESTMENT("Investment");

        private final String name;

        AccountType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for account statuses
     */
    public enum Status {
        ACTIVE("Active"),
        INACTIVE("Inactive"),
        BLOCKED("Blocked"),
        CLOSED("Closed");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}