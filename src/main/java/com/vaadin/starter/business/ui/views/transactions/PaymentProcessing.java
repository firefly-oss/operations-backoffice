package com.vaadin.starter.business.ui.views.transactions;

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

@PageTitle(NavigationConstants.PAYMENT_PROCESSING)
@Route(value = "transactions/payment-processing", layout = MainLayout.class)
public class PaymentProcessing extends ViewFrame {

    private static final String CLASS_NAME = "payment-processing";
    public static final String MAX_WIDTH = "1024px";

    public PaymentProcessing() {
        setViewContent(createContent());
    }

    private Component createContent() {
        Component paymentStatus = createPaymentStatus();
        Component paymentVolume = createPaymentVolume();
        Component paymentMethods = createPaymentMethods();

        FlexBoxLayout content = new FlexBoxLayout(paymentStatus, paymentVolume, paymentMethods);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createPaymentStatus() {
        FlexBoxLayout paymentStatus = new FlexBoxLayout(
                createHeader(VaadinIcon.CREDIT_CARD, "Payment Status"),
                createPaymentStatusCards());
        paymentStatus.setBoxSizing(BoxSizing.BORDER_BOX);
        paymentStatus.setDisplay(Display.BLOCK);
        paymentStatus.setMargin(com.vaadin.starter.business.ui.layout.size.Top.L);
        paymentStatus.setMaxWidth(MAX_WIDTH);
        paymentStatus.setPadding(Horizontal.RESPONSIVE_L);
        paymentStatus.setWidthFull();
        return paymentStatus;
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

    private Component createPaymentStatusCards() {
        Row cards = new Row();
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, cards);
        UIUtils.setBorderRadius(BorderRadius.S, cards);
        UIUtils.setShadow(Shadow.XS, cards);

        cards.add(createPaymentStatusCard("Pending", 24, "orange"));
        cards.add(createPaymentStatusCard("Processing", 12, "blue"));
        cards.add(createPaymentStatusCard("Completed", 156, "green"));
        cards.add(createPaymentStatusCard("Failed", 8, "red"));

        return cards;
    }

    private Component createPaymentStatusCard(String status, int count, String color) {
        Span statusLabel = new Span(status);
        statusLabel.getStyle().set("font-weight", "bold");
        statusLabel.getStyle().set("color", color);

        H3 countLabel = new H3(String.valueOf(count));
        countLabel.getStyle().set("margin", "0.2em 0");

        FlexBoxLayout card = new FlexBoxLayout(statusLabel, countLabel);
        card.setFlexDirection(FlexDirection.COLUMN);
        card.setAlignItems(FlexComponent.Alignment.CENTER);
        card.setPadding(Uniform.M);
        card.setSpacing(Vertical.S);
        card.setHeight("120px");

        return card;
    }

    private Component createPaymentVolume() {
        FlexBoxLayout paymentVolume = new FlexBoxLayout(
                createHeader(VaadinIcon.CHART, "Payment Volume"),
                createPaymentVolumeChart());
        paymentVolume.setBoxSizing(BoxSizing.BORDER_BOX);
        paymentVolume.setDisplay(Display.BLOCK);
        paymentVolume.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        paymentVolume.setMaxWidth(MAX_WIDTH);
        paymentVolume.setPadding(Horizontal.RESPONSIVE_L);
        paymentVolume.setWidthFull();
        return paymentVolume;
    }

    private Component createPaymentVolumeChart() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        conf.setTitle("Daily Payment Volume");

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Number of Payments");
        conf.addyAxis(yAxis);

        // Create series for different payment methods
        ListSeries creditCard = new ListSeries("Credit Card");
        creditCard.setData(45, 52, 48, 58, 63, 42, 30);

        ListSeries bankTransfer = new ListSeries("Bank Transfer");
        bankTransfer.setData(32, 38, 35, 40, 42, 28, 25);

        ListSeries digitalWallet = new ListSeries("Digital Wallet");
        digitalWallet.setData(18, 24, 22, 28, 32, 20, 15);

        conf.addSeries(creditCard);
        conf.addSeries(bankTransfer);
        conf.addSeries(digitalWallet);

        FlexBoxLayout card = new FlexBoxLayout(chart);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setHeight("400px");
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }

    private Component createPaymentMethods() {
        FlexBoxLayout paymentMethods = new FlexBoxLayout(
                createHeader(VaadinIcon.WALLET, "Payment Methods"),
                createPaymentMethodsChart());
        paymentMethods.setBoxSizing(BoxSizing.BORDER_BOX);
        paymentMethods.setDisplay(Display.BLOCK);
        paymentMethods.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        paymentMethods.setMaxWidth(MAX_WIDTH);
        paymentMethods.setPadding(Horizontal.RESPONSIVE_L);
        paymentMethods.setWidthFull();
        return paymentMethods;
    }

    private Component createPaymentMethodsChart() {
        Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        conf.setTitle("Payment Methods Distribution");

        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
        tooltip.setValueSuffix("%");
        conf.setTooltip(tooltip);

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(Cursor.POINTER);
        plotOptions.setShowInLegend(true);
        conf.setPlotOptions(plotOptions);

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Credit Card", 45.0));
        series.add(new DataSeriesItem("Bank Transfer", 30.0));
        series.add(new DataSeriesItem("Digital Wallet", 15.0));
        series.add(new DataSeriesItem("Mobile Payment", 8.0));
        series.add(new DataSeriesItem("Other", 2.0));
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
}
