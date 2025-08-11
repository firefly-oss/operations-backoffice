package com.vaadin.starter.business.ui.views.clients.tabs.models;

import java.time.LocalDate;

/**
 * Data model for a client leasing.
 */
public class Leasing {
    private String leasingNumber;
    private String assetType;
    private String assetDescription;
    private String assetValue;
    private String remainingBalance;
    private String interestRate;
    private String monthlyPayment;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public Leasing(String leasingNumber, String assetType, String assetDescription, String assetValue, 
                  String remainingBalance, String interestRate, String monthlyPayment, 
                  LocalDate startDate, LocalDate endDate, String status) {
        this.leasingNumber = leasingNumber;
        this.assetType = assetType;
        this.assetDescription = assetDescription;
        this.assetValue = assetValue;
        this.remainingBalance = remainingBalance;
        this.interestRate = interestRate;
        this.monthlyPayment = monthlyPayment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public String getLeasingNumber() { return leasingNumber; }
    public String getAssetType() { return assetType; }
    public String getAssetDescription() { return assetDescription; }
    public String getAssetValue() { return assetValue; }
    public String getRemainingBalance() { return remainingBalance; }
    public String getInterestRate() { return interestRate; }
    public String getMonthlyPayment() { return monthlyPayment; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getStatus() { return status; }
}