package com.vaadin.starter.business.ui.views.clients;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.IconSize;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Route(value = "clients/contracts", layout = MainLayout.class)
@PageTitle(NavigationConstants.CONTRACTS)
public class Contracts extends ViewFrame {

    public static final int MOBILE_BREAKPOINT = 480;
    private Grid<Contract> grid;
    private Registration resizeListener;
    private ListDataProvider<Contract> dataProvider;
    private UI ui;

    // Search form fields
    private TextField contractIdFilter;
    private TextField clientIdFilter;
    private ComboBox<String> typeFilter;
    private ComboBox<String> statusFilter;
    private DatePicker startDateFilter;

    public Contracts() {
        setViewContent(createContent());

        // Initialize with default filter
        filter();
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createFilterForm(), createGrid());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        return content;
    }

    private Component createFilterForm() {
        // Initialize filter fields
        contractIdFilter = new TextField();
        contractIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        contractIdFilter.setClearButtonVisible(true);

        clientIdFilter = new TextField();
        clientIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        clientIdFilter.setClearButtonVisible(true);

        typeFilter = new ComboBox<>();
        typeFilter.setItems("Standard", "Premium", "Enterprise", "Custom");
        typeFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("Active", "Pending", "Expired", "Terminated");
        statusFilter.setClearButtonVisible(true);

        startDateFilter = new DatePicker();
        startDateFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> applyFilter());

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> clearFilter());

        Button createContractButton = UIUtils.createSuccessButton("Create Contract");
        createContractButton.addClickListener(e -> openCreateContractDialog());

        // Create a wrapper for search and clear buttons (right side)
        HorizontalLayout rightButtons = new HorizontalLayout(searchButton, clearButton);
        rightButtons.setSpacing(true);

        // Create button layout with Create Contract on left and search/clear on right
        HorizontalLayout buttonLayout = new HorizontalLayout(createContractButton, rightButtons);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(contractIdFilter, "Contract ID");
        formLayout.addFormItem(clientIdFilter, "Client ID");
        formLayout.addFormItem(typeFilter, "Type");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(startDateFilter, "Start Date After");

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

    private Grid<Contract> createGrid() {
        grid = new Grid<>();
        grid.addThemeName("mobile");

        grid.setId("contracts");
        grid.setSizeFull();

        // Configure grid columns
        grid.addColumn(Contract::getContractId)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("Contract ID")
                .setSortable(true);
        grid.addColumn(Contract::getClientId)
                .setAutoWidth(true)
                .setHeader("Client ID")
                .setSortable(true);
        grid.addColumn(Contract::getClientName)
                .setAutoWidth(true)
                .setHeader("Client Name")
                .setSortable(true);
        grid.addColumn(Contract::getType)
                .setAutoWidth(true)
                .setHeader("Type")
                .setSortable(true);
        grid.addColumn(Contract::getValue)
                .setAutoWidth(true)
                .setHeader("Value")
                .setSortable(true);
        grid.addColumn(new ComponentRenderer<>(this::createStatusComponent))
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Status")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new ComponentRenderer<>(this::createStartDate))
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Start Date")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new ComponentRenderer<>(this::createEndDate))
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("End Date")
                .setTextAlign(ColumnTextAlign.END);

        // Add Actions column with view and delete buttons
        grid.addColumn(new ComponentRenderer<>(this::createActionButtons))
                .setHeader("Actions")
                .setWidth("150px")
                .setFlexGrow(0)
                .setTextAlign(ColumnTextAlign.CENTER);

        // Initialize with data provider
        dataProvider = DataProvider.ofCollection(getMockContracts());
        grid.setDataProvider(dataProvider);

        return grid;
    }

    private Component createStatusComponent(Contract contract) {
        Icon icon;
        String status = contract.getStatus();

        if ("Active".equals(status)) {
            icon = UIUtils.createPrimaryIcon(VaadinIcon.CHECK);
        } else if ("Pending".equals(status)) {
            icon = UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.CLOCK);
        } else if ("Expired".equals(status)) {
            icon = UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.CALENDAR);
        } else {
            icon = UIUtils.createDisabledIcon(VaadinIcon.CLOSE);
        }
        return icon;
    }

    private Component createStartDate(Contract contract) {
        return new Span(UIUtils.formatDate(contract.getStartDate()));
    }

    private Component createEndDate(Contract contract) {
        return new Span(UIUtils.formatDate(contract.getEndDate()));
    }

    private Component createActionButtons(Contract contract) {
        // Create layout for buttons
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        // Create view details button with eye icon
        Button viewDetailsButton = UIUtils.createButton(VaadinIcon.EYE);
        viewDetailsButton.addClickListener(e -> showDetails(contract));
        viewDetailsButton.getElement().getThemeList().add("small");
        viewDetailsButton.getElement().getThemeList().add("tertiary");
        viewDetailsButton.getElement().setAttribute("title", "View Details");
        viewDetailsButton.getStyle().set("cursor", "pointer");

        // Create delete button with trash icon
        Button deleteButton = UIUtils.createButton(VaadinIcon.TRASH);
        deleteButton.addClickListener(e -> deleteContract(contract));
        deleteButton.getElement().getThemeList().add("small");
        deleteButton.getElement().getThemeList().add("error");
        deleteButton.getElement().getThemeList().add("tertiary");
        deleteButton.getElement().setAttribute("title", "Delete");
        deleteButton.getStyle().set("cursor", "pointer");

        layout.add(viewDetailsButton, deleteButton);
        return layout;
    }

    private void showDetails(Contract contract) {
        ContractDetails contractDetails = new ContractDetails(contract);
        contractDetails.open();
    }

    private void deleteContract(Contract contract) {
        // This would be implemented to delete the contract
        System.out.println("[DEBUG_LOG] Delete contract: " + contract.getContractId());

        // For demo purposes, remove from the data provider
        dataProvider.getItems().remove(contract);
        dataProvider.refreshAll();

        UIUtils.showNotification("Contract " + contract.getContractId() + " deleted.");
    }

    private void filter() {
        // Default filter - show all
        dataProvider.clearFilters();
    }

    private void applyFilter() {
        dataProvider.clearFilters();

        // Apply contract ID filter if not empty
        if (contractIdFilter.getValue() != null && !contractIdFilter.getValue().isEmpty()) {
            String contractIdFilterValue = contractIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(contract -> 
                contract.getContractId().toLowerCase().contains(contractIdFilterValue));
        }

        // Apply client ID filter if not empty
        if (clientIdFilter.getValue() != null && !clientIdFilter.getValue().isEmpty()) {
            String clientIdFilterValue = clientIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(contract -> 
                contract.getClientId() != null &&
                contract.getClientId().toLowerCase().contains(clientIdFilterValue));
        }

        // Apply type filter if selected
        if (typeFilter.getValue() != null) {
            String typeFilterValue = typeFilter.getValue();
            dataProvider.addFilter(contract -> 
                typeFilterValue.equals(contract.getType()));
        }

        // Apply status filter if selected
        if (statusFilter.getValue() != null) {
            String statusFilterValue = statusFilter.getValue();
            dataProvider.addFilter(contract -> 
                statusFilterValue.equals(contract.getStatus()));
        }

        // Apply start date filter if selected
        if (startDateFilter.getValue() != null) {
            LocalDate filterDate = startDateFilter.getValue();
            dataProvider.addFilter(contract -> 
                contract.getStartDate() != null && 
                !contract.getStartDate().isBefore(filterDate));
        }
    }

    private void clearFilter() {
        // Clear all filter fields
        contractIdFilter.clear();
        clientIdFilter.clear();
        typeFilter.clear();
        statusFilter.clear();
        startDateFilter.clear();

        // Reset filters
        dataProvider.clearFilters();
    }

    private void openCreateContractDialog() {
        // This would be implemented to open a dialog for creating a new contract
        System.out.println("[DEBUG_LOG] Create contract dialog would open here");
        UIUtils.showNotification("Create contract functionality would be implemented here.");
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        getUI().ifPresent(currentUI -> {
            this.ui = currentUI;
            Page page = currentUI.getPage();
            resizeListener = page.addBrowserWindowResizeListener(event -> updateVisibleColumns(event.getWidth()));
            page.retrieveExtendedClientDetails(details -> updateVisibleColumns(details.getBodyClientWidth()));
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        if (resizeListener != null) {
            resizeListener.remove();
        }
        super.onDetach(detachEvent);
    }

    private void updateVisibleColumns(int width) {
        boolean mobile = width < MOBILE_BREAKPOINT;
        List<Grid.Column<Contract>> columns = grid.getColumns();

        // "Desktop" columns
        for (Grid.Column<Contract> column : columns) {
            column.setVisible(!mobile);
        }
    }

    // Mock data for the grid
    private List<Contract> getMockContracts() {
        List<Contract> contracts = new ArrayList<>();

        contracts.add(new Contract("CNT001", "CLI001", "John Smith", "Standard", 1200.00, "Active", LocalDate.now().minusMonths(6), LocalDate.now().plusMonths(6), LocalDateTime.now().minusDays(180), LocalDateTime.now().minusDays(5)));
        contracts.add(new Contract("CNT002", "CLI002", "Acme Corporation", "Enterprise", 5000.00, "Active", LocalDate.now().minusMonths(3), LocalDate.now().plusMonths(9), LocalDateTime.now().minusDays(90), LocalDateTime.now().minusDays(4)));
        contracts.add(new Contract("CNT003", "CLI003", "Maria Garcia", "Standard", 1000.00, "Expired", LocalDate.now().minusMonths(12), LocalDate.now().minusMonths(1), LocalDateTime.now().minusDays(365), LocalDateTime.now().minusDays(3)));
        contracts.add(new Contract("CNT004", "CLI004", "Tech Innovations Ltd", "Premium", 3500.00, "Active", LocalDate.now().minusMonths(2), LocalDate.now().plusMonths(10), LocalDateTime.now().minusDays(60), LocalDateTime.now().minusDays(2)));
        contracts.add(new Contract("CNT005", "CLI005", "Hiroshi Tanaka", "Standard", 1200.00, "Terminated", LocalDate.now().minusMonths(8), LocalDate.now().plusMonths(4), LocalDateTime.now().minusDays(240), LocalDateTime.now().minusDays(1)));
        contracts.add(new Contract("CNT006", "CLI006", "Global Traders Inc", "Custom", 7500.00, "Active", LocalDate.now().minusMonths(1), LocalDate.now().plusMonths(23), LocalDateTime.now().minusDays(30), LocalDateTime.now()));
        contracts.add(new Contract("CNT007", "CLI007", "Sophie Dubois", "Standard", 1200.00, "Pending", LocalDate.now().plusDays(15), LocalDate.now().plusMonths(12).plusDays(15), LocalDateTime.now().minusDays(10), LocalDateTime.now()));
        contracts.add(new Contract("CNT008", "CLI008", "Green Energy Solutions", "Premium", 3000.00, "Terminated", LocalDate.now().minusMonths(5), LocalDate.now().plusMonths(7), LocalDateTime.now().minusDays(150), LocalDateTime.now()));
        contracts.add(new Contract("CNT009", "CLI009", "Carlos Rodriguez", "Standard", 1200.00, "Active", LocalDate.now().minusMonths(4), LocalDate.now().plusMonths(8), LocalDateTime.now().minusDays(120), LocalDateTime.now()));
        contracts.add(new Contract("CNT010", "CLI010", "Australian Mining Ltd", "Enterprise", 6000.00, "Active", LocalDate.now().minusMonths(2), LocalDate.now().plusMonths(22), LocalDateTime.now().minusDays(60), LocalDateTime.now()));

        return contracts;
    }

    // Contract model class
    public static class Contract {
        private String contractId;
        private String clientId;
        private String clientName;
        private String type;
        private Double value;
        private String status;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDateTime dateCreated;
        private LocalDateTime dateUpdated;

        public Contract(String contractId, String clientId, String clientName, String type, 
                      Double value, String status, LocalDate startDate, LocalDate endDate, 
                      LocalDateTime dateCreated, LocalDateTime dateUpdated) {
            this.contractId = contractId;
            this.clientId = clientId;
            this.clientName = clientName;
            this.type = type;
            this.value = value;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
            this.dateCreated = dateCreated;
            this.dateUpdated = dateUpdated;
        }

        public String getContractId() {
            return contractId;
        }

        public String getClientId() {
            return clientId;
        }

        public String getClientName() {
            return clientName;
        }

        public String getType() {
            return type;
        }

        public Double getValue() {
            return value;
        }

        public String getStatus() {
            return status;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public LocalDateTime getDateCreated() {
            return dateCreated;
        }

        public LocalDateTime getDateUpdated() {
            return dateUpdated;
        }
    }
}
