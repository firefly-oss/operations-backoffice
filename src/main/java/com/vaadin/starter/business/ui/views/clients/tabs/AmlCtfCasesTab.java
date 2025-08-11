package com.vaadin.starter.business.ui.views.clients.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.clients.ClientManagement.Client;
import com.vaadin.starter.business.ui.views.clients.tabs.models.AmlCtfCase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Tab for displaying client AML/CTF cases information.
 */
public class AmlCtfCasesTab extends AbstractTab {

    /**
     * Constructor for AmlCtfCasesTab.
     *
     * @param client The client whose AML/CTF cases are displayed
     */
    public AmlCtfCasesTab(Client client) {
        super(client);
    }

    @Override
    protected void initContent() {
        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Cases", "4", VaadinIcon.EXCLAMATION_CIRCLE));
        summaryCards.add(createSummaryCard("Open Cases", "2", VaadinIcon.HOURGLASS));
        summaryCards.add(createSummaryCard("High Risk", "1", VaadinIcon.WARNING));

        add(summaryCards);

        // Add AML/CTF cases grid
        Grid<AmlCtfCase> amlCtfGrid = createAmlCtfCasesGrid();
        add(amlCtfGrid);
        expand(amlCtfGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button newCaseButton = UIUtils.createPrimaryButton("New Case");
        Button reportButton = UIUtils.createTertiaryButton("Generate Report");
        Button historyButton = UIUtils.createTertiaryButton("Case History");
        actionButtons.add(newCaseButton, reportButton, historyButton);
        actionButtons.setSpacing(true);
        add(actionButtons);
    }

    @Override
    public String getTabName() {
        return "AML/CTF Cases";
    }

    /**
     * Creates the AML/CTF cases grid with all columns and data.
     *
     * @return The configured grid component
     */
    private Grid<AmlCtfCase> createAmlCtfCasesGrid() {
        Grid<AmlCtfCase> grid = new Grid<>();
        grid.setItems(generateMockAmlCtfCases());
        grid.setSizeFull();
        grid.setMinHeight("400px");

        grid.addColumn(AmlCtfCase::getCaseId)
                .setHeader("Case ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(AmlCtfCase::getCaseType)
                .setHeader("Case Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(AmlCtfCase::getDescription)
                .setHeader("Description")
                .setSortable(true)
                .setWidth("250px");
        grid.addColumn(AmlCtfCase::getRiskLevel)
                .setHeader("Risk Level")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(amlCtfCase -> createStatusBadge(amlCtfCase.getStatus())))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(AmlCtfCase::getStatus)
                .setWidth("120px");
        grid.addColumn(amlCtfCase -> amlCtfCase.getOpenDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setHeader("Open Date")
                .setSortable(true)
                .setComparator(AmlCtfCase::getOpenDate)
                .setWidth("120px");
        grid.addColumn(amlCtfCase -> amlCtfCase.getAssignedTo())
                .setHeader("Assigned To")
                .setSortable(true)
                .setWidth("150px");

        // Add item click listener to show AML/CTF case details dialog
        grid.addItemClickListener(event -> {
            AmlCtfCase amlCtfCase = event.getItem();
            showAmlCtfCaseDetails(amlCtfCase);
        });

        // Add style to indicate rows are clickable
        grid.addClassName("clickable-grid");
        grid.getElement().getStyle().set("cursor", "pointer");

        return grid;
    }

    /**
     * Shows a dialog with detailed information about an AML/CTF case.
     *
     * @param amlCtfCase The AML/CTF case to show details for
     */
    private void showAmlCtfCaseDetails(AmlCtfCase amlCtfCase) {
        // Create dialog for AML/CTF case details
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");

        // Create dialog title
        H3 title = new H3("AML/CTF Case Details: " + amlCtfCase.getCaseId());

        // Create form layout for AML/CTF case details
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create fields for AML/CTF case details
        TextField caseIdField = new TextField("Case ID");
        caseIdField.setValue(amlCtfCase.getCaseId());
        caseIdField.setReadOnly(true);

        TextField caseTypeField = new TextField("Case Type");
        caseTypeField.setValue(amlCtfCase.getCaseType());
        caseTypeField.setReadOnly(true);

        TextField descriptionField = new TextField("Description");
        descriptionField.setValue(amlCtfCase.getDescription());
        descriptionField.setReadOnly(true);

        TextField riskLevelField = new TextField("Risk Level");
        riskLevelField.setValue(amlCtfCase.getRiskLevel());
        riskLevelField.setReadOnly(true);

        TextField statusField = new TextField("Status");
        statusField.setValue(amlCtfCase.getStatus());
        statusField.setReadOnly(true);

        DatePicker openDateField = new DatePicker("Open Date");
        openDateField.setValue(amlCtfCase.getOpenDate());
        openDateField.setReadOnly(true);

        DatePicker closeDateField = new DatePicker("Close Date");
        closeDateField.setValue(amlCtfCase.getCloseDate());
        closeDateField.setReadOnly(true);

        TextField assignedToField = new TextField("Assigned To");
        assignedToField.setValue(amlCtfCase.getAssignedTo());
        assignedToField.setReadOnly(true);

        TextField notesField = new TextField("Notes");
        notesField.setValue(amlCtfCase.getNotes());
        notesField.setReadOnly(true);

        // Add fields to form layout
        formLayout.add(caseIdField, caseTypeField, descriptionField, riskLevelField, 
                      statusField, openDateField, closeDateField, assignedToField, notesField);

        // Create close button
        Button closeButton = UIUtils.createTertiaryButton("Close");
        closeButton.addClickListener(e -> dialog.close());

        // Create layout for button
        HorizontalLayout buttonLayout = new HorizontalLayout(closeButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonLayout.setWidthFull();

        // Create layout for dialog content
        VerticalLayout dialogLayout = new VerticalLayout(title, formLayout, buttonLayout);
        dialogLayout.setPadding(true);
        dialogLayout.setSpacing(true);

        dialog.add(dialogLayout);
        dialog.open();
    }

    /**
     * Generates mock AML/CTF case data for testing.
     *
     * @return A list of mock AML/CTF cases
     */
    private List<AmlCtfCase> generateMockAmlCtfCases() {
        List<AmlCtfCase> amlCtfCases = new ArrayList<>();

        amlCtfCases.add(new AmlCtfCase(
            "AML001", 
            "KYC Verification", 
            "Annual KYC verification", 
            "Low", 
            "Completed", 
            LocalDate.now().minusMonths(6), 
            LocalDate.now().minusMonths(5), 
            "Compliance Officer", 
            "Regular KYC verification completed successfully."
        ));

        amlCtfCases.add(new AmlCtfCase(
            "AML002", 
            "Suspicious Transaction", 
            "Multiple large transactions in short period", 
            "High", 
            "Open", 
            LocalDate.now().minusDays(15), 
            null, 
            "Senior Compliance Officer", 
            "Client made 5 transactions over $10,000 within 3 days. Requires further investigation."
        ));

        amlCtfCases.add(new AmlCtfCase(
            "AML003", 
            "PEP Screening", 
            "Politically Exposed Person screening", 
            "Medium", 
            "Open", 
            LocalDate.now().minusDays(10), 
            null, 
            "Compliance Officer", 
            "Client identified as potential PEP. Additional documentation requested."
        ));

        amlCtfCases.add(new AmlCtfCase(
            "AML004", 
            "Sanctions Check", 
            "Regular sanctions list check", 
            "Low", 
            "Completed", 
            LocalDate.now().minusMonths(3), 
            LocalDate.now().minusMonths(3), 
            "Compliance Officer", 
            "No matches found on sanctions lists."
        ));

        return amlCtfCases;
    }
}