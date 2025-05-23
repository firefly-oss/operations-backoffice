package com.vaadin.starter.business.backend.dto.reportinganalytics;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for performance metrics.
 */
public class PerformanceMetricDTO {
    private String metricId;
    private String metricName;
    private String category;
    private String value;
    private String unit;
    private String change;
    private String changeDirection; // "up" or "down"
    private LocalDateTime timestamp;
    private String server;
    private Number[] historicalData;
    private String[] timeLabels;

    /**
     * Default constructor.
     */
    public PerformanceMetricDTO() {
    }

    /**
     * Constructor with all fields.
     */
    public PerformanceMetricDTO(String metricId, String metricName, String category, String value, String unit,
                               String change, String changeDirection, LocalDateTime timestamp, String server,
                               Number[] historicalData, String[] timeLabels) {
        this.metricId = metricId;
        this.metricName = metricName;
        this.category = category;
        this.value = value;
        this.unit = unit;
        this.change = change;
        this.changeDirection = changeDirection;
        this.timestamp = timestamp;
        this.server = server;
        this.historicalData = historicalData;
        this.timeLabels = timeLabels;
    }

    public String getMetricId() {
        return metricId;
    }

    public void setMetricId(String metricId) {
        this.metricId = metricId;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getChangeDirection() {
        return changeDirection;
    }

    public void setChangeDirection(String changeDirection) {
        this.changeDirection = changeDirection;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Number[] getHistoricalData() {
        return historicalData;
    }

    public void setHistoricalData(Number[] historicalData) {
        this.historicalData = historicalData;
    }

    public String[] getTimeLabels() {
        return timeLabels;
    }

    public void setTimeLabels(String[] timeLabels) {
        this.timeLabels = timeLabels;
    }
}