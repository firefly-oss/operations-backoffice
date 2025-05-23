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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.business.backend.service.CardService;
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
import java.util.List;

@PageTitle(NavigationConstants.REPLACEMENT_REQUESTS)
@Route(value = "card-operations/replacements", layout = MainLayout.class)
public class ReplacementRequests extends ViewFrame {

    public static final int MOBILE_BREAKPOINT = 480;
    private Grid<ReplacementRequest> grid;
    private Registration resizeListener;
    private ListDataProvider<ReplacementRequest> dataProvider;

    // Filter form fields
    private TextField requestIdFilter;
    private TextField cardNumberFilter;
    private TextField cardHolderNameFilter;
    private ComboBox<String> statusFilter;
    private ComboBox<String> reasonFilter;
    private DatePicker requestDateFromFilter;
    private DatePicker requestDateToFilter;
    private DatePicker completionDateFromFilter;
    private DatePicker completionDateToFilter;

    private final CardService cardService;

    @Autowired
    public ReplacementRequests(CardService cardService) {
        this.cardService = cardService;
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
        H3 header = new H3("Card Replacement Requests");
        header.getStyle().set("margin-top", "0");
        
        Span description = new Span("Manage card replacement requests for lost, stolen, damaged, or expired cards.");
        description.getStyle().set("color", "var(--lumo-secondary-text-color)");
        
        FlexBoxLayout headerLayout = new FlexBoxLayout(header, description);
        headerLayout.setFlexDirection(FlexDirection.COLUMN);
        headerLayout.setMargin(Bottom.M);
        
        return headerLayout;
    }

    private Component createFilterForm() {
        // Initialize filter fields
        requestIdFilter = new TextField();
        requestIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        requestIdFilter.setClearButtonVisible(true);

        cardNumberFilter = new TextField();
        cardNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        cardNumberFilter.setClearButtonVisible(true);

        cardHolderNameFilter = new TextField();
        cardHolderNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        cardHolderNameFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("New", "In Progress", "Card Issued", "Completed", "Rejected");
        statusFilter.setClearButtonVisible(true);

        reasonFilter = new ComboBox<>();
        reasonFilter.setItems("Lost", "Stolen", "Damaged", "Expired", "Compromised", "Name Change", "Other");
        reasonFilter.setClearButtonVisible(true);

        requestDateFromFilter = new DatePicker();
        requestDateFromFilter.setClearButtonVisible(true);

        requestDateToFilter = new DatePicker();
        requestDateToFilter.setClearButtonVisible(true);

        completionDateFromFilter = new DatePicker();
        completionDateFromFilter.setClearButtonVisible(true);

        completionDateToFilter = new DatePicker();
        completionDateToFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> applyFilters());

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> clearFilters());

        Button newRequestButton = UIUtils.createSuccessButton("New Request");
        newRequestButton.addClickListener(e -> {
            // Logic to create a new replacement request would go here
            // For demo purposes, we'll just show a notification
            UIUtils.showNotification("New replacement request form would open here");
        });

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton, newRequestButton);
        buttonLayout.setSpacing(true);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(requestIdFilter, "Request ID");
        formLayout.addFormItem(cardNumberFilter, "Card Number");
        formLayout.addFormItem(cardHolderNameFilter, "Card Holder Name");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(reasonFilter, "Reason");
        formLayout.addFormItem(requestDateFromFilter, "Request Date From");
        formLayout.addFormItem(requestDateToFilter, "Request Date To");
        formLayout.addFormItem(completionDateFromFilter, "Completion Date From");
        formLayout.addFormItem(completionDateToFilter, "Completion Date To");

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

        // Initialize with dummy data
        List<ReplacementRequest> requests = getDummyReplacementRequests();
        dataProvider = new ListDataProvider<>(requests);
        grid.setDataProvider(dataProvider);

        grid.setId("replacementRequests");
        grid.setSizeFull();

        // "Mobile" column
        grid.addColumn(new ComponentRenderer<>(this::getMobileTemplate))
                .setVisible(false);

        // "Desktop" columns
        grid.addColumn(ReplacementRequest::getRequestId)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("Request ID")
                .setSortable(true);
        grid.addColumn(ReplacementRequest::getCardNumber)
                .setHeader("Card Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(ReplacementRequest::getCardHolderName)
                .setHeader("Card Holder Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(new ComponentRenderer<>(this::createStatusBadge))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(ReplacementRequest::getStatus)
                .setWidth("120px");
        grid.addColumn(ReplacementRequest::getReason)
                .setHeader("Reason")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new LocalDateRenderer<>(ReplacementRequest::getRequestDate, "MMM dd, yyyy"))
                .setAutoWidth(true)
                .setComparator(ReplacementRequest::getRequestDate)
                .setFlexGrow(0)
                .setHeader("Request Date");
        grid.addColumn(new LocalDateRenderer<>(ReplacementRequest::getCompletionDate, "MMM dd, yyyy"))
                .setAutoWidth(true)
                .setComparator(ReplacementRequest::getCompletionDate)
                .setFlexGrow(0)
                .setHeader("Completion Date");
        grid.addColumn(ReplacementRequest::getNewCardNumber)
                .setHeader("New Card Number")
                .setSortable(true)
                .setWidth("150px");

        // Add action buttons
        grid.addComponentColumn(request -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button viewButton = UIUtils.createSmallButton("View");
            viewButton.addClickListener(e -> {
                viewDetails(request);
            });

            Button updateButton = UIUtils.createSmallButton("Update");
            updateButton.addClickListener(e -> {
                // Logic to update request would go here
                // For demo purposes, we'll just show a notification
                UIUtils.showNotification("Update form for request " + request.getRequestId() + " would open here");
            });

            actions.add(viewButton, updateButton);
            return actions;
        }).setHeader("Actions").setWidth("150px");

        return grid;
    }

    private Component createStatusBadge(ReplacementRequest request) {
        String status = request.getStatus();
        BadgeColor color;
        
        switch (status) {
            case "Completed":
                color = BadgeColor.SUCCESS;
                break;
            case "Rejected":
                color = BadgeColor.ERROR;
                break;
            case "In Progress":
            case "Card Issued":
                color = BadgeColor.CONTRAST;
                break;
            default:
                color = BadgeColor.NORMAL;
        }
        
        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private ReplacementMobileTemplate getMobileTemplate(ReplacementRequest request) {
        return new ReplacementMobileTemplate(request);
    }

    private void viewDetails(ReplacementRequest request) {
        // Navigate to request details view
        // UI.getCurrent().navigate(ReplacementRequestDetails.class, request.getRequestId());
        // For demo purposes, we'll just show a notification
        UIUtils.showNotification("Viewing details for replacement request: " + request.getRequestId());
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
        List<Grid.Column<ReplacementRequest>> columns = grid.getColumns();

        // "Mobile" column
        columns.get(0).setVisible(mobile);

        // "Desktop" columns
        for (int i = 1; i < columns.size(); i++) {
            columns.get(i).setVisible(!mobile);
        }
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply request ID filter
        if (requestIdFilter.getValue() != null && !requestIdFilter.getValue().isEmpty()) {
            String requestIdValue = requestIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(request -> 
                request.getRequestId() != null && 
                request.getRequestId().toLowerCase().contains(requestIdValue));
        }

        // Apply card number filter
        if (cardNumberFilter.getValue() != null && !cardNumberFilter.getValue().isEmpty()) {
            String cardNumberValue = cardNumberFilter.getValue().toLowerCase();
            dataProvider.addFilter(request -> 
                request.getCardNumber() != null && 
                request.getCardNumber().toLowerCase().contains(cardNumberValue));
        }

        // Apply card holder name filter
        if (cardHolderNameFilter.getValue() != null && !cardHolderNameFilter.getValue().isEmpty()) {
            String cardHolderNameValue = cardHolderNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(request -> 
                request.getCardHolderName() != null && 
                request.getCardHolderName().toLowerCase().contains(cardHolderNameValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getStatus() != null && 
                request.getStatus().equals(statusValue));
        }

        // Apply reason filter
        if (reasonFilter.getValue() != null) {
            String reasonValue = reasonFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getReason() != null && 
                request.getReason().equals(reasonValue));
        }

        // Apply request date from filter
        if (requestDateFromFilter.getValue() != null) {
            LocalDate fromDate = requestDateFromFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getRequestDate() != null && 
                !request.getRequestDate().isBefore(fromDate));
        }

        // Apply request date to filter
        if (requestDateToFilter.getValue() != null) {
            LocalDate toDate = requestDateToFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getRequestDate() != null && 
                !request.getRequestDate().isAfter(toDate));
        }

        // Apply completion date from filter
        if (completionDateFromFilter.getValue() != null) {
            LocalDate fromDate = completionDateFromFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getCompletionDate() != null && 
                !request.getCompletionDate().isBefore(fromDate));
        }

        // Apply completion date to filter
        if (completionDateToFilter.getValue() != null) {
            LocalDate toDate = completionDateToFilter.getValue();
            dataProvider.addFilter(request -> 
                request.getCompletionDate() != null && 
                !request.getCompletionDate().isAfter(toDate));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        requestIdFilter.clear();
        cardNumberFilter.clear();
        cardHolderNameFilter.clear();
        statusFilter.clear();
        reasonFilter.clear();
        requestDateFromFilter.clear();
        requestDateToFilter.clear();
        completionDateFromFilter.clear();
        completionDateToFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    // Dummy data for demonstration
    private List<ReplacementRequest> getDummyReplacementRequests() {
        List<ReplacementRequest> requests = new ArrayList<>();
        
        requests.add(new ReplacementRequest("REP001", "4532123456781234", "John Smith", "In Progress", "Lost", 
                LocalDate.now().minusDays(5), null, null));
        requests.add(new ReplacementRequest("REP002", "4532123456781235", "John Smith", "Completed", "Expired", 
                LocalDate.now().minusDays(15), LocalDate.now().minusDays(10), "4532123456789876"));
        requests.add(new ReplacementRequest("REP003", "4532123456781236", "Jane Doe", "New", "Damaged", 
                LocalDate.now().minusDays(2), null, null));
        requests.add(new ReplacementRequest("REP004", "4532123456781237", "Jane Doe", "Rejected", "Other", 
                LocalDate.now().minusDays(20), LocalDate.now().minusDays(18), null));
        requests.add(new ReplacementRequest("REP005", "4532123456781238", "Robert Johnson", "Card Issued", "Stolen", 
                LocalDate.now().minusDays(8), LocalDate.now().minusDays(3), "4532123456789877"));
        requests.add(new ReplacementRequest("REP006", "4532123456781239", "Sarah Williams", "In Progress", "Compromised", 
                LocalDate.now().minusDays(4), null, null));
        requests.add(new ReplacementRequest("REP007", "4532123456781240", "Michael Brown", "New", "Name Change", 
                LocalDate.now().minusDays(1), null, null));
        requests.add(new ReplacementRequest("REP008", "4532123456781241", "Emily Davis", "Completed", "Lost", 
                LocalDate.now().minusDays(25), LocalDate.now().minusDays(20), "4532123456789878"));
        requests.add(new ReplacementRequest("REP009", "4532123456781242", "David Miller", "Card Issued", "Damaged", 
                LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), "4532123456789879"));
        requests.add(new ReplacementRequest("REP010", "4532123456781243", "Jennifer Wilson", "Rejected", "Other", 
                LocalDate.now().minusDays(18), LocalDate.now().minusDays(15), null));
        
        return requests;
    }

    /**
     * A layout for displaying ReplacementRequest info in a mobile friendly format.
     */
    private class ReplacementMobileTemplate extends FlexBoxLayout {

        private ReplacementRequest request;

        public ReplacementMobileTemplate(ReplacementRequest request) {
            this.request = request;

            UIUtils.setLineHeight(LineHeight.M, this);
            UIUtils.setPointerEvents(PointerEvents.NONE, this);

            setPadding(Vertical.S);
            setSpacing(Right.L);

            Span requestId = new Span(request.getRequestId());
            UIUtils.setFontSize(FontSize.S, requestId);
            UIUtils.setTextColor(TextColor.SECONDARY, requestId);

            Span cardInfo = new Span(request.getCardNumber() + " - " + request.getCardHolderName());
            UIUtils.setFontSize(FontSize.M, cardInfo);
            UIUtils.setTextColor(TextColor.BODY, cardInfo);

            Span reason = new Span("Reason: " + request.getReason());
            UIUtils.setFontSize(FontSize.S, reason);
            UIUtils.setTextColor(TextColor.SECONDARY, reason);

            Component statusBadge = createStatusBadge(request);

            Span dates = new Span("Requested: " + request.getRequestDate().format(java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            if (request.getCompletionDate() != null) {
                dates.setText(dates.getText() + " | Completed: " + 
                        request.getCompletionDate().format(java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            }
            UIUtils.setFontSize(FontSize.XS, dates);
            UIUtils.setTextColor(TextColor.TERTIARY, dates);

            FlexBoxLayout column = new FlexBoxLayout(requestId, cardInfo, reason, statusBadge, dates);
            column.setFlexDirection(FlexDirection.COLUMN);
            column.setOverflow(Overflow.HIDDEN);
            column.setSpacing(Vertical.XS);

            add(column);
            setFlexGrow(1, column);
        }
    }

    /**
     * Class representing a card replacement request.
     */
    public static class ReplacementRequest {
        private String requestId;
        private String cardNumber;
        private String cardHolderName;
        private String status;
        private String reason;
        private LocalDate requestDate;
        private LocalDate completionDate;
        private String newCardNumber;
        private String notes;

        public ReplacementRequest(String requestId, String cardNumber, String cardHolderName, String status, 
                                 String reason, LocalDate requestDate, LocalDate completionDate, String newCardNumber) {
            this.requestId = requestId;
            this.cardNumber = cardNumber;
            this.cardHolderName = cardHolderName;
            this.status = status;
            this.reason = reason;
            this.requestDate = requestDate;
            this.completionDate = completionDate;
            this.newCardNumber = newCardNumber;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardHolderName() {
            return cardHolderName;
        }

        public void setCardHolderName(String cardHolderName) {
            this.cardHolderName = cardHolderName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public LocalDate getRequestDate() {
            return requestDate;
        }

        public void setRequestDate(LocalDate requestDate) {
            this.requestDate = requestDate;
        }

        public LocalDate getCompletionDate() {
            return completionDate;
        }

        public void setCompletionDate(LocalDate completionDate) {
            this.completionDate = completionDate;
        }

        public String getNewCardNumber() {
            return newCardNumber;
        }

        public void setNewCardNumber(String newCardNumber) {
            this.newCardNumber = newCardNumber;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }
}