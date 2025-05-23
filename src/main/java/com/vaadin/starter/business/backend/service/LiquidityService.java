package com.vaadin.starter.business.backend.service;

import com.vaadin.starter.business.backend.dto.cashmanagement.*;

/**
 * Service interface for liquidity management operations.
 */
public interface LiquidityService {

    /**
     * Get liquidity summary metrics.
     *
     * @return Liquidity summary
     */
    LiquiditySummaryDTO getLiquiditySummary();

    /**
     * Get cash flow forecast for the next days.
     *
     * @param days Number of days to forecast
     * @return Cash flow forecast
     */
    CashFlowForecastDTO getCashFlowForecast(int days);

    /**
     * Get liquidity allocation across different asset categories.
     *
     * @return Liquidity allocation
     */
    LiquidityAllocationDTO getLiquidityAllocation();

    /**
     * Get liquidity trend over the past months.
     *
     * @param months Number of months of historical data
     * @return Liquidity trend
     */
    LiquidityTrendDTO getLiquidityTrend(int months);

    /**
     * Get liquidity data by entity.
     *
     * @return Entity liquidity data
     */
    EntityLiquidityDTO getEntityLiquidity();
}