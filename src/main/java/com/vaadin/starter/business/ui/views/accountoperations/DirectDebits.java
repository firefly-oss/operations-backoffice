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

@PageTitle(NavigationConstants.DIRECT_DEBITS)
@Route(value = "account-operations/direct-debits", layout = MainLayout.class)
public class DirectDebits extends ViewFrame {

    private Grid<DirectDebit> grid;
    private ListDataProvider<DirectDebit> dataProvider;

    // Filter form fields
    private TextField mandateIdFilter;
    private TextField accountNumberFilter;
    private TextField creditorFilter;
    private ComboBox<String> statusFilter;
    private DatePicker nextCollectionDateFromFilter;
    private DatePicker nextCollectionDateToFilter;
    private ComboBox<String> frequencyFilter;

    public DirectDebits() {
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
        mandateIdFilter = new TextField();
        mandateIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        mandateIdFilter.setClearButtonVisible(true);

        accountNumberFilter = new TextField();
        accountNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        accountNumberFilter.setClearButtonVisible(true);

        creditorFilter = new TextField();
        creditorFilter.setValueChangeMode(ValueChangeMode.EAGER);
        creditorFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("Active", "Suspended", "Cancelled");
        statusFilter.setClearButtonVisible(true);

        nextCollectionDateFromFilter = new DatePicker();
        nextCollectionDateFromFilter.setClearButtonVisible(true);

        nextCollectionDateToFilter = new DatePicker();
        nextCollectionDateToFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(mandateIdFilter, "Mandate ID");
        formLayout.addFormItem(accountNumberFilter, "Account Number");
        formLayout.addFormItem(creditorFilter, "Creditor");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(nextCollectionDateFromFilter, "Next Collection From");
        formLayout.addFormItem(nextCollectionDateToFilter, "Next Collection To");
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

    private Grid<DirectDebit> createGrid() {
        grid = new Grid<>();
        
        // Initialize with dummy data
        Collection<DirectDebit> debits = getDummyDirectDebits();
        dataProvider = new ListDataProvider<>(debits);
        grid.setDataProvider(dataProvider);

        grid.setId("directDebits");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(DirectDebit::getMandateId)
                .setHeader("Mandate ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(DirectDebit::getAccountNumber)
                .setHeader("Account Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(DirectDebit::getCreditor)
                .setHeader("Creditor")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(DirectDebit::getCreditorId)
                .setHeader("Creditor ID")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(DirectDebit::getAmount)
                .setHeader("Amount")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(DirectDebit::getFrequency)
                .setHeader("Frequency")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(new LocalDateRenderer<>(DirectDebit::getNextCollectionDate, "MMM dd, YYYY"))
                .setHeader("Next Collection")
                .setSortable(true)
                .setComparator(DirectDebit::getNextCollectionDate)
                .setWidth("150px");
        grid.addColumn(DirectDebit::getStatus)
                .setHeader("Status")
                .setSortable(true)
                .setWidth("120px");

        // Add action buttons
        grid.addComponentColumn(debit -> {
            HorizontalLayout actions = new HorizontalLayout();
            
            Button editButton = UIUtils.createSmallButton("Edit");
            editButton.addClickListener(e -> {
                // Edit logic would go here
            });
            
            Button suspendButton = UIUtils.createSmallButton(
                    debit.getStatus().equals("Active") ? "Suspend" : 
                    debit.getStatus().equals("Suspended") ? "Activate" : "");
            
            if (!debit.getStatus().equals("Cancelled")) {
                suspendButton.addClickListener(e -> {
                    if (debit.getStatus().equals("Active")) {
                        debit.setStatus("Suspended");
                    } else if (debit.getStatus().equals("Suspended")) {
                        debit.setStatus("Active");
                    }
                    grid.getDataProvider().refreshItem(debit);
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

        // Apply mandate ID filter
        if (mandateIdFilter.getValue() != null && !mandateIdFilter.getValue().isEmpty()) {
            String mandateIdValue = mandateIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(debit -> 
                debit.getMandateId() != null && 
                debit.getMandateId().toLowerCase().contains(mandateIdValue));
        }

        // Apply account number filter
        if (accountNumberFilter.getValue() != null && !accountNumberFilter.getValue().isEmpty()) {
            String accountNumberValue = accountNumberFilter.getValue().toLowerCase();
            dataProvider.addFilter(debit -> 
                debit.getAccountNumber() != null && 
                debit.getAccountNumber().toLowerCase().contains(accountNumberValue));
        }

        // Apply creditor filter
        if (creditorFilter.getValue() != null && !creditorFilter.getValue().isEmpty()) {
            String creditorValue = creditorFilter.getValue().toLowerCase();
            dataProvider.addFilter(debit -> 
                debit.getCreditor() != null && 
                debit.getCreditor().toLowerCase().contains(creditorValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(debit -> 
                debit.getStatus() != null && 
                debit.getStatus().equals(statusValue));
        }

        // Apply next collection date from filter
        if (nextCollectionDateFromFilter.getValue() != null) {
            LocalDate fromDate = nextCollectionDateFromFilter.getValue();
            dataProvider.addFilter(debit -> 
                debit.getNextCollectionDate() != null && 
                !debit.getNextCollectionDate().isBefore(fromDate));
        }

        // Apply next collection date to filter
        if (nextCollectionDateToFilter.getValue() != null) {
            LocalDate toDate = nextCollectionDateToFilter.getValue();
            dataProvider.addFilter(debit -> 
                debit.getNextCollectionDate() != null && 
                !debit.getNextCollectionDate().isAfter(toDate));
        }

        // Apply frequency filter
        if (frequencyFilter.getValue() != null) {
            String frequencyValue = frequencyFilter.getValue();
            dataProvider.addFilter(debit -> 
                debit.getFrequency() != null && 
                debit.getFrequency().equals(frequencyValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        mandateIdFilter.clear();
        accountNumberFilter.clear();
        creditorFilter.clear();
        statusFilter.clear();
        nextCollectionDateFromFilter.clear();
        nextCollectionDateToFilter.clear();
        frequencyFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    // Dummy data for demonstration
    private Collection<DirectDebit> getDummyDirectDebits() {
        List<DirectDebit> debits = new ArrayList<>();
        
        debits.add(new DirectDebit("DD001", "ACC001", "Electric Company", "CRED001", 85.0, "Monthly", LocalDate.now().plusDays(5), "Active"));
        debits.add(new DirectDebit("DD002", "ACC002", "Water Utility", "CRED002", 45.0, "Monthly", LocalDate.now().plusDays(10), "Active"));
        debits.add(new DirectDebit("DD003", "ACC003", "Internet Provider", "CRED003", 60.0, "Monthly", LocalDate.now().plusDays(15), "Active"));
        debits.add(new DirectDebit("DD004", "ACC004", "Mobile Phone", "CRED004", 35.0, "Monthly", LocalDate.now().plusDays(20), "Active"));
        debits.add(new DirectDebit("DD005", "ACC005", "Gym Membership", "CRED005", 50.0, "Monthly", LocalDate.now().plusDays(25), "Active"));
        debits.add(new DirectDebit("DD006", "ACC006", "Magazine Subscription", "CRED006", 15.0, "Monthly", LocalDate.now().plusDays(7), "Suspended"));
        debits.add(new DirectDebit("DD007", "ACC007", "Charity Donation", "CRED007", 20.0, "Monthly", LocalDate.now().plusDays(12), "Active"));
        debits.add(new DirectDebit("DD008", "ACC008", "Insurance Premium", "CRED008", 75.0, "Monthly", LocalDate.now().plusDays(18), "Active"));
        debits.add(new DirectDebit("DD009", "ACC009", "TV License", "CRED009", 13.0, "Monthly", LocalDate.now().plusDays(22), "Active"));
        debits.add(new DirectDebit("DD010", "ACC010", "Old Service", "CRED010", 25.0, "Monthly", LocalDate.now().plusDays(30), "Cancelled"));
        
        return debits;
    }

    // Direct Debit class for demonstration
    public static class DirectDebit {
        private String mandateId;
        private String accountNumber;
        private String creditor;
        private String creditorId;
        private Double amount;
        private String frequency;
        private LocalDate nextCollectionDate;
        private String status;

        public DirectDebit(String mandateId, String accountNumber, String creditor, String creditorId,
                          Double amount, String frequency, LocalDate nextCollectionDate, String status) {
            this.mandateId = mandateId;
            this.accountNumber = accountNumber;
            this.creditor = creditor;
            this.creditorId = creditorId;
            this.amount = amount;
            this.frequency = frequency;
            this.nextCollectionDate = nextCollectionDate;
            this.status = status;
        }

        public String getMandateId() {
            return mandateId;
        }

        public void setMandateId(String mandateId) {
            this.mandateId = mandateId;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getCreditor() {
            return creditor;
        }

        public void setCreditor(String creditor) {
            this.creditor = creditor;
        }

        public String getCreditorId() {
            return creditorId;
        }

        public void setCreditorId(String creditorId) {
            this.creditorId = creditorId;
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

        public LocalDate getNextCollectionDate() {
            return nextCollectionDate;
        }

        public void setNextCollectionDate(LocalDate nextCollectionDate) {
            this.nextCollectionDate = nextCollectionDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}