package com.vaadin.starter.business.backend.dto.documentmanagement;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Document Tracking Entry information.
 */
public class DocumentTrackingEntryDTO {
    private String documentId;
    private String customerId;
    private String documentType;
    private String status;
    private LocalDateTime submissionDate;
    private LocalDateTime lastUpdated;
    private String currentStage;
    private String assignedTo;
    private String estimatedCompletion;
    private String notes;

    public DocumentTrackingEntryDTO() {
    }

    public DocumentTrackingEntryDTO(String documentId, String customerId, String documentType, String status,
                                  LocalDateTime submissionDate, LocalDateTime lastUpdated, String currentStage,
                                  String assignedTo, String estimatedCompletion, String notes) {
        this.documentId = documentId;
        this.customerId = customerId;
        this.documentType = documentType;
        this.status = status;
        this.submissionDate = submissionDate;
        this.lastUpdated = lastUpdated;
        this.currentStage = currentStage;
        this.assignedTo = assignedTo;
        this.estimatedCompletion = estimatedCompletion;
        this.notes = notes;
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

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getEstimatedCompletion() {
        return estimatedCompletion;
    }

    public void setEstimatedCompletion(String estimatedCompletion) {
        this.estimatedCompletion = estimatedCompletion;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Enum for document tracking statuses.
     */
    public enum Status {
        PENDING("Pending"),
        PROCESSING("Processing"),
        COMPLETED("Completed"),
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
     * Enum for document tracking stages.
     */
    public enum Stage {
        DOCUMENT_SUBMISSION("Document Submission"),
        INITIAL_VERIFICATION("Initial Verification"),
        CONTENT_VALIDATION("Content Validation"),
        APPROVAL_PROCESS("Approval Process"),
        FINAL_REVIEW("Final Review"),
        ARCHIVING("Archiving"),
        REJECTED("Rejected");

        private final String name;

        Stage(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}