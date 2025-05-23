package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.cardoperations.DisputeDTO;

import java.util.Collection;

/**
 * Service interface for card dispute operations.
 */
public interface DisputeService {
    
    /**
     * Get all disputes.
     *
     * @return Collection of all disputes
     */
    Collection<DisputeDTO> getDisputes();
    
    /**
     * Get dispute by dispute ID.
     *
     * @param disputeId the dispute ID
     * @return the dispute with the given dispute ID
     */
    DisputeDTO getDisputeById(String disputeId);
    
    /**
     * Get disputes by card number.
     *
     * @param cardNumber the card number
     * @return Collection of disputes for the given card number
     */
    Collection<DisputeDTO> getDisputesByCardNumber(String cardNumber);
    
    /**
     * Save or update a dispute.
     *
     * @param dispute the dispute to save or update
     * @return the saved or updated dispute
     */
    DisputeDTO saveDispute(DisputeDTO dispute);
    
    /**
     * Update the status of a dispute.
     *
     * @param disputeId the dispute ID
     * @param status the new status
     * @return the updated dispute
     */
    DisputeDTO updateDisputeStatus(String disputeId, String status);
    
    /**
     * Add documentation to a dispute.
     *
     * @param disputeId the dispute ID
     * @param description the documentation to add
     * @return the updated dispute
     */
    DisputeDTO addDocumentation(String disputeId, String description);
    
    /**
     * Resolve a dispute.
     *
     * @param disputeId the dispute ID
     * @return the resolved dispute
     */
    DisputeDTO resolveDispute(String disputeId);
    
    /**
     * Reject a dispute.
     *
     * @param disputeId the dispute ID
     * @return the rejected dispute
     */
    DisputeDTO rejectDispute(String disputeId);
}