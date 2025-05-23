package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.cardoperations.DisputeDTO;
import com.vaadin.starter.business.backend.service.DisputeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of the DisputeService interface.
 * Provides dummy data for demonstration purposes.
 */
@Service
public class DisputeServiceImpl implements DisputeService {

    private final Map<String, DisputeDTO> disputes = new HashMap<>();

    public DisputeServiceImpl() {
        // Initialize with dummy data
        initDummyData();
    }

    @Override
    public Collection<DisputeDTO> getDisputes() {
        return disputes.values();
    }

    @Override
    public DisputeDTO getDisputeById(String disputeId) {
        return disputes.get(disputeId);
    }

    @Override
    public Collection<DisputeDTO> getDisputesByCardNumber(String cardNumber) {
        return disputes.values().stream()
                .filter(dispute -> dispute.getCardNumber().equals(cardNumber))
                .collect(Collectors.toList());
    }

    @Override
    public DisputeDTO saveDispute(DisputeDTO dispute) {
        disputes.put(dispute.getDisputeId(), dispute);
        return dispute;
    }

    @Override
    public DisputeDTO updateDisputeStatus(String disputeId, String status) {
        DisputeDTO dispute = disputes.get(disputeId);
        if (dispute != null) {
            dispute.setStatus(status);
            disputes.put(disputeId, dispute);
        }
        return dispute;
    }

    @Override
    public DisputeDTO addDocumentation(String disputeId, String description) {
        DisputeDTO dispute = disputes.get(disputeId);
        if (dispute != null) {
            dispute.setDescription(description);
            disputes.put(disputeId, dispute);
        }
        return dispute;
    }

    @Override
    public DisputeDTO resolveDispute(String disputeId) {
        return updateDisputeStatus(disputeId, DisputeDTO.Status.RESOLVED.getName());
    }

    @Override
    public DisputeDTO rejectDispute(String disputeId) {
        return updateDisputeStatus(disputeId, DisputeDTO.Status.REJECTED.getName());
    }

    private void initDummyData() {
        List<DisputeDTO> dummyDisputes = new ArrayList<>();

        dummyDisputes.add(new DisputeDTO("DSP001", "4532123456781234", "John Smith", "In Progress", "Unauthorized Transaction", 125.50, LocalDate.now().minusDays(5), "Online Store Inc."));
        dummyDisputes.add(new DisputeDTO("DSP002", "4532123456781235", "John Smith", "Resolved", "Duplicate Charge", 75.00, LocalDate.now().minusDays(15), "Subscription Service"));
        dummyDisputes.add(new DisputeDTO("DSP003", "4532123456781236", "Jane Doe", "Pending Documentation", "Merchandise Not Received", 349.99, LocalDate.now().minusDays(3), "Electronics Shop"));
        dummyDisputes.add(new DisputeDTO("DSP004", "4532123456781237", "Jane Doe", "Rejected", "Incorrect Amount", 50.00, LocalDate.now().minusDays(20), "Restaurant Chain"));
        dummyDisputes.add(new DisputeDTO("DSP005", "4532123456781238", "Robert Johnson", "New", "Defective Merchandise", 199.95, LocalDate.now().minusDays(1), "Furniture Store"));
        dummyDisputes.add(new DisputeDTO("DSP006", "4532123456781239", "Sarah Williams", "In Progress", "Unauthorized Transaction", 1250.00, LocalDate.now().minusDays(7), "Travel Agency"));
        dummyDisputes.add(new DisputeDTO("DSP007", "4532123456781240", "Michael Brown", "Pending Documentation", "Other", 89.99, LocalDate.now().minusDays(10), "Streaming Service"));
        dummyDisputes.add(new DisputeDTO("DSP008", "4532123456781241", "Emily Davis", "Resolved", "Duplicate Charge", 45.50, LocalDate.now().minusDays(25), "Gas Station"));
        dummyDisputes.add(new DisputeDTO("DSP009", "4532123456781242", "David Miller", "In Progress", "Merchandise Not Received", 799.00, LocalDate.now().minusDays(8), "Computer Store"));
        dummyDisputes.add(new DisputeDTO("DSP010", "4532123456781243", "Jennifer Wilson", "Rejected", "Incorrect Amount", 120.75, LocalDate.now().minusDays(18), "Department Store"));

        for (DisputeDTO dispute : dummyDisputes) {
            disputes.put(dispute.getDisputeId(), dispute);
        }
    }
}