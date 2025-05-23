package com.vaadin.starter.business.backend.dto.cashmanagement;

import java.time.Month;
import java.util.List;

/**
 * Represents the trend of liquidity metrics over time.
 */
public class LiquidityTrendDTO {
    private List<Month> months;
    private List<Double> availableLiquidity;
    private List<Double> requiredLiquidity;

    public LiquidityTrendDTO() {
    }

    public LiquidityTrendDTO(List<Month> months, List<Double> availableLiquidity, List<Double> requiredLiquidity) {
        this.months = months;
        this.availableLiquidity = availableLiquidity;
        this.requiredLiquidity = requiredLiquidity;
    }

    public List<Month> getMonths() {
        return months;
    }

    public void setMonths(List<Month> months) {
        this.months = months;
    }

    public List<Double> getAvailableLiquidity() {
        return availableLiquidity;
    }

    public void setAvailableLiquidity(List<Double> availableLiquidity) {
        this.availableLiquidity = availableLiquidity;
    }

    public List<Double> getRequiredLiquidity() {
        return requiredLiquidity;
    }

    public void setRequiredLiquidity(List<Double> requiredLiquidity) {
        this.requiredLiquidity = requiredLiquidity;
    }
}