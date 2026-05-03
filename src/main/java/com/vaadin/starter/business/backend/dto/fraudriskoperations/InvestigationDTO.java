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


package com.vaadin.starter.business.backend.dto.fraudriskoperations;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for fraud investigations.
 */
public class InvestigationDTO {
    private String caseId;
    private String subject;
    private String type;
    private String status;
    private String priority;
    private String assignedTo;
    private LocalDateTime openedDate;
    private LocalDateTime lastUpdated;
    private String description;

    /**
     * Default constructor.
     */
    public InvestigationDTO() {
    }

    /**
     * Constructor with all fields.
     */
    public InvestigationDTO(String caseId, String subject, String type, String status, String priority,
                           String assignedTo, LocalDateTime openedDate, LocalDateTime lastUpdated,
                           String description) {
        this.caseId = caseId;
        this.subject = subject;
        this.type = type;
        this.status = status;
        this.priority = priority;
        this.assignedTo = assignedTo;
        this.openedDate = openedDate;
        this.lastUpdated = lastUpdated;
        this.description = description;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDateTime getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(LocalDateTime openedDate) {
        this.openedDate = openedDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}