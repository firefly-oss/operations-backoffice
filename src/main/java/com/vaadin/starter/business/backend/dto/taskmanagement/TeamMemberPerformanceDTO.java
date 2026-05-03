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


package com.vaadin.starter.business.backend.dto.taskmanagement;

/**
 * Data Transfer Object for Team Member Performance.
 */
public class TeamMemberPerformanceDTO {

    private String name;
    private String team;
    private int tasksCompleted;
    private String avgResolutionTime;
    private String slaCompliance;
    private String customerSatisfaction;
    private String efficiencyScore;
    private int performanceScore;

    /**
     * Default constructor.
     */
    public TeamMemberPerformanceDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param name                 the team member name
     * @param team                 the team name
     * @param tasksCompleted       the number of tasks completed
     * @param avgResolutionTime    the average resolution time
     * @param slaCompliance        the SLA compliance percentage
     * @param customerSatisfaction the customer satisfaction rating
     * @param efficiencyScore      the efficiency score
     * @param performanceScore     the overall performance score
     */
    public TeamMemberPerformanceDTO(String name, String team, int tasksCompleted, String avgResolutionTime,
                                  String slaCompliance, String customerSatisfaction, String efficiencyScore,
                                  int performanceScore) {
        this.name = name;
        this.team = team;
        this.tasksCompleted = tasksCompleted;
        this.avgResolutionTime = avgResolutionTime;
        this.slaCompliance = slaCompliance;
        this.customerSatisfaction = customerSatisfaction;
        this.efficiencyScore = efficiencyScore;
        this.performanceScore = performanceScore;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getTasksCompleted() {
        return tasksCompleted;
    }

    public void setTasksCompleted(int tasksCompleted) {
        this.tasksCompleted = tasksCompleted;
    }

    public String getAvgResolutionTime() {
        return avgResolutionTime;
    }

    public void setAvgResolutionTime(String avgResolutionTime) {
        this.avgResolutionTime = avgResolutionTime;
    }

    public String getSlaCompliance() {
        return slaCompliance;
    }

    public void setSlaCompliance(String slaCompliance) {
        this.slaCompliance = slaCompliance;
    }

    public String getCustomerSatisfaction() {
        return customerSatisfaction;
    }

    public void setCustomerSatisfaction(String customerSatisfaction) {
        this.customerSatisfaction = customerSatisfaction;
    }

    public String getEfficiencyScore() {
        return efficiencyScore;
    }

    public void setEfficiencyScore(String efficiencyScore) {
        this.efficiencyScore = efficiencyScore;
    }

    public int getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(int performanceScore) {
        this.performanceScore = performanceScore;
    }
}