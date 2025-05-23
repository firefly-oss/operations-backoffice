package com.vaadin.starter.business.ui.views.accountoperations;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.business.backend.Account;
import com.vaadin.starter.business.backend.service.AccountService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Right;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.layout.size.Vertical;
import com.vaadin.starter.business.ui.util.*;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.util.css.Overflow;
import com.vaadin.starter.business.ui.util.css.PointerEvents;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeColor;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeShape;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeSize;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@PageTitle(NavigationConstants.ACCOUNT_MAINTENANCE)
@Route(value = "account-operations/maintenance", layout = MainLayout.class)
public class AccountMaintenance extends ViewFrame {

    public static final int MOBILE_BREAKPOINT = 480;
    private Grid<Account> grid;
    private Registration resizeListener;
    private ListDataProvider<Account> dataProvider;

    // Filter form fields
    private TextField accountNumberFilter;
    private TextField accountNameFilter;
    private ComboBox<String> accountTypeFilter;
    private ComboBox<String> statusFilter;
    private TextField customerIdFilter;
    private NumberField minBalanceFilter;
    private NumberField maxBalanceFilter;
    private DatePicker openDateFromFilter;
    private DatePicker openDateToFilter;

    private final AccountService accountService;

    @Autowired
    public AccountMaintenance(AccountService accountService) {
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

        minBalanceFilter = new NumberField();
        minBalanceFilter.setClearButtonVisible(true);

        maxBalanceFilter = new NumberField();
        maxBalanceFilter.setClearButtonVisible(true);

        openDateFromFilter = new DatePicker();
        openDateFromFilter.setClearButtonVisible(true);

        openDateToFilter = new DatePicker();
        openDateToFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(minBalanceFilter, "Min Balance ($)");
        formLayout.addFormItem(maxBalanceFilter, "Max Balance ($)");
        formLayout.addFormItem(openDateFromFilter, "Open Date From");
        formLayout.addFormItem(openDateToFilter, "Open Date To");

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
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));
        grid.addThemeName("mobile");

        // Initialize with data from service
        Collection<Account> accounts = accountService.getAccounts();
        dataProvider = new ListDataProvider<>(accounts);
        grid.setDataProvider(dataProvider);

        grid.setId("accounts");
        grid.setSizeFull();

        // "Mobile" column
        grid.addColumn(new ComponentRenderer<>(this::getMobileTemplate))
                .setVisible(false);

        // "Desktop" columns
        grid.addColumn(Account::getAccountNumber)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("Account Number")
                .setSortable(true);
        grid.addColumn(Account::getAccountName)
                .setHeader("Account Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(Account::getAccountType)
                .setHeader("Account Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Account::getStatus)
                .setHeader("Status")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Account::getCustomerId)
                .setHeader("Customer ID")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new ComponentRenderer<>(this::createBalance))
                .setAutoWidth(true)
                .setComparator(Account::getBalance)
                .setFlexGrow(0)
                .setHeader("Balance ($)")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new LocalDateRenderer<>(Account::getOpenDate, "MMM dd, YYYY"))
                .setAutoWidth(true)
                .setComparator(Account::getOpenDate)
                .setFlexGrow(0)
                .setHeader("Open Date");

        // Add action buttons
        grid.addComponentColumn(account -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button editButton = UIUtils.createSmallButton("Edit");
            editButton.addClickListener(e -> {
                // Edit account logic would go here
                UI.getCurrent().navigate(AccountDetails.class, account.getAccountNumber());
            });

            Button blockButton = UIUtils.createSmallButton(account.getStatus().equals("Blocked") ? "Unblock" : "Block");
            blockButton.addClickListener(e -> {
                // Block/unblock using service
                if (account.getStatus().equals("Blocked")) {
                    accountService.unblockAccount(account.getAccountNumber());
                } else {
                    accountService.blockAccount(account.getAccountNumber());
                }
                // Refresh the grid
                grid.getDataProvider().refreshItem(account);
            });

            actions.add(editButton, blockButton);
            return actions;
        }).setHeader("Actions").setWidth("200px");

        return grid;
    }

    private AccountMobileTemplate getMobileTemplate(Account account) {
        return new AccountMobileTemplate(account);
    }

    private Span createBalance(Account account) {
        Double balance = account.getBalance();
        Span amountLabel = new Span(UIUtils.formatAmount(balance));
        amountLabel.addClassName(LumoStyles.FontFamily.MONOSPACE);
        if (balance > 0) {
            UIUtils.setTextColor(TextColor.SUCCESS, amountLabel);
        } else {
            UIUtils.setTextColor(TextColor.ERROR, amountLabel);
        }
        return amountLabel;
    }

    private void viewDetails(Account account) {
        UI.getCurrent().navigate(AccountDetails.class, account.getAccountNumber());
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        getUI().ifPresent(ui -> {
            Page page = ui.getPage();
            resizeListener = page.addBrowserWindowResizeListener(event -> updateVisibleColumns(event.getWidth()));
            page.retrieveExtendedClientDetails(details -> updateVisibleColumns(details.getBodyClientWidth()));
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        resizeListener.remove();
        super.onDetach(detachEvent);
    }

    private void updateVisibleColumns(int width) {
        boolean mobile = width < MOBILE_BREAKPOINT;
        List<Grid.Column<Account>> columns = grid.getColumns();

        // "Mobile" column
        columns.get(0).setVisible(mobile);

        // "Desktop" columns
        for (int i = 1; i < columns.size(); i++) {
            columns.get(i).setVisible(!mobile);
        }
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

        // Apply min balance filter
        if (minBalanceFilter.getValue() != null) {
            Double minValue = minBalanceFilter.getValue();
            dataProvider.addFilter(account -> 
                account.getBalance() != null && 
                account.getBalance() >= minValue);
        }

        // Apply max balance filter
        if (maxBalanceFilter.getValue() != null) {
            Double maxValue = maxBalanceFilter.getValue();
            dataProvider.addFilter(account -> 
                account.getBalance() != null && 
                account.getBalance() <= maxValue);
        }

        // Apply open date from filter
        if (openDateFromFilter.getValue() != null) {
            LocalDate fromDate = openDateFromFilter.getValue();
            dataProvider.addFilter(account -> 
                account.getOpenDate() != null && 
                !account.getOpenDate().isBefore(fromDate));
        }

        // Apply open date to filter
        if (openDateToFilter.getValue() != null) {
            LocalDate toDate = openDateToFilter.getValue();
            dataProvider.addFilter(account -> 
                account.getOpenDate() != null && 
                !account.getOpenDate().isAfter(toDate));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        accountNumberFilter.clear();
        accountNameFilter.clear();
        accountTypeFilter.clear();
        statusFilter.clear();
        customerIdFilter.clear();
        minBalanceFilter.clear();
        maxBalanceFilter.clear();
        openDateFromFilter.clear();
        openDateToFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }


    /**
     * A layout for displaying Account info in a mobile friendly format.
     */
    private class AccountMobileTemplate extends FlexBoxLayout {

        private Account account;

        public AccountMobileTemplate(Account account) {
            this.account = account;

            UIUtils.setLineHeight(LineHeight.M, this);
            UIUtils.setPointerEvents(PointerEvents.NONE, this);

            setPadding(Vertical.S);
            setSpacing(Right.L);

            Span accountNumber = new Span(account.getAccountNumber());
            UIUtils.setFontSize(FontSize.S, accountNumber);
            UIUtils.setTextColor(TextColor.SECONDARY, accountNumber);

            Span accountName = new Span(account.getAccountName());
            UIUtils.setFontSize(FontSize.M, accountName);
            UIUtils.setTextColor(TextColor.BODY, accountName);

            Span accountType = new Span(account.getAccountType());
            UIUtils.setFontSize(FontSize.S, accountType);
            UIUtils.setTextColor(TextColor.SECONDARY, accountType);

            Span balance = createBalance(account);

            Badge status = new Badge(account.getStatus(), 
                account.getStatus().equals("Active") ? BadgeColor.SUCCESS : 
                account.getStatus().equals("Blocked") ? BadgeColor.ERROR : 
                BadgeColor.NORMAL, 
                BadgeSize.S, BadgeShape.PILL);

            FlexBoxLayout column = new FlexBoxLayout(accountNumber, accountName, accountType, balance, status);
            column.setFlexDirection(FlexDirection.COLUMN);
            column.setOverflow(Overflow.HIDDEN);
            column.setSpacing(Vertical.XS);

            add(column);
            setFlexGrow(1, column);
        }
    }
}
