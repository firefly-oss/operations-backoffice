package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.CashPosition;
import com.vaadin.starter.business.backend.service.CashPositionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the CashPositionService interface.
 */
@Service
public class CashPositionServiceImpl implements CashPositionService {
    private static final String[] CURRENCIES = {"USD", "EUR", "GBP", "JPY", "CAD", "AUD"};
    private static final String[] ACCOUNT_PREFIXES = {"CASH-", "ACCT-", "BANK-"};
    private static final String[] BANK_NAMES = {
            "Global Bank", "First National", "City Trust", "Metro Financial", "Continental Bank",
            "Pacific Union", "Atlantic Trust", "Northern Securities", "Southern Bank", "Eastern Financial"
    };
    private static final String[] BRANCH_CODES = {"BR001", "BR002", "BR003", "BR004", "BR005", "BR006", "BR007"};
    private static final String[] AVAILABLE_OPTIONS = {"Yes", "No", "Partial"};

    private final Random random = new Random();
    private final List<CashPosition> cashPositions = new ArrayList<>();

    public CashPositionServiceImpl() {
        generateMockCashPositions();
    }

    /**
     * Get all cash positions.
     *
     * @return List of all cash positions
     */
    @Override
    public List<CashPosition> getCashPositions() {
        return cashPositions;
    }

    /**
     * Get a cash position by ID.
     *
     * @param id Cash position ID
     * @return Cash position with the given ID, or null if not found
     */
    @Override
    public CashPosition getCashPositionById(String id) {
        return cashPositions.stream()
                .filter(cashPosition -> cashPosition.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Generate mock cash position data.
     */
    private void generateMockCashPositions() {
        // Clear existing cash positions
        cashPositions.clear();

        // Generate 50 random cash positions
        for (int i = 0; i < 50; i++) {
            CashPosition cashPosition = new CashPosition();
            
            // Set random account number
            String accountPrefix = ACCOUNT_PREFIXES[random.nextInt(ACCOUNT_PREFIXES.length)];
            cashPosition.setAccountNumber(accountPrefix + random.nextInt(1000000));
            
            // Set random account name
            cashPosition.setAccountName("Account " + (i + 1));
            
            // Set random account type
            CashPosition.AccountType[] types = CashPosition.AccountType.values();
            cashPosition.setAccountType(types[random.nextInt(types.length)].getName());
            
            // Set random balance (between 1000 and 10000000)
            double balance = 1000 + (random.nextDouble() * 9999000);
            cashPosition.setBalance(Math.round(balance * 100.0) / 100.0);
            
            // Set random currency
            cashPosition.setCurrency(CURRENCIES[random.nextInt(CURRENCIES.length)]);
            
            // Set random last updated timestamp (within the last 7 days)
            LocalDateTime now = LocalDateTime.now();
            int daysBack = random.nextInt(7);
            int hoursBack = random.nextInt(24);
            int minutesBack = random.nextInt(60);
            LocalDateTime lastUpdated = now.minusDays(daysBack).minusHours(hoursBack).minusMinutes(minutesBack);
            cashPosition.setLastUpdated(lastUpdated);
            
            // Set random status
            CashPosition.Status[] statuses = CashPosition.Status.values();
            cashPosition.setStatus(statuses[random.nextInt(statuses.length)].getName());
            
            // Set random bank name
            cashPosition.setBankName(BANK_NAMES[random.nextInt(BANK_NAMES.length)]);
            
            // Set random branch code
            cashPosition.setBranchCode(BRANCH_CODES[random.nextInt(BRANCH_CODES.length)]);
            
            // Set random available for trading
            cashPosition.setAvailableForTrading(AVAILABLE_OPTIONS[random.nextInt(AVAILABLE_OPTIONS.length)]);
            
            // Add to list
            cashPositions.add(cashPosition);
        }
    }
}