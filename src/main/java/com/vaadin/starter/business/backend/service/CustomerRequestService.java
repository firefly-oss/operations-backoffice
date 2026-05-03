/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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