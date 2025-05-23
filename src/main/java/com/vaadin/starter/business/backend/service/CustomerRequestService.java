package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.customerservice.CustomerRequestDTO;

import java.util.Collection;

/**
 * Service interface for managing customer requests.
 */
public interface CustomerRequestService {

    /**
     * Get all customer requests.
     *
     * @return Collection of all customer requests
     */
    Collection<CustomerRequestDTO> getCustomerRequests();

    /**
     * Get a customer request by ID.
     *
     * @param id Customer request ID
     * @return Customer request with the given ID, or null if not found
     */
    CustomerRequestDTO getCustomerRequestById(String id);
}