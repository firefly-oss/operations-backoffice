package com.vaadin.starter.business.ui.views.dashboard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.dashboard.OperationalMetricDTO;
import com.vaadin.starter.business.backend.dto.dashboard.SystemStatusDTO;
import com.vaadin.starter.business.backend.service.DashboardService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Right;
import com.vaadin.starter.business.ui.layout.size.Uniform;
import com.vaadin.starter.business.ui.layout.size.Vertical;
import com.vaadin.starter.business.ui.util.IconSize;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BorderRadius;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.util.css.Display;
import com.vaadin.starter.business.ui.util.css.Shadow;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle(NavigationConstants.DASHBOARD)
@Route(value = "", layout = MainLayout.class)
public class OperationsOverview extends ViewFrame {

    private static final String CLASS_NAME = "operations-overview";
    public static final String MAX_WIDTH = "1024px";

    public OperationsOverview() {
        setViewContent(createContent());
    }

    private Component createContent() {
        Component systemStatus = createSystemStatus();
        Component transactionVolume = createTransactionVolume();
        Component operationalMetrics = createOperationalMetrics();

        FlexBoxLayout content = new FlexBoxLayout(systemStatus, transactionVolume, operationalMetrics);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createSystemStatus() {
        FlexBoxLayout systemStatus = new FlexBoxLayout(
                createHeader(VaadinIcon.SERVER, "System Status"),
                createSystemStatusCards());
        systemStatus.setBoxSizing(BoxSizing.BORDER_BOX);
        systemStatus.setDisplay(Display.BLOCK);
        systemStatus.setMargin(com.vaadin.starter.business.ui.layout.size.Top.L);
        systemStatus.setMaxWidth(MAX_WIDTH);
        systemStatus.setPadding(Horizontal.RESPONSIVE_L);
        systemStatus.setWidthFull();
        return systemStatus;
    }

    private FlexBoxLayout createHeader(VaadinIcon icon, String title) {
        FlexBoxLayout header = new FlexBoxLayout(
                UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, icon),
                UIUtils.createH3Label(title));
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setMargin(com.vaadin.starter.business.ui.layout.size.Bottom.L, Horizontal.RESPONSIVE_L);
        header.setSpacing(Right.L);
        return header;
    }

    private Component createSystemStatusCards() {
        Row cards = new Row();
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, cards);
        UIUtils.setBorderRadius(BorderRadius.S, cards);
        UIUtils.setShadow(Shadow.XS, cards);

        cards.add(createSystemStatusCard("Core Banking", "Operational", "99.99%"));
        cards.add(createSystemStatusCard("Payment Processing", "Operational", "99.95%"));
        cards.add(createSystemStatusCard("Customer Portal", "Operational", "99.98%"));
        cards.add(createSystemStatusCard("Mobile Banking", "Operational", "99.97%"));

        return cards;
    }

    private Component createSystemStatusCard(String system, String status, String uptime) {
        Span systemName = new Span(system);
        systemName.getStyle().set("font-weight", "bold");

        Span statusLabel = new Span(status);
        statusLabel.getStyle().set("color", "green");

        Span uptimeLabel = new Span("Uptime: " + uptime);
        uptimeLabel.getStyle().set("font-size", "0.8em");

        FlexBoxLayout card = new FlexBoxLayout(systemName, statusLabel, uptimeLabel);
        card.setFlexDirection(FlexDirection.COLUMN);
        card.setAlignItems(FlexComponent.Alignment.CENTER);
        card.setPadding(Uniform.M);
        card.setSpacing(Vertical.S);
        card.setHeight("120px");

        return card;
    }

    private Component createTransactionVolume() {
        FlexBoxLayout transactionVolume = new FlexBoxLayout(
                createHeader(VaadinIcon.CHART, "Transaction Volume"),
                createTransactionVolumeChart());
        transactionVolume.setBoxSizing(BoxSizing.BORDER_BOX);
        transactionVolume.setDisplay(Display.BLOCK);
        transactionVolume.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        transactionVolume.setMaxWidth(MAX_WIDTH);
        transactionVolume.setPadding(Horizontal.RESPONSIVE_L);
        transactionVolume.setWidthFull();
        return transactionVolume;
    }

    private Component createTransactionVolumeChart() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        conf.setTitle("Hourly Transaction Volume");
        conf.getLegend().setEnabled(false);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("00:00", "02:00", "04:00", "06:00", "08:00", "10:00", "12:00", 
                "14:00", "16:00", "18:00", "20:00", "22:00");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Number of Transactions");
        conf.addyAxis(yAxis);

        ListSeries series = new ListSeries("Transactions");
        series.setData(1200, 980, 850, 920, 1450, 2100, 2400, 2300, 2100, 1800, 1650, 1400);
        conf.addSeries(series);

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }

    private Component createOperationalMetrics() {
        FlexBoxLayout operationalMetrics = new FlexBoxLayout(
                createHeader(VaadinIcon.DASHBOARD, "Key Operational Metrics"),
                createOperationalMetricsCards());
        operationalMetrics.setBoxSizing(BoxSizing.BORDER_BOX);
        operationalMetrics.setDisplay(Display.BLOCK);
        operationalMetrics.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        operationalMetrics.setMaxWidth(MAX_WIDTH);
        operationalMetrics.setPadding(Horizontal.RESPONSIVE_L);
        operationalMetrics.setWidthFull();
        return operationalMetrics;
    }

    private Component createOperationalMetricsCards() {
        Row cards = new Row();
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, cards);
        UIUtils.setBorderRadius(BorderRadius.S, cards);
        UIUtils.setShadow(Shadow.XS, cards);

        cards.add(createMetricCard("Average Response Time", "125ms", "↓ 5%"));
        cards.add(createMetricCard("Transaction Success Rate", "99.8%", "↑ 0.2%"));
        cards.add(createMetricCard("Active Users", "12,450", "↑ 8%"));
        cards.add(createMetricCard("Error Rate", "0.15%", "↓ 0.05%"));

        return cards;
    }

    private Component createMetricCard(String metric, String value, String change) {
        Span metricName = new Span(metric);
        metricName.getStyle().set("font-weight", "bold");

        H3 metricValue = new H3(value);
        metricValue.getStyle().set("margin", "0.2em 0");

        Span changeLabel = new Span(change);
        changeLabel.getStyle().set("font-size", "0.8em");

        // Set color based on whether it's an improvement
        if (change.contains("↑") && !metric.contains("Error")) {
            changeLabel.getStyle().set("color", "green");
        } else if (change.contains("↓") && metric.contains("Error")) {
            changeLabel.getStyle().set("color", "green");
        } else if (change.contains("↓") && !metric.contains("Error")) {
            changeLabel.getStyle().set("color", "red");
        } else if (change.contains("↑") && metric.contains("Error")) {
            changeLabel.getStyle().set("color", "red");
        }

        FlexBoxLayout card = new FlexBoxLayout(metricName, metricValue, changeLabel);
        card.setFlexDirection(FlexDirection.COLUMN);
        card.setAlignItems(FlexComponent.Alignment.CENTER);
        card.setPadding(Uniform.M);
        card.setSpacing(Vertical.XS);
        card.setHeight("120px");

        return card;
    }
}
