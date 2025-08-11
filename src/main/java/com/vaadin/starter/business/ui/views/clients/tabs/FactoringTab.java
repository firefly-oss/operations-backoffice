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
import com.vaadin.starter.business.ui.views.clients.tabs.models.Factoring;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Tab for displaying client factoring information.
 */
public class FactoringTab extends AbstractTab {

    /**
     * Constructor for FactoringTab.
     *
     * @param client The client whose factoring data is displayed
     */
    public FactoringTab(Client client) {
        super(client);
    }

    @Override
    protected void initContent() {
        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Invoices", "5", VaadinIcon.FILE_TEXT_O));
        summaryCards.add(createSummaryCard("Total Amount", "$45,000.00", VaadinIcon.DOLLAR));
        summaryCards.add(createSummaryCard("Advance Rate", "80%", VaadinIcon.CHART));

        add(summaryCards);

        // Add factoring grid
        Grid<Factoring> factoringGrid = createFactoringGrid();
        add(factoringGrid);
        expand(factoringGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button newInvoiceButton = UIUtils.createPrimaryButton("New Invoice");
        Button advanceRequestButton = UIUtils.createTertiaryButton("Request Advance");
        Button factoringDetailsButton = UIUtils.createTertiaryButton("Factoring Details");
        actionButtons.add(newInvoiceButton, advanceRequestButton, factoringDetailsButton);
        actionButtons.setSpacing(true);
        add(actionButtons);
    }

    @Override
    public String getTabName() {
        return "Factoring";
    }

    /**
     * Creates the factoring grid with all columns and data.
     *
     * @return The configured grid component
     */
    private Grid<Factoring> createFactoringGrid() {
        Grid<Factoring> grid = new Grid<>();
        grid.setItems(generateMockFactorings());
        grid.setSizeFull();
        grid.setMinHeight("400px");

        grid.addColumn(Factoring::getInvoiceNumber)
                .setHeader("Invoice Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Factoring::getDebtor)
                .setHeader("Debtor")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(Factoring::getInvoiceAmount)
                .setHeader("Invoice Amount")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Factoring::getAdvanceAmount)
                .setHeader("Advance Amount")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Factoring::getAdvanceRate)
                .setHeader("Advance Rate")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(factoring -> factoring.getInvoiceDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setHeader("Invoice Date")
                .setSortable(true)
                .setComparator(Factoring::getInvoiceDate)
                .setWidth("120px");
        grid.addColumn(factoring -> factoring.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setHeader("Due Date")
                .setSortable(true)
                .setComparator(Factoring::getDueDate)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(factoring -> createStatusBadge(factoring.getStatus())))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Factoring::getStatus)
                .setWidth("120px");

        // Add item click listener to show factoring details dialog
        grid.addItemClickListener(event -> {
            Factoring factoring = event.getItem();
            showFactoringDetails(factoring);
        });

        // Add style to indicate rows are clickable
        grid.addClassName("clickable-grid");
        grid.getElement().getStyle().set("cursor", "pointer");

        return grid;
    }

    /**
     * Shows a dialog with detailed information about a factoring.
     *
     * @param factoring The factoring to show details for
     */
    private void showFactoringDetails(Factoring factoring) {
        // Create dialog for factoring details
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");

        // Create dialog title
        H3 title = new H3("Factoring Details: " + factoring.getInvoiceNumber());

        // Create form layout for factoring details
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create fields for factoring details
        TextField invoiceNumberField = new TextField("Invoice Number");
        invoiceNumberField.setValue(factoring.getInvoiceNumber());
        invoiceNumberField.setReadOnly(true);

        TextField debtorField = new TextField("Debtor");
        debtorField.setValue(factoring.getDebtor());
        debtorField.setReadOnly(true);

        TextField invoiceAmountField = new TextField("Invoice Amount");
        invoiceAmountField.setValue(factoring.getInvoiceAmount());
        invoiceAmountField.setReadOnly(true);

        TextField advanceAmountField = new TextField("Advance Amount");
        advanceAmountField.setValue(factoring.getAdvanceAmount());
        advanceAmountField.setReadOnly(true);

        TextField advanceRateField = new TextField("Advance Rate");
        advanceRateField.setValue(factoring.getAdvanceRate());
        advanceRateField.setReadOnly(true);

        DatePicker invoiceDateField = new DatePicker("Invoice Date");
        invoiceDateField.setValue(factoring.getInvoiceDate());
        invoiceDateField.setReadOnly(true);

        DatePicker dueDateField = new DatePicker("Due Date");
        dueDateField.setValue(factoring.getDueDate());
        dueDateField.setReadOnly(true);

        TextField statusField = new TextField("Status");
        statusField.setValue(factoring.getStatus());
        statusField.setReadOnly(true);

        // Add fields to form layout
        formLayout.add(invoiceNumberField, debtorField, invoiceAmountField, advanceAmountField, 
                      advanceRateField, invoiceDateField, dueDateField, statusField);

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
     * Generates mock factoring data for testing.
     *
     * @return A list of mock factorings
     */
    private List<Factoring> generateMockFactorings() {
        List<Factoring> factorings = new ArrayList<>();
        factorings.add(new Factoring("INV10012345", "Acme Corporation", "$12,000.00", "$9,600.00", "80%", LocalDate.now().minusDays(15), LocalDate.now().plusDays(45), "Active"));
        factorings.add(new Factoring("INV10012346", "Global Traders Inc", "$8,500.00", "$6,800.00", "80%", LocalDate.now().minusDays(10), LocalDate.now().plusDays(50), "Active"));
        factorings.add(new Factoring("INV10012347", "Tech Innovations Ltd", "$15,000.00", "$12,000.00", "80%", LocalDate.now().minusDays(5), LocalDate.now().plusDays(55), "Active"));
        factorings.add(new Factoring("INV10012348", "Green Energy Solutions", "$5,500.00", "$4,400.00", "80%", LocalDate.now().minusDays(3), LocalDate.now().plusDays(57), "Active"));
        factorings.add(new Factoring("INV10012349", "Australian Mining Ltd", "$4,000.00", "$3,200.00", "80%", LocalDate.now().minusDays(1), LocalDate.now().plusDays(59), "Active"));
        return factorings;
    }
}
