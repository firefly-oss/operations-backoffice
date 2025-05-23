package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.cardoperations.ReplacementRequestDTO;
import com.vaadin.starter.business.backend.service.ReplacementRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of the ReplacementRequestService interface.
 * Provides dummy data for demonstration purposes.
 */
@Service
public class ReplacementRequestServiceImpl implements ReplacementRequestService {

    private final Map<String, ReplacementRequestDTO> replacementRequests = new HashMap<>();

    public ReplacementRequestServiceImpl() {
        // Initialize with dummy data
        initDummyData();
    }

    @Override
    public Collection<ReplacementRequestDTO> getReplacementRequests() {
        return replacementRequests.values();
    }

    @Override
    public ReplacementRequestDTO getReplacementRequestById(String requestId) {
        return replacementRequests.get(requestId);
    }

    @Override
    public Collection<ReplacementRequestDTO> getReplacementRequestsByCardNumber(String cardNumber) {
        return replacementRequests.values().stream()
                .filter(request -> request.getCardNumber().equals(cardNumber))
                .collect(Collectors.toList());
    }

    @Override
    public ReplacementRequestDTO saveReplacementRequest(ReplacementRequestDTO request) {
        replacementRequests.put(request.getRequestId(), request);
        return request;
    }

    @Override
    public ReplacementRequestDTO updateRequestStatus(String requestId, String status) {
        ReplacementRequestDTO request = replacementRequests.get(requestId);
        if (request != null) {
            request.setStatus(status);
            replacementRequests.put(requestId, request);
        }
        return request;
    }

    @Override
    public ReplacementRequestDTO addNotes(String requestId, String notes) {
        ReplacementRequestDTO request = replacementRequests.get(requestId);
        if (request != null) {
            request.setNotes(notes);
            replacementRequests.put(requestId, request);
        }
        return request;
    }

    @Override
    public ReplacementRequestDTO issueNewCard(String requestId, String newCardNumber) {
        ReplacementRequestDTO request = replacementRequests.get(requestId);
        if (request != null) {
            request.setNewCardNumber(newCardNumber);
            request.setStatus(ReplacementRequestDTO.Status.CARD_ISSUED.getName());
            replacementRequests.put(requestId, request);
        }
        return request;
    }

    @Override
    public ReplacementRequestDTO completeRequest(String requestId) {
        ReplacementRequestDTO request = replacementRequests.get(requestId);
        if (request != null) {
            request.setStatus(ReplacementRequestDTO.Status.COMPLETED.getName());
            request.setCompletionDate(LocalDate.now());
            replacementRequests.put(requestId, request);
        }
        return request;
    }

    @Override
    public ReplacementRequestDTO rejectRequest(String requestId) {
        ReplacementRequestDTO request = replacementRequests.get(requestId);
        if (request != null) {
            request.setStatus(ReplacementRequestDTO.Status.REJECTED.getName());
            request.setCompletionDate(LocalDate.now());
            replacementRequests.put(requestId, request);
        }
        return request;
    }

    private void initDummyData() {
        List<ReplacementRequestDTO> dummyRequests = new ArrayList<>();
        
        dummyRequests.add(new ReplacementRequestDTO("REP001", "4532123456781234", "John Smith", "In Progress", "Lost", 
                LocalDate.now().minusDays(5), null, null));
        dummyRequests.add(new ReplacementRequestDTO("REP002", "4532123456781235", "John Smith", "Completed", "Expired", 
                LocalDate.now().minusDays(15), LocalDate.now().minusDays(10), "4532123456789876"));
        dummyRequests.add(new ReplacementRequestDTO("REP003", "4532123456781236", "Jane Doe", "New", "Damaged", 
                LocalDate.now().minusDays(2), null, null));
        dummyRequests.add(new ReplacementRequestDTO("REP004", "4532123456781237", "Jane Doe", "Rejected", "Other", 
                LocalDate.now().minusDays(20), LocalDate.now().minusDays(18), null));
        dummyRequests.add(new ReplacementRequestDTO("REP005", "4532123456781238", "Robert Johnson", "Card Issued", "Stolen", 
                LocalDate.now().minusDays(8), LocalDate.now().minusDays(3), "4532123456789877"));
        dummyRequests.add(new ReplacementRequestDTO("REP006", "4532123456781239", "Sarah Williams", "In Progress", "Compromised", 
                LocalDate.now().minusDays(4), null, null));
        dummyRequests.add(new ReplacementRequestDTO("REP007", "4532123456781240", "Michael Brown", "New", "Name Change", 
                LocalDate.now().minusDays(1), null, null));
        dummyRequests.add(new ReplacementRequestDTO("REP008", "4532123456781241", "Emily Davis", "Completed", "Lost", 
                LocalDate.now().minusDays(25), LocalDate.now().minusDays(20), "4532123456789878"));
        dummyRequests.add(new ReplacementRequestDTO("REP009", "4532123456781242", "David Miller", "Card Issued", "Damaged", 
                LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), "4532123456789879"));
        dummyRequests.add(new ReplacementRequestDTO("REP010", "4532123456781243", "Jennifer Wilson", "Rejected", "Other", 
                LocalDate.now().minusDays(18), LocalDate.now().minusDays(15), null));
        
        for (ReplacementRequestDTO request : dummyRequests) {
            replacementRequests.put(request.getRequestId(), request);
        }
    }
}