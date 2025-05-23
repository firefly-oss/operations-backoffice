package com.vaadin.starter.business.ui.views.cashmanagement;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.cashmanagement.*;
import com.vaadin.starter.business.backend.service.LiquidityService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.util.BoxShadowBorders;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle(NavigationConstants.LIQUIDITY_MANAGEMENT)
@Route(value = "cash-management/liquidity", layout = MainLayout.class)
public class LiquidityManagement extends ViewFrame {

    private final LiquidityService liquidityService;

    @Autowired
    public LiquidityManagement(LiquidityService liquidityService) {
        this.liquidityService = liquidityService;
        setViewContent(createContent());
    }

    private Component createContent() {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(false);
        content.setSizeFull();

        content.add(createTabs());
        content.add(createDashboard());

        return content;
    }

    private Component createTabs() {
        Tab overview = new Tab("Overview");
        Tab forecasting = new Tab("Forecasting");
        Tab planning = new Tab("Planning");
        Tab monitoring = new Tab("Monitoring");
        Tab reporting = new Tab("Reporting");

        Tabs tabs = new Tabs(overview, forecasting, planning, monitoring, reporting);
        tabs.addClassName(BoxShadowBorders.BOTTOM);
        tabs.setWidthFull();
        return tabs;
    }

    private Component createDashboard() {
        Board board = new Board();
        board.addRow(
                createLiquiditySummary(),
                createCashFlowForecast()
        );
        board.addRow(
                createLiquidityRatios(),
                createLiquidityTrend()
        );
        board.addRow(createLiquidityByEntity());

        VerticalLayout dashboardLayout = new VerticalLayout(board);
        dashboardLayout.setPadding(true);
        dashboardLayout.setSpacing(true);
        dashboardLayout.setSizeFull();

        return dashboardLayout;
    }

    private Component createLiquiditySummary() {
        // Get liquidity summary from service
        LiquiditySummaryDTO summary = liquidityService.getLiquiditySummary();

        // Format values
        String availableLiquidityValue = "$" + String.format("%,.0f", summary.getAvailableLiquidity());
        String requiredLiquidityValue = "$" + String.format("%,.0f", summary.getRequiredLiquidity());
        String liquidityBufferValue = "$" + String.format("%,.0f", summary.getLiquidityBuffer());
        String liquidityCoverageValue = String.format("%.1f%%", summary.getLiquidityCoverageRatio());

        // Format changes
        String availableLiquidityChange = String.format("%+.1f%%", summary.getAvailableLiquidityChange());
        String requiredLiquidityChange = String.format("%+.1f%%", summary.getRequiredLiquidityChange());
        String liquidityBufferChange = String.format("%+.1f%%", summary.getLiquidityBufferChange());
        String liquidityCoverageChange = String.format("%+.1f%%", summary.getLiquidityCoverageRatioChange());

        // Create metrics
        Component availableLiquidity = createMetric("Available Liquidity", availableLiquidityValue, availableLiquidityChange, summary.getAvailableLiquidityChange() >= 0);
        Component requiredLiquidity = createMetric("Required Liquidity", requiredLiquidityValue, requiredLiquidityChange, summary.getRequiredLiquidityChange() <= 0);
        Component liquidityBuffer = createMetric("Liquidity Buffer", liquidityBufferValue, liquidityBufferChange, summary.getLiquidityBufferChange() >= 0);
        Component liquidityCoverage = createMetric("Liquidity Coverage Ratio", liquidityCoverageValue, liquidityCoverageChange, summary.getLiquidityCoverageRatioChange() >= 0);

        // Create layout
        VerticalLayout layout = new VerticalLayout();
        layout.add(new H3("Liquidity Summary"));
        layout.add(availableLiquidity);
        layout.add(requiredLiquidity);
        layout.add(liquidityBuffer);
        layout.add(liquidityCoverage);

        layout.addClassName(BoxShadowBorders.BOTTOM);
        layout.getStyle().set("background-color", "var(--lumo-base-color)");
        layout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        layout.setPadding(true);

        return layout;
    }

    private Component createMetric(String label, String value, String change, boolean positive) {
        Span labelSpan = new Span(label);
        labelSpan.getStyle().set("font-size", "var(--lumo-font-size-s)");

        Span valueSpan = new Span(value);
        valueSpan.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        valueSpan.getStyle().set("font-weight", "bold");

        Span changeSpan = new Span(change);
        changeSpan.getStyle().set("font-size", "var(--lumo-font-size-s)");
        if (positive) {
            UIUtils.setTextColor(TextColor.SUCCESS, changeSpan);
        } else {
            UIUtils.setTextColor(TextColor.ERROR, changeSpan);
        }

        VerticalLayout layout = new VerticalLayout(labelSpan, valueSpan, changeSpan);
        layout.setPadding(false);
        layout.setSpacing(false);

        return layout;
    }

    private Component createCashFlowForecast() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration configuration = chart.getConfiguration();
        configuration.setTitle("Cash Flow Forecast (Next 7 Days)");

        // Get cash flow forecast from service
        CashFlowForecastDTO forecast = liquidityService.getCashFlowForecast(7);

        // Format dates for x-axis
        String[] days = new String[7];
        for (int i = 0; i < 7; i++) {
            days[i] = forecast.getDates().get(i).toString();
        }

        XAxis xAxis = new XAxis();
        xAxis.setCategories(days);
        configuration.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Amount (USD)");
        configuration.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix(" USD");
        configuration.setTooltip(tooltip);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setPointPadding(0.2);
        plotOptions.setBorderWidth(0);
        configuration.setPlotOptions(plotOptions);

        ListSeries inflows = new ListSeries("Inflows");
        ListSeries outflows = new ListSeries("Outflows");
        ListSeries netCashFlow = new ListSeries("Net Cash Flow");

        for (int i = 0; i < 7; i++) {
            inflows.addData(forecast.getInflows().get(i));
            outflows.addData(-forecast.getOutflows().get(i)); // Negative for visualization
            netCashFlow.addData(forecast.getNetCashFlows().get(i));
        }

        configuration.addSeries(inflows);
        configuration.addSeries(outflows);
        configuration.addSeries(netCashFlow);

        chart.addClassName(BoxShadowBorders.BOTTOM);
        chart.getStyle().set("background-color", "var(--lumo-base-color)");
        chart.getStyle().set("border-radius", "var(--lumo-border-radius)");

        return chart;
    }

    private Component createLiquidityRatios() {
        Chart chart = new Chart(ChartType.PIE);

        Configuration configuration = chart.getConfiguration();
        configuration.setTitle("Liquidity Allocation");

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix(" USD");
        configuration.setTooltip(tooltip);

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(Cursor.POINTER);
        plotOptions.setShowInLegend(true);
        configuration.setPlotOptions(plotOptions);

        // Get liquidity allocation from service
        LiquidityAllocationDTO allocation = liquidityService.getLiquidityAllocation();

        DataSeries series = new DataSeries();
        for (int i = 0; i < allocation.getCategories().size(); i++) {
            series.add(new DataSeriesItem(allocation.getCategories().get(i), allocation.getAmounts().get(i)));
        }

        configuration.addSeries(series);

        chart.addClassName(BoxShadowBorders.BOTTOM);
        chart.getStyle().set("background-color", "var(--lumo-base-color)");
        chart.getStyle().set("border-radius", "var(--lumo-border-radius)");

        return chart;
    }

    private Component createLiquidityTrend() {
        Chart chart = new Chart(ChartType.AREA);

        Configuration configuration = chart.getConfiguration();
        configuration.setTitle("Liquidity Trend (Last 12 Months)");

        // Get liquidity trend from service
        LiquidityTrendDTO trend = liquidityService.getLiquidityTrend(12);

        // Format months for x-axis
        String[] months = new String[12];
        for (int i = 0; i < 12; i++) {
            months[i] = trend.getMonths().get(i).toString();
        }

        XAxis xAxis = new XAxis();
        xAxis.setCategories(months);
        configuration.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Amount (USD)");
        configuration.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix(" USD");
        configuration.setTooltip(tooltip);

        PlotOptionsArea plotOptions = new PlotOptionsArea();
        configuration.setPlotOptions(plotOptions);

        ListSeries availableLiquidity = new ListSeries("Available Liquidity");
        ListSeries requiredLiquidity = new ListSeries("Required Liquidity");

        for (int i = 0; i < 12; i++) {
            availableLiquidity.addData(trend.getAvailableLiquidity().get(i));
            requiredLiquidity.addData(trend.getRequiredLiquidity().get(i));
        }

        configuration.addSeries(availableLiquidity);
        configuration.addSeries(requiredLiquidity);

        chart.addClassName(BoxShadowBorders.BOTTOM);
        chart.getStyle().set("background-color", "var(--lumo-base-color)");
        chart.getStyle().set("border-radius", "var(--lumo-border-radius)");

        return chart;
    }

    private Component createLiquidityByEntity() {
        Chart chart = new Chart(ChartType.BAR);

        Configuration configuration = chart.getConfiguration();
        configuration.setTitle("Liquidity by Entity");

        // Get entity liquidity from service
        EntityLiquidityDTO entityLiquidityDTO = liquidityService.getEntityLiquidity();

        XAxis xAxis = new XAxis();
        String[] entities = entityLiquidityDTO.getEntities().toArray(new String[0]);
        xAxis.setCategories(entities);
        configuration.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Amount (USD)");
        configuration.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix(" USD");
        configuration.setTooltip(tooltip);

        PlotOptionsBar plotOptions = new PlotOptionsBar();
        plotOptions.setDataLabels(new DataLabels(true));
        configuration.setPlotOptions(plotOptions);

        ListSeries availableLiquidity = new ListSeries("Available Liquidity");
        Number[] availableData = entityLiquidityDTO.getAvailableLiquidity().stream()
                .map(Number.class::cast)
                .toArray(Number[]::new);
        availableLiquidity.setData(availableData);

        ListSeries requiredLiquidity = new ListSeries("Required Liquidity");
        Number[] requiredData = entityLiquidityDTO.getRequiredLiquidity().stream()
                .map(Number.class::cast)
                .toArray(Number[]::new);
        requiredLiquidity.setData(requiredData);

        configuration.addSeries(availableLiquidity);
        configuration.addSeries(requiredLiquidity);

        chart.addClassName(BoxShadowBorders.BOTTOM);
        chart.getStyle().set("background-color", "var(--lumo-base-color)");
        chart.getStyle().set("border-radius", "var(--lumo-border-radius)");

        Row row = new Row(chart);
        row.setComponentSpan(chart, 2);

        return row;
    }
}
