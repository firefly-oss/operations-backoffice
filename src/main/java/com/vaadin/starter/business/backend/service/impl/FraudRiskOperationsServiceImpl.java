package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.fraudriskoperations.AmlKycCaseDTO;
import com.vaadin.starter.business.backend.dto.fraudriskoperations.InvestigationDTO;
import com.vaadin.starter.business.backend.dto.fraudriskoperations.RiskAlertDTO;
import com.vaadin.starter.business.backend.dto.fraudriskoperations.SuspiciousActivityDTO;
import com.vaadin.starter.business.backend.service.FraudRiskOperationsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Implementation of the FraudRiskOperationsService interface.
 * Provides mock data for demonstration purposes.
 */
@Service
public class FraudRiskOperationsServiceImpl implements FraudRiskOperationsService {

    private final Map<String, AmlKycCaseDTO> amlKycCases = new HashMap<>();
    private final Map<String, InvestigationDTO> investigations = new HashMap<>();
    private final Map<String, RiskAlertDTO> riskAlerts = new HashMap<>();
    private final Map<String, SuspiciousActivityDTO> suspiciousActivities = new HashMap<>();
    
    private final Random random = new Random(42); // Fixed seed for reproducible data

    public FraudRiskOperationsServiceImpl() {
        generateMockAmlKycCases();
        generateMockInvestigations();
        generateMockRiskAlerts();
        generateMockSuspiciousActivities();
    }

    @Override
    public Collection<AmlKycCaseDTO> getAmlKycCases() {
        return amlKycCases.values();
    }

    @Override
    public AmlKycCaseDTO getAmlKycCaseById(String id) {
        return amlKycCases.get(id);
    }

    @Override
    public Collection<InvestigationDTO> getInvestigations() {
        return investigations.values();
    }

    @Override
    public InvestigationDTO getInvestigationById(String id) {
        return investigations.get(id);
    }

    @Override
    public Collection<RiskAlertDTO> getRiskAlerts() {
        return riskAlerts.values();
    }

    @Override
    public RiskAlertDTO getRiskAlertById(String id) {
        return riskAlerts.get(id);
    }

    @Override
    public Collection<SuspiciousActivityDTO> getSuspiciousActivities() {
        return suspiciousActivities.values();
    }

    @Override
    public SuspiciousActivityDTO getSuspiciousActivityById(String id) {
        return suspiciousActivities.get(id);
    }

    private void generateMockAmlKycCases() {
        amlKycCases.clear();

        String[] customerIds = {"C10045", "C20056", "C30078", "C40023", "C50091", "C60112", "C70134", "C80156"};
        String[] customerNames = {
            "John Smith", 
            "Maria Garcia", 
            "Wei Chen", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] caseTypes = getCaseTypes();
        String[] statuses = getStatuses();
        String[] riskLevels = getRiskLevels();
        String[] assignees = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] regulatoryBodies = getRegulatoryBodies();

        String[] notes = {
            "Customer documentation incomplete, follow-up required",
            "Unusual transaction patterns detected, further investigation needed",
            "PEP status confirmed, enhanced due diligence in progress",
            "Sanctions screening alert, verification in progress",
            "Periodic review due to high-risk jurisdiction",
            "Missing source of funds documentation",
            "Suspicious activity report filed with authorities",
            "Beneficial ownership structure requires clarification"
        };

        for (int i = 1; i <= 50; i++) {
            String caseId = "AML" + String.format("%06d", i);
            
            int customerIndex = random.nextInt(customerIds.length);
            String customerId = customerIds[customerIndex];
            String customerName = customerNames[customerIndex];
            
            String caseType = caseTypes[random.nextInt(caseTypes.length)];
            String status = statuses[random.nextInt(statuses.length)];
            String riskLevel = riskLevels[random.nextInt(riskLevels.length)];
            String assignedTo = assignees[random.nextInt(assignees.length)];

            // Generate a random created date within the last 180 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime createdDate = now.minusDays(random.nextInt(180)).minusHours(random.nextInt(24));

            // Generate a random due date between created date and 30 days in the future
            long createdEpochDay = createdDate.toLocalDate().toEpochDay();
            long futureEpochDay = now.plusDays(30).toLocalDate().toEpochDay();
            long randomDay = createdEpochDay + random.nextInt((int) (futureEpochDay - createdEpochDay + 1));
            LocalDate dueDate = LocalDate.ofEpochDay(randomDay);

            String regulatoryBody = regulatoryBodies[random.nextInt(regulatoryBodies.length)];
            String note = notes[random.nextInt(notes.length)];

            AmlKycCaseDTO amlCase = new AmlKycCaseDTO(
                caseId, customerId, customerName, caseType, status, riskLevel, assignedTo,
                createdDate, dueDate, regulatoryBody, note
            );

            amlKycCases.put(caseId, amlCase);
        }
    }

    private void generateMockInvestigations() {
        investigations.clear();

        String[] subjects = {
            "Suspicious wire transfer pattern",
            "Multiple large cash deposits",
            "Unusual account activity",
            "Cross-border transaction anomalies",
            "Identity verification concerns",
            "Potential account takeover",
            "Suspected money laundering",
            "Potential fraud scheme"
        };

        String[] types = {
            "Fraud", 
            "Money Laundering", 
            "Terrorist Financing", 
            "Sanctions Violation", 
            "Identity Theft",
            "Account Compromise",
            "Internal Fraud",
            "Cyber Security"
        };

        String[] statuses = {
            "Open", 
            "In Progress", 
            "Pending Review", 
            "Escalated", 
            "Closed",
            "On Hold"
        };

        String[] priorities = {
            "High", 
            "Medium", 
            "Low"
        };

        String[] assignees = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] descriptions = {
            "Multiple high-value transactions from high-risk jurisdictions",
            "Pattern of structured deposits just below reporting threshold",
            "Account activity inconsistent with customer profile",
            "Transactions with sanctioned entities detected",
            "Unusual cross-border wire transfers with no clear business purpose",
            "Rapid movement of funds through multiple accounts",
            "Suspicious documentation provided for account opening",
            "Review of suspicious account opening documentation"
        };

        for (int i = 1; i <= 50; i++) {
            String caseId = "INV" + String.format("%06d", i);
            
            String subject = subjects[random.nextInt(subjects.length)];
            String type = types[random.nextInt(types.length)];
            String status = statuses[random.nextInt(statuses.length)];
            String priority = priorities[random.nextInt(priorities.length)];
            String assignedTo = assignees[random.nextInt(assignees.length)];

            // Generate a random opened date within the last 90 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime openedDate = now.minusDays(random.nextInt(90)).minusHours(random.nextInt(24));

            // Generate a random last updated date between opened date and now
            long openedEpochDay = openedDate.toLocalDate().toEpochDay();
            long nowEpochDay = now.toLocalDate().toEpochDay();
            long randomDay = openedEpochDay + random.nextInt((int) (nowEpochDay - openedEpochDay + 1));
            LocalDateTime lastUpdated = LocalDateTime.of(
                LocalDate.ofEpochDay(randomDay),
                java.time.LocalTime.of(random.nextInt(24), random.nextInt(60))
            );

            String description = descriptions[random.nextInt(descriptions.length)];

            InvestigationDTO investigation = new InvestigationDTO(
                caseId, subject, type, status, priority, assignedTo, openedDate, lastUpdated, description
            );

            investigations.put(caseId, investigation);
        }
    }

    private void generateMockRiskAlerts() {
        riskAlerts.clear();

        String[] alertTypes = {
            "Transaction Monitoring", 
            "Customer Due Diligence", 
            "Sanctions Screening", 
            "Adverse Media", 
            "PEP Screening",
            "Fraud Detection",
            "Behavioral Analytics",
            "Regulatory Reporting"
        };

        String[] statuses = {
            "New", 
            "Under Review", 
            "Escalated", 
            "Closed - False Positive", 
            "Closed - Action Taken",
            "Pending Additional Info"
        };

        String[] entityIds = {"E10045", "E20056", "E30078", "E40023", "E50091", "E60112", "E70134", "E80156"};
        String[] entityNames = {
            "Global Trading Ltd", 
            "Sunrise Investments", 
            "Tech Innovations Inc", 
            "Oceanwide Shipping", 
            "Desert Financial Group",
            "Northern Manufacturing",
            "Eastside Retail Chain",
            "Mountain Mining Corp"
        };
        String[] entityTypes = {
            "Customer", 
            "Transaction", 
            "Account", 
            "Employee", 
            "Vendor",
            "Partner",
            "Third Party",
            "Correspondent Bank"
        };

        String[] assignees = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] descriptions = {
            "Multiple high-risk transactions detected in short timeframe",
            "Potential match with sanctioned entity",
            "Unusual pattern of account activity detected",
            "Adverse media found related to entity",
            "Potential politically exposed person identified",
            "Suspicious transaction pattern identified",
            "Unusual cross-border activity detected",
            "Potential structuring activity identified"
        };

        for (int i = 1; i <= 50; i++) {
            String alertId = "ALERT" + String.format("%06d", i);
            
            String alertType = alertTypes[random.nextInt(alertTypes.length)];
            
            // Determine severity based on a weighted random approach
            String severity;
            int severityRandom = random.nextInt(10);
            if (severityRandom < 2) {
                severity = "High";
            } else if (severityRandom < 6) {
                severity = "Medium";
            } else {
                severity = "Low";
            }

            String status = statuses[random.nextInt(statuses.length)];

            int entityIndex = random.nextInt(entityIds.length);
            String entityId = entityIds[entityIndex];
            String entityName = entityNames[entityIndex];
            String entityType = entityTypes[entityIndex % entityTypes.length];

            // Generate a random generated date within the last 30 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime generatedDate = now.minusDays(random.nextInt(30)).minusHours(random.nextInt(24));

            String assignedTo = assignees[random.nextInt(assignees.length)];
            String description = descriptions[random.nextInt(descriptions.length)];

            RiskAlertDTO alert = new RiskAlertDTO(
                alertId, alertType, severity, status, entityId, entityName, entityType,
                generatedDate, assignedTo, description
            );

            riskAlerts.put(alertId, alert);
        }
    }

    private void generateMockSuspiciousActivities() {
        suspiciousActivities.clear();

        String[] customerIds = {"C10045", "C20056", "C30078", "C40023", "C50091", "C60112", "C70134", "C80156"};
        String[] customerNames = {
            "John Smith", 
            "Maria Garcia", 
            "Wei Chen", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };
        String[] accountNumbers = {
            "1234567890", 
            "2345678901", 
            "3456789012", 
            "4567890123", 
            "5678901234",
            "6789012345",
            "7890123456",
            "8901234567"
        };

        String[] activityTypes = {
            "Unusual Transaction", 
            "Structured Deposits", 
            "Large Cash Transaction", 
            "High-Risk Country Transfer", 
            "Account Behavior Change",
            "Multiple Rapid Transfers",
            "Identity Verification Issue",
            "Unusual Online Activity"
        };

        String[] riskLevels = {
            "High", 
            "Medium", 
            "Low"
        };

        String[] statuses = {
            "New", 
            "Under Investigation", 
            "Escalated", 
            "Reported to Authorities", 
            "Closed - Legitimate",
            "Closed - Suspicious Confirmed"
        };

        String[] descriptions = {
            "Multiple cash deposits just below reporting threshold",
            "Unusual wire transfers to high-risk jurisdictions",
            "Sudden large transactions inconsistent with customer profile",
            "Multiple transactions with sanctioned countries",
            "Rapid movement of funds through multiple accounts",
            "Unusual pattern of ATM withdrawals across multiple locations",
            "Transactions inconsistent with stated business purpose",
            "Unusual online banking access patterns detected"
        };

        for (int i = 1; i <= 50; i++) {
            String activityId = "ACT" + String.format("%06d", i);

            int customerIndex = random.nextInt(customerIds.length);
            String customerId = customerIds[customerIndex];
            String customerName = customerNames[customerIndex];
            String accountNumber = accountNumbers[random.nextInt(accountNumbers.length)];

            String activityType = activityTypes[random.nextInt(activityTypes.length)];
            String riskLevel = riskLevels[random.nextInt(riskLevels.length)];
            String status = statuses[random.nextInt(statuses.length)];

            // Generate a random date within the last 30 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime detectedDate = now.minusDays(random.nextInt(30)).minusHours(random.nextInt(24));

            // Generate a random amount between 100 and 10000
            double amount = 100 + random.nextInt(9900);

            String description = descriptions[random.nextInt(descriptions.length)];

            SuspiciousActivityDTO activity = new SuspiciousActivityDTO(
                activityId, customerId, customerName, accountNumber, activityType, riskLevel,
                status, detectedDate, amount, description
            );

            suspiciousActivities.put(activityId, activity);
        }
    }

    private String[] getCaseTypes() {
        return new String[] {
            "KYC Review", 
            "Enhanced Due Diligence", 
            "Suspicious Activity", 
            "PEP Review", 
            "Sanctions Screening",
            "Transaction Monitoring",
            "Adverse Media",
            "Periodic Review"
        };
    }

    private String[] getStatuses() {
        return new String[] {
            "Open", 
            "In Progress", 
            "Pending Documentation", 
            "Under Review", 
            "Escalated",
            "Closed"
        };
    }

    private String[] getRiskLevels() {
        return new String[] {
            "High", 
            "Medium", 
            "Low"
        };
    }

    private String[] getRegulatoryBodies() {
        return new String[] {
            "Financial Conduct Authority (FCA)", 
            "Financial Crimes Enforcement Network (FinCEN)", 
            "Office of Foreign Assets Control (OFAC)", 
            "Securities and Exchange Commission (SEC)", 
            "Prudential Regulation Authority (PRA)",
            "Financial Action Task Force (FATF)",
            "European Banking Authority (EBA)",
            "Monetary Authority of Singapore (MAS)"
        };
    }
}