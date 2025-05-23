package com.vaadin.starter.business.backend.dto.cashmanagement;

import java.util.List;

/**
 * Represents the allocation of liquidity across different asset categories.
 */
public class LiquidityAllocationDTO {
    private List<String> categories;
    private List<Double> amounts;

    public LiquidityAllocationDTO() {
    }

    public LiquidityAllocationDTO(List<String> categories, List<Double> amounts) {
        this.categories = categories;
        this.amounts = amounts;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Double> getAmounts() {
        return amounts;
    }

    public void setAmounts(List<Double> amounts) {
        this.amounts = amounts;
    }
}