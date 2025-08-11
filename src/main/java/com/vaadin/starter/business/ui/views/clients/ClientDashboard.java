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
import com.vaadin.starter.business.ui.views.clients.tabs.AbstractTab;
import com.vaadin.starter.business.ui.views.clients.tabs.AccountsTab;
import com.vaadin.starter.business.ui.views.clients.tabs.AmlCtfCasesTab;
import com.vaadin.starter.business.ui.views.clients.tabs.CardsTab;
import com.vaadin.starter.business.ui.views.clients.tabs.ConfirmingTab;
import com.vaadin.starter.business.ui.views.clients.tabs.ContractsTab;
import com.vaadin.starter.business.ui.views.clients.tabs.DocumentsTab;
import com.vaadin.starter.business.ui.views.clients.tabs.FactoringTab;
import com.vaadin.starter.business.ui.views.clients.tabs.LeasingTab;
import com.vaadin.starter.business.ui.views.clients.tabs.RentingTab;
import com.vaadin.starter.business.ui.views.clients.tabs.models.Account;
import com.vaadin.starter.business.ui.views.clients.tabs.models.AmlCtfCase;
import com.vaadin.starter.business.ui.views.clients.tabs.models.Card;
import com.vaadin.starter.business.ui.views.clients.tabs.models.Confirming;
import com.vaadin.starter.business.ui.views.clients.tabs.models.Contract;
import com.vaadin.starter.business.ui.views.clients.tabs.models.Document;
import com.vaadin.starter.business.ui.views.clients.tabs.models.Factoring;
import com.vaadin.starter.business.ui.views.clients.tabs.models.Leasing;
import com.vaadin.starter.business.ui.views.clients.tabs.models.Renting;

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
        FlexBoxLayout content = new FlexBoxLayout(createHeader(), createClientDetails(), createTabs());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createClientDetails() {
        // Client details form
        FormLayout form = new FormLayout();
        form.addClassNames("client-details-form");
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("600px", 4,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP));

        // Client ID
        TextField clientIdField = new TextField();
        clientIdField.setValue(client.getClientId() != null ? client.getClientId() : "");
        clientIdField.setReadOnly(true);

        // Name
        TextField nameField = new TextField();
        nameField.setValue(client.getName() != null ? client.getName() : "");

        // Email
        TextField emailField = new TextField();
        emailField.setValue(client.getEmail() != null ? client.getEmail() : "");

        // Phone
        TextField phoneField = new TextField();
        phoneField.setValue(client.getPhone() != null ? client.getPhone() : "");

        // Address
        TextField addressField = new TextField();
        addressField.setValue(client.getAddress() != null ? client.getAddress() : "");

        // Type
        TextField typeField = new TextField();
        typeField.setValue(client.getType());

        // Status
        TextField statusField = new TextField();
        statusField.setValue(client.getStatus());

        // Creation Date
        DatePicker creationDateField = new DatePicker();
        creationDateField.setValue(client.getDateCreated() != null ? client.getDateCreated().toLocalDate() : LocalDate.now());
        creationDateField.setReadOnly(true);

        // Update Date
        DatePicker updateDateField = new DatePicker();
        updateDateField.setValue(client.getDateUpdated() != null ? client.getDateUpdated().toLocalDate() : LocalDate.now());
        updateDateField.setReadOnly(true);

        // Add fields to form
        form.addFormItem(clientIdField, "Client ID");
        form.addFormItem(nameField, "Name");
        form.addFormItem(emailField, "Email");
        form.addFormItem(phoneField, "Phone");
        form.addFormItem(addressField, "Address");
        form.addFormItem(typeField, "Type");
        form.addFormItem(statusField, "Status");
        form.addFormItem(creationDateField, "Creation Date");
        form.addFormItem(updateDateField, "Update Date");

        // Save button
        Button saveButton = UIUtils.createPrimaryButton("Save");
        saveButton.addClickListener(e -> {
            // In a real application, this would save the client data to the backend
            UIUtils.showNotification("Client details saved.");
        });

        // Create a container for the form with a title
        VerticalLayout container = new VerticalLayout();
        container.setPadding(true);
        container.setSpacing(true);

        H3 title = new H3("Client Details");
        container.add(title, form, saveButton);

        return container;
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
        // Create tab objects
        Tab accountsTab = new Tab("Accounts");
        Tab cardsTab = new Tab("Cards");
        Tab leasingTab = new Tab("Leasing");
        Tab rentingTab = new Tab("Renting");
        Tab factoringTab = new Tab("Factoring");
        Tab confirmingTab = new Tab("Confirming");
        Tab contractsTab = new Tab("Contracts");
        Tab documentsTab = new Tab("Documents");
        Tab amlCtfCasesTab = new Tab("AML/CTF Cases");

        Tabs tabs = new Tabs(accountsTab, cardsTab, leasingTab, rentingTab, factoringTab, confirmingTab, contractsTab, documentsTab, amlCtfCasesTab);
        tabs.getStyle().set("margin", "0 1rem");

        contentArea = new Div();
        contentArea.setSizeFull();
        contentArea.getStyle().set("padding", "1rem");

        // Create tab content components
        AccountsTab accountsTabComponent = new AccountsTab(client);
        CardsTab cardsTabComponent = new CardsTab(client);
        LeasingTab leasingTabComponent = new LeasingTab(client);
        RentingTab rentingTabComponent = new RentingTab(client);
        FactoringTab factoringTabComponent = new FactoringTab(client);
        ConfirmingTab confirmingTabComponent = new ConfirmingTab(client);
        ContractsTab contractsTabComponent = new ContractsTab(client);
        DocumentsTab documentsTabComponent = new DocumentsTab(client);
        AmlCtfCasesTab amlCtfCasesTabComponent = new AmlCtfCasesTab(client);

        // Show accounts tab by default
        contentArea.add(accountsTabComponent);

        tabs.addSelectedChangeListener(event -> {
            contentArea.removeAll();
            if (event.getSelectedTab().equals(accountsTab)) {
                contentArea.add(accountsTabComponent);
            } else if (event.getSelectedTab().equals(cardsTab)) {
                contentArea.add(cardsTabComponent);
            } else if (event.getSelectedTab().equals(leasingTab)) {
                contentArea.add(leasingTabComponent);
            } else if (event.getSelectedTab().equals(rentingTab)) {
                contentArea.add(rentingTabComponent);
            } else if (event.getSelectedTab().equals(factoringTab)) {
                contentArea.add(factoringTabComponent);
            } else if (event.getSelectedTab().equals(confirmingTab)) {
                contentArea.add(confirmingTabComponent);
            } else if (event.getSelectedTab().equals(contractsTab)) {
                contentArea.add(contractsTabComponent);
            } else if (event.getSelectedTab().equals(documentsTab)) {
                contentArea.add(documentsTabComponent);
            } else if (event.getSelectedTab().equals(amlCtfCasesTab)) {
                contentArea.add(amlCtfCasesTabComponent);
            }
        });

        VerticalLayout tabsLayout = new VerticalLayout(tabs, contentArea);
        tabsLayout.setPadding(false);
        tabsLayout.setSpacing(false);
        tabsLayout.setSizeFull();
        return tabsLayout;
    }

    // Accounts tab functionality has been moved to AccountsTab class

    // Cards tab functionality has been moved to CardsTab class

    // Leasing tab functionality has been moved to LeasingTab class

    // Renting tab functionality has been moved to RentingTab class

    // Factoring tab functionality has been moved to FactoringTab class

    // This method is kept for reference but is no longer used
    private void showFactoringTab() {
        VerticalLayout factoringLayout = new VerticalLayout();
        factoringLayout.setPadding(false);
        factoringLayout.setSpacing(true);
        factoringLayout.setSizeFull();

        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Invoices", "5", VaadinIcon.FILE_TEXT_O));
        summaryCards.add(createSummaryCard("Total Amount", "$45,000.00", VaadinIcon.DOLLAR));
        summaryCards.add(createSummaryCard("Advance Rate", "80%", VaadinIcon.CHART));

        factoringLayout.add(summaryCards);

        // Add factoring grid
        Grid<Factoring> factoringGrid = createFactoringGrid();
        factoringLayout.add(factoringGrid);
        factoringLayout.expand(factoringGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button newInvoiceButton = UIUtils.createPrimaryButton("New Invoice");
        Button advanceRequestButton = UIUtils.createTertiaryButton("Request Advance");
        Button factoringDetailsButton = UIUtils.createTertiaryButton("Factoring Details");
        actionButtons.add(newInvoiceButton, advanceRequestButton, factoringDetailsButton);
        actionButtons.setSpacing(true);
        factoringLayout.add(actionButtons);

        contentArea.add(factoringLayout);
    }

    // Confirming tab functionality has been moved to ConfirmingTab class

    // This method is kept for reference but is no longer used
    private void showConfirmingTab() {
        VerticalLayout confirmingLayout = new VerticalLayout();
        confirmingLayout.setPadding(false);
        confirmingLayout.setSpacing(true);
        confirmingLayout.setSizeFull();

        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Suppliers", "3", VaadinIcon.USERS));
        summaryCards.add(createSummaryCard("Total Amount", "$28,500.00", VaadinIcon.DOLLAR));
        summaryCards.add(createSummaryCard("Payment Terms", "60 days", VaadinIcon.CALENDAR_CLOCK));

        confirmingLayout.add(summaryCards);

        // Add confirming grid
        Grid<Confirming> confirmingGrid = createConfirmingGrid();
        confirmingLayout.add(confirmingGrid);
        confirmingLayout.expand(confirmingGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button newSupplierButton = UIUtils.createPrimaryButton("New Supplier");
        Button paymentRequestButton = UIUtils.createTertiaryButton("Payment Request");
        Button confirmingDetailsButton = UIUtils.createTertiaryButton("Confirming Details");
        actionButtons.add(newSupplierButton, paymentRequestButton, confirmingDetailsButton);
        actionButtons.setSpacing(true);
        confirmingLayout.add(actionButtons);

        contentArea.add(confirmingLayout);
    }

    // Contracts tab functionality has been moved to ContractsTab class

    // This method is kept for reference but is no longer used
    private void showContractsTab() {
        VerticalLayout contractsLayout = new VerticalLayout();
        contractsLayout.setPadding(false);
        contractsLayout.setSpacing(true);
        contractsLayout.setSizeFull();

        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Contracts", "3", VaadinIcon.FILE_TEXT));
        summaryCards.add(createSummaryCard("Active Contracts", "2", VaadinIcon.CHECK));
        summaryCards.add(createSummaryCard("Contract Value", "$7,700.00", VaadinIcon.DOLLAR));

        contractsLayout.add(summaryCards);

        // Add contracts grid
        Grid<Contract> contractsGrid = createContractsGrid();
        contractsLayout.add(contractsGrid);
        contractsLayout.expand(contractsGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button newContractButton = UIUtils.createPrimaryButton("New Contract");
        Button renewButton = UIUtils.createTertiaryButton("Renew Contract");
        Button historyButton = UIUtils.createTertiaryButton("Contract History");
        actionButtons.add(newContractButton, renewButton, historyButton);
        actionButtons.setSpacing(true);
        contractsLayout.add(actionButtons);

        contentArea.add(contractsLayout);
    }

    // Documents tab functionality has been moved to DocumentsTab class

    // This method is kept for reference but is no longer used
    private void showDocumentsTab() {
        VerticalLayout documentsLayout = new VerticalLayout();
        documentsLayout.setPadding(false);
        documentsLayout.setSpacing(true);
        documentsLayout.setSizeFull();

        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Documents", "8", VaadinIcon.FOLDER));
        summaryCards.add(createSummaryCard("Pending Review", "2", VaadinIcon.CLOCK));
        summaryCards.add(createSummaryCard("Recently Added", "3", VaadinIcon.CALENDAR));

        documentsLayout.add(summaryCards);

        // Add documents grid
        Grid<Document> documentsGrid = createDocumentsGrid();
        documentsLayout.add(documentsGrid);
        documentsLayout.expand(documentsGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button uploadButton = UIUtils.createPrimaryButton("Upload Document");
        Button downloadAllButton = UIUtils.createTertiaryButton("Download All");
        Button archiveButton = UIUtils.createTertiaryButton("Archive Selected");
        actionButtons.add(uploadButton, downloadAllButton, archiveButton);
        actionButtons.setSpacing(true);
        documentsLayout.add(actionButtons);

        contentArea.add(documentsLayout);
    }

    // AML/CTF Cases tab functionality has been moved to AmlCtfCasesTab class

    // This method is kept for reference but is no longer used
    private void showAmlCtfCasesTab() {
        VerticalLayout amlCtfLayout = new VerticalLayout();
        amlCtfLayout.setPadding(false);
        amlCtfLayout.setSpacing(true);
        amlCtfLayout.setSizeFull();

        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Cases", "4", VaadinIcon.EXCLAMATION_CIRCLE));
        summaryCards.add(createSummaryCard("Open Cases", "2", VaadinIcon.HOURGLASS));
        summaryCards.add(createSummaryCard("High Risk", "1", VaadinIcon.WARNING));

        amlCtfLayout.add(summaryCards);

        // Add AML/CTF cases grid
        Grid<AmlCtfCase> amlCtfGrid = createAmlCtfCasesGrid();
        amlCtfLayout.add(amlCtfGrid);
        amlCtfLayout.expand(amlCtfGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button newCaseButton = UIUtils.createPrimaryButton("New Case");
        Button reportButton = UIUtils.createTertiaryButton("Generate Report");
        Button historyButton = UIUtils.createTertiaryButton("Case History");
        actionButtons.add(newCaseButton, reportButton, historyButton);
        actionButtons.setSpacing(true);
        amlCtfLayout.add(actionButtons);

        contentArea.add(amlCtfLayout);
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

    // Accounts grid functionality has been moved to AccountsTab class

    // Account details functionality has been moved to AccountsTab class

    // Cards grid functionality has been moved to CardsTab class

    // Card details functionality has been moved to CardsTab class

    private Grid<Loan> createLoansGrid() {
        Grid<Loan> grid = new Grid<>();
        grid.setItems(generateMockLoans());
        grid.setSizeFull();
        grid.setMinHeight("400px");

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

    // Mock accounts functionality has been moved to AccountsTab class

    // Mock cards functionality has been moved to CardsTab class

    // Leasing grid functionality has been moved to LeasingTab class

    // Leasing details functionality has been moved to LeasingTab class

    // Mock leasings functionality has been moved to LeasingTab class

    // Renting grid functionality has been moved to RentingTab class

    // Renting details functionality has been moved to RentingTab class

    // Mock rentings functionality has been moved to RentingTab class

    // Factoring grid functionality has been moved to FactoringTab class

    // This method is kept for reference but is no longer used
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

    // Factoring details functionality has been moved to FactoringTab class

    // This method is kept for reference but is no longer used
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

    // Mock factorings functionality has been moved to FactoringTab class

    // This method is kept for reference but is no longer used
    private List<Factoring> generateMockFactorings() {
        List<Factoring> factorings = new ArrayList<>();
        factorings.add(new Factoring("INV10012345", "Acme Corporation", "$12,000.00", "$9,600.00", "80%", LocalDate.now().minusDays(15), LocalDate.now().plusDays(45), "Active"));
        factorings.add(new Factoring("INV10012346", "Global Traders Inc", "$8,500.00", "$6,800.00", "80%", LocalDate.now().minusDays(10), LocalDate.now().plusDays(50), "Active"));
        factorings.add(new Factoring("INV10012347", "Tech Innovations Ltd", "$15,000.00", "$12,000.00", "80%", LocalDate.now().minusDays(5), LocalDate.now().plusDays(55), "Active"));
        factorings.add(new Factoring("INV10012348", "Green Energy Solutions", "$5,500.00", "$4,400.00", "80%", LocalDate.now().minusDays(3), LocalDate.now().plusDays(57), "Active"));
        factorings.add(new Factoring("INV10012349", "Australian Mining Ltd", "$4,000.00", "$3,200.00", "80%", LocalDate.now().minusDays(1), LocalDate.now().plusDays(59), "Active"));
        return factorings;
    }

    // Confirming grid functionality has been moved to ConfirmingTab class

    // This method is kept for reference but is no longer used
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

    // Confirming details functionality has been moved to ConfirmingTab class

    // This method is kept for reference but is no longer used
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

    // Mock confirmings functionality has been moved to ConfirmingTab class

    // This method is kept for reference but is no longer used
    private List<Confirming> generateMockConfirmings() {
        List<Confirming> confirmings = new ArrayList<>();
        confirmings.add(new Confirming("SUP10012345", "Office Supplies Inc", "INV-OS-12345", "$12,500.00", LocalDate.now().minusDays(10), LocalDate.now().plusDays(50), "60 days", "Active"));
        confirmings.add(new Confirming("SUP10012346", "IT Hardware Solutions", "INV-IT-54321", "$8,500.00", LocalDate.now().minusDays(5), LocalDate.now().plusDays(55), "60 days", "Active"));
        confirmings.add(new Confirming("SUP10012347", "Logistics Partners", "INV-LP-98765", "$7,500.00", LocalDate.now().minusDays(3), LocalDate.now().plusDays(57), "60 days", "Active"));
        return confirmings;
    }

    // AML/CTF Cases grid functionality has been moved to AmlCtfCasesTab class

    // This method is kept for reference but is no longer used
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

    // AML/CTF Case details functionality has been moved to AmlCtfCasesTab class

    // This method is kept for reference but is no longer used
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
        if (amlCtfCase.getCloseDate() != null) {
            closeDateField.setValue(amlCtfCase.getCloseDate());
        }
        closeDateField.setReadOnly(true);

        TextField assignedToField = new TextField("Assigned To");
        assignedToField.setValue(amlCtfCase.getAssignedTo());
        assignedToField.setReadOnly(true);

        TextField alertSourceField = new TextField("Alert Source");
        alertSourceField.setValue(amlCtfCase.getAlertSource());
        alertSourceField.setReadOnly(true);

        // Add fields to form layout
        formLayout.add(caseIdField, caseTypeField, descriptionField, riskLevelField, 
                      statusField, openDateField, closeDateField, assignedToField, alertSourceField);

        // Create buttons
        Button updateButton = UIUtils.createPrimaryButton("Update Case");
        updateButton.addClickListener(e -> {
            UIUtils.showNotification("Case updated successfully.");
            dialog.close();
        });

        Button closeButton = UIUtils.createTertiaryButton("Close");
        closeButton.addClickListener(e -> dialog.close());

        // Create layout for buttons
        HorizontalLayout buttonLayout = new HorizontalLayout(updateButton, closeButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);

        // Create layout for dialog content
        VerticalLayout dialogLayout = new VerticalLayout(title, formLayout, buttonLayout);
        dialogLayout.setPadding(true);
        dialogLayout.setSpacing(true);

        dialog.add(dialogLayout);
        dialog.open();
    }

    // Mock AML/CTF Cases functionality has been moved to AmlCtfCasesTab class

    // This method is kept for reference but is no longer used
    private List<AmlCtfCase> generateMockAmlCtfCases() {
        List<AmlCtfCase> amlCtfCases = new ArrayList<>();

        amlCtfCases.add(new AmlCtfCase(
            "AML001", 
            "Money Laundering", 
            "Suspicious transaction pattern detected", 
            "High", 
            "Open", 
            LocalDate.now().minusDays(15), 
            null, 
            "John Smith", 
            "Transaction Monitoring System"
        ));

        amlCtfCases.add(new AmlCtfCase(
            "AML002", 
            "Terrorist Financing", 
            "Potential link to sanctioned entity", 
            "Medium", 
            "Under Investigation", 
            LocalDate.now().minusDays(30), 
            null, 
            "Maria Garcia", 
            "Sanctions Screening"
        ));

        amlCtfCases.add(new AmlCtfCase(
            "AML003", 
            "Fraud", 
            "Identity theft suspected", 
            "Low", 
            "Closed", 
            LocalDate.now().minusDays(60), 
            LocalDate.now().minusDays(45), 
            "Robert Johnson", 
            "Customer Complaint"
        ));

        amlCtfCases.add(new AmlCtfCase(
            "AML004", 
            "Regulatory Compliance", 
            "KYC documentation incomplete", 
            "Medium", 
            "Open", 
            LocalDate.now().minusDays(5), 
            null, 
            "Sarah Williams", 
            "Periodic Review"
        ));

        return amlCtfCases;
    }

    private List<Loan> generateMockLoans() {
        List<Loan> loans = new ArrayList<>();
        loans.add(new Loan("L10012345", "Mortgage", "$250,000.00", "$120,500.00", "3.25%", "$1,250.00", LocalDate.now().minusYears(5), LocalDate.now().plusYears(25), "Active"));
        return loans;
    }

    // Contracts grid functionality has been moved to ContractsTab class

    // This method is kept for reference but is no longer used
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

    // Contract details functionality has been moved to ContractsTab class

    // This method is kept for reference but is no longer used
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

    // Mock contracts functionality has been moved to ContractsTab class

    // This method is kept for reference but is no longer used
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

    // Documents grid functionality has been moved to DocumentsTab class

    // This method is kept for reference but is no longer used
    private Grid<Document> createDocumentsGrid() {
        Grid<Document> grid = new Grid<>();
        grid.setItems(generateMockDocuments());
        grid.setSizeFull();
        grid.setMinHeight("400px");

        grid.addColumn(Document::getDocumentId)
                .setHeader("Document ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Document::getDocumentName)
                .setHeader("Document Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(Document::getDocumentType)
                .setHeader("Type")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Document::getSize)
                .setHeader("Size")
                .setSortable(true)
                .setWidth("100px");
        grid.addColumn(new ComponentRenderer<>(document -> createStatusBadge(document.getStatus())))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Document::getStatus)
                .setWidth("120px");
        grid.addColumn(document -> UIUtils.formatDate(document.getUploadDate()))
                .setHeader("Upload Date")
                .setSortable(true)
                .setComparator(Document::getUploadDate)
                .setWidth("120px");
        grid.addColumn(Document::getUploadedBy)
                .setHeader("Uploaded By")
                .setSortable(true)
                .setWidth("150px");

        // Add item click listener to show document details dialog
        grid.addItemClickListener(event -> {
            Document document = event.getItem();
            showDocumentDetails(document);
        });

        // Add style to indicate rows are clickable
        grid.addClassName("clickable-grid");
        grid.getElement().getStyle().set("cursor", "pointer");

        return grid;
    }

    // Document details functionality has been moved to DocumentsTab class

    // This method is kept for reference but is no longer used
    private void showDocumentDetails(Document document) {
        // Create dialog for document details
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");

        // Create dialog title
        H3 title = new H3("Document Details: " + document.getDocumentName());

        // Create form layout for document details
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create fields for document details
        TextField documentIdField = new TextField("Document ID");
        documentIdField.setValue(document.getDocumentId());
        documentIdField.setReadOnly(true);

        TextField documentNameField = new TextField("Document Name");
        documentNameField.setValue(document.getDocumentName());
        documentNameField.setReadOnly(true);

        TextField documentTypeField = new TextField("Document Type");
        documentTypeField.setValue(document.getDocumentType());
        documentTypeField.setReadOnly(true);

        TextField sizeField = new TextField("Size");
        sizeField.setValue(document.getSize());
        sizeField.setReadOnly(true);

        TextField statusField = new TextField("Status");
        statusField.setValue(document.getStatus());
        statusField.setReadOnly(true);

        DatePicker uploadDateField = new DatePicker("Upload Date");
        uploadDateField.setValue(document.getUploadDate());
        uploadDateField.setReadOnly(true);

        TextField uploadedByField = new TextField("Uploaded By");
        uploadedByField.setValue(document.getUploadedBy());
        uploadedByField.setReadOnly(true);

        TextField descriptionField = new TextField("Description");
        descriptionField.setValue(document.getDescription());
        descriptionField.setReadOnly(true);

        // Add fields to form layout
        formLayout.add(documentIdField, documentNameField, documentTypeField, sizeField, 
                      statusField, uploadDateField, uploadedByField, descriptionField);

        // Create buttons
        Button downloadButton = UIUtils.createPrimaryButton("Download");
        downloadButton.addClickListener(e -> {
            UIUtils.showNotification("Downloading " + document.getDocumentName());
            dialog.close();
        });

        Button closeButton = UIUtils.createTertiaryButton("Close");
        closeButton.addClickListener(e -> dialog.close());

        // Create layout for buttons
        HorizontalLayout buttonLayout = new HorizontalLayout(downloadButton, closeButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);

        // Create layout for dialog content
        VerticalLayout dialogLayout = new VerticalLayout(title, formLayout, buttonLayout);
        dialogLayout.setPadding(true);
        dialogLayout.setSpacing(true);

        dialog.add(dialogLayout);
        dialog.open();
    }

    // Mock documents functionality has been moved to DocumentsTab class

    // This method is kept for reference but is no longer used
    private List<Document> generateMockDocuments() {
        List<Document> documents = new ArrayList<>();

        documents.add(new Document(
            "DOC001", 
            "ID_Verification.pdf", 
            "PDF", 
            "1.2 MB", 
            "Verified", 
            LocalDate.now().minusMonths(6), 
            "System Admin", 
            "Client ID verification document"
        ));

        documents.add(new Document(
            "DOC002", 
            "Address_Proof.pdf", 
            "PDF", 
            "2.5 MB", 
            "Verified", 
            LocalDate.now().minusMonths(6), 
            "System Admin", 
            "Proof of address document"
        ));

        documents.add(new Document(
            "DOC003", 
            "Income_Statement_2023.xlsx", 
            "Excel", 
            "3.7 MB", 
            "Verified", 
            LocalDate.now().minusMonths(3), 
            "Financial Advisor", 
            "Annual income statement"
        ));

        documents.add(new Document(
            "DOC004", 
            "Contract_CNT001.pdf", 
            "PDF", 
            "4.1 MB", 
            "Active", 
            LocalDate.now().minusMonths(6), 
            "Account Manager", 
            "Standard contract document"
        ));

        documents.add(new Document(
            "DOC005", 
            "Contract_CNT002.pdf", 
            "PDF", 
            "4.3 MB", 
            "Active", 
            LocalDate.now().minusMonths(2), 
            "Account Manager", 
            "Premium contract document"
        ));

        documents.add(new Document(
            "DOC006", 
            "Tax_Form_2023.pdf", 
            "PDF", 
            "1.8 MB", 
            "Pending Review", 
            LocalDate.now().minusDays(5), 
            client.getName(), 
            "Annual tax form"
        ));

        documents.add(new Document(
            "DOC007", 
            "Investment_Portfolio.pdf", 
            "PDF", 
            "5.2 MB", 
            "Pending Review", 
            LocalDate.now().minusDays(3), 
            "Financial Advisor", 
            "Investment portfolio analysis"
        ));

        documents.add(new Document(
            "DOC008", 
            "Meeting_Notes_2023-06-15.docx", 
            "Word", 
            "0.8 MB", 
            "Internal", 
            LocalDate.now().minusDays(15), 
            "Account Manager", 
            "Client meeting notes"
        ));

        return documents;
    }

    public static class Document {
        private String documentId;
        private String documentName;
        private String documentType;
        private String size;
        private String status;
        private LocalDate uploadDate;
        private String uploadedBy;
        private String description;

        public Document(String documentId, String documentName, String documentType, String size,
                      String status, LocalDate uploadDate, String uploadedBy, String description) {
            this.documentId = documentId;
            this.documentName = documentName;
            this.documentType = documentType;
            this.size = size;
            this.status = status;
            this.uploadDate = uploadDate;
            this.uploadedBy = uploadedBy;
            this.description = description;
        }

        public String getDocumentId() { return documentId; }
        public String getDocumentName() { return documentName; }
        public String getDocumentType() { return documentType; }
        public String getSize() { return size; }
        public String getStatus() { return status; }
        public LocalDate getUploadDate() { return uploadDate; }
        public String getUploadedBy() { return uploadedBy; }
        public String getDescription() { return description; }
    }

    // Card class has been moved to com.vaadin.starter.business.ui.views.clients.tabs.models.Card

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

    // Leasing class has been moved to com.vaadin.starter.business.ui.views.clients.tabs.models.Leasing

    // Renting class has been moved to com.vaadin.starter.business.ui.views.clients.tabs.models.Renting

    public static class Factoring {
        private String invoiceNumber;
        private String debtor;
        private String invoiceAmount;
        private String advanceAmount;
        private String advanceRate;
        private LocalDate invoiceDate;
        private LocalDate dueDate;
        private String status;

        public Factoring(String invoiceNumber, String debtor, String invoiceAmount, String advanceAmount, String advanceRate, LocalDate invoiceDate, LocalDate dueDate, String status) {
            this.invoiceNumber = invoiceNumber;
            this.debtor = debtor;
            this.invoiceAmount = invoiceAmount;
            this.advanceAmount = advanceAmount;
            this.advanceRate = advanceRate;
            this.invoiceDate = invoiceDate;
            this.dueDate = dueDate;
            this.status = status;
        }

        public String getInvoiceNumber() { return invoiceNumber; }
        public String getDebtor() { return debtor; }
        public String getInvoiceAmount() { return invoiceAmount; }
        public String getAdvanceAmount() { return advanceAmount; }
        public String getAdvanceRate() { return advanceRate; }
        public LocalDate getInvoiceDate() { return invoiceDate; }
        public LocalDate getDueDate() { return dueDate; }
        public String getStatus() { return status; }
    }

    public static class Confirming {
        private String supplierNumber;
        private String supplierName;
        private String invoiceNumber;
        private String invoiceAmount;
        private LocalDate invoiceDate;
        private LocalDate dueDate;
        private String paymentTerms;
        private String status;

        public Confirming(String supplierNumber, String supplierName, String invoiceNumber, String invoiceAmount, LocalDate invoiceDate, LocalDate dueDate, String paymentTerms, String status) {
            this.supplierNumber = supplierNumber;
            this.supplierName = supplierName;
            this.invoiceNumber = invoiceNumber;
            this.invoiceAmount = invoiceAmount;
            this.invoiceDate = invoiceDate;
            this.dueDate = dueDate;
            this.paymentTerms = paymentTerms;
            this.status = status;
        }

        public String getSupplierNumber() { return supplierNumber; }
        public String getSupplierName() { return supplierName; }
        public String getInvoiceNumber() { return invoiceNumber; }
        public String getInvoiceAmount() { return invoiceAmount; }
        public LocalDate getInvoiceDate() { return invoiceDate; }
        public LocalDate getDueDate() { return dueDate; }
        public String getPaymentTerms() { return paymentTerms; }
        public String getStatus() { return status; }
    }

    public static class AmlCtfCase {
        private String caseId;
        private String caseType;
        private String description;
        private String riskLevel;
        private String status;
        private LocalDate openDate;
        private LocalDate closeDate;
        private String assignedTo;
        private String alertSource;

        public AmlCtfCase(String caseId, String caseType, String description, String riskLevel, String status, LocalDate openDate, LocalDate closeDate, String assignedTo, String alertSource) {
            this.caseId = caseId;
            this.caseType = caseType;
            this.description = description;
            this.riskLevel = riskLevel;
            this.status = status;
            this.openDate = openDate;
            this.closeDate = closeDate;
            this.assignedTo = assignedTo;
            this.alertSource = alertSource;
        }

        public String getCaseId() { return caseId; }
        public String getCaseType() { return caseType; }
        public String getDescription() { return description; }
        public String getRiskLevel() { return riskLevel; }
        public String getStatus() { return status; }
        public LocalDate getOpenDate() { return openDate; }
        public LocalDate getCloseDate() { return closeDate; }
        public String getAssignedTo() { return assignedTo; }
        public String getAlertSource() { return alertSource; }
    }
}
