package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.accountoperations.StandingOrderDTO;
import com.vaadin.starter.business.backend.service.StandingOrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of the StandingOrderService interface.
 * Provides dummy data for demonstration purposes.
 */
@Service
public class StandingOrderServiceImpl implements StandingOrderService {

    private final Map<String, StandingOrderDTO> standingOrders = new HashMap<>();

    public StandingOrderServiceImpl() {
        // Initialize with dummy data
        initDummyData();
    }

    @Override
    public Collection<StandingOrderDTO> getStandingOrders() {
        return standingOrders.values();
    }

    @Override
    public StandingOrderDTO getStandingOrderById(String orderId) {
        return standingOrders.get(orderId);
    }

    @Override
    public Collection<StandingOrderDTO> getStandingOrdersByAccountNumber(String accountNumber) {
        return standingOrders.values().stream()
                .filter(order -> order.getAccountNumber().equals(accountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public StandingOrderDTO saveStandingOrder(StandingOrderDTO standingOrder) {
        standingOrders.put(standingOrder.getOrderId(), standingOrder);
        return standingOrder;
    }

    @Override
    public StandingOrderDTO suspendStandingOrder(String orderId) {
        StandingOrderDTO order = standingOrders.get(orderId);
        if (order != null && order.getStatus().equals(StandingOrderDTO.Status.ACTIVE.getName())) {
            order.setStatus(StandingOrderDTO.Status.SUSPENDED.getName());
            standingOrders.put(orderId, order);
        }
        return order;
    }

    @Override
    public StandingOrderDTO activateStandingOrder(String orderId) {
        StandingOrderDTO order = standingOrders.get(orderId);
        if (order != null && order.getStatus().equals(StandingOrderDTO.Status.SUSPENDED.getName())) {
            order.setStatus(StandingOrderDTO.Status.ACTIVE.getName());
            standingOrders.put(orderId, order);
        }
        return order;
    }

    @Override
    public StandingOrderDTO cancelStandingOrder(String orderId) {
        StandingOrderDTO order = standingOrders.get(orderId);
        if (order != null && !order.getStatus().equals(StandingOrderDTO.Status.CANCELLED.getName())) {
            order.setStatus(StandingOrderDTO.Status.CANCELLED.getName());
            standingOrders.put(orderId, order);
        }
        return order;
    }

    private void initDummyData() {
        List<StandingOrderDTO> dummyOrders = new ArrayList<>();
        
        dummyOrders.add(new StandingOrderDTO("SO001", "ACC001", "John Smith", "BENACC001", 500.0, "Monthly", LocalDate.now().plusDays(5), "Active"));
        dummyOrders.add(new StandingOrderDTO("SO002", "ACC002", "Utility Company", "BENACC002", 150.0, "Monthly", LocalDate.now().plusDays(10), "Active"));
        dummyOrders.add(new StandingOrderDTO("SO003", "ACC003", "Insurance Ltd", "BENACC003", 75.0, "Monthly", LocalDate.now().plusDays(15), "Active"));
        dummyOrders.add(new StandingOrderDTO("SO004", "ACC004", "Mortgage Bank", "BENACC004", 1200.0, "Monthly", LocalDate.now().plusDays(20), "Active"));
        dummyOrders.add(new StandingOrderDTO("SO005", "ACC005", "Charity Foundation", "BENACC005", 50.0, "Monthly", LocalDate.now().plusDays(25), "Active"));
        dummyOrders.add(new StandingOrderDTO("SO006", "ACC006", "Gym Membership", "BENACC006", 45.0, "Monthly", LocalDate.now().plusDays(7), "Suspended"));
        dummyOrders.add(new StandingOrderDTO("SO007", "ACC007", "Savings Account", "BENACC007", 200.0, "Monthly", LocalDate.now().plusDays(12), "Active"));
        dummyOrders.add(new StandingOrderDTO("SO008", "ACC008", "Investment Fund", "BENACC008", 300.0, "Monthly", LocalDate.now().plusDays(18), "Active"));
        dummyOrders.add(new StandingOrderDTO("SO009", "ACC009", "Child Support", "BENACC009", 350.0, "Monthly", LocalDate.now().plusDays(22), "Active"));
        dummyOrders.add(new StandingOrderDTO("SO010", "ACC010", "Old Subscription", "BENACC010", 15.0, "Monthly", LocalDate.now().plusDays(30), "Cancelled"));

        for (StandingOrderDTO order : dummyOrders) {
            standingOrders.put(order.getOrderId(), order);
        }
    }
}