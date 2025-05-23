package com.vaadin.starter.business.backend;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an incident in the system.
 */
public class Incident {
    private String id;
    private String incidentNumber;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String impact;
    private String category;
    private String affectedService;
    private String reportedBy;
    private String assignedTo;
    private LocalDateTime reportedDate;
    private LocalDateTime lastUpdatedDate;
    private LocalDateTime resolvedDate;
    private String resolution;
    private String rootCause;

    public Incident() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }

    public Incident(String id, String incidentNumber, String title, String description, String status,
                  String priority, String impact, String category, String affectedService,
                  String reportedBy, String assignedTo, LocalDateTime reportedDate,
                  LocalDateTime lastUpdatedDate, LocalDateTime resolvedDate, String resolution,
                  String rootCause) {
        this.id = id;
        this.incidentNumber = incidentNumber;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.impact = impact;
        this.category = category;
        this.affectedService = affectedService;
        this.reportedBy = reportedBy;
        this.assignedTo = assignedTo;
        this.reportedDate = reportedDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.resolvedDate = resolvedDate;
        this.resolution = resolution;
        this.rootCause = rootCause;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIncidentNumber() {
        return incidentNumber;
    }

    public void setIncidentNumber(String incidentNumber) {
        this.incidentNumber = incidentNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAffectedService() {
        return affectedService;
    }

    public void setAffectedService(String affectedService) {
        this.affectedService = affectedService;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDateTime getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(LocalDateTime reportedDate) {
        this.reportedDate = reportedDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public LocalDateTime getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(LocalDateTime resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }

    /**
     * Enum for incident statuses.
     */
    public enum Status {
        NEW("New"),
        ASSIGNED("Assigned"),
        IN_PROGRESS("In Progress"),
        ON_HOLD("On Hold"),
        RESOLVED("Resolved"),
        CLOSED("Closed");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for incident priorities.
     */
    public enum Priority {
        LOW("Low"),
        MEDIUM("Medium"),
        HIGH("High"),
        CRITICAL("Critical"),
        EMERGENCY("Emergency");

        private final String name;

        Priority(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for incident impacts.
     */
    public enum Impact {
        LOW("Low"),
        MEDIUM("Medium"),
        HIGH("High"),
        EXTENSIVE("Extensive");

        private final String name;

        Impact(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for incident categories.
     */
    public enum Category {
        HARDWARE("Hardware"),
        SOFTWARE("Software"),
        NETWORK("Network"),
        DATABASE("Database"),
        SECURITY("Security"),
        APPLICATION("Application"),
        INFRASTRUCTURE("Infrastructure"),
        SERVICE_DISRUPTION("Service Disruption");

        private final String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}