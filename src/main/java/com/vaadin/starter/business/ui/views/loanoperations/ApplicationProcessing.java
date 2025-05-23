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

@PageTitle(NavigationConstants.APPLICATION_PROCESSING)
@Route(value = "loan-operations/applications", layout = MainLayout.class)
public class ApplicationProcessing extends ViewFrame {

    private Grid<LoanApplication> grid;
    private ListDataProvider<LoanApplication> dataProvider;

    // Filter form fields
    private TextField applicationIdFilter;
    private TextField customerIdFilter;
    private TextField customerNameFilter;
    private ComboBox<String> loanTypeFilter;
    private ComboBox<String> statusFilter;
    private NumberField minAmountFilter;
    private NumberField maxAmountFilter;
    private DatePicker applicationDateFromFilter;
    private DatePicker applicationDateToFilter;

    public ApplicationProcessing() {
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
        applicationIdFilter = new TextField();
        applicationIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        applicationIdFilter.setClearButtonVisible(true);

        customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        customerNameFilter = new TextField();
        customerNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilter.setClearButtonVisible(true);

        loanTypeFilter = new ComboBox<>();
        loanTypeFilter.setItems("Personal", "Home", "Auto", "Education", "Business");
        loanTypeFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("New", "Under Review", "Approved", "Rejected", "Pending Documentation");
        statusFilter.setClearButtonVisible(true);

        minAmountFilter = new NumberField();
        minAmountFilter.setClearButtonVisible(true);

        maxAmountFilter = new NumberField();
        maxAmountFilter.setClearButtonVisible(true);

        applicationDateFromFilter = new DatePicker();
        applicationDateFromFilter.setClearButtonVisible(true);

        applicationDateToFilter = new DatePicker();
        applicationDateToFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(applicationIdFilter, "Application ID");
        formLayout.addFormItem(customerIdFilter, "Customer ID");
        formLayout.addFormItem(customerNameFilter, "Customer Name");
        formLayout.addFormItem(loanTypeFilter, "Loan Type");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(minAmountFilter, "Min Amount ($)");
        formLayout.addFormItem(maxAmountFilter, "Max Amount ($)");
        formLayout.addFormItem(applicationDateFromFilter, "Application Date From");
        formLayout.addFormItem(applicationDateToFilter, "Application Date To");

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

    private Grid<LoanApplication> createGrid() {
        grid = new Grid<>();

        // Initialize with dummy data
        Collection<LoanApplication> applications = getDummyLoanApplications();
        dataProvider = new ListDataProvider<>(applications);
        grid.setDataProvider(dataProvider);

        grid.setId("loanApplications");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(LoanApplication::getApplicationId)
                .setHeader("Application ID")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(LoanApplication::getCustomerId)
                .setHeader("Customer ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(LoanApplication::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(LoanApplication::getLoanType)
                .setHeader("Loan Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new ComponentRenderer<>(this::createStatusBadge))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(LoanApplication::getStatus)
                .setWidth("150px");
        grid.addColumn(new ComponentRenderer<>(this::createAmountComponent))
                .setHeader("Amount ($)")
                .setSortable(true)
                .setComparator(LoanApplication::getAmount)
                .setWidth("120px");
        grid.addColumn(new LocalDateRenderer<>(LoanApplication::getApplicationDate, "MMM dd, YYYY"))
                .setHeader("Application Date")
                .setSortable(true)
                .setComparator(LoanApplication::getApplicationDate)
                .setWidth("150px");

        // Add action buttons
        grid.addComponentColumn(application -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button viewButton = UIUtils.createSmallButton("View");
            viewButton.addClickListener(e -> {
                // View application details logic would go here
            });

            Button processButton = UIUtils.createSmallButton("Process");
            if (!application.getStatus().equals("Approved") && !application.getStatus().equals("Rejected")) {
                processButton.addClickListener(e -> {
                    // Process application logic would go here
                });
                actions.add(viewButton, processButton);
            } else {
                actions.add(viewButton);
            }

            return actions;
        }).setHeader("Actions").setWidth("150px");

        return grid;
    }

    private Component createStatusBadge(LoanApplication application) {
        String status = application.getStatus();
        BadgeColor color;

        switch (status) {
            case "Approved":
                color = BadgeColor.SUCCESS;
                break;
            case "Rejected":
                color = BadgeColor.ERROR;
                break;
            case "Under Review":
                color = BadgeColor.CONTRAST;
                break;
            case "Pending Documentation":
                color = BadgeColor.CONTRAST;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Component createAmountComponent(LoanApplication application) {
        Double amount = application.getAmount();
        Span amountSpan = new Span(UIUtils.formatAmount(amount));
        return amountSpan;
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply application ID filter
        if (applicationIdFilter.getValue() != null && !applicationIdFilter.getValue().isEmpty()) {
            String applicationIdValue = applicationIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(application -> 
                application.getApplicationId() != null && 
                application.getApplicationId().toLowerCase().contains(applicationIdValue));
        }

        // Apply customer ID filter
        if (customerIdFilter.getValue() != null && !customerIdFilter.getValue().isEmpty()) {
            String customerIdValue = customerIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(application -> 
                application.getCustomerId() != null && 
                application.getCustomerId().toLowerCase().contains(customerIdValue));
        }

        // Apply customer name filter
        if (customerNameFilter.getValue() != null && !customerNameFilter.getValue().isEmpty()) {
            String customerNameValue = customerNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(application -> 
                application.getCustomerName() != null && 
                application.getCustomerName().toLowerCase().contains(customerNameValue));
        }

        // Apply loan type filter
        if (loanTypeFilter.getValue() != null) {
            String loanTypeValue = loanTypeFilter.getValue();
            dataProvider.addFilter(application -> 
                application.getLoanType() != null && 
                application.getLoanType().equals(loanTypeValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(application -> 
                application.getStatus() != null && 
                application.getStatus().equals(statusValue));
        }

        // Apply min amount filter
        if (minAmountFilter.getValue() != null) {
            Double minValue = minAmountFilter.getValue();
            dataProvider.addFilter(application -> 
                application.getAmount() != null && 
                application.getAmount() >= minValue);
        }

        // Apply max amount filter
        if (maxAmountFilter.getValue() != null) {
            Double maxValue = maxAmountFilter.getValue();
            dataProvider.addFilter(application -> 
                application.getAmount() != null && 
                application.getAmount() <= maxValue);
        }

        // Apply application date from filter
        if (applicationDateFromFilter.getValue() != null) {
            LocalDate fromDate = applicationDateFromFilter.getValue();
            dataProvider.addFilter(application -> 
                application.getApplicationDate() != null && 
                !application.getApplicationDate().isBefore(fromDate));
        }

        // Apply application date to filter
        if (applicationDateToFilter.getValue() != null) {
            LocalDate toDate = applicationDateToFilter.getValue();
            dataProvider.addFilter(application -> 
                application.getApplicationDate() != null && 
                !application.getApplicationDate().isAfter(toDate));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        applicationIdFilter.clear();
        customerIdFilter.clear();
        customerNameFilter.clear();
        loanTypeFilter.clear();
        statusFilter.clear();
        minAmountFilter.clear();
        maxAmountFilter.clear();
        applicationDateFromFilter.clear();
        applicationDateToFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    // Dummy data for demonstration
    private Collection<LoanApplication> getDummyLoanApplications() {
        List<LoanApplication> applications = new ArrayList<>();

        applications.add(new LoanApplication("LA001", "CUST001", "John Smith", "Personal", "Under Review", 25000.0, LocalDate.now().minusDays(5)));
        applications.add(new LoanApplication("LA002", "CUST002", "Jane Doe", "Home", "Approved", 350000.0, LocalDate.now().minusDays(15)));
        applications.add(new LoanApplication("LA003", "CUST003", "Robert Johnson", "Auto", "Pending Documentation", 45000.0, LocalDate.now().minusDays(3)));
        applications.add(new LoanApplication("LA004", "CUST004", "Emily Wilson", "Education", "New", 75000.0, LocalDate.now().minusDays(1)));
        applications.add(new LoanApplication("LA005", "CUST005", "Michael Brown", "Business", "Rejected", 150000.0, LocalDate.now().minusDays(10)));
        applications.add(new LoanApplication("LA006", "CUST006", "Sarah Davis", "Personal", "Under Review", 15000.0, LocalDate.now().minusDays(7)));
        applications.add(new LoanApplication("LA007", "CUST007", "David Miller", "Home", "Pending Documentation", 275000.0, LocalDate.now().minusDays(4)));
        applications.add(new LoanApplication("LA008", "CUST008", "Jennifer Garcia", "Auto", "Approved", 35000.0, LocalDate.now().minusDays(20)));
        applications.add(new LoanApplication("LA009", "CUST009", "James Rodriguez", "Business", "New", 200000.0, LocalDate.now()));
        applications.add(new LoanApplication("LA010", "CUST010", "Lisa Martinez", "Education", "Under Review", 50000.0, LocalDate.now().minusDays(2)));

        return applications;
    }

    // Loan Application class for demonstration
    public static class LoanApplication {
        private String applicationId;
        private String customerId;
        private String customerName;
        private String loanType;
        private String status;
        private Double amount;
        private LocalDate applicationDate;

        public LoanApplication(String applicationId, String customerId, String customerName, String loanType,
                              String status, Double amount, LocalDate applicationDate) {
            this.applicationId = applicationId;
            this.customerId = customerId;
            this.customerName = customerName;
            this.loanType = loanType;
            this.status = status;
            this.amount = amount;
            this.applicationDate = applicationDate;
        }

        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getLoanType() {
            return loanType;
        }

        public void setLoanType(String loanType) {
            this.loanType = loanType;
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

        public LocalDate getApplicationDate() {
            return applicationDate;
        }

        public void setApplicationDate(LocalDate applicationDate) {
            this.applicationDate = applicationDate;
        }
    }
}
