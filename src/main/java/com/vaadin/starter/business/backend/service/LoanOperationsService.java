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

import com.vaadin.starter.business.backend.dto.loanoperations.LoanApplicationDTO;
import com.vaadin.starter.business.backend.dto.loanoperations.LoanCollectionDTO;
import com.vaadin.starter.business.backend.dto.loanoperations.LoanDisbursementDTO;
import com.vaadin.starter.business.backend.dto.loanoperations.LoanRestructuringDTO;

import java.util.Collection;

/**
 * Service interface for loan operations.
 */
public interface LoanOperationsService {

    /**
     * Get all loan applications.
     *
     * @return Collection of all loan applications
     */
    Collection<LoanApplicationDTO> getLoanApplications();

    /**
     * Get a loan application by ID.
     *
     * @param id Loan application ID
     * @return Loan application with the given ID, or null if not found
     */
    LoanApplicationDTO getLoanApplicationById(String id);

    /**
     * Get all loan collections.
     *
     * @return Collection of all loan collections
     */
    Collection<LoanCollectionDTO> getLoanCollections();

    /**
     * Get a loan collection by ID.
     *
     * @param id Loan collection ID
     * @return Loan collection with the given ID, or null if not found
     */
    LoanCollectionDTO getLoanCollectionById(String id);

    /**
     * Get all loan disbursements.
     *
     * @return Collection of all loan disbursements
     */
    Collection<LoanDisbursementDTO> getLoanDisbursements();

    /**
     * Get a loan disbursement by ID.
     *
     * @param id Loan disbursement ID
     * @return Loan disbursement with the given ID, or null if not found
     */
    LoanDisbursementDTO getLoanDisbursementById(String id);

    /**
     * Get all loan restructurings.
     *
     * @return Collection of all loan restructurings
     */
    Collection<LoanRestructuringDTO> getLoanRestructurings();

    /**
     * Get a loan restructuring by ID.
     *
     * @param id Loan restructuring ID
     * @return Loan restructuring with the given ID, or null if not found
     */
    LoanRestructuringDTO getLoanRestructuringById(String id);
}