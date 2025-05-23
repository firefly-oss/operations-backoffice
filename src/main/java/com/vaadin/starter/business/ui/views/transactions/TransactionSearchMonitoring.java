package com.vaadin.starter.business.ui.views.transactions;

import com.vaadin.flow.component.Component;
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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.Transaction;
import com.vaadin.starter.business.backend.service.TransactionService;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@PageTitle(NavigationConstants.TRANSACTION_SEARCH_MONITORING)
@Route(value = "transactions/search", layout = MainLayout.class)
public class TransactionSearchMonitoring extends ViewFrame {

    private Grid<Transaction> grid;
    private ListDataProvider<Transaction> dataProvider;

    // Filter form fields
    private TextField idFilter;
    private ComboBox<String> typeFilter;
    private ComboBox<String> statusFilter;
    private NumberField minAmountFilter;
    private NumberField maxAmountFilter;
    private ComboBox<String> currencyFilter;
    private TextField sourceAccountFilter;
    private TextField destinationAccountFilter;
    private DatePicker startDateFilter;
    private DatePicker endDateFilter;
    private TextField referenceFilter;

    private final TransactionService transactionService;

    @Autowired
    public TransactionSearchMonitoring(TransactionService transactionService) {
        this.transactionService = transactionService;
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

    private void viewDetails(Transaction transaction) {
        UI.getCurrent().navigate(TransactionDetails.class, transaction.getId());
    }

    private Component createFilterForm() {
        // Initialize filter fields
        idFilter = new TextField();
        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.setClearButtonVisible(true);

        typeFilter = new ComboBox<>();
        typeFilter.setItems(getTransactionTypes());
        typeFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems(getTransactionStatuses());
        statusFilter.setClearButtonVisible(true);

        minAmountFilter = new NumberField();
        minAmountFilter.setClearButtonVisible(true);

        maxAmountFilter = new NumberField();
        maxAmountFilter.setClearButtonVisible(true);

        currencyFilter = new ComboBox<>();
        currencyFilter.setItems("USD", "EUR", "GBP", "JPY", "CAD", "AUD");
        currencyFilter.setClearButtonVisible(true);

        sourceAccountFilter = new TextField();
        sourceAccountFilter.setValueChangeMode(ValueChangeMode.EAGER);
        sourceAccountFilter.setClearButtonVisible(true);

        destinationAccountFilter = new TextField();
        destinationAccountFilter.setValueChangeMode(ValueChangeMode.EAGER);
        destinationAccountFilter.setClearButtonVisible(true);

        startDateFilter = new DatePicker();
        startDateFilter.setClearButtonVisible(true);

        endDateFilter = new DatePicker();
        endDateFilter.setClearButtonVisible(true);

        referenceFilter = new TextField();
        referenceFilter.setValueChangeMode(ValueChangeMode.EAGER);
        referenceFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(typeFilter, "Type");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(minAmountFilter, "Min Amount");
        formLayout.addFormItem(maxAmountFilter, "Max Amount");
        formLayout.addFormItem(currencyFilter, "Currency");
        formLayout.addFormItem(sourceAccountFilter, "Source Account");
        formLayout.addFormItem(destinationAccountFilter, "Destination Account");
        formLayout.addFormItem(startDateFilter, "From Date");
        formLayout.addFormItem(endDateFilter, "To Date");
        formLayout.addFormItem(referenceFilter, "Reference");

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

    private Grid<Transaction> createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));

        // Initialize data provider
        Collection<Transaction> transactions = transactionService.getTransactions();
        dataProvider = new ListDataProvider<>(transactions);
        grid.setDataProvider(dataProvider);

        grid.setId("transactions");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(Transaction::getId)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("ID")
                .setSortable(true);
        grid.addColumn(Transaction::getType)
                .setHeader("Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Transaction::getStatus)
                .setHeader("Status")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new ComponentRenderer<>(this::createAmountComponent))
                .setAutoWidth(true)
                .setComparator(Transaction::getAmount)
                .setFlexGrow(0)
                .setHeader("Amount")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(Transaction::getCurrency)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Currency");
        grid.addColumn(Transaction::getSourceAccount)
                .setHeader("Source Account")
                .setWidth("200px");
        grid.addColumn(Transaction::getDestinationAccount)
                .setHeader("Destination Account")
                .setWidth("200px");
        grid.addColumn(transaction -> {
                    if (transaction.getTimestamp() != null) {
                        return transaction.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    }
                    return "";
                })
                .setAutoWidth(true)
                .setComparator(Transaction::getTimestamp)
                .setFlexGrow(0)
                .setHeader("Timestamp");
        grid.addColumn(Transaction::getReference)
                .setHeader("Reference")
                .setWidth("150px");
        grid.addColumn(Transaction::getDescription)
                .setHeader("Description")
                .setWidth("200px");

        return grid;
    }

    private Component createAmountComponent(Transaction transaction) {
        Double amount = transaction.getAmount();
        Span amountLabel = new Span(UIUtils.formatAmount(amount));
        amountLabel.addClassName(LumoStyles.FontFamily.MONOSPACE);

        // Color positive amounts green and negative amounts red
        if (amount >= 0) {
            UIUtils.setTextColor(TextColor.SUCCESS, amountLabel);
        } else {
            UIUtils.setTextColor(TextColor.ERROR, amountLabel);
        }

        return amountLabel;
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply ID filter
        if (idFilter.getValue() != null && !idFilter.getValue().isEmpty()) {
            String idValue = idFilter.getValue();
            dataProvider.addFilter(transaction -> 
                transaction.getId() != null && 
                transaction.getId().contains(idValue));
        }

        // Apply type filter
        if (typeFilter.getValue() != null) {
            String typeValue = typeFilter.getValue();
            dataProvider.addFilter(transaction -> 
                transaction.getType() != null && 
                transaction.getType().equals(typeValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(transaction -> 
                transaction.getStatus() != null && 
                transaction.getStatus().equals(statusValue));
        }

        // Apply min amount filter
        if (minAmountFilter.getValue() != null) {
            Double minValue = minAmountFilter.getValue();
            dataProvider.addFilter(transaction -> 
                transaction.getAmount() != null && 
                transaction.getAmount() >= minValue);
        }

        // Apply max amount filter
        if (maxAmountFilter.getValue() != null) {
            Double maxValue = maxAmountFilter.getValue();
            dataProvider.addFilter(transaction -> 
                transaction.getAmount() != null && 
                transaction.getAmount() <= maxValue);
        }

        // Apply currency filter
        if (currencyFilter.getValue() != null) {
            String currencyValue = currencyFilter.getValue();
            dataProvider.addFilter(transaction -> 
                transaction.getCurrency() != null && 
                transaction.getCurrency().equals(currencyValue));
        }

        // Apply source account filter
        if (sourceAccountFilter.getValue() != null && !sourceAccountFilter.getValue().isEmpty()) {
            String sourceValue = sourceAccountFilter.getValue().toLowerCase();
            dataProvider.addFilter(transaction -> 
                transaction.getSourceAccount() != null && 
                transaction.getSourceAccount().toLowerCase().contains(sourceValue));
        }

        // Apply destination account filter
        if (destinationAccountFilter.getValue() != null && !destinationAccountFilter.getValue().isEmpty()) {
            String destValue = destinationAccountFilter.getValue().toLowerCase();
            dataProvider.addFilter(transaction -> 
                transaction.getDestinationAccount() != null && 
                transaction.getDestinationAccount().toLowerCase().contains(destValue));
        }

        // Apply start date filter
        if (startDateFilter.getValue() != null) {
            LocalDate startDate = startDateFilter.getValue();
            dataProvider.addFilter(transaction -> 
                transaction.getTimestamp() != null && 
                !transaction.getTimestamp().toLocalDate().isBefore(startDate));
        }

        // Apply end date filter
        if (endDateFilter.getValue() != null) {
            LocalDate endDate = endDateFilter.getValue();
            dataProvider.addFilter(transaction -> 
                transaction.getTimestamp() != null && 
                !transaction.getTimestamp().toLocalDate().isAfter(endDate));
        }

        // Apply reference filter
        if (referenceFilter.getValue() != null && !referenceFilter.getValue().isEmpty()) {
            String refValue = referenceFilter.getValue().toLowerCase();
            dataProvider.addFilter(transaction -> 
                transaction.getReference() != null && 
                transaction.getReference().toLowerCase().contains(refValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        idFilter.clear();
        typeFilter.clear();
        statusFilter.clear();
        minAmountFilter.clear();
        maxAmountFilter.clear();
        currencyFilter.clear();
        sourceAccountFilter.clear();
        destinationAccountFilter.clear();
        startDateFilter.clear();
        endDateFilter.clear();
        referenceFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private String[] getTransactionTypes() {
        Transaction.Type[] types = Transaction.Type.values();
        String[] typeNames = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            typeNames[i] = types[i].getName();
        }
        return typeNames;
    }

    private String[] getTransactionStatuses() {
        Transaction.Status[] statuses = Transaction.Status.values();
        String[] statusNames = new String[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            statusNames[i] = statuses[i].getName();
        }
        return statusNames;
    }
}
