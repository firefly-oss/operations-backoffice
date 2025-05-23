package com.vaadin.starter.business.ui.views.reportinganalytics;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.reportinganalytics.PerformanceMetricDTO;
import com.vaadin.starter.business.backend.service.ReportingAnalyticsService;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@PageTitle(NavigationConstants.PERFORMANCE_ANALYTICS)
@Route(value = "reporting-analytics/performance-analytics", layout = MainLayout.class)
public class PerformanceAnalytics extends ViewFrame {

    private final ReportingAnalyticsService reportingAnalyticsService;
    private Div contentArea;
    private Random random = new Random(42); // Fixed seed for reproducible data
    private Map<String, PerformanceMetricDTO> metricsCache = new HashMap<>();

    @Autowired
    public PerformanceAnalytics(ReportingAnalyticsService reportingAnalyticsService) {
        this.reportingAnalyticsService = reportingAnalyticsService;
        loadMetricsFromService();
        setViewContent(createContent());
    }

    private void loadMetricsFromService() {
        // Load metrics from service and cache them for use in various charts
        Collection<PerformanceMetricDTO> metrics = reportingAnalyticsService.getPerformanceMetrics();
        metricsCache = metrics.stream()
                .collect(Collectors.toMap(PerformanceMetricDTO::getMetricId, metric -> metric));
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createHeader(), createTabs());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createHeader() {
        H3 header = new H3("Performance Analytics");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createTabs() {
        Tab systemPerformanceTab = new Tab("System Performance");
        Tab transactionMetricsTab = new Tab("Transaction Metrics");
        Tab userActivityTab = new Tab("User Activity");
        Tab customDashboardTab = new Tab("Custom Dashboard");

        Tabs tabs = new Tabs(systemPerformanceTab, transactionMetricsTab, userActivityTab, customDashboardTab);
        tabs.getStyle().set("margin", "0 1rem");

        contentArea = new Div();
        contentArea.setSizeFull();
        contentArea.getStyle().set("padding", "1rem");

        // Show system performance tab by default
        showSystemPerformanceTab();

        tabs.addSelectedChangeListener(event -> {
            contentArea.removeAll();
            if (event.getSelectedTab().equals(systemPerformanceTab)) {
                showSystemPerformanceTab();
            } else if (event.getSelectedTab().equals(transactionMetricsTab)) {
                showTransactionMetricsTab();
            } else if (event.getSelectedTab().equals(userActivityTab)) {
                showUserActivityTab();
            } else if (event.getSelectedTab().equals(customDashboardTab)) {
                showCustomDashboardTab();
            }
        });

        VerticalLayout tabsLayout = new VerticalLayout(tabs, contentArea);
        tabsLayout.setPadding(false);
        tabsLayout.setSpacing(false);
        tabsLayout.setSizeFull();
        return tabsLayout;
    }

    private void showSystemPerformanceTab() {
        VerticalLayout systemLayout = new VerticalLayout();
        systemLayout.setPadding(false);
        systemLayout.setSpacing(true);
        systemLayout.setSizeFull();

        // Add filter controls
        HorizontalLayout filterLayout = createFilterControls();
        systemLayout.add(filterLayout);

        // Create dashboard using Board layout
        Board board = new Board();
        board.setSizeFull();

        // First row - Key metrics
        Component cpuCard = createMetricCard("CPU Utilization", "42%", "5% ↓", "var(--lumo-success-color)");
        Component memoryCard = createMetricCard("Memory Usage", "68%", "3% ↑", "var(--lumo-error-color)");
        Component diskCard = createMetricCard("Disk I/O", "156 MB/s", "12% ↑", "var(--lumo-error-color)");
        Component networkCard = createMetricCard("Network Traffic", "42 Mbps", "8% ↓", "var(--lumo-success-color)");

        Row metricsRow = board.addRow(cpuCard, memoryCard, diskCard, networkCard);
        metricsRow.setComponentSpan(cpuCard, 1);
        metricsRow.setComponentSpan(memoryCard, 1);
        metricsRow.setComponentSpan(diskCard, 1);
        metricsRow.setComponentSpan(networkCard, 1);

        // Second row - CPU and Memory charts
        Row chartsRow1 = board.addRow(
            createCpuUsageChart(),
            createMemoryUsageChart()
        );

        // Third row - Response time and Error rate
        Row chartsRow2 = board.addRow(
            createResponseTimeChart(),
            createErrorRateChart()
        );

        systemLayout.add(board);
        systemLayout.expand(board);

        contentArea.add(systemLayout);
    }

    private void showTransactionMetricsTab() {
        VerticalLayout transactionLayout = new VerticalLayout();
        transactionLayout.setPadding(false);
        transactionLayout.setSpacing(true);
        transactionLayout.setSizeFull();

        // Add filter controls
        HorizontalLayout filterLayout = createFilterControls();
        transactionLayout.add(filterLayout);

        // Create dashboard using Board layout
        Board board = new Board();
        board.setSizeFull();

        // First row - Key metrics
        Row metricsRow = board.addRow(
            createMetricCard("Transaction Volume", "12,456", "8% ↑", "var(--lumo-success-color)"),
            createMetricCard("Avg. Processing Time", "1.2s", "0.3s ↓", "var(--lumo-success-color)"),
            createMetricCard("Success Rate", "99.7%", "0.2% ↑", "var(--lumo-success-color)"),
            createMetricCard("Pending Transactions", "42", "5 ↓", "var(--lumo-success-color)")
        );

        // Second row - Transaction volume and processing time
        Row chartsRow1 = board.addRow(
            createTransactionVolumeChart(),
            createTransactionTypeChart()
        );

        // Third row - Success rate and transaction by channel
        Row chartsRow2 = board.addRow(
            createProcessingTimeChart(),
            createTransactionByChannelChart()
        );

        transactionLayout.add(board);
        transactionLayout.expand(board);

        contentArea.add(transactionLayout);
    }

    private void showUserActivityTab() {
        VerticalLayout userActivityLayout = new VerticalLayout();
        userActivityLayout.setPadding(false);
        userActivityLayout.setSpacing(true);
        userActivityLayout.setSizeFull();

        // Add filter controls
        HorizontalLayout filterLayout = createFilterControls();
        userActivityLayout.add(filterLayout);

        // Create dashboard using Board layout
        Board board = new Board();
        board.setSizeFull();

        // First row - Key metrics
        Row metricsRow = board.addRow(
            createMetricCard("Active Users", "1,245", "156 ↑", "var(--lumo-success-color)"),
            createMetricCard("Avg. Session Duration", "18m 42s", "2m ↑", "var(--lumo-success-color)"),
            createMetricCard("Login Success Rate", "98.5%", "0.5% ↓", "var(--lumo-error-color)"),
            createMetricCard("New Users", "87", "12 ↑", "var(--lumo-success-color)")
        );

        // Second row - User activity and session duration
        Row chartsRow1 = board.addRow(
            createActiveUsersChart(),
            createUsersByRoleChart()
        );

        // Third row - Login attempts and user actions
        Row chartsRow2 = board.addRow(
            createSessionDurationChart(),
            createUserActionsChart()
        );

        userActivityLayout.add(board);
        userActivityLayout.expand(board);

        contentArea.add(userActivityLayout);
    }

    private void showCustomDashboardTab() {
        VerticalLayout customDashboardLayout = new VerticalLayout();
        customDashboardLayout.setPadding(false);
        customDashboardLayout.setSpacing(true);
        customDashboardLayout.setSizeFull();

        // Add dashboard configuration controls
        HorizontalLayout configLayout = new HorizontalLayout();
        configLayout.setWidthFull();
        configLayout.setSpacing(true);
        configLayout.setPadding(true);
        configLayout.getStyle().set("background-color", "var(--lumo-base-color)");
        configLayout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        configLayout.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        configLayout.setAlignItems(Alignment.END); // Align items at the bottom

        ComboBox<String> addWidgetComboBox = new ComboBox<>("Add Widget");
        addWidgetComboBox.setItems("CPU Usage", "Memory Usage", "Disk I/O", "Network Traffic", 
                                  "Transaction Volume", "Processing Time", "Success Rate", 
                                  "Active Users", "Session Duration", "User Actions");

        // Create buttons
        Button addWidgetButton = UIUtils.createPrimaryButton("Add");
        addWidgetButton.setIcon(VaadinIcon.PLUS.create());

        Button saveLayoutButton = UIUtils.createTertiaryButton("Save Layout");
        saveLayoutButton.setIcon(VaadinIcon.DOWNLOAD.create());

        Button resetLayoutButton = UIUtils.createTertiaryButton("Reset");
        resetLayoutButton.setIcon(VaadinIcon.REFRESH.create());

        // Create a horizontal layout for all three buttons
        HorizontalLayout buttonsLayout = new HorizontalLayout(addWidgetButton, saveLayoutButton, resetLayoutButton);
        buttonsLayout.setSpacing(true);

        // Create vertical layout with empty label for alignment
        VerticalLayout buttonsVerticalLayout = new VerticalLayout();
        buttonsVerticalLayout.setPadding(false);
        buttonsVerticalLayout.setSpacing(false);
        Span buttonLabel = new Span(" "); // Empty label for spacing
        buttonsVerticalLayout.add(buttonLabel, buttonsLayout);

        configLayout.add(addWidgetComboBox, buttonsVerticalLayout);

        customDashboardLayout.add(configLayout);

        // Create a placeholder for the custom dashboard
        Div placeholderDiv = new Div();
        placeholderDiv.setSizeFull();
        placeholderDiv.getStyle().set("display", "flex");
        placeholderDiv.getStyle().set("align-items", "center");
        placeholderDiv.getStyle().set("justify-content", "center");
        placeholderDiv.getStyle().set("background-color", "var(--lumo-base-color)");
        placeholderDiv.getStyle().set("border-radius", "var(--lumo-border-radius)");
        placeholderDiv.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");

        H4 placeholderText = new H4("Select widgets to create your custom dashboard");
        placeholderText.getStyle().set("color", "var(--lumo-secondary-text-color)");

        placeholderDiv.add(placeholderText);

        customDashboardLayout.add(placeholderDiv);
        customDashboardLayout.expand(placeholderDiv);

        contentArea.add(customDashboardLayout);
    }

    private HorizontalLayout createFilterControls() {
        HorizontalLayout filterLayout = new HorizontalLayout();
        filterLayout.setWidthFull();
        filterLayout.setSpacing(true);
        filterLayout.setPadding(true);
        filterLayout.getStyle().set("background-color", "var(--lumo-base-color)");
        filterLayout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        filterLayout.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        filterLayout.setAlignItems(Alignment.END); // Align items at the bottom

        DatePicker startDate = new DatePicker("Start Date");
        startDate.setValue(LocalDate.now().minusDays(30));

        DatePicker endDate = new DatePicker("End Date");
        endDate.setValue(LocalDate.now());

        ComboBox<String> intervalComboBox = new ComboBox<>("Interval");
        intervalComboBox.setItems("Hourly", "Daily", "Weekly", "Monthly");
        intervalComboBox.setValue("Daily");

        ComboBox<String> serverComboBox = new ComboBox<>("Server");
        serverComboBox.setItems("All Servers", "Server 1", "Server 2", "Server 3", "Server 4");
        serverComboBox.setValue("All Servers");

        // Create a horizontal layout for both buttons
        Button refreshButton = UIUtils.createPrimaryButton("Refresh");
        refreshButton.setIcon(VaadinIcon.REFRESH.create());

        Button exportButton = UIUtils.createTertiaryButton("Export");
        exportButton.setIcon(VaadinIcon.DOWNLOAD.create());

        HorizontalLayout buttonsHorizontalLayout = new HorizontalLayout(refreshButton, exportButton);
        buttonsHorizontalLayout.setSpacing(true);

        // Create vertical layout with label for alignment
        VerticalLayout buttonsVerticalLayout = new VerticalLayout();
        buttonsVerticalLayout.setPadding(false);
        buttonsVerticalLayout.setSpacing(false);
        Span buttonLabel = new Span("Actions"); // Label for alignment with combobox labels
        buttonLabel.getStyle().set("font-size", "var(--lumo-font-size-s)");
        buttonLabel.getStyle().set("color", "var(--lumo-secondary-text-color)");
        buttonLabel.getStyle().set("font-weight", "500");
        buttonLabel.getStyle().set("padding-bottom", "0.5em");
        buttonsVerticalLayout.add(buttonLabel, buttonsHorizontalLayout);

        filterLayout.add(startDate, endDate, intervalComboBox, serverComboBox, buttonsVerticalLayout);

        return filterLayout;
    }

    private Component createMetricCard(String title, String value, String change, String changeColor) {
        // Try to get metric data from service
        PerformanceMetricDTO metric = getMetricByName(title);

        if (metric != null) {
            value = metric.getValue() + (metric.getUnit() != null ? " " + metric.getUnit() : "");
            change = metric.getChange();
            changeColor = "up".equals(metric.getChangeDirection()) ? 
                "var(--lumo-success-color)" : "var(--lumo-error-color)";
        }

        Div card = new Div();
        card.getStyle().set("background-color", "var(--lumo-base-color)");
        card.getStyle().set("border-radius", "var(--lumo-border-radius)");
        card.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");
        card.getStyle().set("padding", "1.5rem");
        card.getStyle().set("text-align", "center");

        H4 titleElement = new H4(title);
        titleElement.getStyle().set("margin-top", "0");
        titleElement.getStyle().set("margin-bottom", "0.5rem");
        titleElement.getStyle().set("font-size", "1rem");

        Span valueElement = new Span(value);
        valueElement.getStyle().set("font-size", "2rem");
        valueElement.getStyle().set("font-weight", "bold");
        valueElement.getStyle().set("display", "block");
        valueElement.getStyle().set("margin-bottom", "0.5rem");

        Span changeElement = new Span(change);
        changeElement.getStyle().set("color", changeColor);
        changeElement.getStyle().set("font-weight", "500");

        card.add(titleElement, valueElement, changeElement);
        return card;
    }

    private Chart createCpuUsageChart() {
        Chart chart = new Chart(ChartType.AREASPLINE);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("CPU Usage (%)");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("00:00", "02:00", "04:00", "06:00", "08:00", "10:00", "12:00", 
                           "14:00", "16:00", "18:00", "20:00", "22:00");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Usage (%)");
        yAxis.setMin(0);
        yAxis.setMax(100);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix("%");
        conf.setTooltip(tooltip);

        PlotOptionsSpline plotOptions = new PlotOptionsSpline();
        plotOptions.setMarker(new com.vaadin.flow.component.charts.model.Marker(false));
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("CPU Usage");
        series.setData(generateRandomData(12, 20, 80));
        conf.addSeries(series);

        return chart;
    }

    private Chart createMemoryUsageChart() {
        Chart chart = new Chart(ChartType.AREASPLINE);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-success-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Memory Usage (GB)");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("00:00", "02:00", "04:00", "06:00", "08:00", "10:00", "12:00", 
                           "14:00", "16:00", "18:00", "20:00", "22:00");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Usage (GB)");
        yAxis.setMin(0);
        yAxis.setMax(32);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix(" GB");
        conf.setTooltip(tooltip);

        PlotOptionsSpline plotOptions = new PlotOptionsSpline();
        plotOptions.setMarker(new com.vaadin.flow.component.charts.model.Marker(false));
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("Memory Usage");
        series.setData(generateRandomData(12, 12, 24));
        conf.addSeries(series);

        return chart;
    }

    private Chart createResponseTimeChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Average Response Time (ms)");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Login", "Search", "Transaction", "Report", "Dashboard", "Profile", "Settings");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Time (ms)");
        yAxis.setMin(0);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix(" ms");
        conf.setTooltip(tooltip);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("Response Time");
        series.setData(generateRandomData(7, 50, 500));
        conf.addSeries(series);

        return chart;
    }

    private Chart createErrorRateChart() {
        Chart chart = new Chart(ChartType.SPLINE);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-error-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Error Rate (%)");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("00:00", "02:00", "04:00", "06:00", "08:00", "10:00", "12:00", 
                           "14:00", "16:00", "18:00", "20:00", "22:00");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Error Rate (%)");
        yAxis.setMin(0);
        yAxis.setMax(5);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix("%");
        conf.setTooltip(tooltip);

        ListSeries series = new ListSeries("Error Rate");
        series.setData(generateRandomData(12, 0.1, 2.5));
        conf.addSeries(series);

        return chart;
    }

    private Chart createTransactionVolumeChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Transaction Volume by Day");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        List<String> days = new ArrayList<>();
        LocalDate date = LocalDate.now().minusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM d");

        for (int i = 0; i < 7; i++) {
            days.add(date.format(formatter));
            date = date.plusDays(1);
        }

        xAxis.setCategories(days.toArray(new String[0]));
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Number of Transactions");
        yAxis.setMin(0);
        conf.addyAxis(yAxis);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("Transactions");
        series.setData(generateRandomData(7, 800, 1500));
        conf.addSeries(series);

        return chart;
    }

    private Chart createTransactionTypeChart() {
        Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Transactions by Type");
        conf.getChart().setStyledMode(true);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
        tooltip.setValueSuffix("%");
        conf.setTooltip(tooltip);

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(com.vaadin.flow.component.charts.model.Cursor.POINTER);
        plotOptions.setShowInLegend(true);
        conf.setPlotOptions(plotOptions);

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Payments", 45.0));
        series.add(new DataSeriesItem("Transfers", 26.8));
        series.add(new DataSeriesItem("Deposits", 12.8));
        series.add(new DataSeriesItem("Withdrawals", 8.5));
        series.add(new DataSeriesItem("Currency Exchange", 6.9));

        conf.addSeries(series);

        return chart;
    }

    private Chart createProcessingTimeChart() {
        Chart chart = new Chart(ChartType.SPLINE);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-success-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Average Processing Time (s)");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        List<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Month month = Month.of(i % 12 + 1);
            months.add(month.toString().substring(0, 3));
        }

        xAxis.setCategories(months.toArray(new String[0]));
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Time (s)");
        yAxis.setMin(0);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix(" s");
        conf.setTooltip(tooltip);

        ListSeries series = new ListSeries("Processing Time");
        series.setData(generateRandomData(12, 0.8, 2.5));
        conf.addSeries(series);

        return chart;
    }

    private Chart createTransactionByChannelChart() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Transactions by Channel");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Mobile App", "Web", "Branch", "ATM", "API", "Phone");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Number of Transactions");
        yAxis.setMin(0);
        conf.addyAxis(yAxis);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("Transactions");
        series.setData(5842, 4215, 1253, 2184, 3842, 684);
        conf.addSeries(series);

        return chart;
    }

    private Chart createActiveUsersChart() {
        Chart chart = new Chart(ChartType.AREASPLINE);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Active Users by Hour");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("00:00", "02:00", "04:00", "06:00", "08:00", "10:00", "12:00", 
                           "14:00", "16:00", "18:00", "20:00", "22:00");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Number of Users");
        yAxis.setMin(0);
        conf.addyAxis(yAxis);

        PlotOptionsSpline plotOptions = new PlotOptionsSpline();
        plotOptions.setMarker(new com.vaadin.flow.component.charts.model.Marker(false));
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("Active Users");
        series.setData(generateRandomData(12, 200, 1200));
        conf.addSeries(series);

        return chart;
    }

    private Chart createUsersByRoleChart() {
        Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Users by Role");
        conf.getChart().setStyledMode(true);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
        tooltip.setValueSuffix("%");
        conf.setTooltip(tooltip);

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(com.vaadin.flow.component.charts.model.Cursor.POINTER);
        plotOptions.setShowInLegend(true);
        conf.setPlotOptions(plotOptions);

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Customer Service", 35.0));
        series.add(new DataSeriesItem("Operations", 22.8));
        series.add(new DataSeriesItem("Management", 8.5));
        series.add(new DataSeriesItem("Finance", 18.2));
        series.add(new DataSeriesItem("IT", 12.3));
        series.add(new DataSeriesItem("Admin", 3.2));

        conf.addSeries(series);

        return chart;
    }

    private Chart createSessionDurationChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-success-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Average Session Duration by Role (minutes)");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Customer Service", "Operations", "Management", "Finance", "IT", "Admin");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Duration (minutes)");
        yAxis.setMin(0);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix(" min");
        conf.setTooltip(tooltip);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("Session Duration");
        series.setData(24.5, 18.2, 12.8, 22.3, 28.6, 15.4);
        conf.addSeries(series);

        return chart;
    }

    private Chart createUserActionsChart() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("User Actions by Type");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("View", "Create", "Update", "Delete", "Export", "Import", "Approve", "Reject");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Number of Actions");
        yAxis.setMin(0);
        conf.addyAxis(yAxis);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("Actions");
        series.setData(8452, 2184, 3842, 684, 1253, 542, 1842, 421);
        conf.addSeries(series);

        return chart;
    }

    private Number[] generateRandomData(int count, double min, double max) {
        // First check if we have data from the service
        if (!metricsCache.isEmpty()) {
            // Try to find a metric with historical data of the right size
            for (PerformanceMetricDTO metric : metricsCache.values()) {
                if (metric.getHistoricalData() != null && metric.getHistoricalData().length == count) {
                    return metric.getHistoricalData();
                }
            }
        }

        // Fall back to random data if no suitable metric data is found
        Number[] data = new Number[count];
        for (int i = 0; i < count; i++) {
            data[i] = min + (random.nextDouble() * (max - min));
        }
        return data;
    }

    private PerformanceMetricDTO getMetricByName(String metricName) {
        return metricsCache.values().stream()
                .filter(metric -> metric.getMetricName().equals(metricName))
                .findFirst()
                .orElse(null);
    }
}
