package com.vaadin.starter.business.backend.dto.cashmanagement;

/**
 * Represents a currency exchange rate.
 */
public class CurrencyExchangeRateDTO {
    private String currency;
    private Double rate;
    private Double change;

    public CurrencyExchangeRateDTO() {
    }

    public CurrencyExchangeRateDTO(String currency, Double rate, Double change) {
        this.currency = currency;
        this.rate = rate;
        this.change = change;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }
}