package com.vaadin.starter.business.backend.dto.transactions;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for transaction details.
 */
public class TransactionDetailsDTO {
    private String id;
    private String type;
    private String status;
    private double amount;
    private String currency;
    private String sourceAccount;
    private String destinationAccount;
    private LocalDateTime timestamp;
    private String reference;
    private String description;
    private List<TransactionEventDTO> events;
    private List<TransactionNoteDTO> notes;

    public TransactionDetailsDTO() {
    }

    public TransactionDetailsDTO(String id, String type, String status, double amount, String currency,
                               String sourceAccount, String destinationAccount, LocalDateTime timestamp,
                               String reference, String description, List<TransactionEventDTO> events,
                               List<TransactionNoteDTO> notes) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.currency = currency;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.timestamp = timestamp;
        this.reference = reference;
        this.description = description;
        this.events = events;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TransactionEventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<TransactionEventDTO> events) {
        this.events = events;
    }

    public List<TransactionNoteDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<TransactionNoteDTO> notes) {
        this.notes = notes;
    }

    /**
     * Data Transfer Object for transaction events.
     */
    public static class TransactionEventDTO {
        private LocalDateTime timestamp;
        private String event;
        private String user;

        public TransactionEventDTO() {
        }

        public TransactionEventDTO(LocalDateTime timestamp, String event, String user) {
            this.timestamp = timestamp;
            this.event = event;
            this.user = user;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }

    /**
     * Data Transfer Object for transaction notes.
     */
    public static class TransactionNoteDTO {
        private LocalDateTime timestamp;
        private String note;
        private String user;

        public TransactionNoteDTO() {
        }

        public TransactionNoteDTO(LocalDateTime timestamp, String note, String user) {
            this.timestamp = timestamp;
            this.note = note;
            this.user = user;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }
}