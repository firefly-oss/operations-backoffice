package com.vaadin.starter.business.ui.views.clients.tabs.models;

/**
 * Data model for a client card.
 */
public class Card {
    private String cardNumber;
    private String cardType;
    private String linkedAccount;
    private String cardholderName;
    private String expiryDate;
    private String creditLimit;
    private String availableCredit;
    private String status;

    public Card(String cardNumber, String cardType, String linkedAccount, String cardholderName, 
               String expiryDate, String creditLimit, String availableCredit, String status) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.linkedAccount = linkedAccount;
        this.cardholderName = cardholderName;
        this.expiryDate = expiryDate;
        this.creditLimit = creditLimit;
        this.availableCredit = availableCredit;
        this.status = status;
    }

    public String getCardNumber() { return cardNumber; }
    public String getCardType() { return cardType; }
    public String getLinkedAccount() { return linkedAccount; }
    public String getCardholderName() { return cardholderName; }
    public String getExpiryDate() { return expiryDate; }
    public String getCreditLimit() { return creditLimit; }
    public String getAvailableCredit() { return availableCredit; }
    public String getStatus() { return status; }
}