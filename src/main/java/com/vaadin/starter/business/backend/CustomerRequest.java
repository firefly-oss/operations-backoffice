package com.vaadin.starter.business.backend;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a customer request in the system.
 */
public class CustomerRequest {
    private String id;
    private String requestNumber;
    private String subject;
    private String description;
    private String status;
    private String priority;
    private String type;
    private String customerId;
    private String customerName;
    private String assignedTo;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private LocalDateTime completionDate;
    private String resolution;
    private String channel;

    public CustomerRequest() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }

    public CustomerRequest(String id, String requestNumber, String subject, String description, String status,
                         String priority, String type, String customerId, String customerName,
                         String assignedTo, LocalDateTime createdDate, LocalDateTime lastModifiedDate,
                         LocalDateTime completionDate, String resolution, String channel) {
        this.id = id;
        this.requestNumber = requestNumber;
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.type = type;
        this.customerId = customerId;
        this.customerName = customerName;
        this.assignedTo = assignedTo;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.completionDate = completionDate;
        this.resolution = resolution;
        this.channel = channel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * Enum for request statuses.
     */
    public enum Status {
        NEW("New"),
        IN_PROGRESS("In Progress"),
        PENDING("Pending"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled"),
        REJECTED("Rejected");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for request priorities.
     */
    public enum Priority {
        LOW("Low"),
        MEDIUM("Medium"),
        HIGH("High"),
        URGENT("Urgent");

        private final String name;

        Priority(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for request types.
     */
    public enum Type {
        INFORMATION("Information"),
        SERVICE_CHANGE("Service Change"),
        ACCOUNT_UPDATE("Account Update"),
        DOCUMENT_REQUEST("Document Request"),
        PRODUCT_INQUIRY("Product Inquiry"),
        FEEDBACK("Feedback"),
        OTHER("Other");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for request channels.
     */
    public enum Channel {
        EMAIL("Email"),
        PHONE("Phone"),
        WEB("Web"),
        MOBILE_APP("Mobile App"),
        BRANCH("Branch"),
        MAIL("Mail");

        private final String name;

        Channel(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}