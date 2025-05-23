package com.vaadin.starter.business.backend.dto.reportinganalytics;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for audit events.
 */
public class AuditEventDTO {
    private String eventId;
    private LocalDateTime timestamp;
    private String user;
    private String actionType;
    private String module;
    private String status;
    private String ipAddress;
    private String sessionId;
    private String details;
    private String beforeData;
    private String afterData;

    /**
     * Default constructor.
     */
    public AuditEventDTO() {
    }

    /**
     * Constructor with all fields.
     */
    public AuditEventDTO(String eventId, LocalDateTime timestamp, String user, String actionType, String module,
                        String status, String ipAddress, String sessionId, String details, String beforeData,
                        String afterData) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.user = user;
        this.actionType = actionType;
        this.module = module;
        this.status = status;
        this.ipAddress = ipAddress;
        this.sessionId = sessionId;
        this.details = details;
        this.beforeData = beforeData;
        this.afterData = afterData;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getBeforeData() {
        return beforeData;
    }

    public void setBeforeData(String beforeData) {
        this.beforeData = beforeData;
    }

    public String getAfterData() {
        return afterData;
    }

    public void setAfterData(String afterData) {
        this.afterData = afterData;
    }
}