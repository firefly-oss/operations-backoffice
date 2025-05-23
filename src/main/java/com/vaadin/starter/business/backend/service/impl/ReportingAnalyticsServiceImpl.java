package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.reportinganalytics.AuditEventDTO;
import com.vaadin.starter.business.backend.dto.reportinganalytics.ComplianceReportDTO;
import com.vaadin.starter.business.backend.dto.reportinganalytics.OperationalReportDTO;
import com.vaadin.starter.business.backend.dto.reportinganalytics.PerformanceMetricDTO;
import com.vaadin.starter.business.backend.service.ReportingAnalyticsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Implementation of the ReportingAnalyticsService interface.
 * Provides mock data for demonstration purposes.
 */
@Service
public class ReportingAnalyticsServiceImpl implements ReportingAnalyticsService {

    private final Map<String, AuditEventDTO> auditEvents = new HashMap<>();
    private final Map<String, ComplianceReportDTO> complianceReports = new HashMap<>();
    private final Map<String, OperationalReportDTO> operationalReports = new HashMap<>();
    private final Map<String, PerformanceMetricDTO> performanceMetrics = new HashMap<>();

    private final Random random = new Random(42); // Fixed seed for reproducible data

    public ReportingAnalyticsServiceImpl() {
        generateMockAuditEvents();
        generateMockComplianceReports();
        generateMockOperationalReports();
        generateMockPerformanceMetrics();
    }

    @Override
    public Collection<AuditEventDTO> getAuditEvents() {
        return auditEvents.values();
    }

    @Override
    public AuditEventDTO getAuditEventById(String id) {
        return auditEvents.get(id);
    }

    @Override
    public Collection<ComplianceReportDTO> getComplianceReports() {
        return complianceReports.values();
    }

    @Override
    public ComplianceReportDTO getComplianceReportById(String id) {
        return complianceReports.get(id);
    }

    @Override
    public Collection<OperationalReportDTO> getOperationalReports() {
        return operationalReports.values();
    }

    @Override
    public OperationalReportDTO getOperationalReportById(String id) {
        return operationalReports.get(id);
    }

    @Override
    public Collection<PerformanceMetricDTO> getPerformanceMetrics() {
        return performanceMetrics.values();
    }

    @Override
    public PerformanceMetricDTO getPerformanceMetricById(String id) {
        return performanceMetrics.get(id);
    }

    private void generateMockAuditEvents() {
        auditEvents.clear();

        String[] users = {
            "john.smith", 
            "maria.rodriguez", 
            "wei.zhang", 
            "sarah.johnson", 
            "ahmed.hassan",
            "olivia.wilson",
            "michael.brown",
            "admin.user"
        };

        String[] actionTypes = {
            "Login", 
            "Logout", 
            "Create", 
            "Update", 
            "Delete",
            "View",
            "Export",
            "Import",
            "Approve",
            "Reject"
        };

        String[] modules = {
            "User Management", 
            "Customer Records", 
            "Accounts", 
            "Transactions", 
            "Reports",
            "System Configuration",
            "Security",
            "Compliance"
        };

        String[] statuses = {
            "Success", 
            "Failure", 
            "Warning"
        };

        String[] ipAddresses = {
            "192.168.1.101", 
            "192.168.1.102", 
            "192.168.1.103", 
            "192.168.1.104", 
            "192.168.1.105",
            "10.0.0.15",
            "10.0.0.16",
            "10.0.0.17"
        };

        String[] details = {
            "User logged in successfully",
            "Failed login attempt - incorrect password",
            "Customer record updated",
            "New account created",
            "Report exported to PDF",
            "System settings modified",
            "User permissions updated",
            "Transaction approved",
            "Document uploaded",
            "Password reset requested"
        };

        for (int i = 1; i <= 50; i++) {
            String eventId = "EVT" + String.format("%06d", i);

            String user = users[random.nextInt(users.length)];
            String actionType = actionTypes[random.nextInt(actionTypes.length)];
            String module = modules[random.nextInt(modules.length)];
            String status = statuses[random.nextInt(statuses.length)];
            String ipAddress = ipAddresses[random.nextInt(ipAddresses.length)];
            String sessionId = "SES" + String.format("%08d", random.nextInt(100000000));
            String detail = details[random.nextInt(details.length)];

            // Generate a random timestamp within the last 30 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime timestamp = now.minusDays(random.nextInt(30)).minusHours(random.nextInt(24));

            // Generate before/after data for some events
            String beforeData = null;
            String afterData = null;
            if (actionType.equals("Update")) {
                beforeData = "{ \"name\": \"Old Name\", \"status\": \"Inactive\", \"email\": \"old@example.com\" }";
                afterData = "{ \"name\": \"New Name\", \"status\": \"Active\", \"email\": \"new@example.com\" }";
            }

            AuditEventDTO event = new AuditEventDTO(
                eventId, timestamp, user, actionType, module, status, ipAddress, sessionId, detail, beforeData, afterData
            );

            auditEvents.put(eventId, event);
        }
    }

    private void generateMockComplianceReports() {
        complianceReports.clear();

        String[] reportNames = {
            "GDPR Compliance Report", 
            "PCI-DSS Compliance Assessment", 
            "AML Policy Compliance", 
            "KYC Procedures Audit", 
            "Regulatory Examination Results",
            "Data Privacy Compliance",
            "Information Security Standards",
            "Vendor Management Compliance"
        };

        String[] regulations = {
            "GDPR", 
            "PCI-DSS", 
            "SOX", 
            "Basel III", 
            "AML",
            "KYC",
            "FATCA",
            "MiFID II"
        };

        String[] frequencies = {
            "Daily", 
            "Weekly", 
            "Monthly", 
            "Quarterly", 
            "Yearly"
        };

        String[] statuses = {
            "Pending", 
            "In Progress", 
            "Submitted", 
            "Approved", 
            "Rejected"
        };

        String[] assignees = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Compliance Team",
            "Risk Management",
            "Legal Department"
        };

        for (int i = 1; i <= 20; i++) {
            String reportId = "CR" + String.format("%04d", i);

            String reportName = reportNames[random.nextInt(reportNames.length)];
            String regulation = regulations[random.nextInt(regulations.length)];
            String frequency = frequencies[random.nextInt(frequencies.length)];
            String status = statuses[random.nextInt(statuses.length)];
            String assignedTo = assignees[random.nextInt(assignees.length)];

            // Generate a random due date in the future
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime dueDate = now.plusDays(random.nextInt(90) + 1);

            ComplianceReportDTO report = new ComplianceReportDTO(
                reportId, reportName, regulation, frequency, dueDate, status, assignedTo
            );

            complianceReports.put(reportId, report);
        }
    }

    private void generateMockOperationalReports() {
        operationalReports.clear();

        String[] reportNames = {
            "Daily Transaction Summary", 
            "Customer Activity Report", 
            "Account Operations Overview", 
            "Loan Operations Status", 
            "Card Operations Metrics",
            "System Performance Report",
            "Error Rate Analysis",
            "Processing Time Metrics",
            "User Activity Summary",
            "Resource Utilization Report"
        };

        String[] categories = {
            "Transactions", 
            "Customer Activity", 
            "Account Operations", 
            "Loan Operations", 
            "Card Operations",
            "System Performance",
            "Error Analysis",
            "Processing Metrics",
            "User Activity",
            "Resource Utilization"
        };

        String[] frequencies = {
            "Daily", 
            "Weekly", 
            "Monthly", 
            "Quarterly", 
            "Yearly",
            "On-demand"
        };

        String[] createdBy = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Operations Team",
            "System Admin",
            "Reporting Engine"
        };

        String[] descriptions = {
            "Summary of all transactions processed in the system",
            "Overview of customer activity and engagement metrics",
            "Detailed report on account operations and status changes",
            "Analysis of loan processing operations and performance",
            "Metrics related to card operations and usage patterns",
            "System performance indicators and resource utilization",
            "Analysis of error rates and types across the system",
            "Processing time metrics for various operations",
            "Summary of user activity and system usage patterns",
            "Report on resource utilization and capacity planning"
        };

        for (int i = 1; i <= 20; i++) {
            String reportId = "OR" + String.format("%04d", i);

            int nameIndex = random.nextInt(reportNames.length);
            String reportName = reportNames[nameIndex];
            String category = categories[nameIndex % categories.length]; // Match category to name
            String frequency = frequencies[random.nextInt(frequencies.length)];
            String creator = createdBy[random.nextInt(createdBy.length)];
            String description = descriptions[nameIndex % descriptions.length]; // Match description to name

            // Generate a random last generated date in the past
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lastGenerated = now.minusDays(random.nextInt(30));

            OperationalReportDTO report = new OperationalReportDTO(
                reportId, reportName, category, frequency, lastGenerated, creator, description
            );

            operationalReports.put(reportId, report);
        }
    }

    private void generateMockPerformanceMetrics() {
        performanceMetrics.clear();

        // Define some metric types
        String[][] metricDefinitions = {
            // metricName, category, unit, changeDirection
            {"CPU Utilization", "System", "%", "down"},
            {"Memory Usage", "System", "%", "up"},
            {"Disk I/O", "System", "MB/s", "up"},
            {"Network Traffic", "System", "Mbps", "down"},
            {"Transaction Volume", "Transactions", "", "up"},
            {"Avg. Processing Time", "Transactions", "s", "down"},
            {"Success Rate", "Transactions", "%", "up"},
            {"Pending Transactions", "Transactions", "", "down"},
            {"Active Users", "Users", "", "up"},
            {"Avg. Session Duration", "Users", "min", "up"},
            {"Login Success Rate", "Users", "%", "down"},
            {"New Users", "Users", "", "up"}
        };

        for (int i = 0; i < metricDefinitions.length; i++) {
            String metricId = "METRIC" + String.format("%03d", i + 1);
            String metricName = metricDefinitions[i][0];
            String category = metricDefinitions[i][1];
            String unit = metricDefinitions[i][2];
            String changeDirection = metricDefinitions[i][3];

            // Generate appropriate value based on the metric type
            String value;
            String change;
            Number[] historicalData;

            switch (metricName) {
                case "CPU Utilization":
                    value = String.valueOf(20 + random.nextInt(60));
                    change = String.valueOf(1 + random.nextInt(10)) + "%";
                    historicalData = generateHistoricalData(12, 20, 80);
                    break;
                case "Memory Usage":
                    value = String.valueOf(40 + random.nextInt(40));
                    change = String.valueOf(1 + random.nextInt(5)) + "%";
                    historicalData = generateHistoricalData(12, 40, 80);
                    break;
                case "Disk I/O":
                    value = String.valueOf(50 + random.nextInt(200));
                    change = String.valueOf(5 + random.nextInt(20)) + "%";
                    historicalData = generateHistoricalData(12, 50, 250);
                    break;
                case "Network Traffic":
                    value = String.valueOf(10 + random.nextInt(90));
                    change = String.valueOf(5 + random.nextInt(15)) + "%";
                    historicalData = generateHistoricalData(12, 10, 100);
                    break;
                case "Transaction Volume":
                    value = String.valueOf(5000 + random.nextInt(10000));
                    change = String.valueOf(100 + random.nextInt(900));
                    historicalData = generateHistoricalData(7, 5000, 15000);
                    break;
                case "Avg. Processing Time":
                    double procTime = 0.5 + (random.nextDouble() * 2.0);
                    value = String.format("%.1f", procTime);
                    change = String.format("%.1f", 0.1 + (random.nextDouble() * 0.5)) + "s";
                    historicalData = generateHistoricalData(12, 0.5, 3.0);
                    break;
                case "Success Rate":
                    value = String.valueOf(90 + random.nextInt(10));
                    change = "0." + String.valueOf(1 + random.nextInt(9)) + "%";
                    historicalData = generateHistoricalData(12, 90, 100);
                    break;
                case "Pending Transactions":
                    value = String.valueOf(10 + random.nextInt(90));
                    change = String.valueOf(1 + random.nextInt(10));
                    historicalData = generateHistoricalData(12, 10, 100);
                    break;
                case "Active Users":
                    value = String.valueOf(500 + random.nextInt(1500));
                    change = String.valueOf(50 + random.nextInt(200));
                    historicalData = generateHistoricalData(12, 500, 2000);
                    break;
                case "Avg. Session Duration":
                    value = String.valueOf(10 + random.nextInt(30));
                    change = String.valueOf(1 + random.nextInt(5)) + "m";
                    historicalData = generateHistoricalData(12, 10, 40);
                    break;
                case "Login Success Rate":
                    value = String.valueOf(95 + random.nextInt(5));
                    change = "0." + String.valueOf(1 + random.nextInt(9)) + "%";
                    historicalData = generateHistoricalData(12, 95, 100);
                    break;
                case "New Users":
                    value = String.valueOf(20 + random.nextInt(100));
                    change = String.valueOf(5 + random.nextInt(20));
                    historicalData = generateHistoricalData(12, 20, 120);
                    break;
                default:
                    value = String.valueOf(random.nextInt(100));
                    change = String.valueOf(random.nextInt(10)) + "%";
                    historicalData = generateHistoricalData(12, 0, 100);
            }

            // Generate timestamp and time labels
            LocalDateTime timestamp = LocalDateTime.now().minusHours(random.nextInt(24));
            String server = "Server" + (1 + random.nextInt(4)); // Random server name
            String[] timeLabels = generateTimeLabels(historicalData.length);

            PerformanceMetricDTO metric = new PerformanceMetricDTO(
                metricId, metricName, category, value, unit, change, changeDirection, timestamp, server, historicalData, timeLabels
            );

            performanceMetrics.put(metricId, metric);
        }
    }

    private Number[] generateHistoricalData(int count, double min, double max) {
        Number[] data = new Number[count];
        for (int i = 0; i < count; i++) {
            data[i] = min + (random.nextDouble() * (max - min));
        }
        return data;
    }

    private String[] generateTimeLabels(int count) {
        String[] labels = new String[count];
        LocalDateTime now = LocalDateTime.now();

        // Generate appropriate time labels based on count
        if (count <= 12) {
            // Hourly labels for the last 12 hours
            for (int i = 0; i < count; i++) {
                LocalDateTime time = now.minusHours(count - i - 1);
                labels[i] = time.getHour() + ":00";
            }
        } else if (count <= 31) {
            // Daily labels for the last month
            for (int i = 0; i < count; i++) {
                LocalDateTime date = now.minusDays(count - i - 1);
                labels[i] = date.getMonthValue() + "/" + date.getDayOfMonth();
            }
        } else {
            // Monthly labels
            for (int i = 0; i < count; i++) {
                LocalDateTime date = now.minusMonths(count - i - 1);
                labels[i] = date.getMonth().toString().substring(0, 3);
            }
        }

        return labels;
    }
}
