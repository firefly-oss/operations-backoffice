package com.vaadin.starter.business.backend.dto.cashmanagement;

import java.util.List;

/**
 * Represents liquidity data for different entities.
 */
public class EntityLiquidityDTO {
    private List<String> entities;
    private List<Double> availableLiquidity;
    private List<Double> requiredLiquidity;

    public EntityLiquidityDTO() {
    }

    public EntityLiquidityDTO(List<String> entities, List<Double> availableLiquidity, List<Double> requiredLiquidity) {
        this.entities = entities;
        this.availableLiquidity = availableLiquidity;
        this.requiredLiquidity = requiredLiquidity;
    }

    public List<String> getEntities() {
        return entities;
    }

    public void setEntities(List<String> entities) {
        this.entities = entities;
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