package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.customerservice.CaseDTO;
import com.vaadin.starter.business.backend.service.CustomerServiceCaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Implementation of the CustomerServiceCaseService interface.
 * Provides mock data for demonstration purposes.
 */
@Service
public class CustomerServiceCaseServiceImpl implements CustomerServiceCaseService {

    private static final String[] SUBJECTS = {
            "Account access issue", "Billing dispute", "Product information request",
            "Service interruption", "Feature request", "Technical issue",
            "Complaint about service", "Upgrade inquiry", "Cancellation request",
            "General inquiry", "Password reset", "Account update"
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
            "Issue resolved to customer's satisfaction",
            "Account updated as requested",
            "Information provided to customer",
            "Technical issue fixed",
            "Refund processed",
            "Service restored",
            "Feature request submitted to product team",
            "Customer provided with workaround",
            "Escalated to management for review",
            "Customer complaint addressed"
    };

    private final Random random = new Random();
    private final Map<String, CaseDTO> cases = new HashMap<>();

    public CustomerServiceCaseServiceImpl() {
        generateMockCases();
    }

    /**
     * Get all cases.
     *
     * @return Collection of all cases
     */
    @Override
    public Collection<CaseDTO> getCases() {
        return cases.values();
    }

    /**
     * Get a case by ID.
     *
     * @param id Case ID
     * @return Case with the given ID, or null if not found
     */
    @Override
    public CaseDTO getCaseById(String id) {
        return cases.get(id);
    }

    /**
     * Generate mock case data.
     */
    private void generateMockCases() {
        // Clear existing cases
        cases.clear();

        // Generate 50 random cases
        for (int i = 0; i < 50; i++) {
            String id = UUID.randomUUID().toString().substring(0, 8);
            
            // Set case number
            String caseNumber = "CASE-" + (10000 + i);
            
            // Set random subject
            String subject = SUBJECTS[random.nextInt(SUBJECTS.length)];
            
            // Set random description
            String description = "Detailed description for case: " + subject;
            
            // Set random status
            CaseDTO.Status[] statuses = CaseDTO.Status.values();
            String status = statuses[random.nextInt(statuses.length)].getName();
            
            // Set random priority
            CaseDTO.Priority[] priorities = CaseDTO.Priority.values();
            String priority = priorities[random.nextInt(priorities.length)].getName();
            
            // Set random category
            CaseDTO.Category[] categories = CaseDTO.Category.values();
            String category = categories[random.nextInt(categories.length)].getName();
            
            // Set random customer ID and name
            String customerId = "CUST-" + (1000 + random.nextInt(9000));
            String customerName = CUSTOMER_NAMES[random.nextInt(CUSTOMER_NAMES.length)];
            
            // Set random assigned to
            String assignedTo = ASSIGNED_TO[random.nextInt(ASSIGNED_TO.length)];
            
            // Set random created date (within the last 30 days)
            LocalDateTime now = LocalDateTime.now();
            int daysBack = random.nextInt(30);
            int hoursBack = random.nextInt(24);
            int minutesBack = random.nextInt(60);
            LocalDateTime createdDate = now.minusDays(daysBack).minusHours(hoursBack).minusMinutes(minutesBack);
            
            // Set random last updated date (after created date)
            int daysAfter = random.nextInt(daysBack + 1);
            int hoursAfter = random.nextInt(24);
            int minutesAfter = random.nextInt(60);
            LocalDateTime lastUpdatedDate = createdDate.plusDays(daysAfter).plusHours(hoursAfter).plusMinutes(minutesAfter);
            
            // Set random due date (within the next 14 days from created date)
            int dueDays = random.nextInt(14) + 1;
            LocalDateTime dueDate = createdDate.plusDays(dueDays);
            
            // Set random closed date and resolution (only for resolved or closed cases)
            LocalDateTime closedDate = null;
            String resolution = null;
            if (status.equals(CaseDTO.Status.RESOLVED.getName()) || 
                status.equals(CaseDTO.Status.CLOSED.getName())) {
                closedDate = lastUpdatedDate;
                resolution = RESOLUTIONS[random.nextInt(RESOLUTIONS.length)];
            }
            
            // Set random source
            CaseDTO.Source[] sources = CaseDTO.Source.values();
            String source = sources[random.nextInt(sources.length)].getName();
            
            // Create case DTO
            CaseDTO caseDTO = new CaseDTO(
                id, caseNumber, subject, description, status, priority, category,
                customerId, customerName, assignedTo, createdDate, lastUpdatedDate,
                dueDate, closedDate, resolution, source
            );
            
            // Add to map
            cases.put(id, caseDTO);
        }
    }
}