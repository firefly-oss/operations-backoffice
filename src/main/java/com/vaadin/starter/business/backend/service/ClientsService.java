/*
 * Copyright 2025 Firefly Software Solutions Inc
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