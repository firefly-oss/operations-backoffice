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

/**
 * Represents a summary of liquidity metrics.
 */
public class LiquiditySummaryDTO {
    private double availableLiquidity;
    private double requiredLiquidity;
    private double liquidityBuffer;
    private double liquidityCoverageRatio;
    private double availableLiquidityChange;
    private double requiredLiquidityChange;
    private double liquidityBufferChange;
    private double liquidityCoverageRatioChange;

    public LiquiditySummaryDTO() {
    }

    public LiquiditySummaryDTO(double availableLiquidity, double requiredLiquidity,
                               double liquidityBuffer, double liquidityCoverageRatio,
                               double availableLiquidityChange, double requiredLiquidityChange,
                               double liquidityBufferChange, double liquidityCoverageRatioChange) {
        this.availableLiquidity = availableLiquidity;
        this.requiredLiquidity = requiredLiquidity;
        this.liquidityBuffer = liquidityBuffer;
        this.liquidityCoverageRatio = liquidityCoverageRatio;
        this.availableLiquidityChange = availableLiquidityChange;
        this.requiredLiquidityChange = requiredLiquidityChange;
        this.liquidityBufferChange = liquidityBufferChange;
        this.liquidityCoverageRatioChange = liquidityCoverageRatioChange;
    }

    public double getAvailableLiquidity() {
        return availableLiquidity;
    }

    public void setAvailableLiquidity(double availableLiquidity) {
        this.availableLiquidity = availableLiquidity;
    }

    public double getRequiredLiquidity() {
        return requiredLiquidity;
    }

    public void setRequiredLiquidity(double requiredLiquidity) {
        this.requiredLiquidity = requiredLiquidity;
    }

    public double getLiquidityBuffer() {
        return liquidityBuffer;
    }

    public void setLiquidityBuffer(double liquidityBuffer) {
        this.liquidityBuffer = liquidityBuffer;
    }

    public double getLiquidityCoverageRatio() {
        return liquidityCoverageRatio;
    }

    public void setLiquidityCoverageRatio(double liquidityCoverageRatio) {
        this.liquidityCoverageRatio = liquidityCoverageRatio;
    }

    public double getAvailableLiquidityChange() {
        return availableLiquidityChange;
    }

    public void setAvailableLiquidityChange(double availableLiquidityChange) {
        this.availableLiquidityChange = availableLiquidityChange;
    }

    public double getRequiredLiquidityChange() {
        return requiredLiquidityChange;
    }

    public void setRequiredLiquidityChange(double requiredLiquidityChange) {
        this.requiredLiquidityChange = requiredLiquidityChange;
    }

    public double getLiquidityBufferChange() {
        return liquidityBufferChange;
    }

    public void setLiquidityBufferChange(double liquidityBufferChange) {
        this.liquidityBufferChange = liquidityBufferChange;
    }

    public double getLiquidityCoverageRatioChange() {
        return liquidityCoverageRatioChange;
    }

    public void setLiquidityCoverageRatioChange(double liquidityCoverageRatioChange) {
        this.liquidityCoverageRatioChange = liquidityCoverageRatioChange;
    }
}