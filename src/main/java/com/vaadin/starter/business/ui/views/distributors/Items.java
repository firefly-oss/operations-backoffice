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

@Route(value = "distributors/items", layout = MainLayout.class)
@PageTitle(NavigationConstants.ITEMS)
public class Items extends ViewFrame {

    public static final int MOBILE_BREAKPOINT = 480;
    private Grid<Item> grid;
    private Registration resizeListener;
    private ListDataProvider<Item> dataProvider;
    private UI ui;

    // Search form fields
    private TextField itemCodeFilter;
    private TextField nameFilter;
    private ComboBox<String> categoryFilter;
    private ComboBox<String> statusFilter;
    private DatePicker creationDateFilter;

    public Items() {
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
        itemCodeFilter = new TextField();
        itemCodeFilter.setValueChangeMode(ValueChangeMode.EAGER);
        itemCodeFilter.setClearButtonVisible(true);

        nameFilter = new TextField();
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.setClearButtonVisible(true);

        categoryFilter = new ComboBox<>();
        categoryFilter.setItems("Electronics", "Clothing", "Food", "Books", "Other");
        categoryFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("Available", "Out of Stock", "Discontinued");
        statusFilter.setClearButtonVisible(true);

        creationDateFilter = new DatePicker();
        creationDateFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> applyFilter());

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> clearFilter());

        Button createItemButton = UIUtils.createSuccessButton("Create Item");
        createItemButton.addClickListener(e -> openCreateItemDialog());

        // Create a wrapper for search and clear buttons (right side)
        HorizontalLayout rightButtons = new HorizontalLayout(searchButton, clearButton);
        rightButtons.setSpacing(true);

        // Create button layout with Create Item on left and search/clear on right
        HorizontalLayout buttonLayout = new HorizontalLayout(createItemButton, rightButtons);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(itemCodeFilter, "Item Code");
        formLayout.addFormItem(nameFilter, "Name");
        formLayout.addFormItem(categoryFilter, "Category");
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

    private Grid<Item> createGrid() {
        grid = new Grid<>();
        grid.addThemeName("mobile");

        grid.setId("items");
        grid.setSizeFull();

        // Configure grid columns
        grid.addColumn(Item::getItemCode)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("Item Code")
                .setSortable(true);
        grid.addColumn(Item::getName)
                .setAutoWidth(true)
                .setHeader("Name")
                .setSortable(true);
        grid.addColumn(Item::getCategory)
                .setAutoWidth(true)
                .setHeader("Category")
                .setSortable(true);
        grid.addColumn(Item::getPrice)
                .setAutoWidth(true)
                .setHeader("Price")
                .setSortable(true);
        grid.addColumn(Item::getQuantity)
                .setAutoWidth(true)
                .setHeader("Quantity")
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
        dataProvider = DataProvider.ofCollection(getMockItems());
        grid.setDataProvider(dataProvider);

        return grid;
    }

    private Component createStatusComponent(Item item) {
        Icon icon;
        String status = item.getStatus();

        if ("Available".equals(status)) {
            icon = UIUtils.createPrimaryIcon(VaadinIcon.CHECK);
        } else if ("Out of Stock".equals(status)) {
            icon = UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, VaadinIcon.CLOCK);
        } else {
            icon = UIUtils.createDisabledIcon(VaadinIcon.CLOSE);
        }
        return icon;
    }

    private Component createDate(Item item) {
        return new Span(UIUtils.formatDate(item.getDateCreated().toLocalDate()));
    }

    private Component createActionButtons(Item item) {
        // Create layout for buttons
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        // Create view details button with eye icon
        Button viewDetailsButton = UIUtils.createPastelButton(VaadinIcon.EYE);
        viewDetailsButton.addClickListener(e -> showDetails(item));
        viewDetailsButton.getElement().setAttribute("title", "View Details");

        // Create delete button with trash icon
        Button deleteButton = UIUtils.createPastelErrorButton(VaadinIcon.TRASH);
        deleteButton.addClickListener(e -> deleteItem(item));
        deleteButton.getElement().setAttribute("title", "Delete");

        layout.add(viewDetailsButton, deleteButton);
        return layout;
    }

    private void showDetails(Item item) {
        ItemDetails itemDetails = new ItemDetails(item);
        itemDetails.open();
    }

    private void deleteItem(Item item) {
        // This would be implemented to delete the item
        System.out.println("[DEBUG_LOG] Delete item: " + item.getItemCode());

        // For demo purposes, remove from the data provider
        dataProvider.getItems().remove(item);
        dataProvider.refreshAll();

        UIUtils.showNotification("Item " + item.getItemCode() + " deleted.");
    }

    private void filter() {
        // Default filter - show all
        dataProvider.clearFilters();
    }

    private void applyFilter() {
        dataProvider.clearFilters();

        // Apply item code filter if not empty
        if (itemCodeFilter.getValue() != null && !itemCodeFilter.getValue().isEmpty()) {
            String itemCodeFilterValue = itemCodeFilter.getValue().toLowerCase();
            dataProvider.addFilter(item -> 
                item.getItemCode().toLowerCase().contains(itemCodeFilterValue));
        }

        // Apply name filter if not empty
        if (nameFilter.getValue() != null && !nameFilter.getValue().isEmpty()) {
            String nameFilterValue = nameFilter.getValue().toLowerCase();
            dataProvider.addFilter(item -> 
                item.getName() != null &&
                item.getName().toLowerCase().contains(nameFilterValue));
        }

        // Apply category filter if selected
        if (categoryFilter.getValue() != null) {
            String categoryFilterValue = categoryFilter.getValue();
            dataProvider.addFilter(item -> 
                categoryFilterValue.equals(item.getCategory()));
        }

        // Apply status filter if selected
        if (statusFilter.getValue() != null) {
            String statusFilterValue = statusFilter.getValue();
            dataProvider.addFilter(item -> 
                statusFilterValue.equals(item.getStatus()));
        }

        // Apply creation date filter if selected
        if (creationDateFilter.getValue() != null) {
            LocalDate filterDate = creationDateFilter.getValue();
            dataProvider.addFilter(item -> 
                item.getDateCreated() != null && 
                !item.getDateCreated().toLocalDate().isBefore(filterDate));
        }
    }

    private void clearFilter() {
        // Clear all filter fields
        itemCodeFilter.clear();
        nameFilter.clear();
        categoryFilter.clear();
        statusFilter.clear();
        creationDateFilter.clear();

        // Reset filters
        dataProvider.clearFilters();
    }

    private void openCreateItemDialog() {
        // This would be implemented to open a dialog for creating a new item
        System.out.println("[DEBUG_LOG] Create item dialog would open here");
        UIUtils.showNotification("Create item functionality would be implemented here.");
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
        List<Grid.Column<Item>> columns = grid.getColumns();

        // "Desktop" columns
        for (Grid.Column<Item> column : columns) {
            column.setVisible(!mobile);
        }
    }

    // Mock data for the grid
    private List<Item> getMockItems() {
        List<Item> items = new ArrayList<>();

        items.add(new Item(1L, "ITEM001", "Laptop", "Electronics", 1299.99, 25, "Available", LocalDateTime.now().minusDays(30), LocalDateTime.now().minusDays(5)));
        items.add(new Item(2L, "ITEM002", "T-Shirt", "Clothing", 19.99, 100, "Available", LocalDateTime.now().minusDays(25), LocalDateTime.now().minusDays(4)));
        items.add(new Item(3L, "ITEM003", "Chocolate Bar", "Food", 2.99, 0, "Out of Stock", LocalDateTime.now().minusDays(20), LocalDateTime.now().minusDays(3)));
        items.add(new Item(4L, "ITEM004", "Novel", "Books", 12.99, 50, "Available", LocalDateTime.now().minusDays(15), LocalDateTime.now().minusDays(2)));
        items.add(new Item(5L, "ITEM005", "Headphones", "Electronics", 89.99, 15, "Available", LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1)));
        items.add(new Item(6L, "ITEM006", "Desk Chair", "Furniture", 149.99, 0, "Out of Stock", LocalDateTime.now().minusDays(5), LocalDateTime.now()));
        items.add(new Item(7L, "ITEM007", "Coffee Maker", "Appliances", 79.99, 10, "Available", LocalDateTime.now().minusDays(3), LocalDateTime.now()));
        items.add(new Item(8L, "ITEM008", "Smartphone", "Electronics", 699.99, 30, "Available", LocalDateTime.now().minusDays(2), LocalDateTime.now()));
        items.add(new Item(9L, "ITEM009", "Vintage Jeans", "Clothing", 59.99, 0, "Discontinued", LocalDateTime.now().minusDays(1), LocalDateTime.now()));
        items.add(new Item(10L, "ITEM010", "Bluetooth Speaker", "Electronics", 49.99, 20, "Available", LocalDateTime.now(), LocalDateTime.now()));

        return items;
    }

    // Item model class
    public static class Item {
        private Long itemId;
        private String itemCode;
        private String name;
        private String category;
        private Double price;
        private Integer quantity;
        private String status;
        private LocalDateTime dateCreated;
        private LocalDateTime dateUpdated;

        public Item(Long itemId, String itemCode, String name, String category, 
                    Double price, Integer quantity, String status, 
                    LocalDateTime dateCreated, LocalDateTime dateUpdated) {
            this.itemId = itemId;
            this.itemCode = itemCode;
            this.name = name;
            this.category = category;
            this.price = price;
            this.quantity = quantity;
            this.status = status;
            this.dateCreated = dateCreated;
            this.dateUpdated = dateUpdated;
        }

        public Long getItemId() {
            return itemId;
        }

        public String getItemCode() {
            return itemCode;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public Double getPrice() {
            return price;
        }

        public Integer getQuantity() {
            return quantity;
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
