package com.vaadin.starter.business.ui.views.accountoperations;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.accountoperations.DirectDebitDTO;
import com.vaadin.starter.business.backend.service.DirectDebitService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PageTitle(NavigationConstants.DIRECT_DEBITS)
@Route(value = "account-operations/direct-debits", layout = MainLayout.class)
public class DirectDebits extends ViewFrame {

    private Grid<DirectDebitDTO> grid;
    private ListDataProvider<DirectDebitDTO> dataProvider;

    // Filter form fields
    private TextField mandateIdFilter;
    private TextField accountNumberFilter;
    private TextField creditorFilter;
    private ComboBox<String> statusFilter;
    private DatePicker nextCollectionDateFromFilter;
    private DatePicker nextCollectionDateToFilter;
    private ComboBox<String> frequencyFilter;

    private final DirectDebitService directDebitService;

    @Autowired
    public DirectDebits(DirectDebitService directDebitService) {
        this.directDebitService = directDebitService;
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
        mandateIdFilter = new TextField();
        mandateIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        mandateIdFilter.setClearButtonVisible(true);

        accountNumberFilter = new TextField();
        accountNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        accountNumberFilter.setClearButtonVisible(true);

        creditorFilter = new TextField();
        creditorFilter.setValueChangeMode(ValueChangeMode.EAGER);
        creditorFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("Active", "Suspended", "Cancelled");
        statusFilter.setClearButtonVisible(true);

        nextCollectionDateFromFilter = new DatePicker();
        nextCollectionDateFromFilter.setClearButtonVisible(true);

        nextCollectionDateToFilter = new DatePicker();
        nextCollectionDateToFilter.setClearButtonVisible(true);

        frequencyFilter = new ComboBox<>();
        frequencyFilter.setItems("Daily", "Weekly", "Monthly", "Quarterly", "Annually");
        frequencyFilter.setClearButtonVisible(true);

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
        formLayout.addFormItem(mandateIdFilter, "Mandate ID");
        formLayout.addFormItem(accountNumberFilter, "Account Number");
        formLayout.addFormItem(creditorFilter, "Creditor");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(nextCollectionDateFromFilter, "Next Collection From");
        formLayout.addFormItem(nextCollectionDateToFilter, "Next Collection To");
        formLayout.addFormItem(frequencyFilter, "Frequency");

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

    private Grid<DirectDebitDTO> createGrid() {
        grid = new Grid<>();

        // Initialize with data from service
        Collection<DirectDebitDTO> debits = directDebitService.getDirectDebits();
        dataProvider = new ListDataProvider<>(debits);
        grid.setDataProvider(dataProvider);

        grid.setId("directDebits");
        grid.setSizeFull();

        // Add columns
        grid.addColumn(DirectDebitDTO::getMandateId)
                .setHeader("Mandate ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(DirectDebitDTO::getAccountNumber)
                .setHeader("Account Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(DirectDebitDTO::getCreditor)
                .setHeader("Creditor")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(DirectDebitDTO::getCreditorId)
                .setHeader("Creditor ID")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(DirectDebitDTO::getAmount)
                .setHeader("Amount")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(DirectDebitDTO::getFrequency)
                .setHeader("Frequency")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(new LocalDateRenderer<>(DirectDebitDTO::getNextCollectionDate, "MMM dd, YYYY"))
                .setHeader("Next Collection")
                .setSortable(true)
                .setComparator(DirectDebitDTO::getNextCollectionDate)
                .setWidth("150px");
        grid.addColumn(DirectDebitDTO::getStatus)
                .setHeader("Status")
                .setSortable(true)
                .setWidth("120px");

        // Add action buttons
        grid.addComponentColumn(debit -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button editButton = UIUtils.createSmallButton("Edit");
            editButton.addClickListener(e -> {
                // Edit logic would go here
            });

            Button suspendButton = UIUtils.createSmallButton(
                    debit.getStatus().equals("Active") ? "Suspend" : 
                    debit.getStatus().equals("Suspended") ? "Activate" : "");

            if (!debit.getStatus().equals("Cancelled")) {
                suspendButton.addClickListener(e -> {
                    if (debit.getStatus().equals("Active")) {
                        directDebitService.suspendDirectDebit(debit.getMandateId());
                        refreshGrid();
                    } else if (debit.getStatus().equals("Suspended")) {
                        directDebitService.activateDirectDebit(debit.getMandateId());
                        refreshGrid();
                    }
                });
                actions.add(editButton, suspendButton);
            } else {
                actions.add(editButton);
            }

            return actions;
        }).setHeader("Actions").setWidth("200px");

        return grid;
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply mandate ID filter
        if (mandateIdFilter.getValue() != null && !mandateIdFilter.getValue().isEmpty()) {
            String mandateIdValue = mandateIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(debit -> 
                debit.getMandateId() != null && 
                debit.getMandateId().toLowerCase().contains(mandateIdValue));
        }

        // Apply account number filter
        if (accountNumberFilter.getValue() != null && !accountNumberFilter.getValue().isEmpty()) {
            String accountNumberValue = accountNumberFilter.getValue().toLowerCase();
            dataProvider.addFilter(debit -> 
                debit.getAccountNumber() != null && 
                debit.getAccountNumber().toLowerCase().contains(accountNumberValue));
        }

        // Apply creditor filter
        if (creditorFilter.getValue() != null && !creditorFilter.getValue().isEmpty()) {
            String creditorValue = creditorFilter.getValue().toLowerCase();
            dataProvider.addFilter(debit -> 
                debit.getCreditor() != null && 
                debit.getCreditor().toLowerCase().contains(creditorValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(debit -> 
                debit.getStatus() != null && 
                debit.getStatus().equals(statusValue));
        }

        // Apply next collection date from filter
        if (nextCollectionDateFromFilter.getValue() != null) {
            LocalDate fromDate = nextCollectionDateFromFilter.getValue();
            dataProvider.addFilter(debit -> 
                debit.getNextCollectionDate() != null && 
                !debit.getNextCollectionDate().isBefore(fromDate));
        }

        // Apply next collection date to filter
        if (nextCollectionDateToFilter.getValue() != null) {
            LocalDate toDate = nextCollectionDateToFilter.getValue();
            dataProvider.addFilter(debit -> 
                debit.getNextCollectionDate() != null && 
                !debit.getNextCollectionDate().isAfter(toDate));
        }

        // Apply frequency filter
        if (frequencyFilter.getValue() != null) {
            String frequencyValue = frequencyFilter.getValue();
            dataProvider.addFilter(debit -> 
                debit.getFrequency() != null && 
                debit.getFrequency().equals(frequencyValue));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        mandateIdFilter.clear();
        accountNumberFilter.clear();
        creditorFilter.clear();
        statusFilter.clear();
        nextCollectionDateFromFilter.clear();
        nextCollectionDateToFilter.clear();
        frequencyFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private void refreshGrid() {
        // Refresh data from service
        Collection<DirectDebitDTO> debits = directDebitService.getDirectDebits();
        dataProvider = new ListDataProvider<>(debits);
        grid.setDataProvider(dataProvider);
        applyFilters(); // Re-apply any active filters
    }

}
