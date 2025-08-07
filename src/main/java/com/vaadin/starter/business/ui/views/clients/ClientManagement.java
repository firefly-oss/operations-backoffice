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

@Route(value = "clients/management", layout = MainLayout.class)
@PageTitle(NavigationConstants.CLIENT_MANAGEMENT)
public class ClientManagement extends ViewFrame {

    public static final int MOBILE_BREAKPOINT = 480;
    private Grid<Client> grid;
    private Registration resizeListener;
    private ListDataProvider<Client> dataProvider;
    private UI ui;

    // Search form fields
    private TextField clientIdFilter;
    private TextField nameFilter;
    private TextField emailFilter;
    private ComboBox<String> statusFilter;
    private DatePicker creationDateFilter;

    public ClientManagement() {
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
        clientIdFilter = new TextField();
        clientIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        clientIdFilter.setClearButtonVisible(true);

        nameFilter = new TextField();
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.setClearButtonVisible(true);

        emailFilter = new TextField();
        emailFilter.setValueChangeMode(ValueChangeMode.EAGER);
        emailFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("Active", "Inactive", "Suspended");
        statusFilter.setClearButtonVisible(true);

        creationDateFilter = new DatePicker();
        creationDateFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> applyFilter());

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> clearFilter());

        Button createClientButton = UIUtils.createSuccessButton("Create Client");
        createClientButton.addClickListener(e -> openCreateClientDialog());

        // Create a wrapper for search and clear buttons (right side)
        HorizontalLayout rightButtons = new HorizontalLayout(searchButton, clearButton);
        rightButtons.setSpacing(true);

        // Create button layout with Create Client on left and search/clear on right
        HorizontalLayout buttonLayout = new HorizontalLayout(createClientButton, rightButtons);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(clientIdFilter, "Client ID");
        formLayout.addFormItem(nameFilter, "Name");
        formLayout.addFormItem(emailFilter, "Email");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(creationDateFilter, "Creation Date After");

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

    private Grid<Client> createGrid() {
        grid = new Grid<>();
        grid.addThemeName("mobile");

        grid.setId("clients");
        grid.setSizeFull();

        // Configure grid columns
        grid.addColumn(Client::getClientId)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("Client ID")
                .setSortable(true);
        grid.addColumn(Client::getName)
                .setAutoWidth(true)
                .setHeader("Name")
                .setSortable(true);
        grid.addColumn(Client::getEmail)
                .setAutoWidth(true)
                .setHeader("Email")
                .setSortable(true);
        grid.addColumn(Client::getPhone)
                .setAutoWidth(true)
                .setHeader("Phone")
                .setSortable(true);
        grid.addColumn(Client::getType)
                .setAutoWidth(true)
                .setHeader("Type")
                .setSortable(true);
        grid.addColumn(new ComponentRenderer<>(this::createActive))
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Status")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new ComponentRenderer<>(this::createDate))
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Creation Date")
                .setTextAlign(ColumnTextAlign.END);

        // Add Actions column with view and delete buttons
        grid.addColumn(new ComponentRenderer<>(this::createActionButtons))
                .setHeader("Actions")
                .setWidth("150px")
                .setFlexGrow(0)
                .setTextAlign(ColumnTextAlign.CENTER);

        // Initialize with data provider
        dataProvider = DataProvider.ofCollection(getMockClients());
        grid.setDataProvider(dataProvider);

        return grid;
    }

    private Component createActive(Client client) {
        Icon icon;
        if (client.isActive()) {
            icon = UIUtils.createPrimaryIcon(VaadinIcon.CHECK);
        } else if ("Suspended".equals(client.getStatus())) {
            icon = UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.PAUSE);
        } else {
            icon = UIUtils.createDisabledIcon(VaadinIcon.CLOSE);
        }
        return icon;
    }

    private Component createDate(Client client) {
        return new Span(UIUtils.formatDate(client.getDateCreated().toLocalDate()));
    }

    private Component createActionButtons(Client client) {
        // Create layout for buttons
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        // Create view details button with eye icon
        Button viewDetailsButton = UIUtils.createButton(VaadinIcon.EYE);
        viewDetailsButton.addClickListener(e -> showDetails(client));
        viewDetailsButton.getElement().getThemeList().add("small");
        viewDetailsButton.getElement().getThemeList().add("tertiary");
        viewDetailsButton.getElement().setAttribute("title", "View Details");
        viewDetailsButton.getStyle().set("cursor", "pointer");

        // Create delete button with trash icon
        Button deleteButton = UIUtils.createButton(VaadinIcon.TRASH);
        deleteButton.addClickListener(e -> deleteClient(client));
        deleteButton.getElement().getThemeList().add("small");
        deleteButton.getElement().getThemeList().add("error");
        deleteButton.getElement().getThemeList().add("tertiary");
        deleteButton.getElement().setAttribute("title", "Delete");
        deleteButton.getStyle().set("cursor", "pointer");

        layout.add(viewDetailsButton, deleteButton);
        return layout;
    }

    private void showDetails(Client client) {
        ClientDetails clientDetails = new ClientDetails(client);
        clientDetails.open();
    }

    private void deleteClient(Client client) {
        // This would be implemented to delete the client
        System.out.println("[DEBUG_LOG] Delete client: " + client.getClientId());

        // For demo purposes, remove from the data provider
        dataProvider.getItems().remove(client);
        dataProvider.refreshAll();

        UIUtils.showNotification("Client " + client.getClientId() + " deleted.");
    }

    private void filter() {
        // Default filter - show all
        dataProvider.clearFilters();
    }

    private void applyFilter() {
        dataProvider.clearFilters();

        // Apply client ID filter if not empty
        if (clientIdFilter.getValue() != null && !clientIdFilter.getValue().isEmpty()) {
            String clientIdFilterValue = clientIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(client -> 
                client.getClientId().toLowerCase().contains(clientIdFilterValue));
        }

        // Apply name filter if not empty
        if (nameFilter.getValue() != null && !nameFilter.getValue().isEmpty()) {
            String nameFilterValue = nameFilter.getValue().toLowerCase();
            dataProvider.addFilter(client -> 
                client.getName() != null &&
                client.getName().toLowerCase().contains(nameFilterValue));
        }

        // Apply email filter if not empty
        if (emailFilter.getValue() != null && !emailFilter.getValue().isEmpty()) {
            String emailFilterValue = emailFilter.getValue().toLowerCase();
            dataProvider.addFilter(client -> 
                client.getEmail() != null &&
                client.getEmail().toLowerCase().contains(emailFilterValue));
        }

        // Apply status filter if selected
        if (statusFilter.getValue() != null) {
            String statusFilterValue = statusFilter.getValue();
            dataProvider.addFilter(client -> 
                statusFilterValue.equals(client.getStatus()));
        }

        // Apply creation date filter if selected
        if (creationDateFilter.getValue() != null) {
            LocalDate filterDate = creationDateFilter.getValue();
            dataProvider.addFilter(client -> 
                client.getDateCreated() != null && 
                !client.getDateCreated().toLocalDate().isBefore(filterDate));
        }
    }

    private void clearFilter() {
        // Clear all filter fields
        clientIdFilter.clear();
        nameFilter.clear();
        emailFilter.clear();
        statusFilter.clear();
        creationDateFilter.clear();

        // Reset filters
        dataProvider.clearFilters();
    }

    private void openCreateClientDialog() {
        // This would be implemented to open a dialog for creating a new client
        System.out.println("[DEBUG_LOG] Create client dialog would open here");
        UIUtils.showNotification("Create client functionality would be implemented here.");
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
        List<Grid.Column<Client>> columns = grid.getColumns();

        // "Desktop" columns
        for (Grid.Column<Client> column : columns) {
            column.setVisible(!mobile);
        }
    }

    // Mock data for the grid
    private List<Client> getMockClients() {
        List<Client> clients = new ArrayList<>();

        clients.add(new Client("CLI001", "John Smith", "john.smith@example.com", "+1-555-123-4567", "123 Main St, New York, NY 10001", "Individual", "Active", LocalDateTime.now().minusDays(30), LocalDateTime.now().minusDays(5)));
        clients.add(new Client("CLI002", "Acme Corporation", "contact@acmecorp.com", "+1-555-987-6543", "456 Business Ave, Chicago, IL 60601", "Corporate", "Active", LocalDateTime.now().minusDays(25), LocalDateTime.now().minusDays(4)));
        clients.add(new Client("CLI003", "Maria Garcia", "maria.garcia@example.com", "+34-91-123-4567", "Calle Mayor 10, Madrid, Spain", "Individual", "Inactive", LocalDateTime.now().minusDays(20), LocalDateTime.now().minusDays(3)));
        clients.add(new Client("CLI004", "Tech Innovations Ltd", "info@techinnovations.co.uk", "+44-20-7946-0958", "10 Innovation Way, London, UK", "Corporate", "Active", LocalDateTime.now().minusDays(15), LocalDateTime.now().minusDays(2)));
        clients.add(new Client("CLI005", "Hiroshi Tanaka", "h.tanaka@example.jp", "+81-3-1234-5678", "1-1-1 Shibuya, Tokyo, Japan", "Individual", "Suspended", LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1)));
        clients.add(new Client("CLI006", "Global Traders Inc", "contact@globaltraders.com", "+1-555-444-3333", "789 Trade Blvd, Miami, FL 33101", "Corporate", "Active", LocalDateTime.now().minusDays(5), LocalDateTime.now()));
        clients.add(new Client("CLI007", "Sophie Dubois", "sophie.dubois@example.fr", "+33-1-2345-6789", "15 Rue de la Paix, Paris, France", "Individual", "Active", LocalDateTime.now().minusDays(3), LocalDateTime.now()));
        clients.add(new Client("CLI008", "Green Energy Solutions", "info@greenenergy.de", "+49-30-1234-5678", "Energiestraße 10, Berlin, Germany", "Corporate", "Inactive", LocalDateTime.now().minusDays(2), LocalDateTime.now()));
        clients.add(new Client("CLI009", "Carlos Rodriguez", "c.rodriguez@example.mx", "+52-55-1234-5678", "Av. Reforma 222, Mexico City, Mexico", "Individual", "Active", LocalDateTime.now().minusDays(1), LocalDateTime.now()));
        clients.add(new Client("CLI010", "Australian Mining Ltd", "contact@ausmine.com.au", "+61-2-9876-5432", "42 Mining Road, Sydney, Australia", "Corporate", "Active", LocalDateTime.now(), LocalDateTime.now()));

        return clients;
    }

    // Client model class
    public static class Client {
        private String clientId;
        private String name;
        private String email;
        private String phone;
        private String address;
        private String type;
        private String status;
        private LocalDateTime dateCreated;
        private LocalDateTime dateUpdated;

        public Client(String clientId, String name, String email, String phone, 
                    String address, String type, String status, 
                    LocalDateTime dateCreated, LocalDateTime dateUpdated) {
            this.clientId = clientId;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.type = type;
            this.status = status;
            this.dateCreated = dateCreated;
            this.dateUpdated = dateUpdated;
        }

        public String getClientId() {
            return clientId;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }

        public String getType() {
            return type;
        }

        public String getStatus() {
            return status;
        }

        public boolean isActive() {
            return "Active".equals(status);
        }

        public LocalDateTime getDateCreated() {
            return dateCreated;
        }

        public LocalDateTime getDateUpdated() {
            return dateUpdated;
        }
    }
}
