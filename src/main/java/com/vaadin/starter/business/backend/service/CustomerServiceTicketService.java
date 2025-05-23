package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.customerservice.ServiceTicketDTO;

import java.util.List;

/**
 * Service interface for managing customer service tickets.
 */
public interface CustomerServiceTicketService {

    /**
     * Get all service tickets.
     *
     * @return List of all service tickets
     */
    List<ServiceTicketDTO> getServiceTickets();

    /**
     * Get a service ticket by ID.
     *
     * @param id Service ticket ID
     * @return Service ticket with the given ID, or null if not found
     */
    ServiceTicketDTO getServiceTicketById(String id);
}
