package com.vaadin.starter.business.backend.dto.loanoperations;

import java.time.LocalDate;

/**
 * Data Transfer Object for loan applications.
 */
public class LoanApplicationDTO {
    private String applicationId;
    private String customerId;
    private String customerName;
    private String loanType;
    private String status;
    private Double amount;
    private LocalDate applicationDate;

    /**
     * Default constructor.
     */
    public LoanApplicationDTO() {
    }

    /**
     * Constructor with all fields.
     */
    public LoanApplicationDTO(String applicationId, String customerId, String customerName, String loanType,
                             String status, Double amount, LocalDate applicationDate) {
        this.applicationId = applicationId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.loanType = loanType;
        this.status = status;
        this.amount = amount;
        this.applicationDate = applicationDate;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
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

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
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

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }
}