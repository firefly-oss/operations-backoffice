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