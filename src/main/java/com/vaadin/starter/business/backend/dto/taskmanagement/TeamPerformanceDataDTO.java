package com.vaadin.starter.business.backend.dto.taskmanagement;

/**
 * Data Transfer Object for Team Performance Data.
 */
public class TeamPerformanceDataDTO {

    private String team;
    private int memberCount;
    private int tasksCompleted;
    private double tasksPerMember;
    private String avgResolutionTime;
    private String slaCompliance;
    private String customerSatisfaction;
    private String efficiencyRank;

    /**
     * Default constructor.
     */
    public TeamPerformanceDataDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param team                 the team name
     * @param memberCount          the number of team members
     * @param tasksCompleted       the number of tasks completed
     * @param tasksPerMember       the tasks per member ratio
     * @param avgResolutionTime    the average resolution time
     * @param slaCompliance        the SLA compliance percentage
     * @param customerSatisfaction the customer satisfaction rating
     * @param efficiencyRank       the efficiency rank
     */
    public TeamPerformanceDataDTO(String team, int memberCount, int tasksCompleted, double tasksPerMember,
                                String avgResolutionTime, String slaCompliance, String customerSatisfaction,
                                String efficiencyRank) {
        this.team = team;
        this.memberCount = memberCount;
        this.tasksCompleted = tasksCompleted;
        this.tasksPerMember = tasksPerMember;
        this.avgResolutionTime = avgResolutionTime;
        this.slaCompliance = slaCompliance;
        this.customerSatisfaction = customerSatisfaction;
        this.efficiencyRank = efficiencyRank;
    }

    // Getters and setters
    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public int getTasksCompleted() {
        return tasksCompleted;
    }

    public void setTasksCompleted(int tasksCompleted) {
        this.tasksCompleted = tasksCompleted;
    }

    public double getTasksPerMember() {
        return tasksPerMember;
    }

    public void setTasksPerMember(double tasksPerMember) {
        this.tasksPerMember = tasksPerMember;
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

    public String getEfficiencyRank() {
        return efficiencyRank;
    }

    public void setEfficiencyRank(String efficiencyRank) {
        this.efficiencyRank = efficiencyRank;
    }
}