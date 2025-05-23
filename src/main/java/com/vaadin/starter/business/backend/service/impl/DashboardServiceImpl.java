package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.dashboard.AlertDTO;
import com.vaadin.starter.business.backend.dto.dashboard.AlertSummaryDTO;
import com.vaadin.starter.business.backend.dto.dashboard.IncidentDTO;
import com.vaadin.starter.business.backend.dto.dashboard.OperationalMetricDTO;
import com.vaadin.starter.business.backend.dto.dashboard.SystemStatusDTO;
import com.vaadin.starter.business.backend.service.DashboardService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of the DashboardService interface.
 * Provides mock data for dashboard visualizations.
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    /**
     * Get hourly categories for transaction metrics.
     *
     * @return List of hour labels
     */
    @Override
    public List<String> getHourlyCategories() {
        return Arrays.asList(
                "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", 
                "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", 
                "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"
        );
    }

    /**
     * Get payment transaction data by hour.
     *
     * @return List of payment transaction counts by hour
     */
    @Override
    public List<Number> getPaymentTransactions() {
        return Arrays.asList(
                120, 90, 80, 70, 60, 90, 150, 250, 300, 280, 260, 240, 
                320, 350, 330, 310, 290, 270, 250, 230, 210, 190, 170, 150
        );
    }

    /**
     * Get transfer transaction data by hour.
     *
     * @return List of transfer transaction counts by hour
     */
    @Override
    public List<Number> getTransferTransactions() {
        return Arrays.asList(
                80, 70, 60, 50, 40, 60, 100, 180, 220, 200, 190, 180, 
                240, 260, 250, 230, 210, 190, 170, 150, 130, 110, 90, 70
        );
    }

    /**
     * Get inquiry transaction data by hour.
     *
     * @return List of inquiry transaction counts by hour
     */
    @Override
    public List<Number> getInquiryTransactions() {
        return Arrays.asList(
                200, 150, 130, 110, 100, 140, 220, 350, 420, 400, 380, 360, 
                450, 480, 460, 440, 420, 400, 380, 360, 340, 320, 300, 280
        );
    }

    /**
     * Get user activity channel categories.
     *
     * @return List of channel names for user activity
     */
    @Override
    public List<String> getUserActivityChannels() {
        return Arrays.asList("Web", "Mobile App", "API", "Branch", "Call Center");
    }

    /**
     * Get morning user activity data by channel.
     *
     * @return List of morning user counts by channel
     */
    @Override
    public List<Number> getMorningUserActivity() {
        return Arrays.asList(2500, 4200, 1800, 850, 650);
    }

    /**
     * Get afternoon user activity data by channel.
     *
     * @return List of afternoon user counts by channel
     */
    @Override
    public List<Number> getAfternoonUserActivity() {
        return Arrays.asList(3200, 5100, 2200, 920, 780);
    }

    /**
     * Get evening user activity data by channel.
     *
     * @return List of evening user counts by channel
     */
    @Override
    public List<Number> getEveningUserActivity() {
        return Arrays.asList(2800, 4800, 1950, 320, 580);
    }

    /**
     * Get response time metrics.
     *
     * @return List of response time metrics
     */
    @Override
    public List<ResponseTimeMetric> getResponseTimeMetrics() {
        List<ResponseTimeMetric> metrics = new ArrayList<>();
        metrics.add(new ResponseTimeMetric("API Response Time", "85ms", "↓ 12ms"));
        metrics.add(new ResponseTimeMetric("Page Load Time", "1.2s", "↓ 0.3s"));
        metrics.add(new ResponseTimeMetric("Database Query Time", "45ms", "↓ 8ms"));
        metrics.add(new ResponseTimeMetric("Authentication Time", "120ms", "↑ 15ms"));
        return metrics;
    }

    /**
     * Get alert summary data.
     *
     * @return List of alert summaries by severity
     */
    @Override
    public List<AlertSummaryDTO> getAlertSummaries() {
        List<AlertSummaryDTO> summaries = new ArrayList<>();
        summaries.add(new AlertSummaryDTO("Critical", 2, "red"));
        summaries.add(new AlertSummaryDTO("Warning", 5, "orange"));
        summaries.add(new AlertSummaryDTO("Info", 12, "blue"));
        summaries.add(new AlertSummaryDTO("Resolved", 8, "green"));
        return summaries;
    }

    /**
     * Get active alerts.
     *
     * @return List of active alerts
     */
    @Override
    public List<AlertDTO> getActiveAlerts() {
        List<AlertDTO> alerts = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        alerts.add(new AlertDTO(
                LocalDateTime.now().minusMinutes(5).format(formatter),
                "Critical",
                "Payment Gateway",
                "Connection timeout to payment processor"
        ));

        alerts.add(new AlertDTO(
                LocalDateTime.now().minusMinutes(15).format(formatter),
                "Critical",
                "Database Cluster",
                "High CPU usage on primary database node"
        ));

        alerts.add(new AlertDTO(
                LocalDateTime.now().minusMinutes(30).format(formatter),
                "Warning",
                "API Gateway",
                "Increased latency in API responses"
        ));

        alerts.add(new AlertDTO(
                LocalDateTime.now().minusMinutes(45).format(formatter),
                "Warning",
                "Authentication Service",
                "Increased failed login attempts"
        ));

        alerts.add(new AlertDTO(
                LocalDateTime.now().minusHours(1).format(formatter),
                "Warning",
                "Storage System",
                "Disk space below 20% threshold"
        ));

        alerts.add(new AlertDTO(
                LocalDateTime.now().minusHours(2).format(formatter),
                "Info",
                "Monitoring System",
                "Scheduled maintenance starting in 1 hour"
        ));

        alerts.add(new AlertDTO(
                LocalDateTime.now().minusHours(3).format(formatter),
                "Info",
                "Load Balancer",
                "New instance added to server pool"
        ));

        return alerts;
    }

    /**
     * Get recent incidents.
     *
     * @return List of recent incidents
     */
    @Override
    public List<IncidentDTO> getRecentIncidents() {
        List<IncidentDTO> incidents = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        incidents.add(new IncidentDTO(
                "INC-001",
                LocalDateTime.now().minusDays(1).format(formatter),
                LocalDateTime.now().minusDays(1).plusHours(2).format(formatter),
                "Payment Gateway",
                "Payment processor connection failure",
                "Resolved"
        ));

        incidents.add(new IncidentDTO(
                "INC-002",
                LocalDateTime.now().minusDays(2).format(formatter),
                LocalDateTime.now().minusDays(2).plusHours(1).format(formatter),
                "Authentication Service",
                "Increased authentication failures due to misconfiguration",
                "Resolved"
        ));

        incidents.add(new IncidentDTO(
                "INC-003",
                LocalDateTime.now().minusDays(3).format(formatter),
                LocalDateTime.now().minusDays(3).plusHours(4).format(formatter),
                "Database Cluster",
                "Database replication lag causing data inconsistency",
                "Resolved"
        ));

        incidents.add(new IncidentDTO(
                "INC-004",
                LocalDateTime.now().minusDays(5).format(formatter),
                LocalDateTime.now().minusDays(5).plusHours(1).format(formatter),
                "API Gateway",
                "Rate limiting misconfiguration causing API throttling",
                "Resolved"
        ));

        incidents.add(new IncidentDTO(
                "INC-005",
                LocalDateTime.now().minusHours(1).format(formatter),
                "",
                "Storage System",
                "Disk space critical on backup server",
                "In Progress"
        ));

        return incidents;
    }

    /**
     * Get system status data.
     *
     * @return List of system status information
     */
    @Override
    public List<SystemStatusDTO> getSystemStatus() {
        List<SystemStatusDTO> statuses = new ArrayList<>();
        statuses.add(new SystemStatusDTO("Core Banking", "Operational", "99.99%"));
        statuses.add(new SystemStatusDTO("Payment Processing", "Operational", "99.95%"));
        statuses.add(new SystemStatusDTO("Customer Portal", "Operational", "99.98%"));
        statuses.add(new SystemStatusDTO("Mobile Banking", "Operational", "99.97%"));
        return statuses;
    }

    /**
     * Get transaction volume categories.
     *
     * @return List of transaction volume categories (hours)
     */
    @Override
    public List<String> getTransactionVolumeCategories() {
        return Arrays.asList("00:00", "02:00", "04:00", "06:00", "08:00", "10:00", "12:00", 
                "14:00", "16:00", "18:00", "20:00", "22:00");
    }

    /**
     * Get transaction volume data.
     *
     * @return List of transaction counts by hour
     */
    @Override
    public List<Number> getTransactionVolume() {
        return Arrays.asList(1200, 980, 850, 920, 1450, 2100, 2400, 2300, 2100, 1800, 1650, 1400);
    }

    /**
     * Get operational metrics data.
     *
     * @return List of operational metrics
     */
    @Override
    public List<OperationalMetricDTO> getOperationalMetrics() {
        List<OperationalMetricDTO> metrics = new ArrayList<>();
        metrics.add(new OperationalMetricDTO("Average Response Time", "125ms", "↓ 5%"));
        metrics.add(new OperationalMetricDTO("Transaction Success Rate", "99.8%", "↑ 0.2%"));
        metrics.add(new OperationalMetricDTO("Active Users", "12,450", "↑ 8%"));
        metrics.add(new OperationalMetricDTO("Error Rate", "0.15%", "↓ 0.05%"));
        return metrics;
    }
}
