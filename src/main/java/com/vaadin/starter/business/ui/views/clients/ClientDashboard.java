package com.vaadin.starter.business.ui.views.clients;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeColor;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeShape;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeSize;
import com.vaadin.starter.business.ui.views.ViewFrame;
import com.vaadin.starter.business.ui.views.clients.ClientManagement.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@PageTitle("Client Dashboard")
@Route(value = "clients/dashboard", layout = MainLayout.class)
public class ClientDashboard extends ViewFrame implements HasUrlParameter<String> {

    private Client client;
    private Div contentArea;
    private String clientId;

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {
        this.clientId = parameter;
        // In a real application, you would fetch the client from a service
        // For this example, we'll create a mock client
        this.client = createMockClient(clientId);
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createHeader(), createTabs());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createHeader() {
        // Create back button
        Button backButton = UIUtils.createTertiaryButton("Back", VaadinIcon.ARROW_LEFT);
        backButton.addClickListener(e -> navigateBack());
        backButton.getStyle().set("margin-right", "1rem");

        // Create header text
        H3 header = new H3("Client Dashboard: " + client.getName() + " (" + client.getClientId() + ")");
        header.getStyle().set("margin", "0");

        // Create layout for header with back button
        HorizontalLayout headerLayout = new HorizontalLayout(backButton, header);
        headerLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        headerLayout.setPadding(true);
        headerLayout.setSpacing(true);

        return headerLayout;
    }

    private void navigateBack() {
        // Navigate back to the client management view
        getUI().ifPresent(ui -> ui.navigate("clients/management"));
    }

    private Component createTabs() {
        Tab accountsTab = new Tab("Accounts");
        Tab cardsTab = new Tab("Cards");
        Tab lendingTab = new Tab("Lending");

        Tabs tabs = new Tabs(accountsTab, cardsTab, lendingTab);
        tabs.getStyle().set("margin", "0 1rem");

        contentArea = new Div();
        contentArea.setSizeFull();
        contentArea.getStyle().set("padding", "1rem");

        // Show accounts tab by default
        showAccountsTab();

        tabs.addSelectedChangeListener(event -> {
            contentArea.removeAll();
            if (event.getSelectedTab().equals(accountsTab)) {
                showAccountsTab();
            } else if (event.getSelectedTab().equals(cardsTab)) {
                showCardsTab();
            } else if (event.getSelectedTab().equals(lendingTab)) {
                showLendingTab();
            }
        });

        VerticalLayout tabsLayout = new VerticalLayout(tabs, contentArea);
        tabsLayout.setPadding(false);
        tabsLayout.setSpacing(false);
        tabsLayout.setSizeFull();
        return tabsLayout;
    }

    private void showAccountsTab() {
        VerticalLayout accountsLayout = new VerticalLayout();
        accountsLayout.setPadding(false);
        accountsLayout.setSpacing(true);
        accountsLayout.setSizeFull();

        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Accounts", "3", VaadinIcon.WALLET));
        summaryCards.add(createSummaryCard("Total Balance", "$45,678.90", VaadinIcon.DOLLAR));
        summaryCards.add(createSummaryCard("Active Accounts", "3", VaadinIcon.CHECK));

        accountsLayout.add(summaryCards);

        // Add accounts grid
        Grid<Account> accountsGrid = createAccountsGrid();
        accountsLayout.add(accountsGrid);
        accountsLayout.expand(accountsGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button newAccountButton = UIUtils.createPrimaryButton("New Account");
        Button transferButton = UIUtils.createTertiaryButton("Transfer");
        Button statementsButton = UIUtils.createTertiaryButton("Statements");
        actionButtons.add(newAccountButton, transferButton, statementsButton);
        actionButtons.setSpacing(true);
        accountsLayout.add(actionButtons);

        contentArea.add(accountsLayout);
    }

    private void showCardsTab() {
        VerticalLayout cardsLayout = new VerticalLayout();
        cardsLayout.setPadding(false);
        cardsLayout.setSpacing(true);
        cardsLayout.setSizeFull();

        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Cards", "2", VaadinIcon.CREDIT_CARD));
        summaryCards.add(createSummaryCard("Available Credit", "$15,000.00", VaadinIcon.DOLLAR));
        summaryCards.add(createSummaryCard("Current Balance", "$2,345.67", VaadinIcon.CHART));

        cardsLayout.add(summaryCards);

        // Add cards grid
        Grid<Card> cardsGrid = createCardsGrid();
        cardsLayout.add(cardsGrid);
        cardsLayout.expand(cardsGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button newCardButton = UIUtils.createPrimaryButton("New Card");
        Button reportLostButton = UIUtils.createTertiaryButton("Report Lost/Stolen");
        Button statementsButton = UIUtils.createTertiaryButton("Statements");
        actionButtons.add(newCardButton, reportLostButton, statementsButton);
        actionButtons.setSpacing(true);
        cardsLayout.add(actionButtons);

        contentArea.add(cardsLayout);
    }

    private void showLendingTab() {
        VerticalLayout lendingLayout = new VerticalLayout();
        lendingLayout.setPadding(false);
        lendingLayout.setSpacing(true);
        lendingLayout.setSizeFull();

        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Loans", "1", VaadinIcon.PIGGY_BANK));
        summaryCards.add(createSummaryCard("Outstanding Balance", "$120,500.00", VaadinIcon.DOLLAR));
        summaryCards.add(createSummaryCard("Next Payment", "$1,250.00", VaadinIcon.CALENDAR));

        lendingLayout.add(summaryCards);

        // Add loans grid
        Grid<Loan> loansGrid = createLoansGrid();
        lendingLayout.add(loansGrid);
        lendingLayout.expand(loansGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button applyLoanButton = UIUtils.createPrimaryButton("Apply for Loan");
        Button makePaymentButton = UIUtils.createTertiaryButton("Make Payment");
        Button loanDetailsButton = UIUtils.createTertiaryButton("Loan Details");
        actionButtons.add(applyLoanButton, makePaymentButton, loanDetailsButton);
        actionButtons.setSpacing(true);
        lendingLayout.add(actionButtons);

        contentArea.add(lendingLayout);
    }

    private Component createSummaryCard(String title, String value, VaadinIcon icon) {
        Div card = new Div();
        card.getStyle().set("background-color", "var(--lumo-base-color)");
        card.getStyle().set("border-radius", "var(--lumo-border-radius)");
        card.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        card.getStyle().set("padding", "1rem");
        card.getStyle().set("width", "250px");
        card.getStyle().set("height", "120px");
        card.getStyle().set("display", "flex");
        card.getStyle().set("flex-direction", "column");
        card.getStyle().set("justify-content", "center");
        card.getStyle().set("align-items", "center");

        Span iconElement = new Span(icon.create());
        iconElement.getStyle().set("font-size", "24px");
        iconElement.getStyle().set("color", "var(--lumo-primary-color)");
        iconElement.getStyle().set("margin-bottom", "0.5rem");

        Span titleElement = new Span(title);
        titleElement.getStyle().set("color", "var(--lumo-secondary-text-color)");
        titleElement.getStyle().set("margin-bottom", "0.5rem");

        Span valueElement = new Span(value);
        valueElement.getStyle().set("font-size", "24px");
        valueElement.getStyle().set("font-weight", "bold");

        card.add(iconElement, titleElement, valueElement);
        return card;
    }

    private Grid<Account> createAccountsGrid() {
        Grid<Account> grid = new Grid<>();
        grid.setItems(generateMockAccounts());
        grid.setSizeFull();

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

        DatePicker openDateField = new DatePicker("Open Date");
        openDateField.setValue(account.getOpenDate());
        openDateField.setReadOnly(true);

        // Add fields to form layout
        formLayout.add(accountNumberField, accountTypeField, balanceField, availableBalanceField, 
                      currencyField, statusField, openDateField);

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

    private Grid<Card> createCardsGrid() {
        Grid<Card> grid = new Grid<>();
        grid.setItems(generateMockCards());
        grid.setSizeFull();

        grid.addColumn(Card::getCardNumber)
                .setHeader("Card Number")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(Card::getCardType)
                .setHeader("Card Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Card::getLinkedAccount)
                .setHeader("Linked Account")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Card::getCardholderName)
                .setHeader("Cardholder")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Card::getExpiryDate)
                .setHeader("Expiry Date")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Card::getCreditLimit)
                .setHeader("Credit Limit")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Card::getAvailableCredit)
                .setHeader("Available Credit")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new ComponentRenderer<>(card -> createStatusBadge(card.getStatus())))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Card::getStatus)
                .setWidth("120px");

        // Add item click listener to show card details dialog
        grid.addItemClickListener(event -> {
            Card card = event.getItem();
            showCardDetails(card);
        });

        // Add style to indicate rows are clickable
        grid.addClassName("clickable-grid");
        grid.getElement().getStyle().set("cursor", "pointer");

        return grid;
    }

    private void showCardDetails(Card card) {
        // Create dialog for card details
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");

        // Create dialog title
        H3 title = new H3("Card Details: " + card.getCardNumber());

        // Create form layout for card details
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create fields for card details
        TextField cardNumberField = new TextField("Card Number");
        cardNumberField.setValue(card.getCardNumber());
        cardNumberField.setReadOnly(true);

        TextField cardTypeField = new TextField("Card Type");
        cardTypeField.setValue(card.getCardType());
        cardTypeField.setReadOnly(true);

        TextField linkedAccountField = new TextField("Linked Account");
        linkedAccountField.setValue(card.getLinkedAccount());
        linkedAccountField.setReadOnly(true);

        TextField cardholderNameField = new TextField("Cardholder Name");
        cardholderNameField.setValue(card.getCardholderName());
        cardholderNameField.setReadOnly(true);

        TextField expiryDateField = new TextField("Expiry Date");
        expiryDateField.setValue(card.getExpiryDate());
        expiryDateField.setReadOnly(true);

        TextField creditLimitField = new TextField("Credit Limit");
        creditLimitField.setValue(card.getCreditLimit());
        creditLimitField.setReadOnly(true);

        TextField availableCreditField = new TextField("Available Credit");
        availableCreditField.setValue(card.getAvailableCredit());
        availableCreditField.setReadOnly(true);

        TextField statusField = new TextField("Status");
        statusField.setValue(card.getStatus());
        statusField.setReadOnly(true);

        // Add fields to form layout
        formLayout.add(cardNumberField, cardTypeField, linkedAccountField, cardholderNameField, 
                      expiryDateField, creditLimitField, availableCreditField, statusField);

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

    private Grid<Loan> createLoansGrid() {
        Grid<Loan> grid = new Grid<>();
        grid.setItems(generateMockLoans());
        grid.setSizeFull();

        grid.addColumn(Loan::getLoanNumber)
                .setHeader("Loan Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Loan::getLoanType)
                .setHeader("Loan Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Loan::getOriginalAmount)
                .setHeader("Original Amount")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Loan::getCurrentBalance)
                .setHeader("Current Balance")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Loan::getInterestRate)
                .setHeader("Interest Rate")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Loan::getMonthlyPayment)
                .setHeader("Monthly Payment")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(loan -> loan.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setHeader("Start Date")
                .setSortable(true)
                .setComparator(Loan::getStartDate)
                .setWidth("120px");
        grid.addColumn(loan -> loan.getMaturityDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setHeader("Maturity Date")
                .setSortable(true)
                .setComparator(Loan::getMaturityDate)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(loan -> createStatusBadge(loan.getStatus())))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Loan::getStatus)
                .setWidth("120px");

        // Add item click listener to show loan details dialog
        grid.addItemClickListener(event -> {
            Loan loan = event.getItem();
            showLoanDetails(loan);
        });

        // Add style to indicate rows are clickable
        grid.addClassName("clickable-grid");
        grid.getElement().getStyle().set("cursor", "pointer");

        return grid;
    }

    private void showLoanDetails(Loan loan) {
        // Create dialog for loan details
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");

        // Create dialog title
        H3 title = new H3("Loan Details: " + loan.getLoanNumber());

        // Create form layout for loan details
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create fields for loan details
        TextField loanNumberField = new TextField("Loan Number");
        loanNumberField.setValue(loan.getLoanNumber());
        loanNumberField.setReadOnly(true);

        TextField loanTypeField = new TextField("Loan Type");
        loanTypeField.setValue(loan.getLoanType());
        loanTypeField.setReadOnly(true);

        TextField originalAmountField = new TextField("Original Amount");
        originalAmountField.setValue(loan.getOriginalAmount());
        originalAmountField.setReadOnly(true);

        TextField currentBalanceField = new TextField("Current Balance");
        currentBalanceField.setValue(loan.getCurrentBalance());
        currentBalanceField.setReadOnly(true);

        TextField interestRateField = new TextField("Interest Rate");
        interestRateField.setValue(loan.getInterestRate());
        interestRateField.setReadOnly(true);

        TextField monthlyPaymentField = new TextField("Monthly Payment");
        monthlyPaymentField.setValue(loan.getMonthlyPayment());
        monthlyPaymentField.setReadOnly(true);

        DatePicker startDateField = new DatePicker("Start Date");
        startDateField.setValue(loan.getStartDate());
        startDateField.setReadOnly(true);

        DatePicker maturityDateField = new DatePicker("Maturity Date");
        maturityDateField.setValue(loan.getMaturityDate());
        maturityDateField.setReadOnly(true);

        TextField statusField = new TextField("Status");
        statusField.setValue(loan.getStatus());
        statusField.setReadOnly(true);

        // Add fields to form layout
        formLayout.add(loanNumberField, loanTypeField, originalAmountField, currentBalanceField, 
                      interestRateField, monthlyPaymentField, startDateField, maturityDateField, statusField);

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

    private Component createStatusBadge(String status) {
        BadgeColor color;
        switch (status) {
            case "Active":
            case "Completed":
            case "Approved":
                color = BadgeColor.SUCCESS;
                break;
            case "Inactive":
            case "Expired":
            case "Closed":
                color = BadgeColor.ERROR_PRIMARY;
                break;
            case "Blocked":
            case "Rejected":
            case "Failed":
                color = BadgeColor.ERROR;
                break;
            case "Pending":
            case "Processing":
                color = BadgeColor.CONTRAST;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Client createMockClient(String clientId) {
        // In a real application, you would fetch the client from a service
        return new Client(
            clientId,
            "John Smith",
            "john.smith@example.com",
            "+1-555-123-4567",
            "123 Main St, New York, NY 10001",
            "Individual",
            "Active",
            LocalDateTime.now().minusDays(30),
            LocalDateTime.now().minusDays(5)
        );
    }

    private List<Account> generateMockAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account("1001234567", "Checking Account", "$12,345.67", "$12,345.67", "USD", "Active", LocalDate.now().minusYears(2)));
        accounts.add(new Account("1001234568", "Savings Account", "$30,000.00", "$30,000.00", "USD", "Active", LocalDate.now().minusYears(1)));
        accounts.add(new Account("1001234569", "Money Market", "$3,333.23", "$3,333.23", "USD", "Active", LocalDate.now().minusMonths(6)));
        return accounts;
    }

    private List<Card> generateMockCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("4111 **** **** 1234", "Credit Card", "1001234567", "John Smith", "12/25", "$10,000.00", "$7,654.33", "Active"));
        cards.add(new Card("5500 **** **** 5678", "Debit Card", "1001234568", "John Smith", "09/24", "N/A", "N/A", "Active"));
        return cards;
    }

    private List<Loan> generateMockLoans() {
        List<Loan> loans = new ArrayList<>();
        loans.add(new Loan("L10012345", "Mortgage", "$250,000.00", "$120,500.00", "3.25%", "$1,250.00", LocalDate.now().minusYears(5), LocalDate.now().plusYears(25), "Active"));
        return loans;
    }

    // Model classes
    public static class Account {
        private String accountNumber;
        private String accountType;
        private String balance;
        private String availableBalance;
        private String currency;
        private String status;
        private LocalDate openDate;

        public Account(String accountNumber, String accountType, String balance, String availableBalance, String currency, String status, LocalDate openDate) {
            this.accountNumber = accountNumber;
            this.accountType = accountType;
            this.balance = balance;
            this.availableBalance = availableBalance;
            this.currency = currency;
            this.status = status;
            this.openDate = openDate;
        }

        public String getAccountNumber() { return accountNumber; }
        public String getAccountType() { return accountType; }
        public String getBalance() { return balance; }
        public String getAvailableBalance() { return availableBalance; }
        public String getCurrency() { return currency; }
        public String getStatus() { return status; }
        public LocalDate getOpenDate() { return openDate; }
    }

    public static class Card {
        private String cardNumber;
        private String cardType;
        private String linkedAccount;
        private String cardholderName;
        private String expiryDate;
        private String creditLimit;
        private String availableCredit;
        private String status;

        public Card(String cardNumber, String cardType, String linkedAccount, String cardholderName, String expiryDate, String creditLimit, String availableCredit, String status) {
            this.cardNumber = cardNumber;
            this.cardType = cardType;
            this.linkedAccount = linkedAccount;
            this.cardholderName = cardholderName;
            this.expiryDate = expiryDate;
            this.creditLimit = creditLimit;
            this.availableCredit = availableCredit;
            this.status = status;
        }

        public String getCardNumber() { return cardNumber; }
        public String getCardType() { return cardType; }
        public String getLinkedAccount() { return linkedAccount; }
        public String getCardholderName() { return cardholderName; }
        public String getExpiryDate() { return expiryDate; }
        public String getCreditLimit() { return creditLimit; }
        public String getAvailableCredit() { return availableCredit; }
        public String getStatus() { return status; }
    }

    public static class Loan {
        private String loanNumber;
        private String loanType;
        private String originalAmount;
        private String currentBalance;
        private String interestRate;
        private String monthlyPayment;
        private LocalDate startDate;
        private LocalDate maturityDate;
        private String status;

        public Loan(String loanNumber, String loanType, String originalAmount, String currentBalance, String interestRate, String monthlyPayment, LocalDate startDate, LocalDate maturityDate, String status) {
            this.loanNumber = loanNumber;
            this.loanType = loanType;
            this.originalAmount = originalAmount;
            this.currentBalance = currentBalance;
            this.interestRate = interestRate;
            this.monthlyPayment = monthlyPayment;
            this.startDate = startDate;
            this.maturityDate = maturityDate;
            this.status = status;
        }

        public String getLoanNumber() { return loanNumber; }
        public String getLoanType() { return loanType; }
        public String getOriginalAmount() { return originalAmount; }
        public String getCurrentBalance() { return currentBalance; }
        public String getInterestRate() { return interestRate; }
        public String getMonthlyPayment() { return monthlyPayment; }
        public LocalDate getStartDate() { return startDate; }
        public LocalDate getMaturityDate() { return maturityDate; }
        public String getStatus() { return status; }
    }
}
