package com.vaadin.starter.business.backend.dto.documentmanagement;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Authorization information.
 */
public class AuthorizationDTO {
    private String authorizationId;
    private String customerId;
    private String customerName;
    private String authorizationType;
    private String status;
    private String authorizedTo;
    private LocalDateTime creationDate;
    private LocalDateTime expiryDate;
    private String approvedBy;
    private String notes;

    public AuthorizationDTO() {
    }

    public AuthorizationDTO(String authorizationId, String customerId, String customerName, String authorizationType,
                          String status, String authorizedTo, LocalDateTime creationDate, LocalDateTime expiryDate,
                          String approvedBy, String notes) {
        this.authorizationId = authorizationId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.authorizationType = authorizationType;
        this.status = status;
        this.authorizedTo = authorizedTo;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.approvedBy = approvedBy;
        this.notes = notes;
    }

    public String getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAuthorizationType() {
        return authorizationType;
    }

    public void setAuthorizationType(String authorizationType) {
        this.authorizationType = authorizationType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthorizedTo() {
        return authorizedTo;
    }

    public void setAuthorizedTo(String authorizedTo) {
        this.authorizedTo = authorizedTo;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Enum for authorization statuses.
     */
    public enum Status {
        ACTIVE("Active"),
        EXPIRED("Expired"),
        REVOKED("Revoked"),
        PENDING_APPROVAL("Pending Approval"),
        APPROVED("Approved");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for authorization types.
     */
    public enum AuthorizationType {
        ACCOUNT_ACCESS("Account Access"),
        TRANSACTION_APPROVAL("Transaction Approval"),
        DOCUMENT_SIGNING("Document Signing"),
        INFORMATION_DISCLOSURE("Information Disclosure"),
        POWER_OF_ATTORNEY("Power of Attorney");

        private final String name;

        AuthorizationType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}