package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.TreasuryOperation;

import java.util.List;

/**
 * Service interface for managing treasury operations.
 */
public interface TreasuryOperationService {

    /**
     * Get all treasury operations.
     *
     * @return List of all treasury operations
     */
    List<TreasuryOperation> getTreasuryOperations();

    /**
     * Get a treasury operation by ID.
     *
     * @param id Treasury operation ID
     * @return Treasury operation with the given ID, or null if not found
     */
    TreasuryOperation getTreasuryOperationById(String id);
}