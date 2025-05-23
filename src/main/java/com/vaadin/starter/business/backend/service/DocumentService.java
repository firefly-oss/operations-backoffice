package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.documentmanagement.AuthorizationDTO;
import com.vaadin.starter.business.backend.dto.documentmanagement.DocumentDTO;
import com.vaadin.starter.business.backend.dto.documentmanagement.DocumentTrackingEntryDTO;
import com.vaadin.starter.business.backend.dto.documentmanagement.SignatureDTO;
import com.vaadin.starter.business.backend.dto.documentmanagement.TransactionDocumentDTO;

import java.util.Collection;

/**
 * Service interface for managing documents.
 */
public interface DocumentService {

    /**
     * Get all documents.
     *
     * @return Collection of all documents
     */
    Collection<DocumentDTO> getDocuments();

    /**
     * Get a document by ID.
     *
     * @param id Document ID
     * @return Document with the given ID, or null if not found
     */
    DocumentDTO getDocumentById(String id);

    /**
     * Get all document tracking entries.
     *
     * @return Collection of all document tracking entries
     */
    Collection<DocumentTrackingEntryDTO> getDocumentTrackingEntries();

    /**
     * Get a document tracking entry by ID.
     *
     * @param id Document tracking entry ID
     * @return Document tracking entry with the given ID, or null if not found
     */
    DocumentTrackingEntryDTO getDocumentTrackingEntryById(String id);

    /**
     * Get all signatures.
     *
     * @return Collection of all signatures
     */
    Collection<SignatureDTO> getSignatures();

    /**
     * Get a signature by ID.
     *
     * @param id Signature ID
     * @return Signature with the given ID, or null if not found
     */
    SignatureDTO getSignatureById(String id);

    /**
     * Get all authorizations.
     *
     * @return Collection of all authorizations
     */
    Collection<AuthorizationDTO> getAuthorizations();

    /**
     * Get an authorization by ID.
     *
     * @param id Authorization ID
     * @return Authorization with the given ID, or null if not found
     */
    AuthorizationDTO getAuthorizationById(String id);

    /**
     * Get all transaction documents.
     *
     * @return Collection of all transaction documents
     */
    Collection<TransactionDocumentDTO> getTransactionDocuments();

    /**
     * Get a transaction document by ID.
     *
     * @param id Transaction document ID
     * @return Transaction document with the given ID, or null if not found
     */
    TransactionDocumentDTO getTransactionDocumentById(String id);
}
