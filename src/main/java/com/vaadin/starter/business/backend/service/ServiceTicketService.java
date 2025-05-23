package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.ServiceTicket;

import java.util.List;

/**
 * Service interface for managing service tickets.
 */
public interface ServiceTicketService {

    /**
     * Get all service tickets.
     *
     * @return List of all service tickets
     */
    List<ServiceTicket> getServiceTickets();

    /**
     * Get a service ticket by ID.
     *
     * @param id Service ticket ID
     * @return Service ticket with the given ID, or null if not found
     */
    ServiceTicket getServiceTicketById(String id);
}