package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.CashPosition;

import java.util.List;

/**
 * Service interface for managing cash positions.
 */
public interface CashPositionService {

    /**
     * Get all cash positions.
     *
     * @return List of all cash positions
     */
    List<CashPosition> getCashPositions();

    /**
     * Get a cash position by ID.
     *
     * @param id Cash position ID
     * @return Cash position with the given ID, or null if not found
     */
    CashPosition getCashPositionById(String id);
}