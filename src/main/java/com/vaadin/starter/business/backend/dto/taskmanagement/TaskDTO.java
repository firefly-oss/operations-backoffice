/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.vaadin.starter.business.backend.dto.taskmanagement;

import java.time.LocalDate;

/**
 * Data Transfer Object for Task.
 */
public class TaskDTO {

    private String id;
    private String subject;
    private String status;
    private String priority;
    private String type;
    private String assignee;
    private LocalDate dueDate;
    private String customer;

    /**
     * Default constructor.
     */
    public TaskDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id        the task ID
     * @param subject   the task subject
     * @param status    the task status
     * @param priority  the task priority
     * @param type      the task type
     * @param assignee  the task assignee
     * @param dueDate   the task due date
     * @param customer  the task customer
     */
    public TaskDTO(String id, String subject, String status, String priority, 
                 String type, String assignee, LocalDate dueDate, String customer) {
        this.id = id;
        this.subject = subject;
        this.status = status;
        this.priority = priority;
        this.type = type;
        this.assignee = assignee;
        this.dueDate = dueDate;
        this.customer = customer;
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

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}