/*
 * Copyright 2025 Firefly Software Foundation
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
