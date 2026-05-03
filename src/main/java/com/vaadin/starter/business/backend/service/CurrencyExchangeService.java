/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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