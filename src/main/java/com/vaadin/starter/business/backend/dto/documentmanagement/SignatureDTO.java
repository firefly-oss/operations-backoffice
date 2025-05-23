package com.vaadin.starter.business.backend.dto.documentmanagement;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Signature information.
 */
public class SignatureDTO {
    private String signatureId;
    private String customerId;
    private String customerName;
    private String signatureType;
    private String status;
    private LocalDateTime creationDate;
    private LocalDateTime expiryDate;
    private String verifiedBy;
    private String notes;

    public SignatureDTO() {
    }

    public SignatureDTO(String signatureId, String customerId, String customerName, String signatureType,
                      String status, LocalDateTime creationDate, LocalDateTime expiryDate,
                      String verifiedBy, String notes) {
        this.signatureId = signatureId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.signatureType = signatureType;
        this.status = status;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.verifiedBy = verifiedBy;
        this.notes = notes;
    }

    public String getSignatureId() {
        return signatureId;
    }

    public void setSignatureId(String signatureId) {
        this.signatureId = signatureId;
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

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Enum for signature statuses.
     */
    public enum Status {
        ACTIVE("Active"),
        EXPIRED("Expired"),
        REVOKED("Revoked"),
        PENDING_VERIFICATION("Pending Verification"),
        VERIFIED("Verified");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for signature types.
     */
    public enum SignatureType {
        ELECTRONIC("Electronic"),
        DIGITAL("Digital"),
        HANDWRITTEN("Handwritten"),
        BIOMETRIC("Biometric"),
        QUALIFIED_ELECTRONIC("Qualified Electronic");

        private final String name;

        SignatureType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}