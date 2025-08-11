package com.vaadin.starter.business.ui.views.clients.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.clients.ClientManagement.Client;
import com.vaadin.starter.business.ui.views.clients.tabs.models.Account;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Tab for displaying client accounts information.
 */
public class AccountsTab extends AbstractTab {

    /**
     * Constructor for AccountsTab.
     *
     * @param client The client whose accounts are displayed
     */
    public AccountsTab(Client client) {
        super(client);
    }

    @Override
    protected void initContent() {
        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Accounts", "3", VaadinIcon.WALLET));
        summaryCards.add(createSummaryCard("Total Balance", "$45,678.90", VaadinIcon.DOLLAR));
        summaryCards.add(createSummaryCard("Active Accounts", "3", VaadinIcon.CHECK));

        add(summaryCards);

        // Add accounts grid
        Grid<Account> accountsGrid = createAccountsGrid();
        add(accountsGrid);
        expand(accountsGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button newAccountButton = UIUtils.createPrimaryButton("New Account");
        Button transferButton = UIUtils.createTertiaryButton("Transfer");
        Button statementsButton = UIUtils.createTertiaryButton("Statements");
        actionButtons.add(newAccountButton, transferButton, statementsButton);
        actionButtons.setSpacing(true);
        add(actionButtons);
    }

    @Override
    public String getTabName() {
        return "Accounts";
    }

    /**
     * Creates the accounts grid with all columns and data.
     *
     * @return The configured grid component
     */
    private Grid<Account> createAccountsGrid() {
        Grid<Account> grid = new Grid<>();
        grid.setItems(generateMockAccounts());
        grid.setSizeFull();
        grid.setMinHeight("400px");

        grid.addColumn(Account::getAccountNumber)
                .setHeader("Account Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Account::getAccountType)
                .setHeader("Account Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Account::getBalance)
                .setHeader("Balance")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Account::getAvailableBalance)
                .setHeader("Available Balance")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Account::getCurrency)
                .setHeader("Currency")
                .setSortable(true)
                .setWidth("100px");
        grid.addColumn(new ComponentRenderer<>(account -> createStatusBadge(account.getStatus())))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Account::getStatus)
                .setWidth("120px");
        grid.addColumn(account -> account.getOpenDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setHeader("Open Date")
                .setSortable(true)
                .setComparator(Account::getOpenDate)
                .setWidth("120px");

        // Add item click listener to show account details dialog
        grid.addItemClickListener(event -> {
            Account account = event.getItem();
            showAccountDetails(account);
        });

        // Add style to indicate rows are clickable
        grid.addClassName("clickable-grid");
        grid.getElement().getStyle().set("cursor", "pointer");

        return grid;
    }

    /**
     * Shows a dialog with detailed information about an account.
     *
     * @param account The account to show details for
     */
    private void showAccountDetails(Account account) {
        // Create dialog for account details
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");

        // Create dialog title
        H3 title = new H3("Account Details: " + account.getAccountNumber());

        // Create form layout for account details
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create fields for account details
        TextField accountNumberField = new TextField("Account Number");
        accountNumberField.setValue(account.getAccountNumber());
        accountNumberField.setReadOnly(true);

        TextField accountTypeField = new TextField("Account Type");
        accountTypeField.setValue(account.getAccountType());
        accountTypeField.setReadOnly(true);

        TextField balanceField = new TextField("Balance");
        balanceField.setValue(account.getBalance());
        balanceField.setReadOnly(true);

        TextField availableBalanceField = new TextField("Available Balance");
        availableBalanceField.setValue(account.getAvailableBalance());
        availableBalanceField.setReadOnly(true);

        TextField currencyField = new TextField("Currency");
        currencyField.setValue(account.getCurrency());
        currencyField.setReadOnly(true);

        TextField statusField = new TextField("Status");
        statusField.setValue(account.getStatus());
        statusField.setReadOnly(true);

        TextField openDateField = new TextField("Open Date");
        openDateField.setValue(account.getOpenDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        openDateField.setReadOnly(true);

        // Add fields to form
        formLayout.add(accountNumberField, accountTypeField, balanceField, availableBalanceField, 
                      currencyField, statusField, openDateField);

        // Add close button
        Button closeButton = UIUtils.createTertiaryButton("Close");
        closeButton.addClickListener(e -> dialog.close());

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(closeButton);
        buttonLayout.setSpacing(true);
        buttonLayout.getStyle().set("margin-top", "1rem");

        // Create layout for dialog content
        HorizontalLayout dialogLayout = new HorizontalLayout(title, formLayout, buttonLayout);
        dialogLayout.setPadding(true);
        dialogLayout.setSpacing(true);
        dialogLayout.setWidthFull();

        dialog.add(dialogLayout);
        dialog.open();
    }

    /**
     * Generates mock account data for testing.
     *
     * @return A list of mock accounts
     */
    private List<Account> generateMockAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account("1001234567", "Checking Account", "$12,345.67", "$12,345.67", "USD", "Active", LocalDate.now().minusYears(2)));
        accounts.add(new Account("1001234568", "Savings Account", "$30,000.00", "$30,000.00", "USD", "Active", LocalDate.now().minusYears(1)));
        accounts.add(new Account("1001234569", "Money Market", "$3,333.23", "$3,333.23", "USD", "Active", LocalDate.now().minusMonths(6)));
        return accounts;
    }
}