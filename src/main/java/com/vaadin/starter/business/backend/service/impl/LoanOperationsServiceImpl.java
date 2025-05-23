package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.loanoperations.LoanApplicationDTO;
import com.vaadin.starter.business.backend.dto.loanoperations.LoanCollectionDTO;
import com.vaadin.starter.business.backend.dto.loanoperations.LoanDisbursementDTO;
import com.vaadin.starter.business.backend.dto.loanoperations.LoanRestructuringDTO;
import com.vaadin.starter.business.backend.service.LoanOperationsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the LoanOperationsService interface.
 * Provides mock data for demonstration purposes.
 */
@Service
public class LoanOperationsServiceImpl implements LoanOperationsService {

    private final Map<String, LoanApplicationDTO> loanApplications = new HashMap<>();
    private final Map<String, LoanCollectionDTO> loanCollections = new HashMap<>();
    private final Map<String, LoanDisbursementDTO> loanDisbursements = new HashMap<>();
    private final Map<String, LoanRestructuringDTO> loanRestructurings = new HashMap<>();

    public LoanOperationsServiceImpl() {
        generateMockLoanApplications();
        generateMockLoanCollections();
        generateMockLoanDisbursements();
        generateMockLoanRestructurings();
    }

    @Override
    public Collection<LoanApplicationDTO> getLoanApplications() {
        return loanApplications.values();
    }

    @Override
    public LoanApplicationDTO getLoanApplicationById(String id) {
        return loanApplications.get(id);
    }

    @Override
    public Collection<LoanCollectionDTO> getLoanCollections() {
        return loanCollections.values();
    }

    @Override
    public LoanCollectionDTO getLoanCollectionById(String id) {
        return loanCollections.get(id);
    }

    @Override
    public Collection<LoanDisbursementDTO> getLoanDisbursements() {
        return loanDisbursements.values();
    }

    @Override
    public LoanDisbursementDTO getLoanDisbursementById(String id) {
        return loanDisbursements.get(id);
    }

    @Override
    public Collection<LoanRestructuringDTO> getLoanRestructurings() {
        return loanRestructurings.values();
    }

    @Override
    public LoanRestructuringDTO getLoanRestructuringById(String id) {
        return loanRestructurings.get(id);
    }

    private void generateMockLoanApplications() {
        loanApplications.clear();

        loanApplications.put("LA001", new LoanApplicationDTO("LA001", "CUST001", "John Smith", "Personal", "Under Review", 25000.0, LocalDate.now().minusDays(5)));
        loanApplications.put("LA002", new LoanApplicationDTO("LA002", "CUST002", "Jane Doe", "Home", "Approved", 350000.0, LocalDate.now().minusDays(15)));
        loanApplications.put("LA003", new LoanApplicationDTO("LA003", "CUST003", "Robert Johnson", "Auto", "Pending Documentation", 45000.0, LocalDate.now().minusDays(3)));
        loanApplications.put("LA004", new LoanApplicationDTO("LA004", "CUST004", "Emily Wilson", "Education", "New", 75000.0, LocalDate.now().minusDays(1)));
        loanApplications.put("LA005", new LoanApplicationDTO("LA005", "CUST005", "Michael Brown", "Business", "Rejected", 150000.0, LocalDate.now().minusDays(10)));
        loanApplications.put("LA006", new LoanApplicationDTO("LA006", "CUST006", "Sarah Davis", "Personal", "Under Review", 15000.0, LocalDate.now().minusDays(7)));
        loanApplications.put("LA007", new LoanApplicationDTO("LA007", "CUST007", "David Miller", "Home", "Pending Documentation", 275000.0, LocalDate.now().minusDays(4)));
        loanApplications.put("LA008", new LoanApplicationDTO("LA008", "CUST008", "Jennifer Garcia", "Auto", "Approved", 35000.0, LocalDate.now().minusDays(20)));
        loanApplications.put("LA009", new LoanApplicationDTO("LA009", "CUST009", "James Rodriguez", "Business", "New", 200000.0, LocalDate.now()));
        loanApplications.put("LA010", new LoanApplicationDTO("LA010", "CUST010", "Lisa Martinez", "Education", "Under Review", 50000.0, LocalDate.now().minusDays(2)));
    }

    private void generateMockLoanCollections() {
        loanCollections.clear();

        loanCollections.put("C001", new LoanCollectionDTO("C001", "LA002", "Jane Doe", "Pending", 2500.0, "Auto Debit", LocalDate.now().plusDays(5), null));
        loanCollections.put("C002", new LoanCollectionDTO("C002", "LA008", "Jennifer Garcia", "Paid", 1200.0, "Bank Transfer", LocalDate.now().minusDays(10), LocalDate.now().minusDays(8)));
        loanCollections.put("C003", new LoanCollectionDTO("C003", "LA003", "Robert Johnson", "Overdue", 1500.0, "Auto Debit", LocalDate.now().minusDays(5), null));
        loanCollections.put("C004", new LoanCollectionDTO("C004", "LA005", "Michael Brown", "Written Off", 3000.0, "Bank Transfer", LocalDate.now().minusDays(60), null));
        loanCollections.put("C005", new LoanCollectionDTO("C005", "LA001", "John Smith", "Pending", 1800.0, "Auto Debit", LocalDate.now().plusDays(10), null));
        loanCollections.put("C006", new LoanCollectionDTO("C006", "LA007", "David Miller", "Paid", 2200.0, "Digital Wallet", LocalDate.now().minusDays(15), LocalDate.now().minusDays(14)));
        loanCollections.put("C007", new LoanCollectionDTO("C007", "LA006", "Sarah Davis", "In Collection", 1600.0, "Bank Transfer", LocalDate.now().minusDays(20), null));
        loanCollections.put("C008", new LoanCollectionDTO("C008", "LA010", "Lisa Martinez", "Pending", 2000.0, "Auto Debit", LocalDate.now().plusDays(15), null));
        loanCollections.put("C009", new LoanCollectionDTO("C009", "LA004", "Emily Wilson", "Paid", 1900.0, "Check", LocalDate.now().minusDays(5), LocalDate.now().minusDays(3)));
        loanCollections.put("C010", new LoanCollectionDTO("C010", "LA009", "James Rodriguez", "Overdue", 2700.0, "Auto Debit", LocalDate.now().minusDays(2), null));
    }

    private void generateMockLoanDisbursements() {
        loanDisbursements.clear();

        loanDisbursements.put("D001", new LoanDisbursementDTO("D001", "LA002", "Jane Doe", "Scheduled", 350000.0, "Bank Transfer", LocalDate.now().plusDays(2), null));
        loanDisbursements.put("D002", new LoanDisbursementDTO("D002", "LA008", "Jennifer Garcia", "Processed", 35000.0, "Bank Transfer", LocalDate.now().minusDays(5), LocalDate.now().minusDays(3)));
        loanDisbursements.put("D003", new LoanDisbursementDTO("D003", "LA003", "Robert Johnson", "Scheduled", 45000.0, "Check", LocalDate.now().plusDays(1), null));
        loanDisbursements.put("D004", new LoanDisbursementDTO("D004", "LA005", "Michael Brown", "Cancelled", 150000.0, "Bank Transfer", LocalDate.now().minusDays(10), null));
        loanDisbursements.put("D005", new LoanDisbursementDTO("D005", "LA001", "John Smith", "Scheduled", 25000.0, "Digital Wallet", LocalDate.now().plusDays(3), null));
        loanDisbursements.put("D006", new LoanDisbursementDTO("D006", "LA007", "David Miller", "Processed", 275000.0, "Bank Transfer", LocalDate.now().minusDays(7), LocalDate.now().minusDays(6)));
        loanDisbursements.put("D007", new LoanDisbursementDTO("D007", "LA006", "Sarah Davis", "Failed", 15000.0, "Bank Transfer", LocalDate.now().minusDays(4), LocalDate.now().minusDays(4)));
        loanDisbursements.put("D008", new LoanDisbursementDTO("D008", "LA010", "Lisa Martinez", "Scheduled", 50000.0, "Check", LocalDate.now().plusDays(5), null));
        loanDisbursements.put("D009", new LoanDisbursementDTO("D009", "LA004", "Emily Wilson", "Processed", 75000.0, "Bank Transfer", LocalDate.now().minusDays(2), LocalDate.now().minusDays(1)));
        loanDisbursements.put("D010", new LoanDisbursementDTO("D010", "LA009", "James Rodriguez", "Scheduled", 200000.0, "Digital Wallet", LocalDate.now().plusDays(4), null));
    }

    private void generateMockLoanRestructurings() {
        loanRestructurings.clear();

        loanRestructurings.put("RR001", new LoanRestructuringDTO("RR001", "LA003", "Robert Johnson", "Term Extension", "New", LocalDate.now().minusDays(2), "John Analyst", null));
        loanRestructurings.put("RR002", new LoanRestructuringDTO("RR002", "LA005", "Michael Brown", "Interest Rate Reduction", "Approved", LocalDate.now().minusDays(15), "Sarah Manager", LocalDate.now().minusDays(10)));
        loanRestructurings.put("RR003", new LoanRestructuringDTO("RR003", "LA006", "Sarah Davis", "Payment Holiday", "Under Review", LocalDate.now().minusDays(5), "John Analyst", null));
        loanRestructurings.put("RR004", new LoanRestructuringDTO("RR004", "LA010", "Lisa Martinez", "Debt Consolidation", "Rejected", LocalDate.now().minusDays(20), "Mark Supervisor", LocalDate.now().minusDays(15)));
        loanRestructurings.put("RR005", new LoanRestructuringDTO("RR005", "LA007", "David Miller", "Principal Reduction", "Implemented", LocalDate.now().minusDays(30), "Sarah Manager", LocalDate.now().minusDays(25)));
        loanRestructurings.put("RR006", new LoanRestructuringDTO("RR006", "LA001", "John Smith", "Term Extension", "New", LocalDate.now().minusDays(1), null, null));
        loanRestructurings.put("RR007", new LoanRestructuringDTO("RR007", "LA008", "Jennifer Garcia", "Interest Rate Reduction", "Under Review", LocalDate.now().minusDays(7), "Mark Supervisor", null));
        loanRestructurings.put("RR008", new LoanRestructuringDTO("RR008", "LA004", "Emily Wilson", "Payment Holiday", "Approved", LocalDate.now().minusDays(12), "John Analyst", LocalDate.now().minusDays(8)));
        loanRestructurings.put("RR009", new LoanRestructuringDTO("RR009", "LA009", "James Rodriguez", "Debt Consolidation", "New", LocalDate.now(), null, null));
        loanRestructurings.put("RR010", new LoanRestructuringDTO("RR010", "LA002", "Jane Doe", "Principal Reduction", "Implemented", LocalDate.now().minusDays(25), "Sarah Manager", LocalDate.now().minusDays(20)));
    }
}