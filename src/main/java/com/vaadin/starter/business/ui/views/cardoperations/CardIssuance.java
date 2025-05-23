package com.vaadin.starter.business.ui.views.cardoperations;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
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
import com.vaadin.starter.business.backend.Card;
import com.vaadin.starter.business.backend.service.CardService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Right;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.layout.size.Vertical;
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
import java.util.Collection;
import java.util.List;

@PageTitle(NavigationConstants.CARD_ISSUANCE)
@Route(value = "card-operations/issuance", layout = MainLayout.class)
public class CardIssuance extends ViewFrame {

    public static final int MOBILE_BREAKPOINT = 480;
    private Grid<Card> grid;
    private Registration resizeListener;
    private ListDataProvider<Card> dataProvider;

    // Filter form fields
    private TextField cardNumberFilter;
    private TextField cardHolderNameFilter;
    private ComboBox<String> cardTypeFilter;
    private ComboBox<String> statusFilter;
    private TextField linkedAccountFilter;
    private TextField customerIdFilter;
    private DatePicker expirationDateFromFilter;
    private DatePicker expirationDateToFilter;
    private DatePicker issueDateFromFilter;
    private DatePicker issueDateToFilter;

    private final CardService cardService;

    @Autowired
    public CardIssuance(CardService cardService) {
        this.cardService = cardService;
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
        cardNumberFilter = new TextField();
        cardNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        cardNumberFilter.setClearButtonVisible(true);

        cardHolderNameFilter = new TextField();
        cardHolderNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        cardHolderNameFilter.setClearButtonVisible(true);

        cardTypeFilter = new ComboBox<>();
        cardTypeFilter.setItems("Debit", "Credit", "Prepaid", "Virtual", "Business");
        cardTypeFilter.setClearButtonVisible(true);

        statusFilter = new ComboBox<>();
        statusFilter.setItems("Active", "Inactive", "Blocked", "Expired", "Pending Activation", "Reported Lost", "Reported Stolen");
        statusFilter.setClearButtonVisible(true);

        linkedAccountFilter = new TextField();
        linkedAccountFilter.setValueChangeMode(ValueChangeMode.EAGER);
        linkedAccountFilter.setClearButtonVisible(true);

        customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        expirationDateFromFilter = new DatePicker();
        expirationDateFromFilter.setClearButtonVisible(true);

        expirationDateToFilter = new DatePicker();
        expirationDateToFilter.setClearButtonVisible(true);

        issueDateFromFilter = new DatePicker();
        issueDateFromFilter.setClearButtonVisible(true);

        issueDateToFilter = new DatePicker();
        issueDateToFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> applyFilters());

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> clearFilters());

        Button newCardButton = UIUtils.createSuccessButton("New Card");
        newCardButton.addClickListener(e -> {
            // Logic to create a new card would go here
            // For demo purposes, we'll just show a notification
            UIUtils.showNotification("New card creation form would open here");
        });

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton, newCardButton);
        buttonLayout.setSpacing(true);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(cardNumberFilter, "Card Number");
        formLayout.addFormItem(cardHolderNameFilter, "Card Holder Name");
        formLayout.addFormItem(cardTypeFilter, "Card Type");
        formLayout.addFormItem(statusFilter, "Status");
        formLayout.addFormItem(linkedAccountFilter, "Linked Account");
        formLayout.addFormItem(customerIdFilter, "Customer ID");
        formLayout.addFormItem(expirationDateFromFilter, "Expiration From");
        formLayout.addFormItem(expirationDateToFilter, "Expiration To");
        formLayout.addFormItem(issueDateFromFilter, "Issue Date From");
        formLayout.addFormItem(issueDateToFilter, "Issue Date To");

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
        Collection<Card> cards = cardService.getCards();
        dataProvider = new ListDataProvider<>(cards);
        grid.setDataProvider(dataProvider);

        grid.setId("cards");
        grid.setSizeFull();

        // "Mobile" column
        grid.addColumn(new ComponentRenderer<>(this::getMobileTemplate))
                .setVisible(false);

        // "Desktop" columns
        grid.addColumn(Card::getCardNumber)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("Card Number")
                .setSortable(true);
        grid.addColumn(Card::getCardHolderName)
                .setHeader("Card Holder Name")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(Card::getCardType)
                .setHeader("Card Type")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(this::createStatusBadge))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Card::getStatus)
                .setWidth("150px");
        grid.addColumn(Card::getLinkedAccountNumber)
                .setHeader("Linked Account")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new LocalDateRenderer<>(Card::getExpirationDate, "MMM yyyy"))
                .setAutoWidth(true)
                .setComparator(Card::getExpirationDate)
                .setFlexGrow(0)
                .setHeader("Expiration Date");
        grid.addColumn(new LocalDateRenderer<>(Card::getIssueDate, "MMM dd, yyyy"))
                .setAutoWidth(true)
                .setComparator(Card::getIssueDate)
                .setFlexGrow(0)
                .setHeader("Issue Date");
        grid.addColumn(Card::getCustomerId)
                .setHeader("Customer ID")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(new ComponentRenderer<>(this::createContactlessIndicator))
                .setHeader("Contactless")
                .setWidth("120px");

        // Add action buttons
        grid.addComponentColumn(card -> {
            HorizontalLayout actions = new HorizontalLayout();

            Button viewButton = UIUtils.createSmallButton("View");
            viewButton.addClickListener(e -> {
                viewDetails(card);
            });

            Button activateButton = null;
            if (card.getStatus().equals(Card.Status.PENDING_ACTIVATION.getName())) {
                activateButton = UIUtils.createSmallButton("Activate");
                activateButton.getElement().getThemeList().add("success");
                activateButton.addClickListener(e -> {
                    cardService.activateCard(card.getCardNumber());
                    // Refresh the grid
                    grid.getDataProvider().refreshItem(card);
                });
            }

            actions.add(viewButton);
            if (activateButton != null) {
                actions.add(activateButton);
            }
            
            return actions;
        }).setHeader("Actions").setWidth("150px");

        return grid;
    }

    private Component createStatusBadge(Card card) {
        String status = card.getStatus();
        BadgeColor color;
        
        switch (status) {
            case "Active":
                color = BadgeColor.SUCCESS;
                break;
            case "Blocked":
            case "Reported Lost":
            case "Reported Stolen":
                color = BadgeColor.ERROR;
                break;
            case "Pending Activation":
                color = BadgeColor.CONTRAST;
                break;
            case "Expired":
                color = BadgeColor.NORMAL;
                break;
            default:
                color = BadgeColor.NORMAL;
        }
        
        return new Badge(status, color, BadgeSize.S, BadgeShape.PILL);
    }

    private Component createContactlessIndicator(Card card) {
        Checkbox contactless = new Checkbox();
        contactless.setValue(card.isContactless());
        contactless.setReadOnly(true);
        return contactless;
    }

    private CardMobileTemplate getMobileTemplate(Card card) {
        return new CardMobileTemplate(card);
    }

    private void viewDetails(Card card) {
        // Navigate to card details view
        // UI.getCurrent().navigate(CardDetails.class, card.getCardNumber());
        // For demo purposes, we'll just show a notification
        UIUtils.showNotification("Viewing details for card: " + card.getCardNumber());
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
        List<Grid.Column<Card>> columns = grid.getColumns();

        // "Mobile" column
        columns.get(0).setVisible(mobile);

        // "Desktop" columns
        for (int i = 1; i < columns.size(); i++) {
            columns.get(i).setVisible(!mobile);
        }
    }

    private void applyFilters() {
        dataProvider.clearFilters();

        // Apply card number filter
        if (cardNumberFilter.getValue() != null && !cardNumberFilter.getValue().isEmpty()) {
            String cardNumberValue = cardNumberFilter.getValue().toLowerCase();
            dataProvider.addFilter(card -> 
                card.getCardNumber() != null && 
                card.getCardNumber().toLowerCase().contains(cardNumberValue));
        }

        // Apply card holder name filter
        if (cardHolderNameFilter.getValue() != null && !cardHolderNameFilter.getValue().isEmpty()) {
            String cardHolderNameValue = cardHolderNameFilter.getValue().toLowerCase();
            dataProvider.addFilter(card -> 
                card.getCardHolderName() != null && 
                card.getCardHolderName().toLowerCase().contains(cardHolderNameValue));
        }

        // Apply card type filter
        if (cardTypeFilter.getValue() != null) {
            String cardTypeValue = cardTypeFilter.getValue();
            dataProvider.addFilter(card -> 
                card.getCardType() != null && 
                card.getCardType().equals(cardTypeValue));
        }

        // Apply status filter
        if (statusFilter.getValue() != null) {
            String statusValue = statusFilter.getValue();
            dataProvider.addFilter(card -> 
                card.getStatus() != null && 
                card.getStatus().equals(statusValue));
        }

        // Apply linked account filter
        if (linkedAccountFilter.getValue() != null && !linkedAccountFilter.getValue().isEmpty()) {
            String linkedAccountValue = linkedAccountFilter.getValue().toLowerCase();
            dataProvider.addFilter(card -> 
                card.getLinkedAccountNumber() != null && 
                card.getLinkedAccountNumber().toLowerCase().contains(linkedAccountValue));
        }

        // Apply customer ID filter
        if (customerIdFilter.getValue() != null && !customerIdFilter.getValue().isEmpty()) {
            String customerIdValue = customerIdFilter.getValue().toLowerCase();
            dataProvider.addFilter(card -> 
                card.getCustomerId() != null && 
                card.getCustomerId().toLowerCase().contains(customerIdValue));
        }

        // Apply expiration date from filter
        if (expirationDateFromFilter.getValue() != null) {
            LocalDate fromDate = expirationDateFromFilter.getValue();
            dataProvider.addFilter(card -> 
                card.getExpirationDate() != null && 
                !card.getExpirationDate().isBefore(fromDate));
        }

        // Apply expiration date to filter
        if (expirationDateToFilter.getValue() != null) {
            LocalDate toDate = expirationDateToFilter.getValue();
            dataProvider.addFilter(card -> 
                card.getExpirationDate() != null && 
                !card.getExpirationDate().isAfter(toDate));
        }

        // Apply issue date from filter
        if (issueDateFromFilter.getValue() != null) {
            LocalDate fromDate = issueDateFromFilter.getValue();
            dataProvider.addFilter(card -> 
                card.getIssueDate() != null && 
                !card.getIssueDate().isBefore(fromDate));
        }

        // Apply issue date to filter
        if (issueDateToFilter.getValue() != null) {
            LocalDate toDate = issueDateToFilter.getValue();
            dataProvider.addFilter(card -> 
                card.getIssueDate() != null && 
                !card.getIssueDate().isAfter(toDate));
        }
    }

    private void clearFilters() {
        // Clear all filter fields
        cardNumberFilter.clear();
        cardHolderNameFilter.clear();
        cardTypeFilter.clear();
        statusFilter.clear();
        linkedAccountFilter.clear();
        customerIdFilter.clear();
        expirationDateFromFilter.clear();
        expirationDateToFilter.clear();
        issueDateFromFilter.clear();
        issueDateToFilter.clear();

        // Clear all filters from data provider
        dataProvider.clearFilters();
    }

    /**
     * A layout for displaying Card info in a mobile friendly format.
     */
    private class CardMobileTemplate extends FlexBoxLayout {

        private Card card;

        public CardMobileTemplate(Card card) {
            this.card = card;

            UIUtils.setLineHeight(LineHeight.M, this);
            UIUtils.setPointerEvents(PointerEvents.NONE, this);

            setPadding(Vertical.S);
            setSpacing(Right.L);

            Span cardNumber = new Span(card.getCardNumber());
            UIUtils.setFontSize(FontSize.S, cardNumber);
            UIUtils.setTextColor(TextColor.SECONDARY, cardNumber);

            Span cardHolderName = new Span(card.getCardHolderName());
            UIUtils.setFontSize(FontSize.M, cardHolderName);
            UIUtils.setTextColor(TextColor.BODY, cardHolderName);

            Span cardType = new Span(card.getCardType());
            UIUtils.setFontSize(FontSize.S, cardType);
            UIUtils.setTextColor(TextColor.SECONDARY, cardType);

            Component statusBadge = createStatusBadge(card);

            Span expirationDate = new Span("Exp: " + card.getExpirationDate().format(java.time.format.DateTimeFormatter.ofPattern("MM/yy")));
            UIUtils.setFontSize(FontSize.XS, expirationDate);
            UIUtils.setTextColor(TextColor.TERTIARY, expirationDate);

            FlexBoxLayout column = new FlexBoxLayout(cardNumber, cardHolderName, cardType, statusBadge, expirationDate);
            column.setFlexDirection(FlexDirection.COLUMN);
            column.setOverflow(Overflow.HIDDEN);
            column.setSpacing(Vertical.XS);

            add(column);
            setFlexGrow(1, column);
        }
    }
}