package com.vaadin.starter.business.backend.dto.fraudriskoperations;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for suspicious activities.
 */
public class SuspiciousActivityDTO {
    private String activityId;
    private String customerId;
    private String customerName;
    private String accountNumber;
    private String activityType;
    private String riskLevel;
    private String status;
    private LocalDateTime detectedDate;
    private double amount;
    private String description;

    /**
     * Default constructor.
     */
    public SuspiciousActivityDTO() {
    }

    /**
     * Constructor with all fields.
     */
    public SuspiciousActivityDTO(String activityId, String customerId, String customerName, String accountNumber,
                                String activityType, String riskLevel, String status, LocalDateTime detectedDate,
                                double amount, String description) {
        this.activityId = activityId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.accountNumber = accountNumber;
        this.activityType = activityType;
        this.riskLevel = riskLevel;
        this.status = status;
        this.detectedDate = detectedDate;
        this.amount = amount;
        this.description = description;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDetectedDate() {
        return detectedDate;
    }

    public void setDetectedDate(LocalDateTime detectedDate) {
        this.detectedDate = detectedDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}