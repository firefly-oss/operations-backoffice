/*
 * Copyright 2025 Firefly Software Foundation
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