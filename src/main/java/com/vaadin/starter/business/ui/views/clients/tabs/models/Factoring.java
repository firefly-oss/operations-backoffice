package com.vaadin.starter.business.ui.views.clients.tabs.models;

import java.time.LocalDate;

/**
 * Data model for a client factoring.
 */
public class Factoring {
    private String invoiceNumber;
    private String debtor;
    private String invoiceAmount;
    private String advanceAmount;
    private String advanceRate;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private String status;

    /**
     * Constructor for Factoring.
     *
     * @param invoiceNumber The invoice number
     * @param debtor The debtor name
     * @param invoiceAmount The invoice amount
     * @param advanceAmount The advance amount
     * @param advanceRate The advance rate
     * @param invoiceDate The invoice date
     * @param dueDate The due date
     * @param status The status
     */
    public Factoring(String invoiceNumber, String debtor, String invoiceAmount, String advanceAmount, 
                    String advanceRate, LocalDate invoiceDate, LocalDate dueDate, String status) {
        this.invoiceNumber = invoiceNumber;
        this.debtor = debtor;
        this.invoiceAmount = invoiceAmount;
        this.advanceAmount = advanceAmount;
        this.advanceRate = advanceRate;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getInvoiceNumber() { return invoiceNumber; }
    public String getDebtor() { return debtor; }
    public String getInvoiceAmount() { return invoiceAmount; }
    public String getAdvanceAmount() { return advanceAmount; }
    public String getAdvanceRate() { return advanceRate; }
    public LocalDate getInvoiceDate() { return invoiceDate; }
    public LocalDate getDueDate() { return dueDate; }
    public String getStatus() { return status; }
}