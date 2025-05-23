package com.vaadin.starter.business.backend.dto.taskmanagement;

/**
 * Data Transfer Object for SLA Policy.
 */
public class SLAPolicyDTO {

    private String taskType;
    private String priority;
    private String responseTime;
    private String resolutionTime;
    private String escalationTime;

    /**
     * Default constructor.
     */
    public SLAPolicyDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param taskType       the task type
     * @param priority       the priority level
     * @param responseTime   the target response time
     * @param resolutionTime the target resolution time
     * @param escalationTime the escalation time
     */
    public SLAPolicyDTO(String taskType, String priority, String responseTime,
                      String resolutionTime, String escalationTime) {
        this.taskType = taskType;
        this.priority = priority;
        this.responseTime = responseTime;
        this.resolutionTime = resolutionTime;
        this.escalationTime = escalationTime;
    }

    // Getters and setters
    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getResolutionTime() {
        return resolutionTime;
    }

    public void setResolutionTime(String resolutionTime) {
        this.resolutionTime = resolutionTime;
    }

    public String getEscalationTime() {
        return escalationTime;
    }

    public void setEscalationTime(String escalationTime) {
        this.escalationTime = escalationTime;
    }
}