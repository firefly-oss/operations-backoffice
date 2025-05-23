package com.vaadin.starter.business.backend;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a cash position in the system.
 */
public class CashPosition {
    private String id;
    private String accountNumber;
    private String accountName;
    private String accountType;
    private Double balance;
    private String currency;
    private LocalDateTime lastUpdated;
    private String status;
    private String bankName;
    private String branchCode;
    private String availableForTrading;

    public CashPosition() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }

    public CashPosition(String id, String accountNumber, String accountName, String accountType,
                      Double balance, String currency, LocalDateTime lastUpdated,
                      String status, String bankName, String branchCode, String availableForTrading) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
        this.currency = currency;
        this.lastUpdated = lastUpdated;
        this.status = status;
        this.bankName = bankName;
        this.branchCode = branchCode;
        this.availableForTrading = availableForTrading;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAvailableForTrading() {
        return availableForTrading;
    }

    public void setAvailableForTrading(String availableForTrading) {
        this.availableForTrading = availableForTrading;
    }

    /**
     * Enum for account types.
     */
    public enum AccountType {
        CHECKING("Checking"),
        SAVINGS("Savings"),
        INVESTMENT("Investment"),
        CORPORATE("Corporate"),
        TREASURY("Treasury"),
        NOSTRO("Nostro"),
        VOSTRO("Vostro");

        private final String name;

        AccountType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for account statuses.
     */
    public enum Status {
        ACTIVE("Active"),
        INACTIVE("Inactive"),
        BLOCKED("Blocked"),
        PENDING("Pending"),
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