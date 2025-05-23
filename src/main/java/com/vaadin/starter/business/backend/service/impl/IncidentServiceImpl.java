package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.Incident;
import com.vaadin.starter.business.backend.service.IncidentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the IncidentService interface.
 */
@Service
public class IncidentServiceImpl implements IncidentService {
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
    private final List<Incident> incidents = new ArrayList<>();

    public IncidentServiceImpl() {
        generateMockIncidents();
    }

    /**
     * Get all incidents.
     *
     * @return List of all incidents
     */
    @Override
    public List<Incident> getIncidents() {
        return incidents;
    }

    /**
     * Get an incident by ID.
     *
     * @param id Incident ID
     * @return Incident with the given ID, or null if not found
     */
    @Override
    public Incident getIncidentById(String id) {
        return incidents.stream()
                .filter(incident -> incident.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Generate mock incident data.
     */
    private void generateMockIncidents() {
        // Clear existing incidents
        incidents.clear();

        // Generate 50 random incidents
        for (int i = 0; i < 50; i++) {
            Incident incident = new Incident();
            
            // Set incident number
            incident.setIncidentNumber("INC-" + (10000 + i));
            
            // Set random title
            incident.setTitle(TITLES[random.nextInt(TITLES.length)]);
            
            // Set random description
            incident.setDescription("Detailed description for incident: " + incident.getTitle());
            
            // Set random status
            Incident.Status[] statuses = Incident.Status.values();
            incident.setStatus(statuses[random.nextInt(statuses.length)].getName());
            
            // Set random priority
            Incident.Priority[] priorities = Incident.Priority.values();
            incident.setPriority(priorities[random.nextInt(priorities.length)].getName());
            
            // Set random impact
            Incident.Impact[] impacts = Incident.Impact.values();
            incident.setImpact(impacts[random.nextInt(impacts.length)].getName());
            
            // Set random category
            Incident.Category[] categories = Incident.Category.values();
            incident.setCategory(categories[random.nextInt(categories.length)].getName());
            
            // Set random affected service
            incident.setAffectedService(AFFECTED_SERVICES[random.nextInt(AFFECTED_SERVICES.length)]);
            
            // Set random reported by
            incident.setReportedBy(REPORTED_BY[random.nextInt(REPORTED_BY.length)]);
            
            // Set random assigned to
            incident.setAssignedTo(ASSIGNED_TO[random.nextInt(ASSIGNED_TO.length)]);
            
            // Set random reported date (within the last 30 days)
            LocalDateTime now = LocalDateTime.now();
            int daysBack = random.nextInt(30);
            int hoursBack = random.nextInt(24);
            int minutesBack = random.nextInt(60);
            LocalDateTime reportedDate = now.minusDays(daysBack).minusHours(hoursBack).minusMinutes(minutesBack);
            incident.setReportedDate(reportedDate);
            
            // Set random last updated date (after reported date)
            int daysAfter = random.nextInt(daysBack + 1);
            int hoursAfter = random.nextInt(24);
            int minutesAfter = random.nextInt(60);
            LocalDateTime lastUpdatedDate = reportedDate.plusDays(daysAfter).plusHours(hoursAfter).plusMinutes(minutesAfter);
            incident.setLastUpdatedDate(lastUpdatedDate);
            
            // Set random resolved date (only for resolved or closed incidents)
            if (incident.getStatus().equals(Incident.Status.RESOLVED.getName()) || 
                incident.getStatus().equals(Incident.Status.CLOSED.getName())) {
                incident.setResolvedDate(lastUpdatedDate);
                
                // Set random resolution and root cause
                incident.setResolution(RESOLUTIONS[random.nextInt(RESOLUTIONS.length)]);
                incident.setRootCause(ROOT_CAUSES[random.nextInt(ROOT_CAUSES.length)]);
            }
            
            // Add to list
            incidents.add(incident);
        }
    }
}