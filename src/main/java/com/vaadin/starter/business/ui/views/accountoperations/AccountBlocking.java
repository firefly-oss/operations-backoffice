package com.vaadin.starter.business.ui.views.accountoperations;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.Account;
import com.vaadin.starter.business.backend.service.AccountService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeColor;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeShape;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeSize;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@PageTitle(NavigationConstants.ACCOUNT_BLOCKING)
@Route(value = "account-operations/blocking", layout = MainLayout.class)
public class AccountBlocking extends ViewFrame {

    private Grid<Account> grid;
    private ListDataProvider<Account> dataProvider;

    // Filter form fields
    private TextField accountNumberFilter;
    private TextField accountNameFilter;
    private ComboBox<String> accountTypeFilter;
    private ComboBox<String> statusFilter;
    private TextField customerIdFilter;

    private final AccountService accountService;

    @Autowired
    public AccountBlocking(AccountService accountService) {
        this.accountService = accountService;
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createFilterForm(), createGrid());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createFilterForm() {
        // Initialize filter fields
        accountNumberFilter = new TextField();
        accountNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        accountNumberFilter.setClearButtonVisible(true);

        accountNameFilter = new TextField();
        accountNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        accountNameFilter.setClearButtonVisible(true);

        accountTypeFilter = new ComboBox<>();
        accountTypeFilter.setItems("Checking", "Savings", "Credit Card", "Loan", "Investment");
        accountTypeFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("Active", "Inactive", "Blocked", "Closed");
        statusFilter.setClearButtonVisible(true);

        customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> applyFilters());

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> clearFilters());

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton);
        buttonLayout.setSpacing(true);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(accountNumberFilter, "Account Number");
        formLayout.addFormItem(accountNameFilter, "Account Name");
        formLayout.addFormItem(accountTypeFilter, "Account Type");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(customerIdFilter, "Customer ID");

        formLayout.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
            new FormLayout.ResponsiveStep("900px", 4, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create a container for the form and buttons
        Div formContainer = new Div(formLayout, buttonLayout);
        formContainer.getStyle().set("padding", "1em");
        formContainer.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        formContainer.getStyle().set("border-radius", "var(--lumo-border-radius)");
        formContainer.getStyle().set("background-color", "var(--lumo-base-color)");
        formContainer.getStyle().set("margin-bottom", "1em");

        return formContainer;
    }

    private Grid createGrid() {
        grid = new Grid<>();

        // Initialize with data from service
        Collection<Account> accounts = accountService.getAccounts();
        dataProvider = new ListDataProvider<>(accounts);
        grid.setDataProvider(dataProvider);

        grid.setId("accounts");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(Account::getAccountNumber)
                .setHeader("Account Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Account::getAccountName)
                .setHeader("Account Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(Account::getAccountType)
                .setHeader("Account Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new ComponentRenderer<>(this::createStatusBadge))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Account::getStatus)
                .setWidth("120px");
        grid.addColumn(Account::getCustomerId)
                .setHeader("Customer ID")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new ComponentRenderer<>(this::createBalance))
                .setHeader("Balance")
                .setSortable(true)
                .setComparator(Account::getBalance)
                .setWidth("150px");
        grid.addColumn(new LocalDateRenderer<>(Account::getOpenDate, "MMM dd, YYYY"))
                .setHeader("Open Date")
                .setSortable(true)
                .setComparator(Account::getOpenDate)
                .setWidth("150px");

        // Add action buttons
        grid.addComponentColumn(account -> {
            HorizontalLayout actions = new HorizontalLayout();
            
            Button viewButton = UIUtils.createSmallButton("View");
            viewButton.addClickListener(e -> {
                UI.getCurrent().navigate(AccountDetails.class, account.getAccountNumber());
            });
            
            Button blockButton = UIUtils.createSmallButton(
                    account.getStatus().equals("Blocked") ? "Unblock" : "Block");
            
            if (!account.getStatus().equals("Closed")) {
                if (account.getStatus().equals("Blocked")) {
                    blockButton.getElement().getThemeList().add("success");
                } else {
                    blockButton.getElement().getThemeList().add("error");
                }
                
                blockButton.addClickListener(e -> {
                    if (account.getStatus().equals("Blocked")) {
                        accountService.unblockAccount(account.getAccountNumber());
                    } else {
                        accountService.blockAccount(account.getAccountNumber());
                    }
                    // Refresh the grid
                    refreshGrid();
                });
                actions.add(viewButton, blockButton);
            } else {
                actions.add(viewButton);
            }
            
            return actions;
        }).setHeader("Actions").setWidth("200px");

        return grid;
    }

    private Component createStatusBadge(Account account) {
        String status = account.getStatus();
        BadgeColor color;
        
        switch (status) {
            case "Active":
                color = BadgeColor.SUCCESS;
                break;
            case "Blocked":
                color = BadgeColor.ERROR;
                break;
            case "Inactive":
                color = BadgeColor.NORMAL;
                break;
            case "Closed":
                color = BadgeColor.CONTRAST;
                break;
            default:
                color = BadgeColor.NORMAL;
        }
        
        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Component createBalance(Account account) {
        Double balance = account.getBalance();
        Span balanceSpan = new Span(UIUtils.formatAmount(balance));
        
        if (balance > 0) {
            UIUtils.setTextColor(TextColor.SUCCESS, balanceSpan);
        } else if (balance < 0) {
            UIUtils.setTextColor(TextColor.ERROR, balanceSpan);
        }
        
        return balanceSpan;
    }

    private void refreshGrid() {
        // Refresh data from service
        Collection<Account> accounts = accountService.getAccounts();
        dataProvider = new ListDataProvider<>(accounts);
        grid.setDataProvider(dataProvider);
        applyFilters(); // Re-apply any active filters
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply account number filter
        if (accountNumberFilter.getValue() != null && !accountNumberFilter.getValue().isEmpty()) {
            String accountNumberValue = accountNumberFilter.getValue().toLowerCase();
            dataProvider.addFilter(account -> 
                account.getAccountNumber() != null && 
                account.getAccountNumber().toLowerCase().contains(accountNumberValue));
        }

        // Apply account name filter
        if (accountNameFilter.getValue() != null && !accountNameFilter.getValue().isEmpty()) {
            String accountNameValue = accountNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(account -> 
                account.getAccountName() != null && 
                account.getAccountName().toLowerCase().contains(accountNameValue));
        }

        // Apply account type filter
        if (accountTypeFilter.getValue() != null) {
            String accountTypeValue = accountTypeFilter.getValue();
            dataProvider.addFilter(account -> 
                account.getAccountType() != null && 
                account.getAccountType().equals(accountTypeValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(account -> 
                account.getStatus() != null && 
                account.getStatus().equals(statusValue));
        }

        // Apply customer ID filter
        if (customerIdFilter.getValue() != null && !customerIdFilter.getValue().isEmpty()) {
            String customerIdValue = customerIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(account -> 
                account.getCustomerId() != null && 
                account.getCustomerId().toLowerCase().contains(customerIdValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        accountNumberFilter.clear();
        accountNameFilter.clear();
        accountTypeFilter.clear();
        statusFilter.clear();
        customerIdFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }
}