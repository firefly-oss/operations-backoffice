package com.vaadin.starter.business.backend.dto.cardoperations;

import java.time.LocalDate;

/**
 * Data Transfer Object for Card Dispute information.
 */
public class DisputeDTO {
    private String disputeId;
    private String cardNumber;
    private String cardHolderName;
    private String status;
    private String type;
    private Double amount;
    private LocalDate date;
    private String merchant;
    private String description;

    public DisputeDTO(String disputeId, String cardNumber, String cardHolderName, String status, 
                    String type, Double amount, LocalDate date, String merchant) {
        this.disputeId = disputeId;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.status = status;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.merchant = merchant;
    }

    public String getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(String disputeId) {
        this.disputeId = disputeId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Enum for dispute statuses
     */
    public enum Status {
        NEW("New"),
        IN_PROGRESS("In Progress"),
        PENDING_DOCUMENTATION("Pending Documentation"),
        RESOLVED("Resolved"),
        REJECTED("Rejected");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for dispute types
     */
    public enum Type {
        UNAUTHORIZED_TRANSACTION("Unauthorized Transaction"),
        DUPLICATE_CHARGE("Duplicate Charge"),
        MERCHANDISE_NOT_RECEIVED("Merchandise Not Received"),
        DEFECTIVE_MERCHANDISE("Defective Merchandise"),
        INCORRECT_AMOUNT("Incorrect Amount"),
        OTHER("Other");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}