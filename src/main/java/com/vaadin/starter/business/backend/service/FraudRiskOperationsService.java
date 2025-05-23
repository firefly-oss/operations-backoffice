package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.fraudriskoperations.AmlKycCaseDTO;
import com.vaadin.starter.business.backend.dto.fraudriskoperations.InvestigationDTO;
import com.vaadin.starter.business.backend.dto.fraudriskoperations.RiskAlertDTO;
import com.vaadin.starter.business.backend.dto.fraudriskoperations.SuspiciousActivityDTO;

import java.util.Collection;

/**
 * Service interface for fraud risk operations.
 */
public interface FraudRiskOperationsService {

    /**
     * Get all AML/KYC cases.
     *
     * @return Collection of all AML/KYC cases
     */
    Collection<AmlKycCaseDTO> getAmlKycCases();

    /**
     * Get an AML/KYC case by ID.
     *
     * @param id AML/KYC case ID
     * @return AML/KYC case with the given ID, or null if not found
     */
    AmlKycCaseDTO getAmlKycCaseById(String id);

    /**
     * Get all investigations.
     *
     * @return Collection of all investigations
     */
    Collection<InvestigationDTO> getInvestigations();

    /**
     * Get an investigation by ID.
     *
     * @param id Investigation ID
     * @return Investigation with the given ID, or null if not found
     */
    InvestigationDTO getInvestigationById(String id);

    /**
     * Get all risk alerts.
     *
     * @return Collection of all risk alerts
     */
    Collection<RiskAlertDTO> getRiskAlerts();

    /**
     * Get a risk alert by ID.
     *
     * @param id Risk alert ID
     * @return Risk alert with the given ID, or null if not found
     */
    RiskAlertDTO getRiskAlertById(String id);

    /**
     * Get all suspicious activities.
     *
     * @return Collection of all suspicious activities
     */
    Collection<SuspiciousActivityDTO> getSuspiciousActivities();

    /**
     * Get a suspicious activity by ID.
     *
     * @param id Suspicious activity ID
     * @return Suspicious activity with the given ID, or null if not found
     */
    SuspiciousActivityDTO getSuspiciousActivityById(String id);
}