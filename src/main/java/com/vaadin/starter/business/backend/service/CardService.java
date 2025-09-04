/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.Card;

import java.util.Collection;

/**
 * Service interface for card operations.
 */
public interface CardService {
    
    /**
     * Get all cards.
     *
     * @return Collection of all cards
     */
    Collection<Card> getCards();
    
    /**
     * Get card by card number.
     *
     * @param cardNumber the card number
     * @return the card with the given card number
     */
    Card getCardByNumber(String cardNumber);
    
    /**
     * Save or update a card.
     *
     * @param card the card to save or update
     * @return the saved or updated card
     */
    Card saveCard(Card card);
    
    /**
     * Activate a card.
     *
     * @param cardNumber the card number to activate
     * @return the activated card
     */
    Card activateCard(String cardNumber);
    
    /**
     * Block a card.
     *
     * @param cardNumber the card number to block
     * @return the blocked card
     */
    Card blockCard(String cardNumber);
    
    /**
     * Report a card as lost.
     *
     * @param cardNumber the card number to report as lost
     * @return the reported card
     */
    Card reportCardAsLost(String cardNumber);
    
    /**
     * Report a card as stolen.
     *
     * @param cardNumber the card number to report as stolen
     * @return the reported card
     */
    Card reportCardAsStolen(String cardNumber);
    
    /**
     * Request a card replacement.
     *
     * @param cardNumber the card number to replace
     * @return the replacement request status
     */
    boolean requestCardReplacement(String cardNumber);
}