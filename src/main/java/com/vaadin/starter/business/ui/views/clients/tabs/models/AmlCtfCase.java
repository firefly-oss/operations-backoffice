package com.vaadin.starter.business.ui.views.clients.tabs.models;

import java.time.LocalDate;

/**
 * Data model for an AML/CTF case.
 */
public class AmlCtfCase {
    private String caseId;
    private String caseType;
    private String description;
    private String riskLevel;
    private String status;
    private LocalDate openDate;
    private LocalDate closeDate;
    private String assignedTo;
    private String notes;

    /**
     * Constructor for AmlCtfCase.
     *
     * @param caseId The case ID
     * @param caseType The case type
     * @param description The case description
     * @param riskLevel The risk level
     * @param status The case status
     * @param openDate The open date
     * @param closeDate The close date (can be null)
     * @param assignedTo The person assigned to the case
     * @param notes Additional notes
     */
    public AmlCtfCase(String caseId, String caseType, String description, String riskLevel, 
                     String status, LocalDate openDate, LocalDate closeDate, String assignedTo, String notes) {
        this.caseId = caseId;
        this.caseType = caseType;
        this.description = description;
        this.riskLevel = riskLevel;
        this.status = status;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.assignedTo = assignedTo;
        this.notes = notes;
    }

    public String getCaseId() { return caseId; }
    public String getCaseType() { return caseType; }
    public String getDescription() { return description; }
    public String getRiskLevel() { return riskLevel; }
    public String getStatus() { return status; }
    public LocalDate getOpenDate() { return openDate; }
    public LocalDate getCloseDate() { return closeDate; }
    public String getAssignedTo() { return assignedTo; }
    public String getNotes() { return notes; }
}