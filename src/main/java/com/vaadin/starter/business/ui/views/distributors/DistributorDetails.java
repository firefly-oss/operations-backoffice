package com.vaadin.starter.business.ui.views.distributors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.distributors.DistributorManagement.Distributor;
import com.vaadin.starter.business.ui.views.distributors.Items.Item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Dialog for displaying distributor details.
 */
public class DistributorDetails extends Dialog {

    private final Distributor distributor;

    private TextField distributorCodeField;
    private TextField nameField;
    private TextField contactPersonField;
    private TextField emailField;
    private TextField phoneField;
    private ComboBox<String> statusField;
    private DatePicker creationDateField;
    private DatePicker updateDateField;
    private TextArea addressField;

    // Items grid
    private Grid<DistributorItem> itemsGrid;
    private ListDataProvider<DistributorItem> itemsDataProvider;
    private List<DistributorItem> distributorItems = new ArrayList<>();

    /**
     * Constructor for the DistributorDetails dialog.
     *
     * @param distributor the distributor to display details for
     */
    public DistributorDetails(Distributor distributor) {
        this.distributor = distributor;

        setWidth("900px");
        setHeight("auto");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);

        H3 title = new H3("Distributor Details: " + distributor.getDistributorCode());
        content.add(title);

        content.add(createForm());

        // Add items section
        H4 itemsTitle = new H4("Distributor Items");
        content.add(itemsTitle);
        content.add(createItemsGrid());
        content.add(createItemsButtonLayout());

        content.add(createButtonLayout());

        // Initialize with mock data for demonstration
        loadMockItems();

        add(content);
    }

    /**
     * Class representing an item associated with a distributor.
     */
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

    private FormLayout createForm() {
        // Distributor ID
        TextField distributorIdField = new TextField();
        distributorIdField.setValue(distributor.getDistributorId() != null ? distributor.getDistributorId().toString() : "");
        distributorIdField.setReadOnly(true);
        distributorIdField.setWidthFull();

        // Distributor Code
        distributorCodeField = new TextField();
        distributorCodeField.setValue(distributor.getDistributorCode() != null ? distributor.getDistributorCode() : "");
        distributorCodeField.setWidthFull();

        // Name
        nameField = new TextField();
        nameField.setValue(distributor.getName() != null ? distributor.getName() : "");
        nameField.setWidthFull();

        // Contact Person
        contactPersonField = new TextField();
        contactPersonField.setValue(distributor.getContactPerson() != null ? distributor.getContactPerson() : "");
        contactPersonField.setWidthFull();

        // Email
        emailField = new TextField();
        emailField.setValue(distributor.getEmail() != null ? distributor.getEmail() : "");
        emailField.setWidthFull();

        // Phone
        phoneField = new TextField();
        phoneField.setValue(distributor.getPhone() != null ? distributor.getPhone() : "");
        phoneField.setWidthFull();

        // Address
        addressField = new TextArea();
        addressField.setValue(distributor.getAddress() != null ? distributor.getAddress() : "");
        addressField.setWidthFull();
        addressField.setHeight("100px");

        // Status
        statusField = new ComboBox<>();
        statusField.setItems("Active", "Inactive");
        statusField.setValue(distributor.isActive() ? "Active" : "Inactive");
        statusField.setWidthFull();

        // Creation Date
        creationDateField = new DatePicker();
        creationDateField.setValue(distributor.getDateCreated() != null ? distributor.getDateCreated().toLocalDate() : LocalDate.now());
        creationDateField.setReadOnly(true);
        creationDateField.setWidthFull();

        // Update Date
        updateDateField = new DatePicker();
        updateDateField.setValue(distributor.getDateUpdated() != null ? distributor.getDateUpdated().toLocalDate() : LocalDate.now());
        updateDateField.setReadOnly(true);
        updateDateField.setWidthFull();

        // Form layout
        FormLayout form = new FormLayout();
        form.addClassNames(LumoStyles.Padding.Bottom.L,
                LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("600px", 2,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP));
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

        return form;
    }

    private HorizontalLayout createButtonLayout() {
        Button saveButton = UIUtils.createPrimaryButton("Save");
        saveButton.addClickListener(e -> {
            saveDistributor();
            close();
        });

        Button cancelButton = UIUtils.createTertiaryButton("Cancel");
        cancelButton.addClickListener(e -> close());

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);

        return buttonLayout;
    }

    private void saveDistributor() {
        // In a real application, this would save the distributor data to the backend
        // For this example, we'll just show a notification
        UIUtils.showNotification("Distributor details saved.");

        // Here you would update the distributor with the new values
        // Example: distributor.setName(nameField.getValue());
        // distributorService.updateDistributor(distributor);
    }

    private Component createItemsGrid() {
        itemsGrid = new Grid<>();
        itemsGrid.setSelectionMode(SelectionMode.SINGLE);
        itemsGrid.setHeight("200px");

        // Configure grid columns
        itemsGrid.addColumn(DistributorItem::getItemCode)
                .setHeader("Item Code")
                .setAutoWidth(true)
                .setSortable(true);

        itemsGrid.addColumn(DistributorItem::getName)
                .setHeader("Name")
                .setAutoWidth(true)
                .setSortable(true);

        itemsGrid.addColumn(DistributorItem::getCategory)
                .setHeader("Category")
                .setAutoWidth(true)
                .setSortable(true);

        itemsGrid.addColumn(DistributorItem::getPrice)
                .setHeader("Price")
                .setAutoWidth(true)
                .setSortable(true);

        itemsGrid.addColumn(DistributorItem::getQuantity)
                .setHeader("Quantity")
                .setAutoWidth(true)
                .setSortable(true);

        // Initialize with data provider
        itemsDataProvider = DataProvider.ofCollection(distributorItems);
        itemsGrid.setDataProvider(itemsDataProvider);

        return itemsGrid;
    }

    private Component createItemsButtonLayout() {
        Button addItemButton = UIUtils.createSuccessButton("Add Item");
        addItemButton.setIcon(VaadinIcon.PLUS.create());
        addItemButton.addClickListener(e -> openAddItemDialog());

        Button removeItemButton = UIUtils.createErrorButton("Remove Item");
        removeItemButton.setIcon(VaadinIcon.TRASH.create());
        removeItemButton.addClickListener(e -> removeSelectedItem());

        HorizontalLayout buttonLayout = new HorizontalLayout(addItemButton, removeItemButton);
        buttonLayout.setSpacing(true);
        buttonLayout.setPadding(true);

        return buttonLayout;
    }

    private void openAddItemDialog() {
        Dialog addItemDialog = new Dialog();
        addItemDialog.setWidth("600px");

        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setSpacing(true);

        H4 title = new H4("Add Item to Distributor");
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
        Button addButton = UIUtils.createPrimaryButton("Add Selected Item");
        addButton.addClickListener(e -> {
            Item selectedItem = itemsSelectionGrid.getSelectedItems().stream().findFirst().orElse(null);
            if (selectedItem != null) {
                addItemToDistributor(selectedItem);
                addItemDialog.close();
            } else {
                UIUtils.showNotification("Please select an item to add.");
            }
        });

        Button cancelButton = UIUtils.createTertiaryButton("Cancel");
        cancelButton.addClickListener(e -> addItemDialog.close());

        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, cancelButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);

        layout.add(buttonLayout);

        addItemDialog.add(layout);
        addItemDialog.open();
    }

    private void addItemToDistributor(Item item) {
        // In a real application, this would add the item to the distributor in the backend
        // For this example, we'll just add it to our local list
        Long newId = (long) (distributorItems.size() + 1);
        DistributorItem distributorItem = new DistributorItem(newId, item, LocalDateTime.now());
        distributorItems.add(distributorItem);
        itemsDataProvider.refreshAll();

        UIUtils.showNotification("Item " + item.getItemCode() + " added to distributor.");
    }

    private void removeSelectedItem() {
        DistributorItem selectedItem = itemsGrid.getSelectedItems().stream().findFirst().orElse(null);
        if (selectedItem != null) {
            // In a real application, this would remove the item from the distributor in the backend
            // For this example, we'll just remove it from our local list
            distributorItems.remove(selectedItem);
            itemsDataProvider.refreshAll();

            UIUtils.showNotification("Item " + selectedItem.getItemCode() + " removed from distributor.");
        } else {
            UIUtils.showNotification("Please select an item to remove.");
        }
    }

    private void loadMockItems() {
        // In a real application, this would load the distributor's items from the backend
        // For this example, we'll create some mock data
        List<Item> availableItems = getMockItems();

        // Add a few items to the distributor
        distributorItems.add(new DistributorItem(1L, availableItems.get(0), LocalDateTime.now().minusDays(5)));
        distributorItems.add(new DistributorItem(2L, availableItems.get(2), LocalDateTime.now().minusDays(3)));
        distributorItems.add(new DistributorItem(3L, availableItems.get(4), LocalDateTime.now().minusDays(1)));

        // Refresh the grid
        if (itemsDataProvider != null) {
            itemsDataProvider.refreshAll();
        }
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
}
