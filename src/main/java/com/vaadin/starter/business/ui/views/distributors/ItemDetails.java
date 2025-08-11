package com.vaadin.starter.business.ui.views.distributors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.distributors.Items.Item;

import java.time.LocalDate;

/**
 * Dialog for displaying item details.
 */
public class ItemDetails extends Dialog {

    private final Item item;

    private TextField itemCodeField;
    private TextField nameField;
    private ComboBox<String> categoryField;
    private NumberField priceField;
    private NumberField quantityField;
    private ComboBox<String> statusField;
    private DatePicker creationDateField;
    private DatePicker updateDateField;
    private TextArea descriptionField;

    /**
     * Constructor for the ItemDetails dialog.
     *
     * @param item the item to display details for
     */
    public ItemDetails(Item item) {
        this.item = item;

        setWidth("800px");
        setHeight("auto");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);

        H3 title = new H3("Item Details: " + item.getItemCode());
        content.add(title);

        content.add(createForm());
        content.add(createButtonLayout());

        add(content);
    }

    private FormLayout createForm() {
        // Item ID
        TextField itemIdField = new TextField();
        itemIdField.setValue(item.getItemId() != null ? item.getItemId().toString() : "");
        itemIdField.setReadOnly(true);
        itemIdField.setWidthFull();

        // Item Code
        itemCodeField = new TextField();
        itemCodeField.setValue(item.getItemCode() != null ? item.getItemCode() : "");
        itemCodeField.setWidthFull();

        // Name
        nameField = new TextField();
        nameField.setValue(item.getName() != null ? item.getName() : "");
        nameField.setWidthFull();

        // Category
        categoryField = new ComboBox<>();
        categoryField.setItems("Electronics", "Clothing", "Food", "Books", "Furniture", "Appliances", "Other");
        categoryField.setValue(item.getCategory());
        categoryField.setWidthFull();

        // Price
        priceField = new NumberField();
        priceField.setValue(item.getPrice());
        priceField.setMin(0);
        priceField.setStep(0.01);
        priceField.setWidthFull();

        // Quantity
        quantityField = new NumberField();
        quantityField.setValue(item.getQuantity().doubleValue());
        quantityField.setMin(0);
        quantityField.setStep(1);
        quantityField.setWidthFull();

        // Description
        descriptionField = new TextArea();
        descriptionField.setValue(""); // Item doesn't have a description field, so we'll leave it empty
        descriptionField.setWidthFull();
        descriptionField.setHeight("100px");

        // Status
        statusField = new ComboBox<>();
        statusField.setItems("Available", "Out of Stock", "Discontinued");
        statusField.setValue(item.getStatus());
        statusField.setWidthFull();

        // Creation Date
        creationDateField = new DatePicker();
        creationDateField.setValue(item.getDateCreated() != null ? item.getDateCreated().toLocalDate() : LocalDate.now());
        creationDateField.setReadOnly(true);
        creationDateField.setWidthFull();

        // Update Date
        updateDateField = new DatePicker();
        updateDateField.setValue(item.getDateUpdated() != null ? item.getDateUpdated().toLocalDate() : LocalDate.now());
        updateDateField.setReadOnly(true);
        updateDateField.setWidthFull();

        // Product Image
        Image productImage = new Image(getProductImageUrl(), item.getName());
        productImage.setWidth("200px");
        productImage.setHeight("200px");
        productImage.getStyle().set("object-fit", "contain");
        productImage.getStyle().set("border", "1px solid var(--lumo-contrast-10pct)");
        productImage.getStyle().set("border-radius", "var(--lumo-border-radius)");
        productImage.getStyle().set("background-color", "var(--lumo-base-color)");

        Div imageContainer = new Div(productImage);
        imageContainer.getStyle().set("display", "flex");
        imageContainer.getStyle().set("justify-content", "center");
        imageContainer.getStyle().set("align-items", "center");
        imageContainer.getStyle().set("width", "100%");
        imageContainer.getStyle().set("margin-bottom", "var(--lumo-space-m)");

        // Form layout
        FormLayout form = new FormLayout();
        form.addClassNames(LumoStyles.Padding.Bottom.L,
                LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("600px", 2,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP));

        // Add image at the top
        form.addFormItem(imageContainer, "Product Image");

        form.addFormItem(itemIdField, "Item ID");
        form.addFormItem(itemCodeField, "Item Code");
        form.addFormItem(nameField, "Name");
        form.addFormItem(categoryField, "Category");
        form.addFormItem(priceField, "Price");
        form.addFormItem(quantityField, "Quantity");
        form.addFormItem(descriptionField, "Description");
        form.addFormItem(statusField, "Status");
        form.addFormItem(creationDateField, "Creation Date");
        form.addFormItem(updateDateField, "Update Date");

        return form;
    }

    private HorizontalLayout createButtonLayout() {
        Button saveButton = UIUtils.createPrimaryButton("Save");
        saveButton.addClickListener(e -> {
            saveItem();
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

    private void saveItem() {
        // In a real application, this would save the item data to the backend
        // For this example, we'll just show a notification
        UIUtils.showNotification("Item details saved.");

        // Here you would update the item with the new values
        // Example: item.setName(nameField.getValue());
        // itemService.updateItem(item);
    }

    /**
     * Generate a mock image URL based on the item's category or name.
     * In a real application, this would be replaced with actual product images.
     * 
     * @return URL of a placeholder image
     */
    private String getProductImageUrl() {
        // Use placeholder images based on category
        String category = item.getCategory() != null ? item.getCategory().toLowerCase() : "";

        switch (category) {
            case "electronics":
                return "https://via.placeholder.com/400x400.png?text=Electronics+" + item.getItemCode();
            case "clothing":
                return "https://via.placeholder.com/400x400.png?text=Clothing+" + item.getItemCode();
            case "food":
                return "https://via.placeholder.com/400x400.png?text=Food+" + item.getItemCode();
            case "books":
                return "https://via.placeholder.com/400x400.png?text=Books+" + item.getItemCode();
            case "furniture":
                return "https://via.placeholder.com/400x400.png?text=Furniture+" + item.getItemCode();
            case "appliances":
                return "https://via.placeholder.com/400x400.png?text=Appliances+" + item.getItemCode();
            default:
                return "https://via.placeholder.com/400x400.png?text=Product+" + item.getItemCode();
        }
    }
}
