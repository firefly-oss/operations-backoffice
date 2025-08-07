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
import com.vaadin.starter.business.ui.views.clients.OnboardingProcesses.OnboardingProcess;

import java.time.LocalDate;

/**
 * Dialog for displaying onboarding process details.
 */
public class OnboardingProcessDetails extends Dialog {

    private final OnboardingProcess process;

    private TextField processIdField;
    private TextField clientIdField;
    private TextField clientNameField;
    private ComboBox<String> stageField;
    private TextField assignedToField;
    private ComboBox<String> statusField;
    private DatePicker startDateField;
    private DatePicker dueDateField;
    private ComboBox<String> clientTypeField;
    private TextArea notesField;
    private DatePicker creationDateField;
    private DatePicker updateDateField;

    /**
     * Constructor for the OnboardingProcessDetails dialog.
     *
     * @param process the onboarding process to display details for
     */
    public OnboardingProcessDetails(OnboardingProcess process) {
        this.process = process;

        setWidth("800px");
        setHeight("auto");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);

        H3 title = new H3("Onboarding Process Details: " + process.getProcessId());
        content.add(title);

        content.add(createForm());
        content.add(createButtonLayout());

        add(content);
    }

    private FormLayout createForm() {
        // Process ID
        processIdField = new TextField();
        processIdField.setValue(process.getProcessId() != null ? process.getProcessId() : "");
        processIdField.setWidthFull();

        // Client ID
        clientIdField = new TextField();
        clientIdField.setValue(process.getClientId() != null ? process.getClientId() : "");
        clientIdField.setWidthFull();

        // Client Name
        clientNameField = new TextField();
        clientNameField.setValue(process.getClientName() != null ? process.getClientName() : "");
        clientNameField.setWidthFull();

        // Stage
        stageField = new ComboBox<>();
        stageField.setItems("Application", "Verification", "Documentation", "Approval", "Activation");
        stageField.setValue(process.getStage());
        stageField.setWidthFull();

        // Assigned To
        assignedToField = new TextField();
        assignedToField.setValue(process.getAssignedTo() != null ? process.getAssignedTo() : "");
        assignedToField.setWidthFull();

        // Status
        statusField = new ComboBox<>();
        statusField.setItems("In Progress", "Completed", "On Hold", "Rejected");
        statusField.setValue(process.getStatus());
        statusField.setWidthFull();

        // Start Date
        startDateField = new DatePicker();
        startDateField.setValue(process.getStartDate());
        startDateField.setWidthFull();

        // Due Date
        dueDateField = new DatePicker();
        dueDateField.setValue(process.getDueDate());
        dueDateField.setWidthFull();

        // Client Type
        clientTypeField = new ComboBox<>();
        clientTypeField.setItems("Individual", "Corporate");
        clientTypeField.setValue(process.getClientType());
        clientTypeField.setWidthFull();

        // Notes
        notesField = new TextArea();
        notesField.setValue(""); // Process doesn't have a notes field, so we'll leave it empty
        notesField.setWidthFull();
        notesField.setHeight("100px");

        // Creation Date
        creationDateField = new DatePicker();
        creationDateField.setValue(process.getDateCreated() != null ? process.getDateCreated().toLocalDate() : LocalDate.now());
        creationDateField.setReadOnly(true);
        creationDateField.setWidthFull();

        // Update Date
        updateDateField = new DatePicker();
        updateDateField.setValue(process.getDateUpdated() != null ? process.getDateUpdated().toLocalDate() : LocalDate.now());
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
        form.addFormItem(processIdField, "Process ID");
        form.addFormItem(clientIdField, "Client ID");
        form.addFormItem(clientNameField, "Client Name");
        form.addFormItem(stageField, "Stage");
        form.addFormItem(assignedToField, "Assigned To");
        form.addFormItem(statusField, "Status");
        form.addFormItem(startDateField, "Start Date");
        form.addFormItem(dueDateField, "Due Date");
        form.addFormItem(clientTypeField, "Client Type");
        form.addFormItem(notesField, "Notes");
        form.addFormItem(creationDateField, "Creation Date");
        form.addFormItem(updateDateField, "Update Date");

        return form;
    }

    private HorizontalLayout createButtonLayout() {
        Button saveButton = UIUtils.createPrimaryButton("Save");
        saveButton.addClickListener(e -> {
            saveProcess();
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

    private void saveProcess() {
        // In a real application, this would save the process data to the backend
        // For this example, we'll just show a notification
        UIUtils.showNotification("Onboarding process details saved.");

        // Here you would update the process with the new values
        // Example: process.setStage(stageField.getValue());
        // processService.updateProcess(process);
    }
}