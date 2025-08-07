package com.vaadin.starter.business.ui.views.distributors;

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
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Route(value = "distributors/management", layout = MainLayout.class)
@PageTitle(NavigationConstants.DISTRIBUTOR_MANAGEMENT)
public class DistributorManagement extends ViewFrame {

    public static final int MOBILE_BREAKPOINT = 480;
    private Grid<Distributor> grid;
    private Registration resizeListener;
    private ListDataProvider<Distributor> dataProvider;
    private UI ui;

    // Search form fields
    private TextField distributorCodeFilter;
    private TextField nameFilter;
    private TextField contactPersonFilter;
    private ComboBox<String> statusFilter;
    private DatePicker creationDateFilter;

    public DistributorManagement() {
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
        distributorCodeFilter = new TextField();
        distributorCodeFilter.setValueChangeMode(ValueChangeMode.EAGER);
        distributorCodeFilter.setClearButtonVisible(true);

        nameFilter = new TextField();
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.setClearButtonVisible(true);

        contactPersonFilter = new TextField();
        contactPersonFilter.setValueChangeMode(ValueChangeMode.EAGER);
        contactPersonFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("Active", "Inactive");
        statusFilter.setClearButtonVisible(true);

        creationDateFilter = new DatePicker();
        creationDateFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> applyFilter());

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> clearFilter());

        Button createDistributorButton = UIUtils.createSuccessButton("Create Distributor");
        createDistributorButton.addClickListener(e -> openCreateDistributorDialog());

        // Create a wrapper for search and clear buttons (right side)
        HorizontalLayout rightButtons = new HorizontalLayout(searchButton, clearButton);
        rightButtons.setSpacing(true);

        // Create button layout with Create Distributor on left and search/clear on right
        HorizontalLayout buttonLayout = new HorizontalLayout(createDistributorButton, rightButtons);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(distributorCodeFilter, "Distributor Code");
        formLayout.addFormItem(nameFilter, "Name");
        formLayout.addFormItem(contactPersonFilter, "Contact Person");
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

    private Grid<Distributor> createGrid() {
        grid = new Grid<>();
        grid.addThemeName("mobile");

        grid.setId("distributors");
        grid.setSizeFull();

        // Configure grid columns
        grid.addColumn(Distributor::getDistributorCode)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("Distributor Code")
                .setSortable(true);
        grid.addColumn(Distributor::getName)
                .setAutoWidth(true)
                .setHeader("Name")
                .setSortable(true);
        grid.addColumn(Distributor::getContactPerson)
                .setAutoWidth(true)
                .setHeader("Contact Person")
                .setSortable(true);
        grid.addColumn(Distributor::getEmail)
                .setAutoWidth(true)
                .setHeader("Email")
                .setSortable(true);
        grid.addColumn(Distributor::getPhone)
                .setAutoWidth(true)
                .setHeader("Phone")
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
        dataProvider = DataProvider.ofCollection(getMockDistributors());
        grid.setDataProvider(dataProvider);

        return grid;
    }

    private Component createActive(Distributor distributor) {
        Icon icon;
        if (distributor.isActive()) {
            icon = UIUtils.createPrimaryIcon(VaadinIcon.CHECK);
        } else {
            icon = UIUtils.createDisabledIcon(VaadinIcon.CLOSE);
        }
        return icon;
    }

    private Component createDate(Distributor distributor) {
        return new Span(UIUtils.formatDate(distributor.getDateCreated().toLocalDate()));
    }

    private Component createActionButtons(Distributor distributor) {
        // Create layout for buttons
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        // Create view details button with eye icon
        Button viewDetailsButton = UIUtils.createButton(VaadinIcon.EYE);
        viewDetailsButton.addClickListener(e -> showDetails(distributor));
        viewDetailsButton.getElement().getThemeList().add("small");
        viewDetailsButton.getElement().getThemeList().add("tertiary");
        viewDetailsButton.getElement().setAttribute("title", "View Details");
        viewDetailsButton.getStyle().set("cursor", "pointer");

        // Create delete button with trash icon
        Button deleteButton = UIUtils.createButton(VaadinIcon.TRASH);
        deleteButton.addClickListener(e -> deleteDistributor(distributor));
        deleteButton.getElement().getThemeList().add("small");
        deleteButton.getElement().getThemeList().add("error");
        deleteButton.getElement().getThemeList().add("tertiary");
        deleteButton.getElement().setAttribute("title", "Delete");
        deleteButton.getStyle().set("cursor", "pointer");

        layout.add(viewDetailsButton, deleteButton);
        return layout;
    }

    private void showDetails(Distributor distributor) {
        DistributorDetails distributorDetails = new DistributorDetails(distributor);
        distributorDetails.open();
    }

    private void deleteDistributor(Distributor distributor) {
        // This would be implemented to delete the distributor
        System.out.println("[DEBUG_LOG] Delete distributor: " + distributor.getDistributorCode());

        // For demo purposes, remove from the data provider
        dataProvider.getItems().remove(distributor);
        dataProvider.refreshAll();

        UIUtils.showNotification("Distributor " + distributor.getDistributorCode() + " deleted.");
    }

    private void filter() {
        // Default filter - show all
        dataProvider.clearFilters();
    }

    private void applyFilter() {
        dataProvider.clearFilters();

        // Apply distributor code filter if not empty
        if (distributorCodeFilter.getValue() != null && !distributorCodeFilter.getValue().isEmpty()) {
            String distributorCodeFilterValue = distributorCodeFilter.getValue().toLowerCase();
            dataProvider.addFilter(distributor -> 
                distributor.getDistributorCode().toLowerCase().contains(distributorCodeFilterValue));
        }

        // Apply name filter if not empty
        if (nameFilter.getValue() != null && !nameFilter.getValue().isEmpty()) {
            String nameFilterValue = nameFilter.getValue().toLowerCase();
            dataProvider.addFilter(distributor -> 
                distributor.getName() != null &&
                distributor.getName().toLowerCase().contains(nameFilterValue));
        }

        // Apply contact person filter if not empty
        if (contactPersonFilter.getValue() != null && !contactPersonFilter.getValue().isEmpty()) {
            String contactPersonFilterValue = contactPersonFilter.getValue().toLowerCase();
            dataProvider.addFilter(distributor -> 
                distributor.getContactPerson() != null &&
                distributor.getContactPerson().toLowerCase().contains(contactPersonFilterValue));
        }

        // Apply status filter if selected
        if (statusFilter.getValue() != null) {
            boolean isActive = "Active".equals(statusFilter.getValue());
            dataProvider.addFilter(distributor -> 
                distributor.isActive() == isActive);
        }

        // Apply creation date filter if selected
        if (creationDateFilter.getValue() != null) {
            LocalDate filterDate = creationDateFilter.getValue();
            dataProvider.addFilter(distributor -> 
                distributor.getDateCreated() != null && 
                !distributor.getDateCreated().toLocalDate().isBefore(filterDate));
        }
    }

    private void clearFilter() {
        // Clear all filter fields
        distributorCodeFilter.clear();
        nameFilter.clear();
        contactPersonFilter.clear();
        statusFilter.clear();
        creationDateFilter.clear();

        // Reset filters
        dataProvider.clearFilters();
    }

    private void openCreateDistributorDialog() {
        // This would be implemented to open a dialog for creating a new distributor
        System.out.println("[DEBUG_LOG] Create distributor dialog would open here");
        UIUtils.showNotification("Create distributor functionality would be implemented here.");
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
        List<Grid.Column<Distributor>> columns = grid.getColumns();

        // "Desktop" columns
        for (Grid.Column<Distributor> column : columns) {
            column.setVisible(!mobile);
        }
    }

    // Mock data for the grid
    private List<Distributor> getMockDistributors() {
        List<Distributor> distributors = new ArrayList<>();

        distributors.add(new Distributor(1L, "DIST001", "Global Distribution Inc.", "John Smith", "john.smith@globaldist.com", "+1-555-123-4567", "123 Main St, New York, NY 10001", true, LocalDateTime.now().minusDays(30), LocalDateTime.now().minusDays(5)));
        distributors.add(new Distributor(2L, "DIST002", "European Logistics Ltd.", "Marie Dubois", "marie@europelogistics.eu", "+33-1-2345-6789", "45 Rue de Paris, 75001 Paris, France", true, LocalDateTime.now().minusDays(25), LocalDateTime.now().minusDays(4)));
        distributors.add(new Distributor(3L, "DIST003", "Asia Pacific Supplies", "Lee Wong", "lwong@asiapacific.com", "+65-9876-5432", "88 Orchard Road, Singapore 238823", false, LocalDateTime.now().minusDays(20), LocalDateTime.now().minusDays(3)));
        distributors.add(new Distributor(4L, "DIST004", "Latin American Exports", "Carlos Rodriguez", "carlos@latamexports.com", "+52-55-1234-5678", "Av. Reforma 222, Mexico City, Mexico", true, LocalDateTime.now().minusDays(15), LocalDateTime.now().minusDays(2)));
        distributors.add(new Distributor(5L, "DIST005", "African Trade Partners", "Amara Okafor", "amara@africantp.com", "+27-11-987-6543", "25 Nelson Mandela Blvd, Johannesburg, South Africa", true, LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1)));
        distributors.add(new Distributor(6L, "DIST006", "Middle East Ventures", "Ahmed Al-Farsi", "ahmed@meventures.ae", "+971-4-123-4567", "Dubai Marina Tower, Dubai, UAE", false, LocalDateTime.now().minusDays(5), LocalDateTime.now()));
        distributors.add(new Distributor(7L, "DIST007", "Nordic Supplies AS", "Erik Johansson", "erik@nordicsupplies.no", "+47-22-123-456", "Aker Brygge 10, Oslo, Norway", true, LocalDateTime.now().minusDays(3), LocalDateTime.now()));
        distributors.add(new Distributor(8L, "DIST008", "Oceania Distribution Pty", "Sarah Johnson", "sarah@oceaniadist.com.au", "+61-2-9876-5432", "42 Circular Quay, Sydney, Australia", true, LocalDateTime.now().minusDays(2), LocalDateTime.now()));
        distributors.add(new Distributor(9L, "DIST009", "Canadian Logistics Corp", "Michael Thompson", "michael@canadianlogistics.ca", "+1-416-123-4567", "200 Bay Street, Toronto, ON, Canada", false, LocalDateTime.now().minusDays(1), LocalDateTime.now()));
        distributors.add(new Distributor(10L, "DIST010", "UK Distribution Services", "Emma Wilson", "emma@ukdistribution.co.uk", "+44-20-7946-0958", "10 Liverpool Street, London, UK", true, LocalDateTime.now(), LocalDateTime.now()));

        return distributors;
    }

    // Distributor model class
    public static class Distributor {
        private Long distributorId;
        private String distributorCode;
        private String name;
        private String contactPerson;
        private String email;
        private String phone;
        private String address;
        private Boolean isActive;
        private LocalDateTime dateCreated;
        private LocalDateTime dateUpdated;

        public Distributor(Long distributorId, String distributorCode, String name, String contactPerson, 
                        String email, String phone, String address, Boolean isActive, 
                        LocalDateTime dateCreated, LocalDateTime dateUpdated) {
            this.distributorId = distributorId;
            this.distributorCode = distributorCode;
            this.name = name;
            this.contactPerson = contactPerson;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.isActive = isActive;
            this.dateCreated = dateCreated;
            this.dateUpdated = dateUpdated;
        }

        public Long getDistributorId() {
            return distributorId;
        }

        public String getDistributorCode() {
            return distributorCode;
        }

        public String getName() {
            return name;
        }

        public String getContactPerson() {
            return contactPerson;
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

        public Boolean getIsActive() {
            return isActive;
        }

        public boolean isActive() {
            return isActive != null && isActive;
        }

        public LocalDateTime getDateCreated() {
            return dateCreated;
        }

        public LocalDateTime getDateUpdated() {
            return dateUpdated;
        }
    }
}
