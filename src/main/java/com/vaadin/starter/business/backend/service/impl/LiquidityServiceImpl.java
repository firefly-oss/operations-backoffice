package com.vaadin.starter.business.backend.service.impl;

import com.vaadin.starter.business.backend.dto.cashmanagement.*;
import com.vaadin.starter.business.backend.service.LiquidityService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the LiquidityService interface.
 */
@Service
public class LiquidityServiceImpl implements LiquidityService {

    private final Random random = new Random();

    @Override
    public LiquiditySummaryDTO getLiquiditySummary() {
        double availableLiquidity = 24500000;
        double requiredLiquidity = 18750000;
        double liquidityBuffer = availableLiquidity - requiredLiquidity;
        double liquidityCoverageRatio = (availableLiquidity / requiredLiquidity) * 100;
        
        double availableLiquidityChange = 5.2;
        double requiredLiquidityChange = -2.1;
        double liquidityBufferChange = 8.3;
        double liquidityCoverageRatioChange = 3.5;
        
        return new LiquiditySummaryDTO(
            availableLiquidity,
            requiredLiquidity,
            liquidityBuffer,
            liquidityCoverageRatio,
            availableLiquidityChange,
            requiredLiquidityChange,
            liquidityBufferChange,
            liquidityCoverageRatioChange
        );
    }

    @Override
    public CashFlowForecastDTO getCashFlowForecast(int days) {
        List<LocalDate> dates = new ArrayList<>();
        List<Double> inflows = new ArrayList<>();
        List<Double> outflows = new ArrayList<>();
        List<Double> netCashFlows = new ArrayList<>();
        
        LocalDate today = LocalDate.now();
        
        for (int i = 0; i < days; i++) {
            dates.add(today.plusDays(i));
            
            double inflow = 1000000 + random.nextDouble() * 500000;
            double outflow = 800000 + random.nextDouble() * 400000;
            
            inflows.add(inflow);
            outflows.add(outflow);
            netCashFlows.add(inflow - outflow);
        }
        
        return new CashFlowForecastDTO(dates, inflows, outflows, netCashFlows);
    }

    @Override
    public LiquidityAllocationDTO getLiquidityAllocation() {
        List<String> categories = Arrays.asList(
            "Operating Cash",
            "Short-term Investments",
            "Credit Lines",
            "Marketable Securities",
            "Other Liquid Assets"
        );
        
        List<Double> amounts = Arrays.asList(
            8500000.0,
            6200000.0,
            4800000.0,
            3100000.0,
            1900000.0
        );
        
        return new LiquidityAllocationDTO(categories, amounts);
    }

    @Override
    public LiquidityTrendDTO getLiquidityTrend(int months) {
        List<Month> monthsList = new ArrayList<>();
        List<Double> availableLiquidity = new ArrayList<>();
        List<Double> requiredLiquidity = new ArrayList<>();
        
        LocalDate today = LocalDate.now();
        double available = 20000000;
        double required = 16000000;
        
        for (int i = 0; i < months; i++) {
            Month month = today.minusMonths(months - 1 - i).getMonth();
            monthsList.add(month);
            
            available = available * (1 + (random.nextDouble() * 0.05 - 0.02));
            required = required * (1 + (random.nextDouble() * 0.04 - 0.01));
            
            availableLiquidity.add(available);
            requiredLiquidity.add(required);
        }
        
        return new LiquidityTrendDTO(monthsList, availableLiquidity, requiredLiquidity);
    }

    @Override
    public EntityLiquidityDTO getEntityLiquidity() {
        List<String> entities = Arrays.asList(
            "Corporate HQ",
            "North America",
            "Europe",
            "Asia Pacific",
            "Latin America",
            "Middle East & Africa"
        );
        
        List<Double> availableLiquidity = Arrays.asList(
            9500000.0,
            6200000.0,
            5100000.0,
            3800000.0,
            2100000.0,
            1300000.0
        );
        
        List<Double> requiredLiquidity = Arrays.asList(
            7200000.0,
            4800000.0,
            4100000.0,
            3000000.0,
            1600000.0,
            900000.0
        );
        
        return new EntityLiquidityDTO(entities, availableLiquidity, requiredLiquidity);
    }
}