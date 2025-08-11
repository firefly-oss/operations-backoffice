package com.vaadin.starter.business.ui.views.clients.tabs.models;

import java.time.LocalDate;

/**
 * Data model for a client renting.
 */
public class Renting {
    private String rentingNumber;
    private String assetType;
    private String assetDescription;
    private String monthlyPayment;
    private String duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public Renting(String rentingNumber, String assetType, String assetDescription, 
                  String monthlyPayment, String duration, LocalDate startDate, 
                  LocalDate endDate, String status) {
        this.rentingNumber = rentingNumber;
        this.assetType = assetType;
        this.assetDescription = assetDescription;
        this.monthlyPayment = monthlyPayment;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public String getRentingNumber() { return rentingNumber; }
    public String getAssetType() { return assetType; }
    public String getAssetDescription() { return assetDescription; }
    public String getMonthlyPayment() { return monthlyPayment; }
    public String getDuration() { return duration; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getStatus() { return status; }
}