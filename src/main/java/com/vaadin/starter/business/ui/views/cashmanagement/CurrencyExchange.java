package com.vaadin.starter.business.ui.views.cashmanagement;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.cashmanagement.CurrencyExchangeRateDTO;
import com.vaadin.starter.business.backend.dto.cashmanagement.CurrencyTransactionDTO;
import com.vaadin.starter.business.backend.service.CurrencyExchangeService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.util.BoxShadowBorders;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@PageTitle(NavigationConstants.CURRENCY_EXCHANGE)
@Route(value = "cash-management/currency-exchange", layout = MainLayout.class)
public class CurrencyExchange extends ViewFrame {

    private final CurrencyExchangeService currencyExchangeService;

    @Autowired
    public CurrencyExchange(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
        setViewContent(createContent());
    }

    private Component createContent() {
        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(false);
        content.setSizeFull();

        content.add(createTabs());
        content.add(createMainContent());

        return content;
    }

    private Component createTabs() {
        Tab exchangeRates = new Tab("Exchange Rates");
        Tab currencyConverter = new Tab("Currency Converter");
        Tab transactions = new Tab("Transactions");
        Tab marketAnalysis = new Tab("Market Analysis");

        Tabs tabs = new Tabs(exchangeRates, currencyConverter, transactions, marketAnalysis);
        tabs.addClassName(BoxShadowBorders.BOTTOM);
        tabs.setWidthFull();
        return tabs;
    }

    private Component createMainContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setSpacing(true);
        layout.setSizeFull();

        HorizontalLayout topSection = new HorizontalLayout();
        topSection.setWidthFull();
        topSection.add(createCurrencyConverter());
        topSection.add(createExchangeRateTable());

        HorizontalLayout middleSection = new HorizontalLayout();
        middleSection.setWidthFull();
        middleSection.add(createExchangeRateChart());

        layout.add(topSection);
        layout.add(middleSection);
        layout.add(createRecentTransactionsGrid());

        return layout;
    }

    private Component createCurrencyConverter() {
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName(BoxShadowBorders.BOTTOM);
        layout.getStyle().set("background-color", "var(--lumo-base-color)");
        layout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        layout.setPadding(true);
        layout.setWidth("400px");

        H3 title = new H3("Currency Converter");
        layout.add(title);

        FormLayout formLayout = new FormLayout();

        NumberField amountField = new NumberField("Amount");
        amountField.setValue(1000.0);
        amountField.setMin(0);

        ComboBox<String> fromCurrencyField = new ComboBox<>("From Currency");
        fromCurrencyField.setItems(currencyExchangeService.getExchangeRates().keySet());
        fromCurrencyField.setValue("USD");

        ComboBox<String> toCurrencyField = new ComboBox<>("To Currency");
        toCurrencyField.setItems(currencyExchangeService.getExchangeRates().keySet());
        toCurrencyField.setValue("EUR");

        TextField rateField = new TextField("Exchange Rate");
        rateField.setValue("0.9200");
        rateField.setReadOnly(true);

        TextField resultField = new TextField("Converted Amount");
        resultField.setValue("920.00 EUR");
        resultField.setReadOnly(true);

        Button convertButton = UIUtils.createPrimaryButton("Convert");
        convertButton.addClickListener(e -> {
            if (amountField.getValue() == null || fromCurrencyField.getValue() == null || toCurrencyField.getValue() == null) {
                Notification.show("Please fill all fields");
                return;
            }

            double amount = amountField.getValue();
            String fromCurrency = fromCurrencyField.getValue();
            String toCurrency = toCurrencyField.getValue();

            double rate = currencyExchangeService.getExchangeRate(fromCurrency, toCurrency);
            double result = currencyExchangeService.convertCurrency(amount, fromCurrency, toCurrency);

            rateField.setValue(String.format("%.4f", rate));
            resultField.setValue(String.format("%.2f %s", result, toCurrency));
        });

        formLayout.add(amountField, fromCurrencyField, toCurrencyField, rateField, resultField);

        layout.add(formLayout);
        layout.add(convertButton);

        return layout;
    }

    private Component createExchangeRateTable() {
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName(BoxShadowBorders.BOTTOM);
        layout.getStyle().set("background-color", "var(--lumo-base-color)");
        layout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        layout.setPadding(true);
        layout.setWidth("400px");

        H3 title = new H3("Exchange Rates (USD)");
        layout.add(title);

        List<CurrencyExchangeRateDTO> rates = currencyExchangeService.getExchangeRatesWithChange();
        Grid<CurrencyExchangeRateDTO> grid = new Grid<>();
        grid.setItems(rates);

        grid.addColumn(CurrencyExchangeRateDTO::getCurrency)
                .setHeader("Currency")
                .setAutoWidth(true);

        grid.addColumn(rate -> String.format("%.4f", rate.getRate()))
                .setHeader("Rate")
                .setAutoWidth(true);

        grid.addComponentColumn(rate -> {
            double change = rate.getChange();
            Span changeSpan = new Span(String.format("%+.2f%%", change * 100));

            if (change >= 0) {
                UIUtils.setTextColor(TextColor.SUCCESS, changeSpan);
            } else {
                UIUtils.setTextColor(TextColor.ERROR, changeSpan);
            }

            return changeSpan;
        }).setHeader("24h Change").setAutoWidth(true);

        layout.add(grid);

        return layout;
    }

    private Component createExchangeRateChart() {
        Chart chart = new Chart(ChartType.SPLINE);

        Configuration configuration = chart.getConfiguration();
        configuration.setTitle("Exchange Rate Trends (vs USD)");

        XAxis xAxis = new XAxis();
        String[] days = new String[30];
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 30; i++) {
            days[i] = today.minusDays(29 - i).format(DateTimeFormatter.ofPattern("MMM dd"));
        }
        xAxis.setCategories(days);
        configuration.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Exchange Rate");
        configuration.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix(" USD");
        configuration.setTooltip(tooltip);

        PlotOptionsSpline plotOptions = new PlotOptionsSpline();
        configuration.setPlotOptions(plotOptions);

        // Get historical rates for EUR, GBP, JPY
        List<String> currencies = Arrays.asList("EUR", "GBP", "JPY");
        Map<String, List<Double>> historicalRates = currencyExchangeService.getHistoricalRates(30, currencies);

        ListSeries eurSeries = new ListSeries("EUR");
        ListSeries gbpSeries = new ListSeries("GBP");
        ListSeries jpySeries = new ListSeries("JPY (÷100)");

        List<Double> eurRates = historicalRates.get("EUR");
        List<Double> gbpRates = historicalRates.get("GBP");
        List<Double> jpyRates = historicalRates.get("JPY");

        for (int i = 0; i < 30; i++) {
            eurSeries.addData(eurRates.get(i));
            gbpSeries.addData(gbpRates.get(i));
            jpySeries.addData(jpyRates.get(i) / 100); // Convert to JPY/100 for better visualization
        }

        configuration.addSeries(eurSeries);
        configuration.addSeries(gbpSeries);
        configuration.addSeries(jpySeries);

        chart.addClassName(BoxShadowBorders.BOTTOM);
        chart.getStyle().set("background-color", "var(--lumo-base-color)");
        chart.getStyle().set("border-radius", "var(--lumo-border-radius)");

        return chart;
    }

    private Component createRecentTransactionsGrid() {
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName(BoxShadowBorders.BOTTOM);
        layout.getStyle().set("background-color", "var(--lumo-base-color)");
        layout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        layout.setPadding(true);
        layout.setWidthFull();

        H3 title = new H3("Recent Currency Transactions");
        layout.add(title);

        List<CurrencyTransactionDTO> transactions = currencyExchangeService.getRecentTransactions();
        Grid<CurrencyTransactionDTO> grid = new Grid<>();
        grid.setItems(transactions);
        grid.setHeight("300px");

        grid.addColumn(CurrencyTransactionDTO::getId)
                .setHeader("Transaction ID")
                .setAutoWidth(true);

        grid.addColumn(tx -> tx.getFormattedDate())
                .setHeader("Date")
                .setAutoWidth(true);

        grid.addColumn(CurrencyTransactionDTO::getType)
                .setHeader("Type")
                .setAutoWidth(true);

        grid.addColumn(tx -> String.format("%.2f %s", tx.getAmount(), tx.getFromCurrency()))
                .setHeader("From")
                .setAutoWidth(true);

        grid.addColumn(tx -> String.format("%.2f %s", tx.getConvertedAmount(), tx.getToCurrency()))
                .setHeader("To")
                .setAutoWidth(true);

        grid.addColumn(tx -> String.format("%.4f", tx.getRate()))
                .setHeader("Rate")
                .setAutoWidth(true);

        grid.addComponentColumn(tx -> {
            Span statusSpan = new Span(tx.getStatus());

            switch (tx.getStatus()) {
                case "Completed":
                    UIUtils.setTextColor(TextColor.SUCCESS, statusSpan);
                    break;
                case "Pending":
                case "Processing":
                    UIUtils.setTextColor(TextColor.TERTIARY, statusSpan);
                    break;
                case "Failed":
                    UIUtils.setTextColor(TextColor.ERROR, statusSpan);
                    break;
            }

            return statusSpan;
        }).setHeader("Status").setAutoWidth(true);

        layout.add(grid);

        return layout;
    }

}
