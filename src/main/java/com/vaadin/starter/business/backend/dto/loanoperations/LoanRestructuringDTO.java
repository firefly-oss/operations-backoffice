/*
 * Copyright 2025 Firefly Software Solutions Inc
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


package com.vaadin.starter.business.backend.dto.loanoperations;

import java.time.LocalDate;

/**
 * Data Transfer Object for loan restructuring requests.
 */
public class LoanRestructuringDTO {
    private String requestId;
    private String loanId;
    private String customerName;
    private String type;
    private String status;
    private LocalDate requestDate;
    private String assignedTo;
    private LocalDate decisionDate;

    /**
     * Default constructor.
     */
    public LoanRestructuringDTO() {
    }

    /**
     * Constructor with all fields.
     */
    public LoanRestructuringDTO(String requestId, String loanId, String customerName, String type,
                               String status, LocalDate requestDate, String assignedTo, LocalDate decisionDate) {
        this.requestId = requestId;
        this.loanId = loanId;
        this.customerName = customerName;
        this.type = type;
        this.status = status;
        this.requestDate = requestDate;
        this.assignedTo = assignedTo;
        this.decisionDate = decisionDate;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDate getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(LocalDate decisionDate) {
        this.decisionDate = decisionDate;
    }
}