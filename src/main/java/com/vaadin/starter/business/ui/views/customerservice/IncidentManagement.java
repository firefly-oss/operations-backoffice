package com.vaadin.starter.business.ui.views.customerservice;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.Incident;
import com.vaadin.starter.business.backend.service.IncidentService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@PageTitle(NavigationConstants.INCIDENT_MANAGEMENT)
@Route(value = "incident-management", layout = MainLayout.class)
public class IncidentManagement extends ViewFrame {

    private final IncidentService incidentService;
    private Grid<Incident> grid;
    private ListDataProvider<Incident> dataProvider;

    // Filter fields
    private TextField idFilter;
    private TextField incidentNumberFilter;
    private TextField titleFilter;
    private ComboBox<String> statusFilter;
    private ComboBox<String> priorityFilter;
    private ComboBox<String> impactFilter;
    private ComboBox<String> categoryFilter;
    private TextField affectedServiceFilter;
    private TextField reportedByFilter;
    private TextField assignedToFilter;
    private DatePicker reportedDateFromFilter;
    private DatePicker reportedDateToFilter;

    @Autowired
    public IncidentManagement(IncidentService incidentService) {
        this.incidentService = incidentService;
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createFilterForm(), createGrid());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private void viewDetails(Incident incident) {
        // TODO: Create IncidentDetails view and uncomment the line below
        // UI.getCurrent().navigate(IncidentDetails.class, incident.getId());
    }

    private Component createFilterForm() {
        // Initialize filter fields
        idFilter = new TextField();
        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.setClearButtonVisible(true);

        incidentNumberFilter = new TextField();
        incidentNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        incidentNumberFilter.setClearButtonVisible(true);

        titleFilter = new TextField();
        titleFilter.setValueChangeMode(ValueChangeMode.EAGER);
        titleFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems(getIncidentStatuses());
        statusFilter.setClearButtonVisible(true);

        priorityFilter = new ComboBox<>();
        priorityFilter.setItems(getIncidentPriorities());
        priorityFilter.setClearButtonVisible(true);

        impactFilter = new ComboBox<>();
        impactFilter.setItems(getIncidentImpacts());
        impactFilter.setClearButtonVisible(true);

        categoryFilter = new ComboBox<>();
        categoryFilter.setItems(getIncidentCategories());
        categoryFilter.setClearButtonVisible(true);

        affectedServiceFilter = new TextField();
        affectedServiceFilter.setValueChangeMode(ValueChangeMode.EAGER);
        affectedServiceFilter.setClearButtonVisible(true);

        reportedByFilter = new TextField();
        reportedByFilter.setValueChangeMode(ValueChangeMode.EAGER);
        reportedByFilter.setClearButtonVisible(true);

        assignedToFilter = new TextField();
        assignedToFilter.setValueChangeMode(ValueChangeMode.EAGER);
        assignedToFilter.setClearButtonVisible(true);

        reportedDateFromFilter = new DatePicker();
        reportedDateFromFilter.setClearButtonVisible(true);

        reportedDateToFilter = new DatePicker();
        reportedDateToFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(idFilter, "ID");
        formLayout.addFormItem(incidentNumberFilter, "Incident Number");
        formLayout.addFormItem(titleFilter, "Title");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(priorityFilter, "Priority");
        formLayout.addFormItem(impactFilter, "Impact");
        formLayout.addFormItem(categoryFilter, "Category");
        formLayout.addFormItem(affectedServiceFilter, "Affected Service");
        formLayout.addFormItem(reportedByFilter, "Reported By");
        formLayout.addFormItem(assignedToFilter, "Assigned To");
        formLayout.addFormItem(reportedDateFromFilter, "Reported Date From");
        formLayout.addFormItem(reportedDateToFilter, "Reported Date To");

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

    private Grid<Incident> createGrid() {
        grid = new Grid<>();
        dataProvider = new ListDataProvider<>(incidentService.getIncidents());
        grid.setDataProvider(dataProvider);

        // Add columns to the grid
        grid.addColumn(Incident::getId)
                .setHeader("ID")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(Incident::getIncidentNumber)
                .setHeader("Incident Number")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(Incident::getTitle)
                .setHeader("Title")
                .setFlexGrow(2)
                .setSortable(true);

        grid.addColumn(Incident::getStatus)
                .setHeader("Status")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(Incident::getPriority)
                .setHeader("Priority")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(Incident::getImpact)
                .setHeader("Impact")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(Incident::getCategory)
                .setHeader("Category")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(Incident::getAffectedService)
                .setHeader("Affected Service")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(Incident::getAssignedTo)
                .setHeader("Assigned To")
                .setFlexGrow(1)
                .setSortable(true);

        grid.addColumn(incident -> {
            LocalDateTime reportedDate = incident.getReportedDate();
            if (reportedDate != null) {
                return reportedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            return "";
        })
                .setHeader("Reported Date")
                .setFlexGrow(1)
                .setSortable(true);

        // Add click listener to rows
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                viewDetails(event.getValue());
            }
        });

        grid.setHeightFull();
        return grid;
    }

    private void applyFilters() {
        dataProvider.setFilter(incident -> {
            boolean matchesId = idFilter.getValue().isEmpty() || 
                    incident.getId().toLowerCase().contains(idFilter.getValue().toLowerCase());

            boolean matchesIncidentNumber = incidentNumberFilter.getValue().isEmpty() || 
                    (incident.getIncidentNumber() != null && 
                    incident.getIncidentNumber().toLowerCase().contains(incidentNumberFilter.getValue().toLowerCase()));

            boolean matchesTitle = titleFilter.getValue().isEmpty() || 
                    (incident.getTitle() != null && 
                    incident.getTitle().toLowerCase().contains(titleFilter.getValue().toLowerCase()));

            boolean matchesStatus = statusFilter.getValue() == null || 
                    (incident.getStatus() != null && 
                    incident.getStatus().equals(statusFilter.getValue()));

            boolean matchesPriority = priorityFilter.getValue() == null || 
                    (incident.getPriority() != null && 
                    incident.getPriority().equals(priorityFilter.getValue()));

            boolean matchesImpact = impactFilter.getValue() == null || 
                    (incident.getImpact() != null && 
                    incident.getImpact().equals(impactFilter.getValue()));

            boolean matchesCategory = categoryFilter.getValue() == null || 
                    (incident.getCategory() != null && 
                    incident.getCategory().equals(categoryFilter.getValue()));

            boolean matchesAffectedService = affectedServiceFilter.getValue().isEmpty() || 
                    (incident.getAffectedService() != null && 
                    incident.getAffectedService().toLowerCase().contains(affectedServiceFilter.getValue().toLowerCase()));

            boolean matchesReportedBy = reportedByFilter.getValue().isEmpty() || 
                    (incident.getReportedBy() != null && 
                    incident.getReportedBy().toLowerCase().contains(reportedByFilter.getValue().toLowerCase()));

            boolean matchesAssignedTo = assignedToFilter.getValue().isEmpty() || 
                    (incident.getAssignedTo() != null && 
                    incident.getAssignedTo().toLowerCase().contains(assignedToFilter.getValue().toLowerCase()));

            boolean matchesReportedDateFrom = reportedDateFromFilter.getValue() == null || 
                    (incident.getReportedDate() != null && 
                    !incident.getReportedDate().toLocalDate().isBefore(reportedDateFromFilter.getValue()));

            boolean matchesReportedDateTo = reportedDateToFilter.getValue() == null || 
                    (incident.getReportedDate() != null && 
                    !incident.getReportedDate().toLocalDate().isAfter(reportedDateToFilter.getValue()));

            return matchesId && matchesIncidentNumber && matchesTitle && matchesStatus && 
                   matchesPriority && matchesImpact && matchesCategory && matchesAffectedService && 
                   matchesReportedBy && matchesAssignedTo && matchesReportedDateFrom && matchesReportedDateTo;
        });
    }

    private void clearFilters() {
        idFilter.clear();
        incidentNumberFilter.clear();
        titleFilter.clear();
        statusFilter.clear();
        priorityFilter.clear();
        impactFilter.clear();
        categoryFilter.clear();
        affectedServiceFilter.clear();
        reportedByFilter.clear();
        assignedToFilter.clear();
        reportedDateFromFilter.clear();
        reportedDateToFilter.clear();
        dataProvider.clearFilters();
    }

    private String[] getIncidentStatuses() {
        return java.util.Arrays.stream(Incident.Status.values())
                .map(Incident.Status::getName)
                .toArray(String[]::new);
    }

    private String[] getIncidentPriorities() {
        return java.util.Arrays.stream(Incident.Priority.values())
                .map(Incident.Priority::getName)
                .toArray(String[]::new);
    }

    private String[] getIncidentImpacts() {
        return java.util.Arrays.stream(Incident.Impact.values())
                .map(Incident.Impact::getName)
                .toArray(String[]::new);
    }

    private String[] getIncidentCategories() {
        return java.util.Arrays.stream(Incident.Category.values())
                .map(Incident.Category::getName)
                .toArray(String[]::new);
    }
}
