package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.taskmanagement.*;

import java.util.List;

/**
 * Service interface for task management operations.
 */
public interface TaskManagementService {
    
    /**
     * Get all tasks for the work queue.
     *
     * @param count the number of tasks to generate
     * @return List of task DTOs
     */
    List<TaskDTO> getTasks(int count);
    
    /**
     * Get all SLA tasks.
     *
     * @param count the number of tasks to generate
     * @return List of SLA task DTOs
     */
    List<SLATaskDTO> getSLATasks(int count);
    
    /**
     * Get all SLA policies.
     *
     * @return List of SLA policy DTOs
     */
    List<SLAPolicyDTO> getSLAPolicies();
    
    /**
     * Get all notification settings.
     *
     * @return List of notification setting DTOs
     */
    List<NotificationSettingDTO> getNotificationSettings();
    
    /**
     * Get all team member performance data.
     *
     * @return List of team member performance DTOs
     */
    List<TeamMemberPerformanceDTO> getTeamMemberPerformance();
    
    /**
     * Get all team performance data.
     *
     * @return List of team performance data DTOs
     */
    List<TeamPerformanceDataDTO> getTeamPerformanceData();
    
    /**
     * Get all performance factors.
     *
     * @return List of performance factor DTOs
     */
    List<PerformanceFactorDTO> getPerformanceFactors();
    
    /**
     * Generate random data for charts.
     *
     * @param count the number of data points to generate
     * @param min the minimum value
     * @param max the maximum value
     * @return Array of numbers
     */
    Number[] generateRandomData(int count, double min, double max);
}