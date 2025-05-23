package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.customerservice.IncidentDTO;
import com.vaadin.starter.business.backend.service.CustomerIncidentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Implementation of the CustomerIncidentService interface.
 * Provides mock data for demonstration purposes.
 */
@Service
public class CustomerIncidentServiceImpl implements CustomerIncidentService {

    private static final String[] TITLES = {
            "System outage", "Network connectivity issue", "Database performance degradation",
            "Application error", "Security breach", "Service unavailable",
            "API failure", "Data corruption", "Hardware failure",
            "Software bug", "Performance issue", "Integration failure"
    };
    
    private static final String[] REPORTED_BY = {
            "System Monitor", "Help Desk", "Customer Support", "Operations Team", "Security Team",
            "Network Operations", "Database Team", "Application Support", "End User", "Automated Alert"
    };
    
    private static final String[] ASSIGNED_TO = {
            "Alice Cooper", "Bob Richards", "Carol White", "David Black", "Eve Green",
            "Frank Blue", "Grace Purple", "Henry Orange", "Ivy Yellow", "Jack Red"
    };
    
    private static final String[] AFFECTED_SERVICES = {
            "Online Banking", "Mobile App", "Payment Processing", "Customer Portal",
            "Internal CRM", "Email Service", "Authentication System", "Reporting System",
            "Data Warehouse", "API Gateway", "Web Server", "Database Server"
    };
    
    private static final String[] RESOLUTIONS = {
            "Restarted service",
            "Applied security patch",
            "Fixed software bug",
            "Restored from backup",
            "Increased system resources",
            "Reconfigured network settings",
            "Replaced faulty hardware",
            "Optimized database queries",
            "Rolled back recent changes",
            "Implemented workaround solution"
    };
    
    private static final String[] ROOT_CAUSES = {
            "Software bug",
            "Hardware failure",
            "Network congestion",
            "Database corruption",
            "Insufficient resources",
            "Configuration error",
            "Third-party service failure",
            "Security vulnerability",
            "Human error",
            "Unexpected data volume"
    };

    private final Random random = new Random();
    private final Map<String, IncidentDTO> incidents = new HashMap<>();

    public CustomerIncidentServiceImpl() {
        generateMockIncidents();
    }

    /**
     * Get all incidents.
     *
     * @return List of all incidents
     */
    @Override
    public List<IncidentDTO> getIncidents() {
        return new ArrayList<>(incidents.values());
    }

    /**
     * Get an incident by ID.
     *
     * @param id Incident ID
     * @return Incident with the given ID, or null if not found
     */
    @Override
    public IncidentDTO getIncidentById(String id) {
        return incidents.get(id);
    }

    /**
     * Generate mock incident data.
     */
    private void generateMockIncidents() {
        // Clear existing incidents
        incidents.clear();

        // Generate 50 random incidents
        for (int i = 0; i < 50; i++) {
            String id = UUID.randomUUID().toString().substring(0, 8);
            
            // Set incident number
            String incidentNumber = "INC-" + (10000 + i);
            
            // Set random title
            String title = TITLES[random.nextInt(TITLES.length)];
            
            // Set random description
            String description = "Detailed description for incident: " + title;
            
            // Set random status
            IncidentDTO.Status[] statuses = IncidentDTO.Status.values();
            String status = statuses[random.nextInt(statuses.length)].getName();
            
            // Set random priority
            IncidentDTO.Priority[] priorities = IncidentDTO.Priority.values();
            String priority = priorities[random.nextInt(priorities.length)].getName();
            
            // Set random impact
            IncidentDTO.Impact[] impacts = IncidentDTO.Impact.values();
            String impact = impacts[random.nextInt(impacts.length)].getName();
            
            // Set random category
            IncidentDTO.Category[] categories = IncidentDTO.Category.values();
            String category = categories[random.nextInt(categories.length)].getName();
            
            // Set random affected service
            String affectedService = AFFECTED_SERVICES[random.nextInt(AFFECTED_SERVICES.length)];
            
            // Set random reported by
            String reportedBy = REPORTED_BY[random.nextInt(REPORTED_BY.length)];
            
            // Set random assigned to
            String assignedTo = ASSIGNED_TO[random.nextInt(ASSIGNED_TO.length)];
            
            // Set random reported date (within the last 30 days)
            LocalDateTime now = LocalDateTime.now();
            int daysBack = random.nextInt(30);
            int hoursBack = random.nextInt(24);
            int minutesBack = random.nextInt(60);
            LocalDateTime reportedDate = now.minusDays(daysBack).minusHours(hoursBack).minusMinutes(minutesBack);
            
            // Set random last updated date (after reported date)
            int daysAfter = random.nextInt(daysBack + 1);
            int hoursAfter = random.nextInt(24);
            int minutesAfter = random.nextInt(60);
            LocalDateTime lastUpdatedDate = reportedDate.plusDays(daysAfter).plusHours(hoursAfter).plusMinutes(minutesAfter);
            
            // Set random resolved date (only for resolved or closed incidents)
            LocalDateTime resolvedDate = null;
            String resolution = null;
            String rootCause = null;
            if (status.equals(IncidentDTO.Status.RESOLVED.getName()) || 
                status.equals(IncidentDTO.Status.CLOSED.getName())) {
                resolvedDate = lastUpdatedDate;
                resolution = RESOLUTIONS[random.nextInt(RESOLUTIONS.length)];
                rootCause = ROOT_CAUSES[random.nextInt(ROOT_CAUSES.length)];
            }
            
            // Create incident DTO
            IncidentDTO incident = new IncidentDTO(
                id, incidentNumber, title, description, status, priority, impact, category,
                affectedService, reportedBy, assignedTo, reportedDate, lastUpdatedDate,
                resolvedDate, resolution, rootCause
            );
            
            // Add to map
            incidents.put(id, incident);
        }
    }
}