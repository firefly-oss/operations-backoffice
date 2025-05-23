package com.vaadin.starter.business.backend.dto.taskmanagement;

import java.time.LocalDate;

/**
 * Data Transfer Object for SLA Task.
 */
public class SLATaskDTO {

    private String id;
    private String subject;
    private String slaStatus;
    private String priority;
    private String type;
    private String assignee;
    private LocalDate createdDate;
    private String slaTarget;
    private String timeRemaining;

    /**
     * Default constructor.
     */
    public SLATaskDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id            the task ID
     * @param subject       the task subject
     * @param slaStatus     the SLA status
     * @param priority      the task priority
     * @param type          the task type
     * @param assignee      the task assignee
     * @param createdDate   the task creation date
     * @param slaTarget     the SLA target time
     * @param timeRemaining the time remaining until SLA breach
     */
    public SLATaskDTO(String id, String subject, String slaStatus, String priority,
                    String type, String assignee, LocalDate createdDate,
                    String slaTarget, String timeRemaining) {
        this.id = id;
        this.subject = subject;
        this.slaStatus = slaStatus;
        this.priority = priority;
        this.type = type;
        this.assignee = assignee;
        this.createdDate = createdDate;
        this.slaTarget = slaTarget;
        this.timeRemaining = timeRemaining;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSlaStatus() {
        return slaStatus;
    }

    public void setSlaStatus(String slaStatus) {
        this.slaStatus = slaStatus;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getSlaTarget() {
        return slaTarget;
    }

    public void setSlaTarget(String slaTarget) {
        this.slaTarget = slaTarget;
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(String timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}