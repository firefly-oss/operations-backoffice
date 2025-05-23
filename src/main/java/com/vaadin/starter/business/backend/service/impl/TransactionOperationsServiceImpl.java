package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.transactions.BatchJobDTO;
import com.vaadin.starter.business.backend.dto.transactions.PaymentMethodsDTO;
import com.vaadin.starter.business.backend.dto.transactions.PaymentStatusSummaryDTO;
import com.vaadin.starter.business.backend.dto.transactions.PaymentVolumeDTO;
import com.vaadin.starter.business.backend.dto.transactions.TransactionDTO;
import com.vaadin.starter.business.backend.dto.transactions.TransactionDetailsDTO;
import com.vaadin.starter.business.backend.dto.transactions.TransactionReconciliationDTO;
import com.vaadin.starter.business.backend.dto.transactions.TransactionSearchCriteriaDTO;
import com.vaadin.starter.business.backend.service.TransactionOperationsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Implementation of the TransactionOperationsService interface.
 * This service provides mocked data for various transaction-related views.
 */
@Service
public class TransactionOperationsServiceImpl implements TransactionOperationsService {

    private final Random random = new Random();

    /**
     * Get batch jobs for batch operations view.
     *
     * @return List of batch jobs
     */
    @Override
    public List<BatchJobDTO> getBatchJobs() {
        List<BatchJobDTO> batchJobs = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        batchJobs.add(new BatchJobDTO(
                "B1001",
                "End of Day Processing",
                now.minusHours(2).format(formatter),
                now.minusHours(1).format(formatter),
                "58:24",
                "12,450",
                "Completed"
        ));
        
        batchJobs.add(new BatchJobDTO(
                "B1002",
                "Payment Batch",
                now.minusHours(5).format(formatter),
                now.minusHours(4).minusMinutes(48).format(formatter),
                "12:15",
                "3,245",
                "Completed"
        ));
        
        batchJobs.add(new BatchJobDTO(
                "B1003",
                "Statement Generation",
                now.minusHours(8).format(formatter),
                now.minusHours(7).minusMinutes(42).format(formatter),
                "18:32",
                "5,678",
                "Completed"
        ));
        
        batchJobs.add(new BatchJobDTO(
                "B1004",
                "Interest Calculation",
                now.minusHours(10).format(formatter),
                now.minusHours(9).minusMinutes(35).format(formatter),
                "25:12",
                "8,912",
                "Completed"
        ));
        
        batchJobs.add(new BatchJobDTO(
                "B1005",
                "Fee Processing",
                now.minusHours(12).format(formatter),
                now.minusHours(11).minusMinutes(52).format(formatter),
                "8:05",
                "2,345",
                "Completed"
        ));
        
        return batchJobs;
    }

    /**
     * Get payment status summary for payment processing view.
     *
     * @return Payment status summary
     */
    @Override
    public PaymentStatusSummaryDTO getPaymentStatusSummary() {
        List<PaymentStatusSummaryDTO.PaymentStatusDTO> statuses = new ArrayList<>();
        
        statuses.add(new PaymentStatusSummaryDTO.PaymentStatusDTO("Completed", 1245, "var(--lumo-success-color)"));
        statuses.add(new PaymentStatusSummaryDTO.PaymentStatusDTO("Processing", 356, "var(--lumo-primary-color)"));
        statuses.add(new PaymentStatusSummaryDTO.PaymentStatusDTO("Pending", 189, "var(--lumo-contrast-60pct)"));
        statuses.add(new PaymentStatusSummaryDTO.PaymentStatusDTO("Failed", 45, "var(--lumo-error-color)"));
        
        return new PaymentStatusSummaryDTO(statuses);
    }

    /**
     * Get payment volume data for payment processing view.
     *
     * @return Payment volume data
     */
    @Override
    public PaymentVolumeDTO getPaymentVolumeData() {
        List<String> categories = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
        
        List<PaymentVolumeDTO.SeriesData> series = new ArrayList<>();
        
        List<Number> incomingData = Arrays.asList(43000, 19000, 60000, 35000, 17000, 10000, 7000);
        series.add(new PaymentVolumeDTO.SeriesData("Incoming", incomingData));
        
        List<Number> outgoingData = Arrays.asList(50000, 39000, 42000, 31000, 26000, 14000, 6000);
        series.add(new PaymentVolumeDTO.SeriesData("Outgoing", outgoingData));
        
        return new PaymentVolumeDTO(categories, series);
    }

    /**
     * Get payment methods data for payment processing view.
     *
     * @return Payment methods data
     */
    @Override
    public PaymentMethodsDTO getPaymentMethodsData() {
        List<PaymentMethodsDTO.PaymentMethodData> methodData = new ArrayList<>();
        
        methodData.add(new PaymentMethodsDTO.PaymentMethodData("Credit Transfer", 45.0));
        methodData.add(new PaymentMethodsDTO.PaymentMethodData("Direct Debit", 25.0));
        methodData.add(new PaymentMethodsDTO.PaymentMethodData("Card Payment", 15.0));
        methodData.add(new PaymentMethodsDTO.PaymentMethodData("Mobile Payment", 10.0));
        methodData.add(new PaymentMethodsDTO.PaymentMethodData("Other", 5.0));
        
        return new PaymentMethodsDTO(methodData);
    }

    /**
     * Get transaction details by ID.
     *
     * @param id Transaction ID
     * @return Transaction details
     */
    @Override
    public TransactionDetailsDTO getTransactionDetails(String id) {
        // Generate a transaction with the given ID
        TransactionDTO transaction = generateTransaction(id);
        
        // Generate events for the transaction
        List<TransactionDetailsDTO.TransactionEventDTO> events = new ArrayList<>();
        LocalDateTime timestamp = transaction.getTimestamp();
        
        events.add(new TransactionDetailsDTO.TransactionEventDTO(
                timestamp,
                "Transaction Created",
                "System"
        ));
        
        events.add(new TransactionDetailsDTO.TransactionEventDTO(
                timestamp.plusMinutes(1),
                "Validation Passed",
                "System"
        ));
        
        events.add(new TransactionDetailsDTO.TransactionEventDTO(
                timestamp.plusMinutes(2),
                "Funds Reserved",
                "System"
        ));
        
        events.add(new TransactionDetailsDTO.TransactionEventDTO(
                timestamp.plusMinutes(3),
                "Transaction Completed",
                "System"
        ));
        
        // Generate notes for the transaction
        List<TransactionDetailsDTO.TransactionNoteDTO> notes = new ArrayList<>();
        
        notes.add(new TransactionDetailsDTO.TransactionNoteDTO(
                timestamp.plusMinutes(5),
                "Transaction processed successfully",
                "John Doe"
        ));
        
        return new TransactionDetailsDTO(
                transaction.getId(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getSourceAccount(),
                transaction.getDestinationAccount(),
                transaction.getTimestamp(),
                transaction.getReference(),
                transaction.getDescription(),
                events,
                notes
        );
    }

    /**
     * Get transaction reconciliation data.
     *
     * @return Transaction reconciliation data
     */
    @Override
    public TransactionReconciliationDTO getTransactionReconciliationData() {
        // Generate reconciliation summaries
        List<TransactionReconciliationDTO.ReconciliationSummaryDTO> summaries = new ArrayList<>();
        
        summaries.add(new TransactionReconciliationDTO.ReconciliationSummaryDTO(
                "Credit Transfers",
                1250,
                1230,
                20,
                98.4
        ));
        
        summaries.add(new TransactionReconciliationDTO.ReconciliationSummaryDTO(
                "Direct Debits",
                850,
                845,
                5,
                99.4
        ));
        
        summaries.add(new TransactionReconciliationDTO.ReconciliationSummaryDTO(
                "Card Payments",
                2340,
                2320,
                20,
                99.1
        ));
        
        summaries.add(new TransactionReconciliationDTO.ReconciliationSummaryDTO(
                "Mobile Payments",
                560,
                558,
                2,
                99.6
        ));
        
        // Generate reconciliation items
        List<TransactionReconciliationDTO.ReconciliationItemDTO> items = new ArrayList<>();
        LocalDate today = LocalDate.now();
        
        items.add(new TransactionReconciliationDTO.ReconciliationItemDTO(
                "R1001",
                "T1001",
                today.minusDays(1),
                "Salary Payment",
                1500.00,
                "USD",
                "Matched",
                "Bank"
        ));
        
        items.add(new TransactionReconciliationDTO.ReconciliationItemDTO(
                "R1002",
                "T1002",
                today.minusDays(1),
                "Utility Bill",
                120.50,
                "USD",
                "Matched",
                "Bank"
        ));
        
        items.add(new TransactionReconciliationDTO.ReconciliationItemDTO(
                "R1003",
                "T1003",
                today.minusDays(2),
                "Subscription Fee",
                9.99,
                "USD",
                "Unmatched",
                "Bank"
        ));
        
        items.add(new TransactionReconciliationDTO.ReconciliationItemDTO(
                "R1004",
                "T1004",
                today.minusDays(2),
                "Online Purchase",
                45.75,
                "USD",
                "Matched",
                "Bank"
        ));
        
        items.add(new TransactionReconciliationDTO.ReconciliationItemDTO(
                "R1005",
                "T1005",
                today.minusDays(3),
                "Insurance Premium",
                89.50,
                "USD",
                "Matched",
                "Bank"
        ));
        
        return new TransactionReconciliationDTO(summaries, items);
    }

    /**
     * Search transactions based on criteria.
     *
     * @param searchCriteria Search criteria
     * @return List of transactions matching the criteria
     */
    @Override
    public List<TransactionDTO> searchTransactions(TransactionSearchCriteriaDTO searchCriteria) {
        // Generate a list of transactions
        List<TransactionDTO> allTransactions = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            allTransactions.add(generateTransaction("T" + String.format("%04d", i)));
        }
        
        // Filter transactions based on search criteria
        return allTransactions.stream()
                .filter(transaction -> matchesCriteria(transaction, searchCriteria))
                .collect(Collectors.toList());
    }

    /**
     * Generate a transaction with the given ID.
     *
     * @param id Transaction ID
     * @return Generated transaction
     */
    private TransactionDTO generateTransaction(String id) {
        String[] types = {"Credit Transfer", "Direct Debit", "Card Payment", "Mobile Payment", "Standing Order"};
        String[] statuses = {"Completed", "Processing", "Pending", "Failed"};
        String[] currencies = {"USD", "EUR", "GBP", "JPY", "CAD", "AUD"};
        String[] accountPrefixes = {"ACC-", "IBAN-", "ACCT-"};
        String[] references = {"REF-", "INV-", "PAY-", "TRF-"};
        String[] descriptions = {
                "Monthly payment", "Salary transfer", "Utility bill", "Subscription fee",
                "Online purchase", "ATM withdrawal", "Loan payment", "Interest payment",
                "Tax payment", "Insurance premium", "Rent payment", "Dividend payment"
        };
        
        String type = types[random.nextInt(types.length)];
        String status = statuses[random.nextInt(statuses.length)];
        double amount = 10 + (random.nextDouble() * 9990);
        amount = Math.round(amount * 100.0) / 100.0;
        String currency = currencies[random.nextInt(currencies.length)];
        
        String sourcePrefix = accountPrefixes[random.nextInt(accountPrefixes.length)];
        String sourceAccount = sourcePrefix + random.nextInt(1000000);
        
        String destPrefix = accountPrefixes[random.nextInt(accountPrefixes.length)];
        String destinationAccount = destPrefix + random.nextInt(1000000);
        
        LocalDateTime now = LocalDateTime.now();
        int daysBack = random.nextInt(30);
        int hoursBack = random.nextInt(24);
        int minutesBack = random.nextInt(60);
        LocalDateTime timestamp = now.minusDays(daysBack).minusHours(hoursBack).minusMinutes(minutesBack);
        
        String refPrefix = references[random.nextInt(references.length)];
        String reference = refPrefix + random.nextInt(100000);
        
        String description = descriptions[random.nextInt(descriptions.length)];
        
        return new TransactionDTO(
                id,
                type,
                status,
                amount,
                currency,
                sourceAccount,
                destinationAccount,
                timestamp,
                reference,
                description
        );
    }

    /**
     * Check if a transaction matches the search criteria.
     *
     * @param transaction Transaction to check
     * @param criteria Search criteria
     * @return True if the transaction matches the criteria, false otherwise
     */
    private boolean matchesCriteria(TransactionDTO transaction, TransactionSearchCriteriaDTO criteria) {
        if (criteria == null) {
            return true;
        }
        
        // Check transaction ID
        if (criteria.getTransactionId() != null && !criteria.getTransactionId().isEmpty() &&
                !transaction.getId().contains(criteria.getTransactionId())) {
            return false;
        }
        
        // Check reference
        if (criteria.getReference() != null && !criteria.getReference().isEmpty() &&
                !transaction.getReference().contains(criteria.getReference())) {
            return false;
        }
        
        // Check types
        if (criteria.getTypes() != null && !criteria.getTypes().isEmpty() &&
                !criteria.getTypes().contains(transaction.getType())) {
            return false;
        }
        
        // Check statuses
        if (criteria.getStatuses() != null && !criteria.getStatuses().isEmpty() &&
                !criteria.getStatuses().contains(transaction.getStatus())) {
            return false;
        }
        
        // Check min amount
        if (criteria.getMinAmount() != null && transaction.getAmount() < criteria.getMinAmount()) {
            return false;
        }
        
        // Check max amount
        if (criteria.getMaxAmount() != null && transaction.getAmount() > criteria.getMaxAmount()) {
            return false;
        }
        
        // Check currency
        if (criteria.getCurrency() != null && !criteria.getCurrency().isEmpty() &&
                !transaction.getCurrency().equals(criteria.getCurrency())) {
            return false;
        }
        
        // Check source account
        if (criteria.getSourceAccount() != null && !criteria.getSourceAccount().isEmpty() &&
                !transaction.getSourceAccount().contains(criteria.getSourceAccount())) {
            return false;
        }
        
        // Check destination account
        if (criteria.getDestinationAccount() != null && !criteria.getDestinationAccount().isEmpty() &&
                !transaction.getDestinationAccount().contains(criteria.getDestinationAccount())) {
            return false;
        }
        
        // Check from date
        if (criteria.getFromDate() != null) {
            LocalDate transactionDate = transaction.getTimestamp().toLocalDate();
            if (transactionDate.isBefore(criteria.getFromDate())) {
                return false;
            }
        }
        
        // Check to date
        if (criteria.getToDate() != null) {
            LocalDate transactionDate = transaction.getTimestamp().toLocalDate();
            if (transactionDate.isAfter(criteria.getToDate())) {
                return false;
            }
        }
        
        // Check description
        if (criteria.getDescription() != null && !criteria.getDescription().isEmpty() &&
                !transaction.getDescription().contains(criteria.getDescription())) {
            return false;
        }
        
        return true;
    }
}