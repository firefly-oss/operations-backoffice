package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.transactions.BatchJobDTO;
import com.vaadin.starter.business.backend.dto.transactions.PaymentMethodsDTO;
import com.vaadin.starter.business.backend.dto.transactions.PaymentStatusSummaryDTO;
import com.vaadin.starter.business.backend.dto.transactions.PaymentVolumeDTO;
import com.vaadin.starter.business.backend.dto.transactions.TransactionDTO;
import com.vaadin.starter.business.backend.dto.transactions.TransactionDetailsDTO;
import com.vaadin.starter.business.backend.dto.transactions.TransactionReconciliationDTO;
import com.vaadin.starter.business.backend.dto.transactions.TransactionSearchCriteriaDTO;

import java.util.List;

/**
 * Service interface for transaction operations.
 * This service provides data for various transaction-related views:
 * - BatchOperations
 * - PaymentProcessing
 * - TransactionDetails
 * - TransactionManagement
 * - TransactionReconciliation
 * - TransactionSearchMonitoring
 */
public interface TransactionOperationsService {

    /**
     * Get batch jobs for batch operations view.
     *
     * @return List of batch jobs
     */
    List<BatchJobDTO> getBatchJobs();

    /**
     * Get payment status summary for payment processing view.
     *
     * @return Payment status summary
     */
    PaymentStatusSummaryDTO getPaymentStatusSummary();

    /**
     * Get payment volume data for payment processing view.
     *
     * @return Payment volume data
     */
    PaymentVolumeDTO getPaymentVolumeData();

    /**
     * Get payment methods data for payment processing view.
     *
     * @return Payment methods data
     */
    PaymentMethodsDTO getPaymentMethodsData();

    /**
     * Get transaction details by ID.
     *
     * @param id Transaction ID
     * @return Transaction details
     */
    TransactionDetailsDTO getTransactionDetails(String id);

    /**
     * Get transaction reconciliation data.
     *
     * @return Transaction reconciliation data
     */
    TransactionReconciliationDTO getTransactionReconciliationData();

    /**
     * Search transactions based on criteria.
     *
     * @param searchCriteria Search criteria
     * @return List of transactions matching the criteria
     */
    List<TransactionDTO> searchTransactions(TransactionSearchCriteriaDTO searchCriteria);
}
