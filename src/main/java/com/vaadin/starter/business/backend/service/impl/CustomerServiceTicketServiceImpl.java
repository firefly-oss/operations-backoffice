package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.customerservice.ServiceTicketDTO;
import com.vaadin.starter.business.backend.service.CustomerServiceTicketService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Implementation of the CustomerServiceTicketService interface.
 * Provides mock data for demonstration purposes.
 */
@Service
public class CustomerServiceTicketServiceImpl implements CustomerServiceTicketService {

    private static final String[] SUBJECTS = {
            "Installation service request", "Repair service needed", "Maintenance appointment",
            "Product troubleshooting", "Equipment upgrade", "Technical consultation",
            "On-site support request", "Training session", "Product setup assistance",
            "Warranty service", "Equipment inspection", "Software installation"
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
    
    private static final String[] DEPARTMENTS = {
            "Customer Service", "Technical Support", "Field Services", "Installations",
            "Repairs", "Maintenance", "Training", "Product Support", "Warranty Services"
    };
    
    private static final String[] RESOLUTIONS = {
            "Service completed successfully",
            "Equipment repaired and tested",
            "Software installed and configured",
            "Training provided to customer",
            "Maintenance performed as scheduled",
            "Upgrade completed successfully",
            "Consultation provided with recommendations",
            "Issue diagnosed and fixed",
            "Parts replaced under warranty",
            "Customer provided with workaround solution"
    };

    private final Random random = new Random();
    private final Map<String, ServiceTicketDTO> serviceTickets = new HashMap<>();

    public CustomerServiceTicketServiceImpl() {
        generateMockServiceTickets();
    }

    /**
     * Get all service tickets.
     *
     * @return List of all service tickets
     */
    @Override
    public List<ServiceTicketDTO> getServiceTickets() {
        return new ArrayList<>(serviceTickets.values());
    }

    /**
     * Get a service ticket by ID.
     *
     * @param id Service ticket ID
     * @return Service ticket with the given ID, or null if not found
     */
    @Override
    public ServiceTicketDTO getServiceTicketById(String id) {
        return serviceTickets.get(id);
    }

    /**
     * Generate mock service ticket data.
     */
    private void generateMockServiceTickets() {
        // Clear existing service tickets
        serviceTickets.clear();

        // Generate 50 random service tickets
        for (int i = 0; i < 50; i++) {
            String id = UUID.randomUUID().toString().substring(0, 8);
            
            // Set ticket number
            String ticketNumber = "TCKT-" + (10000 + i);
            
            // Set random subject
            String subject = SUBJECTS[random.nextInt(SUBJECTS.length)];
            
            // Set random description
            String description = "Detailed description for service ticket: " + subject;
            
            // Set random status
            ServiceTicketDTO.Status[] statuses = ServiceTicketDTO.Status.values();
            String status = statuses[random.nextInt(statuses.length)].getName();
            
            // Set random priority
            ServiceTicketDTO.Priority[] priorities = ServiceTicketDTO.Priority.values();
            String priority = priorities[random.nextInt(priorities.length)].getName();
            
            // Set random category
            ServiceTicketDTO.Category[] categories = ServiceTicketDTO.Category.values();
            String category = categories[random.nextInt(categories.length)].getName();
            
            // Set random service type
            ServiceTicketDTO.ServiceType[] serviceTypes = ServiceTicketDTO.ServiceType.values();
            String serviceType = serviceTypes[random.nextInt(serviceTypes.length)].getName();
            
            // Set random customer ID and name
            String customerId = "CUST-" + (1000 + random.nextInt(9000));
            String customerName = CUSTOMER_NAMES[random.nextInt(CUSTOMER_NAMES.length)];
            
            // Set random assigned to
            String assignedTo = ASSIGNED_TO[random.nextInt(ASSIGNED_TO.length)];
            
            // Set random department
            String department = DEPARTMENTS[random.nextInt(DEPARTMENTS.length)];
            
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
            
            // Set random resolved date and resolution (only for resolved or closed tickets)
            LocalDateTime resolvedDate = null;
            String resolution = null;
            if (status.equals(ServiceTicketDTO.Status.RESOLVED.getName()) || 
                status.equals(ServiceTicketDTO.Status.CLOSED.getName())) {
                resolvedDate = lastUpdatedDate;
                resolution = RESOLUTIONS[random.nextInt(RESOLUTIONS.length)];
            }
            
            // Create service ticket DTO
            ServiceTicketDTO ticket = new ServiceTicketDTO(
                id, ticketNumber, subject, description, status, priority, category, serviceType,
                customerId, customerName, assignedTo, department, createdDate, lastUpdatedDate,
                dueDate, resolvedDate, resolution
            );
            
            // Add to map
            serviceTickets.put(id, ticket);
        }
    }
}