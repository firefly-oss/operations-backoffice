package com.vaadin.starter.business.ui.views.cardoperations;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
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
import com.vaadin.starter.business.backend.dto.cardoperations.DisputeDTO;
import com.vaadin.starter.business.backend.service.CardService;
import com.vaadin.starter.business.backend.service.DisputeService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.*;
import com.vaadin.starter.business.ui.util.FontSize;
import com.vaadin.starter.business.ui.util.LineHeight;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.util.css.Overflow;
import com.vaadin.starter.business.ui.util.css.PointerEvents;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeColor;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeShape;
import com.vaadin.starter.business.ui.util.css.lumo.BadgeSize;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PageTitle(NavigationConstants.DISPUTES_MANAGEMENT)
@Route(value = "card-operations/disputes", layout = MainLayout.class)
public class DisputesManagement extends ViewFrame {

    public static final int MOBILE_BREAKPOINT = 480;
    private Grid<DisputeDTO> grid;
    private Registration resizeListener;
    private ListDataProvider<DisputeDTO> dataProvider;

    // Filter form fields
    private TextField disputeIdFilter;
    private TextField cardNumberFilter;
    private TextField cardHolderNameFilter;
    private ComboBox<String> statusFilter;
    private ComboBox<String> typeFilter;
    private NumberField amountFromFilter;
    private NumberField amountToFilter;
    private DatePicker dateFromFilter;
    private DatePicker dateToFilter;

    private final CardService cardService;
    private final DisputeService disputeService;

    @Autowired
    public DisputesManagement(CardService cardService, DisputeService disputeService) {
        this.cardService = cardService;
        this.disputeService = disputeService;
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createHeader(), createFilterForm(), createGrid());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createHeader() {
        H3 header = new H3("Disputes Management");
        header.getStyle().set("margin-top", "0");

        Span description = new Span("Manage card disputes and chargebacks.");
        description.getStyle().set("color", "var(--lumo-secondary-text-color)");

        FlexBoxLayout headerLayout = new FlexBoxLayout(header, description);
        headerLayout.setFlexDirection(FlexDirection.COLUMN);
        headerLayout.setMargin(Bottom.M);

        return headerLayout;
    }

    private Component createFilterForm() {
        // Initialize filter fields
        disputeIdFilter = new TextField();
        disputeIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        disputeIdFilter.setClearButtonVisible(true);

        cardNumberFilter = new TextField();
        cardNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        cardNumberFilter.setClearButtonVisible(true);

        cardHolderNameFilter = new TextField();
        cardHolderNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        cardHolderNameFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("New", "In Progress", "Pending Documentation", "Resolved", "Rejected");
        statusFilter.setClearButtonVisible(true);

        typeFilter = new ComboBox<>();
        typeFilter.setItems("Unauthorized Transaction", "Duplicate Charge", "Merchandise Not Received", "Defective Merchandise", "Incorrect Amount", "Other");
        typeFilter.setClearButtonVisible(true);

        amountFromFilter = new NumberField();
        amountFromFilter.setClearButtonVisible(true);

        amountToFilter = new NumberField();
        amountToFilter.setClearButtonVisible(true);

        dateFromFilter = new DatePicker();
        dateFromFilter.setClearButtonVisible(true);

        dateToFilter = new DatePicker();
        dateToFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> applyFilters());

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> clearFilters());

        Button newDisputeButton = UIUtils.createSuccessButton("New Dispute");
        newDisputeButton.addClickListener(e -> {
            // Logic to create a new dispute would go here
            // For demo purposes, we'll just show a notification
            UIUtils.showNotification("New dispute creation form would open here");
        });

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton, newDisputeButton);
        buttonLayout.setSpacing(true);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(disputeIdFilter, "Dispute ID");
        formLayout.addFormItem(cardNumberFilter, "Card Number");
        formLayout.addFormItem(cardHolderNameFilter, "Card Holder Name");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(typeFilter, "Dispute Type");
        formLayout.addFormItem(amountFromFilter, "Amount From ($)");
        formLayout.addFormItem(amountToFilter, "Amount To ($)");
        formLayout.addFormItem(dateFromFilter, "Date From");
        formLayout.addFormItem(dateToFilter, "Date To");

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
        Collection<DisputeDTO> disputes = disputeService.getDisputes();
        dataProvider = new ListDataProvider<>(disputes);
        grid.setDataProvider(dataProvider);

        grid.setId("disputes");
        grid.setSizeFull();

        // "Mobile" column
        grid.addColumn(new ComponentRenderer<>(this::getMobileTemplate))
                .setVisible(false);

        // "Desktop" columns
        grid.addColumn(DisputeDTO::getDisputeId)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("Dispute ID")
                .setSortable(true);
        grid.addColumn(DisputeDTO::getCardNumber)
                .setHeader("Card Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(DisputeDTO::getCardHolderName)
                .setHeader("Card Holder Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(new ComponentRenderer<>(this::createStatusBadge))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(DisputeDTO::getStatus)
                .setWidth("150px");
        grid.addColumn(DisputeDTO::getType)
                .setHeader("Type")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(dispute -> UIUtils.formatAmount(dispute.getAmount()))
                .setHeader("Amount")
                .setSortable(true)
                .setComparator(DisputeDTO::getAmount)
                .setWidth("120px");
        grid.addColumn(new LocalDateRenderer<>(DisputeDTO::getDate, "MMM dd, yyyy"))
                .setAutoWidth(true)
                .setComparator(DisputeDTO::getDate)
                .setFlexGrow(0)
                .setHeader("Date");
        grid.addColumn(DisputeDTO::getMerchant)
                .setHeader("Merchant")
                .setSortable(true)
                .setWidth("200px");

        // Add action buttons
        grid.addComponentColumn(dispute -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button viewButton = UIUtils.createSmallButton("View");
            viewButton.addClickListener(e -> {
                viewDetails(dispute);
            });

            Button updateButton = UIUtils.createSmallButton("Update");
            updateButton.addClickListener(e -> {
                // Logic to update dispute would go here
                // For demo purposes, we'll just show a notification
                UIUtils.showNotification("Update form for dispute " + dispute.getDisputeId() + " would open here");
            });

            // Add status-specific action buttons
            if (dispute.getStatus().equals("New") || dispute.getStatus().equals("In Progress")) {
                Button documentButton = UIUtils.createSmallButton("Add Documentation");
                documentButton.getElement().getThemeList().add("contrast");
                documentButton.addClickListener(e -> {
                    // In a real app, this would open a form to add documentation
                    String documentation = "Documentation added on " + LocalDate.now();
                    disputeService.addDocumentation(dispute.getDisputeId(), documentation);
                    disputeService.updateDisputeStatus(dispute.getDisputeId(), "Pending Documentation");
                    refreshGrid();
                    UIUtils.showNotification("Documentation added and status updated");
                });
                actions.add(viewButton, updateButton, documentButton);
            } else if (dispute.getStatus().equals("Pending Documentation")) {
                Button resolveButton = UIUtils.createSmallButton("Resolve");
                resolveButton.getElement().getThemeList().add("success");
                Button rejectButton = UIUtils.createSmallButton("Reject");
                rejectButton.getElement().getThemeList().add("error");

                resolveButton.addClickListener(e -> {
                    disputeService.resolveDispute(dispute.getDisputeId());
                    refreshGrid();
                    UIUtils.showNotification("Dispute resolved");
                });

                rejectButton.addClickListener(e -> {
                    disputeService.rejectDispute(dispute.getDisputeId());
                    refreshGrid();
                    UIUtils.showNotification("Dispute rejected");
                });

                actions.add(viewButton, updateButton, resolveButton, rejectButton);
            } else {
                actions.add(viewButton, updateButton);
            }

            return actions;
        }).setHeader("Actions").setWidth("200px");

        return grid;
    }

    private Component createStatusBadge(DisputeDTO dispute) {
        String status = dispute.getStatus();
        BadgeColor color;

        switch (status) {
            case "Resolved":
                color = BadgeColor.SUCCESS;
                break;
            case "Rejected":
                color = BadgeColor.ERROR;
                break;
            case "In Progress":
                color = BadgeColor.CONTRAST;
                break;
            case "Pending Documentation":
                color = BadgeColor.CONTRAST;
                break;
            default:
                color = BadgeColor.NORMAL;
        }

        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private DisputeMobileTemplate getMobileTemplate(DisputeDTO dispute) {
        return new DisputeMobileTemplate(dispute);
    }

    private void viewDetails(DisputeDTO dispute) {
        // Navigate to dispute details view
        // UI.getCurrent().navigate(DisputeDetails.class, dispute.getDisputeId());
        // For demo purposes, we'll just show a notification
        UIUtils.showNotification("Viewing details for dispute: " + dispute.getDisputeId());
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
        List<Grid.Column<DisputeDTO>> columns = grid.getColumns();

        // "Mobile" column
        columns.get(0).setVisible(mobile);

        // "Desktop" columns
        for (int i = 1; i < columns.size(); i++) {
            columns.get(i).setVisible(!mobile);
        }
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply dispute ID filter
        if (disputeIdFilter.getValue() != null && !disputeIdFilter.getValue().isEmpty()) {
            String disputeIdValue = disputeIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(dispute -> 
                dispute.getDisputeId() != null && 
                dispute.getDisputeId().toLowerCase().contains(disputeIdValue));
        }

        // Apply card number filter
        if (cardNumberFilter.getValue() != null && !cardNumberFilter.getValue().isEmpty()) {
            String cardNumberValue = cardNumberFilter.getValue().toLowerCase();
            dataProvider.addFilter(dispute -> 
                dispute.getCardNumber() != null && 
                dispute.getCardNumber().toLowerCase().contains(cardNumberValue));
        }

        // Apply card holder name filter
        if (cardHolderNameFilter.getValue() != null && !cardHolderNameFilter.getValue().isEmpty()) {
            String cardHolderNameValue = cardHolderNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(dispute -> 
                dispute.getCardHolderName() != null && 
                dispute.getCardHolderName().toLowerCase().contains(cardHolderNameValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(dispute -> 
                dispute.getStatus() != null && 
                dispute.getStatus().equals(statusValue));
        }

        // Apply type filter
        if (typeFilter.getValue() != null) {
            String typeValue = typeFilter.getValue();
            dataProvider.addFilter(dispute -> 
                dispute.getType() != null && 
                dispute.getType().equals(typeValue));
        }

        // Apply amount from filter
        if (amountFromFilter.getValue() != null) {
            Double amountFromValue = amountFromFilter.getValue();
            dataProvider.addFilter(dispute -> 
                dispute.getAmount() != null && 
                dispute.getAmount() >= amountFromValue);
        }

        // Apply amount to filter
        if (amountToFilter.getValue() != null) {
            Double amountToValue = amountToFilter.getValue();
            dataProvider.addFilter(dispute -> 
                dispute.getAmount() != null && 
                dispute.getAmount() <= amountToValue);
        }

        // Apply date from filter
        if (dateFromFilter.getValue() != null) {
            LocalDate fromDate = dateFromFilter.getValue();
            dataProvider.addFilter(dispute -> 
                dispute.getDate() != null && 
                !dispute.getDate().isBefore(fromDate));
        }

        // Apply date to filter
        if (dateToFilter.getValue() != null) {
            LocalDate toDate = dateToFilter.getValue();
            dataProvider.addFilter(dispute -> 
                dispute.getDate() != null && 
                !dispute.getDate().isAfter(toDate));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        disputeIdFilter.clear();
        cardNumberFilter.clear();
        cardHolderNameFilter.clear();
        statusFilter.clear();
        typeFilter.clear();
        amountFromFilter.clear();
        amountToFilter.clear();
        dateFromFilter.clear();
        dateToFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    private void refreshGrid() {
        // Refresh data from service
        Collection<DisputeDTO> disputes = disputeService.getDisputes();
        dataProvider = new ListDataProvider<>(disputes);
        grid.setDataProvider(dataProvider);
        applyFilters(); // Re-apply any active filters
    }


    /**
     * A layout for displaying Dispute info in a mobile friendly format.
     */
    private class DisputeMobileTemplate extends FlexBoxLayout {

        private DisputeDTO dispute;

        public DisputeMobileTemplate(DisputeDTO dispute) {
            this.dispute = dispute;

            UIUtils.setLineHeight(LineHeight.M, this);
            UIUtils.setPointerEvents(PointerEvents.NONE, this);

            setPadding(Vertical.S);
            setSpacing(Right.L);

            Span disputeId = new Span(dispute.getDisputeId());
            UIUtils.setFontSize(FontSize.S, disputeId);
            UIUtils.setTextColor(TextColor.SECONDARY, disputeId);

            Span cardInfo = new Span(dispute.getCardNumber() + " - " + dispute.getCardHolderName());
            UIUtils.setFontSize(FontSize.M, cardInfo);
            UIUtils.setTextColor(TextColor.BODY, cardInfo);

            Span type = new Span(dispute.getType());
            UIUtils.setFontSize(FontSize.S, type);
            UIUtils.setTextColor(TextColor.SECONDARY, type);

            Component statusBadge = createStatusBadge(dispute);

            Span amount = new Span("$" + dispute.getAmount());
            UIUtils.setFontSize(FontSize.M, amount);
            UIUtils.setTextColor(TextColor.SUCCESS, amount);

            Span date = new Span(dispute.getDate().format(java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            UIUtils.setFontSize(FontSize.XS, date);
            UIUtils.setTextColor(TextColor.TERTIARY, date);

            FlexBoxLayout column = new FlexBoxLayout(disputeId, cardInfo, type, statusBadge, amount, date);
            column.setFlexDirection(FlexDirection.COLUMN);
            column.setOverflow(Overflow.HIDDEN);
            column.setSpacing(Vertical.XS);

            add(column);
            setFlexGrow(1, column);
        }
    }

}
