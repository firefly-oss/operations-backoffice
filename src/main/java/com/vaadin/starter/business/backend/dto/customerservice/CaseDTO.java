package com.vaadin.starter.business.backend.dto.customerservice;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Case information.
 */
public class CaseDTO {
    private String id;
    private String caseNumber;
    private String subject;
    private String description;
    private String status;
    private String priority;
    private String category;
    private String customerId;
    private String customerName;
    private String assignedTo;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private LocalDateTime dueDate;
    private LocalDateTime closedDate;
    private String resolution;
    private String source;

    public CaseDTO() {
    }

    public CaseDTO(String id, String caseNumber, String subject, String description, String status,
                 String priority, String category, String customerId, String customerName,
                 String assignedTo, LocalDateTime createdDate, LocalDateTime lastUpdatedDate,
                 LocalDateTime dueDate, LocalDateTime closedDate, String resolution, String source) {
        this.id = id;
        this.caseNumber = caseNumber;
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.category = category;
        this.customerId = customerId;
        this.customerName = customerName;
        this.assignedTo = assignedTo;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.dueDate = dueDate;
        this.closedDate = closedDate;
        this.resolution = resolution;
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Enum for case statuses.
     */
    public enum Status {
        NEW("New"),
        OPEN("Open"),
        IN_PROGRESS("In Progress"),
        PENDING("Pending"),
        RESOLVED("Resolved"),
        CLOSED("Closed"),
        CANCELLED("Cancelled");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for case priorities.
     */
    public enum Priority {
        LOW("Low"),
        MEDIUM("Medium"),
        HIGH("High"),
        URGENT("Urgent"),
        CRITICAL("Critical");

        private final String name;

        Priority(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for case categories.
     */
    public enum Category {
        ACCOUNT("Account"),
        BILLING("Billing"),
        TECHNICAL("Technical"),
        PRODUCT("Product"),
        SERVICE("Service"),
        COMPLAINT("Complaint"),
        INQUIRY("Inquiry"),
        FEEDBACK("Feedback");

        private final String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for case sources.
     */
    public enum Source {
        PHONE("Phone"),
        EMAIL("Email"),
        WEB("Web"),
        CHAT("Chat"),
        SOCIAL_MEDIA("Social Media"),
        IN_PERSON("In Person"),
        LETTER("Letter"),
        INTERNAL("Internal");

        private final String name;

        Source(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}