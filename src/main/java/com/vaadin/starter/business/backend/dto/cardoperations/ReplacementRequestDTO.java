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


package com.vaadin.starter.business.backend.dto.cardoperations;

import java.time.LocalDate;

/**
 * Data Transfer Object for Card Replacement Request information.
 */
public class ReplacementRequestDTO {
    private String requestId;
    private String cardNumber;
    private String cardHolderName;
    private String status;
    private String reason;
    private LocalDate requestDate;
    private LocalDate completionDate;
    private String newCardNumber;
    private String notes;

    public ReplacementRequestDTO(String requestId, String cardNumber, String cardHolderName, String status, 
                               String reason, LocalDate requestDate, LocalDate completionDate, String newCardNumber) {
        this.requestId = requestId;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.status = status;
        this.reason = reason;
        this.requestDate = requestDate;
        this.completionDate = completionDate;
        this.newCardNumber = newCardNumber;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public String getNewCardNumber() {
        return newCardNumber;
    }

    public void setNewCardNumber(String newCardNumber) {
        this.newCardNumber = newCardNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    /**
     * Enum for replacement request statuses
     */
    public enum Status {
        NEW("New"),
        IN_PROGRESS("In Progress"),
        CARD_ISSUED("Card Issued"),
        COMPLETED("Completed"),
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
     * Enum for replacement request reasons
     */
    public enum Reason {
        LOST("Lost"),
        STOLEN("Stolen"),
        DAMAGED("Damaged"),
        EXPIRED("Expired"),
        COMPROMISED("Compromised"),
        NAME_CHANGE("Name Change"),
        OTHER("Other");

        private final String name;

        Reason(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}