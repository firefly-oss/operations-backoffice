package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.Client;

import java.util.Collection;

/**
 * Service interface for managing clients.
 */
public interface ClientsService {
    
    /**
     * Get all clients.
     *
     * @return a collection of all clients
     */
    Collection<Client> getClients();
    
    /**
     * Get a client by its ID.
     *
     * @param id the ID of the client to retrieve
     * @return the client with the specified ID, or null if not found
     */
    Client getClient(Long id);
}