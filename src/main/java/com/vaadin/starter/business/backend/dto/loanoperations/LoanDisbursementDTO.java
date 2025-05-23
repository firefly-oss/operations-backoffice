package com.vaadin.starter.business.backend.dto.loanoperations;

import java.time.LocalDate;

/**
 * Data Transfer Object for loan disbursements.
 */
public class LoanDisbursementDTO {
    private String disbursementId;
    private String loanId;
    private String customerName;
    private String status;
    private Double amount;
    private String disbursementMethod;
    private LocalDate scheduledDate;
    private LocalDate processedDate;

    /**
     * Default constructor.
     */
    public LoanDisbursementDTO() {
    }

    /**
     * Constructor with all fields.
     */
    public LoanDisbursementDTO(String disbursementId, String loanId, String customerName, String status,
                             Double amount, String disbursementMethod, LocalDate scheduledDate, LocalDate processedDate) {
        this.disbursementId = disbursementId;
        this.loanId = loanId;
        this.customerName = customerName;
        this.status = status;
        this.amount = amount;
        this.disbursementMethod = disbursementMethod;
        this.scheduledDate = scheduledDate;
        this.processedDate = processedDate;
    }

    public String getDisbursementId() {
        return disbursementId;
    }

    public void setDisbursementId(String disbursementId) {
        this.disbursementId = disbursementId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getDisbursementMethod() {
        return disbursementMethod;
    }

    public void setDisbursementMethod(String disbursementMethod) {
        this.disbursementMethod = disbursementMethod;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public LocalDate getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(LocalDate processedDate) {
        this.processedDate = processedDate;
    }
}