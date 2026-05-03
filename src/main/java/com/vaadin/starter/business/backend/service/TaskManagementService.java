/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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