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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PageTitle(NavigationConstants.DISBURSEMENTS)
@Route(value = "loan-operations/disbursements", layout = MainLayout.class)
public class Disbursements extends ViewFrame {

    private Grid<Disbursement> grid;
    private ListDataProvider<Disbursement> dataProvider;

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

    public Disbursements() {
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

    private Grid<Disbursement> createGrid() {
        grid = new Grid<>();

        // Initialize with dummy data
        Collection<Disbursement> disbursements = getDummyDisbursements();
        dataProvider = new ListDataProvider<>(disbursements);
        grid.setDataProvider(dataProvider);

        grid.setId("disbursements");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(Disbursement::getDisbursementId)
                .setHeader("Disbursement ID")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Disbursement::getLoanId)
                .setHeader("Loan ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Disbursement::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(new ComponentRenderer<>(this::createStatusBadge))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Disbursement::getStatus)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(this::createAmountComponent))
                .setHeader("Amount ($)")
                .setSortable(true)
                .setComparator(Disbursement::getAmount)
                .setWidth("120px");
        grid.addColumn(Disbursement::getDisbursementMethod)
                .setHeader("Method")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new LocalDateRenderer<>(Disbursement::getScheduledDate, "MMM dd, YYYY"))
                .setHeader("Scheduled Date")
                .setSortable(true)
                .setComparator(Disbursement::getScheduledDate)
                .setWidth("150px");
        grid.addColumn(new LocalDateRenderer<>(Disbursement::getProcessedDate, "MMM dd, YYYY"))
                .setHeader("Processed Date")
                .setSortable(true)
                .setComparator(Disbursement::getProcessedDate)
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

    private Component createStatusBadge(Disbursement disbursement) {
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

    private Component createAmountComponent(Disbursement disbursement) {
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

    // Dummy data for demonstration
    private Collection<Disbursement> getDummyDisbursements() {
        List<Disbursement> disbursements = new ArrayList<>();
        
        disbursements.add(new Disbursement("D001", "LA002", "Jane Doe", "Scheduled", 350000.0, "Bank Transfer", LocalDate.now().plusDays(2), null));
        disbursements.add(new Disbursement("D002", "LA008", "Jennifer Garcia", "Processed", 35000.0, "Bank Transfer", LocalDate.now().minusDays(5), LocalDate.now().minusDays(3)));
        disbursements.add(new Disbursement("D003", "LA003", "Robert Johnson", "Scheduled", 45000.0, "Check", LocalDate.now().plusDays(1), null));
        disbursements.add(new Disbursement("D004", "LA005", "Michael Brown", "Cancelled", 150000.0, "Bank Transfer", LocalDate.now().minusDays(10), null));
        disbursements.add(new Disbursement("D005", "LA001", "John Smith", "Scheduled", 25000.0, "Digital Wallet", LocalDate.now().plusDays(3), null));
        disbursements.add(new Disbursement("D006", "LA007", "David Miller", "Processed", 275000.0, "Bank Transfer", LocalDate.now().minusDays(7), LocalDate.now().minusDays(6)));
        disbursements.add(new Disbursement("D007", "LA006", "Sarah Davis", "Failed", 15000.0, "Bank Transfer", LocalDate.now().minusDays(4), LocalDate.now().minusDays(4)));
        disbursements.add(new Disbursement("D008", "LA010", "Lisa Martinez", "Scheduled", 50000.0, "Check", LocalDate.now().plusDays(5), null));
        disbursements.add(new Disbursement("D009", "LA004", "Emily Wilson", "Processed", 75000.0, "Bank Transfer", LocalDate.now().minusDays(2), LocalDate.now().minusDays(1)));
        disbursements.add(new Disbursement("D010", "LA009", "James Rodriguez", "Scheduled", 200000.0, "Digital Wallet", LocalDate.now().plusDays(4), null));
        
        return disbursements;
    }

    // Disbursement class for demonstration
    public static class Disbursement {
        private String disbursementId;
        private String loanId;
        private String customerName;
        private String status;
        private Double amount;
        private String disbursementMethod;
        private LocalDate scheduledDate;
        private LocalDate processedDate;

        public Disbursement(String disbursementId, String loanId, String customerName, String status,
                           Double amount, String disbursementMethod, LocalDate scheduledDate, LocalDate processedDate) {
            this.disbursementId = disbursementId;
            this.loanId = loanId;
            this.customerName = customerName;
            this.status = status;
            this.amount = amount;
            this.disbursementMethod = disbursementMethod;
            this.scheduledDate = scheduledDate;
            this.processedDate = processedDate;
        }

        public String getDisbursementId() {
            return disbursementId;
        }

        public void setDisbursementId(String disbursementId) {
            this.disbursementId = disbursementId;
        }

        public String getLoanId() {
            return loanId;
        }

        public void setLoanId(String loanId) {
            this.loanId = loanId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getDisbursementMethod() {
            return disbursementMethod;
        }

        public void setDisbursementMethod(String disbursementMethod) {
            this.disbursementMethod = disbursementMethod;
        }

        public LocalDate getScheduledDate() {
            return scheduledDate;
        }

        public void setScheduledDate(LocalDate scheduledDate) {
            this.scheduledDate = scheduledDate;
        }

        public LocalDate getProcessedDate() {
            return processedDate;
        }

        public void setProcessedDate(LocalDate processedDate) {
            this.processedDate = processedDate;
        }
    }
}