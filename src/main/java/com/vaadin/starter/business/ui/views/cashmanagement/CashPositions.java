package com.vaadin.starter.business.ui.views.cashmanagement;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.CashPosition;
import com.vaadin.starter.business.backend.service.CashPositionService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.Collection;

@PageTitle(NavigationConstants.CASH_POSITIONS)
@Route(value = "cash-management/positions", layout = MainLayout.class)
public class CashPositions extends ViewFrame {

    private Grid<CashPosition> grid;
    private ListDataProvider<CashPosition> dataProvider;

    // Filter form fields
    private TextField idFilter;
    private TextField accountNumberFilter;
    private TextField accountNameFilter;
    private ComboBox<String> accountTypeFilter;
    private NumberField minBalanceFilter;
    private NumberField maxBalanceFilter;
    private ComboBox<String> currencyFilter;
    private ComboBox<String> statusFilter;
    private TextField bankNameFilter;
    private TextField branchCodeFilter;

    private final CashPositionService cashPositionService;

    @Autowired
    public CashPositions(CashPositionService cashPositionService) {
        this.cashPositionService = cashPositionService;
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

    private void viewDetails(CashPosition cashPosition) {
        UI.getCurrent().navigate(CashPositionDetails.class, cashPosition.getId());
    }

    private Component createFilterForm() {
        // Initialize filter fields
        idFilter = new TextField();
        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.setClearButtonVisible(true);

        accountNumberFilter = new TextField();
        accountNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        accountNumberFilter.setClearButtonVisible(true);

        accountNameFilter = new TextField();
        accountNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        accountNameFilter.setClearButtonVisible(true);

        accountTypeFilter = new ComboBox<>();
        accountTypeFilter.setItems(getAccountTypes());
        accountTypeFilter.setClearButtonVisible(true);

        minBalanceFilter = new NumberField();
        minBalanceFilter.setClearButtonVisible(true);

        maxBalanceFilter = new NumberField();
        maxBalanceFilter.setClearButtonVisible(true);

        currencyFilter = new ComboBox<>();
        currencyFilter.setItems("USD", "EUR", "GBP", "JPY", "CAD", "AUD");
        currencyFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems(getAccountStatuses());
        statusFilter.setClearButtonVisible(true);

        bankNameFilter = new TextField();
        bankNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        bankNameFilter.setClearButtonVisible(true);

        branchCodeFilter = new TextField();
        branchCodeFilter.setValueChangeMode(ValueChangeMode.EAGER);
        branchCodeFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(idFilter, "ID");
        formLayout.addFormItem(accountNumberFilter, "Account Number");
        formLayout.addFormItem(accountNameFilter, "Account Name");
        formLayout.addFormItem(accountTypeFilter, "Account Type");
        formLayout.addFormItem(minBalanceFilter, "Min Balance");
        formLayout.addFormItem(maxBalanceFilter, "Max Balance");
        formLayout.addFormItem(currencyFilter, "Currency");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(bankNameFilter, "Bank Name");
        formLayout.addFormItem(branchCodeFilter, "Branch Code");

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

    private Grid<CashPosition> createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));

        // Initialize data provider
        Collection<CashPosition> cashPositions = cashPositionService.getCashPositions();
        dataProvider = new ListDataProvider<>(cashPositions);
        grid.setDataProvider(dataProvider);

        grid.setId("cashPositions");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(CashPosition::getId)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("ID")
                .setSortable(true);
        grid.addColumn(CashPosition::getAccountNumber)
                .setHeader("Account Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(CashPosition::getAccountName)
                .setHeader("Account Name")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(CashPosition::getAccountType)
                .setHeader("Account Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new ComponentRenderer<>(this::createBalanceComponent))
                .setAutoWidth(true)
                .setComparator(CashPosition::getBalance)
                .setFlexGrow(0)
                .setHeader("Balance")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(CashPosition::getCurrency)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Currency");
        grid.addColumn(cashPosition -> {
                    if (cashPosition.getLastUpdated() != null) {
                        return cashPosition.getLastUpdated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    }
                    return "";
                })
                .setAutoWidth(true)
                .setComparator(CashPosition::getLastUpdated)
                .setFlexGrow(0)
                .setHeader("Last Updated");
        grid.addColumn(CashPosition::getStatus)
                .setHeader("Status")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(CashPosition::getBankName)
                .setHeader("Bank Name")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(CashPosition::getBranchCode)
                .setHeader("Branch Code")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(CashPosition::getAvailableForTrading)
                .setHeader("Available for Trading")
                .setSortable(true)
                .setWidth("150px");

        return grid;
    }

    private Component createBalanceComponent(CashPosition cashPosition) {
        Double balance = cashPosition.getBalance();
        Span balanceLabel = new Span(UIUtils.formatAmount(balance));
        balanceLabel.addClassName(LumoStyles.FontFamily.MONOSPACE);

        // Color positive amounts green and negative amounts red
        if (balance >= 0) {
            UIUtils.setTextColor(TextColor.SUCCESS, balanceLabel);
        } else {
            UIUtils.setTextColor(TextColor.ERROR, balanceLabel);
        }

        return balanceLabel;
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply ID filter
        if (idFilter.getValue() != null && !idFilter.getValue().isEmpty()) {
            String idValue = idFilter.getValue();
            dataProvider.addFilter(cashPosition -> 
                cashPosition.getId() != null && 
                cashPosition.getId().contains(idValue));
        }

        // Apply account number filter
        if (accountNumberFilter.getValue() != null && !accountNumberFilter.getValue().isEmpty()) {
            String accountNumberValue = accountNumberFilter.getValue().toLowerCase();
            dataProvider.addFilter(cashPosition -> 
                cashPosition.getAccountNumber() != null && 
                cashPosition.getAccountNumber().toLowerCase().contains(accountNumberValue));
        }

        // Apply account name filter
        if (accountNameFilter.getValue() != null && !accountNameFilter.getValue().isEmpty()) {
            String accountNameValue = accountNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(cashPosition -> 
                cashPosition.getAccountName() != null && 
                cashPosition.getAccountName().toLowerCase().contains(accountNameValue));
        }

        // Apply account type filter
        if (accountTypeFilter.getValue() != null) {
            String accountTypeValue = accountTypeFilter.getValue();
            dataProvider.addFilter(cashPosition -> 
                cashPosition.getAccountType() != null && 
                cashPosition.getAccountType().equals(accountTypeValue));
        }

        // Apply min balance filter
        if (minBalanceFilter.getValue() != null) {
            Double minValue = minBalanceFilter.getValue();
            dataProvider.addFilter(cashPosition -> 
                cashPosition.getBalance() != null && 
                cashPosition.getBalance() >= minValue);
        }

        // Apply max balance filter
        if (maxBalanceFilter.getValue() != null) {
            Double maxValue = maxBalanceFilter.getValue();
            dataProvider.addFilter(cashPosition -> 
                cashPosition.getBalance() != null && 
                cashPosition.getBalance() <= maxValue);
        }

        // Apply currency filter
        if (currencyFilter.getValue() != null) {
            String currencyValue = currencyFilter.getValue();
            dataProvider.addFilter(cashPosition -> 
                cashPosition.getCurrency() != null && 
                cashPosition.getCurrency().equals(currencyValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(cashPosition -> 
                cashPosition.getStatus() != null && 
                cashPosition.getStatus().equals(statusValue));
        }

        // Apply bank name filter
        if (bankNameFilter.getValue() != null && !bankNameFilter.getValue().isEmpty()) {
            String bankNameValue = bankNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(cashPosition -> 
                cashPosition.getBankName() != null && 
                cashPosition.getBankName().toLowerCase().contains(bankNameValue));
        }

        // Apply branch code filter
        if (branchCodeFilter.getValue() != null && !branchCodeFilter.getValue().isEmpty()) {
            String branchCodeValue = branchCodeFilter.getValue().toLowerCase();
            dataProvider.addFilter(cashPosition -> 
                cashPosition.getBranchCode() != null && 
                cashPosition.getBranchCode().toLowerCase().contains(branchCodeValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        idFilter.clear();
        accountNumberFilter.clear();
        accountNameFilter.clear();
        accountTypeFilter.clear();
        minBalanceFilter.clear();
        maxBalanceFilter.clear();
        currencyFilter.clear();
        statusFilter.clear();
        bankNameFilter.clear();
        branchCodeFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private String[] getAccountTypes() {
        CashPosition.AccountType[] types = CashPosition.AccountType.values();
        String[] typeNames = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            typeNames[i] = types[i].getName();
        }
        return typeNames;
    }

    private String[] getAccountStatuses() {
        CashPosition.Status[] statuses = CashPosition.Status.values();
        String[] statusNames = new String[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            statusNames[i] = statuses[i].getName();
        }
        return statusNames;
    }
}