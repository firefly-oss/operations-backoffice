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

@PageTitle(NavigationConstants.DAILY_PERFORMANCE_METRICS)
@Route(value = "dashboard/daily-performance-metrics", layout = MainLayout.class)
public class DailyPerformanceMetrics extends ViewFrame {

    private static final String CLASS_NAME = "daily-performance-metrics";
    public static final String MAX_WIDTH = "1024px";

    public DailyPerformanceMetrics() {
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
        xAxis.setCategories("00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", 
                "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", 
                "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Number of Transactions");
        conf.addyAxis(yAxis);

        // Add multiple series for different transaction types
        ListSeries payments = new ListSeries("Payments");
        payments.setData(120, 90, 80, 70, 60, 90, 150, 250, 300, 280, 260, 240, 
                         320, 350, 330, 310, 290, 270, 250, 230, 210, 190, 170, 150);
        
        ListSeries transfers = new ListSeries("Transfers");
        transfers.setData(80, 70, 60, 50, 40, 60, 100, 180, 220, 200, 190, 180, 
                         240, 260, 250, 230, 210, 190, 170, 150, 130, 110, 90, 70);
        
        ListSeries inquiries = new ListSeries("Inquiries");
        inquiries.setData(200, 150, 130, 110, 100, 140, 220, 350, 420, 400, 380, 360, 
                         450, 480, 460, 440, 420, 400, 380, 360, 340, 320, 300, 280);
        
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
        xAxis.setCategories("Web", "Mobile App", "API", "Branch", "Call Center");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Number of Users");
        conf.addyAxis(yAxis);

        // Create series for different time periods
        ListSeries morning = new ListSeries("Morning (6-12)");
        morning.setData(2500, 4200, 1800, 850, 650);
        
        ListSeries afternoon = new ListSeries("Afternoon (12-18)");
        afternoon.setData(3200, 5100, 2200, 920, 780);
        
        ListSeries evening = new ListSeries("Evening (18-24)");
        evening.setData(2800, 4800, 1950, 320, 580);
        
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

        cards.add(createMetricCard("API Response Time", "85ms", "↓ 12ms"));
        cards.add(createMetricCard("Page Load Time", "1.2s", "↓ 0.3s"));
        cards.add(createMetricCard("Database Query Time", "45ms", "↓ 8ms"));
        cards.add(createMetricCard("Authentication Time", "120ms", "↑ 15ms"));

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