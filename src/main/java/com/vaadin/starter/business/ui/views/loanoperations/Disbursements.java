package com.vaadin.starter.business.ui.views.loanoperations;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.loanoperations.LoanDisbursementDTO;
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

@PageTitle(NavigationConstants.DISBURSEMENTS)
@Route(value = "loan-operations/disbursements", layout = MainLayout.class)
public class Disbursements extends ViewFrame {

    private Grid<LoanDisbursementDTO> grid;
    private ListDataProvider<LoanDisbursementDTO> dataProvider;

    private final LoanOperationsService loanOperationsService;

    @Autowired
    public Disbursements(LoanOperationsService loanOperationsService) {
        this.loanOperationsService = loanOperationsService;
        setViewContent(createContent());
    }

    // Filter form fields
    private TextField disbursementIdFilter;
    private TextField loanIdFilter;
    private TextField customerNameFilter;
    private ComboBox<String> statusFilter;
    private NumberField minAmountFilter;
    private NumberField maxAmountFilter;
    private DatePicker scheduledDateFromFilter;
    private DatePicker scheduledDateToFilter;
    private ComboBox<String> disbursementMethodFilter;


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
        disbursementIdFilter = new TextField();
        disbursementIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        disbursementIdFilter.setClearButtonVisible(true);

        loanIdFilter = new TextField();
        loanIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        loanIdFilter.setClearButtonVisible(true);

        customerNameFilter = new TextField();
        customerNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("Scheduled", "Processed", "Failed", "Cancelled");
        statusFilter.setClearButtonVisible(true);

        minAmountFilter = new NumberField();
        minAmountFilter.setClearButtonVisible(true);

        maxAmountFilter = new NumberField();
        maxAmountFilter.setClearButtonVisible(true);

        scheduledDateFromFilter = new DatePicker();
        scheduledDateFromFilter.setClearButtonVisible(true);

        scheduledDateToFilter = new DatePicker();
        scheduledDateToFilter.setClearButtonVisible(true);

        disbursementMethodFilter = new ComboBox<>();
        disbursementMethodFilter.setItems("Bank Transfer", "Check", "Cash", "Digital Wallet");
        disbursementMethodFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(disbursementIdFilter, "Disbursement ID");
        formLayout.addFormItem(loanIdFilter, "Loan ID");
        formLayout.addFormItem(customerNameFilter, "Customer Name");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(minAmountFilter, "Min Amount ($)");
        formLayout.addFormItem(maxAmountFilter, "Max Amount ($)");
        formLayout.addFormItem(scheduledDateFromFilter, "Scheduled From");
        formLayout.addFormItem(scheduledDateToFilter, "Scheduled To");
        formLayout.addFormItem(disbursementMethodFilter, "Method");

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

    private Grid<LoanDisbursementDTO> createGrid() {
        grid = new Grid<>();

        // Initialize with data from service
        Collection<LoanDisbursementDTO> disbursements = loanOperationsService.getLoanDisbursements();
        dataProvider = new ListDataProvider<>(disbursements);
        grid.setDataProvider(dataProvider);

        grid.setId("disbursements");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(LoanDisbursementDTO::getDisbursementId)
                .setHeader("Disbursement ID")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(LoanDisbursementDTO::getLoanId)
                .setHeader("Loan ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(LoanDisbursementDTO::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(new ComponentRenderer<>(this::createStatusBadge))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(LoanDisbursementDTO::getStatus)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(this::createAmountComponent))
                .setHeader("Amount ($)")
                .setSortable(true)
                .setComparator(LoanDisbursementDTO::getAmount)
                .setWidth("120px");
        grid.addColumn(LoanDisbursementDTO::getDisbursementMethod)
                .setHeader("Method")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new LocalDateRenderer<>(LoanDisbursementDTO::getScheduledDate, "MMM dd, YYYY"))
                .setHeader("Scheduled Date")
                .setSortable(true)
                .setComparator(LoanDisbursementDTO::getScheduledDate)
                .setWidth("150px");
        grid.addColumn(new LocalDateRenderer<>(LoanDisbursementDTO::getProcessedDate, "MMM dd, YYYY"))
                .setHeader("Processed Date")
                .setSortable(true)
                .setComparator(LoanDisbursementDTO::getProcessedDate)
                .setWidth("150px");

        // Add action buttons
        grid.addComponentColumn(disbursement -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button viewButton = UIUtils.createSmallButton("View");
            viewButton.addClickListener(e -> {
                // View disbursement details logic would go here
            });

            Button processButton = UIUtils.createSmallButton("Process");
            if (disbursement.getStatus().equals("Scheduled")) {
                processButton.addClickListener(e -> {
                    // Process disbursement logic would go here
                });
                actions.add(viewButton, processButton);
            } else {
                actions.add(viewButton);
            }

            return actions;
        }).setHeader("Actions").setWidth("150px");

        return grid;
    }

    private Component createStatusBadge(LoanDisbursementDTO disbursement) {
        String status = disbursement.getStatus();
        BadgeColor color;

        switch (status) {
            case "Processed":
                color = BadgeColor.SUCCESS;
                break;
            case "Failed":
                color = BadgeColor.ERROR;
                break;
            case "Cancelled":
                color = BadgeColor.ERROR;
                break;
            case "Scheduled":
                color = BadgeColor.CONTRAST;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Component createAmountComponent(LoanDisbursementDTO disbursement) {
        Double amount = disbursement.getAmount();
        Span amountSpan = new Span(UIUtils.formatAmount(amount));
        return amountSpan;
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply disbursement ID filter
        if (disbursementIdFilter.getValue() != null && !disbursementIdFilter.getValue().isEmpty()) {
            String disbursementIdValue = disbursementIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(disbursement -> 
                disbursement.getDisbursementId() != null && 
                disbursement.getDisbursementId().toLowerCase().contains(disbursementIdValue));
        }

        // Apply loan ID filter
        if (loanIdFilter.getValue() != null && !loanIdFilter.getValue().isEmpty()) {
            String loanIdValue = loanIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(disbursement -> 
                disbursement.getLoanId() != null && 
                disbursement.getLoanId().toLowerCase().contains(loanIdValue));
        }

        // Apply customer name filter
        if (customerNameFilter.getValue() != null && !customerNameFilter.getValue().isEmpty()) {
            String customerNameValue = customerNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(disbursement -> 
                disbursement.getCustomerName() != null && 
                disbursement.getCustomerName().toLowerCase().contains(customerNameValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(disbursement -> 
                disbursement.getStatus() != null && 
                disbursement.getStatus().equals(statusValue));
        }

        // Apply min amount filter
        if (minAmountFilter.getValue() != null) {
            Double minValue = minAmountFilter.getValue();
            dataProvider.addFilter(disbursement -> 
                disbursement.getAmount() != null && 
                disbursement.getAmount() >= minValue);
        }

        // Apply max amount filter
        if (maxAmountFilter.getValue() != null) {
            Double maxValue = maxAmountFilter.getValue();
            dataProvider.addFilter(disbursement -> 
                disbursement.getAmount() != null && 
                disbursement.getAmount() <= maxValue);
        }

        // Apply scheduled date from filter
        if (scheduledDateFromFilter.getValue() != null) {
            LocalDate fromDate = scheduledDateFromFilter.getValue();
            dataProvider.addFilter(disbursement -> 
                disbursement.getScheduledDate() != null && 
                !disbursement.getScheduledDate().isBefore(fromDate));
        }

        // Apply scheduled date to filter
        if (scheduledDateToFilter.getValue() != null) {
            LocalDate toDate = scheduledDateToFilter.getValue();
            dataProvider.addFilter(disbursement -> 
                disbursement.getScheduledDate() != null && 
                !disbursement.getScheduledDate().isAfter(toDate));
        }

        // Apply disbursement method filter
        if (disbursementMethodFilter.getValue() != null) {
            String methodValue = disbursementMethodFilter.getValue();
            dataProvider.addFilter(disbursement -> 
                disbursement.getDisbursementMethod() != null && 
                disbursement.getDisbursementMethod().equals(methodValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        disbursementIdFilter.clear();
        loanIdFilter.clear();
        customerNameFilter.clear();
        statusFilter.clear();
        minAmountFilter.clear();
        maxAmountFilter.clear();
        scheduledDateFromFilter.clear();
        scheduledDateToFilter.clear();
        disbursementMethodFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

}
