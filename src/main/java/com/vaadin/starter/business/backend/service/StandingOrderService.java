package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.accountoperations.StandingOrderDTO;

import java.util.Collection;

/**
 * Service interface for standing order operations.
 */
public interface StandingOrderService {
    
    /**
     * Get all standing orders.
     *
     * @return Collection of all standing orders
     */
    Collection<StandingOrderDTO> getStandingOrders();
    
    /**
     * Get standing order by order ID.
     *
     * @param orderId the order ID
     * @return the standing order with the given order ID
     */
    StandingOrderDTO getStandingOrderById(String orderId);
    
    /**
     * Get standing orders by account number.
     *
     * @param accountNumber the account number
     * @return Collection of standing orders for the given account number
     */
    Collection<StandingOrderDTO> getStandingOrdersByAccountNumber(String accountNumber);
    
    /**
     * Save or update a standing order.
     *
     * @param standingOrder the standing order to save or update
     * @return the saved or updated standing order
     */
    StandingOrderDTO saveStandingOrder(StandingOrderDTO standingOrder);
    
    /**
     * Suspend a standing order.
     *
     * @param orderId the order ID to suspend
     * @return the suspended standing order
     */
    StandingOrderDTO suspendStandingOrder(String orderId);
    
    /**
     * Activate a standing order.
     *
     * @param orderId the order ID to activate
     * @return the activated standing order
     */
    StandingOrderDTO activateStandingOrder(String orderId);
    
    /**
     * Cancel a standing order.
     *
     * @param orderId the order ID to cancel
     * @return the cancelled standing order
     */
    StandingOrderDTO cancelStandingOrder(String orderId);
}