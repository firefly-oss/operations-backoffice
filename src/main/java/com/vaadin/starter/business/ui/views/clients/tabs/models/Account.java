package com.vaadin.starter.business.ui.views.clients.tabs.models;

import java.time.LocalDate;

/**
 * Data model for a client account.
 */
public class Account {
    private String accountNumber;
    private String accountType;
    private String balance;
    private String availableBalance;
    private String currency;
    private String status;
    private LocalDate openDate;

    public Account(String accountNumber, String accountType, String balance, String availableBalance, 
                  String currency, String status, LocalDate openDate) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.availableBalance = availableBalance;
        this.currency = currency;
        this.status = status;
        this.openDate = openDate;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getAccountType() { return accountType; }
    public String getBalance() { return balance; }
    public String getAvailableBalance() { return availableBalance; }
    public String getCurrency() { return currency; }
    public String getStatus() { return status; }
    public LocalDate getOpenDate() { return openDate; }
}