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