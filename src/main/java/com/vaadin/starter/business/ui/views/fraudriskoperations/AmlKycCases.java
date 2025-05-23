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

@PageTitle(NavigationConstants.AML_KYC_CASES)
@Route(value = "fraud-risk/aml-kyc", layout = MainLayout.class)
public class AmlKycCases extends ViewFrame {

    private Grid<AmlKycCase> grid;
    private ListDataProvider<AmlKycCase> dataProvider;

    // Filter form fields
    private TextField caseIdFilter;
    private TextField customerIdFilter;
    private TextField customerNameFilter;
    private ComboBox<String> caseTypeFilter;
    private ComboBox<String> statusFilter;
    private ComboBox<String> riskLevelFilter;
    private TextField assignedToFilter;
    private DatePicker createdDateFromFilter;
    private DatePicker createdDateToFilter;
    private ComboBox<String> regulatoryBodyFilter;

    public AmlKycCases() {
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
        H3 header = new H3("AML/KYC Case Management");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createFilterForm() {
        // Initialize filter fields
        caseIdFilter = new TextField();
        caseIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        caseIdFilter.setClearButtonVisible(true);

        customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        customerNameFilter = new TextField();
        customerNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilter.setClearButtonVisible(true);

        caseTypeFilter = new ComboBox<>();
        caseTypeFilter.setItems(getCaseTypes());
        caseTypeFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems(getStatuses());
        statusFilter.setClearButtonVisible(true);

        riskLevelFilter = new ComboBox<>();
        riskLevelFilter.setItems(getRiskLevels());
        riskLevelFilter.setClearButtonVisible(true);

        assignedToFilter = new TextField();
        assignedToFilter.setValueChangeMode(ValueChangeMode.EAGER);
        assignedToFilter.setClearButtonVisible(true);

        createdDateFromFilter = new DatePicker();
        createdDateFromFilter.setClearButtonVisible(true);

        createdDateToFilter = new DatePicker();
        createdDateToFilter.setClearButtonVisible(true);

        regulatoryBodyFilter = new ComboBox<>();
        regulatoryBodyFilter.setItems(getRegulatoryBodies());
        regulatoryBodyFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(caseIdFilter, "Case ID");
        formLayout.addFormItem(customerIdFilter, "Customer ID");
        formLayout.addFormItem(customerNameFilter, "Customer Name");
        formLayout.addFormItem(caseTypeFilter, "Case Type");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(riskLevelFilter, "Risk Level");
        formLayout.addFormItem(assignedToFilter, "Assigned To");
        formLayout.addFormItem(createdDateFromFilter, "Created From");
        formLayout.addFormItem(createdDateToFilter, "Created To");
        formLayout.addFormItem(regulatoryBodyFilter, "Regulatory Body");

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

    private Grid<AmlKycCase> createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));

        // Initialize data provider with mock data
        Collection<AmlKycCase> cases = generateMockData();
        dataProvider = new ListDataProvider<>(cases);
        grid.setDataProvider(dataProvider);

        grid.setId("amlKycCases");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(AmlKycCase::getCaseId)
                .setHeader("Case ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(AmlKycCase::getCustomerId)
                .setHeader("Customer ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(AmlKycCase::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(AmlKycCase::getCaseType)
                .setHeader("Case Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(amlCase -> createStatusBadge(amlCase.getStatus()))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(AmlKycCase::getStatus)
                .setWidth("120px");
        grid.addColumn(amlCase -> createRiskLevelBadge(amlCase.getRiskLevel()))
                .setHeader("Risk Level")
                .setSortable(true)
                .setComparator(AmlKycCase::getRiskLevel)
                .setWidth("120px");
        grid.addColumn(AmlKycCase::getAssignedTo)
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(amlCase -> {
                    if (amlCase.getCreatedDate() != null) {
                        return amlCase.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                    return "";
                })
                .setHeader("Created Date")
                .setSortable(true)
                .setComparator(AmlKycCase::getCreatedDate)
                .setWidth("150px");
        grid.addColumn(amlCase -> {
                    if (amlCase.getDueDate() != null) {
                        return amlCase.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    return "";
                })
                .setHeader("Due Date")
                .setSortable(true)
                .setComparator(AmlKycCase::getDueDate)
                .setWidth("120px");
        grid.addColumn(AmlKycCase::getRegulatoryBody)
                .setHeader("Regulatory Body")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(AmlKycCase::getNotes)
                .setHeader("Notes")
                .setSortable(true)
                .setWidth("250px");

        return grid;
    }

    private void viewDetails(AmlKycCase amlCase) {
        // Navigate to the details view
        UI.getCurrent().navigate(AmlKycCaseDetails.class, amlCase.getCaseId());
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply case ID filter
        if (caseIdFilter.getValue() != null && !caseIdFilter.getValue().isEmpty()) {
            String caseIdValue = caseIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(amlCase -> 
                amlCase.getCaseId() != null && 
                amlCase.getCaseId().toLowerCase().contains(caseIdValue));
        }

        // Apply customer ID filter
        if (customerIdFilter.getValue() != null && !customerIdFilter.getValue().isEmpty()) {
            String customerIdValue = customerIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(amlCase -> 
                amlCase.getCustomerId() != null && 
                amlCase.getCustomerId().toLowerCase().contains(customerIdValue));
        }

        // Apply customer name filter
        if (customerNameFilter.getValue() != null && !customerNameFilter.getValue().isEmpty()) {
            String customerNameValue = customerNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(amlCase -> 
                amlCase.getCustomerName() != null && 
                amlCase.getCustomerName().toLowerCase().contains(customerNameValue));
        }

        // Apply case type filter
        if (caseTypeFilter.getValue() != null) {
            String caseTypeValue = caseTypeFilter.getValue();
            dataProvider.addFilter(amlCase -> 
                amlCase.getCaseType() != null && 
                amlCase.getCaseType().equals(caseTypeValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(amlCase -> 
                amlCase.getStatus() != null && 
                amlCase.getStatus().equals(statusValue));
        }

        // Apply risk level filter
        if (riskLevelFilter.getValue() != null) {
            String riskLevelValue = riskLevelFilter.getValue();
            dataProvider.addFilter(amlCase -> 
                amlCase.getRiskLevel() != null && 
                amlCase.getRiskLevel().equals(riskLevelValue));
        }

        // Apply assigned to filter
        if (assignedToFilter.getValue() != null && !assignedToFilter.getValue().isEmpty()) {
            String assignedToValue = assignedToFilter.getValue().toLowerCase();
            dataProvider.addFilter(amlCase -> 
                amlCase.getAssignedTo() != null && 
                amlCase.getAssignedTo().toLowerCase().contains(assignedToValue));
        }

        // Apply created date from filter
        if (createdDateFromFilter.getValue() != null) {
            java.time.LocalDate fromDate = createdDateFromFilter.getValue();
            dataProvider.addFilter(amlCase -> 
                amlCase.getCreatedDate() != null && 
                !amlCase.getCreatedDate().toLocalDate().isBefore(fromDate));
        }

        // Apply created date to filter
        if (createdDateToFilter.getValue() != null) {
            java.time.LocalDate toDate = createdDateToFilter.getValue();
            dataProvider.addFilter(amlCase -> 
                amlCase.getCreatedDate() != null && 
                !amlCase.getCreatedDate().toLocalDate().isAfter(toDate));
        }

        // Apply regulatory body filter
        if (regulatoryBodyFilter.getValue() != null) {
            String regulatoryBodyValue = regulatoryBodyFilter.getValue();
            dataProvider.addFilter(amlCase -> 
                amlCase.getRegulatoryBody() != null && 
                amlCase.getRegulatoryBody().equals(regulatoryBodyValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        caseIdFilter.clear();
        customerIdFilter.clear();
        customerNameFilter.clear();
        caseTypeFilter.clear();
        statusFilter.clear();
        riskLevelFilter.clear();
        assignedToFilter.clear();
        createdDateFromFilter.clear();
        createdDateToFilter.clear();
        regulatoryBodyFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private String[] getCaseTypes() {
        return new String[] {
            "KYC Verification", 
            "AML Investigation", 
            "Suspicious Activity Report", 
            "Enhanced Due Diligence", 
            "Periodic Review",
            "Sanctions Screening",
            "PEP Screening",
            "Transaction Monitoring Alert"
        };
    }

    private String[] getStatuses() {
        return new String[] {
            "New", 
            "In Progress", 
            "Pending Documentation", 
            "Pending Review", 
            "Escalated",
            "Reported to Authorities",
            "Closed - Compliant",
            "Closed - Non-Compliant"
        };
    }

    private String[] getRiskLevels() {
        return new String[] {"High", "Medium", "Low"};
    }

    private String[] getRegulatoryBodies() {
        return new String[] {
            "FinCEN", 
            "OFAC", 
            "FCA", 
            "FINTRAC", 
            "AUSTRAC",
            "MAS",
            "HKMA",
            "BaFin"
        };
    }

    private Component createStatusBadge(String status) {
        BadgeColor color;
        switch (status) {
            case "New":
                color = BadgeColor.NORMAL;
                break;
            case "In Progress":
                color = BadgeColor.CONTRAST;
                break;
            case "Pending Documentation":
            case "Pending Review":
                color = BadgeColor.CONTRAST_PRIMARY;
                break;
            case "Escalated":
            case "Reported to Authorities":
                color = BadgeColor.ERROR;
                break;
            case "Closed - Non-Compliant":
                color = BadgeColor.ERROR_PRIMARY;
                break;
            case "Closed - Compliant":
                color = BadgeColor.SUCCESS;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
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

    private Collection<AmlKycCase> generateMockData() {
        List<AmlKycCase> cases = new ArrayList<>();
        Random random = new Random(42); // Fixed seed for reproducible data

        String[] customerIds = {"C10045", "C20056", "C30078", "C40023", "C50091", "C60112", "C70134", "C80156"};
        String[] customerNames = {
            "John Smith", 
            "Maria Garcia", 
            "Wei Chen", 
            "Sarah Johnson", 
            "Ahmed Hassan",
            "Olivia Wilson",
            "Michael Brown",
            "Fatima Al-Farsi"
        };

        String[] caseTypes = getCaseTypes();
        String[] statuses = getStatuses();
        String[] riskLevels = getRiskLevels();
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

        String[] regulatoryBodies = getRegulatoryBodies();

        String[] notes = {
            "Customer documentation incomplete, follow-up required",
            "Unusual transaction patterns detected, further investigation needed",
            "PEP status confirmed, enhanced due diligence in progress",
            "Sanctions screening alert, verification in progress",
            "Periodic review due to high-risk jurisdiction",
            "Missing source of funds documentation",
            "Suspicious activity report filed with authorities",
            "Beneficial ownership structure requires clarification"
        };

        for (int i = 1; i <= 50; i++) {
            AmlKycCase amlCase = new AmlKycCase();
            amlCase.setCaseId("AML" + String.format("%06d", i));

            int customerIndex = random.nextInt(customerIds.length);
            amlCase.setCustomerId(customerIds[customerIndex]);
            amlCase.setCustomerName(customerNames[customerIndex]);

            amlCase.setCaseType(caseTypes[random.nextInt(caseTypes.length)]);
            amlCase.setStatus(statuses[random.nextInt(statuses.length)]);
            amlCase.setRiskLevel(riskLevels[random.nextInt(riskLevels.length)]);
            amlCase.setAssignedTo(assignees[random.nextInt(assignees.length)]);

            // Generate a random created date within the last 180 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime createdDate = now.minusDays(random.nextInt(180)).minusHours(random.nextInt(24));
            amlCase.setCreatedDate(createdDate);

            // Generate a random due date between created date and 30 days in the future
            long createdEpochDay = createdDate.toLocalDate().toEpochDay();
            long futureEpochDay = now.plusDays(30).toLocalDate().toEpochDay();
            long randomDay = createdEpochDay + random.nextInt((int) (futureEpochDay - createdEpochDay + 1));
            amlCase.setDueDate(java.time.LocalDate.ofEpochDay(randomDay));

            amlCase.setRegulatoryBody(regulatoryBodies[random.nextInt(regulatoryBodies.length)]);
            amlCase.setNotes(notes[random.nextInt(notes.length)]);

            cases.add(amlCase);
        }

        return cases;
    }

    // Inner class to represent an AML/KYC case
    public static class AmlKycCase {
        private String caseId;
        private String customerId;
        private String customerName;
        private String caseType;
        private String status;
        private String riskLevel;
        private String assignedTo;
        private LocalDateTime createdDate;
        private java.time.LocalDate dueDate;
        private String regulatoryBody;
        private String notes;

        public String getCaseId() {
            return caseId;
        }

        public void setCaseId(String caseId) {
            this.caseId = caseId;
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

        public String getCaseType() {
            return caseType;
        }

        public void setCaseType(String caseType) {
            this.caseType = caseType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRiskLevel() {
            return riskLevel;
        }

        public void setRiskLevel(String riskLevel) {
            this.riskLevel = riskLevel;
        }

        public String getAssignedTo() {
            return assignedTo;
        }

        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }

        public LocalDateTime getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
        }

        public java.time.LocalDate getDueDate() {
            return dueDate;
        }

        public void setDueDate(java.time.LocalDate dueDate) {
            this.dueDate = dueDate;
        }

        public String getRegulatoryBody() {
            return regulatoryBody;
        }

        public void setRegulatoryBody(String regulatoryBody) {
            this.regulatoryBody = regulatoryBody;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }
}
