package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.Account;

import java.util.Collection;

/**
 * Service interface for account operations.
 */
public interface AccountService {
    
    /**
     * Get all accounts.
     *
     * @return Collection of all accounts
     */
    Collection<Account> getAccounts();
    
    /**
     * Get account by account number.
     *
     * @param accountNumber the account number
     * @return the account with the given account number
     */
    Account getAccountByNumber(String accountNumber);
    
    /**
     * Save or update an account.
     *
     * @param account the account to save or update
     * @return the saved or updated account
     */
    Account saveAccount(Account account);
    
    /**
     * Block an account.
     *
     * @param accountNumber the account number to block
     * @return the blocked account
     */
    Account blockAccount(String accountNumber);
    
    /**
     * Unblock an account.
     *
     * @param accountNumber the account number to unblock
     * @return the unblocked account
     */
    Account unblockAccount(String accountNumber);
}