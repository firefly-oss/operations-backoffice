package com.vaadin.starter.business.backend.dto.documentmanagement;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Document information.
 */
public class DocumentDTO {
    private String documentId;
    private String customerId;
    private String customerName;
    private String documentType;
    private String status;
    private String filename;
    private LocalDateTime uploadDate;
    private String fileSize;
    private String uploadedBy;
    private String description;

    public DocumentDTO() {
    }

    public DocumentDTO(String documentId, String customerId, String customerName, String documentType,
                     String status, String filename, LocalDateTime uploadDate, String fileSize,
                     String uploadedBy, String description) {
        this.documentId = documentId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.documentType = documentType;
        this.status = status;
        this.filename = filename;
        this.uploadDate = uploadDate;
        this.fileSize = fileSize;
        this.uploadedBy = uploadedBy;
        this.description = description;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Enum for document statuses.
     */
    public enum Status {
        PENDING_REVIEW("Pending Review"),
        APPROVED("Approved"),
        REJECTED("Rejected"),
        EXPIRED("Expired"),
        NEEDS_UPDATE("Needs Update");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for document types.
     */
    public enum DocumentType {
        ID_DOCUMENT("ID Document"),
        PROOF_OF_ADDRESS("Proof of Address"),
        INCOME_STATEMENT("Income Statement"),
        TAX_RETURN("Tax Return"),
        BANK_STATEMENT("Bank Statement"),
        EMPLOYMENT_VERIFICATION("Employment Verification"),
        SIGNATURE_CARD("Signature Card"),
        CONTRACT("Contract"),
        APPLICATION_FORM("Application Form"),
        POWER_OF_ATTORNEY("Power of Attorney");

        private final String name;

        DocumentType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}