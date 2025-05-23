package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.customerservice.CustomerRequestDTO;
import com.vaadin.starter.business.backend.service.CustomerRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Implementation of the CustomerRequestService interface.
 * Provides mock data for demonstration purposes.
 */
@Service
public class CustomerRequestServiceImpl implements CustomerRequestService {

    private static final String[] SUBJECTS = {
            "Account information update", "Statement request", "Address change",
            "Card activation", "Transaction dispute", "Fee waiver request",
            "Beneficiary addition", "Online banking access", "Loan information",
            "Interest rate inquiry", "Account statement frequency", "Contact details update"
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
            "Request completed as per customer instructions",
            "Information provided to customer",
            "Account updated as requested",
            "Documents sent to customer",
            "Changes applied to customer profile",
            "Request processed successfully",
            "Customer informed about policy",
            "Request approved and processed",
            "Request denied due to policy restrictions",
            "Alternative solution provided to customer"
    };

    private final Random random = new Random();
    private final Map<String, CustomerRequestDTO> customerRequests = new HashMap<>();

    public CustomerRequestServiceImpl() {
        generateMockCustomerRequests();
    }

    /**
     * Get all customer requests.
     *
     * @return Collection of all customer requests
     */
    @Override
    public Collection<CustomerRequestDTO> getCustomerRequests() {
        return customerRequests.values();
    }

    /**
     * Get a customer request by ID.
     *
     * @param id Customer request ID
     * @return Customer request with the given ID, or null if not found
     */
    @Override
    public CustomerRequestDTO getCustomerRequestById(String id) {
        return customerRequests.get(id);
    }

    /**
     * Generate mock customer request data.
     */
    private void generateMockCustomerRequests() {
        // Clear existing customer requests
        customerRequests.clear();

        // Generate 50 random customer requests
        for (int i = 0; i < 50; i++) {
            String id = UUID.randomUUID().toString().substring(0, 8);
            
            // Set request number
            String requestNumber = "REQ-" + (10000 + i);
            
            // Set random subject
            String subject = SUBJECTS[random.nextInt(SUBJECTS.length)];
            
            // Set random description
            String description = "Detailed description for request: " + subject;
            
            // Set random status
            CustomerRequestDTO.Status[] statuses = CustomerRequestDTO.Status.values();
            String status = statuses[random.nextInt(statuses.length)].getName();
            
            // Set random priority
            CustomerRequestDTO.Priority[] priorities = CustomerRequestDTO.Priority.values();
            String priority = priorities[random.nextInt(priorities.length)].getName();
            
            // Set random type
            CustomerRequestDTO.Type[] types = CustomerRequestDTO.Type.values();
            String type = types[random.nextInt(types.length)].getName();
            
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
            
            // Set random completion date and resolution (only for completed requests)
            LocalDateTime completionDate = null;
            String resolution = null;
            if (status.equals(CustomerRequestDTO.Status.COMPLETED.getName())) {
                completionDate = lastUpdatedDate;
                resolution = RESOLUTIONS[random.nextInt(RESOLUTIONS.length)];
            }
            
            // Set random channel
            CustomerRequestDTO.Channel[] channels = CustomerRequestDTO.Channel.values();
            String channel = channels[random.nextInt(channels.length)].getName();
            
            // Create customer request DTO
            CustomerRequestDTO request = new CustomerRequestDTO(
                id, requestNumber, subject, description, status, priority, type,
                customerId, customerName, assignedTo, createdDate, lastUpdatedDate,
                completionDate, resolution, channel
            );
            
            // Add to map
            customerRequests.put(id, request);
        }
    }
}