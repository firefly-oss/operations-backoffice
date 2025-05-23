package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.ServiceTicket;
import com.vaadin.starter.business.backend.service.ServiceTicketService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the ServiceTicketService interface.
 */
@Service
public class ServiceTicketServiceImpl implements ServiceTicketService {
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
    private final List<ServiceTicket> serviceTickets = new ArrayList<>();

    public ServiceTicketServiceImpl() {
        generateMockServiceTickets();
    }

    /**
     * Get all service tickets.
     *
     * @return List of all service tickets
     */
    @Override
    public List<ServiceTicket> getServiceTickets() {
        return serviceTickets;
    }

    /**
     * Get a service ticket by ID.
     *
     * @param id Service ticket ID
     * @return Service ticket with the given ID, or null if not found
     */
    @Override
    public ServiceTicket getServiceTicketById(String id) {
        return serviceTickets.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Generate mock service ticket data.
     */
    private void generateMockServiceTickets() {
        // Clear existing service tickets
        serviceTickets.clear();

        // Generate 50 random service tickets
        for (int i = 0; i < 50; i++) {
            ServiceTicket ticket = new ServiceTicket();
            
            // Set ticket number
            ticket.setTicketNumber("TCKT-" + (10000 + i));
            
            // Set random subject
            ticket.setSubject(SUBJECTS[random.nextInt(SUBJECTS.length)]);
            
            // Set random description
            ticket.setDescription("Detailed description for service ticket: " + ticket.getSubject());
            
            // Set random status
            ServiceTicket.Status[] statuses = ServiceTicket.Status.values();
            ticket.setStatus(statuses[random.nextInt(statuses.length)].getName());
            
            // Set random priority
            ServiceTicket.Priority[] priorities = ServiceTicket.Priority.values();
            ticket.setPriority(priorities[random.nextInt(priorities.length)].getName());
            
            // Set random category
            ServiceTicket.Category[] categories = ServiceTicket.Category.values();
            ticket.setCategory(categories[random.nextInt(categories.length)].getName());
            
            // Set random service type
            ServiceTicket.ServiceType[] serviceTypes = ServiceTicket.ServiceType.values();
            ticket.setServiceType(serviceTypes[random.nextInt(serviceTypes.length)].getName());
            
            // Set random customer ID and name
            ticket.setCustomerId("CUST-" + (1000 + random.nextInt(9000)));
            ticket.setCustomerName(CUSTOMER_NAMES[random.nextInt(CUSTOMER_NAMES.length)]);
            
            // Set random assigned to
            ticket.setAssignedTo(ASSIGNED_TO[random.nextInt(ASSIGNED_TO.length)]);
            
            // Set random department
            ticket.setDepartment(DEPARTMENTS[random.nextInt(DEPARTMENTS.length)]);
            
            // Set random created date (within the last 30 days)
            LocalDateTime now = LocalDateTime.now();
            int daysBack = random.nextInt(30);
            int hoursBack = random.nextInt(24);
            int minutesBack = random.nextInt(60);
            LocalDateTime createdDate = now.minusDays(daysBack).minusHours(hoursBack).minusMinutes(minutesBack);
            ticket.setCreatedDate(createdDate);
            
            // Set random last updated date (after created date)
            int daysAfter = random.nextInt(daysBack + 1);
            int hoursAfter = random.nextInt(24);
            int minutesAfter = random.nextInt(60);
            LocalDateTime lastUpdatedDate = createdDate.plusDays(daysAfter).plusHours(hoursAfter).plusMinutes(minutesAfter);
            ticket.setLastUpdatedDate(lastUpdatedDate);
            
            // Set random due date (within the next 14 days from created date)
            int dueDays = random.nextInt(14) + 1;
            ticket.setDueDate(createdDate.plusDays(dueDays));
            
            // Set random resolved date (only for resolved or closed tickets)
            if (ticket.getStatus().equals(ServiceTicket.Status.RESOLVED.getName()) || 
                ticket.getStatus().equals(ServiceTicket.Status.CLOSED.getName())) {
                ticket.setResolvedDate(lastUpdatedDate);
                
                // Set random resolution
                ticket.setResolution(RESOLUTIONS[random.nextInt(RESOLUTIONS.length)]);
            }
            
            // Add to list
            serviceTickets.add(ticket);
        }
    }
}