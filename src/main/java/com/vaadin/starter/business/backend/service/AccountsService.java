package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.BankAccount;

import java.util.Collection;

/**
 * Service interface for managing bank accounts.
 */
public interface AccountsService {
    
    /**
     * Get all bank accounts.
     *
     * @return a collection of all bank accounts
     */
    Collection<BankAccount> getBankAccounts();
    
    /**
     * Get a bank account by its ID.
     *
     * @param id the ID of the bank account to retrieve
     * @return the bank account with the specified ID, or null if not found
     */
    BankAccount getBankAccount(Long id);
}