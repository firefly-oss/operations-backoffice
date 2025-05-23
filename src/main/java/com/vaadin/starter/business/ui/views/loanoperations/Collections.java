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

@PageTitle(NavigationConstants.COLLECTIONS)
@Route(value = "loan-operations/collections", layout = MainLayout.class)
public class Collections extends ViewFrame {

    private Grid<LoanCollection> grid;
    private ListDataProvider<LoanCollection> dataProvider;

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

    public Collections() {
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

    private Grid<LoanCollection> createGrid() {
        grid = new Grid<>();

        // Initialize with dummy data
        Collection<LoanCollection> collections = getDummyCollections();
        dataProvider = new ListDataProvider<>(collections);
        grid.setDataProvider(dataProvider);

        grid.setId("loanCollections");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(LoanCollection::getCollectionId)
                .setHeader("Collection ID")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(LoanCollection::getLoanId)
                .setHeader("Loan ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(LoanCollection::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(new ComponentRenderer<>(this::createStatusBadge))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(LoanCollection::getStatus)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(this::createAmountComponent))
                .setHeader("Amount ($)")
                .setSortable(true)
                .setComparator(LoanCollection::getAmount)
                .setWidth("120px");
        grid.addColumn(LoanCollection::getPaymentMethod)
                .setHeader("Payment Method")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new LocalDateRenderer<>(LoanCollection::getDueDate, "MMM dd, YYYY"))
                .setHeader("Due Date")
                .setSortable(true)
                .setComparator(LoanCollection::getDueDate)
                .setWidth("150px");
        grid.addColumn(new LocalDateRenderer<>(LoanCollection::getPaymentDate, "MMM dd, YYYY"))
                .setHeader("Payment Date")
                .setSortable(true)
                .setComparator(LoanCollection::getPaymentDate)
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

    private Component createStatusBadge(LoanCollection collection) {
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

    private Component createAmountComponent(LoanCollection collection) {
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

    // Dummy data for demonstration
    private Collection<LoanCollection> getDummyCollections() {
        List<LoanCollection> collections = new ArrayList<>();
        
        collections.add(new LoanCollection("C001", "LA002", "Jane Doe", "Pending", 2500.0, "Auto Debit", LocalDate.now().plusDays(5), null));
        collections.add(new LoanCollection("C002", "LA008", "Jennifer Garcia", "Paid", 1200.0, "Bank Transfer", LocalDate.now().minusDays(10), LocalDate.now().minusDays(8)));
        collections.add(new LoanCollection("C003", "LA003", "Robert Johnson", "Overdue", 1500.0, "Auto Debit", LocalDate.now().minusDays(5), null));
        collections.add(new LoanCollection("C004", "LA005", "Michael Brown", "Written Off", 3000.0, "Bank Transfer", LocalDate.now().minusDays(60), null));
        collections.add(new LoanCollection("C005", "LA001", "John Smith", "Pending", 1800.0, "Auto Debit", LocalDate.now().plusDays(10), null));
        collections.add(new LoanCollection("C006", "LA007", "David Miller", "Paid", 2200.0, "Digital Wallet", LocalDate.now().minusDays(15), LocalDate.now().minusDays(14)));
        collections.add(new LoanCollection("C007", "LA006", "Sarah Davis", "In Collection", 1600.0, "Bank Transfer", LocalDate.now().minusDays(20), null));
        collections.add(new LoanCollection("C008", "LA010", "Lisa Martinez", "Pending", 2000.0, "Auto Debit", LocalDate.now().plusDays(15), null));
        collections.add(new LoanCollection("C009", "LA004", "Emily Wilson", "Paid", 1900.0, "Check", LocalDate.now().minusDays(5), LocalDate.now().minusDays(3)));
        collections.add(new LoanCollection("C010", "LA009", "James Rodriguez", "Overdue", 2700.0, "Auto Debit", LocalDate.now().minusDays(2), null));
        
        return collections;
    }

    // Loan Collection class for demonstration
    public static class LoanCollection {
        private String collectionId;
        private String loanId;
        private String customerName;
        private String status;
        private Double amount;
        private String paymentMethod;
        private LocalDate dueDate;
        private LocalDate paymentDate;

        public LoanCollection(String collectionId, String loanId, String customerName, String status,
                             Double amount, String paymentMethod, LocalDate dueDate, LocalDate paymentDate) {
            this.collectionId = collectionId;
            this.loanId = loanId;
            this.customerName = customerName;
            this.status = status;
            this.amount = amount;
            this.paymentMethod = paymentMethod;
            this.dueDate = dueDate;
            this.paymentDate = paymentDate;
        }

        public String getCollectionId() {
            return collectionId;
        }

        public void setCollectionId(String collectionId) {
            this.collectionId = collectionId;
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

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public LocalDate getDueDate() {
            return dueDate;
        }

        public void setDueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
        }

        public LocalDate getPaymentDate() {
            return paymentDate;
        }

        public void setPaymentDate(LocalDate paymentDate) {
            this.paymentDate = paymentDate;
        }
    }
}