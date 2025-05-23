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

@PageTitle(NavigationConstants.DAILY_PERFORMANCE_METRICS)
@Route(value = "dashboard/daily-performance-metrics", layout = MainLayout.class)
public class DailyPerformanceMetrics extends ViewFrame {

    private static final String CLASS_NAME = "daily-performance-metrics";
    public static final String MAX_WIDTH = "1024px";

    private final DashboardService dashboardService;

    @Autowired
    public DailyPerformanceMetrics(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
        setViewContent(createContent());
    }

    private Component createContent() {
        Component transactionMetrics = createTransactionMetrics();
        Component userActivity = createUserActivity();
        Component responseTimeMetrics = createResponseTimeMetrics();

        FlexBoxLayout content = new FlexBoxLayout(transactionMetrics, userActivity, responseTimeMetrics);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createTransactionMetrics() {
        FlexBoxLayout transactionMetrics = new FlexBoxLayout(
                createHeader(VaadinIcon.CHART_LINE, "Transaction Metrics"),
                createTransactionMetricsChart());
        transactionMetrics.setBoxSizing(BoxSizing.BORDER_BOX);
        transactionMetrics.setDisplay(Display.BLOCK);
        transactionMetrics.setMargin(com.vaadin.starter.business.ui.layout.size.Top.L);
        transactionMetrics.setMaxWidth(MAX_WIDTH);
        transactionMetrics.setPadding(Horizontal.RESPONSIVE_L);
        transactionMetrics.setWidthFull();
        return transactionMetrics;
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

    private Component createTransactionMetricsChart() {
        Chart chart = new Chart(ChartType.AREA);

        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        conf.setTitle("Today's Transaction Volume by Hour");

        XAxis xAxis = new XAxis();
        xAxis.setCategories(dashboardService.getHourlyCategories().toArray(new String[0]));
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Number of Transactions");
        conf.addyAxis(yAxis);

        // Add multiple series for different transaction types
        ListSeries payments = new ListSeries("Payments");
        payments.setData(dashboardService.getPaymentTransactions().toArray(new Number[0]));

        ListSeries transfers = new ListSeries("Transfers");
        transfers.setData(dashboardService.getTransferTransactions().toArray(new Number[0]));

        ListSeries inquiries = new ListSeries("Inquiries");
        inquiries.setData(dashboardService.getInquiryTransactions().toArray(new Number[0]));

        conf.addSeries(payments);
        conf.addSeries(transfers);
        conf.addSeries(inquiries);

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }

    private Component createUserActivity() {
        FlexBoxLayout userActivity = new FlexBoxLayout(
                createHeader(VaadinIcon.USERS, "User Activity"),
                createUserActivityChart());
        userActivity.setBoxSizing(BoxSizing.BORDER_BOX);
        userActivity.setDisplay(Display.BLOCK);
        userActivity.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        userActivity.setMaxWidth(MAX_WIDTH);
        userActivity.setPadding(Horizontal.RESPONSIVE_L);
        userActivity.setWidthFull();
        return userActivity;
    }

    private Component createUserActivityChart() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        conf.setTitle("Active Users by Channel");

        XAxis xAxis = new XAxis();
        xAxis.setCategories(dashboardService.getUserActivityChannels().toArray(new String[0]));
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Number of Users");
        conf.addyAxis(yAxis);

        // Create series for different time periods
        ListSeries morning = new ListSeries("Morning (6-12)");
        morning.setData(dashboardService.getMorningUserActivity().toArray(new Number[0]));

        ListSeries afternoon = new ListSeries("Afternoon (12-18)");
        afternoon.setData(dashboardService.getAfternoonUserActivity().toArray(new Number[0]));

        ListSeries evening = new ListSeries("Evening (18-24)");
        evening.setData(dashboardService.getEveningUserActivity().toArray(new Number[0]));

        conf.addSeries(morning);
        conf.addSeries(afternoon);
        conf.addSeries(evening);

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }

    private Component createResponseTimeMetrics() {
        FlexBoxLayout responseTimeMetrics = new FlexBoxLayout(
                createHeader(VaadinIcon.TIMER, "Response Time Metrics"),
                createResponseTimeCards());
        responseTimeMetrics.setBoxSizing(BoxSizing.BORDER_BOX);
        responseTimeMetrics.setDisplay(Display.BLOCK);
        responseTimeMetrics.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        responseTimeMetrics.setMaxWidth(MAX_WIDTH);
        responseTimeMetrics.setPadding(Horizontal.RESPONSIVE_L);
        responseTimeMetrics.setWidthFull();
        return responseTimeMetrics;
    }

    private Component createResponseTimeCards() {
        Row cards = new Row();
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, cards);
        UIUtils.setBorderRadius(BorderRadius.S, cards);
        UIUtils.setShadow(Shadow.XS, cards);

        List<DashboardService.ResponseTimeMetric> metrics = dashboardService.getResponseTimeMetrics();
        for (DashboardService.ResponseTimeMetric metric : metrics) {
            cards.add(createMetricCard(metric.getMetric(), metric.getValue(), metric.getChange()));
        }

        return cards;
    }

    private Component createMetricCard(String metric, String value, String change) {
        Span metricName = new Span(metric);
        metricName.getStyle().set("font-weight", "bold");

        H3 metricValue = new H3(value);
        metricValue.getStyle().set("margin", "0.2em 0");

        Span changeLabel = new Span(change);
        changeLabel.getStyle().set("font-size", "0.8em");

        // Set color based on whether it's an improvement (lower is better for response times)
        if (change.contains("↓")) {
            changeLabel.getStyle().set("color", "green");
        } else {
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
