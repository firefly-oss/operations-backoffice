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
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.util.BoxShadowBorders;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.LocalDate;
import java.util.Random;

@PageTitle(NavigationConstants.LIQUIDITY_MANAGEMENT)
@Route(value = "cash-management/liquidity", layout = MainLayout.class)
public class LiquidityManagement extends ViewFrame {

    private final Random random = new Random();

    public LiquidityManagement() {
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
        // Create metrics
        Component availableLiquidity = createMetric("Available Liquidity", "$24,500,000", "+5.2%", true);
        Component requiredLiquidity = createMetric("Required Liquidity", "$18,750,000", "-2.1%", false);
        Component liquidityBuffer = createMetric("Liquidity Buffer", "$5,750,000", "+8.3%", true);
        Component liquidityCoverage = createMetric("Liquidity Coverage Ratio", "130.7%", "+3.5%", true);

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

        XAxis xAxis = new XAxis();
        String[] days = new String[7];
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            days[i] = today.plusDays(i).toString();
        }
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
            double inflow = 1000000 + random.nextDouble() * 500000;
            double outflow = 800000 + random.nextDouble() * 400000;
            inflows.addData(inflow);
            outflows.addData(-outflow);
            netCashFlow.addData(inflow - outflow);
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

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Operating Cash", 8500000));
        series.add(new DataSeriesItem("Short-term Investments", 6200000));
        series.add(new DataSeriesItem("Credit Lines", 4800000));
        series.add(new DataSeriesItem("Marketable Securities", 3100000));
        series.add(new DataSeriesItem("Other Liquid Assets", 1900000));

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

        XAxis xAxis = new XAxis();
        String[] months = new String[12];
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 12; i++) {
            months[i] = today.minusMonths(11 - i).getMonth().toString();
        }
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

        double available = 20000000;
        double required = 16000000;

        for (int i = 0; i < 12; i++) {
            available = available * (1 + (random.nextDouble() * 0.05 - 0.02));
            required = required * (1 + (random.nextDouble() * 0.04 - 0.01));
            availableLiquidity.addData(available);
            requiredLiquidity.addData(required);
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

        XAxis xAxis = new XAxis();
        String[] entities = {"Corporate HQ", "North America", "Europe", "Asia Pacific", "Latin America", "Middle East & Africa"};
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
        availableLiquidity.setData(9500000, 6200000, 5100000, 3800000, 2100000, 1300000);

        ListSeries requiredLiquidity = new ListSeries("Required Liquidity");
        requiredLiquidity.setData(7200000, 4800000, 4100000, 3000000, 1600000, 900000);

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
