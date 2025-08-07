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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.clients.Contracts.Contract;

import java.time.LocalDate;

/**
 * Dialog for displaying contract details.
 */
public class ContractDetails extends Dialog {

    private final Contract contract;

    private TextField contractIdField;
    private TextField clientIdField;
    private TextField clientNameField;
    private ComboBox<String> typeField;
    private NumberField valueField;
    private ComboBox<String> statusField;
    private DatePicker startDateField;
    private DatePicker endDateField;
    private DatePicker creationDateField;
    private DatePicker updateDateField;

    /**
     * Constructor for the ContractDetails dialog.
     *
     * @param contract the contract to display details for
     */
    public ContractDetails(Contract contract) {
        this.contract = contract;

        setWidth("800px");
        setHeight("auto");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);

        H3 title = new H3("Contract Details: " + contract.getContractId());
        content.add(title);

        content.add(createForm());
        content.add(createButtonLayout());

        add(content);
    }

    private FormLayout createForm() {
        // Contract ID
        contractIdField = new TextField();
        contractIdField.setValue(contract.getContractId() != null ? contract.getContractId() : "");
        contractIdField.setWidthFull();

        // Client ID
        clientIdField = new TextField();
        clientIdField.setValue(contract.getClientId() != null ? contract.getClientId() : "");
        clientIdField.setWidthFull();

        // Client Name
        clientNameField = new TextField();
        clientNameField.setValue(contract.getClientName() != null ? contract.getClientName() : "");
        clientNameField.setWidthFull();

        // Type
        typeField = new ComboBox<>();
        typeField.setItems("Standard", "Premium", "Enterprise", "Custom");
        typeField.setValue(contract.getType());
        typeField.setWidthFull();

        // Value
        valueField = new NumberField();
        valueField.setValue(contract.getValue());
        valueField.setMin(0);
        valueField.setStep(0.01);
        valueField.setWidthFull();

        // Status
        statusField = new ComboBox<>();
        statusField.setItems("Active", "Pending", "Expired", "Terminated");
        statusField.setValue(contract.getStatus());
        statusField.setWidthFull();

        // Start Date
        startDateField = new DatePicker();
        startDateField.setValue(contract.getStartDate());
        startDateField.setWidthFull();

        // End Date
        endDateField = new DatePicker();
        endDateField.setValue(contract.getEndDate());
        endDateField.setWidthFull();

        // Creation Date
        creationDateField = new DatePicker();
        creationDateField.setValue(contract.getDateCreated() != null ? contract.getDateCreated().toLocalDate() : LocalDate.now());
        creationDateField.setReadOnly(true);
        creationDateField.setWidthFull();

        // Update Date
        updateDateField = new DatePicker();
        updateDateField.setValue(contract.getDateUpdated() != null ? contract.getDateUpdated().toLocalDate() : LocalDate.now());
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
        form.addFormItem(contractIdField, "Contract ID");
        form.addFormItem(clientIdField, "Client ID");
        form.addFormItem(clientNameField, "Client Name");
        form.addFormItem(typeField, "Type");
        form.addFormItem(valueField, "Value");
        form.addFormItem(statusField, "Status");
        form.addFormItem(startDateField, "Start Date");
        form.addFormItem(endDateField, "End Date");
        form.addFormItem(creationDateField, "Creation Date");
        form.addFormItem(updateDateField, "Update Date");

        return form;
    }

    private HorizontalLayout createButtonLayout() {
        Button saveButton = UIUtils.createPrimaryButton("Save");
        saveButton.addClickListener(e -> {
            saveContract();
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

    private void saveContract() {
        // In a real application, this would save the contract data to the backend
        // For this example, we'll just show a notification
        UIUtils.showNotification("Contract details saved.");

        // Here you would update the contract with the new values
        // Example: contract.setType(typeField.getValue());
        // contractService.updateContract(contract);
    }
}