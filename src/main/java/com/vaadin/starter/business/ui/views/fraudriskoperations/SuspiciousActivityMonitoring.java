package com.vaadin.starter.business.ui.views.fraudriskoperations;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.fraudriskoperations.SuspiciousActivityDTO;
import com.vaadin.starter.business.backend.service.FraudRiskOperationsService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeColor;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeShape;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeSize;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@PageTitle(NavigationConstants.SUSPICIOUS_ACTIVITY_MONITORING)
@Route(value = "fraud-risk/suspicious-activity", layout = MainLayout.class)
public class SuspiciousActivityMonitoring extends ViewFrame {

    private final FraudRiskOperationsService fraudRiskOperationsService;

    private Grid<SuspiciousActivity> grid;
    private ListDataProvider<SuspiciousActivity> dataProvider;

    // Filter form fields
    private TextField activityIdFilter;
    private TextField customerIdFilter;
    private TextField accountNumberFilter;
    private ComboBox<String> activityTypeFilter;
    private ComboBox<String> riskLevelFilter;
    private ComboBox<String> statusFilter;
    private DatePicker detectedDateFromFilter;
    private DatePicker detectedDateToFilter;
    private TextField amountFilter;

    @Autowired
    public SuspiciousActivityMonitoring(FraudRiskOperationsService fraudRiskOperationsService) {
        this.fraudRiskOperationsService = fraudRiskOperationsService;
        setViewContent(createContent());
    }

    private Component createContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setPadding(false);
        content.setSpacing(false);

        content.add(createDashboardHeader());
        content.add(createDashboardStats());
        content.add(createFilterForm());
        content.add(createGrid());

        return content;
    }

    private Component createDashboardHeader() {
        H3 header = new H3("Suspicious Activity Monitoring Dashboard");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createDashboardStats() {
        HorizontalLayout statsLayout = new HorizontalLayout();
        statsLayout.setWidthFull();
        statsLayout.setSpacing(true);
        statsLayout.setPadding(true);

        // Add stat cards
        statsLayout.add(createStatCard("High Risk Alerts", "24", BadgeColor.ERROR));
        statsLayout.add(createStatCard("Medium Risk Alerts", "47", BadgeColor.CONTRAST_PRIMARY));
        statsLayout.add(createStatCard("Low Risk Alerts", "83", BadgeColor.SUCCESS));
        statsLayout.add(createStatCard("Pending Review Alerts", "18", BadgeColor.CONTRAST));

        // Add chart
        statsLayout.add(createActivityChart());

        return statsLayout;
    }

    private Component createStatCard(String title, String value, BadgeColor color) {
        Div card = new Div();
        card.getStyle().set("background-color", "var(--lumo-base-color)");
        card.getStyle().set("border-radius", "var(--lumo-border-radius)");
        card.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        card.getStyle().set("padding", "1rem");
        card.getStyle().set("text-align", "center");
        card.getStyle().set("flex", "1");

        Span titleSpan = new Span(title);
        titleSpan.getStyle().set("font-size", "var(--lumo-font-size-s)");
        titleSpan.getStyle().set("color", "var(--lumo-secondary-text-color)");
        titleSpan.getStyle().set("display", "block");

        Span valueSpan = new Span(value);
        valueSpan.getStyle().set("font-size", "var(--lumo-font-size-xxl)");
        valueSpan.getStyle().set("font-weight", "bold");
        valueSpan.getStyle().set("color", "var(--lumo-primary-text-color)");
        valueSpan.getStyle().set("display", "block");
        valueSpan.getStyle().set("margin-top", "0.5rem");

        Badge badge = new Badge(title, color, BadgeSize.S, BadgeShape.PILL);

        card.add(titleSpan, valueSpan);

        return card;
    }

    private Component createActivityChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.setWidth("100%");
        chart.setHeight("200px");

        Configuration configuration = chart.getConfiguration();
        configuration.setTitle("Activity by Type");
        configuration.getTitle().setMargin(0);
        configuration.getLegend().setEnabled(false);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Card Fraud", "AML", "Identity Theft", "Account Takeover", "Unusual Login");
        configuration.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Count");
        configuration.addyAxis(yAxis);

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Card Fraud", 35));
        series.add(new DataSeriesItem("AML", 28));
        series.add(new DataSeriesItem("Identity Theft", 17));
        series.add(new DataSeriesItem("Account Takeover", 42));
        series.add(new DataSeriesItem("Unusual Login", 32));

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setColorByPoint(true);
        series.setPlotOptions(plotOptions);

        configuration.addSeries(series);

        return chart;
    }

    private Component createFilterForm() {
        // Initialize filter fields
        activityIdFilter = new TextField();
        activityIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        activityIdFilter.setClearButtonVisible(true);

        customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        accountNumberFilter = new TextField();
        accountNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        accountNumberFilter.setClearButtonVisible(true);

        activityTypeFilter = new ComboBox<>();
        activityTypeFilter.setItems(getActivityTypes());
        activityTypeFilter.setClearButtonVisible(true);

        riskLevelFilter = new ComboBox<>();
        riskLevelFilter.setItems(getRiskLevels());
        riskLevelFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems(getStatuses());
        statusFilter.setClearButtonVisible(true);

        detectedDateFromFilter = new DatePicker();
        detectedDateFromFilter.setClearButtonVisible(true);

        detectedDateToFilter = new DatePicker();
        detectedDateToFilter.setClearButtonVisible(true);

        amountFilter = new TextField();
        amountFilter.setValueChangeMode(ValueChangeMode.EAGER);
        amountFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(activityIdFilter, "Activity ID");
        formLayout.addFormItem(customerIdFilter, "Customer ID");
        formLayout.addFormItem(accountNumberFilter, "Account Number");
        formLayout.addFormItem(activityTypeFilter, "Activity Type");
        formLayout.addFormItem(riskLevelFilter, "Risk Level");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(detectedDateFromFilter, "Detected From");
        formLayout.addFormItem(detectedDateToFilter, "Detected To");
        formLayout.addFormItem(amountFilter, "Amount");

        formLayout.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("900px", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP)
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

    private Grid<SuspiciousActivity> createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));

        // Initialize data provider with data from service
        Collection<SuspiciousActivity> activities = getSuspiciousActivitiesFromService();
        dataProvider = new ListDataProvider<>(activities);
        grid.setDataProvider(dataProvider);

        grid.setId("suspiciousActivities");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(SuspiciousActivity::getActivityId)
                .setHeader("Activity ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(SuspiciousActivity::getCustomerId)
                .setHeader("Customer ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(SuspiciousActivity::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(SuspiciousActivity::getAccountNumber)
                .setHeader("Account Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(SuspiciousActivity::getActivityType)
                .setHeader("Activity Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(activity -> createRiskLevelBadge(activity.getRiskLevel()))
                .setHeader("Risk Level")
                .setSortable(true)
                .setComparator(SuspiciousActivity::getRiskLevel)
                .setWidth("120px");
        grid.addColumn(SuspiciousActivity::getStatus)
                .setHeader("Status")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(activity -> {
                    if (activity.getDetectedDate() != null) {
                        return activity.getDetectedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                    return "";
                })
                .setHeader("Detected Date")
                .setSortable(true)
                .setComparator(SuspiciousActivity::getDetectedDate)
                .setWidth("150px");
        grid.addColumn(SuspiciousActivity::getAmount)
                .setHeader("Amount")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(SuspiciousActivity::getDescription)
                .setHeader("Description")
                .setSortable(true)
                .setWidth("250px");

        return grid;
    }

    private void viewDetails(SuspiciousActivity activity) {
        // Navigate to the details view
        UI.getCurrent().navigate(SuspiciousActivityDetails.class, activity.getActivityId());
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply activity ID filter
        if (activityIdFilter.getValue() != null && !activityIdFilter.getValue().isEmpty()) {
            String activityIdValue = activityIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(activity -> 
                activity.getActivityId() != null && 
                activity.getActivityId().toLowerCase().contains(activityIdValue));
        }

        // Apply customer ID filter
        if (customerIdFilter.getValue() != null && !customerIdFilter.getValue().isEmpty()) {
            String customerIdValue = customerIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(activity -> 
                activity.getCustomerId() != null && 
                activity.getCustomerId().toLowerCase().contains(customerIdValue));
        }

        // Apply account number filter
        if (accountNumberFilter.getValue() != null && !accountNumberFilter.getValue().isEmpty()) {
            String accountNumberValue = accountNumberFilter.getValue().toLowerCase();
            dataProvider.addFilter(activity -> 
                activity.getAccountNumber() != null && 
                activity.getAccountNumber().toLowerCase().contains(accountNumberValue));
        }

        // Apply activity type filter
        if (activityTypeFilter.getValue() != null) {
            String activityTypeValue = activityTypeFilter.getValue();
            dataProvider.addFilter(activity -> 
                activity.getActivityType() != null && 
                activity.getActivityType().equals(activityTypeValue));
        }

        // Apply risk level filter
        if (riskLevelFilter.getValue() != null) {
            String riskLevelValue = riskLevelFilter.getValue();
            dataProvider.addFilter(activity -> 
                activity.getRiskLevel() != null && 
                activity.getRiskLevel().equals(riskLevelValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(activity -> 
                activity.getStatus() != null && 
                activity.getStatus().equals(statusValue));
        }

        // Apply detected date from filter
        if (detectedDateFromFilter.getValue() != null) {
            java.time.LocalDate fromDate = detectedDateFromFilter.getValue();
            dataProvider.addFilter(activity -> 
                activity.getDetectedDate() != null && 
                !activity.getDetectedDate().toLocalDate().isBefore(fromDate));
        }

        // Apply detected date to filter
        if (detectedDateToFilter.getValue() != null) {
            java.time.LocalDate toDate = detectedDateToFilter.getValue();
            dataProvider.addFilter(activity -> 
                activity.getDetectedDate() != null && 
                !activity.getDetectedDate().toLocalDate().isAfter(toDate));
        }

        // Apply amount filter
        if (amountFilter.getValue() != null && !amountFilter.getValue().isEmpty()) {
            try {
                double amountValue = Double.parseDouble(amountFilter.getValue());
                dataProvider.addFilter(activity -> 
                    activity.getAmount() >= amountValue);
            } catch (NumberFormatException e) {
                // Ignore invalid number format
            }
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        activityIdFilter.clear();
        customerIdFilter.clear();
        accountNumberFilter.clear();
        activityTypeFilter.clear();
        riskLevelFilter.clear();
        statusFilter.clear();
        detectedDateFromFilter.clear();
        detectedDateToFilter.clear();
        amountFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private String[] getActivityTypes() {
        return new String[] {
            "Unusual Transaction", 
            "Multiple Failed Logins", 
            "Large Cash Deposit", 
            "Cross-Border Transfer", 
            "Identity Verification Failure",
            "Unusual Login Location",
            "Multiple Account Access",
            "Rapid Fund Movement"
        };
    }

    private String[] getRiskLevels() {
        return new String[] {"High", "Medium", "Low"};
    }

    private String[] getStatuses() {
        return new String[] {"New", "Under Review", "Escalated", "Closed", "False Positive"};
    }

    private Component createRiskLevelBadge(String riskLevel) {
        BadgeColor color;
        switch (riskLevel) {
            case "High":
                color = BadgeColor.ERROR;
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

        return new Badge(riskLevel, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Collection<SuspiciousActivity> getSuspiciousActivitiesFromService() {
        // Get suspicious activities from the service
        Collection<SuspiciousActivityDTO> activityDTOs = fraudRiskOperationsService.getSuspiciousActivities();

        // Convert DTOs to view model objects
        return activityDTOs.stream()
                .map(this::convertToViewModel)
                .collect(Collectors.toList());
    }

    private SuspiciousActivity convertToViewModel(SuspiciousActivityDTO dto) {
        SuspiciousActivity activity = new SuspiciousActivity();
        activity.setActivityId(dto.getActivityId());
        activity.setCustomerId(dto.getCustomerId());
        activity.setCustomerName(dto.getCustomerName());
        activity.setAccountNumber(dto.getAccountNumber());
        activity.setActivityType(dto.getActivityType());
        activity.setRiskLevel(dto.getRiskLevel());
        activity.setStatus(dto.getStatus());
        activity.setDetectedDate(dto.getDetectedDate());
        activity.setAmount(dto.getAmount());
        activity.setDescription(dto.getDescription());
        return activity;
    }


    // Inner class to represent a suspicious activity
    public static class SuspiciousActivity {
        private String activityId;
        private String customerId;
        private String customerName;
        private String accountNumber;
        private String activityType;
        private String riskLevel;
        private String status;
        private LocalDateTime detectedDate;
        private double amount;
        private String description;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getActivityType() {
            return activityType;
        }

        public void setActivityType(String activityType) {
            this.activityType = activityType;
        }

        public String getRiskLevel() {
            return riskLevel;
        }

        public void setRiskLevel(String riskLevel) {
            this.riskLevel = riskLevel;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LocalDateTime getDetectedDate() {
            return detectedDate;
        }

        public void setDetectedDate(LocalDateTime detectedDate) {
            this.detectedDate = detectedDate;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
