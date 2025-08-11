package com.vaadin.starter.business.ui.views.clients.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.clients.ClientManagement.Client;
import com.vaadin.starter.business.ui.views.clients.tabs.models.Leasing;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Tab for displaying client leasing information.
 */
public class LeasingTab extends AbstractTab {

    /**
     * Constructor for LeasingTab.
     *
     * @param client The client whose leasing information is displayed
     */
    public LeasingTab(Client client) {
        super(client);
    }

    @Override
    protected void initContent() {
        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Leasing", "2", VaadinIcon.CAR));
        summaryCards.add(createSummaryCard("Outstanding Balance", "$85,000.00", VaadinIcon.DOLLAR));
        summaryCards.add(createSummaryCard("Next Payment", "$950.00", VaadinIcon.CALENDAR));

        add(summaryCards);

        // Add leasing grid
        Grid<Leasing> leasingGrid = createLeasingGrid();
        add(leasingGrid);
        expand(leasingGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button applyLeasingButton = UIUtils.createPrimaryButton("Apply for Leasing");
        Button makePaymentButton = UIUtils.createTertiaryButton("Make Payment");
        Button leasingDetailsButton = UIUtils.createTertiaryButton("Leasing Details");
        actionButtons.add(applyLeasingButton, makePaymentButton, leasingDetailsButton);
        actionButtons.setSpacing(true);
        add(actionButtons);
    }

    @Override
    public String getTabName() {
        return "Leasing";
    }

    /**
     * Creates the leasing grid with all columns and data.
     *
     * @return The configured grid component
     */
    private Grid<Leasing> createLeasingGrid() {
        Grid<Leasing> grid = new Grid<>();
        grid.setItems(generateMockLeasings());
        grid.setSizeFull();
        grid.setMinHeight("400px");

        grid.addColumn(Leasing::getLeasingNumber)
                .setHeader("Leasing Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Leasing::getAssetType)
                .setHeader("Asset Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Leasing::getAssetValue)
                .setHeader("Asset Value")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Leasing::getRemainingBalance)
                .setHeader("Remaining Balance")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Leasing::getInterestRate)
                .setHeader("Interest Rate")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Leasing::getMonthlyPayment)
                .setHeader("Monthly Payment")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(leasing -> leasing.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setHeader("Start Date")
                .setSortable(true)
                .setComparator(Leasing::getStartDate)
                .setWidth("120px");
        grid.addColumn(leasing -> leasing.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setHeader("End Date")
                .setSortable(true)
                .setComparator(Leasing::getEndDate)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(leasing -> createStatusBadge(leasing.getStatus())))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Leasing::getStatus)
                .setWidth("120px");

        // Add item click listener to show leasing details dialog
        grid.addItemClickListener(event -> {
            Leasing leasing = event.getItem();
            showLeasingDetails(leasing);
        });

        // Add style to indicate rows are clickable
        grid.addClassName("clickable-grid");
        grid.getElement().getStyle().set("cursor", "pointer");

        return grid;
    }

    /**
     * Shows a dialog with detailed information about a leasing.
     *
     * @param leasing The leasing to show details for
     */
    private void showLeasingDetails(Leasing leasing) {
        // Create dialog for leasing details
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");

        // Create dialog title
        H3 title = new H3("Leasing Details: " + leasing.getLeasingNumber());

        // Create form layout for leasing details
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create fields for leasing details
        TextField leasingNumberField = new TextField("Leasing Number");
        leasingNumberField.setValue(leasing.getLeasingNumber());
        leasingNumberField.setReadOnly(true);

        TextField assetTypeField = new TextField("Asset Type");
        assetTypeField.setValue(leasing.getAssetType());
        assetTypeField.setReadOnly(true);

        TextField assetDescriptionField = new TextField("Asset Description");
        assetDescriptionField.setValue(leasing.getAssetDescription());
        assetDescriptionField.setReadOnly(true);

        TextField assetValueField = new TextField("Asset Value");
        assetValueField.setValue(leasing.getAssetValue());
        assetValueField.setReadOnly(true);

        TextField remainingBalanceField = new TextField("Remaining Balance");
        remainingBalanceField.setValue(leasing.getRemainingBalance());
        remainingBalanceField.setReadOnly(true);

        TextField interestRateField = new TextField("Interest Rate");
        interestRateField.setValue(leasing.getInterestRate());
        interestRateField.setReadOnly(true);

        TextField monthlyPaymentField = new TextField("Monthly Payment");
        monthlyPaymentField.setValue(leasing.getMonthlyPayment());
        monthlyPaymentField.setReadOnly(true);

        DatePicker startDateField = new DatePicker("Start Date");
        startDateField.setValue(leasing.getStartDate());
        startDateField.setReadOnly(true);

        DatePicker endDateField = new DatePicker("End Date");
        endDateField.setValue(leasing.getEndDate());
        endDateField.setReadOnly(true);

        TextField statusField = new TextField("Status");
        statusField.setValue(leasing.getStatus());
        statusField.setReadOnly(true);

        // Add fields to form layout
        formLayout.add(leasingNumberField, assetTypeField, assetDescriptionField, assetValueField, 
                      remainingBalanceField, interestRateField, monthlyPaymentField, startDateField, endDateField, statusField);

        // Create close button
        Button closeButton = UIUtils.createTertiaryButton("Close");
        closeButton.addClickListener(e -> dialog.close());

        // Create layout for button
        HorizontalLayout buttonLayout = new HorizontalLayout(closeButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonLayout.setWidthFull();

        // Create layout for dialog content
        HorizontalLayout dialogLayout = new HorizontalLayout(title, formLayout, buttonLayout);
        dialogLayout.setPadding(true);
        dialogLayout.setSpacing(true);
        dialogLayout.setWidthFull();

        dialog.add(dialogLayout);
        dialog.open();
    }

    /**
     * Generates mock leasing data for testing.
     *
     * @return A list of mock leasings
     */
    private List<Leasing> generateMockLeasings() {
        List<Leasing> leasings = new ArrayList<>();
        leasings.add(new Leasing("LS10012345", "Vehicle", "BMW X5", "$45,000.00", "$35,000.00", "4.5%", "$550.00", LocalDate.now().minusMonths(6), LocalDate.now().plusMonths(30), "Active"));
        leasings.add(new Leasing("LS10012346", "Equipment", "Industrial Machinery", "$65,000.00", "$50,000.00", "5.0%", "$400.00", LocalDate.now().minusMonths(3), LocalDate.now().plusMonths(33), "Active"));
        return leasings;
    }
}