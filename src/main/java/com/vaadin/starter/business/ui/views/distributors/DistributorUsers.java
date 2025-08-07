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
import com.vaadin.starter.business.ui.util.IconSize;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Route(value = "distributors/users", layout = MainLayout.class)
@PageTitle(NavigationConstants.DISTRIBUTOR_USERS)
public class DistributorUsers extends ViewFrame {

    public static final int MOBILE_BREAKPOINT = 480;
    private Grid<DistributorUser> grid;
    private Registration resizeListener;
    private ListDataProvider<DistributorUser> dataProvider;
    private UI ui;

    // Search form fields
    private TextField usernameFilter;
    private TextField emailFilter;
    private ComboBox<String> roleFilter;
    private ComboBox<String> statusFilter;
    private DatePicker creationDateFilter;

    public DistributorUsers() {
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
        usernameFilter = new TextField();
        usernameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        usernameFilter.setClearButtonVisible(true);

        emailFilter = new TextField();
        emailFilter.setValueChangeMode(ValueChangeMode.EAGER);
        emailFilter.setClearButtonVisible(true);

        roleFilter = new ComboBox<>();
        roleFilter.setItems("Admin", "Manager", "Operator", "Viewer");
        roleFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("Active", "Inactive", "Locked");
        statusFilter.setClearButtonVisible(true);

        creationDateFilter = new DatePicker();
        creationDateFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> applyFilter());

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> clearFilter());

        Button createUserButton = UIUtils.createSuccessButton("Create User");
        createUserButton.addClickListener(e -> openCreateUserDialog());

        // Create a wrapper for search and clear buttons (right side)
        HorizontalLayout rightButtons = new HorizontalLayout(searchButton, clearButton);
        rightButtons.setSpacing(true);

        // Create button layout with Create User on left and search/clear on right
        HorizontalLayout buttonLayout = new HorizontalLayout(createUserButton, rightButtons);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(usernameFilter, "Username");
        formLayout.addFormItem(emailFilter, "Email");
        formLayout.addFormItem(roleFilter, "Role");
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

    private Grid<DistributorUser> createGrid() {
        grid = new Grid<>();
        grid.addThemeName("mobile");

        grid.setId("distributor-users");
        grid.setSizeFull();

        // Configure grid columns
        grid.addColumn(DistributorUser::getUsername)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("Username")
                .setSortable(true);
        grid.addColumn(DistributorUser::getFullName)
                .setAutoWidth(true)
                .setHeader("Full Name")
                .setSortable(true);
        grid.addColumn(DistributorUser::getEmail)
                .setAutoWidth(true)
                .setHeader("Email")
                .setSortable(true);
        grid.addColumn(DistributorUser::getRole)
                .setAutoWidth(true)
                .setHeader("Role")
                .setSortable(true);
        grid.addColumn(DistributorUser::getDistributorName)
                .setAutoWidth(true)
                .setHeader("Distributor")
                .setSortable(true);
        grid.addColumn(new ComponentRenderer<>(this::createStatusComponent))
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
        dataProvider = DataProvider.ofCollection(getMockUsers());
        grid.setDataProvider(dataProvider);

        return grid;
    }

    private Component createStatusComponent(DistributorUser user) {
        Icon icon;
        String status = user.getStatus();

        if ("Active".equals(status)) {
            icon = UIUtils.createPrimaryIcon(VaadinIcon.CHECK);
        } else if ("Locked".equals(status)) {
            icon = UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.LOCK);
        } else {
            icon = UIUtils.createDisabledIcon(VaadinIcon.CLOSE);
        }
        return icon;
    }

    private Component createDate(DistributorUser user) {
        return new Span(UIUtils.formatDate(user.getDateCreated().toLocalDate()));
    }

    private Component createActionButtons(DistributorUser user) {
        // Create layout for buttons
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        // Create view details button with eye icon
        Button viewDetailsButton = UIUtils.createButton(VaadinIcon.EYE);
        viewDetailsButton.addClickListener(e -> showDetails(user));
        viewDetailsButton.getElement().getThemeList().add("small");
        viewDetailsButton.getElement().getThemeList().add("tertiary");
        viewDetailsButton.getElement().setAttribute("title", "View Details");
        viewDetailsButton.getStyle().set("cursor", "pointer");

        // Create delete button with trash icon
        Button deleteButton = UIUtils.createButton(VaadinIcon.TRASH);
        deleteButton.addClickListener(e -> deleteUser(user));
        deleteButton.getElement().getThemeList().add("small");
        deleteButton.getElement().getThemeList().add("error");
        deleteButton.getElement().getThemeList().add("tertiary");
        deleteButton.getElement().setAttribute("title", "Delete");
        deleteButton.getStyle().set("cursor", "pointer");

        layout.add(viewDetailsButton, deleteButton);
        return layout;
    }

    private void showDetails(DistributorUser user) {
        DistributorUserDetails userDetails = new DistributorUserDetails(user);
        userDetails.open();
    }

    private void deleteUser(DistributorUser user) {
        // This would be implemented to delete the user
        System.out.println("[DEBUG_LOG] Delete user: " + user.getUsername());

        // For demo purposes, remove from the data provider
        dataProvider.getItems().remove(user);
        dataProvider.refreshAll();

        UIUtils.showNotification("User " + user.getUsername() + " deleted.");
    }

    private void filter() {
        // Default filter - show all
        dataProvider.clearFilters();
    }

    private void applyFilter() {
        dataProvider.clearFilters();

        // Apply username filter if not empty
        if (usernameFilter.getValue() != null && !usernameFilter.getValue().isEmpty()) {
            String usernameFilterValue = usernameFilter.getValue().toLowerCase();
            dataProvider.addFilter(user -> 
                user.getUsername().toLowerCase().contains(usernameFilterValue));
        }

        // Apply email filter if not empty
        if (emailFilter.getValue() != null && !emailFilter.getValue().isEmpty()) {
            String emailFilterValue = emailFilter.getValue().toLowerCase();
            dataProvider.addFilter(user -> 
                user.getEmail() != null &&
                user.getEmail().toLowerCase().contains(emailFilterValue));
        }

        // Apply role filter if selected
        if (roleFilter.getValue() != null) {
            String roleFilterValue = roleFilter.getValue();
            dataProvider.addFilter(user -> 
                roleFilterValue.equals(user.getRole()));
        }

        // Apply status filter if selected
        if (statusFilter.getValue() != null) {
            String statusFilterValue = statusFilter.getValue();
            dataProvider.addFilter(user -> 
                statusFilterValue.equals(user.getStatus()));
        }

        // Apply creation date filter if selected
        if (creationDateFilter.getValue() != null) {
            LocalDate filterDate = creationDateFilter.getValue();
            dataProvider.addFilter(user -> 
                user.getDateCreated() != null && 
                !user.getDateCreated().toLocalDate().isBefore(filterDate));
        }
    }

    private void clearFilter() {
        // Clear all filter fields
        usernameFilter.clear();
        emailFilter.clear();
        roleFilter.clear();
        statusFilter.clear();
        creationDateFilter.clear();

        // Reset filters
        dataProvider.clearFilters();
    }

    private void openCreateUserDialog() {
        // This would be implemented to open a dialog for creating a new user
        System.out.println("[DEBUG_LOG] Create user dialog would open here");
        UIUtils.showNotification("Create user functionality would be implemented here.");
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
        List<Grid.Column<DistributorUser>> columns = grid.getColumns();

        // "Desktop" columns
        for (Grid.Column<DistributorUser> column : columns) {
            column.setVisible(!mobile);
        }
    }

    // Mock data for the grid
    private List<DistributorUser> getMockUsers() {
        List<DistributorUser> users = new ArrayList<>();

        users.add(new DistributorUser(1L, "jsmith", "John Smith", "john.smith@globaldist.com", "Admin", "Global Distribution Inc.", "Active", LocalDateTime.now().minusDays(30), LocalDateTime.now().minusDays(5)));
        users.add(new DistributorUser(2L, "mdubois", "Marie Dubois", "marie@europelogistics.eu", "Manager", "European Logistics Ltd.", "Active", LocalDateTime.now().minusDays(25), LocalDateTime.now().minusDays(4)));
        users.add(new DistributorUser(3L, "lwong", "Lee Wong", "lwong@asiapacific.com", "Operator", "Asia Pacific Supplies", "Inactive", LocalDateTime.now().minusDays(20), LocalDateTime.now().minusDays(3)));
        users.add(new DistributorUser(4L, "crodriguez", "Carlos Rodriguez", "carlos@latamexports.com", "Admin", "Latin American Exports", "Active", LocalDateTime.now().minusDays(15), LocalDateTime.now().minusDays(2)));
        users.add(new DistributorUser(5L, "aokafor", "Amara Okafor", "amara@africantp.com", "Manager", "African Trade Partners", "Active", LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1)));
        users.add(new DistributorUser(6L, "aalfarsi", "Ahmed Al-Farsi", "ahmed@meventures.ae", "Viewer", "Middle East Ventures", "Locked", LocalDateTime.now().minusDays(5), LocalDateTime.now()));
        users.add(new DistributorUser(7L, "ejohansson", "Erik Johansson", "erik@nordicsupplies.no", "Operator", "Nordic Supplies AS", "Active", LocalDateTime.now().minusDays(3), LocalDateTime.now()));
        users.add(new DistributorUser(8L, "sjohnson", "Sarah Johnson", "sarah@oceaniadist.com.au", "Admin", "Oceania Distribution Pty", "Active", LocalDateTime.now().minusDays(2), LocalDateTime.now()));
        users.add(new DistributorUser(9L, "mthompson", "Michael Thompson", "michael@canadianlogistics.ca", "Viewer", "Canadian Logistics Corp", "Inactive", LocalDateTime.now().minusDays(1), LocalDateTime.now()));
        users.add(new DistributorUser(10L, "ewilson", "Emma Wilson", "emma@ukdistribution.co.uk", "Manager", "UK Distribution Services", "Active", LocalDateTime.now(), LocalDateTime.now()));

        return users;
    }

    // DistributorUser model class
    public static class DistributorUser {
        private Long userId;
        private String username;
        private String fullName;
        private String email;
        private String role;
        private String distributorName;
        private String status;
        private LocalDateTime dateCreated;
        private LocalDateTime dateUpdated;

        public DistributorUser(Long userId, String username, String fullName, String email, 
                            String role, String distributorName, String status, 
                            LocalDateTime dateCreated, LocalDateTime dateUpdated) {
            this.userId = userId;
            this.username = username;
            this.fullName = fullName;
            this.email = email;
            this.role = role;
            this.distributorName = distributorName;
            this.status = status;
            this.dateCreated = dateCreated;
            this.dateUpdated = dateUpdated;
        }

        public Long getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public String getFullName() {
            return fullName;
        }

        public String getEmail() {
            return email;
        }

        public String getRole() {
            return role;
        }

        public String getDistributorName() {
            return distributorName;
        }

        public String getStatus() {
            return status;
        }

        public LocalDateTime getDateCreated() {
            return dateCreated;
        }

        public LocalDateTime getDateUpdated() {
            return dateUpdated;
        }
    }
}
