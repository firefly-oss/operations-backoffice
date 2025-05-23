package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.Case;
import com.vaadin.starter.business.backend.service.CaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the CaseService interface.
 */
@Service
public class CaseServiceImpl implements CaseService {
    private static final String[] SUBJECTS = {
            "Account access issue", "Billing discrepancy", "Product information request",
            "Service interruption", "Update account details", "Payment issue",
            "Technical support", "Complaint about service", "Refund request",
            "Product malfunction", "Subscription cancellation", "General inquiry"
    };
    
    private static final String[] CUSTOMER_NAMES = {
            "John Smith", "Emma Johnson", "Michael Brown", "Sarah Davis", "Robert Wilson",
            "Jennifer Lee", "David Miller", "Lisa Anderson", "James Taylor", "Patricia Moore",
            "Corporate Solutions Inc.", "Global Enterprises Ltd.", "Tech Innovations LLC"
    };
    
    private static final String[] ASSIGNED_TO = {
            "Alice Cooper", "Bob Richards", "Carol White", "David Black", "Eve Green",
            "Frank Blue", "Grace Purple", "Henry Orange", "Ivy Yellow", "Jack Red"
    };
    
    private static final String[] RESOLUTIONS = {
            "Issue resolved by updating account settings",
            "Provided customer with requested information",
            "Escalated to technical team for further investigation",
            "Processed refund as requested",
            "Replaced defective product",
            "Updated billing information",
            "Resolved technical issue with customer's account",
            "Provided workaround solution",
            "No action required - informational only",
            "Customer withdrew complaint after explanation"
    };

    private final Random random = new Random();
    private final List<Case> cases = new ArrayList<>();

    public CaseServiceImpl() {
        generateMockCases();
    }

    /**
     * Get all cases.
     *
     * @return List of all cases
     */
    @Override
    public List<Case> getCases() {
        return cases;
    }

    /**
     * Get a case by ID.
     *
     * @param id Case ID
     * @return Case with the given ID, or null if not found
     */
    @Override
    public Case getCaseById(String id) {
        return cases.stream()
                .filter(caseItem -> caseItem.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Generate mock case data.
     */
    private void generateMockCases() {
        // Clear existing cases
        cases.clear();

        // Generate 50 random cases
        for (int i = 0; i < 50; i++) {
            Case caseItem = new Case();
            
            // Set case number
            caseItem.setCaseNumber("CASE-" + (10000 + i));
            
            // Set random subject
            caseItem.setSubject(SUBJECTS[random.nextInt(SUBJECTS.length)]);
            
            // Set random description
            caseItem.setDescription("Detailed description for case: " + caseItem.getSubject());
            
            // Set random status
            Case.Status[] statuses = Case.Status.values();
            caseItem.setStatus(statuses[random.nextInt(statuses.length)].getName());
            
            // Set random priority
            Case.Priority[] priorities = Case.Priority.values();
            caseItem.setPriority(priorities[random.nextInt(priorities.length)].getName());
            
            // Set random category
            Case.Category[] categories = Case.Category.values();
            caseItem.setCategory(categories[random.nextInt(categories.length)].getName());
            
            // Set random customer ID and name
            caseItem.setCustomerId("CUST-" + (1000 + random.nextInt(9000)));
            caseItem.setCustomerName(CUSTOMER_NAMES[random.nextInt(CUSTOMER_NAMES.length)]);
            
            // Set random assigned to
            caseItem.setAssignedTo(ASSIGNED_TO[random.nextInt(ASSIGNED_TO.length)]);
            
            // Set random created date (within the last 30 days)
            LocalDateTime now = LocalDateTime.now();
            int daysBack = random.nextInt(30);
            int hoursBack = random.nextInt(24);
            int minutesBack = random.nextInt(60);
            LocalDateTime createdDate = now.minusDays(daysBack).minusHours(hoursBack).minusMinutes(minutesBack);
            caseItem.setCreatedDate(createdDate);
            
            // Set random last modified date (after created date)
            int daysAfter = random.nextInt(daysBack + 1);
            int hoursAfter = random.nextInt(24);
            int minutesAfter = random.nextInt(60);
            LocalDateTime lastModifiedDate = createdDate.plusDays(daysAfter).plusHours(hoursAfter).plusMinutes(minutesAfter);
            caseItem.setLastModifiedDate(lastModifiedDate);
            
            // Set random due date (within the next 14 days from last modified)
            int dueDays = random.nextInt(14) + 1;
            caseItem.setDueDate(lastModifiedDate.plusDays(dueDays));
            
            // Set random resolution (only for resolved or closed cases)
            if (caseItem.getStatus().equals(Case.Status.RESOLVED.getName()) || 
                caseItem.getStatus().equals(Case.Status.CLOSED.getName())) {
                caseItem.setResolution(RESOLUTIONS[random.nextInt(RESOLUTIONS.length)]);
            }
            
            // Set random source
            Case.Source[] sources = Case.Source.values();
            caseItem.setSource(sources[random.nextInt(sources.length)].getName());
            
            // Add to list
            cases.add(caseItem);
        }
    }
}