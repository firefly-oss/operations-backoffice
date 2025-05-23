package com.vaadin.starter.business.ui.views.cashmanagement;

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
import com.vaadin.starter.business.backend.TreasuryOperation;
import com.vaadin.starter.business.backend.service.TreasuryOperationService;
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

@PageTitle(NavigationConstants.TREASURY_OPERATIONS)
@Route(value = "cash-management/treasury-operations", layout = MainLayout.class)
public class TreasuryOperations extends ViewFrame {

    private Grid<TreasuryOperation> grid;
    private ListDataProvider<TreasuryOperation> dataProvider;

    // Filter form fields
    private TextField idFilter;
    private ComboBox<String> operationTypeFilter;
    private ComboBox<String> statusFilter;
    private NumberField minAmountFilter;
    private NumberField maxAmountFilter;
    private ComboBox<String> currencyFilter;
    private TextField counterpartyFilter;
    private DatePicker startDateFilter;
    private DatePicker endDateFilter;
    private TextField traderFilter;
    private TextField instrumentFilter;
    private TextField referenceFilter;

    private final TreasuryOperationService treasuryOperationService;

    @Autowired
    public TreasuryOperations(TreasuryOperationService treasuryOperationService) {
        this.treasuryOperationService = treasuryOperationService;
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

    private void viewDetails(TreasuryOperation treasuryOperation) {
        UI.getCurrent().navigate(TreasuryOperationDetails.class, treasuryOperation.getId());
    }

    private Component createFilterForm() {
        // Initialize filter fields
        idFilter = new TextField();
        idFilter.setValueChangeMode(ValueChangeMode.EAGER);
        idFilter.setClearButtonVisible(true);

        operationTypeFilter = new ComboBox<>();
        operationTypeFilter.setItems(getOperationTypes());
        operationTypeFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems(getOperationStatuses());
        statusFilter.setClearButtonVisible(true);

        minAmountFilter = new NumberField();
        minAmountFilter.setClearButtonVisible(true);

        maxAmountFilter = new NumberField();
        maxAmountFilter.setClearButtonVisible(true);

        currencyFilter = new ComboBox<>();
        currencyFilter.setItems("USD", "EUR", "GBP", "JPY", "CAD", "AUD");
        currencyFilter.setClearButtonVisible(true);

        counterpartyFilter = new TextField();
        counterpartyFilter.setValueChangeMode(ValueChangeMode.EAGER);
        counterpartyFilter.setClearButtonVisible(true);

        startDateFilter = new DatePicker();
        startDateFilter.setClearButtonVisible(true);

        endDateFilter = new DatePicker();
        endDateFilter.setClearButtonVisible(true);

        traderFilter = new TextField();
        traderFilter.setValueChangeMode(ValueChangeMode.EAGER);
        traderFilter.setClearButtonVisible(true);

        instrumentFilter = new TextField();
        instrumentFilter.setValueChangeMode(ValueChangeMode.EAGER);
        instrumentFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(operationTypeFilter, "Operation Type");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(minAmountFilter, "Min Amount");
        formLayout.addFormItem(maxAmountFilter, "Max Amount");
        formLayout.addFormItem(currencyFilter, "Currency");
        formLayout.addFormItem(counterpartyFilter, "Counterparty");
        formLayout.addFormItem(startDateFilter, "From Date");
        formLayout.addFormItem(endDateFilter, "To Date");
        formLayout.addFormItem(traderFilter, "Trader");
        formLayout.addFormItem(instrumentFilter, "Instrument");
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

    private Grid<TreasuryOperation> createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));

        // Initialize data provider
        Collection<TreasuryOperation> treasuryOperations = treasuryOperationService.getTreasuryOperations();
        dataProvider = new ListDataProvider<>(treasuryOperations);
        grid.setDataProvider(dataProvider);

        grid.setId("treasuryOperations");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(TreasuryOperation::getId)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("ID")
                .setSortable(true);
        grid.addColumn(TreasuryOperation::getOperationType)
                .setHeader("Operation Type")
                .setSortable(true)
                .setWidth("180px");
        grid.addColumn(TreasuryOperation::getStatus)
                .setHeader("Status")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(this::createAmountComponent))
                .setAutoWidth(true)
                .setComparator(TreasuryOperation::getAmount)
                .setFlexGrow(0)
                .setHeader("Amount")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(TreasuryOperation::getCurrency)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Currency");
        grid.addColumn(TreasuryOperation::getCounterparty)
                .setHeader("Counterparty")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(treasuryOperation -> {
                    if (treasuryOperation.getExecutionDate() != null) {
                        return treasuryOperation.getExecutionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    }
                    return "";
                })
                .setAutoWidth(true)
                .setComparator(TreasuryOperation::getExecutionDate)
                .setFlexGrow(0)
                .setHeader("Execution Date");
        grid.addColumn(treasuryOperation -> {
                    if (treasuryOperation.getSettlementDate() != null) {
                        return treasuryOperation.getSettlementDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    }
                    return "";
                })
                .setAutoWidth(true)
                .setComparator(TreasuryOperation::getSettlementDate)
                .setFlexGrow(0)
                .setHeader("Settlement Date");
        grid.addColumn(TreasuryOperation::getTrader)
                .setHeader("Trader")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(TreasuryOperation::getInstrument)
                .setHeader("Instrument")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(TreasuryOperation::getRate)
                .setHeader("Rate")
                .setSortable(true)
                .setWidth("100px");
        grid.addColumn(TreasuryOperation::getReference)
                .setHeader("Reference")
                .setSortable(true)
                .setWidth("120px");

        return grid;
    }

    private Component createAmountComponent(TreasuryOperation treasuryOperation) {
        Double amount = treasuryOperation.getAmount();
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
            dataProvider.addFilter(treasuryOperation -> 
                treasuryOperation.getId() != null && 
                treasuryOperation.getId().contains(idValue));
        }

        // Apply operation type filter
        if (operationTypeFilter.getValue() != null) {
            String typeValue = operationTypeFilter.getValue();
            dataProvider.addFilter(treasuryOperation -> 
                treasuryOperation.getOperationType() != null && 
                treasuryOperation.getOperationType().equals(typeValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(treasuryOperation -> 
                treasuryOperation.getStatus() != null && 
                treasuryOperation.getStatus().equals(statusValue));
        }

        // Apply min amount filter
        if (minAmountFilter.getValue() != null) {
            Double minValue = minAmountFilter.getValue();
            dataProvider.addFilter(treasuryOperation -> 
                treasuryOperation.getAmount() != null && 
                treasuryOperation.getAmount() >= minValue);
        }

        // Apply max amount filter
        if (maxAmountFilter.getValue() != null) {
            Double maxValue = maxAmountFilter.getValue();
            dataProvider.addFilter(treasuryOperation -> 
                treasuryOperation.getAmount() != null && 
                treasuryOperation.getAmount() <= maxValue);
        }

        // Apply currency filter
        if (currencyFilter.getValue() != null) {
            String currencyValue = currencyFilter.getValue();
            dataProvider.addFilter(treasuryOperation -> 
                treasuryOperation.getCurrency() != null && 
                treasuryOperation.getCurrency().equals(currencyValue));
        }

        // Apply counterparty filter
        if (counterpartyFilter.getValue() != null && !counterpartyFilter.getValue().isEmpty()) {
            String counterpartyValue = counterpartyFilter.getValue().toLowerCase();
            dataProvider.addFilter(treasuryOperation -> 
                treasuryOperation.getCounterparty() != null && 
                treasuryOperation.getCounterparty().toLowerCase().contains(counterpartyValue));
        }

        // Apply start date filter
        if (startDateFilter.getValue() != null) {
            java.time.LocalDate startDate = startDateFilter.getValue();
            dataProvider.addFilter(treasuryOperation -> 
                treasuryOperation.getExecutionDate() != null && 
                !treasuryOperation.getExecutionDate().toLocalDate().isBefore(startDate));
        }

        // Apply end date filter
        if (endDateFilter.getValue() != null) {
            java.time.LocalDate endDate = endDateFilter.getValue();
            dataProvider.addFilter(treasuryOperation -> 
                treasuryOperation.getExecutionDate() != null && 
                !treasuryOperation.getExecutionDate().toLocalDate().isAfter(endDate));
        }

        // Apply trader filter
        if (traderFilter.getValue() != null && !traderFilter.getValue().isEmpty()) {
            String traderValue = traderFilter.getValue().toLowerCase();
            dataProvider.addFilter(treasuryOperation -> 
                treasuryOperation.getTrader() != null && 
                treasuryOperation.getTrader().toLowerCase().contains(traderValue));
        }

        // Apply instrument filter
        if (instrumentFilter.getValue() != null && !instrumentFilter.getValue().isEmpty()) {
            String instrumentValue = instrumentFilter.getValue().toLowerCase();
            dataProvider.addFilter(treasuryOperation -> 
                treasuryOperation.getInstrument() != null && 
                treasuryOperation.getInstrument().toLowerCase().contains(instrumentValue));
        }

        // Apply reference filter
        if (referenceFilter.getValue() != null && !referenceFilter.getValue().isEmpty()) {
            String referenceValue = referenceFilter.getValue().toLowerCase();
            dataProvider.addFilter(treasuryOperation -> 
                treasuryOperation.getReference() != null && 
                treasuryOperation.getReference().toLowerCase().contains(referenceValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        idFilter.clear();
        operationTypeFilter.clear();
        statusFilter.clear();
        minAmountFilter.clear();
        maxAmountFilter.clear();
        currencyFilter.clear();
        counterpartyFilter.clear();
        startDateFilter.clear();
        endDateFilter.clear();
        traderFilter.clear();
        instrumentFilter.clear();
        referenceFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private String[] getOperationTypes() {
        TreasuryOperation.OperationType[] types = TreasuryOperation.OperationType.values();
        String[] typeNames = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            typeNames[i] = types[i].getName();
        }
        return typeNames;
    }

    private String[] getOperationStatuses() {
        TreasuryOperation.Status[] statuses = TreasuryOperation.Status.values();
        String[] statusNames = new String[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            statusNames[i] = statuses[i].getName();
        }
        return statusNames;
    }
}