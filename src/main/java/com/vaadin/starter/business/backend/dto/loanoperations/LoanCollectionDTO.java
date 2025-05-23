package com.vaadin.starter.business.backend.dto.loanoperations;

import java.time.LocalDate;

/**
 * Data Transfer Object for loan collections.
 */
public class LoanCollectionDTO {
    private String collectionId;
    private String loanId;
    private String customerName;
    private String status;
    private Double amount;
    private String paymentMethod;
    private LocalDate dueDate;
    private LocalDate paymentDate;

    /**
     * Default constructor.
     */
    public LoanCollectionDTO() {
    }

    /**
     * Constructor with all fields.
     */
    public LoanCollectionDTO(String collectionId, String loanId, String customerName, String status,
                           Double amount, String paymentMethod, LocalDate dueDate, LocalDate paymentDate) {
        this.collectionId = collectionId;
        this.loanId = loanId;
        this.customerName = customerName;
        this.status = status;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}