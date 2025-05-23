package com.vaadin.starter.business.backend.dto.fraudriskoperations;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for risk alerts.
 */
public class RiskAlertDTO {
    private String alertId;
    private String alertType;
    private String severity;
    private String status;
    private String entityId;
    private String entityName;
    private String entityType;
    private LocalDateTime generatedDate;
    private String assignedTo;
    private String description;

    /**
     * Default constructor.
     */
    public RiskAlertDTO() {
    }

    /**
     * Constructor with all fields.
     */
    public RiskAlertDTO(String alertId, String alertType, String severity, String status, String entityId,
                       String entityName, String entityType, LocalDateTime generatedDate, String assignedTo,
                       String description) {
        this.alertId = alertId;
        this.alertType = alertType;
        this.severity = severity;
        this.status = status;
        this.entityId = entityId;
        this.entityName = entityName;
        this.entityType = entityType;
        this.generatedDate = generatedDate;
        this.assignedTo = assignedTo;
        this.description = description;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDateTime generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}