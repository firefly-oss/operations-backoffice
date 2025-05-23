package com.vaadin.starter.business.backend.dto.transactions;

/**
 * Data Transfer Object for batch jobs.
 */
public class BatchJobDTO {
    private String id;
    private String name;
    private String startTime;
    private String endTime;
    private String duration;
    private String records;
    private String status;

    public BatchJobDTO() {
    }

    public BatchJobDTO(String id, String name, String startTime, String endTime, 
                     String duration, String records, String status) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.records = records;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}