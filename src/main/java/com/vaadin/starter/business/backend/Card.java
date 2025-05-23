package com.vaadin.starter.business.backend;

import java.time.LocalDate;

/**
 * Card entity representing a bank card.
 */
public class Card {
    private String cardNumber;
    private String cardHolderName;
    private String cardType;
    private String status;
    private LocalDate expirationDate;
    private LocalDate issueDate;
    private String linkedAccountNumber;
    private String customerId;
    private boolean contactless;
    private Double creditLimit;

    public Card(String cardNumber, String cardHolderName, String cardType, String status, 
                LocalDate expirationDate, LocalDate issueDate, String linkedAccountNumber, 
                String customerId, boolean contactless, Double creditLimit) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardType = cardType;
        this.status = status;
        this.expirationDate = expirationDate;
        this.issueDate = issueDate;
        this.linkedAccountNumber = linkedAccountNumber;
        this.customerId = customerId;
        this.contactless = contactless;
        this.creditLimit = creditLimit;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getLinkedAccountNumber() {
        return linkedAccountNumber;
    }

    public void setLinkedAccountNumber(String linkedAccountNumber) {
        this.linkedAccountNumber = linkedAccountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean isContactless() {
        return contactless;
    }

    public void setContactless(boolean contactless) {
        this.contactless = contactless;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    /**
     * Enum for card types
     */
    public enum CardType {
        DEBIT("Debit"),
        CREDIT("Credit"),
        PREPAID("Prepaid"),
        VIRTUAL("Virtual"),
        BUSINESS("Business");

        private final String name;

        CardType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for card statuses
     */
    public enum Status {
        ACTIVE("Active"),
        INACTIVE("Inactive"),
        BLOCKED("Blocked"),
        EXPIRED("Expired"),
        PENDING_ACTIVATION("Pending Activation"),
        REPORTED_LOST("Reported Lost"),
        REPORTED_STOLEN("Reported Stolen");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}