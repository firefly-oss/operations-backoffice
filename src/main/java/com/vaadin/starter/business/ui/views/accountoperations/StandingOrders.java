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
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PageTitle(NavigationConstants.STANDING_ORDERS)
@Route(value = "account-operations/standing-orders", layout = MainLayout.class)
public class StandingOrders extends ViewFrame {

    private Grid<StandingOrder> grid;
    private ListDataProvider<StandingOrder> dataProvider;

    // Filter form fields
    private TextField orderIdFilter;
    private TextField accountNumberFilter;
    private TextField beneficiaryFilter;
    private ComboBox<String> statusFilter;
    private DatePicker nextExecutionDateFromFilter;
    private DatePicker nextExecutionDateToFilter;
    private ComboBox<String> frequencyFilter;

    public StandingOrders() {
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

    private Grid<StandingOrder> createGrid() {
        grid = new Grid<>();

        // Initialize with dummy data
        Collection<StandingOrder> orders = getDummyStandingOrders();
        dataProvider = new ListDataProvider<>(orders);
        grid.setDataProvider(dataProvider);

        grid.setId("standingOrders");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(StandingOrder::getOrderId)
                .setHeader("Order ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(StandingOrder::getAccountNumber)
                .setHeader("Account Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(StandingOrder::getBeneficiary)
                .setHeader("Beneficiary")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(StandingOrder::getBeneficiaryAccount)
                .setHeader("Beneficiary Account")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(StandingOrder::getAmount)
                .setHeader("Amount")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(StandingOrder::getFrequency)
                .setHeader("Frequency")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(new LocalDateRenderer<>(StandingOrder::getNextExecutionDate, "MMM dd, YYYY"))
                .setHeader("Next Execution")
                .setSortable(true)
                .setComparator(StandingOrder::getNextExecutionDate)
                .setWidth("150px");
        grid.addColumn(StandingOrder::getStatus)
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
                        order.setStatus("Suspended");
                    } else if (order.getStatus().equals("Suspended")) {
                        order.setStatus("Active");
                    }
                    grid.getDataProvider().refreshItem(order);
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

    // Dummy data for demonstration
    private Collection<StandingOrder> getDummyStandingOrders() {
        List<StandingOrder> orders = new ArrayList<>();

        orders.add(new StandingOrder("SO001", "ACC001", "John Smith", "BENACC001", 500.0, "Monthly", LocalDate.now().plusDays(5), "Active"));
        orders.add(new StandingOrder("SO002", "ACC002", "Utility Company", "BENACC002", 150.0, "Monthly", LocalDate.now().plusDays(10), "Active"));
        orders.add(new StandingOrder("SO003", "ACC003", "Insurance Ltd", "BENACC003", 75.0, "Monthly", LocalDate.now().plusDays(15), "Active"));
        orders.add(new StandingOrder("SO004", "ACC004", "Mortgage Bank", "BENACC004", 1200.0, "Monthly", LocalDate.now().plusDays(20), "Active"));
        orders.add(new StandingOrder("SO005", "ACC005", "Charity Foundation", "BENACC005", 50.0, "Monthly", LocalDate.now().plusDays(25), "Active"));
        orders.add(new StandingOrder("SO006", "ACC006", "Gym Membership", "BENACC006", 45.0, "Monthly", LocalDate.now().plusDays(7), "Suspended"));
        orders.add(new StandingOrder("SO007", "ACC007", "Savings Account", "BENACC007", 200.0, "Monthly", LocalDate.now().plusDays(12), "Active"));
        orders.add(new StandingOrder("SO008", "ACC008", "Investment Fund", "BENACC008", 300.0, "Monthly", LocalDate.now().plusDays(18), "Active"));
        orders.add(new StandingOrder("SO009", "ACC009", "Child Support", "BENACC009", 350.0, "Monthly", LocalDate.now().plusDays(22), "Active"));
        orders.add(new StandingOrder("SO010", "ACC010", "Old Subscription", "BENACC010", 15.0, "Monthly", LocalDate.now().plusDays(30), "Cancelled"));

        return orders;
    }

    // Standing Order class for demonstration
    public static class StandingOrder {
        private String orderId;
        private String accountNumber;
        private String beneficiary;
        private String beneficiaryAccount;
        private Double amount;
        private String frequency;
        private LocalDate nextExecutionDate;
        private String status;

        public StandingOrder(String orderId, String accountNumber, String beneficiary, String beneficiaryAccount,
                            Double amount, String frequency, LocalDate nextExecutionDate, String status) {
            this.orderId = orderId;
            this.accountNumber = accountNumber;
            this.beneficiary = beneficiary;
            this.beneficiaryAccount = beneficiaryAccount;
            this.amount = amount;
            this.frequency = frequency;
            this.nextExecutionDate = nextExecutionDate;
            this.status = status;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getBeneficiary() {
            return beneficiary;
        }

        public void setBeneficiary(String beneficiary) {
            this.beneficiary = beneficiary;
        }

        public String getBeneficiaryAccount() {
            return beneficiaryAccount;
        }

        public void setBeneficiaryAccount(String beneficiaryAccount) {
            this.beneficiaryAccount = beneficiaryAccount;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public LocalDate getNextExecutionDate() {
            return nextExecutionDate;
        }

        public void setNextExecutionDate(LocalDate nextExecutionDate) {
            this.nextExecutionDate = nextExecutionDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
