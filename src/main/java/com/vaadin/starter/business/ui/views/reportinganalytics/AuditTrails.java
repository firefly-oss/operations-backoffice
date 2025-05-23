package com.vaadin.starter.business.ui.views.reportinganalytics;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@PageTitle(NavigationConstants.AUDIT_TRAILS)
@Route(value = "reporting-analytics/audit-trails", layout = MainLayout.class)
public class AuditTrails extends ViewFrame {

    private Grid<AuditEvent> auditGrid;
    private ListDataProvider<AuditEvent> auditDataProvider;
    private Div auditDetailContainer;
    private AuditEvent selectedAuditEvent;

    public AuditTrails() {
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createHeader(), createMainContent());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createHeader() {
        H3 header = new H3("Audit Trails");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createMainContent() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setPadding(false);
        mainLayout.setSpacing(true);
        mainLayout.setSizeFull();
        
        // Add filter form
        mainLayout.add(createAuditFilterForm());
        
        // Create a layout for the audit grid and detail view
        HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.setSizeFull();
        contentLayout.setSpacing(true);
        
        // Create audit grid
        VerticalLayout gridLayout = new VerticalLayout();
        gridLayout.setPadding(false);
        gridLayout.setSpacing(false);
        gridLayout.setSizeFull();
        
        auditGrid = createAuditGrid();
        gridLayout.add(auditGrid);
        gridLayout.expand(auditGrid);
        
        // Create audit detail container
        VerticalLayout detailLayout = new VerticalLayout();
        detailLayout.setPadding(true);
        detailLayout.setSpacing(true);
        detailLayout.setSizeFull();
        
        H4 detailTitle = new H4("Audit Event Details");
        
        auditDetailContainer = new Div();
        auditDetailContainer.setSizeFull();
        auditDetailContainer.getStyle().set("overflow", "auto");
        auditDetailContainer.getStyle().set("padding", "1rem");
        auditDetailContainer.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        auditDetailContainer.getStyle().set("border-radius", "var(--lumo-border-radius)");
        auditDetailContainer.getStyle().set("background-color", "var(--lumo-base-color)");
        
        // Initially show a placeholder
        Span placeholderText = new Span("Select an audit event to view details");
        placeholderText.getStyle().set("color", "var(--lumo-secondary-text-color)");
        placeholderText.getStyle().set("display", "flex");
        placeholderText.getStyle().set("justify-content", "center");
        placeholderText.getStyle().set("align-items", "center");
        placeholderText.getStyle().set("height", "100%");
        
        auditDetailContainer.add(placeholderText);
        
        detailLayout.add(detailTitle, auditDetailContainer);
        detailLayout.expand(auditDetailContainer);
        
        // Set up the content layout
        contentLayout.add(gridLayout, detailLayout);
        contentLayout.setFlexGrow(2, gridLayout);
        contentLayout.setFlexGrow(1, detailLayout);
        
        mainLayout.add(contentLayout);
        mainLayout.expand(contentLayout);
        
        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button exportButton = UIUtils.createPrimaryButton("Export Audit Trail");
        exportButton.setIcon(VaadinIcon.DOWNLOAD.create());
        Button archiveButton = UIUtils.createTertiaryButton("Archive Selected");
        archiveButton.setIcon(VaadinIcon.ARCHIVE.create());
        Button printButton = UIUtils.createTertiaryButton("Print");
        printButton.setIcon(VaadinIcon.PRINT.create());
        
        actionButtons.add(exportButton, archiveButton, printButton);
        actionButtons.setSpacing(true);
        mainLayout.add(actionButtons);
        
        return mainLayout;
    }

    private Component createAuditFilterForm() {
        // Initialize filter fields
        TextField userFilter = new TextField();
        userFilter.setValueChangeMode(ValueChangeMode.EAGER);
        userFilter.setClearButtonVisible(true);
        userFilter.setPlaceholder("Search by user...");
        
        ComboBox<String> actionTypeFilter = new ComboBox<>();
        actionTypeFilter.setItems("Login", "Logout", "Create", "Update", "Delete", "View", "Export", "Import", "Approve", "Reject");
        actionTypeFilter.setClearButtonVisible(true);
        actionTypeFilter.setPlaceholder("All Actions");
        
        ComboBox<String> moduleFilter = new ComboBox<>();
        moduleFilter.setItems("User Management", "Customer Records", "Accounts", "Transactions", "Reports", "System Configuration", "Security", "Compliance");
        moduleFilter.setClearButtonVisible(true);
        moduleFilter.setPlaceholder("All Modules");
        
        DatePicker dateFromFilter = new DatePicker();
        dateFromFilter.setClearButtonVisible(true);
        dateFromFilter.setPlaceholder("From");
        
        DatePicker dateToFilter = new DatePicker();
        dateToFilter.setClearButtonVisible(true);
        dateToFilter.setPlaceholder("To");
        
        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.setItems("Success", "Failure", "Warning");
        statusFilter.setClearButtonVisible(true);
        statusFilter.setPlaceholder("All Statuses");
        
        // Add filter change listeners
        userFilter.addValueChangeListener(e -> {
            filterAuditEvents(userFilter.getValue(), 
                          actionTypeFilter.getValue(), 
                          moduleFilter.getValue(),
                          dateFromFilter.getValue(),
                          dateToFilter.getValue(),
                          statusFilter.getValue());
        });
        
        actionTypeFilter.addValueChangeListener(e -> {
            filterAuditEvents(userFilter.getValue(), 
                          actionTypeFilter.getValue(), 
                          moduleFilter.getValue(),
                          dateFromFilter.getValue(),
                          dateToFilter.getValue(),
                          statusFilter.getValue());
        });
        
        moduleFilter.addValueChangeListener(e -> {
            filterAuditEvents(userFilter.getValue(), 
                          actionTypeFilter.getValue(), 
                          moduleFilter.getValue(),
                          dateFromFilter.getValue(),
                          dateToFilter.getValue(),
                          statusFilter.getValue());
        });
        
        dateFromFilter.addValueChangeListener(e -> {
            filterAuditEvents(userFilter.getValue(), 
                          actionTypeFilter.getValue(), 
                          moduleFilter.getValue(),
                          dateFromFilter.getValue(),
                          dateToFilter.getValue(),
                          statusFilter.getValue());
        });
        
        dateToFilter.addValueChangeListener(e -> {
            filterAuditEvents(userFilter.getValue(), 
                          actionTypeFilter.getValue(), 
                          moduleFilter.getValue(),
                          dateFromFilter.getValue(),
                          dateToFilter.getValue(),
                          statusFilter.getValue());
        });
        
        statusFilter.addValueChangeListener(e -> {
            filterAuditEvents(userFilter.getValue(), 
                          actionTypeFilter.getValue(), 
                          moduleFilter.getValue(),
                          dateFromFilter.getValue(),
                          dateToFilter.getValue(),
                          statusFilter.getValue());
        });
        
        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> {
            filterAuditEvents(userFilter.getValue(), 
                          actionTypeFilter.getValue(), 
                          moduleFilter.getValue(),
                          dateFromFilter.getValue(),
                          dateToFilter.getValue(),
                          statusFilter.getValue());
        });
        
        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> {
            userFilter.clear();
            actionTypeFilter.clear();
            moduleFilter.clear();
            dateFromFilter.clear();
            dateToFilter.clear();
            statusFilter.clear();
            filterAuditEvents(null, null, null, null, null, null);
        });
        
        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton);
        buttonLayout.setSpacing(true);
        
        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(userFilter, "User");
        formLayout.addFormItem(actionTypeFilter, "Action Type");
        formLayout.addFormItem(moduleFilter, "Module");
        formLayout.addFormItem(dateFromFilter, "Date From");
        formLayout.addFormItem(dateToFilter, "Date To");
        formLayout.addFormItem(statusFilter, "Status");
        
        formLayout.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("900px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP)
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

    private Grid<AuditEvent> createAuditGrid() {
        Grid<AuditEvent> grid = new Grid<>();
        
        // Initialize data provider with mock data
        Collection<AuditEvent> auditEvents = generateMockAuditEvents();
        auditDataProvider = new ListDataProvider<>(auditEvents);
        grid.setDataProvider(auditDataProvider);
        
        grid.setId("audit-events");
        grid.setSizeFull();
        
        // Add columns
        grid.addColumn(AuditEvent::getEventId)
                .setHeader("Event ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(AuditEvent::getTimestamp)
                .setHeader("Timestamp")
                .setSortable(true)
                .setComparator(AuditEvent::getTimestamp)
                .setWidth("180px");
        grid.addColumn(AuditEvent::getUser)
                .setHeader("User")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(AuditEvent::getActionType)
                .setHeader("Action")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(AuditEvent::getModule)
                .setHeader("Module")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(event -> createStatusBadge(event.getStatus()))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(AuditEvent::getStatus)
                .setWidth("120px");
        grid.addColumn(AuditEvent::getIpAddress)
                .setHeader("IP Address")
                .setSortable(true)
                .setWidth("150px");
        
        // Add row click listener to show audit details
        grid.addItemClickListener(event -> {
            selectedAuditEvent = event.getItem();
            showAuditDetails(selectedAuditEvent);
        });
        
        return grid;
    }
    
    private void filterAuditEvents(String user, String actionType, String module, 
                                  LocalDate dateFrom, LocalDate dateTo, String status) {
        if (auditDataProvider != null) {
            auditDataProvider.setFilter(event -> {
                boolean matchesUser = user == null || user.isEmpty() || 
                    event.getUser().toLowerCase().contains(user.toLowerCase());
                
                boolean matchesActionType = actionType == null || actionType.isEmpty() || 
                    event.getActionType().equals(actionType);
                
                boolean matchesModule = module == null || module.isEmpty() || 
                    event.getModule().equals(module);
                
                boolean matchesDateFrom = dateFrom == null || 
                    !event.getTimestamp().toLocalDate().isBefore(dateFrom);
                
                boolean matchesDateTo = dateTo == null || 
                    !event.getTimestamp().toLocalDate().isAfter(dateTo);
                
                boolean matchesStatus = status == null || status.isEmpty() || 
                    event.getStatus().equals(status);
                
                return matchesUser && matchesActionType && matchesModule && 
                       matchesDateFrom && matchesDateTo && matchesStatus;
            });
        }
    }
    
    private void showAuditDetails(AuditEvent event) {
        auditDetailContainer.removeAll();
        
        // Create detail layout
        VerticalLayout detailLayout = new VerticalLayout();
        detailLayout.setPadding(false);
        detailLayout.setSpacing(true);
        
        // Event metadata
        FormLayout metadataLayout = new FormLayout();
        metadataLayout.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );
        
        Span eventIdValue = new Span(event.getEventId());
        Span timestampValue = new Span(event.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Span userValue = new Span(event.getUser());
        Span actionTypeValue = new Span(event.getActionType());
        Span moduleValue = new Span(event.getModule());
        Span statusValue = new Span(event.getStatus());
        Span ipAddressValue = new Span(event.getIpAddress());
        Span sessionIdValue = new Span(event.getSessionId());
        
        metadataLayout.addFormItem(eventIdValue, "Event ID");
        metadataLayout.addFormItem(timestampValue, "Timestamp");
        metadataLayout.addFormItem(userValue, "User");
        metadataLayout.addFormItem(actionTypeValue, "Action Type");
        metadataLayout.addFormItem(moduleValue, "Module");
        metadataLayout.addFormItem(statusValue, "Status");
        metadataLayout.addFormItem(ipAddressValue, "IP Address");
        metadataLayout.addFormItem(sessionIdValue, "Session ID");
        
        // Event details
        H4 detailsTitle = new H4("Event Details");
        detailsTitle.getStyle().set("margin-bottom", "0.5em");
        
        TextArea detailsArea = new TextArea();
        detailsArea.setValue(event.getDetails());
        detailsArea.setReadOnly(true);
        detailsArea.setWidthFull();
        detailsArea.setMinHeight("100px");
        
        // Before/After data (if available)
        Details beforeAfterDetails = null;
        if (event.getBeforeData() != null && event.getAfterData() != null) {
            beforeAfterDetails = new Details("Before/After Data", createBeforeAfterLayout(event));
            beforeAfterDetails.setOpened(false);
        }
        
        // Related events
        H4 relatedEventsTitle = new H4("Related Events");
        relatedEventsTitle.getStyle().set("margin-bottom", "0.5em");
        
        Grid<AuditEvent> relatedEventsGrid = new Grid<>();
        relatedEventsGrid.setItems(generateRelatedEvents(event));
        relatedEventsGrid.setHeight("200px");
        
        relatedEventsGrid.addColumn(AuditEvent::getEventId)
                .setHeader("Event ID")
                .setWidth("120px");
        relatedEventsGrid.addColumn(AuditEvent::getTimestamp)
                .setHeader("Timestamp")
                .setWidth("180px");
        relatedEventsGrid.addColumn(AuditEvent::getActionType)
                .setHeader("Action")
                .setWidth("120px");
        relatedEventsGrid.addColumn(event2 -> createStatusBadge(event2.getStatus()))
                .setHeader("Status")
                .setWidth("120px");
        
        // Add all components to the detail layout
        detailLayout.add(metadataLayout, detailsTitle, detailsArea);
        
        if (beforeAfterDetails != null) {
            detailLayout.add(beforeAfterDetails);
        }
        
        detailLayout.add(relatedEventsTitle, relatedEventsGrid);
        
        auditDetailContainer.add(detailLayout);
    }
    
    private Component createBeforeAfterLayout(AuditEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);
        
        // Before data
        H4 beforeTitle = new H4("Before");
        beforeTitle.getStyle().set("margin-bottom", "0.5em");
        beforeTitle.getStyle().set("font-size", "1rem");
        
        TextArea beforeArea = new TextArea();
        beforeArea.setValue(event.getBeforeData());
        beforeArea.setReadOnly(true);
        beforeArea.setWidthFull();
        beforeArea.setHeight("150px");
        
        // After data
        H4 afterTitle = new H4("After");
        afterTitle.getStyle().set("margin-bottom", "0.5em");
        afterTitle.getStyle().set("margin-top", "1em");
        afterTitle.getStyle().set("font-size", "1rem");
        
        TextArea afterArea = new TextArea();
        afterArea.setValue(event.getAfterData());
        afterArea.setReadOnly(true);
        afterArea.setWidthFull();
        afterArea.setHeight("150px");
        
        layout.add(beforeTitle, beforeArea, afterTitle, afterArea);
        return layout;
    }

    private Component createStatusBadge(String status) {
        BadgeColor color;
        switch (status) {
            case "Success":
                color = BadgeColor.SUCCESS;
                break;
            case "Failure":
                color = BadgeColor.ERROR;
                break;
            case "Warning":
                color = BadgeColor.CONTRAST;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Collection<AuditEvent> generateMockAuditEvents() {
        List<AuditEvent> events = new ArrayList<>();
        Random random = new Random(42); // Fixed seed for reproducible data

        String[] users = {
            "john.smith", 
            "maria.rodriguez", 
            "wei.zhang", 
            "sarah.johnson", 
            "ahmed.hassan",
            "admin",
            "system",
            "audit.user"
        };
        
        String[] actionTypes = {
            "Login", 
            "Logout", 
            "Create", 
            "Update", 
            "Delete", 
            "View", 
            "Export", 
            "Import", 
            "Approve", 
            "Reject"
        };
        
        String[] modules = {
            "User Management", 
            "Customer Records", 
            "Accounts", 
            "Transactions", 
            "Reports", 
            "System Configuration", 
            "Security", 
            "Compliance"
        };
        
        String[] statuses = {
            "Success", 
            "Failure", 
            "Warning"
        };
        
        String[] ipAddresses = {
            "192.168.1.100", 
            "10.0.0.45", 
            "172.16.254.1", 
            "192.168.0.1", 
            "10.10.10.10",
            "8.8.8.8",
            "127.0.0.1"
        };
        
        String[] sessionIds = {
            "SESSION-1234567890", 
            "SESSION-0987654321", 
            "SESSION-ABCDEF1234", 
            "SESSION-5678WXYZ", 
            "SESSION-9876QWERTY",
            "SESSION-ASDF1234",
            "SESSION-ZXCV7890"
        };
        
        String[] details = {
            "User logged in successfully", 
            "User logged out", 
            "Created new customer record ID: CUST12345", 
            "Updated account details for ACC987654", 
            "Deleted transaction record TRX123456",
            "Viewed sensitive customer information for CUST54321",
            "Exported transaction report for period 2023-01-01 to 2023-01-31",
            "Imported batch of 100 new customer records",
            "Approved loan application LOAN123456",
            "Rejected suspicious transaction TRX654321",
            "Changed system configuration parameter MAX_ATTEMPTS from 3 to 5",
            "Reset password for user maria.rodriguez",
            "Added new role 'Senior Analyst' to user wei.zhang",
            "Generated compliance report REP123456",
            "Modified access permissions for module 'Customer Records'"
        };
        
        // Generate before/after data pairs for update events
        String[][] beforeAfterPairs = {
            {
                "{\n  \"name\": \"John Doe\",\n  \"email\": \"john.doe@example.com\",\n  \"status\": \"Active\",\n  \"lastLogin\": \"2023-01-15T10:30:00\"\n}",
                "{\n  \"name\": \"John Doe\",\n  \"email\": \"john.doe@newdomain.com\",\n  \"status\": \"Active\",\n  \"lastLogin\": \"2023-01-15T10:30:00\"\n}"
            },
            {
                "{\n  \"accountNumber\": \"ACC123456\",\n  \"balance\": 5000.00,\n  \"status\": \"Active\",\n  \"overdraftLimit\": 1000.00\n}",
                "{\n  \"accountNumber\": \"ACC123456\",\n  \"balance\": 5000.00,\n  \"status\": \"Active\",\n  \"overdraftLimit\": 2000.00\n}"
            },
            {
                "{\n  \"transactionLimit\": 10000.00,\n  \"dailyLimit\": 50000.00,\n  \"requiresApproval\": true\n}",
                "{\n  \"transactionLimit\": 15000.00,\n  \"dailyLimit\": 75000.00,\n  \"requiresApproval\": true\n}"
            },
            {
                "{\n  \"role\": \"Analyst\",\n  \"permissions\": [\"read\", \"export\"],\n  \"department\": \"Finance\"\n}",
                "{\n  \"role\": \"Senior Analyst\",\n  \"permissions\": [\"read\", \"write\", \"export\"],\n  \"department\": \"Finance\"\n}"
            }
        };
        
        for (int i = 1; i <= 50; i++) {
            AuditEvent event = new AuditEvent();
            event.setEventId("EVT" + String.format("%06d", i));
            
            // Generate timestamp within the last 30 days
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime timestamp = now.minusDays(random.nextInt(30)).minusHours(random.nextInt(24)).minusMinutes(random.nextInt(60));
            event.setTimestamp(timestamp);
            
            event.setUser(users[random.nextInt(users.length)]);
            
            String actionType = actionTypes[random.nextInt(actionTypes.length)];
            event.setActionType(actionType);
            
            event.setModule(modules[random.nextInt(modules.length)]);
            
            // Higher chance of success
            String status;
            double statusRandom = random.nextDouble();
            if (statusRandom < 0.8) {
                status = "Success";
            } else if (statusRandom < 0.95) {
                status = "Warning";
            } else {
                status = "Failure";
            }
            event.setStatus(status);
            
            event.setIpAddress(ipAddresses[random.nextInt(ipAddresses.length)]);
            event.setSessionId(sessionIds[random.nextInt(sessionIds.length)]);
            event.setDetails(details[random.nextInt(details.length)]);
            
            // Add before/after data for update events
            if (actionType.equals("Update")) {
                int pairIndex = random.nextInt(beforeAfterPairs.length);
                event.setBeforeData(beforeAfterPairs[pairIndex][0]);
                event.setAfterData(beforeAfterPairs[pairIndex][1]);
            }
            
            events.add(event);
        }
        
        return events;
    }
    
    private List<AuditEvent> generateRelatedEvents(AuditEvent event) {
        List<AuditEvent> relatedEvents = new ArrayList<>();
        Random random = new Random(event.getEventId().hashCode()); // Use event ID as seed for reproducibility
        
        // Generate 1-5 related events
        int numRelatedEvents = 1 + random.nextInt(5);
        
        for (int i = 0; i < numRelatedEvents; i++) {
            AuditEvent relatedEvent = new AuditEvent();
            relatedEvent.setEventId("EVT" + String.format("%06d", random.nextInt(999999)));
            
            // Generate timestamp close to the original event
            LocalDateTime timestamp = event.getTimestamp().plusMinutes(random.nextInt(60) - 30);
            relatedEvent.setTimestamp(timestamp);
            
            // Usually same user
            relatedEvent.setUser(event.getUser());
            
            // Related action types
            String[] relatedActions = {"View", "Export", "Update", "Create", "Approve", "Reject"};
            relatedEvent.setActionType(relatedActions[random.nextInt(relatedActions.length)]);
            
            // Usually same module
            relatedEvent.setModule(event.getModule());
            
            // Status
            String[] statuses = {"Success", "Warning", "Failure"};
            relatedEvent.setStatus(statuses[random.nextInt(statuses.length)]);
            
            // Same IP and session
            relatedEvent.setIpAddress(event.getIpAddress());
            relatedEvent.setSessionId(event.getSessionId());
            
            // Generate a related detail
            relatedEvent.setDetails("Related to event " + event.getEventId());
            
            relatedEvents.add(relatedEvent);
        }
        
        return relatedEvents;
    }
    
    // Inner class to represent an audit event
    public static class AuditEvent {
        private String eventId;
        private LocalDateTime timestamp;
        private String user;
        private String actionType;
        private String module;
        private String status;
        private String ipAddress;
        private String sessionId;
        private String details;
        private String beforeData;
        private String afterData;
        
        public String getEventId() {
            return eventId;
        }
        
        public void setEventId(String eventId) {
            this.eventId = eventId;
        }
        
        public LocalDateTime getTimestamp() {
            return timestamp;
        }
        
        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }
        
        public String getUser() {
            return user;
        }
        
        public void setUser(String user) {
            this.user = user;
        }
        
        public String getActionType() {
            return actionType;
        }
        
        public void setActionType(String actionType) {
            this.actionType = actionType;
        }
        
        public String getModule() {
            return module;
        }
        
        public void setModule(String module) {
            this.module = module;
        }
        
        public String getStatus() {
            return status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
        
        public String getIpAddress() {
            return ipAddress;
        }
        
        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }
        
        public String getSessionId() {
            return sessionId;
        }
        
        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
        
        public String getDetails() {
            return details;
        }
        
        public void setDetails(String details) {
            this.details = details;
        }
        
        public String getBeforeData() {
            return beforeData;
        }
        
        public void setBeforeData(String beforeData) {
            this.beforeData = beforeData;
        }
        
        public String getAfterData() {
            return afterData;
        }
        
        public void setAfterData(String afterData) {
            this.afterData = afterData;
        }
    }
}