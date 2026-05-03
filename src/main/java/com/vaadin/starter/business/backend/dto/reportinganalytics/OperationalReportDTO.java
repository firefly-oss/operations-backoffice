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


package com.vaadin.starter.business.backend.dto.reportinganalytics;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for operational reports.
 */
public class OperationalReportDTO {
    private String reportId;
    private String reportName;
    private String category;
    private String frequency;
    private LocalDateTime lastGenerated;
    private String createdBy;
    private String description;

    /**
     * Default constructor.
     */
    public OperationalReportDTO() {
    }

    /**
     * Constructor with all fields.
     */
    public OperationalReportDTO(String reportId, String reportName, String category, String frequency,
                               LocalDateTime lastGenerated, String createdBy, String description) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.category = category;
        this.frequency = frequency;
        this.lastGenerated = lastGenerated;
        this.createdBy = createdBy;
        this.description = description;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalDateTime getLastGenerated() {
        return lastGenerated;
    }

    public void setLastGenerated(LocalDateTime lastGenerated) {
        this.lastGenerated = lastGenerated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}