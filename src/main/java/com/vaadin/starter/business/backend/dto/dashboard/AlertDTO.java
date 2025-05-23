package com.vaadin.starter.business.backend.dto.dashboard;

/**
 * Data Transfer Object for Alert information.
 */
public class AlertDTO {
    private String timestamp;
    private String severity;
    private String system;
    private String message;

    public AlertDTO() {
    }

    public AlertDTO(String timestamp, String severity, String system, String message) {
        this.timestamp = timestamp;
        this.severity = severity;
        this.system = system;
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}