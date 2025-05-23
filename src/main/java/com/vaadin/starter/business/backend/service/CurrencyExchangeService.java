package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.cashmanagement.CurrencyExchangeRateDTO;
import com.vaadin.starter.business.backend.dto.cashmanagement.CurrencyTransactionDTO;

import java.util.List;
import java.util.Map;

/**
 * Service interface for currency exchange operations.
 */
public interface CurrencyExchangeService {

    /**
     * Get all exchange rates.
     *
     * @return Map of currency codes to exchange rates
     */
    Map<String, Double> getExchangeRates();

    /**
     * Get all exchange rates with change information.
     *
     * @return List of currency exchange rates
     */
    List<CurrencyExchangeRateDTO> getExchangeRatesWithChange();

    /**
     * Get recent currency transactions.
     *
     * @return List of recent currency transactions
     */
    List<CurrencyTransactionDTO> getRecentTransactions();

    /**
     * Convert an amount from one currency to another.
     *
     * @param amount Amount to convert
     * @param fromCurrency Source currency code
     * @param toCurrency Target currency code
     * @return Converted amount
     */
    double convertCurrency(double amount, String fromCurrency, String toCurrency);

    /**
     * Get exchange rate between two currencies.
     *
     * @param fromCurrency Source currency code
     * @param toCurrency Target currency code
     * @return Exchange rate
     */
    double getExchangeRate(String fromCurrency, String toCurrency);

    /**
     * Get historical exchange rate data for chart display.
     *
     * @param days Number of days of historical data
     * @param currencies List of currencies to include
     * @return Map of currency codes to lists of historical rates
     */
    Map<String, List<Double>> getHistoricalRates(int days, List<String> currencies);
}