package com.vaadin.starter.business.backend.dto.fraudriskoperations;

import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * Data Transfer Object for AML/KYC cases.
 */
public class AmlKycCaseDTO {
    private String caseId;
    private String customerId;
    private String customerName;
    private String caseType;
    private String status;
    private String riskLevel;
    private String assignedTo;
    private LocalDateTime createdDate;
    private LocalDate dueDate;
    private String regulatoryBody;
    private String notes;

    /**
     * Default constructor.
     */
    public AmlKycCaseDTO() {
    }

    /**
     * Constructor with all fields.
     */
    public AmlKycCaseDTO(String caseId, String customerId, String customerName, String caseType, String status,
                         String riskLevel, String assignedTo, LocalDateTime createdDate, LocalDate dueDate,
                         String regulatoryBody, String notes) {
        this.caseId = caseId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.caseType = caseType;
        this.status = status;
        this.riskLevel = riskLevel;
        this.assignedTo = assignedTo;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
        this.regulatoryBody = regulatoryBody;
        this.notes = notes;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
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

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getRegulatoryBody() {
        return regulatoryBody;
    }

    public void setRegulatoryBody(String regulatoryBody) {
        this.regulatoryBody = regulatoryBody;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}