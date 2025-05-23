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