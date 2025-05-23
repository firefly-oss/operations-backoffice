package com.vaadin.starter.business.backend;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a customer case in the system.
 */
public class Case {
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
    private LocalDateTime lastModifiedDate;
    private LocalDateTime dueDate;
    private String resolution;
    private String source;

    public Case() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }

    public Case(String id, String caseNumber, String subject, String description, String status,
               String priority, String category, String customerId, String customerName,
               String assignedTo, LocalDateTime createdDate, LocalDateTime lastModifiedDate,
               LocalDateTime dueDate, String resolution, String source) {
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
        this.lastModifiedDate = lastModifiedDate;
        this.dueDate = dueDate;
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

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
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
        INQUIRY("Inquiry"),
        COMPLAINT("Complaint");

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
        EMAIL("Email"),
        PHONE("Phone"),
        WEB("Web"),
        CHAT("Chat"),
        SOCIAL("Social Media"),
        IN_PERSON("In Person");

        private final String name;

        Source(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}