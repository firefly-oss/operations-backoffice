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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.distributors.DistributorUsers.DistributorUser;

import java.time.LocalDate;

/**
 * Dialog for displaying distributor user details.
 */
public class DistributorUserDetails extends Dialog {

    private final DistributorUser user;

    private TextField usernameField;
    private TextField fullNameField;
    private TextField emailField;
    private ComboBox<String> roleField;
    private TextField distributorNameField;
    private ComboBox<String> statusField;
    private DatePicker creationDateField;
    private DatePicker updateDateField;

    /**
     * Constructor for the DistributorUserDetails dialog.
     *
     * @param user the user to display details for
     */
    public DistributorUserDetails(DistributorUser user) {
        this.user = user;

        setWidth("800px");
        setHeight("auto");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(true);
        content.setSpacing(true);

        H3 title = new H3("User Details: " + user.getUsername());
        content.add(title);

        content.add(createForm());
        content.add(createButtonLayout());

        add(content);
    }

    private FormLayout createForm() {
        // User ID
        TextField userIdField = new TextField();
        userIdField.setValue(user.getUserId() != null ? user.getUserId().toString() : "");
        userIdField.setReadOnly(true);
        userIdField.setWidthFull();

        // Username
        usernameField = new TextField();
        usernameField.setValue(user.getUsername() != null ? user.getUsername() : "");
        usernameField.setWidthFull();

        // Full Name
        fullNameField = new TextField();
        fullNameField.setValue(user.getFullName() != null ? user.getFullName() : "");
        fullNameField.setWidthFull();

        // Email
        emailField = new TextField();
        emailField.setValue(user.getEmail() != null ? user.getEmail() : "");
        emailField.setWidthFull();

        // Role
        roleField = new ComboBox<>();
        roleField.setItems("Admin", "Manager", "Operator", "Viewer");
        roleField.setValue(user.getRole());
        roleField.setWidthFull();

        // Distributor Name
        distributorNameField = new TextField();
        distributorNameField.setValue(user.getDistributorName() != null ? user.getDistributorName() : "");
        distributorNameField.setWidthFull();

        // Status
        statusField = new ComboBox<>();
        statusField.setItems("Active", "Inactive", "Locked");
        statusField.setValue(user.getStatus());
        statusField.setWidthFull();

        // Creation Date
        creationDateField = new DatePicker();
        creationDateField.setValue(user.getDateCreated() != null ? user.getDateCreated().toLocalDate() : LocalDate.now());
        creationDateField.setReadOnly(true);
        creationDateField.setWidthFull();

        // Update Date
        updateDateField = new DatePicker();
        updateDateField.setValue(user.getDateUpdated() != null ? user.getDateUpdated().toLocalDate() : LocalDate.now());
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
        form.addFormItem(userIdField, "User ID");
        form.addFormItem(usernameField, "Username");
        form.addFormItem(fullNameField, "Full Name");
        form.addFormItem(emailField, "Email");
        form.addFormItem(roleField, "Role");
        form.addFormItem(distributorNameField, "Distributor");
        form.addFormItem(statusField, "Status");
        form.addFormItem(creationDateField, "Creation Date");
        form.addFormItem(updateDateField, "Update Date");

        return form;
    }

    private HorizontalLayout createButtonLayout() {
        Button saveButton = UIUtils.createPrimaryButton("Save");
        saveButton.addClickListener(e -> {
            saveUser();
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

    private void saveUser() {
        // In a real application, this would save the user data to the backend
        // For this example, we'll just show a notification
        UIUtils.showNotification("User details saved.");

        // Here you would update the user with the new values
        // Example: user.setFullName(fullNameField.getValue());
        // userService.updateUser(user);
    }
}