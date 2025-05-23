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
import com.vaadin.starter.business.backend.Card;
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
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@PageTitle(NavigationConstants.CARD_ACTIVATION)
@Route(value = "card-operations/activation", layout = MainLayout.class)
public class CardActivation extends ViewFrame {

    public static final int MOBILE_BREAKPOINT = 480;
    private Grid<Card> grid;
    private Registration resizeListener;
    private ListDataProvider<Card> dataProvider;

    // Filter form fields
    private TextField cardNumberFilter;
    private TextField cardHolderNameFilter;
    private ComboBox<String> cardTypeFilter;
    private TextField linkedAccountFilter;
    private TextField customerIdFilter;
    private DatePicker issueDateFromFilter;
    private DatePicker issueDateToFilter;

    private final CardService cardService;

    @Autowired
    public CardActivation(CardService cardService) {
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
        H3 header = new H3("Card Activation");
        header.getStyle().set("margin-top", "0");

        Span description = new Span("Activate cards that are pending activation.");
        description.getStyle().set("color", "var(--lumo-secondary-text-color)");

        FlexBoxLayout headerLayout = new FlexBoxLayout(header, description);
        headerLayout.setFlexDirection(FlexDirection.COLUMN);
        headerLayout.setMargin(Bottom.M);

        return headerLayout;
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

        linkedAccountFilter = new TextField();
        linkedAccountFilter.setValueChangeMode(ValueChangeMode.EAGER);
        linkedAccountFilter.setClearButtonVisible(true);

        customerIdFilter = new TextField();
        customerIdFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerIdFilter.setClearButtonVisible(true);

        issueDateFromFilter = new DatePicker();
        issueDateFromFilter.setClearButtonVisible(true);

        issueDateToFilter = new DatePicker();
        issueDateToFilter.setClearButtonVisible(true);

        // Create buttons
        Button searchButton = UIUtils.createPrimaryButton("Search");
        searchButton.addClickListener(e -> applyFilters());

        Button clearButton = UIUtils.createTertiaryButton("Clear");
        clearButton.addClickListener(e -> clearFilters());

        Button activateAllButton = UIUtils.createSuccessButton("Activate Selected");
        activateAllButton.addClickListener(e -> {
            Set<Card> selectedCards = grid.getSelectedItems();
            if (selectedCards.isEmpty()) {
                UIUtils.showNotification("Please select at least one card to activate");
                return;
            }

            for (Card card : selectedCards) {
                if (card.getStatus().equals(Card.Status.PENDING_ACTIVATION.getName())) {
                    cardService.activateCard(card.getCardNumber());
                }
            }

            // Refresh the grid
            refreshGrid();
            UIUtils.showNotification(selectedCards.size() + " card(s) activated successfully");
        });

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(searchButton, clearButton, activateAllButton);
        buttonLayout.setSpacing(true);

        // Create form layout
        FormLayout formLayout = new FormLayout();
        formLayout.addFormItem(cardNumberFilter, "Card Number");
        formLayout.addFormItem(cardHolderNameFilter, "Card Holder Name");
        formLayout.addFormItem(cardTypeFilter, "Card Type");
        formLayout.addFormItem(linkedAccountFilter, "Linked Account");
        formLayout.addFormItem(customerIdFilter, "Customer ID");
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
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addThemeName("mobile");

        // Initialize with data from service - filter to only show pending activation cards
        Collection<Card> allCards = cardService.getCards();
        List<Card> pendingActivationCards = allCards.stream()
                .filter(card -> card.getStatus().equals(Card.Status.PENDING_ACTIVATION.getName()))
                .collect(Collectors.toList());

        dataProvider = new ListDataProvider<>(pendingActivationCards);
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

            Button activateButton = UIUtils.createSmallButton("Activate");
            activateButton.getElement().getThemeList().add("success");
            activateButton.addClickListener(e -> {
                cardService.activateCard(card.getCardNumber());
                // Refresh the grid
                refreshGrid();
            });

            actions.add(activateButton);
            return actions;
        }).setHeader("Actions").setWidth("120px");

        return grid;
    }

    private void refreshGrid() {
        // Refresh data from service - filter to only show pending activation cards
        Collection<Card> allCards = cardService.getCards();
        List<Card> pendingActivationCards = allCards.stream()
                .filter(card -> card.getStatus().equals(Card.Status.PENDING_ACTIVATION.getName()))
                .collect(Collectors.toList());

        dataProvider = new ListDataProvider<>(pendingActivationCards);
        grid.setDataProvider(dataProvider);
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
        linkedAccountFilter.clear();
        customerIdFilter.clear();
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

            Badge status = new Badge("Pending Activation", BadgeColor.CONTRAST, BadgeSize.S, BadgeShape.PILL);

            Span issueDate = new Span("Issued: " + card.getIssueDate().format(java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            UIUtils.setFontSize(FontSize.XS, issueDate);
            UIUtils.setTextColor(TextColor.TERTIARY, issueDate);

            FlexBoxLayout column = new FlexBoxLayout(cardNumber, cardHolderName, cardType, status, issueDate);
            column.setFlexDirection(FlexDirection.COLUMN);
            column.setOverflow(Overflow.HIDDEN);
            column.setSpacing(Vertical.XS);

            add(column);
            setFlexGrow(1, column);
        }
    }
}
