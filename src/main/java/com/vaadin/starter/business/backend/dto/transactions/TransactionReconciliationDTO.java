package com.vaadin.starter.business.backend.dto.transactions;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object for transaction reconciliation data.
 */
public class TransactionReconciliationDTO {
    private List<ReconciliationSummaryDTO> summaries;
    private List<ReconciliationItemDTO> items;

    public TransactionReconciliationDTO() {
    }

    public TransactionReconciliationDTO(List<ReconciliationSummaryDTO> summaries, List<ReconciliationItemDTO> items) {
        this.summaries = summaries;
        this.items = items;
    }

    public List<ReconciliationSummaryDTO> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<ReconciliationSummaryDTO> summaries) {
        this.summaries = summaries;
    }

    public List<ReconciliationItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ReconciliationItemDTO> items) {
        this.items = items;
    }

    /**
     * Data Transfer Object for reconciliation summary.
     */
    public static class ReconciliationSummaryDTO {
        private String category;
        private int total;
        private int matched;
        private int unmatched;
        private double matchPercentage;

        public ReconciliationSummaryDTO() {
        }

        public ReconciliationSummaryDTO(String category, int total, int matched, int unmatched, double matchPercentage) {
            this.category = category;
            this.total = total;
            this.matched = matched;
            this.unmatched = unmatched;
            this.matchPercentage = matchPercentage;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getMatched() {
            return matched;
        }

        public void setMatched(int matched) {
            this.matched = matched;
        }

        public int getUnmatched() {
            return unmatched;
        }

        public void setUnmatched(int unmatched) {
            this.unmatched = unmatched;
        }

        public double getMatchPercentage() {
            return matchPercentage;
        }

        public void setMatchPercentage(double matchPercentage) {
            this.matchPercentage = matchPercentage;
        }
    }

    /**
     * Data Transfer Object for reconciliation item.
     */
    public static class ReconciliationItemDTO {
        private String id;
        private String transactionId;
        private LocalDate date;
        private String description;
        private double amount;
        private String currency;
        private String status;
        private String source;

        public ReconciliationItemDTO() {
        }

        public ReconciliationItemDTO(String id, String transactionId, LocalDate date, String description,
                                   double amount, String currency, String status, String source) {
            this.id = id;
            this.transactionId = transactionId;
            this.date = date;
            this.description = description;
            this.amount = amount;
            this.currency = currency;
            this.status = status;
            this.source = source;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
}