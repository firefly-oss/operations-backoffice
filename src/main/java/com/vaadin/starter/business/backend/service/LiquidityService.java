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