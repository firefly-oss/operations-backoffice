package com.vaadin.starter.business.ui.views.loanoperations;

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
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.loanoperations.LoanRestructuringDTO;
import com.vaadin.starter.business.backend.service.LoanOperationsService;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PageTitle(NavigationConstants.RESTRUCTURING)
@Route(value = "loan-operations/restructuring", layout = MainLayout.class)
public class Restructuring extends ViewFrame {

    private Grid<LoanRestructuringDTO> grid;
    private ListDataProvider<LoanRestructuringDTO> dataProvider;

    private final LoanOperationsService loanOperationsService;

    @Autowired
    public Restructuring(LoanOperationsService loanOperationsService) {
        this.loanOperationsService = loanOperationsService;
        setViewContent(createContent());
    }

    // Filter form fields
    private TextField requestIdFilter;
    private TextField loanIdFilter;
    private TextField customerNameFilter;
    private ComboBox<String> statusFilter;
    private ComboBox<String> typeFilter;
    private DatePicker requestDateFromFilter;
    private DatePicker requestDateToFilter;
    private TextField assignedToFilter;


    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createFilterForm(), createGrid());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createFilterForm() {
        // Initialize filter fields
        requestIdFilter = new TextField();
        requestIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        requestIdFilter.setClearButtonVisible(true);

        loanIdFilter = new TextField();
        loanIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        loanIdFilter.setClearButtonVisible(true);

        customerNameFilter = new TextField();
        customerNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("New", "Under Review", "Approved", "Rejected", "Implemented");
        statusFilter.setClearButtonVisible(true);

        typeFilter = new ComboBox<>();
        typeFilter.setItems("Term Extension", "Interest Rate Reduction", "Payment Holiday", "Debt Consolidation", "Principal Reduction");
        typeFilter.setClearButtonVisible(true);

        requestDateFromFilter = new DatePicker();
        requestDateFromFilter.setClearButtonVisible(true);

        requestDateToFilter = new DatePicker();
        requestDateToFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(requestIdFilter, "Request ID");
        formLayout.addFormItem(loanIdFilter, "Loan ID");
        formLayout.addFormItem(customerNameFilter, "Customer Name");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(typeFilter, "Type");
        formLayout.addFormItem(requestDateFromFilter, "Request Date From");
        formLayout.addFormItem(requestDateToFilter, "Request Date To");
        formLayout.addFormItem(assignedToFilter, "Assigned To");

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

    private Grid<LoanRestructuringDTO> createGrid() {
        grid = new Grid<>();

        // Initialize with data from service
        Collection<LoanRestructuringDTO> requests = loanOperationsService.getLoanRestructurings();
        dataProvider = new ListDataProvider<>(requests);
        grid.setDataProvider(dataProvider);

        grid.setId("restructuringRequests");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(LoanRestructuringDTO::getRequestId)
                .setHeader("Request ID")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(LoanRestructuringDTO::getLoanId)
                .setHeader("Loan ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(LoanRestructuringDTO::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(LoanRestructuringDTO::getType)
                .setHeader("Type")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(new ComponentRenderer<>(this::createStatusBadge))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(LoanRestructuringDTO::getStatus)
                .setWidth("120px");
        grid.addColumn(new LocalDateRenderer<>(LoanRestructuringDTO::getRequestDate, "MMM dd, YYYY"))
                .setHeader("Request Date")
                .setSortable(true)
                .setComparator(LoanRestructuringDTO::getRequestDate)
                .setWidth("150px");
        grid.addColumn(LoanRestructuringDTO::getAssignedTo)
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new LocalDateRenderer<>(LoanRestructuringDTO::getDecisionDate, "MMM dd, YYYY"))
                .setHeader("Decision Date")
                .setSortable(true)
                .setComparator(LoanRestructuringDTO::getDecisionDate)
                .setWidth("150px");

        // Add action buttons
        grid.addComponentColumn(request -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button viewButton = UIUtils.createSmallButton("View");
            viewButton.addClickListener(e -> {
                // View request details logic would go here
            });

            Button processButton = UIUtils.createSmallButton("Process");
            if (request.getStatus().equals("New") || request.getStatus().equals("Under Review")) {
                processButton.addClickListener(e -> {
                    // Process request logic would go here
                });
                actions.add(viewButton, processButton);
            } else {
                actions.add(viewButton);
            }

            return actions;
        }).setHeader("Actions").setWidth("150px");

        return grid;
    }

    private Component createStatusBadge(LoanRestructuringDTO request) {
        String status = request.getStatus();
        BadgeColor color;

        switch (status) {
            case "Approved":
                color = BadgeColor.SUCCESS;
                break;
            case "Rejected":
                color = BadgeColor.ERROR;
                break;
            case "Implemented":
                color = BadgeColor.SUCCESS_PRIMARY;
                break;
            case "Under Review":
                color = BadgeColor.CONTRAST;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply request ID filter
        if (requestIdFilter.getValue() != null && !requestIdFilter.getValue().isEmpty()) {
            String requestIdValue = requestIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(request -> 
                request.getRequestId() != null && 
                request.getRequestId().toLowerCase().contains(requestIdValue));
        }

        // Apply loan ID filter
        if (loanIdFilter.getValue() != null && !loanIdFilter.getValue().isEmpty()) {
            String loanIdValue = loanIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(request -> 
                request.getLoanId() != null && 
                request.getLoanId().toLowerCase().contains(loanIdValue));
        }

        // Apply customer name filter
        if (customerNameFilter.getValue() != null && !customerNameFilter.getValue().isEmpty()) {
            String customerNameValue = customerNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(request -> 
                request.getCustomerName() != null && 
                request.getCustomerName().toLowerCase().contains(customerNameValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getStatus() != null && 
                request.getStatus().equals(statusValue));
        }

        // Apply type filter
        if (typeFilter.getValue() != null) {
            String typeValue = typeFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getType() != null && 
                request.getType().equals(typeValue));
        }

        // Apply request date from filter
        if (requestDateFromFilter.getValue() != null) {
            LocalDate fromDate = requestDateFromFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getRequestDate() != null && 
                !request.getRequestDate().isBefore(fromDate));
        }

        // Apply request date to filter
        if (requestDateToFilter.getValue() != null) {
            LocalDate toDate = requestDateToFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getRequestDate() != null && 
                !request.getRequestDate().isAfter(toDate));
        }

        // Apply assigned to filter
        if (assignedToFilter.getValue() != null && !assignedToFilter.getValue().isEmpty()) {
            String assignedToValue = assignedToFilter.getValue().toLowerCase();
            dataProvider.addFilter(request -> 
                request.getAssignedTo() != null && 
                request.getAssignedTo().toLowerCase().contains(assignedToValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        requestIdFilter.clear();
        loanIdFilter.clear();
        customerNameFilter.clear();
        statusFilter.clear();
        typeFilter.clear();
        requestDateFromFilter.clear();
        requestDateToFilter.clear();
        assignedToFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

}
