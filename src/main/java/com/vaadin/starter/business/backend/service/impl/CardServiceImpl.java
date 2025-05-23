package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.Card;
import com.vaadin.starter.business.backend.service.CardService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the CardService interface.
 * Provides dummy data for demonstration purposes.
 */
@Service
public class CardServiceImpl implements CardService {

    private final Map<String, Card> cards = new HashMap<>();

    public CardServiceImpl() {
        // Initialize with dummy data
        initDummyData();
    }

    @Override
    public Collection<Card> getCards() {
        return cards.values();
    }

    @Override
    public Card getCardByNumber(String cardNumber) {
        return cards.get(cardNumber);
    }

    @Override
    public Card saveCard(Card card) {
        cards.put(card.getCardNumber(), card);
        return card;
    }

    @Override
    public Card activateCard(String cardNumber) {
        Card card = cards.get(cardNumber);
        if (card != null && card.getStatus().equals(Card.Status.PENDING_ACTIVATION.getName())) {
            card.setStatus(Card.Status.ACTIVE.getName());
            cards.put(cardNumber, card);
        }
        return card;
    }

    @Override
    public Card blockCard(String cardNumber) {
        Card card = cards.get(cardNumber);
        if (card != null) {
            card.setStatus(Card.Status.BLOCKED.getName());
            cards.put(cardNumber, card);
        }
        return card;
    }

    @Override
    public Card reportCardAsLost(String cardNumber) {
        Card card = cards.get(cardNumber);
        if (card != null) {
            card.setStatus(Card.Status.REPORTED_LOST.getName());
            cards.put(cardNumber, card);
        }
        return card;
    }

    @Override
    public Card reportCardAsStolen(String cardNumber) {
        Card card = cards.get(cardNumber);
        if (card != null) {
            card.setStatus(Card.Status.REPORTED_STOLEN.getName());
            cards.put(cardNumber, card);
        }
        return card;
    }

    @Override
    public boolean requestCardReplacement(String cardNumber) {
        Card card = cards.get(cardNumber);
        if (card != null) {
            // In a real implementation, this would create a replacement request
            // For demo purposes, we'll just return true
            return true;
        }
        return false;
    }

    private void initDummyData() {
        List<Card> dummyCards = List.of(
            new Card("4532123456781234", "John Smith", "Debit", "Active", 
                    LocalDate.now().plusYears(3), LocalDate.now().minusYears(1), 
                    "ACC001", "CUST001", true, null),
            new Card("4532123456781235", "John Smith", "Credit", "Active", 
                    LocalDate.now().plusYears(2), LocalDate.now().minusYears(2), 
                    "ACC003", "CUST001", true, 5000.0),
            new Card("4532123456781236", "Jane Doe", "Debit", "Active", 
                    LocalDate.now().plusYears(4), LocalDate.now().minusMonths(6), 
                    "ACC002", "CUST002", true, null),
            new Card("4532123456781237", "Jane Doe", "Credit", "Blocked", 
                    LocalDate.now().plusYears(3), LocalDate.now().minusYears(1), 
                    "ACC004", "CUST002", true, 10000.0),
            new Card("4532123456781238", "Robert Johnson", "Prepaid", "Active", 
                    LocalDate.now().plusYears(2), LocalDate.now().minusMonths(3), 
                    null, "CUST003", false, 500.0),
            new Card("4532123456781239", "Sarah Williams", "Credit", "Active", 
                    LocalDate.now().plusYears(3), LocalDate.now().minusYears(1), 
                    "ACC005", "CUST004", true, 15000.0),
            new Card("4532123456781240", "Michael Brown", "Debit", "Pending Activation", 
                    LocalDate.now().plusYears(4), LocalDate.now(), 
                    "ACC006", "CUST005", true, null),
            new Card("4532123456781241", "Emily Davis", "Credit", "Reported Lost", 
                    LocalDate.now().plusYears(2), LocalDate.now().minusYears(2), 
                    "ACC007", "CUST006", true, 7500.0),
            new Card("4532123456781242", "David Miller", "Business", "Active", 
                    LocalDate.now().plusYears(3), LocalDate.now().minusMonths(8), 
                    "ACC008", "CUST007", true, 25000.0),
            new Card("4532123456781243", "Jennifer Wilson", "Debit", "Expired", 
                    LocalDate.now().minusMonths(2), LocalDate.now().minusYears(3), 
                    "ACC009", "CUST008", false, null),
            new Card("4532123456781244", "James Taylor", "Virtual", "Active", 
                    LocalDate.now().plusYears(1), LocalDate.now().minusMonths(1), 
                    "ACC010", "CUST009", false, 2000.0),
            new Card("4532123456781245", "Patricia Anderson", "Credit", "Reported Stolen", 
                    LocalDate.now().plusYears(3), LocalDate.now().minusYears(1), 
                    "ACC011", "CUST010", true, 12000.0),
            new Card("4532123456781246", "Robert Thomas", "Debit", "Active", 
                    LocalDate.now().plusYears(4), LocalDate.now().minusMonths(5), 
                    "ACC012", "CUST011", true, null),
            new Card("4532123456781247", "Linda Jackson", "Credit", "Inactive", 
                    LocalDate.now().plusYears(2), LocalDate.now().minusYears(2), 
                    "ACC013", "CUST012", true, 8000.0),
            new Card("4532123456781248", "William White", "Prepaid", "Active", 
                    LocalDate.now().plusYears(1), LocalDate.now().minusMonths(2), 
                    null, "CUST013", false, 300.0),
            new Card("4532123456781249", "Elizabeth Harris", "Credit", "Pending Activation", 
                    LocalDate.now().plusYears(3), LocalDate.now(), 
                    "ACC014", "CUST014", true, 20000.0),
            new Card("4532123456781250", "Richard Clark", "Business", "Active", 
                    LocalDate.now().plusYears(4), LocalDate.now().minusYears(1), 
                    "ACC015", "CUST015", true, 50000.0),
            new Card("4532123456781251", "Susan Lewis", "Debit", "Blocked", 
                    LocalDate.now().plusYears(2), LocalDate.now().minusMonths(9), 
                    "ACC016", "CUST016", true, null),
            new Card("4532123456781252", "Joseph Young", "Credit", "Active", 
                    LocalDate.now().plusYears(3), LocalDate.now().minusYears(1), 
                    "ACC017", "CUST017", true, 15000.0),
            new Card("4532123456781253", "Margaret Walker", "Virtual", "Active", 
                    LocalDate.now().plusYears(1), LocalDate.now().minusMonths(1), 
                    "ACC018", "CUST018", false, 5000.0)
        );

        for (Card card : dummyCards) {
            cards.put(card.getCardNumber(), card);
        }
    }
}