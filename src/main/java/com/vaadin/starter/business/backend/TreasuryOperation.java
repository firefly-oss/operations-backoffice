package com.vaadin.starter.business.backend;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a treasury operation in the system.
 */
public class TreasuryOperation {
    private String id;
    private String operationType;
    private String status;
    private Double amount;
    private String currency;
    private String counterparty;
    private LocalDateTime executionDate;
    private LocalDateTime settlementDate;
    private String trader;
    private String instrument;
    private Double rate;
    private String reference;
    private String description;

    public TreasuryOperation() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }

    public TreasuryOperation(String id, String operationType, String status, Double amount, String currency,
                           String counterparty, LocalDateTime executionDate, LocalDateTime settlementDate,
                           String trader, String instrument, Double rate, String reference, String description) {
        this.id = id;
        this.operationType = operationType;
        this.status = status;
        this.amount = amount;
        this.currency = currency;
        this.counterparty = counterparty;
        this.executionDate = executionDate;
        this.settlementDate = settlementDate;
        this.trader = trader;
        this.instrument = instrument;
        this.rate = rate;
        this.reference = reference;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(LocalDateTime executionDate) {
        this.executionDate = executionDate;
    }

    public LocalDateTime getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(LocalDateTime settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Enum for operation types.
     */
    public enum OperationType {
        MONEY_MARKET_DEPOSIT("Money Market Deposit"),
        MONEY_MARKET_LOAN("Money Market Loan"),
        BOND_PURCHASE("Bond Purchase"),
        BOND_SALE("Bond Sale"),
        REPO("Repo"),
        REVERSE_REPO("Reverse Repo"),
        COMMERCIAL_PAPER_ISSUANCE("Commercial Paper Issuance"),
        CERTIFICATE_OF_DEPOSIT("Certificate of Deposit"),
        INTEREST_RATE_SWAP("Interest Rate Swap"),
        FORWARD_RATE_AGREEMENT("Forward Rate Agreement");

        private final String name;

        OperationType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for operation statuses.
     */
    public enum Status {
        PENDING("Pending"),
        EXECUTED("Executed"),
        SETTLED("Settled"),
        MATURED("Matured"),
        CANCELLED("Cancelled"),
        FAILED("Failed");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}