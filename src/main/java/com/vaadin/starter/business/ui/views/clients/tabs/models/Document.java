package com.vaadin.starter.business.ui.views.clients.tabs.models;

import java.time.LocalDate;

/**
 * Data model for a client document.
 */
public class Document {
    private String documentId;
    private String documentName;
    private String documentType;
    private String size;
    private String status;
    private LocalDate uploadDate;
    private String uploadedBy;
    private String description;

    /**
     * Constructor for Document.
     *
     * @param documentId The document ID
     * @param documentName The document name
     * @param documentType The document type
     * @param size The document size
     * @param status The document status
     * @param uploadDate The upload date
     * @param uploadedBy The person who uploaded the document
     * @param description The document description
     */
    public Document(String documentId, String documentName, String documentType, String size, 
                   String status, LocalDate uploadDate, String uploadedBy, String description) {
        this.documentId = documentId;
        this.documentName = documentName;
        this.documentType = documentType;
        this.size = size;
        this.status = status;
        this.uploadDate = uploadDate;
        this.uploadedBy = uploadedBy;
        this.description = description;
    }

    public String getDocumentId() { return documentId; }
    public String getDocumentName() { return documentName; }
    public String getDocumentType() { return documentType; }
    public String getSize() { return size; }
    public String getStatus() { return status; }
    public LocalDate getUploadDate() { return uploadDate; }
    public String getUploadedBy() { return uploadedBy; }
    public String getDescription() { return description; }
}