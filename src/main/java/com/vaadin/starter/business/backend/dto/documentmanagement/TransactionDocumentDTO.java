package com.vaadin.starter.business.backend.dto.documentmanagement;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Transaction Document information.
 */
public class TransactionDocumentDTO {
    private String documentId;
    private String transactionId;
    private String transactionType;
    private String documentType;
    private String status;
    private String filename;
    private LocalDateTime uploadDate;
    private String fileSize;
    private String uploadedBy;
    private String description;

    public TransactionDocumentDTO() {
    }

    public TransactionDocumentDTO(String documentId, String transactionId, String transactionType, String documentType,
                                String status, String filename, LocalDateTime uploadDate, String fileSize,
                                String uploadedBy, String description) {
        this.documentId = documentId;
        this.transactionId = transactionId;
        this.transactionType = transactionType;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
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
     * Enum for transaction document statuses.
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
     * Enum for transaction document types.
     */
    public enum DocumentType {
        INVOICE("Invoice"),
        RECEIPT("Receipt"),
        CONTRACT("Contract"),
        AGREEMENT("Agreement"),
        STATEMENT("Statement"),
        AUTHORIZATION("Authorization"),
        CONFIRMATION("Confirmation"),
        OTHER("Other");

        private final String name;

        DocumentType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for transaction types.
     */
    public enum TransactionType {
        PAYMENT("Payment"),
        TRANSFER("Transfer"),
        DEPOSIT("Deposit"),
        WITHDRAWAL("Withdrawal"),
        LOAN("Loan"),
        INVESTMENT("Investment"),
        FOREIGN_EXCHANGE("Foreign Exchange"),
        OTHER("Other");

        private final String name;

        TransactionType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}