package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.reportinganalytics.AuditEventDTO;
import com.vaadin.starter.business.backend.dto.reportinganalytics.ComplianceReportDTO;
import com.vaadin.starter.business.backend.dto.reportinganalytics.OperationalReportDTO;
import com.vaadin.starter.business.backend.dto.reportinganalytics.PerformanceMetricDTO;

import java.util.Collection;

/**
 * Service interface for reporting and analytics operations.
 */
public interface ReportingAnalyticsService {

    /**
     * Get all audit events.
     *
     * @return Collection of all audit events
     */
    Collection<AuditEventDTO> getAuditEvents();

    /**
     * Get an audit event by ID.
     *
     * @param id Audit event ID
     * @return Audit event with the given ID, or null if not found
     */
    AuditEventDTO getAuditEventById(String id);

    /**
     * Get all compliance reports.
     *
     * @return Collection of all compliance reports
     */
    Collection<ComplianceReportDTO> getComplianceReports();

    /**
     * Get a compliance report by ID.
     *
     * @param id Compliance report ID
     * @return Compliance report with the given ID, or null if not found
     */
    ComplianceReportDTO getComplianceReportById(String id);

    /**
     * Get all operational reports.
     *
     * @return Collection of all operational reports
     */
    Collection<OperationalReportDTO> getOperationalReports();

    /**
     * Get an operational report by ID.
     *
     * @param id Operational report ID
     * @return Operational report with the given ID, or null if not found
     */
    OperationalReportDTO getOperationalReportById(String id);

    /**
     * Get all performance metrics.
     *
     * @return Collection of all performance metrics
     */
    Collection<PerformanceMetricDTO> getPerformanceMetrics();

    /**
     * Get a performance metric by ID.
     *
     * @param id Performance metric ID
     * @return Performance metric with the given ID, or null if not found
     */
    PerformanceMetricDTO getPerformanceMetricById(String id);
}