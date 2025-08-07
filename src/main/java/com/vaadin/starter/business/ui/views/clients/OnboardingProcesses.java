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

@Route(value = "clients/onboarding", layout = MainLayout.class)
@PageTitle(NavigationConstants.ONBOARDING_PROCESSES)
public class OnboardingProcesses extends ViewFrame {

    public static final int MOBILE_BREAKPOINT = 480;
    private Grid<OnboardingProcess> grid;
    private Registration resizeListener;
    private ListDataProvider<OnboardingProcess> dataProvider;
    private UI ui;

    // Search form fields
    private TextField processIdFilter;
    private TextField clientIdFilter;
    private ComboBox<String> stageFilter;
    private ComboBox<String> statusFilter;
    private DatePicker startDateFilter;

    public OnboardingProcesses() {
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
        processIdFilter = new TextField();
        processIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        processIdFilter.setClearButtonVisible(true);

        clientIdFilter = new TextField();
        clientIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        clientIdFilter.setClearButtonVisible(true);

        stageFilter = new ComboBox<>();
        stageFilter.setItems("Application", "Verification", "Documentation", "Approval", "Activation");
        stageFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("In Progress", "Completed", "On Hold", "Rejected");
        statusFilter.setClearButtonVisible(true);

        startDateFilter = new DatePicker();
        startDateFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> applyFilter());

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> clearFilter());

        Button createProcessButton = UIUtils.createSuccessButton("Create Process");
        createProcessButton.addClickListener(e -> openCreateProcessDialog());

        // Create a wrapper for search and clear buttons (right side)
        HorizontalLayout rightButtons = new HorizontalLayout(searchButton, clearButton);
        rightButtons.setSpacing(true);

        // Create button layout with Create Process on left and search/clear on right
        HorizontalLayout buttonLayout = new HorizontalLayout(createProcessButton, rightButtons);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(processIdFilter, "Process ID");
        formLayout.addFormItem(clientIdFilter, "Client ID");
        formLayout.addFormItem(stageFilter, "Stage");
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

    private Grid<OnboardingProcess> createGrid() {
        grid = new Grid<>();
        grid.addThemeName("mobile");

        grid.setId("onboarding-processes");
        grid.setSizeFull();

        // Configure grid columns
        grid.addColumn(OnboardingProcess::getProcessId)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("Process ID")
                .setSortable(true);
        grid.addColumn(OnboardingProcess::getClientId)
                .setAutoWidth(true)
                .setHeader("Client ID")
                .setSortable(true);
        grid.addColumn(OnboardingProcess::getClientName)
                .setAutoWidth(true)
                .setHeader("Client Name")
                .setSortable(true);
        grid.addColumn(OnboardingProcess::getStage)
                .setAutoWidth(true)
                .setHeader("Stage")
                .setSortable(true);
        grid.addColumn(OnboardingProcess::getAssignedTo)
                .setAutoWidth(true)
                .setHeader("Assigned To")
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
        grid.addColumn(new ComponentRenderer<>(this::createDueDate))
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Due Date")
                .setTextAlign(ColumnTextAlign.END);

        // Add Actions column with view and delete buttons
        grid.addColumn(new ComponentRenderer<>(this::createActionButtons))
                .setHeader("Actions")
                .setWidth("150px")
                .setFlexGrow(0)
                .setTextAlign(ColumnTextAlign.CENTER);

        // Initialize with data provider
        dataProvider = DataProvider.ofCollection(getMockProcesses());
        grid.setDataProvider(dataProvider);

        return grid;
    }

    private Component createStatusComponent(OnboardingProcess process) {
        Icon icon;
        String status = process.getStatus();

        if ("Completed".equals(status)) {
            icon = UIUtils.createPrimaryIcon(VaadinIcon.CHECK);
        } else if ("In Progress".equals(status)) {
            icon = UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.CLOCK);
        } else if ("On Hold".equals(status)) {
            icon = UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.PAUSE);
        } else {
            icon = UIUtils.createDisabledIcon(VaadinIcon.CLOSE);
        }
        return icon;
    }

    private Component createStartDate(OnboardingProcess process) {
        return new Span(UIUtils.formatDate(process.getStartDate()));
    }

    private Component createDueDate(OnboardingProcess process) {
        return new Span(UIUtils.formatDate(process.getDueDate()));
    }

    private Component createActionButtons(OnboardingProcess process) {
        // Create layout for buttons
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        // Create view details button with eye icon
        Button viewDetailsButton = UIUtils.createPastelButton(VaadinIcon.EYE);
        viewDetailsButton.addClickListener(e -> showDetails(process));
        viewDetailsButton.getElement().setAttribute("title", "View Details");

        // Create delete button with trash icon
        Button deleteButton = UIUtils.createPastelErrorButton(VaadinIcon.TRASH);
        deleteButton.addClickListener(e -> deleteProcess(process));
        deleteButton.getElement().setAttribute("title", "Delete");

        layout.add(viewDetailsButton, deleteButton);
        return layout;
    }

    private void showDetails(OnboardingProcess process) {
        OnboardingProcessDetails processDetails = new OnboardingProcessDetails(process);
        processDetails.open();
    }

    private void deleteProcess(OnboardingProcess process) {
        // This would be implemented to delete the process
        System.out.println("[DEBUG_LOG] Delete process: " + process.getProcessId());

        // For demo purposes, remove from the data provider
        dataProvider.getItems().remove(process);
        dataProvider.refreshAll();

        UIUtils.showNotification("Process " + process.getProcessId() + " deleted.");
    }

    private void filter() {
        // Default filter - show all
        dataProvider.clearFilters();
    }

    private void applyFilter() {
        dataProvider.clearFilters();

        // Apply process ID filter if not empty
        if (processIdFilter.getValue() != null && !processIdFilter.getValue().isEmpty()) {
            String processIdFilterValue = processIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(process -> 
                process.getProcessId().toLowerCase().contains(processIdFilterValue));
        }

        // Apply client ID filter if not empty
        if (clientIdFilter.getValue() != null && !clientIdFilter.getValue().isEmpty()) {
            String clientIdFilterValue = clientIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(process -> 
                process.getClientId() != null &&
                process.getClientId().toLowerCase().contains(clientIdFilterValue));
        }

        // Apply stage filter if selected
        if (stageFilter.getValue() != null) {
            String stageFilterValue = stageFilter.getValue();
            dataProvider.addFilter(process -> 
                stageFilterValue.equals(process.getStage()));
        }

        // Apply status filter if selected
        if (statusFilter.getValue() != null) {
            String statusFilterValue = statusFilter.getValue();
            dataProvider.addFilter(process -> 
                statusFilterValue.equals(process.getStatus()));
        }

        // Apply start date filter if selected
        if (startDateFilter.getValue() != null) {
            LocalDate filterDate = startDateFilter.getValue();
            dataProvider.addFilter(process -> 
                process.getStartDate() != null && 
                !process.getStartDate().isBefore(filterDate));
        }
    }

    private void clearFilter() {
        // Clear all filter fields
        processIdFilter.clear();
        clientIdFilter.clear();
        stageFilter.clear();
        statusFilter.clear();
        startDateFilter.clear();

        // Reset filters
        dataProvider.clearFilters();
    }

    private void openCreateProcessDialog() {
        // This would be implemented to open a dialog for creating a new process
        System.out.println("[DEBUG_LOG] Create process dialog would open here");
        UIUtils.showNotification("Create process functionality would be implemented here.");
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
        List<Grid.Column<OnboardingProcess>> columns = grid.getColumns();

        // "Desktop" columns
        for (Grid.Column<OnboardingProcess> column : columns) {
            column.setVisible(!mobile);
        }
    }

    // Mock data for the grid
    private List<OnboardingProcess> getMockProcesses() {
        List<OnboardingProcess> processes = new ArrayList<>();

        processes.add(new OnboardingProcess("OBP001", "CLI001", "John Smith", "Verification", "Sarah Johnson", "In Progress", LocalDate.now().minusDays(5), LocalDate.now().plusDays(2), "Individual", LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(1)));
        processes.add(new OnboardingProcess("OBP002", "CLI002", "Acme Corporation", "Documentation", "Michael Thompson", "In Progress", LocalDate.now().minusDays(3), LocalDate.now().plusDays(4), "Corporate", LocalDateTime.now().minusDays(3), LocalDateTime.now().minusDays(1)));
        processes.add(new OnboardingProcess("OBP003", "CLI003", "Maria Garcia", "Application", "Emma Wilson", "On Hold", LocalDate.now().minusDays(10), LocalDate.now().plusDays(5), "Individual", LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(2)));
        processes.add(new OnboardingProcess("OBP004", "CLI004", "Tech Innovations Ltd", "Approval", "John Smith", "In Progress", LocalDate.now().minusDays(2), LocalDate.now().plusDays(3), "Corporate", LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1)));
        processes.add(new OnboardingProcess("OBP005", "CLI005", "Hiroshi Tanaka", "Verification", "Sarah Johnson", "Rejected", LocalDate.now().minusDays(15), LocalDate.now().minusDays(5), "Individual", LocalDateTime.now().minusDays(15), LocalDateTime.now().minusDays(5)));
        processes.add(new OnboardingProcess("OBP006", "CLI006", "Global Traders Inc", "Activation", "Michael Thompson", "Completed", LocalDate.now().minusDays(20), LocalDate.now().minusDays(2), "Corporate", LocalDateTime.now().minusDays(20), LocalDateTime.now().minusDays(2)));
        processes.add(new OnboardingProcess("OBP007", "CLI007", "Sophie Dubois", "Documentation", "Emma Wilson", "In Progress", LocalDate.now().minusDays(4), LocalDate.now().plusDays(6), "Individual", LocalDateTime.now().minusDays(4), LocalDateTime.now().minusDays(1)));
        processes.add(new OnboardingProcess("OBP008", "CLI008", "Green Energy Solutions", "Application", "John Smith", "On Hold", LocalDate.now().minusDays(7), LocalDate.now().plusDays(8), "Corporate", LocalDateTime.now().minusDays(7), LocalDateTime.now().minusDays(2)));
        processes.add(new OnboardingProcess("OBP009", "CLI009", "Carlos Rodriguez", "Approval", "Sarah Johnson", "In Progress", LocalDate.now().minusDays(1), LocalDate.now().plusDays(9), "Individual", LocalDateTime.now().minusDays(1), LocalDateTime.now()));
        processes.add(new OnboardingProcess("OBP010", "CLI010", "Australian Mining Ltd", "Activation", "Michael Thompson", "Completed", LocalDate.now().minusDays(12), LocalDate.now().minusDays(1), "Corporate", LocalDateTime.now().minusDays(12), LocalDateTime.now().minusDays(1)));

        return processes;
    }

    // OnboardingProcess model class
    public static class OnboardingProcess {
        private String processId;
        private String clientId;
        private String clientName;
        private String stage;
        private String assignedTo;
        private String status;
        private LocalDate startDate;
        private LocalDate dueDate;
        private String clientType;
        private LocalDateTime dateCreated;
        private LocalDateTime dateUpdated;

        public OnboardingProcess(String processId, String clientId, String clientName, String stage, 
                               String assignedTo, String status, LocalDate startDate, LocalDate dueDate, 
                               String clientType, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
            this.processId = processId;
            this.clientId = clientId;
            this.clientName = clientName;
            this.stage = stage;
            this.assignedTo = assignedTo;
            this.status = status;
            this.startDate = startDate;
            this.dueDate = dueDate;
            this.clientType = clientType;
            this.dateCreated = dateCreated;
            this.dateUpdated = dateUpdated;
        }

        public String getProcessId() {
            return processId;
        }

        public String getClientId() {
            return clientId;
        }

        public String getClientName() {
            return clientName;
        }

        public String getStage() {
            return stage;
        }

        public String getAssignedTo() {
            return assignedTo;
        }

        public String getStatus() {
            return status;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getDueDate() {
            return dueDate;
        }

        public String getClientType() {
            return clientType;
        }

        public LocalDateTime getDateCreated() {
            return dateCreated;
        }

        public LocalDateTime getDateUpdated() {
            return dateUpdated;
        }
    }
}
