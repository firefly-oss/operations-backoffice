package com.vaadin.starter.business.ui.views.clients.tabs.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data model for a client contract.
 */
public class Contract {
    private String contractId;
    private String clientId;
    private String clientName;
    private String type;
    private String value;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    /**
     * Constructor for Contract.
     *
     * @param contractId The contract ID
     * @param clientId The client ID
     * @param clientName The client name
     * @param type The contract type
     * @param value The contract value
     * @param status The contract status
     * @param startDate The start date
     * @param endDate The end date
     * @param dateCreated The creation date
     * @param dateUpdated The last update date
     */
    public Contract(String contractId, String clientId, String clientName, String type, 
                  String value, String status, LocalDate startDate, LocalDate endDate, 
                  LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.contractId = contractId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.type = type;
        this.value = value;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public String getContractId() { return contractId; }
    public String getClientId() { return clientId; }
    public String getClientName() { return clientName; }
    public String getType() { return type; }
    public String getValue() { return value; }
    public String getStatus() { return status; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public LocalDateTime getDateCreated() { return dateCreated; }
    public LocalDateTime getDateUpdated() { return dateUpdated; }
}