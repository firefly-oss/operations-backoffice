package com.vaadin.starter.business.backend.dto.dashboard;

/**
 * Data Transfer Object for Alert Summary information.
 */
public class AlertSummaryDTO {
    private String severity;
    private int count;
    private String color;

    public AlertSummaryDTO() {
    }

    public AlertSummaryDTO(String severity, int count, String color) {
        this.severity = severity;
        this.count = count;
        this.color = color;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}