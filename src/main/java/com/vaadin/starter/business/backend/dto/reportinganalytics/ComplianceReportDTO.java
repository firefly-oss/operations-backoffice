package com.vaadin.starter.business.backend.dto.reportinganalytics;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for compliance reports.
 */
public class ComplianceReportDTO {
    private String reportId;
    private String reportName;
    private String regulation;
    private String frequency;
    private LocalDateTime dueDate;
    private String status;
    private String assignedTo;

    /**
     * Default constructor.
     */
    public ComplianceReportDTO() {
    }

    /**
     * Constructor with all fields.
     */
    public ComplianceReportDTO(String reportId, String reportName, String regulation, String frequency,
                              LocalDateTime dueDate, String status, String assignedTo) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.regulation = regulation;
        this.frequency = frequency;
        this.dueDate = dueDate;
        this.status = status;
        this.assignedTo = assignedTo;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getRegulation() {
        return regulation;
    }

    public void setRegulation(String regulation) {
        this.regulation = regulation;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}