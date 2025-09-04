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

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a cash flow forecast for a period of days.
 */
public class CashFlowForecastDTO {
    private List<LocalDate> dates;
    private List<Double> inflows;
    private List<Double> outflows;
    private List<Double> netCashFlows;

    public CashFlowForecastDTO() {
    }

    public CashFlowForecastDTO(List<LocalDate> dates, List<Double> inflows, List<Double> outflows, List<Double> netCashFlows) {
        this.dates = dates;
        this.inflows = inflows;
        this.outflows = outflows;
        this.netCashFlows = netCashFlows;
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }

    public List<Double> getInflows() {
        return inflows;
    }

    public void setInflows(List<Double> inflows) {
        this.inflows = inflows;
    }

    public List<Double> getOutflows() {
        return outflows;
    }

    public void setOutflows(List<Double> outflows) {
        this.outflows = outflows;
    }

    public List<Double> getNetCashFlows() {
        return netCashFlows;
    }

    public void setNetCashFlows(List<Double> netCashFlows) {
        this.netCashFlows = netCashFlows;
    }
}