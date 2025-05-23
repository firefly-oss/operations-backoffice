package com.vaadin.starter.business.ui.views.accountoperations;

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
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.accountoperations.StandingOrderDTO;
import com.vaadin.starter.business.backend.service.StandingOrderService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

@PageTitle(NavigationConstants.STANDING_ORDERS)
@Route(value = "account-operations/standing-orders", layout = MainLayout.class)
public class StandingOrders extends ViewFrame {

    private Grid<StandingOrderDTO> grid;
    private ListDataProvider<StandingOrderDTO> dataProvider;

    // Filter form fields
    private TextField orderIdFilter;
    private TextField accountNumberFilter;
    private TextField beneficiaryFilter;
    private ComboBox<String> statusFilter;
    private DatePicker nextExecutionDateFromFilter;
    private DatePicker nextExecutionDateToFilter;
    private ComboBox<String> frequencyFilter;

    private final StandingOrderService standingOrderService;

    @Autowired
    public StandingOrders(StandingOrderService standingOrderService) {
        this.standingOrderService = standingOrderService;
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

    private Component createFilterForm() {
        // Initialize filter fields
        orderIdFilter = new TextField();
        orderIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        orderIdFilter.setClearButtonVisible(true);

        accountNumberFilter = new TextField();
        accountNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        accountNumberFilter.setClearButtonVisible(true);

        beneficiaryFilter = new TextField();
        beneficiaryFilter.setValueChangeMode(ValueChangeMode.EAGER);
        beneficiaryFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("Active", "Suspended", "Cancelled");
        statusFilter.setClearButtonVisible(true);

        nextExecutionDateFromFilter = new DatePicker();
        nextExecutionDateFromFilter.setClearButtonVisible(true);

        nextExecutionDateToFilter = new DatePicker();
        nextExecutionDateToFilter.setClearButtonVisible(true);

        frequencyFilter = new ComboBox<>();
        frequencyFilter.setItems("Daily", "Weekly", "Monthly", "Quarterly", "Annually");
        frequencyFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(orderIdFilter, "Order ID");
        formLayout.addFormItem(accountNumberFilter, "Account Number");
        formLayout.addFormItem(beneficiaryFilter, "Beneficiary");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(nextExecutionDateFromFilter, "Next Execution From");
        formLayout.addFormItem(nextExecutionDateToFilter, "Next Execution To");
        formLayout.addFormItem(frequencyFilter, "Frequency");

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

    private Grid<StandingOrderDTO> createGrid() {
        grid = new Grid<>();

        // Initialize with data from service
        Collection<StandingOrderDTO> orders = standingOrderService.getStandingOrders();
        dataProvider = new ListDataProvider<>(orders);
        grid.setDataProvider(dataProvider);

        grid.setId("standingOrders");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(StandingOrderDTO::getOrderId)
                .setHeader("Order ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(StandingOrderDTO::getAccountNumber)
                .setHeader("Account Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(StandingOrderDTO::getBeneficiary)
                .setHeader("Beneficiary")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(StandingOrderDTO::getBeneficiaryAccount)
                .setHeader("Beneficiary Account")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(StandingOrderDTO::getAmount)
                .setHeader("Amount")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(StandingOrderDTO::getFrequency)
                .setHeader("Frequency")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(new LocalDateRenderer<>(StandingOrderDTO::getNextExecutionDate, "MMM dd, YYYY"))
                .setHeader("Next Execution")
                .setSortable(true)
                .setComparator(StandingOrderDTO::getNextExecutionDate)
                .setWidth("150px");
        grid.addColumn(StandingOrderDTO::getStatus)
                .setHeader("Status")
                .setSortable(true)
                .setWidth("120px");

        // Add action buttons
        grid.addComponentColumn(order -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button editButton = UIUtils.createSmallButton("Edit");
            editButton.addClickListener(e -> {
                // Edit logic would go here
            });

            Button suspendButton = UIUtils.createSmallButton(
                    order.getStatus().equals("Active") ? "Suspend" : 
                    order.getStatus().equals("Suspended") ? "Activate" : "");

            if (!order.getStatus().equals("Cancelled")) {
                suspendButton.addClickListener(e -> {
                    if (order.getStatus().equals("Active")) {
                        standingOrderService.suspendStandingOrder(order.getOrderId());
                        refreshGrid();
                    } else if (order.getStatus().equals("Suspended")) {
                        standingOrderService.activateStandingOrder(order.getOrderId());
                        refreshGrid();
                    }
                });
                actions.add(editButton, suspendButton);
            } else {
                actions.add(editButton);
            }

            return actions;
        }).setHeader("Actions").setWidth("200px");

        return grid;
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply order ID filter
        if (orderIdFilter.getValue() != null && !orderIdFilter.getValue().isEmpty()) {
            String orderIdValue = orderIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(order -> 
                order.getOrderId() != null && 
                order.getOrderId().toLowerCase().contains(orderIdValue));
        }

        // Apply account number filter
        if (accountNumberFilter.getValue() != null && !accountNumberFilter.getValue().isEmpty()) {
            String accountNumberValue = accountNumberFilter.getValue().toLowerCase();
            dataProvider.addFilter(order -> 
                order.getAccountNumber() != null && 
                order.getAccountNumber().toLowerCase().contains(accountNumberValue));
        }

        // Apply beneficiary filter
        if (beneficiaryFilter.getValue() != null && !beneficiaryFilter.getValue().isEmpty()) {
            String beneficiaryValue = beneficiaryFilter.getValue().toLowerCase();
            dataProvider.addFilter(order -> 
                order.getBeneficiary() != null && 
                order.getBeneficiary().toLowerCase().contains(beneficiaryValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(order -> 
                order.getStatus() != null && 
                order.getStatus().equals(statusValue));
        }

        // Apply next execution date from filter
        if (nextExecutionDateFromFilter.getValue() != null) {
            LocalDate fromDate = nextExecutionDateFromFilter.getValue();
            dataProvider.addFilter(order -> 
                order.getNextExecutionDate() != null && 
                !order.getNextExecutionDate().isBefore(fromDate));
        }

        // Apply next execution date to filter
        if (nextExecutionDateToFilter.getValue() != null) {
            LocalDate toDate = nextExecutionDateToFilter.getValue();
            dataProvider.addFilter(order -> 
                order.getNextExecutionDate() != null && 
                !order.getNextExecutionDate().isAfter(toDate));
        }

        // Apply frequency filter
        if (frequencyFilter.getValue() != null) {
            String frequencyValue = frequencyFilter.getValue();
            dataProvider.addFilter(order -> 
                order.getFrequency() != null && 
                order.getFrequency().equals(frequencyValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        orderIdFilter.clear();
        accountNumberFilter.clear();
        beneficiaryFilter.clear();
        statusFilter.clear();
        nextExecutionDateFromFilter.clear();
        nextExecutionDateToFilter.clear();
        frequencyFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private void refreshGrid() {
        // Refresh data from service
        Collection<StandingOrderDTO> orders = standingOrderService.getStandingOrders();
        dataProvider = new ListDataProvider<>(orders);
        grid.setDataProvider(dataProvider);
        applyFilters(); // Re-apply any active filters
    }

}
