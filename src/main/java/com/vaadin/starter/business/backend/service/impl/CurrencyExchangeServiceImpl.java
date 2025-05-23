package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.cashmanagement.CurrencyExchangeRateDTO;
import com.vaadin.starter.business.backend.dto.cashmanagement.CurrencyTransactionDTO;
import com.vaadin.starter.business.backend.service.CurrencyExchangeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * Implementation of the CurrencyExchangeService interface.
 */
@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
    private static final String[] CURRENCIES = {"USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF", "CNY", "HKD", "SGD"};
    private static final String[] TRANSACTION_STATUSES = {"Completed", "Pending", "Processing", "Failed"};
    private static final String[] TRANSACTION_TYPES = {"Spot", "Forward", "Swap"};

    private final Random random = new Random();
    private final Map<String, Double> exchangeRates = new HashMap<>();
    private final List<CurrencyTransactionDTO> recentTransactions = new ArrayList<>();

    public CurrencyExchangeServiceImpl() {
        initializeExchangeRates();
        initializeRecentTransactions();
    }

    /**
     * Initialize exchange rates with mock data.
     */
    private void initializeExchangeRates() {
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.92);
        exchangeRates.put("GBP", 0.79);
        exchangeRates.put("JPY", 149.8);
        exchangeRates.put("CAD", 1.36);
        exchangeRates.put("AUD", 1.52);
        exchangeRates.put("CHF", 0.89);
        exchangeRates.put("CNY", 7.24);
        exchangeRates.put("HKD", 7.82);
        exchangeRates.put("SGD", 1.34);
    }

    /**
     * Initialize recent transactions with mock data.
     */
    private void initializeRecentTransactions() {
        LocalDate today = LocalDate.now();
        
        for (int i = 0; i < 20; i++) {
            String fromCurrency = CURRENCIES[random.nextInt(CURRENCIES.length)];
            String toCurrency;
            do {
                toCurrency = CURRENCIES[random.nextInt(CURRENCIES.length)];
            } while (toCurrency.equals(fromCurrency));
            
            double amount = 10000 + random.nextDouble() * 990000;
            amount = Math.round(amount * 100.0) / 100.0;
            
            double rate = 0.5 + random.nextDouble() * 1.5;
            rate = Math.round(rate * 10000.0) / 10000.0;
            
            double convertedAmount = amount * rate;
            convertedAmount = Math.round(convertedAmount * 100.0) / 100.0;
            
            String status = TRANSACTION_STATUSES[random.nextInt(TRANSACTION_STATUSES.length)];
            String type = TRANSACTION_TYPES[random.nextInt(TRANSACTION_TYPES.length)];
            
            LocalDate date = today.minusDays(random.nextInt(30));
            
            recentTransactions.add(new CurrencyTransactionDTO(
                "TX-" + (100000 + i),
                fromCurrency,
                toCurrency,
                amount,
                rate,
                convertedAmount,
                status,
                type,
                date
            ));
        }
    }

    @Override
    public Map<String, Double> getExchangeRates() {
        return exchangeRates;
    }

    @Override
    public List<CurrencyExchangeRateDTO> getExchangeRatesWithChange() {
        List<CurrencyExchangeRateDTO> rates = new ArrayList<>();
        
        for (Map.Entry<String, Double> entry : exchangeRates.entrySet()) {
            double change = (random.nextDouble() * 2 - 1) * 0.01; // -1% to +1%
            rates.add(new CurrencyExchangeRateDTO(entry.getKey(), entry.getValue(), change));
        }
        
        return rates;
    }

    @Override
    public List<CurrencyTransactionDTO> getRecentTransactions() {
        return recentTransactions;
    }

    @Override
    public double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        double fromRate = exchangeRates.get(fromCurrency);
        double toRate = exchangeRates.get(toCurrency);
        double rate = toRate / fromRate;
        
        return amount * rate;
    }

    @Override
    public double getExchangeRate(String fromCurrency, String toCurrency) {
        double fromRate = exchangeRates.get(fromCurrency);
        double toRate = exchangeRates.get(toCurrency);
        
        return toRate / fromRate;
    }

    @Override
    public Map<String, List<Double>> getHistoricalRates(int days, List<String> currencies) {
        Map<String, List<Double>> historicalRates = new HashMap<>();
        
        for (String currency : currencies) {
            List<Double> rates = new ArrayList<>();
            double baseRate = exchangeRates.get(currency);
            
            for (int i = 0; i < days; i++) {
                double change = 1 + (random.nextDouble() * 0.04 - 0.02); // -2% to +2%
                baseRate *= change;
                rates.add(Math.round(baseRate * 10000) / 10000.0);
            }
            
            historicalRates.put(currency, rates);
        }
        
        return historicalRates;
    }
}