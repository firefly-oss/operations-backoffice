package com.vaadin.starter.business.ui.views.clients.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.clients.ClientManagement.Client;
import com.vaadin.starter.business.ui.views.clients.tabs.models.Confirming;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Tab for displaying client confirming information.
 */
public class ConfirmingTab extends AbstractTab {

    /**
     * Constructor for ConfirmingTab.
     *
     * @param client The client whose confirming data is displayed
     */
    public ConfirmingTab(Client client) {
        super(client);
    }

    @Override
    protected void initContent() {
        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Suppliers", "3", VaadinIcon.USERS));
        summaryCards.add(createSummaryCard("Total Amount", "$28,500.00", VaadinIcon.DOLLAR));
        summaryCards.add(createSummaryCard("Payment Terms", "60 days", VaadinIcon.CALENDAR_CLOCK));

        add(summaryCards);

        // Add confirming grid
        Grid<Confirming> confirmingGrid = createConfirmingGrid();
        add(confirmingGrid);
        expand(confirmingGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button newSupplierButton = UIUtils.createPrimaryButton("New Supplier");
        Button paymentRequestButton = UIUtils.createTertiaryButton("Payment Request");
        Button confirmingDetailsButton = UIUtils.createTertiaryButton("Confirming Details");
        actionButtons.add(newSupplierButton, paymentRequestButton, confirmingDetailsButton);
        actionButtons.setSpacing(true);
        add(actionButtons);
    }

    @Override
    public String getTabName() {
        return "Confirming";
    }

    /**
     * Creates the confirming grid with all columns and data.
     *
     * @return The configured grid component
     */
    private Grid<Confirming> createConfirmingGrid() {
        Grid<Confirming> grid = new Grid<>();
        grid.setItems(generateMockConfirmings());
        grid.setSizeFull();
        grid.setMinHeight("400px");

        grid.addColumn(Confirming::getSupplierNumber)
                .setHeader("Supplier Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Confirming::getSupplierName)
                .setHeader("Supplier Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(Confirming::getInvoiceNumber)
                .setHeader("Invoice Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Confirming::getInvoiceAmount)
                .setHeader("Invoice Amount")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(confirming -> confirming.getInvoiceDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setHeader("Invoice Date")
                .setSortable(true)
                .setComparator(Confirming::getInvoiceDate)
                .setWidth("120px");
        grid.addColumn(confirming -> confirming.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setHeader("Due Date")
                .setSortable(true)
                .setComparator(Confirming::getDueDate)
                .setWidth("120px");
        grid.addColumn(Confirming::getPaymentTerms)
                .setHeader("Payment Terms")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(confirming -> createStatusBadge(confirming.getStatus())))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Confirming::getStatus)
                .setWidth("120px");

        // Add item click listener to show confirming details dialog
        grid.addItemClickListener(event -> {
            Confirming confirming = event.getItem();
            showConfirmingDetails(confirming);
        });

        // Add style to indicate rows are clickable
        grid.addClassName("clickable-grid");
        grid.getElement().getStyle().set("cursor", "pointer");

        return grid;
    }

    /**
     * Shows a dialog with detailed information about a confirming.
     *
     * @param confirming The confirming to show details for
     */
    private void showConfirmingDetails(Confirming confirming) {
        // Create dialog for confirming details
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");

        // Create dialog title
        H3 title = new H3("Confirming Details: " + confirming.getSupplierNumber());

        // Create form layout for confirming details
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create fields for confirming details
        TextField supplierNumberField = new TextField("Supplier Number");
        supplierNumberField.setValue(confirming.getSupplierNumber());
        supplierNumberField.setReadOnly(true);

        TextField supplierNameField = new TextField("Supplier Name");
        supplierNameField.setValue(confirming.getSupplierName());
        supplierNameField.setReadOnly(true);

        TextField invoiceNumberField = new TextField("Invoice Number");
        invoiceNumberField.setValue(confirming.getInvoiceNumber());
        invoiceNumberField.setReadOnly(true);

        TextField invoiceAmountField = new TextField("Invoice Amount");
        invoiceAmountField.setValue(confirming.getInvoiceAmount());
        invoiceAmountField.setReadOnly(true);

        DatePicker invoiceDateField = new DatePicker("Invoice Date");
        invoiceDateField.setValue(confirming.getInvoiceDate());
        invoiceDateField.setReadOnly(true);

        DatePicker dueDateField = new DatePicker("Due Date");
        dueDateField.setValue(confirming.getDueDate());
        dueDateField.setReadOnly(true);

        TextField paymentTermsField = new TextField("Payment Terms");
        paymentTermsField.setValue(confirming.getPaymentTerms());
        paymentTermsField.setReadOnly(true);

        TextField statusField = new TextField("Status");
        statusField.setValue(confirming.getStatus());
        statusField.setReadOnly(true);

        // Add fields to form layout
        formLayout.add(supplierNumberField, supplierNameField, invoiceNumberField, invoiceAmountField, 
                      invoiceDateField, dueDateField, paymentTermsField, statusField);

        // Create close button
        Button closeButton = UIUtils.createTertiaryButton("Close");
        closeButton.addClickListener(e -> dialog.close());

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(closeButton);
        buttonLayout.setSpacing(true);
        buttonLayout.getStyle().set("margin-top", "1rem");

        // Create layout for dialog content
        VerticalLayout dialogLayout = new VerticalLayout(title, formLayout, buttonLayout);
        dialogLayout.setPadding(true);
        dialogLayout.setSpacing(true);

        dialog.add(dialogLayout);
        dialog.open();
    }

    /**
     * Generates mock confirming data for testing.
     *
     * @return A list of mock confirmings
     */
    private List<Confirming> generateMockConfirmings() {
        List<Confirming> confirmings = new ArrayList<>();
        confirmings.add(new Confirming("SUP10012345", "Office Supplies Inc", "INV-OS-12345", "$12,500.00", LocalDate.now().minusDays(10), LocalDate.now().plusDays(50), "60 days", "Active"));
        confirmings.add(new Confirming("SUP10012346", "IT Hardware Solutions", "INV-IT-54321", "$8,500.00", LocalDate.now().minusDays(5), LocalDate.now().plusDays(55), "60 days", "Active"));
        confirmings.add(new Confirming("SUP10012347", "Logistics Partners", "INV-LP-98765", "$7,500.00", LocalDate.now().minusDays(3), LocalDate.now().plusDays(57), "60 days", "Active"));
        return confirmings;
    }
}