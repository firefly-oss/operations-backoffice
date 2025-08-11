package com.vaadin.starter.business.ui.views.distributors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;
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
import com.vaadin.starter.business.ui.views.distributors.DistributorManagement.Distributor;
import com.vaadin.starter.business.ui.views.distributors.Items.Item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Route(value = "distributors/dashboard", layout = MainLayout.class)
@PageTitle(NavigationConstants.DISTRIBUTOR_DASHBOARD)
public class DistributorDashboard extends ViewFrame implements HasUrlParameter<String> {

    private String distributorId;
    private Distributor distributor;
    private Div contentArea;

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {
        this.distributorId = parameter;
        // In a real application, you would fetch the distributor from a service
        // For this example, we'll use the mock distributor from DistributorManagement
        this.distributor = findDistributorById(distributorId);
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createHeader(), createDistributorDetails(), createTabs());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createDistributorDetails() {
        // Distributor details form
        FormLayout form = new FormLayout();
        form.addClassNames("distributor-details-form");
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("600px", 4,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP));

        // Distributor ID
        TextField distributorIdField = new TextField();
        distributorIdField.setValue(distributor.getDistributorId() != null ? distributor.getDistributorId().toString() : "");
        distributorIdField.setReadOnly(true);

        // Distributor Code
        TextField distributorCodeField = new TextField();
        distributorCodeField.setValue(distributor.getDistributorCode() != null ? distributor.getDistributorCode() : "");

        // Name
        TextField nameField = new TextField();
        nameField.setValue(distributor.getName() != null ? distributor.getName() : "");

        // Contact Person
        TextField contactPersonField = new TextField();
        contactPersonField.setValue(distributor.getContactPerson() != null ? distributor.getContactPerson() : "");

        // Email
        TextField emailField = new TextField();
        emailField.setValue(distributor.getEmail() != null ? distributor.getEmail() : "");

        // Phone
        TextField phoneField = new TextField();
        phoneField.setValue(distributor.getPhone() != null ? distributor.getPhone() : "");

        // Address
        TextArea addressField = new TextArea();
        addressField.setValue(distributor.getAddress() != null ? distributor.getAddress() : "");

        // Status
        ComboBox<String> statusField = new ComboBox<>();
        statusField.setItems("Active", "Inactive");
        statusField.setValue(distributor.isActive() ? "Active" : "Inactive");

        // Creation Date
        DatePicker creationDateField = new DatePicker();
        creationDateField.setValue(distributor.getDateCreated() != null ? distributor.getDateCreated().toLocalDate() : LocalDate.now());
        creationDateField.setReadOnly(true);

        // Update Date
        DatePicker updateDateField = new DatePicker();
        updateDateField.setValue(distributor.getDateUpdated() != null ? distributor.getDateUpdated().toLocalDate() : LocalDate.now());
        updateDateField.setReadOnly(true);

        // Add fields to form
        form.addFormItem(distributorIdField, "Distributor ID");
        form.addFormItem(distributorCodeField, "Distributor Code");
        form.addFormItem(nameField, "Name");
        form.addFormItem(contactPersonField, "Contact Person");
        form.addFormItem(emailField, "Email");
        form.addFormItem(phoneField, "Phone");
        form.addFormItem(addressField, "Address");
        form.addFormItem(statusField, "Status");
        form.addFormItem(creationDateField, "Creation Date");
        form.addFormItem(updateDateField, "Update Date");

        // Save button
        Button saveButton = UIUtils.createPrimaryButton("Save");
        saveButton.addClickListener(e -> {
            // In a real application, this would save the distributor data to the backend
            UIUtils.showNotification("Distributor details saved.");
        });

        // Create a container for the form with a title
        VerticalLayout container = new VerticalLayout();
        container.setPadding(true);
        container.setSpacing(true);

        H3 title = new H3("Distributor Details");
        container.add(title, form, saveButton);

        return container;
    }

    private Component createHeader() {
        // Create back button
        Button backButton = UIUtils.createTertiaryButton("Back", VaadinIcon.ARROW_LEFT);
        backButton.addClickListener(e -> navigateBack());
        backButton.getStyle().set("margin-right", "1rem");

        // Create header text
        H3 header = new H3("Distributor Dashboard: " + distributor.getName() + " (" + distributor.getDistributorCode() + ")");
        header.getStyle().set("margin", "0");

        // Create layout for header with back button
        HorizontalLayout headerLayout = new HorizontalLayout(backButton, header);
        headerLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        headerLayout.setPadding(true);
        headerLayout.setSpacing(true);

        return headerLayout;
    }

    private void navigateBack() {
        // Navigate back to the distributor management view
        getUI().ifPresent(ui -> ui.navigate("distributors/management"));
    }

    private Component createTabs() {
        Tab detailsTab = new Tab("Details");
        Tab productsTab = new Tab("Products");

        Tabs tabs = new Tabs(detailsTab, productsTab);
        tabs.getStyle().set("margin", "0 1rem");

        contentArea = new Div();
        contentArea.setSizeFull();
        contentArea.getStyle().set("padding", "1rem");

        // Show details tab by default
        showDetailsTab();

        tabs.addSelectedChangeListener(event -> {
            contentArea.removeAll();
            if (event.getSelectedTab().equals(detailsTab)) {
                showDetailsTab();
            } else if (event.getSelectedTab().equals(productsTab)) {
                showProductsTab();
            }
        });

        VerticalLayout tabsLayout = new VerticalLayout(tabs, contentArea);
        tabsLayout.setPadding(false);
        tabsLayout.setSpacing(false);
        tabsLayout.setSizeFull();
        return tabsLayout;
    }

    private void showDetailsTab() {
        VerticalLayout detailsLayout = new VerticalLayout();
        detailsLayout.setPadding(false);
        detailsLayout.setSpacing(true);
        detailsLayout.setSizeFull();

        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Status", distributor.isActive() ? "Active" : "Inactive", 
                distributor.isActive() ? VaadinIcon.CHECK : VaadinIcon.CLOSE));
        summaryCards.add(createSummaryCard("Products", "3", VaadinIcon.PACKAGE));
        summaryCards.add(createSummaryCard("Created", 
                distributor.getDateCreated() != null ? 
                distributor.getDateCreated().format(DateTimeFormatter.ofPattern("dd MMM yyyy")) : 
                "N/A", 
                VaadinIcon.CALENDAR));

        detailsLayout.add(summaryCards);

        // Add additional details if needed
        H4 additionalInfoTitle = new H4("Additional Information");
        detailsLayout.add(additionalInfoTitle);

        // Add a placeholder for additional information
        Span additionalInfo = new Span("No additional information available.");
        detailsLayout.add(additionalInfo);

        contentArea.add(detailsLayout);
    }

    private void showProductsTab() {
        VerticalLayout productsLayout = new VerticalLayout();
        productsLayout.setPadding(false);
        productsLayout.setSpacing(true);
        productsLayout.setSizeFull();

        // Add search field
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search products...");
        searchField.setWidthFull();
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setClearButtonVisible(true);

        // Add products grid
        Grid<DistributorItem> productsGrid = createProductsGrid();

        // Add search functionality
        searchField.addValueChangeListener(e -> {
            String searchTerm = e.getValue().toLowerCase();
            ((ListDataProvider<DistributorItem>) productsGrid.getDataProvider())
                .setFilter(item -> 
                    item.getItemCode().toLowerCase().contains(searchTerm) ||
                    item.getName().toLowerCase().contains(searchTerm) ||
                    item.getCategory().toLowerCase().contains(searchTerm)
                );
        });

        productsLayout.add(searchField, productsGrid);
        productsLayout.expand(productsGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button addProductButton = UIUtils.createPrimaryButton("Add Product");
        addProductButton.setIcon(VaadinIcon.PLUS.create());
        addProductButton.addClickListener(e -> openAddProductDialog(productsGrid));

        Button removeProductButton = UIUtils.createErrorButton("Remove Product");
        removeProductButton.setIcon(VaadinIcon.TRASH.create());
        removeProductButton.addClickListener(e -> removeSelectedProduct(productsGrid));

        actionButtons.add(addProductButton, removeProductButton);
        actionButtons.setSpacing(true);
        productsLayout.add(actionButtons);

        contentArea.add(productsLayout);
    }

    private Grid<DistributorItem> createProductsGrid() {
        Grid<DistributorItem> grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setHeight("400px");

        // Configure grid columns
        grid.addColumn(DistributorItem::getItemCode)
                .setHeader("Item Code")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(DistributorItem::getName)
                .setHeader("Name")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(DistributorItem::getCategory)
                .setHeader("Category")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(DistributorItem::getPrice)
                .setHeader("Price")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(DistributorItem::getQuantity)
                .setHeader("Quantity")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(DistributorItem::getStatus)
                .setHeader("Status")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(item -> {
                if (item.getDateAdded() != null) {
                    return item.getDateAdded().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
                }
                return "N/A";
            })
            .setHeader("Date Added")
            .setAutoWidth(true)
            .setSortable(true);

        // Initialize with data provider
        List<DistributorItem> distributorItems = getMockDistributorItems();
        ListDataProvider<DistributorItem> dataProvider = DataProvider.ofCollection(distributorItems);
        grid.setDataProvider(dataProvider);

        // Add row click listener to show product details
        grid.addItemClickListener(event -> {
            DistributorItem distributorItem = event.getItem();
            if (distributorItem != null) {
                showProductDetails(distributorItem.getItem());
            }
        });

        return grid;
    }

    private void openAddProductDialog(Grid<DistributorItem> productsGrid) {
        Dialog addProductDialog = new Dialog();
        addProductDialog.setWidth("600px");

        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setSpacing(true);

        H4 title = new H4("Add Product to Distributor");
        layout.add(title);

        // Create a grid to select items
        Grid<Item> itemsSelectionGrid = new Grid<>();
        itemsSelectionGrid.setHeight("300px");

        itemsSelectionGrid.addColumn(Item::getItemCode)
                .setHeader("Item Code")
                .setAutoWidth(true);

        itemsSelectionGrid.addColumn(Item::getName)
                .setHeader("Name")
                .setAutoWidth(true);

        itemsSelectionGrid.addColumn(Item::getCategory)
                .setHeader("Category")
                .setAutoWidth(true);

        itemsSelectionGrid.addColumn(Item::getPrice)
                .setHeader("Price")
                .setAutoWidth(true);

        // Load available items (mock data for demonstration)
        List<Item> availableItems = getMockItems();
        itemsSelectionGrid.setItems(availableItems);

        layout.add(itemsSelectionGrid);

        // Buttons
        Button addButton = UIUtils.createPrimaryButton("Add Selected Product");
        addButton.addClickListener(e -> {
            Item selectedItem = itemsSelectionGrid.getSelectedItems().stream().findFirst().orElse(null);
            if (selectedItem != null) {
                addProductToDistributor(selectedItem, productsGrid);
                addProductDialog.close();
            } else {
                UIUtils.showNotification("Please select a product to add.");
            }
        });

        Button cancelButton = UIUtils.createTertiaryButton("Cancel");
        cancelButton.addClickListener(e -> addProductDialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, cancelButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);

        layout.add(buttonLayout);

        addProductDialog.add(layout);
        addProductDialog.open();
    }

    private void addProductToDistributor(Item item, Grid<DistributorItem> productsGrid) {
        // In a real application, this would add the item to the distributor in the backend
        // For this example, we'll just add it to our local list
        List<DistributorItem> items = new ArrayList<>(((ListDataProvider<DistributorItem>) productsGrid.getDataProvider()).getItems());
        Long newId = (long) (items.size() + 1);
        DistributorItem distributorItem = new DistributorItem(newId, item, LocalDateTime.now());
        items.add(distributorItem);

        productsGrid.setItems(items);
        UIUtils.showNotification("Product " + item.getItemCode() + " added to distributor.");
    }

    private void removeSelectedProduct(Grid<DistributorItem> productsGrid) {
        DistributorItem selectedItem = productsGrid.getSelectedItems().stream().findFirst().orElse(null);
        if (selectedItem != null) {
            // In a real application, this would remove the item from the distributor in the backend
            // For this example, we'll just remove it from our local list
            List<DistributorItem> items = new ArrayList<>(((ListDataProvider<DistributorItem>) productsGrid.getDataProvider()).getItems());
            items.remove(selectedItem);

            productsGrid.setItems(items);
            UIUtils.showNotification("Product " + selectedItem.getItemCode() + " removed from distributor.");
        } else {
            UIUtils.showNotification("Please select a product to remove.");
        }
    }

    /**
     * Show product details in a modal dialog.
     * 
     * @param item the item to show details for
     */
    private void showProductDetails(Item item) {
        ItemDetails itemDetails = new ItemDetails(item);
        itemDetails.open();
    }

    private Component createSummaryCard(String title, String value, VaadinIcon icon) {
        Div card = new Div();
        card.addClassName("summary-card");
        card.getStyle().set("background-color", "var(--lumo-base-color)");
        card.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        card.getStyle().set("border-radius", "var(--lumo-border-radius)");
        card.getStyle().set("padding", "var(--lumo-space-m)");
        card.getStyle().set("margin-right", "var(--lumo-space-m)");
        card.getStyle().set("margin-bottom", "var(--lumo-space-m)");
        card.getStyle().set("flex", "1");

        // Icon
        Div iconContainer = new Div();
        iconContainer.getStyle().set("display", "flex");
        iconContainer.getStyle().set("align-items", "center");
        iconContainer.getStyle().set("justify-content", "center");
        iconContainer.getStyle().set("width", "48px");
        iconContainer.getStyle().set("height", "48px");
        iconContainer.getStyle().set("border-radius", "50%");
        iconContainer.getStyle().set("background-color", "var(--lumo-primary-color-10pct)");
        iconContainer.getStyle().set("margin-bottom", "var(--lumo-space-s)");

        iconContainer.add(icon.create());

        // Title
        Span titleSpan = new Span(title);
        titleSpan.getStyle().set("color", "var(--lumo-secondary-text-color)");
        titleSpan.getStyle().set("font-size", "var(--lumo-font-size-s)");

        // Value
        H4 valueH4 = new H4(value);
        valueH4.getStyle().set("margin", "0");
        valueH4.getStyle().set("font-weight", "bold");

        card.add(iconContainer, titleSpan, valueH4);
        return card;
    }

    // Mock data methods
    private Distributor findDistributorById(String distributorId) {
        // In a real application, this would fetch the distributor from a service
        // For this example, we'll create a mock distributor
        return new Distributor(
            Long.parseLong(distributorId),
            "DIST" + distributorId,
            "Distributor " + distributorId,
            "Contact Person " + distributorId,
            "contact" + distributorId + "@example.com",
            "+1-555-" + distributorId + "-0000",
            "123 Main St, City " + distributorId,
            true,
            LocalDateTime.now().minusDays(30),
            LocalDateTime.now().minusDays(5)
        );
    }

    private List<DistributorItem> getMockDistributorItems() {
        List<DistributorItem> items = new ArrayList<>();
        List<Item> availableItems = getMockItems();

        // Add a few items to the distributor
        items.add(new DistributorItem(1L, availableItems.get(0), LocalDateTime.now().minusDays(5)));
        items.add(new DistributorItem(2L, availableItems.get(2), LocalDateTime.now().minusDays(3)));
        items.add(new DistributorItem(3L, availableItems.get(4), LocalDateTime.now().minusDays(1)));

        return items;
    }

    private List<Item> getMockItems() {
        // This would typically come from a service or repository
        List<Item> items = new ArrayList<>();

        items.add(new Item(1L, "ITEM001", "Laptop", "Electronics", 1299.99, 25, "Available", LocalDateTime.now().minusDays(30), LocalDateTime.now().minusDays(5)));
        items.add(new Item(2L, "ITEM002", "T-Shirt", "Clothing", 19.99, 100, "Available", LocalDateTime.now().minusDays(25), LocalDateTime.now().minusDays(4)));
        items.add(new Item(3L, "ITEM003", "Chocolate Bar", "Food", 2.99, 0, "Out of Stock", LocalDateTime.now().minusDays(20), LocalDateTime.now().minusDays(3)));
        items.add(new Item(4L, "ITEM004", "Novel", "Books", 12.99, 50, "Available", LocalDateTime.now().minusDays(15), LocalDateTime.now().minusDays(2)));
        items.add(new Item(5L, "ITEM005", "Headphones", "Electronics", 89.99, 15, "Available", LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(1)));

        return items;
    }

    // Inner class for DistributorItem
    public static class DistributorItem {
        private Long id;
        private Item item;
        private LocalDateTime dateAdded;

        public DistributorItem(Long id, Item item, LocalDateTime dateAdded) {
            this.id = id;
            this.item = item;
            this.dateAdded = dateAdded;
        }

        public Long getId() {
            return id;
        }

        public Item getItem() {
            return item;
        }

        public String getItemCode() {
            return item.getItemCode();
        }

        public String getName() {
            return item.getName();
        }

        public String getCategory() {
            return item.getCategory();
        }

        public Double getPrice() {
            return item.getPrice();
        }

        public Integer getQuantity() {
            return item.getQuantity();
        }

        public String getStatus() {
            return item.getStatus();
        }

        public LocalDateTime getDateAdded() {
            return dateAdded;
        }
    }
}
