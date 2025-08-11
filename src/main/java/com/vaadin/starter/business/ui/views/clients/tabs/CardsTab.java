package com.vaadin.starter.business.ui.views.clients.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.clients.ClientManagement.Client;
import com.vaadin.starter.business.ui.views.clients.tabs.models.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Tab for displaying client cards information.
 */
public class CardsTab extends AbstractTab {

    /**
     * Constructor for CardsTab.
     *
     * @param client The client whose cards are displayed
     */
    public CardsTab(Client client) {
        super(client);
    }

    @Override
    protected void initContent() {
        // Add summary cards
        HorizontalLayout summaryCards = new HorizontalLayout();
        summaryCards.setWidthFull();
        summaryCards.setSpacing(true);

        summaryCards.add(createSummaryCard("Total Cards", "2", VaadinIcon.CREDIT_CARD));
        summaryCards.add(createSummaryCard("Available Credit", "$15,000.00", VaadinIcon.DOLLAR));
        summaryCards.add(createSummaryCard("Current Balance", "$2,345.67", VaadinIcon.CHART));

        add(summaryCards);

        // Add cards grid
        Grid<Card> cardsGrid = createCardsGrid();
        add(cardsGrid);
        expand(cardsGrid);

        // Add action buttons
        HorizontalLayout actionButtons = new HorizontalLayout();
        Button newCardButton = UIUtils.createPrimaryButton("New Card");
        Button reportLostButton = UIUtils.createTertiaryButton("Report Lost/Stolen");
        Button statementsButton = UIUtils.createTertiaryButton("Statements");
        actionButtons.add(newCardButton, reportLostButton, statementsButton);
        actionButtons.setSpacing(true);
        add(actionButtons);
    }

    @Override
    public String getTabName() {
        return "Cards";
    }

    /**
     * Creates the cards grid with all columns and data.
     *
     * @return The configured grid component
     */
    private Grid<Card> createCardsGrid() {
        Grid<Card> grid = new Grid<>();
        grid.setItems(generateMockCards());
        grid.setSizeFull();
        grid.setMinHeight("400px");

        grid.addColumn(Card::getCardNumber)
                .setHeader("Card Number")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Card::getCardType)
                .setHeader("Card Type")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Card::getLinkedAccount)
                .setHeader("Linked Account")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Card::getCardholderName)
                .setHeader("Cardholder Name")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(Card::getExpiryDate)
                .setHeader("Expiry Date")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Card::getCreditLimit)
                .setHeader("Credit Limit")
                .setSortable(true)
                .setWidth("120px");
        grid.addColumn(Card::getAvailableCredit)
                .setHeader("Available Credit")
                .setSortable(true)
                .setWidth("150px");
        grid.addColumn(new ComponentRenderer<>(card -> createStatusBadge(card.getStatus())))
                .setHeader("Status")
                .setSortable(true)
                .setComparator(Card::getStatus)
                .setWidth("120px");

        // Add item click listener to show card details dialog
        grid.addItemClickListener(event -> {
            Card card = event.getItem();
            showCardDetails(card);
        });

        // Add style to indicate rows are clickable
        grid.addClassName("clickable-grid");
        grid.getElement().getStyle().set("cursor", "pointer");

        return grid;
    }

    /**
     * Shows a dialog with detailed information about a card.
     *
     * @param card The card to show details for
     */
    private void showCardDetails(Card card) {
        // Create dialog for card details
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");

        // Create dialog title
        H3 title = new H3("Card Details: " + card.getCardNumber());

        // Create form layout for card details
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Create fields for card details
        TextField cardNumberField = new TextField("Card Number");
        cardNumberField.setValue(card.getCardNumber());
        cardNumberField.setReadOnly(true);

        TextField cardTypeField = new TextField("Card Type");
        cardTypeField.setValue(card.getCardType());
        cardTypeField.setReadOnly(true);

        TextField linkedAccountField = new TextField("Linked Account");
        linkedAccountField.setValue(card.getLinkedAccount());
        linkedAccountField.setReadOnly(true);

        TextField cardholderNameField = new TextField("Cardholder Name");
        cardholderNameField.setValue(card.getCardholderName());
        cardholderNameField.setReadOnly(true);

        TextField expiryDateField = new TextField("Expiry Date");
        expiryDateField.setValue(card.getExpiryDate());
        expiryDateField.setReadOnly(true);

        TextField creditLimitField = new TextField("Credit Limit");
        creditLimitField.setValue(card.getCreditLimit());
        creditLimitField.setReadOnly(true);

        TextField availableCreditField = new TextField("Available Credit");
        availableCreditField.setValue(card.getAvailableCredit());
        availableCreditField.setReadOnly(true);

        TextField statusField = new TextField("Status");
        statusField.setValue(card.getStatus());
        statusField.setReadOnly(true);

        // Add fields to form
        formLayout.add(cardNumberField, cardTypeField, linkedAccountField, cardholderNameField, 
                      expiryDateField, creditLimitField, availableCreditField, statusField);

        // Add close button
        Button closeButton = UIUtils.createTertiaryButton("Close");
        closeButton.addClickListener(e -> dialog.close());

        // Create button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(closeButton);
        buttonLayout.setSpacing(true);
        buttonLayout.getStyle().set("margin-top", "1rem");

        // Create layout for dialog content
        HorizontalLayout dialogLayout = new HorizontalLayout(title, formLayout, buttonLayout);
        dialogLayout.setPadding(true);
        dialogLayout.setSpacing(true);
        dialogLayout.setWidthFull();

        dialog.add(dialogLayout);
        dialog.open();
    }

    /**
     * Generates mock card data for testing.
     *
     * @return A list of mock cards
     */
    private List<Card> generateMockCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card("4111 **** **** 1234", "Credit Card", "1001234567", "John Smith", "12/25", "$10,000.00", "$7,654.33", "Active"));
        cards.add(new Card("5500 **** **** 5678", "Debit Card", "1001234568", "John Smith", "09/24", "N/A", "N/A", "Active"));
        return cards;
    }
}