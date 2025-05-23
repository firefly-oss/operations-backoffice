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
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.util.BoxShadowBorders;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@PageTitle(NavigationConstants.CURRENCY_EXCHANGE)
@Route(value = "cash-management/currency-exchange", layout = MainLayout.class)
public class CurrencyExchange extends ViewFrame {

    private final Random random = new Random();
    private final Map<String, Double> exchangeRates = new HashMap<>();
    private final List<CurrencyTransaction> recentTransactions = new ArrayList<>();

    public CurrencyExchange() {
        initializeExchangeRates();
        initializeRecentTransactions();
        setViewContent(createContent());
    }

    private void initializeExchangeRates() {
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.92);
        exchangeRates.put("GBP", 0.79);
        exchangeRates.put("JPY", 149.8);
        exchangeRates.put("CAD", 1.36);
        exchangeRates.put("AUD", 1.52);
        exchangeRates.put("CHF", 0.89);
        exchangeRates.put("CNY", 7.24);
        exchangeRates.put("HKD", 7.82);
        exchangeRates.put("SGD", 1.34);
    }

    private void initializeRecentTransactions() {
        String[] currencies = {"USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF", "CNY", "HKD", "SGD"};
        String[] statuses = {"Completed", "Pending", "Processing", "Failed"};
        String[] types = {"Spot", "Forward", "Swap"};
        
        LocalDate today = LocalDate.now();
        
        for (int i = 0; i < 20; i++) {
            String fromCurrency = currencies[random.nextInt(currencies.length)];
            String toCurrency;
            do {
                toCurrency = currencies[random.nextInt(currencies.length)];
            } while (toCurrency.equals(fromCurrency));
            
            double amount = 10000 + random.nextDouble() * 990000;
            amount = Math.round(amount * 100.0) / 100.0;
            
            double rate = 0.5 + random.nextDouble() * 1.5;
            rate = Math.round(rate * 10000.0) / 10000.0;
            
            double convertedAmount = amount * rate;
            convertedAmount = Math.round(convertedAmount * 100.0) / 100.0;
            
            String status = statuses[random.nextInt(statuses.length)];
            String type = types[random.nextInt(types.length)];
            
            LocalDate date = today.minusDays(random.nextInt(30));
            
            recentTransactions.add(new CurrencyTransaction(
                "TX-" + (100000 + i),
                fromCurrency,
                toCurrency,
                amount,
                rate,
                convertedAmount,
                status,
                type,
                date
            ));
        }
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
        fromCurrencyField.setItems(exchangeRates.keySet());
        fromCurrencyField.setValue("USD");
        
        ComboBox<String> toCurrencyField = new ComboBox<>("To Currency");
        toCurrencyField.setItems(exchangeRates.keySet());
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
            
            double fromRate = exchangeRates.get(fromCurrency);
            double toRate = exchangeRates.get(toCurrency);
            double rate = toRate / fromRate;
            
            double result = amount * rate;
            
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

        Grid<Map.Entry<String, Double>> grid = new Grid<>();
        grid.setItems(exchangeRates.entrySet());
        
        grid.addColumn(Map.Entry::getKey)
                .setHeader("Currency")
                .setAutoWidth(true);
        
        grid.addColumn(entry -> String.format("%.4f", entry.getValue()))
                .setHeader("Rate")
                .setAutoWidth(true);
        
        grid.addComponentColumn(entry -> {
            double change = (random.nextDouble() * 2 - 1) * 0.01; // -1% to +1%
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
        
        // Generate data for EUR, GBP, JPY
        ListSeries eurSeries = new ListSeries("EUR");
        ListSeries gbpSeries = new ListSeries("GBP");
        ListSeries jpySeries = new ListSeries("JPY (÷100)");
        
        double eurBase = 0.92;
        double gbpBase = 0.79;
        double jpyBase = 1.498; // JPY/100
        
        for (int i = 0; i < 30; i++) {
            double eurChange = 1 + (random.nextDouble() * 0.04 - 0.02); // -2% to +2%
            double gbpChange = 1 + (random.nextDouble() * 0.04 - 0.02);
            double jpyChange = 1 + (random.nextDouble() * 0.04 - 0.02);
            
            eurBase *= eurChange;
            gbpBase *= gbpChange;
            jpyBase *= jpyChange;
            
            eurSeries.addData(Math.round(eurBase * 10000) / 10000.0);
            gbpSeries.addData(Math.round(gbpBase * 10000) / 10000.0);
            jpySeries.addData(Math.round(jpyBase * 10000) / 10000.0);
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

        Grid<CurrencyTransaction> grid = new Grid<>();
        grid.setItems(recentTransactions);
        grid.setHeight("300px");
        
        grid.addColumn(CurrencyTransaction::getId)
                .setHeader("Transaction ID")
                .setAutoWidth(true);
        
        grid.addColumn(CurrencyTransaction::getDate)
                .setHeader("Date")
                .setAutoWidth(true);
        
        grid.addColumn(CurrencyTransaction::getType)
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

    // Inner class to represent a currency transaction
    private static class CurrencyTransaction {
        private final String id;
        private final String fromCurrency;
        private final String toCurrency;
        private final double amount;
        private final double rate;
        private final double convertedAmount;
        private final String status;
        private final String type;
        private final LocalDate date;

        public CurrencyTransaction(String id, String fromCurrency, String toCurrency, 
                                  double amount, double rate, double convertedAmount, 
                                  String status, String type, LocalDate date) {
            this.id = id;
            this.fromCurrency = fromCurrency;
            this.toCurrency = toCurrency;
            this.amount = amount;
            this.rate = rate;
            this.convertedAmount = convertedAmount;
            this.status = status;
            this.type = type;
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public String getFromCurrency() {
            return fromCurrency;
        }

        public String getToCurrency() {
            return toCurrency;
        }

        public double getAmount() {
            return amount;
        }

        public double getRate() {
            return rate;
        }

        public double getConvertedAmount() {
            return convertedAmount;
        }

        public String getStatus() {
            return status;
        }

        public String getType() {
            return type;
        }

        public String getDate() {
            return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }
}