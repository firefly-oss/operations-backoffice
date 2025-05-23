package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.CustomerRequest;
import com.vaadin.starter.business.backend.service.CustomerRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the CustomerRequestService interface.
 */
@Service
public class CustomerRequestServiceImpl implements CustomerRequestService {
    private static final String[] SUBJECTS = {
            "Request for account statement", "Change of address", "Update contact information",
            "Request for product information", "Change payment method", "Request for document copy",
            "Subscription upgrade request", "Account closure request", "Service plan change",
            "Request for tax documents", "Password reset assistance", "Account access request"
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
            "Processed request as per customer instructions",
            "Updated customer information in the system",
            "Provided requested documents to customer",
            "Changed service plan as requested",
            "Processed account closure",
            "Updated payment method in the system",
            "Upgraded subscription as requested",
            "Sent requested information to customer",
            "Processed address change",
            "Reset password and provided instructions to customer"
    };

    private final Random random = new Random();
    private final List<CustomerRequest> customerRequests = new ArrayList<>();

    public CustomerRequestServiceImpl() {
        generateMockCustomerRequests();
    }

    /**
     * Get all customer requests.
     *
     * @return List of all customer requests
     */
    @Override
    public List<CustomerRequest> getCustomerRequests() {
        return customerRequests;
    }

    /**
     * Get a customer request by ID.
     *
     * @param id Customer request ID
     * @return Customer request with the given ID, or null if not found
     */
    @Override
    public CustomerRequest getCustomerRequestById(String id) {
        return customerRequests.stream()
                .filter(request -> request.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Generate mock customer request data.
     */
    private void generateMockCustomerRequests() {
        // Clear existing customer requests
        customerRequests.clear();

        // Generate 50 random customer requests
        for (int i = 0; i < 50; i++) {
            CustomerRequest request = new CustomerRequest();
            
            // Set request number
            request.setRequestNumber("REQ-" + (10000 + i));
            
            // Set random subject
            request.setSubject(SUBJECTS[random.nextInt(SUBJECTS.length)]);
            
            // Set random description
            request.setDescription("Detailed description for request: " + request.getSubject());
            
            // Set random status
            CustomerRequest.Status[] statuses = CustomerRequest.Status.values();
            request.setStatus(statuses[random.nextInt(statuses.length)].getName());
            
            // Set random priority
            CustomerRequest.Priority[] priorities = CustomerRequest.Priority.values();
            request.setPriority(priorities[random.nextInt(priorities.length)].getName());
            
            // Set random type
            CustomerRequest.Type[] types = CustomerRequest.Type.values();
            request.setType(types[random.nextInt(types.length)].getName());
            
            // Set random customer ID and name
            request.setCustomerId("CUST-" + (1000 + random.nextInt(9000)));
            request.setCustomerName(CUSTOMER_NAMES[random.nextInt(CUSTOMER_NAMES.length)]);
            
            // Set random assigned to
            request.setAssignedTo(ASSIGNED_TO[random.nextInt(ASSIGNED_TO.length)]);
            
            // Set random created date (within the last 30 days)
            LocalDateTime now = LocalDateTime.now();
            int daysBack = random.nextInt(30);
            int hoursBack = random.nextInt(24);
            int minutesBack = random.nextInt(60);
            LocalDateTime createdDate = now.minusDays(daysBack).minusHours(hoursBack).minusMinutes(minutesBack);
            request.setCreatedDate(createdDate);
            
            // Set random last modified date (after created date)
            int daysAfter = random.nextInt(daysBack + 1);
            int hoursAfter = random.nextInt(24);
            int minutesAfter = random.nextInt(60);
            LocalDateTime lastModifiedDate = createdDate.plusDays(daysAfter).plusHours(hoursAfter).plusMinutes(minutesAfter);
            request.setLastModifiedDate(lastModifiedDate);
            
            // Set random completion date (only for completed requests)
            if (request.getStatus().equals(CustomerRequest.Status.COMPLETED.getName())) {
                request.setCompletionDate(lastModifiedDate);
            }
            
            // Set random resolution (only for completed or cancelled requests)
            if (request.getStatus().equals(CustomerRequest.Status.COMPLETED.getName()) || 
                request.getStatus().equals(CustomerRequest.Status.CANCELLED.getName())) {
                request.setResolution(RESOLUTIONS[random.nextInt(RESOLUTIONS.length)]);
            }
            
            // Set random channel
            CustomerRequest.Channel[] channels = CustomerRequest.Channel.values();
            request.setChannel(channels[random.nextInt(channels.length)].getName());
            
            // Add to list
            customerRequests.add(request);
        }
    }
}