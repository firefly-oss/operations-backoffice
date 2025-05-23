package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.cardoperations.ReplacementRequestDTO;

import java.util.Collection;

/**
 * Service interface for card replacement request operations.
 */
public interface ReplacementRequestService {
    
    /**
     * Get all replacement requests.
     *
     * @return Collection of all replacement requests
     */
    Collection<ReplacementRequestDTO> getReplacementRequests();
    
    /**
     * Get replacement request by request ID.
     *
     * @param requestId the request ID
     * @return the replacement request with the given request ID
     */
    ReplacementRequestDTO getReplacementRequestById(String requestId);
    
    /**
     * Get replacement requests by card number.
     *
     * @param cardNumber the card number
     * @return Collection of replacement requests for the given card number
     */
    Collection<ReplacementRequestDTO> getReplacementRequestsByCardNumber(String cardNumber);
    
    /**
     * Save or update a replacement request.
     *
     * @param request the replacement request to save or update
     * @return the saved or updated replacement request
     */
    ReplacementRequestDTO saveReplacementRequest(ReplacementRequestDTO request);
    
    /**
     * Update the status of a replacement request.
     *
     * @param requestId the request ID
     * @param status the new status
     * @return the updated replacement request
     */
    ReplacementRequestDTO updateRequestStatus(String requestId, String status);
    
    /**
     * Add notes to a replacement request.
     *
     * @param requestId the request ID
     * @param notes the notes to add
     * @return the updated replacement request
     */
    ReplacementRequestDTO addNotes(String requestId, String notes);
    
    /**
     * Issue a new card for a replacement request.
     *
     * @param requestId the request ID
     * @param newCardNumber the new card number
     * @return the updated replacement request
     */
    ReplacementRequestDTO issueNewCard(String requestId, String newCardNumber);
    
    /**
     * Complete a replacement request.
     *
     * @param requestId the request ID
     * @return the completed replacement request
     */
    ReplacementRequestDTO completeRequest(String requestId);
    
    /**
     * Reject a replacement request.
     *
     * @param requestId the request ID
     * @return the rejected replacement request
     */
    ReplacementRequestDTO rejectRequest(String requestId);
}