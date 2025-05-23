package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.CustomerRequest;

import java.util.List;

/**
 * Service interface for managing customer requests.
 */
public interface CustomerRequestService {

    /**
     * Get all customer requests.
     *
     * @return List of all customer requests
     */
    List<CustomerRequest> getCustomerRequests();

    /**
     * Get a customer request by ID.
     *
     * @param id Customer request ID
     * @return Customer request with the given ID, or null if not found
     */
    CustomerRequest getCustomerRequestById(String id);
}