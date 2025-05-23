package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.Account;
import com.vaadin.starter.business.backend.service.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the AccountService interface.
 * Provides dummy data for demonstration purposes.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final Map<String, Account> accounts = new HashMap<>();

    public AccountServiceImpl() {
        // Initialize with dummy data
        initDummyData();
    }

    @Override
    public Collection<Account> getAccounts() {
        return accounts.values();
    }

    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accounts.get(accountNumber);
    }

    @Override
    public Account saveAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    @Override
    public Account blockAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.setStatus(Account.Status.BLOCKED.getName());
            accounts.put(accountNumber, account);
        }
        return account;
    }

    @Override
    public Account unblockAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.setStatus(Account.Status.ACTIVE.getName());
            accounts.put(accountNumber, account);
        }
        return account;
    }

    private void initDummyData() {
        List<Account> dummyAccounts = List.of(
            new Account("ACC001", "Checking Account", "Checking", "Active", "CUST001", 5000.0, LocalDate.of(2020, 1, 15)),
            new Account("ACC002", "Savings Account", "Savings", "Active", "CUST001", 15000.0, LocalDate.of(2020, 2, 20)),
            new Account("ACC003", "Credit Card", "Credit Card", "Active", "CUST002", -2500.0, LocalDate.of(2021, 3, 10)),
            new Account("ACC004", "Home Loan", "Loan", "Active", "CUST003", -150000.0, LocalDate.of(2019, 5, 5)),
            new Account("ACC005", "Investment Portfolio", "Investment", "Active", "CUST004", 75000.0, LocalDate.of(2018, 7, 22)),
            new Account("ACC006", "Business Account", "Checking", "Blocked", "CUST005", 25000.0, LocalDate.of(2021, 9, 30)),
            new Account("ACC007", "Student Loan", "Loan", "Active", "CUST006", -35000.0, LocalDate.of(2020, 8, 15)),
            new Account("ACC008", "Retirement Fund", "Investment", "Active", "CUST007", 250000.0, LocalDate.of(2015, 1, 1)),
            new Account("ACC009", "Joint Checking", "Checking", "Active", "CUST008", 7500.0, LocalDate.of(2022, 2, 14)),
            new Account("ACC010", "Dormant Account", "Savings", "Inactive", "CUST009", 100.0, LocalDate.of(2010, 10, 10)),
            new Account("ACC011", "Premium Checking", "Checking", "Active", "CUST010", 12500.0, LocalDate.of(2021, 4, 1)),
            new Account("ACC012", "Fixed Deposit", "Savings", "Active", "CUST011", 50000.0, LocalDate.of(2019, 11, 11)),
            new Account("ACC013", "Car Loan", "Loan", "Active", "CUST012", -25000.0, LocalDate.of(2022, 1, 5)),
            new Account("ACC014", "Gold Investment", "Investment", "Active", "CUST013", 35000.0, LocalDate.of(2020, 6, 15)),
            new Account("ACC015", "Corporate Account", "Checking", "Active", "CUST014", 175000.0, LocalDate.of(2018, 3, 20)),
            new Account("ACC016", "Education Savings", "Savings", "Active", "CUST015", 45000.0, LocalDate.of(2017, 9, 1)),
            new Account("ACC017", "Platinum Credit Card", "Credit Card", "Blocked", "CUST016", -7500.0, LocalDate.of(2021, 7, 12)),
            new Account("ACC018", "Mortgage", "Loan", "Active", "CUST017", -350000.0, LocalDate.of(2015, 5, 20)),
            new Account("ACC019", "Stock Portfolio", "Investment", "Active", "CUST018", 125000.0, LocalDate.of(2019, 2, 28)),
            new Account("ACC020", "Youth Savings", "Savings", "Active", "CUST019", 2500.0, LocalDate.of(2022, 3, 15))
        );

        for (Account account : dummyAccounts) {
            accounts.put(account.getAccountNumber(), account);
        }
    }
}