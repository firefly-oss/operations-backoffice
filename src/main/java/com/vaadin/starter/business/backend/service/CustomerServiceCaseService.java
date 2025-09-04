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

import com.vaadin.starter.business.backend.dto.customerservice.CaseDTO;

import java.util.Collection;

/**
 * Service interface for managing customer service cases.
 */
public interface CustomerServiceCaseService {

    /**
     * Get all cases.
     *
     * @return Collection of all cases
     */
    Collection<CaseDTO> getCases();

    /**
     * Get a case by ID.
     *
     * @param id Case ID
     * @return Case with the given ID, or null if not found
     */
    CaseDTO getCaseById(String id);
}