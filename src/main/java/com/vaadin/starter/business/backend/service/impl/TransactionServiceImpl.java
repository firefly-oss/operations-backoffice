package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.Transaction;
import com.vaadin.starter.business.backend.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the TransactionService interface.
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    private static final String[] CURRENCIES = {"USD", "EUR", "GBP", "JPY", "CAD", "AUD"};
    private static final String[] ACCOUNT_PREFIXES = {"ACC-", "IBAN-", "ACCT-"};
    private static final String[] REFERENCES = {"REF-", "INV-", "PAY-", "TRF-"};
    private static final String[] DESCRIPTIONS = {
            "Monthly payment", "Salary transfer", "Utility bill", "Subscription fee",
            "Online purchase", "ATM withdrawal", "Loan payment", "Interest payment",
            "Tax payment", "Insurance premium", "Rent payment", "Dividend payment"
    };

    private final Random random = new Random();
    private final List<Transaction> transactions = new ArrayList<>();

    public TransactionServiceImpl() {
        generateMockTransactions();
    }

    /**
     * Get all transactions.
     *
     * @return List of all transactions
     */
    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Get a transaction by ID.
     *
     * @param id Transaction ID
     * @return Transaction with the given ID, or null if not found
     */
    @Override
    public Transaction getTransactionById(String id) {
        return transactions.stream()
                .filter(transaction -> transaction.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Generate mock transaction data.
     */
    private void generateMockTransactions() {
        // Clear existing transactions
        transactions.clear();

        // Generate 100 random transactions
        for (int i = 0; i < 100; i++) {
            Transaction transaction = new Transaction();
            
            // Set random type
            Transaction.Type[] types = Transaction.Type.values();
            transaction.setType(types[random.nextInt(types.length)].getName());
            
            // Set random status
            Transaction.Status[] statuses = Transaction.Status.values();
            transaction.setStatus(statuses[random.nextInt(statuses.length)].getName());
            
            // Set random amount (between 10 and 10000)
            double amount = 10 + (random.nextDouble() * 9990);
            transaction.setAmount(Math.round(amount * 100.0) / 100.0);
            
            // Set random currency
            transaction.setCurrency(CURRENCIES[random.nextInt(CURRENCIES.length)]);
            
            // Set random source account
            String sourcePrefix = ACCOUNT_PREFIXES[random.nextInt(ACCOUNT_PREFIXES.length)];
            transaction.setSourceAccount(sourcePrefix + random.nextInt(1000000));
            
            // Set random destination account
            String destPrefix = ACCOUNT_PREFIXES[random.nextInt(ACCOUNT_PREFIXES.length)];
            transaction.setDestinationAccount(destPrefix + random.nextInt(1000000));
            
            // Set random timestamp (within the last 30 days)
            LocalDateTime now = LocalDateTime.now();
            int daysBack = random.nextInt(30);
            int hoursBack = random.nextInt(24);
            int minutesBack = random.nextInt(60);
            LocalDateTime timestamp = now.minusDays(daysBack).minusHours(hoursBack).minusMinutes(minutesBack);
            transaction.setTimestamp(timestamp);
            
            // Set random reference
            String refPrefix = REFERENCES[random.nextInt(REFERENCES.length)];
            transaction.setReference(refPrefix + random.nextInt(100000));
            
            // Set random description
            transaction.setDescription(DESCRIPTIONS[random.nextInt(DESCRIPTIONS.length)]);
            
            // Add to list
            transactions.add(transaction);
        }
    }
}