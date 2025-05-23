package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.dashboard.AlertDTO;
import com.vaadin.starter.business.backend.dto.dashboard.AlertSummaryDTO;
import com.vaadin.starter.business.backend.dto.dashboard.IncidentDTO;
import com.vaadin.starter.business.backend.dto.dashboard.OperationalMetricDTO;
import com.vaadin.starter.business.backend.dto.dashboard.SystemStatusDTO;

import java.util.List;

/**
 * Service interface for dashboard data.
 */
public interface DashboardService {

    /**
     * Get transaction metrics data by hour.
     *
     * @return List of hourly transaction data for different transaction types
     */
    List<String> getHourlyCategories();

    /**
     * Get payment transaction data by hour.
     *
     * @return List of payment transaction counts by hour
     */
    List<Number> getPaymentTransactions();

    /**
     * Get transfer transaction data by hour.
     *
     * @return List of transfer transaction counts by hour
     */
    List<Number> getTransferTransactions();

    /**
     * Get inquiry transaction data by hour.
     *
     * @return List of inquiry transaction counts by hour
     */
    List<Number> getInquiryTransactions();

    /**
     * Get user activity channel categories.
     *
     * @return List of channel names for user activity
     */
    List<String> getUserActivityChannels();

    /**
     * Get morning user activity data by channel.
     *
     * @return List of morning user counts by channel
     */
    List<Number> getMorningUserActivity();

    /**
     * Get afternoon user activity data by channel.
     *
     * @return List of afternoon user counts by channel
     */
    List<Number> getAfternoonUserActivity();

    /**
     * Get evening user activity data by channel.
     *
     * @return List of evening user counts by channel
     */
    List<Number> getEveningUserActivity();

    /**
     * Get response time metrics.
     *
     * @return List of response time metrics
     */
    List<ResponseTimeMetric> getResponseTimeMetrics();

    /**
     * Get alert summary data.
     *
     * @return List of alert summaries by severity
     */
    List<AlertSummaryDTO> getAlertSummaries();

    /**
     * Get active alerts.
     *
     * @return List of active alerts
     */
    List<AlertDTO> getActiveAlerts();

    /**
     * Get recent incidents.
     *
     * @return List of recent incidents
     */
    List<IncidentDTO> getRecentIncidents();

    /**
     * Get system status data.
     *
     * @return List of system status information
     */
    List<SystemStatusDTO> getSystemStatus();

    /**
     * Get transaction volume data.
     *
     * @return List of transaction volume by hour
     */
    List<String> getTransactionVolumeCategories();

    /**
     * Get transaction volume data.
     *
     * @return List of transaction counts by hour
     */
    List<Number> getTransactionVolume();

    /**
     * Get operational metrics data.
     *
     * @return List of operational metrics
     */
    List<OperationalMetricDTO> getOperationalMetrics();

    /**
     * Represents a response time metric with name, value, and change.
     */
    class ResponseTimeMetric {
        private String metric;
        private String value;
        private String change;

        public ResponseTimeMetric(String metric, String value, String change) {
            this.metric = metric;
            this.value = value;
            this.change = change;
        }

        public String getMetric() {
            return metric;
        }

        public String getValue() {
            return value;
        }

        public String getChange() {
            return change;
        }
    }
}
