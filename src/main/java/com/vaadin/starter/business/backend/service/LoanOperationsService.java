package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.loanoperations.LoanApplicationDTO;
import com.vaadin.starter.business.backend.dto.loanoperations.LoanCollectionDTO;
import com.vaadin.starter.business.backend.dto.loanoperations.LoanDisbursementDTO;
import com.vaadin.starter.business.backend.dto.loanoperations.LoanRestructuringDTO;

import java.util.Collection;

/**
 * Service interface for loan operations.
 */
public interface LoanOperationsService {

    /**
     * Get all loan applications.
     *
     * @return Collection of all loan applications
     */
    Collection<LoanApplicationDTO> getLoanApplications();

    /**
     * Get a loan application by ID.
     *
     * @param id Loan application ID
     * @return Loan application with the given ID, or null if not found
     */
    LoanApplicationDTO getLoanApplicationById(String id);

    /**
     * Get all loan collections.
     *
     * @return Collection of all loan collections
     */
    Collection<LoanCollectionDTO> getLoanCollections();

    /**
     * Get a loan collection by ID.
     *
     * @param id Loan collection ID
     * @return Loan collection with the given ID, or null if not found
     */
    LoanCollectionDTO getLoanCollectionById(String id);

    /**
     * Get all loan disbursements.
     *
     * @return Collection of all loan disbursements
     */
    Collection<LoanDisbursementDTO> getLoanDisbursements();

    /**
     * Get a loan disbursement by ID.
     *
     * @param id Loan disbursement ID
     * @return Loan disbursement with the given ID, or null if not found
     */
    LoanDisbursementDTO getLoanDisbursementById(String id);

    /**
     * Get all loan restructurings.
     *
     * @return Collection of all loan restructurings
     */
    Collection<LoanRestructuringDTO> getLoanRestructurings();

    /**
     * Get a loan restructuring by ID.
     *
     * @param id Loan restructuring ID
     * @return Loan restructuring with the given ID, or null if not found
     */
    LoanRestructuringDTO getLoanRestructuringById(String id);
}