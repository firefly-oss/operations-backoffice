package com.vaadin.starter.business.ui.views.reportinganalytics;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeColor;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeShape;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeSize;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@PageTitle(NavigationConstants.COMPLIANCE_REPORTING)
@Route(value = "reporting-analytics/compliance-reporting", layout = MainLayout.class)
public class ComplianceReporting extends ViewFrame {

    private Grid<ComplianceReport> reportsGrid;
    private ListDataProvider<ComplianceReport> reportsDataProvider;
    private Div contentArea;

    public ComplianceReporting() {
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createHeader(), createTabs());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createHeader() {
        H3 header = new H3("Compliance Reporting");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createTabs() {
        Tab regulatoryReportsTab = new Tab("Regulatory Reports");
        Tab internalComplianceTab = new Tab("Internal Compliance");
        Tab auditPreparationTab = new Tab("Audit Preparation");
        Tab complianceSettingsTab = new Tab("Settings");

        Tabs tabs = new Tabs(regulatoryReportsTab, internalComplianceTab, auditPreparationTab, complianceSettingsTab);
        tabs.getStyle().set("margin", "0 1rem");

        contentArea = new Div();
        contentArea.setSizeFull();
        contentArea.getStyle().set("padding", "1rem");

        // Show regulatory reports tab by default
        showRegulatoryReportsTab();

        tabs.addSelectedChangeListener(event -> {
            contentArea.removeAll();
            if (event.getSelectedTab().equals(regulatoryReportsTab)) {
                showRegulatoryReportsTab();
            } else if (event.getSelectedTab().equals(internalComplianceTab)) {
                showInternalComplianceTab();
            } else if (event.getSelectedTab().equals(auditPreparationTab)) {
                showAuditPreparationTab();
            } else if (event.getSelectedTab().equals(complianceSettingsTab)) {
                showComplianceSettingsTab();
            }
        });

        VerticalLayout tabsLayout = new VerticalLayout(tabs, contentArea);
        tabsLayout.setPadding(false);
        tabsLayout.setSpacing(false);
        tabsLayout.setSizeFull();
        return tabsLayout;
    }

    private void showRegulatoryReportsTab() {
        VerticalLayout regulatoryLayout = new VerticalLayout();
        regulatoryLayout.setPadding(false);
        regulatoryLayout.setSpacing(true);
        regulatoryLayout.setSizeFull();

        // Add filter form
        regulatoryLayout.add(createReportsFilterForm());

        // Add grid
        reportsGrid = createReportsGrid();
        regulatoryLayout.add(reportsGrid);
        regulatoryLayout.expand(reportsGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button generateReportButton = UIUtils.createPrimaryButton("Generate Report");
        generateReportButton.setIcon(VaadinIcon.DOWNLOAD.create());
        Button scheduleReportButton = UIUtils.createTertiaryButton("Schedule Report");
        scheduleReportButton.setIcon(VaadinIcon.CALENDAR_CLOCK.create());
        Button exportReportButton = UIUtils.createTertiaryButton("Export Report");
        exportReportButton.setIcon(VaadinIcon.EXTERNAL_LINK.create());
        
        actionButtons.add(generateReportButton, scheduleReportButton, exportReportButton);
        actionButtons.setSpacing(true);
        regulatoryLayout.add(actionButtons);

        contentArea.add(regulatoryLayout);
    }

    private void showInternalComplianceTab() {
        VerticalLayout internalLayout = new VerticalLayout();
        internalLayout.setPadding(false);
        internalLayout.setSpacing(true);
        internalLayout.setSizeFull();

        // Create internal compliance dashboard
        H4 dashboardTitle = new H4("Internal Compliance Dashboard");
        
        // Create compliance metrics cards
        HorizontalLayout metricsLayout = new HorizontalLayout();
        metricsLayout.setWidthFull();
        metricsLayout.setSpacing(true);
        
        metricsLayout.add(createMetricCard("Policy Compliance", "94%", "2% ↑", BadgeColor.SUCCESS));
        metricsLayout.add(createMetricCard("Training Completion", "87%", "5% ↑", BadgeColor.SUCCESS));
        metricsLayout.add(createMetricCard("Risk Assessment", "76%", "3% ↓", BadgeColor.ERROR));
        metricsLayout.add(createMetricCard("Control Effectiveness", "82%", "1% ↑", BadgeColor.SUCCESS));
        
        // Create compliance issues section
        H4 issuesTitle = new H4("Open Compliance Issues");
        
        Grid<ComplianceIssue> issuesGrid = createIssuesGrid();
        
        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button addIssueButton = UIUtils.createPrimaryButton("Add Issue");
        Button exportIssuesButton = UIUtils.createTertiaryButton("Export Issues");
        actionButtons.add(addIssueButton, exportIssuesButton);
        actionButtons.setSpacing(true);
        
        internalLayout.add(dashboardTitle, metricsLayout, issuesTitle, issuesGrid, actionButtons);
        internalLayout.expand(issuesGrid);
        
        contentArea.add(internalLayout);
    }

    private void showAuditPreparationTab() {
        VerticalLayout auditLayout = new VerticalLayout();
        auditLayout.setPadding(false);
        auditLayout.setSpacing(true);
        auditLayout.setSizeFull();

        // Create audit preparation checklist
        H4 checklistTitle = new H4("Audit Preparation Checklist");
        
        Grid<AuditTask> auditTasksGrid = createAuditTasksGrid();
        
        // Create document repository section
        H4 documentsTitle = new H4("Document Repository");
        
        Grid<ComplianceDocument> documentsGrid = createDocumentsGrid();
        
        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button uploadDocumentButton = UIUtils.createPrimaryButton("Upload Document");
        uploadDocumentButton.setIcon(VaadinIcon.UPLOAD.create());
        Button prepareAuditPackageButton = UIUtils.createTertiaryButton("Prepare Audit Package");
        prepareAuditPackageButton.setIcon(VaadinIcon.PACKAGE.create());
        actionButtons.add(uploadDocumentButton, prepareAuditPackageButton);
        actionButtons.setSpacing(true);
        
        auditLayout.add(checklistTitle, auditTasksGrid, documentsTitle, documentsGrid, actionButtons);
        auditLayout.expand(documentsGrid);
        
        contentArea.add(auditLayout);
    }

    private void showComplianceSettingsTab() {
        FormLayout settingsForm = new FormLayout();
        settingsForm.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        ComboBox<String> defaultReportFormat = new ComboBox<>("Default Report Format");
        defaultReportFormat.setItems("PDF", "Excel", "CSV", "HTML");
        defaultReportFormat.setValue("PDF");

        ComboBox<String> reportingFrequency = new ComboBox<>("Reporting Frequency");
        reportingFrequency.setItems("Daily", "Weekly", "Monthly", "Quarterly");
        reportingFrequency.setValue("Monthly");

        ComboBox<String> complianceFramework = new ComboBox<>("Compliance Framework");
        complianceFramework.setItems("GDPR", "PCI-DSS", "SOX", "Basel III", "Custom");
        complianceFramework.setValue("GDPR");

        ComboBox<String> notificationPreference = new ComboBox<>("Notification Preference");
        notificationPreference.setItems("Email", "System", "Both", "None");
        notificationPreference.setValue("Email");

        TextField emailRecipients = new TextField("Email Recipients");
        emailRecipients.setValue("compliance@example.com");
        emailRecipients.setWidthFull();

        settingsForm.add(defaultReportFormat, reportingFrequency, complianceFramework, 
                         notificationPreference, emailRecipients);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button saveSettingsButton = UIUtils.createPrimaryButton("Save Settings");
        Button resetSettingsButton = UIUtils.createTertiaryButton("Reset to Defaults");
        actionButtons.add(saveSettingsButton, resetSettingsButton);
        actionButtons.setSpacing(true);

        VerticalLayout settingsLayout = new VerticalLayout(settingsForm, actionButtons);
        settingsLayout.setPadding(true);
        settingsLayout.setSpacing(true);

        contentArea.add(settingsLayout);
    }

    private Component createMetricCard(String title, String value, String change, BadgeColor trendColor) {
        Div card = new Div();
        card.getStyle().set("background-color", "var(--lumo-base-color)");
        card.getStyle().set("border-radius", "var(--lumo-border-radius)");
        card.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        card.getStyle().set("padding", "1.5rem");
        card.getStyle().set("text-align", "center");
        card.getStyle().set("width", "200px");
        
        H4 titleElement = new H4(title);
        titleElement.getStyle().set("margin-top", "0");
        titleElement.getStyle().set("margin-bottom", "0.5rem");
        titleElement.getStyle().set("font-size", "1rem");
        
        Span valueElement = new Span(value);
        valueElement.getStyle().set("font-size", "2rem");
        valueElement.getStyle().set("font-weight", "bold");
        valueElement.getStyle().set("display", "block");
        valueElement.getStyle().set("margin-bottom", "0.5rem");
        
        Badge changeBadge = new Badge(change, trendColor, BadgeSize.S, BadgeShape.PILL);
        
        card.add(titleElement, valueElement, changeBadge);
        return card;
    }

    private Component createReportsFilterForm() {
        // Initialize filter fields
        TextField reportNameFilter = new TextField();
        reportNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        reportNameFilter.setClearButtonVisible(true);
        reportNameFilter.setPlaceholder("Search by name...");
        
        ComboBox<String> regulationFilter = new ComboBox<>();
        regulationFilter.setItems("GDPR", "PCI-DSS", "SOX", "Basel III", "AML", "KYC", "FATCA");
        regulationFilter.setClearButtonVisible(true);
        regulationFilter.setPlaceholder("All Regulations");
        
        ComboBox<String> frequencyFilter = new ComboBox<>();
        frequencyFilter.setItems("Daily", "Weekly", "Monthly", "Quarterly", "Yearly");
        frequencyFilter.setClearButtonVisible(true);
        frequencyFilter.setPlaceholder("All Frequencies");
        
        DatePicker dueDateFilter = new DatePicker();
        dueDateFilter.setClearButtonVisible(true);
        dueDateFilter.setPlaceholder("Due Date");
        
        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.setItems("Pending", "In Progress", "Submitted", "Approved", "Rejected");
        statusFilter.setClearButtonVisible(true);
        statusFilter.setPlaceholder("All Statuses");
        
        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        Button clearButton = UIUtils.createTertiaryButton("Clear");
        
        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton);
        buttonLayout.setSpacing(true);
        
        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(reportNameFilter, "Report Name");
        formLayout.addFormItem(regulationFilter, "Regulation");
        formLayout.addFormItem(frequencyFilter, "Frequency");
        formLayout.addFormItem(dueDateFilter, "Due Date");
        formLayout.addFormItem(statusFilter, "Status");
        
        formLayout.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("900px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );
        
        // Create a container for the form and buttons
        Div formContainer = new Div(formLayout, buttonLayout);
        formContainer.getStyle().set("padding", "1em");
        formContainer.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        formContainer.getStyle().set("border-radius", "var(--lumo-border-radius)");
        formContainer.getStyle().set("background-color", "var(--lumo-base-color)");
        formContainer.getStyle().set("margin-bottom", "1em");
        
        return formContainer;
    }

    private Grid<ComplianceReport> createReportsGrid() {
        Grid<ComplianceReport> grid = new Grid<>();
        
        // Initialize data provider with mock data
        Collection<ComplianceReport> reports = generateMockReports();
        reportsDataProvider = new ListDataProvider<>(reports);
        grid.setDataProvider(reportsDataProvider);
        
        grid.setId("compliance-reports");
        grid.setSizeFull();
        
        // Add columns
        grid.addColumn(ComplianceReport::getReportId)
                .setHeader("Report ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(ComplianceReport::getReportName)
                .setHeader("Report Name")
                .setSortable(true)
                .setWidth("250px");
        grid.addColumn(ComplianceReport::getRegulation)
                .setHeader("Regulation")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(ComplianceReport::getFrequency)
                .setHeader("Frequency")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(report -> {
                    if (report.getDueDate() != null) {
                        return report.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    return "";
                })
                .setHeader("Due Date")
                .setSortable(true)
                .setComparator(ComplianceReport::getDueDate)
                .setWidth("120px");
        grid.addColumn(report -> createStatusBadge(report.getStatus()))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(ComplianceReport::getStatus)
                .setWidth("150px");
        grid.addColumn(ComplianceReport::getAssignedTo)
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("180px");
        
        return grid;
    }
    
    private Grid<ComplianceIssue> createIssuesGrid() {
        Grid<ComplianceIssue> grid = new Grid<>();
        
        // Initialize data provider with mock data
        Collection<ComplianceIssue> issues = generateMockIssues();
        grid.setItems(issues);
        
        grid.setId("compliance-issues");
        grid.setSizeFull();
        
        // Add columns
        grid.addColumn(ComplianceIssue::getIssueId)
                .setHeader("Issue ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(ComplianceIssue::getTitle)
                .setHeader("Title")
                .setSortable(true)
                .setWidth("250px");
        grid.addColumn(ComplianceIssue::getCategory)
                .setHeader("Category")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(issue -> createSeverityBadge(issue.getSeverity()))
                .setHeader("Severity")
                .setSortable(true)
                .setComparator(ComplianceIssue::getSeverity)
                .setWidth("120px");
        grid.addColumn(issue -> {
                    if (issue.getIdentifiedDate() != null) {
                        return issue.getIdentifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    return "";
                })
                .setHeader("Identified Date")
                .setSortable(true)
                .setComparator(ComplianceIssue::getIdentifiedDate)
                .setWidth("150px");
        grid.addColumn(issue -> {
                    if (issue.getDueDate() != null) {
                        return issue.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    return "";
                })
                .setHeader("Due Date")
                .setSortable(true)
                .setComparator(ComplianceIssue::getDueDate)
                .setWidth("120px");
        grid.addColumn(ComplianceIssue::getAssignedTo)
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("180px");
        
        return grid;
    }
    
    private Grid<AuditTask> createAuditTasksGrid() {
        Grid<AuditTask> grid = new Grid<>();
        
        // Initialize data provider with mock data
        Collection<AuditTask> tasks = generateMockAuditTasks();
        grid.setItems(tasks);
        
        grid.setId("audit-tasks");
        grid.setSizeFull();
        
        // Add columns
        grid.addColumn(AuditTask::getTaskId)
                .setHeader("Task ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(AuditTask::getTaskName)
                .setHeader("Task Name")
                .setSortable(true)
                .setWidth("250px");
        grid.addColumn(AuditTask::getCategory)
                .setHeader("Category")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(task -> createCompletionBadge(task.getCompletionStatus()))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(AuditTask::getCompletionStatus)
                .setWidth("120px");
        grid.addColumn(task -> {
                    if (task.getDueDate() != null) {
                        return task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    return "";
                })
                .setHeader("Due Date")
                .setSortable(true)
                .setComparator(AuditTask::getDueDate)
                .setWidth("120px");
        grid.addColumn(AuditTask::getAssignedTo)
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("180px");
        
        return grid;
    }
    
    private Grid<ComplianceDocument> createDocumentsGrid() {
        Grid<ComplianceDocument> grid = new Grid<>();
        
        // Initialize data provider with mock data
        Collection<ComplianceDocument> documents = generateMockDocuments();
        grid.setItems(documents);
        
        grid.setId("compliance-documents");
        grid.setSizeFull();
        
        // Add columns
        grid.addColumn(ComplianceDocument::getDocumentId)
                .setHeader("Document ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(ComplianceDocument::getDocumentName)
                .setHeader("Document Name")
                .setSortable(true)
                .setWidth("250px");
        grid.addColumn(ComplianceDocument::getCategory)
                .setHeader("Category")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(ComplianceDocument::getVersion)
                .setHeader("Version")
                .setSortable(true)
                .setWidth("100px");
        grid.addColumn(document -> {
                    if (document.getUploadDate() != null) {
                        return document.getUploadDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    return "";
                })
                .setHeader("Upload Date")
                .setSortable(true)
                .setComparator(ComplianceDocument::getUploadDate)
                .setWidth("120px");
        grid.addColumn(ComplianceDocument::getUploadedBy)
                .setHeader("Uploaded By")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(document -> document.getFileSize() + " KB")
                .setHeader("Size")
                .setSortable(true)
                .setComparator(ComplianceDocument::getFileSize)
                .setWidth("100px");
        
        return grid;
    }

    private Component createStatusBadge(String status) {
        BadgeColor color;
        switch (status) {
            case "Approved":
                color = BadgeColor.SUCCESS;
                break;
            case "Rejected":
                color = BadgeColor.ERROR;
                break;
            case "Submitted":
                color = BadgeColor.NORMAL;
                break;
            case "In Progress":
                color = BadgeColor.CONTRAST;
                break;
            case "Pending":
                color = BadgeColor.ERROR_PRIMARY;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }
    
    private Component createSeverityBadge(String severity) {
        BadgeColor color;
        switch (severity) {
            case "Critical":
                color = BadgeColor.ERROR;
                break;
            case "High":
                color = BadgeColor.ERROR_PRIMARY;
                break;
            case "Medium":
                color = BadgeColor.CONTRAST;
                break;
            case "Low":
                color = BadgeColor.SUCCESS;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(severity, color, BadgeSize.S, BadgeShape.PILL);
    }
    
    private Component createCompletionBadge(String status) {
        BadgeColor color;
        switch (status) {
            case "Completed":
                color = BadgeColor.SUCCESS;
                break;
            case "In Progress":
                color = BadgeColor.CONTRAST;
                break;
            case "Not Started":
                color = BadgeColor.ERROR_PRIMARY;
                break;
            case "Overdue":
                color = BadgeColor.ERROR;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Collection<ComplianceReport> generateMockReports() {
        List<ComplianceReport> reports = new ArrayList<>();
        Random random = new Random(42); // Fixed seed for reproducible data

        String[] reportNames = {
            "GDPR Data Processing Report", 
            "PCI-DSS Compliance Assessment",
            "SOX Financial Controls Report",
            "Basel III Capital Adequacy Report",
            "AML Transaction Monitoring Report",
            "KYC Verification Compliance Report",
            "FATCA Reporting",
            "Suspicious Activity Report (SAR)",
            "Currency Transaction Report (CTR)",
            "Regulatory Examination Preparation",
            "Privacy Impact Assessment",
            "Information Security Compliance Report",
            "Third-Party Risk Assessment",
            "Business Continuity Compliance",
            "Fraud Prevention Compliance"
        };
        
        String[] regulations = {
            "GDPR", 
            "PCI-DSS", 
            "SOX", 
            "Basel III", 
            "AML", 
            "KYC", 
            "FATCA"
        };
        
        String[] frequencies = {
            "Monthly", 
            "Quarterly", 
            "Yearly", 
            "Semi-annually", 
            "On-demand"
        };
        
        String[] statuses = {
            "Pending", 
            "In Progress", 
            "Submitted", 
            "Approved", 
            "Rejected"
        };
        
        String[] assignedTo = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Compliance Team",
            "Risk Management",
            "Legal Department"
        };
        
        for (int i = 0; i < reportNames.length; i++) {
            ComplianceReport report = new ComplianceReport();
            report.setReportId("CR" + String.format("%04d", i + 1));
            report.setReportName(reportNames[i]);
            report.setRegulation(regulations[random.nextInt(regulations.length)]);
            report.setFrequency(frequencies[random.nextInt(frequencies.length)]);
            
            // Generate due date
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime dueDate = now.plusDays(random.nextInt(90) + 1);
            report.setDueDate(dueDate);
            
            report.setStatus(statuses[random.nextInt(statuses.length)]);
            report.setAssignedTo(assignedTo[random.nextInt(assignedTo.length)]);
            
            reports.add(report);
        }
        
        return reports;
    }
    
    private Collection<ComplianceIssue> generateMockIssues() {
        List<ComplianceIssue> issues = new ArrayList<>();
        Random random = new Random(42); // Fixed seed for reproducible data

        String[] issueTitles = {
            "Incomplete Customer Due Diligence", 
            "Missing Transaction Monitoring Documentation",
            "Delayed Suspicious Activity Reporting",
            "Inadequate Staff Training Records",
            "Incomplete Risk Assessment",
            "Data Retention Policy Violation",
            "Unauthorized Data Access",
            "Incomplete Audit Trail",
            "Missing Consent Records",
            "Inadequate Third-Party Oversight"
        };
        
        String[] categories = {
            "KYC/AML", 
            "Data Privacy", 
            "Operational Risk", 
            "Information Security", 
            "Regulatory Reporting"
        };
        
        String[] severities = {
            "Critical", 
            "High", 
            "Medium", 
            "Low"
        };
        
        String[] assignedTo = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Compliance Team",
            "Risk Management",
            "Legal Department"
        };
        
        for (int i = 0; i < issueTitles.length; i++) {
            ComplianceIssue issue = new ComplianceIssue();
            issue.setIssueId("CI" + String.format("%04d", i + 1));
            issue.setTitle(issueTitles[i]);
            issue.setCategory(categories[random.nextInt(categories.length)]);
            issue.setSeverity(severities[random.nextInt(severities.length)]);
            
            // Generate identified date (past)
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime identifiedDate = now.minusDays(random.nextInt(30) + 1);
            issue.setIdentifiedDate(identifiedDate);
            
            // Generate due date (future)
            LocalDateTime dueDate = now.plusDays(random.nextInt(60) + 1);
            issue.setDueDate(dueDate);
            
            issue.setAssignedTo(assignedTo[random.nextInt(assignedTo.length)]);
            
            issues.add(issue);
        }
        
        return issues;
    }
    
    private Collection<AuditTask> generateMockAuditTasks() {
        List<AuditTask> tasks = new ArrayList<>();
        Random random = new Random(42); // Fixed seed for reproducible data

        String[] taskNames = {
            "Prepare Transaction Samples", 
            "Collect Policy Documentation",
            "Update Risk Register",
            "Compile Training Records",
            "Prepare System Access Logs",
            "Document Control Procedures",
            "Collect Customer Verification Samples",
            "Prepare Regulatory Correspondence",
            "Update Compliance Calendar",
            "Compile Incident Reports"
        };
        
        String[] categories = {
            "Documentation", 
            "Process Review", 
            "Data Collection", 
            "Control Testing", 
            "Reporting"
        };
        
        String[] statuses = {
            "Completed", 
            "In Progress", 
            "Not Started", 
            "Overdue"
        };
        
        String[] assignedTo = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Compliance Team",
            "Risk Management",
            "Operations Team"
        };
        
        for (int i = 0; i < taskNames.length; i++) {
            AuditTask task = new AuditTask();
            task.setTaskId("AT" + String.format("%04d", i + 1));
            task.setTaskName(taskNames[i]);
            task.setCategory(categories[random.nextInt(categories.length)]);
            task.setCompletionStatus(statuses[random.nextInt(statuses.length)]);
            
            // Generate due date
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime dueDate = now.plusDays(random.nextInt(30) + 1);
            task.setDueDate(dueDate);
            
            task.setAssignedTo(assignedTo[random.nextInt(assignedTo.length)]);
            
            tasks.add(task);
        }
        
        return tasks;
    }
    
    private Collection<ComplianceDocument> generateMockDocuments() {
        List<ComplianceDocument> documents = new ArrayList<>();
        Random random = new Random(42); // Fixed seed for reproducible data

        String[] documentNames = {
            "AML Policy", 
            "KYC Procedures",
            "Risk Assessment Framework",
            "Compliance Training Materials",
            "Regulatory Examination Responses",
            "Board Compliance Reports",
            "Audit Committee Minutes",
            "Regulatory Correspondence",
            "Incident Response Plan",
            "Data Privacy Policy",
            "Information Security Standards",
            "Business Continuity Plan",
            "Vendor Management Policy",
            "Code of Conduct",
            "Whistleblower Policy"
        };
        
        String[] categories = {
            "Policies", 
            "Procedures", 
            "Reports", 
            "Training", 
            "Correspondence",
            "Governance"
        };
        
        String[] uploadedBy = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Compliance Team",
            "Risk Management",
            "Legal Department"
        };
        
        for (int i = 0; i < documentNames.length; i++) {
            ComplianceDocument document = new ComplianceDocument();
            document.setDocumentId("DOC" + String.format("%04d", i + 1));
            document.setDocumentName(documentNames[i]);
            document.setCategory(categories[random.nextInt(categories.length)]);
            document.setVersion(String.format("v%d.%d", 1 + random.nextInt(3), random.nextInt(10)));
            
            // Generate upload date
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime uploadDate = now.minusDays(random.nextInt(180) + 1);
            document.setUploadDate(uploadDate);
            
            document.setUploadedBy(uploadedBy[random.nextInt(uploadedBy.length)]);
            document.setFileSize(100 + random.nextInt(900)); // 100-999 KB
            
            documents.add(document);
        }
        
        return documents;
    }
    
    // Inner class to represent a compliance report
    public static class ComplianceReport {
        private String reportId;
        private String reportName;
        private String regulation;
        private String frequency;
        private LocalDateTime dueDate;
        private String status;
        private String assignedTo;
        
        public String getReportId() {
            return reportId;
        }
        
        public void setReportId(String reportId) {
            this.reportId = reportId;
        }
        
        public String getReportName() {
            return reportName;
        }
        
        public void setReportName(String reportName) {
            this.reportName = reportName;
        }
        
        public String getRegulation() {
            return regulation;
        }
        
        public void setRegulation(String regulation) {
            this.regulation = regulation;
        }
        
        public String getFrequency() {
            return frequency;
        }
        
        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }
        
        public LocalDateTime getDueDate() {
            return dueDate;
        }
        
        public void setDueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
        }
        
        public String getStatus() {
            return status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
        
        public String getAssignedTo() {
            return assignedTo;
        }
        
        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }
    }
    
    // Inner class to represent a compliance issue
    public static class ComplianceIssue {
        private String issueId;
        private String title;
        private String category;
        private String severity;
        private LocalDateTime identifiedDate;
        private LocalDateTime dueDate;
        private String assignedTo;
        
        public String getIssueId() {
            return issueId;
        }
        
        public void setIssueId(String issueId) {
            this.issueId = issueId;
        }
        
        public String getTitle() {
            return title;
        }
        
        public void setTitle(String title) {
            this.title = title;
        }
        
        public String getCategory() {
            return category;
        }
        
        public void setCategory(String category) {
            this.category = category;
        }
        
        public String getSeverity() {
            return severity;
        }
        
        public void setSeverity(String severity) {
            this.severity = severity;
        }
        
        public LocalDateTime getIdentifiedDate() {
            return identifiedDate;
        }
        
        public void setIdentifiedDate(LocalDateTime identifiedDate) {
            this.identifiedDate = identifiedDate;
        }
        
        public LocalDateTime getDueDate() {
            return dueDate;
        }
        
        public void setDueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
        }
        
        public String getAssignedTo() {
            return assignedTo;
        }
        
        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }
    }
    
    // Inner class to represent an audit task
    public static class AuditTask {
        private String taskId;
        private String taskName;
        private String category;
        private String completionStatus;
        private LocalDateTime dueDate;
        private String assignedTo;
        
        public String getTaskId() {
            return taskId;
        }
        
        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }
        
        public String getTaskName() {
            return taskName;
        }
        
        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }
        
        public String getCategory() {
            return category;
        }
        
        public void setCategory(String category) {
            this.category = category;
        }
        
        public String getCompletionStatus() {
            return completionStatus;
        }
        
        public void setCompletionStatus(String completionStatus) {
            this.completionStatus = completionStatus;
        }
        
        public LocalDateTime getDueDate() {
            return dueDate;
        }
        
        public void setDueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
        }
        
        public String getAssignedTo() {
            return assignedTo;
        }
        
        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }
    }
    
    // Inner class to represent a compliance document
    public static class ComplianceDocument {
        private String documentId;
        private String documentName;
        private String category;
        private String version;
        private LocalDateTime uploadDate;
        private String uploadedBy;
        private int fileSize; // in KB
        
        public String getDocumentId() {
            return documentId;
        }
        
        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }
        
        public String getDocumentName() {
            return documentName;
        }
        
        public void setDocumentName(String documentName) {
            this.documentName = documentName;
        }
        
        public String getCategory() {
            return category;
        }
        
        public void setCategory(String category) {
            this.category = category;
        }
        
        public String getVersion() {
            return version;
        }
        
        public void setVersion(String version) {
            this.version = version;
        }
        
        public LocalDateTime getUploadDate() {
            return uploadDate;
        }
        
        public void setUploadDate(LocalDateTime uploadDate) {
            this.uploadDate = uploadDate;
        }
        
        public String getUploadedBy() {
            return uploadedBy;
        }
        
        public void setUploadedBy(String uploadedBy) {
            this.uploadedBy = uploadedBy;
        }
        
        public int getFileSize() {
            return fileSize;
        }
        
        public void setFileSize(int fileSize) {
            this.fileSize = fileSize;
        }
    }
}