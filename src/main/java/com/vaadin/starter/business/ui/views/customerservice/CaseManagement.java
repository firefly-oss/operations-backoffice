package com.vaadin.starter.business.ui.views.customerservice;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
import com.vaadin.starter.business.backend.Case;
import com.vaadin.starter.business.backend.service.CaseService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.Collection;

@PageTitle(NavigationConstants.CASE_MANAGEMENT)
@Route(value = "customer-service/cases", layout = MainLayout.class)
public class CaseManagement extends ViewFrame {

    private Grid<Case> grid;
    private ListDataProvider<Case> dataProvider;

    // Filter form fields
    private TextField idFilter;
    private TextField caseNumberFilter;
    private TextField subjectFilter;
    private ComboBox<String> statusFilter;
    private ComboBox<String> priorityFilter;
    private ComboBox<String> categoryFilter;
    private TextField customerIdFilter;
    private TextField customerNameFilter;
    private TextField assignedToFilter;
    private DatePicker createdDateFromFilter;
    private DatePicker createdDateToFilter;
    private DatePicker dueDateFromFilter;
    private DatePicker dueDateToFilter;
    private ComboBox<String> sourceFilter;

    private final CaseService caseService;

    @Autowired
    public CaseManagement(CaseService caseService) {
        this.caseService = caseService;
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

    private void viewDetails(Case caseItem) {
        UI.getCurrent().navigate(CaseDetails.class, caseItem.getId());
    }

    private Component createFilterForm() {
        // Initialize filter fields
        idFilter = new TextField();
        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.setClearButtonVisible(true);

        caseNumberFilter = new TextField();
        caseNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        caseNumberFilter.setClearButtonVisible(true);

        subjectFilter = new TextField();
        subjectFilter.setValueChangeMode(ValueChangeMode.EAGER);
        subjectFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems(getCaseStatuses());
        statusFilter.setClearButtonVisible(true);

        priorityFilter = new ComboBox<>();
        priorityFilter.setItems(getCasePriorities());
        priorityFilter.setClearButtonVisible(true);

        categoryFilter = new ComboBox<>();
        categoryFilter.setItems(getCaseCategories());
        categoryFilter.setClearButtonVisible(true);

        customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        customerNameFilter = new TextField();
        customerNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilter.setClearButtonVisible(true);

        assignedToFilter = new TextField();
        assignedToFilter.setValueChangeMode(ValueChangeMode.EAGER);
        assignedToFilter.setClearButtonVisible(true);

        createdDateFromFilter = new DatePicker();
        createdDateFromFilter.setClearButtonVisible(true);

        createdDateToFilter = new DatePicker();
        createdDateToFilter.setClearButtonVisible(true);

        dueDateFromFilter = new DatePicker();
        dueDateFromFilter.setClearButtonVisible(true);

        dueDateToFilter = new DatePicker();
        dueDateToFilter.setClearButtonVisible(true);

        sourceFilter = new ComboBox<>();
        sourceFilter.setItems(getCaseSources());
        sourceFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(caseNumberFilter, "Case Number");
        formLayout.addFormItem(subjectFilter, "Subject");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(priorityFilter, "Priority");
        formLayout.addFormItem(categoryFilter, "Category");
        formLayout.addFormItem(customerIdFilter, "Customer ID");
        formLayout.addFormItem(customerNameFilter, "Customer Name");
        formLayout.addFormItem(assignedToFilter, "Assigned To");
        formLayout.addFormItem(createdDateFromFilter, "Created From");
        formLayout.addFormItem(createdDateToFilter, "Created To");
        formLayout.addFormItem(dueDateFromFilter, "Due From");
        formLayout.addFormItem(dueDateToFilter, "Due To");
        formLayout.addFormItem(sourceFilter, "Source");

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

    private Grid<Case> createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));

        // Initialize data provider
        Collection<Case> cases = caseService.getCases();
        dataProvider = new ListDataProvider<>(cases);
        grid.setDataProvider(dataProvider);

        grid.setId("cases");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(Case::getId)
                .setHeader("ID")
                .setSortable(true)
                .setWidth("100px");
        grid.addColumn(Case::getCaseNumber)
                .setHeader("Case Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Case::getSubject)
                .setHeader("Subject")
                .setSortable(true)
                .setWidth("250px");
        grid.addColumn(Case::getStatus)
                .setHeader("Status")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Case::getPriority)
                .setHeader("Priority")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Case::getCategory)
                .setHeader("Category")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Case::getCustomerName)
                .setHeader("Customer")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(Case::getAssignedTo)
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(caseItem -> {
                    if (caseItem.getCreatedDate() != null) {
                        return caseItem.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                    return "";
                })
                .setHeader("Created Date")
                .setSortable(true)
                .setComparator(Case::getCreatedDate)
                .setWidth("150px");
        grid.addColumn(caseItem -> {
                    if (caseItem.getDueDate() != null) {
                        return caseItem.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                    return "";
                })
                .setHeader("Due Date")
                .setSortable(true)
                .setComparator(Case::getDueDate)
                .setWidth("150px");
        grid.addColumn(Case::getSource)
                .setHeader("Source")
                .setSortable(true)
                .setWidth("120px");

        return grid;
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply ID filter
        if (idFilter.getValue() != null && !idFilter.getValue().isEmpty()) {
            String idValue = idFilter.getValue();
            dataProvider.addFilter(caseItem -> 
                caseItem.getId() != null && 
                caseItem.getId().contains(idValue));
        }

        // Apply case number filter
        if (caseNumberFilter.getValue() != null && !caseNumberFilter.getValue().isEmpty()) {
            String caseNumberValue = caseNumberFilter.getValue().toLowerCase();
            dataProvider.addFilter(caseItem -> 
                caseItem.getCaseNumber() != null && 
                caseItem.getCaseNumber().toLowerCase().contains(caseNumberValue));
        }

        // Apply subject filter
        if (subjectFilter.getValue() != null && !subjectFilter.getValue().isEmpty()) {
            String subjectValue = subjectFilter.getValue().toLowerCase();
            dataProvider.addFilter(caseItem -> 
                caseItem.getSubject() != null && 
                caseItem.getSubject().toLowerCase().contains(subjectValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(caseItem -> 
                caseItem.getStatus() != null && 
                caseItem.getStatus().equals(statusValue));
        }

        // Apply priority filter
        if (priorityFilter.getValue() != null) {
            String priorityValue = priorityFilter.getValue();
            dataProvider.addFilter(caseItem -> 
                caseItem.getPriority() != null && 
                caseItem.getPriority().equals(priorityValue));
        }

        // Apply category filter
        if (categoryFilter.getValue() != null) {
            String categoryValue = categoryFilter.getValue();
            dataProvider.addFilter(caseItem -> 
                caseItem.getCategory() != null && 
                caseItem.getCategory().equals(categoryValue));
        }

        // Apply customer ID filter
        if (customerIdFilter.getValue() != null && !customerIdFilter.getValue().isEmpty()) {
            String customerIdValue = customerIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(caseItem -> 
                caseItem.getCustomerId() != null && 
                caseItem.getCustomerId().toLowerCase().contains(customerIdValue));
        }

        // Apply customer name filter
        if (customerNameFilter.getValue() != null && !customerNameFilter.getValue().isEmpty()) {
            String customerNameValue = customerNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(caseItem -> 
                caseItem.getCustomerName() != null && 
                caseItem.getCustomerName().toLowerCase().contains(customerNameValue));
        }

        // Apply assigned to filter
        if (assignedToFilter.getValue() != null && !assignedToFilter.getValue().isEmpty()) {
            String assignedToValue = assignedToFilter.getValue().toLowerCase();
            dataProvider.addFilter(caseItem -> 
                caseItem.getAssignedTo() != null && 
                caseItem.getAssignedTo().toLowerCase().contains(assignedToValue));
        }

        // Apply created date from filter
        if (createdDateFromFilter.getValue() != null) {
            java.time.LocalDate fromDate = createdDateFromFilter.getValue();
            dataProvider.addFilter(caseItem -> 
                caseItem.getCreatedDate() != null && 
                !caseItem.getCreatedDate().toLocalDate().isBefore(fromDate));
        }

        // Apply created date to filter
        if (createdDateToFilter.getValue() != null) {
            java.time.LocalDate toDate = createdDateToFilter.getValue();
            dataProvider.addFilter(caseItem -> 
                caseItem.getCreatedDate() != null && 
                !caseItem.getCreatedDate().toLocalDate().isAfter(toDate));
        }

        // Apply due date from filter
        if (dueDateFromFilter.getValue() != null) {
            java.time.LocalDate fromDate = dueDateFromFilter.getValue();
            dataProvider.addFilter(caseItem -> 
                caseItem.getDueDate() != null && 
                !caseItem.getDueDate().toLocalDate().isBefore(fromDate));
        }

        // Apply due date to filter
        if (dueDateToFilter.getValue() != null) {
            java.time.LocalDate toDate = dueDateToFilter.getValue();
            dataProvider.addFilter(caseItem -> 
                caseItem.getDueDate() != null && 
                !caseItem.getDueDate().toLocalDate().isAfter(toDate));
        }

        // Apply source filter
        if (sourceFilter.getValue() != null) {
            String sourceValue = sourceFilter.getValue();
            dataProvider.addFilter(caseItem -> 
                caseItem.getSource() != null && 
                caseItem.getSource().equals(sourceValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        idFilter.clear();
        caseNumberFilter.clear();
        subjectFilter.clear();
        statusFilter.clear();
        priorityFilter.clear();
        categoryFilter.clear();
        customerIdFilter.clear();
        customerNameFilter.clear();
        assignedToFilter.clear();
        createdDateFromFilter.clear();
        createdDateToFilter.clear();
        dueDateFromFilter.clear();
        dueDateToFilter.clear();
        sourceFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private String[] getCaseStatuses() {
        Case.Status[] statuses = Case.Status.values();
        String[] statusNames = new String[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            statusNames[i] = statuses[i].getName();
        }
        return statusNames;
    }

    private String[] getCasePriorities() {
        Case.Priority[] priorities = Case.Priority.values();
        String[] priorityNames = new String[priorities.length];
        for (int i = 0; i < priorities.length; i++) {
            priorityNames[i] = priorities[i].getName();
        }
        return priorityNames;
    }

    private String[] getCaseCategories() {
        Case.Category[] categories = Case.Category.values();
        String[] categoryNames = new String[categories.length];
        for (int i = 0; i < categories.length; i++) {
            categoryNames[i] = categories[i].getName();
        }
        return categoryNames;
    }

    private String[] getCaseSources() {
        Case.Source[] sources = Case.Source.values();
        String[] sourceNames = new String[sources.length];
        for (int i = 0; i < sources.length; i++) {
            sourceNames[i] = sources[i].getName();
        }
        return sourceNames;
    }
}