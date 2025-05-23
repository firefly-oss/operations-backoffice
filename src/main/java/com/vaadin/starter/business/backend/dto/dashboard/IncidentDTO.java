package com.vaadin.starter.business.backend.dto.dashboard;

/**
 * Data Transfer Object for Incident information.
 */
public class IncidentDTO {
    private String id;
    private String startTime;
    private String endTime;
    private String system;
    private String description;
    private String status;

    public IncidentDTO() {
    }

    public IncidentDTO(String id, String startTime, String endTime, String system, String description, String status) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.system = system;
        this.description = description;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}