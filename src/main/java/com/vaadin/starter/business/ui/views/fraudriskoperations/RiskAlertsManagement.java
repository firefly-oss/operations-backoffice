package com.vaadin.starter.business.ui.views.fraudriskoperations;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.util.UIUtils;
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

@PageTitle(NavigationConstants.RISK_ALERTS_MANAGEMENT)
@Route(value = "fraud-risk/risk-alerts", layout = MainLayout.class)
public class RiskAlertsManagement extends ViewFrame {

    private Grid<RiskAlert> grid;
    private ListDataProvider<RiskAlert> dataProvider;

    // Filter form fields
    private TextField alertIdFilter;
    private ComboBox<String> alertTypeFilter;
    private ComboBox<String> severityFilter;
    private ComboBox<String> statusFilter;
    private TextField entityIdFilter;
    private TextField entityNameFilter;
    private DatePicker generatedDateFromFilter;
    private DatePicker generatedDateToFilter;
    private TextField assignedToFilter;

    public RiskAlertsManagement() {
        setViewContent(createContent());
    }

    private Component createContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setPadding(false);
        content.setSpacing(false);

        content.add(createHeader());
        content.add(createFilterForm());
        content.add(createGrid());

        return content;
    }

    private Component createHeader() {
        H3 header = new H3("Risk Alerts Management");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createFilterForm() {
        // Initialize filter fields
        alertIdFilter = new TextField();
        alertIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        alertIdFilter.setClearButtonVisible(true);

        alertTypeFilter = new ComboBox<>();
        alertTypeFilter.setItems(getAlertTypes());
        alertTypeFilter.setClearButtonVisible(true);

        severityFilter = new ComboBox<>();
        severityFilter.setItems(getSeverities());
        severityFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems(getStatuses());
        statusFilter.setClearButtonVisible(true);

        entityIdFilter = new TextField();
        entityIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        entityIdFilter.setClearButtonVisible(true);

        entityNameFilter = new TextField();
        entityNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        entityNameFilter.setClearButtonVisible(true);

        generatedDateFromFilter = new DatePicker();
        generatedDateFromFilter.setClearButtonVisible(true);

        generatedDateToFilter = new DatePicker();
        generatedDateToFilter.setClearButtonVisible(true);

        assignedToFilter = new TextField();
        assignedToFilter.setValueChangeMode(ValueChangeMode.EAGER);
        assignedToFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> applyFilters());

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> clearFilters());

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton);
        buttonLayout.setSpacing(true);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(alertIdFilter, "Alert ID");
        formLayout.addFormItem(alertTypeFilter, "Alert Type");
        formLayout.addFormItem(severityFilter, "Severity");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(entityIdFilter, "Entity ID");
        formLayout.addFormItem(entityNameFilter, "Entity Name");
        formLayout.addFormItem(generatedDateFromFilter, "Generated From");
        formLayout.addFormItem(generatedDateToFilter, "Generated To");
        formLayout.addFormItem(assignedToFilter, "Assigned To");

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

    private Grid<RiskAlert> createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));

        // Initialize data provider with mock data
        Collection<RiskAlert> alerts = generateMockData();
        dataProvider = new ListDataProvider<>(alerts);
        grid.setDataProvider(dataProvider);

        grid.setId("riskAlerts");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(RiskAlert::getAlertId)
                .setHeader("Alert ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(RiskAlert::getAlertType)
                .setHeader("Alert Type")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(alert -> createSeverityBadge(alert.getSeverity()))
                .setHeader("Severity")
                .setSortable(true)
                .setComparator(RiskAlert::getSeverity)
                .setWidth("120px");
        grid.addColumn(alert -> createStatusBadge(alert.getStatus()))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(RiskAlert::getStatus)
                .setWidth("120px");
        grid.addColumn(RiskAlert::getEntityId)
                .setHeader("Entity ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(RiskAlert::getEntityName)
                .setHeader("Entity Name")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(RiskAlert::getEntityType)
                .setHeader("Entity Type")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(alert -> {
                    if (alert.getGeneratedDate() != null) {
                        return alert.getGeneratedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                    return "";
                })
                .setHeader("Generated Date")
                .setSortable(true)
                .setComparator(RiskAlert::getGeneratedDate)
                .setWidth("150px");
        grid.addColumn(RiskAlert::getAssignedTo)
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(RiskAlert::getDescription)
                .setHeader("Description")
                .setSortable(true)
                .setWidth("250px");

        return grid;
    }

    private void viewDetails(RiskAlert alert) {
        // Navigate to the details view
        UI.getCurrent().navigate(RiskAlertDetails.class, alert.getAlertId());
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply alert ID filter
        if (alertIdFilter.getValue() != null && !alertIdFilter.getValue().isEmpty()) {
            String alertIdValue = alertIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(alert -> 
                alert.getAlertId() != null && 
                alert.getAlertId().toLowerCase().contains(alertIdValue));
        }

        // Apply alert type filter
        if (alertTypeFilter.getValue() != null) {
            String alertTypeValue = alertTypeFilter.getValue();
            dataProvider.addFilter(alert -> 
                alert.getAlertType() != null && 
                alert.getAlertType().equals(alertTypeValue));
        }

        // Apply severity filter
        if (severityFilter.getValue() != null) {
            String severityValue = severityFilter.getValue();
            dataProvider.addFilter(alert -> 
                alert.getSeverity() != null && 
                alert.getSeverity().equals(severityValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(alert -> 
                alert.getStatus() != null && 
                alert.getStatus().equals(statusValue));
        }

        // Apply entity ID filter
        if (entityIdFilter.getValue() != null && !entityIdFilter.getValue().isEmpty()) {
            String entityIdValue = entityIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(alert -> 
                alert.getEntityId() != null && 
                alert.getEntityId().toLowerCase().contains(entityIdValue));
        }

        // Apply entity name filter
        if (entityNameFilter.getValue() != null && !entityNameFilter.getValue().isEmpty()) {
            String entityNameValue = entityNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(alert -> 
                alert.getEntityName() != null && 
                alert.getEntityName().toLowerCase().contains(entityNameValue));
        }

        // Apply generated date from filter
        if (generatedDateFromFilter.getValue() != null) {
            java.time.LocalDate fromDate = generatedDateFromFilter.getValue();
            dataProvider.addFilter(alert -> 
                alert.getGeneratedDate() != null && 
                !alert.getGeneratedDate().toLocalDate().isBefore(fromDate));
        }

        // Apply generated date to filter
        if (generatedDateToFilter.getValue() != null) {
            java.time.LocalDate toDate = generatedDateToFilter.getValue();
            dataProvider.addFilter(alert -> 
                alert.getGeneratedDate() != null && 
                !alert.getGeneratedDate().toLocalDate().isAfter(toDate));
        }

        // Apply assigned to filter
        if (assignedToFilter.getValue() != null && !assignedToFilter.getValue().isEmpty()) {
            String assignedToValue = assignedToFilter.getValue().toLowerCase();
            dataProvider.addFilter(alert -> 
                alert.getAssignedTo() != null && 
                alert.getAssignedTo().toLowerCase().contains(assignedToValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        alertIdFilter.clear();
        alertTypeFilter.clear();
        severityFilter.clear();
        statusFilter.clear();
        entityIdFilter.clear();
        entityNameFilter.clear();
        generatedDateFromFilter.clear();
        generatedDateToFilter.clear();
        assignedToFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private String[] getAlertTypes() {
        return new String[] {
            "Credit Risk", 
            "Market Risk", 
            "Operational Risk", 
            "Liquidity Risk", 
            "Compliance Risk",
            "Reputational Risk",
            "Strategic Risk",
            "Fraud Risk"
        };
    }

    private String[] getSeverities() {
        return new String[] {"Critical", "High", "Medium", "Low"};
    }

    private String[] getStatuses() {
        return new String[] {
            "New", 
            "In Progress", 
            "Under Review", 
            "Escalated", 
            "Mitigated",
            "Closed",
            "False Positive"
        };
    }

    private String[] getEntityTypes() {
        return new String[] {
            "Customer", 
            "Account", 
            "Transaction", 
            "Employee", 
            "Vendor",
            "System",
            "Process"
        };
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
                color = BadgeColor.CONTRAST_PRIMARY;
                break;
            case "Low":
                color = BadgeColor.SUCCESS;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(severity, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Component createStatusBadge(String status) {
        BadgeColor color;
        switch (status) {
            case "New":
                color = BadgeColor.NORMAL;
                break;
            case "In Progress":
            case "Under Review":
                color = BadgeColor.CONTRAST;
                break;
            case "Escalated":
                color = BadgeColor.ERROR;
                break;
            case "Mitigated":
                color = BadgeColor.SUCCESS;
                break;
            case "Closed":
                color = BadgeColor.NORMAL_PRIMARY;
                break;
            case "False Positive":
                color = BadgeColor.CONTRAST_PRIMARY;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Collection<RiskAlert> generateMockData() {
        List<RiskAlert> alerts = new ArrayList<>();
        Random random = new Random(42); // Fixed seed for reproducible data

        String[] alertTypes = getAlertTypes();
        String[] severities = getSeverities();
        String[] statuses = getStatuses();
        String[] entityTypes = getEntityTypes();

        String[] entityIds = {"C10045", "A20056", "T30078", "E40023", "V50091", "S60112", "P70134"};
        String[] entityNames = {
            "John Smith", 
            "Savings Account #12345", 
            "Wire Transfer #78901", 
            "Sarah Johnson", 
            "Acme Suppliers Inc.",
            "Core Banking System",
            "Loan Approval Process"
        };

        String[] assignees = {
            "John Smith", 
            "Maria Rodriguez", 
            "Wei Zhang", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] descriptions = {
            "Unusual pattern of transactions detected for this customer",
            "Account balance dropped below regulatory threshold",
            "Multiple high-value transactions in short time period",
            "Employee accessed customer data outside business hours",
            "Vendor risk score increased due to recent compliance issues",
            "System vulnerability detected in security scan",
            "Process exception rate exceeded threshold",
            "Multiple failed authentication attempts detected"
        };

        for (int i = 1; i <= 75; i++) {
            RiskAlert alert = new RiskAlert();
            alert.setAlertId("RISK" + String.format("%06d", i));

            String alertType = alertTypes[random.nextInt(alertTypes.length)];
            alert.setAlertType(alertType);

            // Assign severity with some weighting toward medium
            int severityRoll = random.nextInt(10);
            if (severityRoll < 1) {
                alert.setSeverity("Critical");
            } else if (severityRoll < 4) {
                alert.setSeverity("High");
            } else if (severityRoll < 8) {
                alert.setSeverity("Medium");
            } else {
                alert.setSeverity("Low");
            }

            alert.setStatus(statuses[random.nextInt(statuses.length)]);

            int entityIndex = random.nextInt(entityIds.length);
            alert.setEntityId(entityIds[entityIndex]);
            alert.setEntityName(entityNames[entityIndex]);
            alert.setEntityType(entityTypes[entityIndex % entityTypes.length]);

            // Generate a random generated date within the last 30 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime generatedDate = now.minusDays(random.nextInt(30)).minusHours(random.nextInt(24));
            alert.setGeneratedDate(generatedDate);

            alert.setAssignedTo(assignees[random.nextInt(assignees.length)]);
            alert.setDescription(descriptions[random.nextInt(descriptions.length)]);

            alerts.add(alert);
        }

        return alerts;
    }

    // Inner class to represent a risk alert
    public static class RiskAlert {
        private String alertId;
        private String alertType;
        private String severity;
        private String status;
        private String entityId;
        private String entityName;
        private String entityType;
        private LocalDateTime generatedDate;
        private String assignedTo;
        private String description;

        public String getAlertId() {
            return alertId;
        }

        public void setAlertId(String alertId) {
            this.alertId = alertId;
        }

        public String getAlertType() {
            return alertType;
        }

        public void setAlertType(String alertType) {
            this.alertType = alertType;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public String getEntityType() {
            return entityType;
        }

        public void setEntityType(String entityType) {
            this.entityType = entityType;
        }

        public LocalDateTime getGeneratedDate() {
            return generatedDate;
        }

        public void setGeneratedDate(LocalDateTime generatedDate) {
            this.generatedDate = generatedDate;
        }

        public String getAssignedTo() {
            return assignedTo;
        }

        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
