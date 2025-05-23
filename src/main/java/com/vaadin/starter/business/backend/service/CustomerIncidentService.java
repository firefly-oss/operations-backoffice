package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.customerservice.IncidentDTO;

import java.util.List;

/**
 * Service interface for managing customer incidents.
 */
public interface CustomerIncidentService {

    /**
     * Get all incidents.
     *
     * @return List of all incidents
     */
    List<IncidentDTO> getIncidents();

    /**
     * Get an incident by ID.
     *
     * @param id Incident ID
     * @return Incident with the given ID, or null if not found
     */
    IncidentDTO getIncidentById(String id);
}