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

import com.vaadin.starter.business.backend.dto.accountoperations.DirectDebitDTO;

import java.util.Collection;

/**
 * Service interface for direct debit operations.
 */
public interface DirectDebitService {
    
    /**
     * Get all direct debits.
     *
     * @return Collection of all direct debits
     */
    Collection<DirectDebitDTO> getDirectDebits();
    
    /**
     * Get direct debit by mandate ID.
     *
     * @param mandateId the mandate ID
     * @return the direct debit with the given mandate ID
     */
    DirectDebitDTO getDirectDebitById(String mandateId);
    
    /**
     * Get direct debits by account number.
     *
     * @param accountNumber the account number
     * @return Collection of direct debits for the given account number
     */
    Collection<DirectDebitDTO> getDirectDebitsByAccountNumber(String accountNumber);
    
    /**
     * Save or update a direct debit.
     *
     * @param directDebit the direct debit to save or update
     * @return the saved or updated direct debit
     */
    DirectDebitDTO saveDirectDebit(DirectDebitDTO directDebit);
    
    /**
     * Suspend a direct debit.
     *
     * @param mandateId the mandate ID to suspend
     * @return the suspended direct debit
     */
    DirectDebitDTO suspendDirectDebit(String mandateId);
    
    /**
     * Activate a direct debit.
     *
     * @param mandateId the mandate ID to activate
     * @return the activated direct debit
     */
    DirectDebitDTO activateDirectDebit(String mandateId);
    
    /**
     * Cancel a direct debit.
     *
     * @param mandateId the mandate ID to cancel
     * @return the cancelled direct debit
     */
    DirectDebitDTO cancelDirectDebit(String mandateId);
}