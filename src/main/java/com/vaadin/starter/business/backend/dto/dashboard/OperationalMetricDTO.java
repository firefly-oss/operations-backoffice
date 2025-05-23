package com.vaadin.starter.business.backend.dto.dashboard;

/**
 * Data Transfer Object for Operational Metric information.
 */
public class OperationalMetricDTO {
    private String metric;
    private String value;
    private String change;

    public OperationalMetricDTO() {
    }

    public OperationalMetricDTO(String metric, String value, String change) {
        this.metric = metric;
        this.value = value;
        this.change = change;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}