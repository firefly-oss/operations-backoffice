package com.vaadin.starter.business.ui.views.dashboard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.charts.model.style.SolidColor;
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

@PageTitle(NavigationConstants.SERVICE_LEVEL_INDICATORS)
@Route(value = "dashboard/service-level-indicators", layout = MainLayout.class)
public class ServiceLevelIndicators extends ViewFrame {

    private static final String CLASS_NAME = "service-level-indicators";
    public static final String MAX_WIDTH = "1024px";

    public ServiceLevelIndicators() {
        setViewContent(createContent());
    }

    private Component createContent() {
        Component slaOverview = createSlaOverview();
        Component serviceAvailability = createServiceAvailability();
        Component customerSatisfaction = createCustomerSatisfaction();

        FlexBoxLayout content = new FlexBoxLayout(slaOverview, serviceAvailability, customerSatisfaction);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createSlaOverview() {
        FlexBoxLayout slaOverview = new FlexBoxLayout(
                createHeader(VaadinIcon.DASHBOARD, "SLA Overview"),
                createSlaCards());
        slaOverview.setBoxSizing(BoxSizing.BORDER_BOX);
        slaOverview.setDisplay(Display.BLOCK);
        slaOverview.setMargin(com.vaadin.starter.business.ui.layout.size.Top.L);
        slaOverview.setMaxWidth(MAX_WIDTH);
        slaOverview.setPadding(Horizontal.RESPONSIVE_L);
        slaOverview.setWidthFull();
        return slaOverview;
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

    private Component createSlaCards() {
        Row cards = new Row();
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, cards);
        UIUtils.setBorderRadius(BorderRadius.S, cards);
        UIUtils.setShadow(Shadow.XS, cards);

        cards.add(createSlaCard("Transaction Processing", "99.98%", "99.9%", true));
        cards.add(createSlaCard("API Response Time", "98.5%", "99.0%", false));
        cards.add(createSlaCard("System Availability", "99.99%", "99.95%", true));
        cards.add(createSlaCard("Customer Support", "97.2%", "95.0%", true));

        return cards;
    }

    private Component createSlaCard(String service, String currentValue, String target, boolean meetingTarget) {
        Span serviceName = new Span(service);
        serviceName.getStyle().set("font-weight", "bold");

        H3 value = new H3(currentValue);
        value.getStyle().set("margin", "0.2em 0");

        Span targetLabel = new Span("Target: " + target);
        targetLabel.getStyle().set("font-size", "0.8em");

        Span statusLabel = new Span(meetingTarget ? "Meeting SLA" : "Below SLA");
        statusLabel.getStyle().set("font-size", "0.8em");
        statusLabel.getStyle().set("color", meetingTarget ? "green" : "red");
        statusLabel.getStyle().set("font-weight", "bold");

        FlexBoxLayout card = new FlexBoxLayout(serviceName, value, targetLabel, statusLabel);
        card.setFlexDirection(FlexDirection.COLUMN);
        card.setAlignItems(FlexComponent.Alignment.CENTER);
        card.setPadding(Uniform.M);
        card.setSpacing(Vertical.XS);
        card.setHeight("140px");

        return card;
    }

    private Component createServiceAvailability() {
        FlexBoxLayout serviceAvailability = new FlexBoxLayout(
                createHeader(VaadinIcon.CHART_TIMELINE, "Service Availability"),
                createServiceAvailabilityChart());
        serviceAvailability.setBoxSizing(BoxSizing.BORDER_BOX);
        serviceAvailability.setDisplay(Display.BLOCK);
        serviceAvailability.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        serviceAvailability.setMaxWidth(MAX_WIDTH);
        serviceAvailability.setPadding(Horizontal.RESPONSIVE_L);
        serviceAvailability.setWidthFull();
        return serviceAvailability;
    }

    private Component createServiceAvailabilityChart() {
        Chart chart = new Chart(ChartType.SPLINE);

        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        conf.setTitle("30-Day Service Availability");

        XAxis xAxis = new XAxis();
        String[] days = new String[30];
        for (int i = 0; i < 30; i++) {
            days[i] = "Day " + (i + 1);
        }
        xAxis.setCategories(days);
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Availability (%)");
        yAxis.setMin(99.5);
        yAxis.setMax(100);
        conf.addyAxis(yAxis);

        // Create series for different services
        ListSeries coreSystem = new ListSeries("Core Banking");
        ListSeries payments = new ListSeries("Payment Processing");
        ListSeries customerPortal = new ListSeries("Customer Portal");

        // Generate random data between 99.8 and 100 for core system
        Number[] coreData = new Number[30];
        for (int i = 0; i < 30; i++) {
            coreData[i] = 99.8 + Math.random() * 0.2;
        }
        coreSystem.setData(coreData);

        // Generate random data between 99.7 and 100 for payments
        Number[] paymentsData = new Number[30];
        for (int i = 0; i < 30; i++) {
            paymentsData[i] = 99.7 + Math.random() * 0.3;
        }
        payments.setData(paymentsData);

        // Generate random data between 99.6 and 100 for customer portal
        Number[] portalData = new Number[30];
        for (int i = 0; i < 30; i++) {
            portalData[i] = 99.6 + Math.random() * 0.4;
        }
        customerPortal.setData(portalData);

        conf.addSeries(coreSystem);
        conf.addSeries(payments);
        conf.addSeries(customerPortal);

        // Add a plot line for the SLA target
        PlotLine plotLine = new PlotLine();
        plotLine.setValue(99.9);
        plotLine.setColor(new SolidColor("#FF0000"));
        plotLine.setWidth(1);
        plotLine.setZIndex(5);
        plotLine.setDashStyle(DashStyle.DASH);
        plotLine.setLabel(new Label("SLA Target (99.9%)"));
        yAxis.setPlotLines(plotLine);

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }

    private Component createCustomerSatisfaction() {
        FlexBoxLayout customerSatisfaction = new FlexBoxLayout(
                createHeader(VaadinIcon.USERS, "Customer Satisfaction"),
                createCustomerSatisfactionChart());
        customerSatisfaction.setBoxSizing(BoxSizing.BORDER_BOX);
        customerSatisfaction.setDisplay(Display.BLOCK);
        customerSatisfaction.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        customerSatisfaction.setMaxWidth(MAX_WIDTH);
        customerSatisfaction.setPadding(Horizontal.RESPONSIVE_L);
        customerSatisfaction.setWidthFull();
        return customerSatisfaction;
    }

    private Component createCustomerSatisfactionChart() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        conf.setTitle("Customer Satisfaction by Service");

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Online Banking", "Mobile App", "Payment Services", "Customer Support", "Account Management");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Satisfaction Score (1-5)");
        yAxis.setMin(0);
        yAxis.setMax(5);
        conf.addyAxis(yAxis);

        // Create series for current month and previous month
        ListSeries currentMonth = new ListSeries("Current Month");
        currentMonth.setData(4.2, 4.5, 4.1, 3.8, 4.3);

        ListSeries previousMonth = new ListSeries("Previous Month");
        previousMonth.setData(4.0, 4.3, 4.0, 3.7, 4.2);

        conf.addSeries(currentMonth);
        conf.addSeries(previousMonth);

        // Add a plot line for the target
        PlotLine plotLine = new PlotLine();
        plotLine.setValue(4.0);
        plotLine.setColor(new SolidColor("#FF0000"));
        plotLine.setWidth(1);
        plotLine.setZIndex(5);
        plotLine.setDashStyle(DashStyle.DASH);
        plotLine.setLabel(new Label("Target (4.0)"));
        yAxis.setPlotLines(plotLine);

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }
}
