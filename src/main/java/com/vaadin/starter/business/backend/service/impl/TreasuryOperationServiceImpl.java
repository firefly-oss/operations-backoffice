package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.TreasuryOperation;
import com.vaadin.starter.business.backend.service.TreasuryOperationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the TreasuryOperationService interface.
 */
@Service
public class TreasuryOperationServiceImpl implements TreasuryOperationService {
    private static final String[] CURRENCIES = {"USD", "EUR", "GBP", "JPY", "CAD", "AUD"};
    private static final String[] COUNTERPARTIES = {
            "JP Morgan", "Goldman Sachs", "Bank of America", "Citibank", "HSBC",
            "Deutsche Bank", "BNP Paribas", "Credit Suisse", "UBS", "Barclays"
    };
    private static final String[] TRADERS = {
            "John Smith", "Emma Johnson", "Michael Brown", "Sarah Davis", "Robert Wilson",
            "Jennifer Lee", "David Miller", "Lisa Anderson", "James Taylor", "Patricia Moore"
    };
    private static final String[] INSTRUMENTS = {
            "Treasury Bill", "Commercial Paper", "Certificate of Deposit", "Bond", "Note",
            "Repo Agreement", "Reverse Repo", "Interest Rate Swap", "Forward Rate Agreement"
    };
    private static final String[] REFERENCES = {"TRO-", "OPS-", "TRS-", "FIN-"};
    private static final String[] DESCRIPTIONS = {
            "Short-term funding operation", "Liquidity management", "Interest rate risk hedging",
            "Balance sheet optimization", "Regulatory compliance", "Capital management",
            "Yield enhancement", "Asset-liability matching", "Funding diversification"
    };

    private final Random random = new Random();
    private final List<TreasuryOperation> treasuryOperations = new ArrayList<>();

    public TreasuryOperationServiceImpl() {
        generateMockTreasuryOperations();
    }

    /**
     * Get all treasury operations.
     *
     * @return List of all treasury operations
     */
    @Override
    public List<TreasuryOperation> getTreasuryOperations() {
        return treasuryOperations;
    }

    /**
     * Get a treasury operation by ID.
     *
     * @param id Treasury operation ID
     * @return Treasury operation with the given ID, or null if not found
     */
    @Override
    public TreasuryOperation getTreasuryOperationById(String id) {
        return treasuryOperations.stream()
                .filter(treasuryOperation -> treasuryOperation.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Generate mock treasury operation data.
     */
    private void generateMockTreasuryOperations() {
        // Clear existing treasury operations
        treasuryOperations.clear();

        // Generate 50 random treasury operations
        for (int i = 0; i < 50; i++) {
            TreasuryOperation treasuryOperation = new TreasuryOperation();
            
            // Set random operation type
            TreasuryOperation.OperationType[] types = TreasuryOperation.OperationType.values();
            treasuryOperation.setOperationType(types[random.nextInt(types.length)].getName());
            
            // Set random status
            TreasuryOperation.Status[] statuses = TreasuryOperation.Status.values();
            treasuryOperation.setStatus(statuses[random.nextInt(statuses.length)].getName());
            
            // Set random amount (between 100000 and 50000000)
            double amount = 100000 + (random.nextDouble() * 49900000);
            treasuryOperation.setAmount(Math.round(amount * 100.0) / 100.0);
            
            // Set random currency
            treasuryOperation.setCurrency(CURRENCIES[random.nextInt(CURRENCIES.length)]);
            
            // Set random counterparty
            treasuryOperation.setCounterparty(COUNTERPARTIES[random.nextInt(COUNTERPARTIES.length)]);
            
            // Set random execution date (within the last 30 days)
            LocalDateTime now = LocalDateTime.now();
            int daysBack = random.nextInt(30);
            int hoursBack = random.nextInt(24);
            int minutesBack = random.nextInt(60);
            LocalDateTime executionDate = now.minusDays(daysBack).minusHours(hoursBack).minusMinutes(minutesBack);
            treasuryOperation.setExecutionDate(executionDate);
            
            // Set random settlement date (between execution date and 7 days after)
            int daysToSettle = random.nextInt(7) + 1;
            LocalDateTime settlementDate = executionDate.plusDays(daysToSettle);
            treasuryOperation.setSettlementDate(settlementDate);
            
            // Set random trader
            treasuryOperation.setTrader(TRADERS[random.nextInt(TRADERS.length)]);
            
            // Set random instrument
            treasuryOperation.setInstrument(INSTRUMENTS[random.nextInt(INSTRUMENTS.length)]);
            
            // Set random rate (between 0.1 and 10.0)
            double rate = 0.1 + (random.nextDouble() * 9.9);
            treasuryOperation.setRate(Math.round(rate * 1000.0) / 1000.0);
            
            // Set random reference
            String refPrefix = REFERENCES[random.nextInt(REFERENCES.length)];
            treasuryOperation.setReference(refPrefix + random.nextInt(100000));
            
            // Set random description
            treasuryOperation.setDescription(DESCRIPTIONS[random.nextInt(DESCRIPTIONS.length)]);
            
            // Add to list
            treasuryOperations.add(treasuryOperation);
        }
    }
}