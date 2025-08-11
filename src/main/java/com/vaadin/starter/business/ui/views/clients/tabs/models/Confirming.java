package com.vaadin.starter.business.ui.views.clients.tabs.models;

import java.time.LocalDate;

/**
 * Data model for a client confirming.
 */
public class Confirming {
    private String supplierNumber;
    private String supplierName;
    private String invoiceNumber;
    private String invoiceAmount;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private String paymentTerms;
    private String status;

    /**
     * Constructor for Confirming.
     *
     * @param supplierNumber The supplier number
     * @param supplierName The supplier name
     * @param invoiceNumber The invoice number
     * @param invoiceAmount The invoice amount
     * @param invoiceDate The invoice date
     * @param dueDate The due date
     * @param paymentTerms The payment terms
     * @param status The status
     */
    public Confirming(String supplierNumber, String supplierName, String invoiceNumber, String invoiceAmount, 
                     LocalDate invoiceDate, LocalDate dueDate, String paymentTerms, String status) {
        this.supplierNumber = supplierNumber;
        this.supplierName = supplierName;
        this.invoiceNumber = invoiceNumber;
        this.invoiceAmount = invoiceAmount;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.paymentTerms = paymentTerms;
        this.status = status;
    }

    public String getSupplierNumber() { return supplierNumber; }
    public String getSupplierName() { return supplierName; }
    public String getInvoiceNumber() { return invoiceNumber; }
    public String getInvoiceAmount() { return invoiceAmount; }
    public LocalDate getInvoiceDate() { return invoiceDate; }
    public LocalDate getDueDate() { return dueDate; }
    public String getPaymentTerms() { return paymentTerms; }
    public String getStatus() { return status; }
}