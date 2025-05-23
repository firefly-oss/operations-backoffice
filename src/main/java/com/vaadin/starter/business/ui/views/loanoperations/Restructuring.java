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

@PageTitle(NavigationConstants.RESTRUCTURING)
@Route(value = "loan-operations/restructuring", layout = MainLayout.class)
public class Restructuring extends ViewFrame {

    private Grid<RestructuringRequest> grid;
    private ListDataProvider<RestructuringRequest> dataProvider;

    // Filter form fields
    private TextField requestIdFilter;
    private TextField loanIdFilter;
    private TextField customerNameFilter;
    private ComboBox<String> statusFilter;
    private ComboBox<String> typeFilter;
    private DatePicker requestDateFromFilter;
    private DatePicker requestDateToFilter;
    private TextField assignedToFilter;

    public Restructuring() {
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

    private Grid<RestructuringRequest> createGrid() {
        grid = new Grid<>();

        // Initialize with dummy data
        Collection<RestructuringRequest> requests = getDummyRequests();
        dataProvider = new ListDataProvider<>(requests);
        grid.setDataProvider(dataProvider);

        grid.setId("restructuringRequests");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(RestructuringRequest::getRequestId)
                .setHeader("Request ID")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(RestructuringRequest::getLoanId)
                .setHeader("Loan ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(RestructuringRequest::getCustomerName)
                .setHeader("Customer Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(RestructuringRequest::getType)
                .setHeader("Type")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(new ComponentRenderer<>(this::createStatusBadge))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(RestructuringRequest::getStatus)
                .setWidth("120px");
        grid.addColumn(new LocalDateRenderer<>(RestructuringRequest::getRequestDate, "MMM dd, YYYY"))
                .setHeader("Request Date")
                .setSortable(true)
                .setComparator(RestructuringRequest::getRequestDate)
                .setWidth("150px");
        grid.addColumn(RestructuringRequest::getAssignedTo)
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new LocalDateRenderer<>(RestructuringRequest::getDecisionDate, "MMM dd, YYYY"))
                .setHeader("Decision Date")
                .setSortable(true)
                .setComparator(RestructuringRequest::getDecisionDate)
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

    private Component createStatusBadge(RestructuringRequest request) {
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

    // Dummy data for demonstration
    private Collection<RestructuringRequest> getDummyRequests() {
        List<RestructuringRequest> requests = new ArrayList<>();
        
        requests.add(new RestructuringRequest("RR001", "LA003", "Robert Johnson", "Term Extension", "New", LocalDate.now().minusDays(2), "John Analyst", null));
        requests.add(new RestructuringRequest("RR002", "LA005", "Michael Brown", "Interest Rate Reduction", "Approved", LocalDate.now().minusDays(15), "Sarah Manager", LocalDate.now().minusDays(10)));
        requests.add(new RestructuringRequest("RR003", "LA006", "Sarah Davis", "Payment Holiday", "Under Review", LocalDate.now().minusDays(5), "John Analyst", null));
        requests.add(new RestructuringRequest("RR004", "LA010", "Lisa Martinez", "Debt Consolidation", "Rejected", LocalDate.now().minusDays(20), "Mark Supervisor", LocalDate.now().minusDays(15)));
        requests.add(new RestructuringRequest("RR005", "LA007", "David Miller", "Principal Reduction", "Implemented", LocalDate.now().minusDays(30), "Sarah Manager", LocalDate.now().minusDays(25)));
        requests.add(new RestructuringRequest("RR006", "LA001", "John Smith", "Term Extension", "New", LocalDate.now().minusDays(1), null, null));
        requests.add(new RestructuringRequest("RR007", "LA008", "Jennifer Garcia", "Interest Rate Reduction", "Under Review", LocalDate.now().minusDays(7), "Mark Supervisor", null));
        requests.add(new RestructuringRequest("RR008", "LA004", "Emily Wilson", "Payment Holiday", "Approved", LocalDate.now().minusDays(12), "John Analyst", LocalDate.now().minusDays(8)));
        requests.add(new RestructuringRequest("RR009", "LA009", "James Rodriguez", "Debt Consolidation", "New", LocalDate.now(), null, null));
        requests.add(new RestructuringRequest("RR010", "LA002", "Jane Doe", "Principal Reduction", "Implemented", LocalDate.now().minusDays(25), "Sarah Manager", LocalDate.now().minusDays(20)));
        
        return requests;
    }

    // Restructuring Request class for demonstration
    public static class RestructuringRequest {
        private String requestId;
        private String loanId;
        private String customerName;
        private String type;
        private String status;
        private LocalDate requestDate;
        private String assignedTo;
        private LocalDate decisionDate;

        public RestructuringRequest(String requestId, String loanId, String customerName, String type,
                                   String status, LocalDate requestDate, String assignedTo, LocalDate decisionDate) {
            this.requestId = requestId;
            this.loanId = loanId;
            this.customerName = customerName;
            this.type = type;
            this.status = status;
            this.requestDate = requestDate;
            this.assignedTo = assignedTo;
            this.decisionDate = decisionDate;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LocalDate getRequestDate() {
            return requestDate;
        }

        public void setRequestDate(LocalDate requestDate) {
            this.requestDate = requestDate;
        }

        public String getAssignedTo() {
            return assignedTo;
        }

        public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
        }

        public LocalDate getDecisionDate() {
            return decisionDate;
        }

        public void setDecisionDate(LocalDate decisionDate) {
            this.decisionDate = decisionDate;
        }
    }
}