package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.taskmanagement.*;
import com.vaadin.starter.business.backend.service.TaskManagementService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the TaskManagementService interface.
 * Provides mocked data for task management operations.
 */
@Service
public class TaskManagementServiceImpl implements TaskManagementService {

    private final Random random = new Random(42); // Fixed seed for reproducible data

    @Override
    public List<TaskDTO> getTasks(int count) {
        List<TaskDTO> tasks = new ArrayList<>();
        String[] subjects = {
            "Customer account verification", "Payment processing issue", "Document review", 
            "Loan application review", "Credit limit increase request", "Fraud alert investigation",
            "Customer complaint", "Account closure request", "Address change verification",
            "Transaction dispute", "Card replacement request", "Statement discrepancy"
        };

        String[] statuses = {"New", "In Progress", "Waiting", "Completed", "Cancelled"};
        String[] priorities = {"High", "Medium", "Low"};
        String[] types = {"Customer Service", "Account Management", "Loan Processing", "Fraud Investigation", "Document Review"};
        String[] assignees = {"John Smith", "Maria Garcia", "Ahmed Khan", "Sarah Johnson", "Unassigned"};
        String[] customers = {"ABC Corp", "Jane Doe", "Global Enterprises", "Local Business LLC", "First Time Customer"};

        for (int i = 1; i <= count; i++) {
            TaskDTO task = new TaskDTO();
            task.setId("TASK-" + (1000 + i));
            task.setSubject(subjects[random.nextInt(subjects.length)]);
            task.setStatus(statuses[random.nextInt(statuses.length)]);
            task.setPriority(priorities[random.nextInt(priorities.length)]);
            task.setType(types[random.nextInt(types.length)]);
            task.setAssignee(assignees[random.nextInt(assignees.length)]);
            task.setDueDate(LocalDate.now().plusDays(random.nextInt(14)));
            task.setCustomer(customers[random.nextInt(customers.length)]);
            tasks.add(task);
        }

        return tasks;
    }

    @Override
    public List<SLATaskDTO> getSLATasks(int count) {
        List<SLATaskDTO> tasks = new ArrayList<>();
        String[] subjects = {
            "Customer account verification", "Payment processing issue", "Document review", 
            "Loan application review", "Credit limit increase request", "Fraud alert investigation",
            "Customer complaint", "Account closure request", "Address change verification",
            "Transaction dispute", "Card replacement request", "Statement discrepancy"
        };

        String[] slaStatuses = {"Met", "At Risk", "Breached"};
        String[] priorities = {"High", "Medium", "Low"};
        String[] types = {"Customer Service", "Account Management", "Loan Processing", "Fraud Investigation", "Document Review"};
        String[] assignees = {"John Smith", "Maria Garcia", "Ahmed Khan", "Sarah Johnson", "Unassigned"};
        String[] slaTargets = {"4 hours", "8 hours", "24 hours", "48 hours"};
        String[] timeRemaining = {"2 hours", "30 minutes", "Overdue (15 min)", "Overdue (2 hrs)", "1 hour", "4 hours"};

        for (int i = 1; i <= count; i++) {
            SLATaskDTO task = new SLATaskDTO();
            task.setId("TASK-" + (1000 + i));
            task.setSubject(subjects[random.nextInt(subjects.length)]);
            task.setSlaStatus(slaStatuses[random.nextInt(slaStatuses.length)]);
            task.setPriority(priorities[random.nextInt(priorities.length)]);
            task.setType(types[random.nextInt(types.length)]);
            task.setAssignee(assignees[random.nextInt(assignees.length)]);
            task.setCreatedDate(LocalDate.now().minusDays(random.nextInt(7)));
            task.setSlaTarget(slaTargets[random.nextInt(slaTargets.length)]);
            task.setTimeRemaining(timeRemaining[random.nextInt(timeRemaining.length)]);
            tasks.add(task);
        }

        return tasks;
    }

    @Override
    public List<SLAPolicyDTO> getSLAPolicies() {
        List<SLAPolicyDTO> policies = new ArrayList<>();
        policies.add(new SLAPolicyDTO("Customer Service", "High", "30 min", "4 hrs", "2 hrs"));
        policies.add(new SLAPolicyDTO("Customer Service", "Medium", "1 hr", "8 hrs", "4 hrs"));
        policies.add(new SLAPolicyDTO("Customer Service", "Low", "4 hrs", "24 hrs", "12 hrs"));
        policies.add(new SLAPolicyDTO("Account Management", "High", "1 hr", "8 hrs", "4 hrs"));
        policies.add(new SLAPolicyDTO("Account Management", "Medium", "2 hrs", "16 hrs", "8 hrs"));
        policies.add(new SLAPolicyDTO("Account Management", "Low", "8 hrs", "48 hrs", "24 hrs"));
        policies.add(new SLAPolicyDTO("Loan Processing", "High", "2 hrs", "24 hrs", "12 hrs"));
        policies.add(new SLAPolicyDTO("Loan Processing", "Medium", "4 hrs", "48 hrs", "24 hrs"));
        policies.add(new SLAPolicyDTO("Loan Processing", "Low", "8 hrs", "72 hrs", "36 hrs"));
        return policies;
    }

    @Override
    public List<NotificationSettingDTO> getNotificationSettings() {
        List<NotificationSettingDTO> notifications = new ArrayList<>();
        notifications.add(new NotificationSettingDTO("SLA Approaching (75%)", "Assignee, Team Lead", "Email, System", "Yes"));
        notifications.add(new NotificationSettingDTO("SLA Approaching (90%)", "Assignee, Team Lead, Manager", "Email, System, SMS", "Yes"));
        notifications.add(new NotificationSettingDTO("SLA Breached", "Assignee, Team Lead, Manager, Director", "Email, System, SMS", "Yes"));
        notifications.add(new NotificationSettingDTO("SLA Resolved", "Assignee, Team Lead", "Email, System", "Yes"));
        return notifications;
    }

    @Override
    public List<TeamMemberPerformanceDTO> getTeamMemberPerformance() {
        List<TeamMemberPerformanceDTO> members = new ArrayList<>();
        members.add(new TeamMemberPerformanceDTO("John Smith", "Customer Support", 156, "3.8 hrs", "92%", "4.8/5", "High", 88));
        members.add(new TeamMemberPerformanceDTO("Maria Garcia", "Customer Support", 142, "4.1 hrs", "89%", "4.7/5", "High", 85));
        members.add(new TeamMemberPerformanceDTO("Ahmed Khan", "Operations", 128, "4.5 hrs", "85%", "4.5/5", "Medium", 78));
        members.add(new TeamMemberPerformanceDTO("Sarah Johnson", "Operations", 138, "4.2 hrs", "87%", "4.6/5", "High", 82));
        members.add(new TeamMemberPerformanceDTO("Michael Brown", "Risk Management", 112, "5.1 hrs", "82%", "4.4/5", "Medium", 75));
        members.add(new TeamMemberPerformanceDTO("Lisa Wong", "Risk Management", 124, "4.8 hrs", "84%", "4.5/5", "Medium", 77));
        members.add(new TeamMemberPerformanceDTO("David Miller", "Document Processing", 148, "3.9 hrs", "90%", "4.7/5", "High", 86));
        members.add(new TeamMemberPerformanceDTO("Emma Wilson", "Document Processing", 132, "4.3 hrs", "86%", "4.6/5", "Medium", 80));
        return members;
    }

    @Override
    public List<TeamPerformanceDataDTO> getTeamPerformanceData() {
        List<TeamPerformanceDataDTO> teams = new ArrayList<>();
        teams.add(new TeamPerformanceDataDTO("Customer Support", 12, 1845, 153.8, "3.9 hrs", "91%", "4.7/5", "1"));
        teams.add(new TeamPerformanceDataDTO("Operations", 15, 1920, 128.0, "4.3 hrs", "86%", "4.5/5", "3"));
        teams.add(new TeamPerformanceDataDTO("Risk Management", 8, 945, 118.1, "4.9 hrs", "83%", "4.4/5", "4"));
        teams.add(new TeamPerformanceDataDTO("Document Processing", 10, 1420, 142.0, "4.1 hrs", "88%", "4.6/5", "2"));
        return teams;
    }

    @Override
    public List<PerformanceFactorDTO> getPerformanceFactors() {
        List<PerformanceFactorDTO> factors = new ArrayList<>();
        factors.add(new PerformanceFactorDTO("Task Volume", "High", "Increasing", "Redistribute workload or increase staffing"));
        factors.add(new PerformanceFactorDTO("Task Complexity", "Medium", "Stable", "Provide additional training for complex tasks"));
        factors.add(new PerformanceFactorDTO("Team Size", "Medium", "Decreasing", "Review resource allocation"));
        factors.add(new PerformanceFactorDTO("System Performance", "Low", "Improving", "Continue system optimization"));
        factors.add(new PerformanceFactorDTO("Process Efficiency", "High", "Improving", "Document and share best practices"));
        return factors;
    }

    @Override
    public Number[] generateRandomData(int count, double min, double max) {
        Number[] data = new Number[count];
        for (int i = 0; i < count; i++) {
            data[i] = min + (random.nextDouble() * (max - min));
        }
        return data;
    }
}