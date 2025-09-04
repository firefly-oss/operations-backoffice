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

import com.vaadin.starter.business.backend.TreasuryOperation;

import java.util.List;

/**
 * Service interface for managing treasury operations.
 */
public interface TreasuryOperationService {

    /**
     * Get all treasury operations.
     *
     * @return List of all treasury operations
     */
    List<TreasuryOperation> getTreasuryOperations();

    /**
     * Get a treasury operation by ID.
     *
     * @param id Treasury operation ID
     * @return Treasury operation with the given ID, or null if not found
     */
    TreasuryOperation getTreasuryOperationById(String id);
}