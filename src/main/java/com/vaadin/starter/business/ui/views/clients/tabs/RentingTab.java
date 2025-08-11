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
import com.vaadin.starter.business.ui.views.clients.tabs.models.Renting;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Tab for displaying client renting information.
 */
public class RentingTab extends AbstractTab {

    /**
     * Constructor for RentingTab.
     *
     * @param client The client whose renting information is displayed
     */
    public RentingTab(Client client) {
        super(client);
    }

    @Override
    protected void initContent() {
        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Renting", "1", VaadinIcon.TRUCK));
        summaryCards.add(createSummaryCard("Monthly Payments", "$1,200.00", VaadinIcon.DOLLAR));
        summaryCards.add(createSummaryCard("Contract Duration", "36 months", VaadinIcon.CALENDAR));

        add(summaryCards);

        // Add renting grid
        Grid<Renting> rentingGrid = createRentingGrid();
        add(rentingGrid);
        expand(rentingGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button applyRentingButton = UIUtils.createPrimaryButton("Apply for Renting");
        Button extendContractButton = UIUtils.createTertiaryButton("Extend Contract");
        Button rentingDetailsButton = UIUtils.createTertiaryButton("Renting Details");
        actionButtons.add(applyRentingButton, extendContractButton, rentingDetailsButton);
        actionButtons.setSpacing(true);
        add(actionButtons);
    }

    @Override
    public String getTabName() {
        return "Renting";
    }

    /**
     * Creates the renting grid with all columns and data.
     *
     * @return The configured grid component
     */
    private Grid<Renting> createRentingGrid() {
        Grid<Renting> grid = new Grid<>();
        grid.setItems(generateMockRentings());
        grid.setSizeFull();
        grid.setMinHeight("400px");

        grid.addColumn(Renting::getRentingNumber)
                .setHeader("Renting Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Renting::getAssetType)
                .setHeader("Asset Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Renting::getAssetDescription)
                .setHeader("Asset Description")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(Renting::getMonthlyPayment)
                .setHeader("Monthly Payment")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Renting::getDuration)
                .setHeader("Duration")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(renting -> renting.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setHeader("Start Date")
                .setSortable(true)
                .setComparator(Renting::getStartDate)
                .setWidth("120px");
        grid.addColumn(renting -> renting.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setHeader("End Date")
                .setSortable(true)
                .setComparator(Renting::getEndDate)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(renting -> createStatusBadge(renting.getStatus())))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Renting::getStatus)
                .setWidth("120px");

        // Add item click listener to show renting details dialog
        grid.addItemClickListener(event -> {
            Renting renting = event.getItem();
            showRentingDetails(renting);
        });

        // Add style to indicate rows are clickable
        grid.addClassName("clickable-grid");
        grid.getElement().getStyle().set("cursor", "pointer");

        return grid;
    }

    /**
     * Shows a dialog with detailed information about a renting.
     *
     * @param renting The renting to show details for
     */
    private void showRentingDetails(Renting renting) {
        // Create dialog for renting details
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");

        // Create dialog title
        H3 title = new H3("Renting Details: " + renting.getRentingNumber());

        // Create form layout for renting details
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create fields for renting details
        TextField rentingNumberField = new TextField("Renting Number");
        rentingNumberField.setValue(renting.getRentingNumber());
        rentingNumberField.setReadOnly(true);

        TextField assetTypeField = new TextField("Asset Type");
        assetTypeField.setValue(renting.getAssetType());
        assetTypeField.setReadOnly(true);

        TextField assetDescriptionField = new TextField("Asset Description");
        assetDescriptionField.setValue(renting.getAssetDescription());
        assetDescriptionField.setReadOnly(true);

        TextField monthlyPaymentField = new TextField("Monthly Payment");
        monthlyPaymentField.setValue(renting.getMonthlyPayment());
        monthlyPaymentField.setReadOnly(true);

        TextField durationField = new TextField("Duration");
        durationField.setValue(renting.getDuration());
        durationField.setReadOnly(true);

        DatePicker startDateField = new DatePicker("Start Date");
        startDateField.setValue(renting.getStartDate());
        startDateField.setReadOnly(true);

        DatePicker endDateField = new DatePicker("End Date");
        endDateField.setValue(renting.getEndDate());
        endDateField.setReadOnly(true);

        TextField statusField = new TextField("Status");
        statusField.setValue(renting.getStatus());
        statusField.setReadOnly(true);

        // Add fields to form layout
        formLayout.add(rentingNumberField, assetTypeField, assetDescriptionField, monthlyPaymentField, 
                      durationField, startDateField, endDateField, statusField);

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
     * Generates mock renting data for testing.
     *
     * @return A list of mock rentings
     */
    private List<Renting> generateMockRentings() {
        List<Renting> rentings = new ArrayList<>();
        rentings.add(new Renting("RT10012345", "Vehicle", "Mercedes-Benz Sprinter", "$1,200.00", "36 months", LocalDate.now().minusMonths(6), LocalDate.now().plusMonths(30), "Active"));
        return rentings;
    }
}