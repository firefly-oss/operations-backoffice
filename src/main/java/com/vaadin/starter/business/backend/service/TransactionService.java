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

import com.vaadin.starter.business.backend.Transaction;

import java.util.List;

/**
 * Service interface for managing transactions.
 */
public interface TransactionService {

    /**
     * Get all transactions.
     *
     * @return List of all transactions
     */
    List<Transaction> getTransactions();

    /**
     * Get a transaction by ID.
     *
     * @param id Transaction ID
     * @return Transaction with the given ID, or null if not found
     */
    Transaction getTransactionById(String id);
}
