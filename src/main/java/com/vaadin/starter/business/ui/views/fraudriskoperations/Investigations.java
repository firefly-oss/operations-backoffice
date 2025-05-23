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

@PageTitle(NavigationConstants.INVESTIGATIONS)
@Route(value = "fraud-risk/investigations", layout = MainLayout.class)
public class Investigations extends ViewFrame {

    private Grid<Investigation> grid;
    private ListDataProvider<Investigation> dataProvider;

    // Filter form fields
    private TextField caseIdFilter;
    private TextField subjectFilter;
    private ComboBox<String> typeFilter;
    private ComboBox<String> statusFilter;
    private ComboBox<String> priorityFilter;
    private TextField assignedToFilter;
    private DatePicker openedDateFromFilter;
    private DatePicker openedDateToFilter;

    public Investigations() {
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
        H3 header = new H3("Fraud & Risk Investigations");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createFilterForm() {
        // Initialize filter fields
        caseIdFilter = new TextField();
        caseIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        caseIdFilter.setClearButtonVisible(true);

        subjectFilter = new TextField();
        subjectFilter.setValueChangeMode(ValueChangeMode.EAGER);
        subjectFilter.setClearButtonVisible(true);

        typeFilter = new ComboBox<>();
        typeFilter.setItems(getInvestigationTypes());
        typeFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems(getStatuses());
        statusFilter.setClearButtonVisible(true);

        priorityFilter = new ComboBox<>();
        priorityFilter.setItems(getPriorities());
        priorityFilter.setClearButtonVisible(true);

        assignedToFilter = new TextField();
        assignedToFilter.setValueChangeMode(ValueChangeMode.EAGER);
        assignedToFilter.setClearButtonVisible(true);

        openedDateFromFilter = new DatePicker();
        openedDateFromFilter.setClearButtonVisible(true);

        openedDateToFilter = new DatePicker();
        openedDateToFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(subjectFilter, "Subject");
        formLayout.addFormItem(typeFilter, "Type");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(priorityFilter, "Priority");
        formLayout.addFormItem(assignedToFilter, "Assigned To");
        formLayout.addFormItem(openedDateFromFilter, "Opened From");
        formLayout.addFormItem(openedDateToFilter, "Opened To");

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

    private Grid<Investigation> createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));

        // Initialize data provider with mock data
        Collection<Investigation> investigations = generateMockData();
        dataProvider = new ListDataProvider<>(investigations);
        grid.setDataProvider(dataProvider);

        grid.setId("investigations");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(Investigation::getCaseId)
                .setHeader("Case ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Investigation::getSubject)
                .setHeader("Subject")
                .setSortable(true)
                .setWidth("250px");
        grid.addColumn(Investigation::getType)
                .setHeader("Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(investigation -> createStatusBadge(investigation.getStatus()))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Investigation::getStatus)
                .setWidth("120px");
        grid.addColumn(investigation -> createPriorityBadge(investigation.getPriority()))
                .setHeader("Priority")
                .setSortable(true)
                .setComparator(Investigation::getPriority)
                .setWidth("120px");
        grid.addColumn(Investigation::getAssignedTo)
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(investigation -> {
                    if (investigation.getOpenedDate() != null) {
                        return investigation.getOpenedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                    return "";
                })
                .setHeader("Opened Date")
                .setSortable(true)
                .setComparator(Investigation::getOpenedDate)
                .setWidth("150px");
        grid.addColumn(investigation -> {
                    if (investigation.getLastUpdated() != null) {
                        return investigation.getLastUpdated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                    return "";
                })
                .setHeader("Last Updated")
                .setSortable(true)
                .setComparator(Investigation::getLastUpdated)
                .setWidth("150px");
        grid.addColumn(Investigation::getDescription)
                .setHeader("Description")
                .setSortable(true)
                .setWidth("250px");

        return grid;
    }

    private void viewDetails(Investigation investigation) {
        // Navigate to the details view
        UI.getCurrent().navigate(InvestigationDetails.class, investigation.getCaseId());
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply case ID filter
        if (caseIdFilter.getValue() != null && !caseIdFilter.getValue().isEmpty()) {
            String caseIdValue = caseIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(investigation -> 
                investigation.getCaseId() != null && 
                investigation.getCaseId().toLowerCase().contains(caseIdValue));
        }

        // Apply subject filter
        if (subjectFilter.getValue() != null && !subjectFilter.getValue().isEmpty()) {
            String subjectValue = subjectFilter.getValue().toLowerCase();
            dataProvider.addFilter(investigation -> 
                investigation.getSubject() != null && 
                investigation.getSubject().toLowerCase().contains(subjectValue));
        }

        // Apply type filter
        if (typeFilter.getValue() != null) {
            String typeValue = typeFilter.getValue();
            dataProvider.addFilter(investigation -> 
                investigation.getType() != null && 
                investigation.getType().equals(typeValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(investigation -> 
                investigation.getStatus() != null && 
                investigation.getStatus().equals(statusValue));
        }

        // Apply priority filter
        if (priorityFilter.getValue() != null) {
            String priorityValue = priorityFilter.getValue();
            dataProvider.addFilter(investigation -> 
                investigation.getPriority() != null && 
                investigation.getPriority().equals(priorityValue));
        }

        // Apply assigned to filter
        if (assignedToFilter.getValue() != null && !assignedToFilter.getValue().isEmpty()) {
            String assignedToValue = assignedToFilter.getValue().toLowerCase();
            dataProvider.addFilter(investigation -> 
                investigation.getAssignedTo() != null && 
                investigation.getAssignedTo().toLowerCase().contains(assignedToValue));
        }

        // Apply opened date from filter
        if (openedDateFromFilter.getValue() != null) {
            java.time.LocalDate fromDate = openedDateFromFilter.getValue();
            dataProvider.addFilter(investigation -> 
                investigation.getOpenedDate() != null && 
                !investigation.getOpenedDate().toLocalDate().isBefore(fromDate));
        }

        // Apply opened date to filter
        if (openedDateToFilter.getValue() != null) {
            java.time.LocalDate toDate = openedDateToFilter.getValue();
            dataProvider.addFilter(investigation -> 
                investigation.getOpenedDate() != null && 
                !investigation.getOpenedDate().toLocalDate().isAfter(toDate));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        caseIdFilter.clear();
        subjectFilter.clear();
        typeFilter.clear();
        statusFilter.clear();
        priorityFilter.clear();
        assignedToFilter.clear();
        openedDateFromFilter.clear();
        openedDateToFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private String[] getInvestigationTypes() {
        return new String[] {
            "Fraud", 
            "Money Laundering", 
            "Identity Theft", 
            "Account Takeover", 
            "Internal Fraud",
            "Merchant Fraud",
            "Card Fraud",
            "Cyber Security"
        };
    }

    private String[] getStatuses() {
        return new String[] {
            "Open", 
            "In Progress", 
            "Pending Evidence", 
            "Pending Review", 
            "Escalated",
            "Closed - Substantiated",
            "Closed - Unsubstantiated",
            "Closed - Inconclusive"
        };
    }

    private String[] getPriorities() {
        return new String[] {"High", "Medium", "Low"};
    }

    private Component createStatusBadge(String status) {
        BadgeColor color;
        switch (status) {
            case "Open":
                color = BadgeColor.NORMAL;
                break;
            case "In Progress":
                color = BadgeColor.CONTRAST;
                break;
            case "Pending Evidence":
            case "Pending Review":
                color = BadgeColor.CONTRAST_PRIMARY;
                break;
            case "Escalated":
                color = BadgeColor.ERROR;
                break;
            case "Closed - Substantiated":
                color = BadgeColor.ERROR_PRIMARY;
                break;
            case "Closed - Unsubstantiated":
                color = BadgeColor.SUCCESS;
                break;
            case "Closed - Inconclusive":
                color = BadgeColor.NORMAL_PRIMARY;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Component createPriorityBadge(String priority) {
        BadgeColor color;
        switch (priority) {
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

        return new Badge(priority, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Collection<Investigation> generateMockData() {
        List<Investigation> investigations = new ArrayList<>();
        Random random = new Random(42); // Fixed seed for reproducible data

        String[] subjects = {
            "Suspicious transaction pattern detected",
            "Multiple account access from unusual locations",
            "Potential identity theft case",
            "Unusual wire transfer activity",
            "Employee reported suspicious behavior",
            "Potential money laundering scheme",
            "Credit card fraud ring",
            "Suspicious account opening documentation"
        };

        String[] types = getInvestigationTypes();
        String[] statuses = getStatuses();
        String[] priorities = getPriorities();
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
            "Investigation into suspicious transaction patterns across multiple accounts",
            "Review of potential identity theft based on customer complaint",
            "Analysis of unusual login patterns from foreign IP addresses",
            "Investigation of employee reported suspicious activity",
            "Review of potential money laundering through structured deposits",
            "Investigation of merchant processing unusual transaction volumes",
            "Analysis of potential internal fraud by employee",
            "Review of suspicious account opening documentation"
        };

        for (int i = 1; i <= 50; i++) {
            Investigation investigation = new Investigation();
            investigation.setCaseId("INV" + String.format("%06d", i));

            investigation.setSubject(subjects[random.nextInt(subjects.length)]);
            investigation.setType(types[random.nextInt(types.length)]);
            investigation.setStatus(statuses[random.nextInt(statuses.length)]);
            investigation.setPriority(priorities[random.nextInt(priorities.length)]);
            investigation.setAssignedTo(assignees[random.nextInt(assignees.length)]);

            // Generate a random opened date within the last 90 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime openedDate = now.minusDays(random.nextInt(90)).minusHours(random.nextInt(24));
            investigation.setOpenedDate(openedDate);

            // Generate a random last updated date between opened date and now
            long openedEpochDay = openedDate.toLocalDate().toEpochDay();
            long nowEpochDay = now.toLocalDate().toEpochDay();
            long randomDay = openedEpochDay + random.nextInt((int) (nowEpochDay - openedEpochDay + 1));
            investigation.setLastUpdated(LocalDateTime.of(
                java.time.LocalDate.ofEpochDay(randomDay),
                java.time.LocalTime.of(random.nextInt(24), random.nextInt(60))
            ));

            investigation.setDescription(descriptions[random.nextInt(descriptions.length)]);

            investigations.add(investigation);
        }

        return investigations;
    }

    // Inner class to represent an investigation
    public static class Investigation {
        private String caseId;
        private String subject;
        private String type;
        private String status;
        private String priority;
        private String assignedTo;
        private LocalDateTime openedDate;
        private LocalDateTime lastUpdated;
        private String description;

        public String getCaseId() {
            return caseId;
        }

        public void setCaseId(String caseId) {
            this.caseId = caseId;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getAssignedTo() {
            return assignedTo;
        }

        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }

        public LocalDateTime getOpenedDate() {
            return openedDate;
        }

        public void setOpenedDate(LocalDateTime openedDate) {
            this.openedDate = openedDate;
        }

        public LocalDateTime getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(LocalDateTime lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
