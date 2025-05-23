package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.accountoperations.DirectDebitDTO;
import com.vaadin.starter.business.backend.service.DirectDebitService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of the DirectDebitService interface.
 * Provides dummy data for demonstration purposes.
 */
@Service
public class DirectDebitServiceImpl implements DirectDebitService {

    private final Map<String, DirectDebitDTO> directDebits = new HashMap<>();

    public DirectDebitServiceImpl() {
        // Initialize with dummy data
        initDummyData();
    }

    @Override
    public Collection<DirectDebitDTO> getDirectDebits() {
        return directDebits.values();
    }

    @Override
    public DirectDebitDTO getDirectDebitById(String mandateId) {
        return directDebits.get(mandateId);
    }

    @Override
    public Collection<DirectDebitDTO> getDirectDebitsByAccountNumber(String accountNumber) {
        return directDebits.values().stream()
                .filter(debit -> debit.getAccountNumber().equals(accountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public DirectDebitDTO saveDirectDebit(DirectDebitDTO directDebit) {
        directDebits.put(directDebit.getMandateId(), directDebit);
        return directDebit;
    }

    @Override
    public DirectDebitDTO suspendDirectDebit(String mandateId) {
        DirectDebitDTO debit = directDebits.get(mandateId);
        if (debit != null && debit.getStatus().equals(DirectDebitDTO.Status.ACTIVE.getName())) {
            debit.setStatus(DirectDebitDTO.Status.SUSPENDED.getName());
            directDebits.put(mandateId, debit);
        }
        return debit;
    }

    @Override
    public DirectDebitDTO activateDirectDebit(String mandateId) {
        DirectDebitDTO debit = directDebits.get(mandateId);
        if (debit != null && debit.getStatus().equals(DirectDebitDTO.Status.SUSPENDED.getName())) {
            debit.setStatus(DirectDebitDTO.Status.ACTIVE.getName());
            directDebits.put(mandateId, debit);
        }
        return debit;
    }

    @Override
    public DirectDebitDTO cancelDirectDebit(String mandateId) {
        DirectDebitDTO debit = directDebits.get(mandateId);
        if (debit != null && !debit.getStatus().equals(DirectDebitDTO.Status.CANCELLED.getName())) {
            debit.setStatus(DirectDebitDTO.Status.CANCELLED.getName());
            directDebits.put(mandateId, debit);
        }
        return debit;
    }

    private void initDummyData() {
        List<DirectDebitDTO> dummyDebits = new ArrayList<>();
        
        dummyDebits.add(new DirectDebitDTO("DD001", "ACC001", "Electric Company", "CRED001", 85.0, "Monthly", LocalDate.now().plusDays(5), "Active"));
        dummyDebits.add(new DirectDebitDTO("DD002", "ACC002", "Water Utility", "CRED002", 45.0, "Monthly", LocalDate.now().plusDays(10), "Active"));
        dummyDebits.add(new DirectDebitDTO("DD003", "ACC003", "Internet Provider", "CRED003", 60.0, "Monthly", LocalDate.now().plusDays(15), "Active"));
        dummyDebits.add(new DirectDebitDTO("DD004", "ACC004", "Mobile Phone", "CRED004", 35.0, "Monthly", LocalDate.now().plusDays(20), "Active"));
        dummyDebits.add(new DirectDebitDTO("DD005", "ACC005", "Gym Membership", "CRED005", 50.0, "Monthly", LocalDate.now().plusDays(25), "Active"));
        dummyDebits.add(new DirectDebitDTO("DD006", "ACC006", "Magazine Subscription", "CRED006", 15.0, "Monthly", LocalDate.now().plusDays(7), "Suspended"));
        dummyDebits.add(new DirectDebitDTO("DD007", "ACC007", "Charity Donation", "CRED007", 20.0, "Monthly", LocalDate.now().plusDays(12), "Active"));
        dummyDebits.add(new DirectDebitDTO("DD008", "ACC008", "Insurance Premium", "CRED008", 75.0, "Monthly", LocalDate.now().plusDays(18), "Active"));
        dummyDebits.add(new DirectDebitDTO("DD009", "ACC009", "TV License", "CRED009", 13.0, "Monthly", LocalDate.now().plusDays(22), "Active"));
        dummyDebits.add(new DirectDebitDTO("DD010", "ACC010", "Old Service", "CRED010", 25.0, "Monthly", LocalDate.now().plusDays(30), "Cancelled"));

        for (DirectDebitDTO debit : dummyDebits) {
            directDebits.put(debit.getMandateId(), debit);
        }
    }
}