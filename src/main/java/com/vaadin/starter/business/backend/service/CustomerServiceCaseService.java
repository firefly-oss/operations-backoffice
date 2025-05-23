package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.customerservice.CaseDTO;

import java.util.Collection;

/**
 * Service interface for managing customer service cases.
 */
public interface CustomerServiceCaseService {

    /**
     * Get all cases.
     *
     * @return Collection of all cases
     */
    Collection<CaseDTO> getCases();

    /**
     * Get a case by ID.
     *
     * @param id Case ID
     * @return Case with the given ID, or null if not found
     */
    CaseDTO getCaseById(String id);
}