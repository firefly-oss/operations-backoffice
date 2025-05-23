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
import com.vaadin.starter.business.backend.dto.loanoperations.LoanCollectionDTO;
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

@PageTitle(NavigationConstants.COLLECTIONS)
@Route(value = "loan-operations/collections", layout = MainLayout.class)
public class Collections extends ViewFrame {

    private Grid<LoanCollectionDTO> grid;
    private ListDataProvider<LoanCollectionDTO> dataProvider;

    private final LoanOperationsService loanOperationsService;

    @Autowired
    public Collections(LoanOperationsService loanOperationsService) {
        this.loanOperationsService = loanOperationsService;
        setViewContent(createContent());
    }

    // Filter form fields
    private TextField collectionIdFilter;
    private TextField loanIdFilter;
    private TextField customerNameFilter;
    private ComboBox<String> statusFilter;
    private NumberField minAmountFilter;
    private NumberField maxAmountFilter;
    private DatePicker dueDateFromFilter;
    private DatePicker dueDateToFilter;
    private ComboBox<String> collectionMethodFilter;


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
        collectionIdFilter = new TextField();
        collectionIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        collectionIdFilter.setClearButtonVisible(true);

        loanIdFilter = new TextField();
        loanIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        loanIdFilter.setClearButtonVisible(true);

        customerNameFilter = new TextField();
        customerNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("Pending", "Paid", "Overdue", "In Collection", "Written Off");
        statusFilter.setClearButtonVisible(true);

        minAmountFilter = new NumberField();
        minAmountFilter.setClearButtonVisible(true);

        maxAmountFilter = new NumberField();
        maxAmountFilter.setClearButtonVisible(true);

        dueDateFromFilter = new DatePicker();
        dueDateFromFilter.setClearButtonVisible(true);

        dueDateToFilter = new DatePicker();
        dueDateToFilter.setClearButtonVisible(true);

        collectionMethodFilter = new ComboBox<>();
        collectionMethodFilter.setItems("Auto Debit", "Bank Transfer", "Check", "Cash", "Digital Wallet");
        collectionMethodFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(collectionIdFilter, "Collection ID");
        formLayout.addFormItem(loanIdFilter, "Loan ID");
        formLayout.addFormItem(customerNameFilter, "Customer Name");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(minAmountFilter, "Min Amount ($)");
        formLayout.addFormItem(maxAmountFilter, "Max Amount ($)");
        formLayout.addFormItem(dueDateFromFilter, "Due Date From");
        formLayout.addFormItem(dueDateToFilter, "Due Date To");
        formLayout.addFormItem(collectionMethodFilter, "Payment Method");

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

    private Grid<LoanCollectionDTO> createGrid() {
        grid = new Grid<>();

        // Initialize with data from service
        Collection<LoanCollectionDTO> collections = loanOperationsService.getLoanCollections();
        dataProvider = new ListDataProvider<>(collections);
        grid.setDataProvider(dataProvider);

        grid.setId("loanCollections");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(LoanCollectionDTO::getCollectionId)
                .setHeader("Collection ID")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(LoanCollectionDTO::getLoanId)
                .setHeader("Loan ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(LoanCollectionDTO::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(new ComponentRenderer<>(this::createStatusBadge))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(LoanCollectionDTO::getStatus)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(this::createAmountComponent))
                .setHeader("Amount ($)")
                .setSortable(true)
                .setComparator(LoanCollectionDTO::getAmount)
                .setWidth("120px");
        grid.addColumn(LoanCollectionDTO::getPaymentMethod)
                .setHeader("Payment Method")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new LocalDateRenderer<>(LoanCollectionDTO::getDueDate, "MMM dd, YYYY"))
                .setHeader("Due Date")
                .setSortable(true)
                .setComparator(LoanCollectionDTO::getDueDate)
                .setWidth("150px");
        grid.addColumn(new LocalDateRenderer<>(LoanCollectionDTO::getPaymentDate, "MMM dd, YYYY"))
                .setHeader("Payment Date")
                .setSortable(true)
                .setComparator(LoanCollectionDTO::getPaymentDate)
                .setWidth("150px");

        // Add action buttons
        grid.addComponentColumn(collection -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button viewButton = UIUtils.createSmallButton("View");
            viewButton.addClickListener(e -> {
                // View collection details logic would go here
            });

            Button processButton = UIUtils.createSmallButton("Record Payment");
            if (collection.getStatus().equals("Pending") || collection.getStatus().equals("Overdue") || collection.getStatus().equals("In Collection")) {
                processButton.addClickListener(e -> {
                    // Record payment logic would go here
                });
                actions.add(viewButton, processButton);
            } else {
                actions.add(viewButton);
            }

            return actions;
        }).setHeader("Actions").setWidth("180px");

        return grid;
    }

    private Component createStatusBadge(LoanCollectionDTO collection) {
        String status = collection.getStatus();
        BadgeColor color;

        switch (status) {
            case "Paid":
                color = BadgeColor.SUCCESS;
                break;
            case "Overdue":
                color = BadgeColor.ERROR;
                break;
            case "Written Off":
                color = BadgeColor.ERROR;
                break;
            case "In Collection":
                color = BadgeColor.CONTRAST;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Component createAmountComponent(LoanCollectionDTO collection) {
        Double amount = collection.getAmount();
        Span amountSpan = new Span(UIUtils.formatAmount(amount));
        return amountSpan;
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply collection ID filter
        if (collectionIdFilter.getValue() != null && !collectionIdFilter.getValue().isEmpty()) {
            String collectionIdValue = collectionIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(collection -> 
                collection.getCollectionId() != null && 
                collection.getCollectionId().toLowerCase().contains(collectionIdValue));
        }

        // Apply loan ID filter
        if (loanIdFilter.getValue() != null && !loanIdFilter.getValue().isEmpty()) {
            String loanIdValue = loanIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(collection -> 
                collection.getLoanId() != null && 
                collection.getLoanId().toLowerCase().contains(loanIdValue));
        }

        // Apply customer name filter
        if (customerNameFilter.getValue() != null && !customerNameFilter.getValue().isEmpty()) {
            String customerNameValue = customerNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(collection -> 
                collection.getCustomerName() != null && 
                collection.getCustomerName().toLowerCase().contains(customerNameValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(collection -> 
                collection.getStatus() != null && 
                collection.getStatus().equals(statusValue));
        }

        // Apply min amount filter
        if (minAmountFilter.getValue() != null) {
            Double minValue = minAmountFilter.getValue();
            dataProvider.addFilter(collection -> 
                collection.getAmount() != null && 
                collection.getAmount() >= minValue);
        }

        // Apply max amount filter
        if (maxAmountFilter.getValue() != null) {
            Double maxValue = maxAmountFilter.getValue();
            dataProvider.addFilter(collection -> 
                collection.getAmount() != null && 
                collection.getAmount() <= maxValue);
        }

        // Apply due date from filter
        if (dueDateFromFilter.getValue() != null) {
            LocalDate fromDate = dueDateFromFilter.getValue();
            dataProvider.addFilter(collection -> 
                collection.getDueDate() != null && 
                !collection.getDueDate().isBefore(fromDate));
        }

        // Apply due date to filter
        if (dueDateToFilter.getValue() != null) {
            LocalDate toDate = dueDateToFilter.getValue();
            dataProvider.addFilter(collection -> 
                collection.getDueDate() != null && 
                !collection.getDueDate().isAfter(toDate));
        }

        // Apply payment method filter
        if (collectionMethodFilter.getValue() != null) {
            String methodValue = collectionMethodFilter.getValue();
            dataProvider.addFilter(collection -> 
                collection.getPaymentMethod() != null && 
                collection.getPaymentMethod().equals(methodValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        collectionIdFilter.clear();
        loanIdFilter.clear();
        customerNameFilter.clear();
        statusFilter.clear();
        minAmountFilter.clear();
        maxAmountFilter.clear();
        dueDateFromFilter.clear();
        dueDateToFilter.clear();
        collectionMethodFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

}
