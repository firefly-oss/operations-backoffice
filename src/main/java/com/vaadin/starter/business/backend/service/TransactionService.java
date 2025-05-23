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
