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
import com.vaadin.starter.business.ui.views.clients.tabs.models.Contract;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Tab for displaying client contracts information.
 */
public class ContractsTab extends AbstractTab {

    /**
     * Constructor for ContractsTab.
     *
     * @param client The client whose contracts are displayed
     */
    public ContractsTab(Client client) {
        super(client);
    }

    @Override
    protected void initContent() {
        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Contracts", "3", VaadinIcon.FILE_TEXT));
        summaryCards.add(createSummaryCard("Active Contracts", "2", VaadinIcon.CHECK));
        summaryCards.add(createSummaryCard("Contract Value", "$7,700.00", VaadinIcon.DOLLAR));

        add(summaryCards);

        // Add contracts grid
        Grid<Contract> contractsGrid = createContractsGrid();
        add(contractsGrid);
        expand(contractsGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button newContractButton = UIUtils.createPrimaryButton("New Contract");
        Button renewButton = UIUtils.createTertiaryButton("Renew Contract");
        Button historyButton = UIUtils.createTertiaryButton("Contract History");
        actionButtons.add(newContractButton, renewButton, historyButton);
        actionButtons.setSpacing(true);
        add(actionButtons);
    }

    @Override
    public String getTabName() {
        return "Contracts";
    }

    /**
     * Creates the contracts grid with all columns and data.
     *
     * @return The configured grid component
     */
    private Grid<Contract> createContractsGrid() {
        Grid<Contract> grid = new Grid<>();
        grid.setItems(generateMockContracts());
        grid.setSizeFull();
        grid.setMinHeight("400px");

        grid.addColumn(Contract::getContractId)
                .setHeader("Contract ID")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Contract::getType)
                .setHeader("Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Contract::getValue)
                .setHeader("Value")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(contract -> createStatusBadge(contract.getStatus())))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Contract::getStatus)
                .setWidth("120px");
        grid.addColumn(contract -> UIUtils.formatDate(contract.getStartDate()))
                .setHeader("Start Date")
                .setSortable(true)
                .setComparator(Contract::getStartDate)
                .setWidth("120px");
        grid.addColumn(contract -> UIUtils.formatDate(contract.getEndDate()))
                .setHeader("End Date")
                .setSortable(true)
                .setComparator(Contract::getEndDate)
                .setWidth("120px");

        // Add item click listener to show contract details dialog
        grid.addItemClickListener(event -> {
            Contract contract = event.getItem();
            showContractDetails(contract);
        });

        // Add style to indicate rows are clickable
        grid.addClassName("clickable-grid");
        grid.getElement().getStyle().set("cursor", "pointer");

        return grid;
    }

    /**
     * Shows a dialog with detailed information about a contract.
     *
     * @param contract The contract to show details for
     */
    private void showContractDetails(Contract contract) {
        // Create dialog for contract details
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");

        // Create dialog title
        H3 title = new H3("Contract Details: " + contract.getContractId());

        // Create form layout for contract details
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create fields for contract details
        TextField contractIdField = new TextField("Contract ID");
        contractIdField.setValue(contract.getContractId());
        contractIdField.setReadOnly(true);

        TextField clientIdField = new TextField("Client ID");
        clientIdField.setValue(contract.getClientId());
        clientIdField.setReadOnly(true);

        TextField clientNameField = new TextField("Client Name");
        clientNameField.setValue(contract.getClientName());
        clientNameField.setReadOnly(true);

        TextField typeField = new TextField("Type");
        typeField.setValue(contract.getType());
        typeField.setReadOnly(true);

        TextField valueField = new TextField("Value");
        valueField.setValue(contract.getValue());
        valueField.setReadOnly(true);

        TextField statusField = new TextField("Status");
        statusField.setValue(contract.getStatus());
        statusField.setReadOnly(true);

        DatePicker startDateField = new DatePicker("Start Date");
        startDateField.setValue(contract.getStartDate());
        startDateField.setReadOnly(true);

        DatePicker endDateField = new DatePicker("End Date");
        endDateField.setValue(contract.getEndDate());
        endDateField.setReadOnly(true);

        // Add fields to form layout
        formLayout.add(contractIdField, clientIdField, clientNameField, typeField, 
                      valueField, statusField, startDateField, endDateField);

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
     * Generates mock contract data for testing.
     *
     * @return A list of mock contracts
     */
    private List<Contract> generateMockContracts() {
        List<Contract> contracts = new ArrayList<>();

        // Create contracts for the current client
        contracts.add(new Contract(
            "CNT001", 
            client.getClientId(), 
            client.getName(), 
            "Standard", 
            "$1,200.00", 
            "Active", 
            LocalDate.now().minusMonths(6), 
            LocalDate.now().plusMonths(6), 
            LocalDateTime.now().minusDays(180), 
            LocalDateTime.now().minusDays(5)
        ));

        contracts.add(new Contract(
            "CNT002", 
            client.getClientId(), 
            client.getName(), 
            "Premium", 
            "$3,500.00", 
            "Active", 
            LocalDate.now().minusMonths(2), 
            LocalDate.now().plusMonths(10), 
            LocalDateTime.now().minusDays(60), 
            LocalDateTime.now().minusDays(2)
        ));

        contracts.add(new Contract(
            "CNT003", 
            client.getClientId(), 
            client.getName(), 
            "Standard", 
            "$3,000.00", 
            "Expired", 
            LocalDate.now().minusMonths(12), 
            LocalDate.now().minusMonths(1), 
            LocalDateTime.now().minusDays(365), 
            LocalDateTime.now().minusDays(30)
        ));

        return contracts;
    }
}