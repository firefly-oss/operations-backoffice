package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.Case;

import java.util.List;

/**
 * Service interface for managing cases.
 */
public interface CaseService {

    /**
     * Get all cases.
     *
     * @return List of all cases
     */
    List<Case> getCases();

    /**
     * Get a case by ID.
     *
     * @param id Case ID
     * @return Case with the given ID, or null if not found
     */
    Case getCaseById(String id);
}