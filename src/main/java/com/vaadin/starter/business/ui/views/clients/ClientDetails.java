package com.vaadin.starter.business.ui.views.clients;

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
import com.vaadin.starter.business.ui.views.clients.ClientManagement.Client;

import java.time.LocalDate;

/**
 * Dialog for displaying client details.
 */
public class ClientDetails extends Dialog {

    private final Client client;

    private TextField clientIdField;
    private TextField nameField;
    private TextField emailField;
    private TextField phoneField;
    private ComboBox<String> typeField;
    private ComboBox<String> statusField;
    private DatePicker creationDateField;
    private DatePicker updateDateField;
    private TextArea addressField;

    /**
     * Constructor for the ClientDetails dialog.
     *
     * @param client the client to display details for
     */
    public ClientDetails(Client client) {
        this.client = client;

        setWidth("800px");
        setHeight("auto");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);

        H3 title = new H3("Client Details: " + client.getClientId());
        content.add(title);

        content.add(createForm());
        content.add(createButtonLayout());

        add(content);
    }

    private FormLayout createForm() {
        // Client ID
        clientIdField = new TextField();
        clientIdField.setValue(client.getClientId() != null ? client.getClientId() : "");
        clientIdField.setWidthFull();

        // Name
        nameField = new TextField();
        nameField.setValue(client.getName() != null ? client.getName() : "");
        nameField.setWidthFull();

        // Email
        emailField = new TextField();
        emailField.setValue(client.getEmail() != null ? client.getEmail() : "");
        emailField.setWidthFull();

        // Phone
        phoneField = new TextField();
        phoneField.setValue(client.getPhone() != null ? client.getPhone() : "");
        phoneField.setWidthFull();

        // Address
        addressField = new TextArea();
        addressField.setValue(client.getAddress() != null ? client.getAddress() : "");
        addressField.setWidthFull();
        addressField.setHeight("100px");

        // Type
        typeField = new ComboBox<>();
        typeField.setItems("Individual", "Corporate");
        typeField.setValue(client.getType());
        typeField.setWidthFull();

        // Status
        statusField = new ComboBox<>();
        statusField.setItems("Active", "Inactive", "Suspended");
        statusField.setValue(client.getStatus());
        statusField.setWidthFull();

        // Creation Date
        creationDateField = new DatePicker();
        creationDateField.setValue(client.getDateCreated() != null ? client.getDateCreated().toLocalDate() : LocalDate.now());
        creationDateField.setReadOnly(true);
        creationDateField.setWidthFull();

        // Update Date
        updateDateField = new DatePicker();
        updateDateField.setValue(client.getDateUpdated() != null ? client.getDateUpdated().toLocalDate() : LocalDate.now());
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
        form.addFormItem(clientIdField, "Client ID");
        form.addFormItem(nameField, "Name");
        form.addFormItem(emailField, "Email");
        form.addFormItem(phoneField, "Phone");
        form.addFormItem(addressField, "Address");
        form.addFormItem(typeField, "Type");
        form.addFormItem(statusField, "Status");
        form.addFormItem(creationDateField, "Creation Date");
        form.addFormItem(updateDateField, "Update Date");

        return form;
    }

    private HorizontalLayout createButtonLayout() {
        Button saveButton = UIUtils.createPrimaryButton("Save");
        saveButton.addClickListener(e -> {
            saveClient();
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

    private void saveClient() {
        // In a real application, this would save the client data to the backend
        // For this example, we'll just show a notification
        UIUtils.showNotification("Client details saved.");

        // Here you would update the client with the new values
        // Example: client.setName(nameField.getValue());
        // clientService.updateClient(client);
    }
}