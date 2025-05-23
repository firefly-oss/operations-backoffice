package com.vaadin.starter.business.backend;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a service ticket in the system.
 */
public class ServiceTicket {
    private String id;
    private String ticketNumber;
    private String subject;
    private String description;
    private String status;
    private String priority;
    private String category;
    private String serviceType;
    private String customerId;
    private String customerName;
    private String assignedTo;
    private String department;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private LocalDateTime dueDate;
    private LocalDateTime resolvedDate;
    private String resolution;

    public ServiceTicket() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }

    public ServiceTicket(String id, String ticketNumber, String subject, String description, String status,
                       String priority, String category, String serviceType, String customerId,
                       String customerName, String assignedTo, String department, LocalDateTime createdDate,
                       LocalDateTime lastUpdatedDate, LocalDateTime dueDate, LocalDateTime resolvedDate,
                       String resolution) {
        this.id = id;
        this.ticketNumber = ticketNumber;
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.category = category;
        this.serviceType = serviceType;
        this.customerId = customerId;
        this.customerName = customerName;
        this.assignedTo = assignedTo;
        this.department = department;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.dueDate = dueDate;
        this.resolvedDate = resolvedDate;
        this.resolution = resolution;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    /**
     * Enum for ticket statuses.
     */
    public enum Status {
        OPEN("Open"),
        IN_PROGRESS("In Progress"),
        WAITING_FOR_CUSTOMER("Waiting for Customer"),
        WAITING_FOR_THIRD_PARTY("Waiting for Third Party"),
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
     * Enum for ticket priorities.
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
     * Enum for ticket categories.
     */
    public enum Category {
        ACCOUNT_SERVICES("Account Services"),
        TECHNICAL_SUPPORT("Technical Support"),
        BILLING_SUPPORT("Billing Support"),
        PRODUCT_SUPPORT("Product Support"),
        GENERAL_INQUIRY("General Inquiry"),
        COMPLAINT("Complaint"),
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
     * Enum for service types.
     */
    public enum ServiceType {
        INSTALLATION("Installation"),
        REPAIR("Repair"),
        MAINTENANCE("Maintenance"),
        UPGRADE("Upgrade"),
        CONSULTATION("Consultation"),
        TRAINING("Training"),
        TROUBLESHOOTING("Troubleshooting");

        private final String name;

        ServiceType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Enum for departments.
     */
    public enum Department {
        CUSTOMER_SERVICE("Customer Service"),
        TECHNICAL_SUPPORT("Technical Support"),
        BILLING("Billing"),
        SALES("Sales"),
        PRODUCT_MANAGEMENT("Product Management"),
        OPERATIONS("Operations");

        private final String name;

        Department(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}