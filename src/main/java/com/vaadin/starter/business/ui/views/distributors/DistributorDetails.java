package com.vaadin.starter.business.ui.views.distributors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.distributors.DistributorManagement.Distributor;

import java.time.LocalDate;

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

    /**
     * Constructor for the DistributorDetails dialog.
     *
     * @param distributor the distributor to display details for
     */
    public DistributorDetails(Distributor distributor) {
        this.distributor = distributor;

        setWidth("800px");
        setHeight("auto");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);

        H3 title = new H3("Distributor Details: " + distributor.getDistributorCode());
        content.add(title);

        content.add(createForm());
        content.add(createButtonLayout());

        add(content);
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
}
