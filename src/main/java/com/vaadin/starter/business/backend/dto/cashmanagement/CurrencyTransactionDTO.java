package com.vaadin.starter.business.backend.dto.cashmanagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a currency transaction.
 */
public class CurrencyTransactionDTO {
    private String id;
    private String fromCurrency;
    private String toCurrency;
    private double amount;
    private double rate;
    private double convertedAmount;
    private String status;
    private String type;
    private LocalDate date;

    public CurrencyTransactionDTO() {
    }

    public CurrencyTransactionDTO(String id, String fromCurrency, String toCurrency,
                                  double amount, double rate, double convertedAmount,
                                  String status, String type, LocalDate date) {
        this.id = id;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.rate = rate;
        this.convertedAmount = convertedAmount;
        this.status = status;
        this.type = type;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getFormattedDate() {
        return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
    }
}