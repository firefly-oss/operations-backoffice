package com.vaadin.starter.business.ui.views.taskmanagement;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.constants.NavigationConstants;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@PageTitle(NavigationConstants.TEAM_PERFORMANCE)
@Route(value = "task-management/team-performance", layout = MainLayout.class)
public class TeamPerformance extends ViewFrame {

    private Div contentArea;
    private Random random = new Random(42); // Fixed seed for reproducible data

    public TeamPerformance() {
        setViewContent(createContent());
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
        H3 header = new H3("Team Performance");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createTabs() {
        Tab overviewTab = new Tab("Performance Overview");
        Tab individualTab = new Tab("Individual Performance");
        Tab teamComparisonTab = new Tab("Team Comparison");
        Tab trendsTab = new Tab("Performance Trends");

        Tabs tabs = new Tabs(overviewTab, individualTab, teamComparisonTab, trendsTab);
        tabs.getStyle().set("margin", "0 1rem");

        contentArea = new Div();
        contentArea.setSizeFull();
        contentArea.getStyle().set("padding", "1rem");

        // Show overview tab by default
        showOverviewTab();

        tabs.addSelectedChangeListener(event -> {
            contentArea.removeAll();
            if (event.getSelectedTab().equals(overviewTab)) {
                showOverviewTab();
            } else if (event.getSelectedTab().equals(individualTab)) {
                showIndividualTab();
            } else if (event.getSelectedTab().equals(teamComparisonTab)) {
                showTeamComparisonTab();
            } else if (event.getSelectedTab().equals(trendsTab)) {
                showTrendsTab();
            }
        });

        VerticalLayout tabsLayout = new VerticalLayout(tabs, contentArea);
        tabsLayout.setPadding(false);
        tabsLayout.setSpacing(false);
        tabsLayout.setSizeFull();
        return tabsLayout;
    }

    private void showOverviewTab() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setSizeFull();

        // Add filter controls
        HorizontalLayout filterLayout = createFilterControls();
        layout.add(filterLayout);

        // Create dashboard using Board layout
        Board board = new Board();
        board.setSizeFull();

        // First row - Key metrics
        Component tasksCompletedCard = createMetricCard("Tasks Completed", "1,245", "8% ↑", "var(--lumo-success-color)");
        Component avgResolutionCard = createMetricCard("Avg. Resolution Time", "4.2 hrs", "0.3 hrs ↓", "var(--lumo-success-color)");
        Component slaComplianceCard = createMetricCard("SLA Compliance", "87%", "2% ↑", "var(--lumo-success-color)");
        Component customerSatisfactionCard = createMetricCard("Customer Satisfaction", "4.7/5", "0.2 ↑", "var(--lumo-success-color)");

        Row metricsRow = board.addRow(tasksCompletedCard, avgResolutionCard, slaComplianceCard, customerSatisfactionCard);
        metricsRow.setComponentSpan(tasksCompletedCard, 1);
        metricsRow.setComponentSpan(avgResolutionCard, 1);
        metricsRow.setComponentSpan(slaComplianceCard, 1);
        metricsRow.setComponentSpan(customerSatisfactionCard, 1);

        // Second row - Team Performance and Task Distribution
        Row chartsRow1 = board.addRow(
            createTeamPerformanceChart(),
            createTaskDistributionChart()
        );

        // Third row - Resolution Time and Trend
        Row chartsRow2 = board.addRow(
            createResolutionTimeChart(),
            createPerformanceTrendChart()
        );

        layout.add(board);
        layout.expand(board);

        contentArea.add(layout);
    }

    private void showIndividualTab() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setSizeFull();

        // Team member selection
        HorizontalLayout selectionLayout = new HorizontalLayout();
        selectionLayout.setWidthFull();
        selectionLayout.setPadding(true);
        selectionLayout.setSpacing(true);
        selectionLayout.getStyle().set("background-color", "var(--lumo-base-color)");
        selectionLayout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        selectionLayout.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");

        ComboBox<String> teamCombo = new ComboBox<>("Team");
        teamCombo.setItems("All Teams", "Customer Support", "Operations", "Risk Management", "Document Processing");
        teamCombo.setValue("All Teams");

        ComboBox<String> memberCombo = new ComboBox<>("Team Member");
        memberCombo.setItems("All Members", "John Smith", "Maria Garcia", "Ahmed Khan", "Sarah Johnson", 
                            "Michael Brown", "Lisa Wong", "David Miller", "Emma Wilson");
        memberCombo.setValue("All Members");

        ComboBox<String> periodCombo = new ComboBox<>("Time Period");
        periodCombo.setItems("Today", "Yesterday", "Last 7 Days", "Last 30 Days", "Last Quarter", "Custom");
        periodCombo.setValue("Last 30 Days");

        Button refreshButton = UIUtils.createPrimaryButton("Refresh");
        refreshButton.setIcon(VaadinIcon.REFRESH.create());

        // Create vertical layout with label for alignment
        VerticalLayout refreshButtonLayout = new VerticalLayout();
        refreshButtonLayout.setPadding(false);
        refreshButtonLayout.setSpacing(false);
        Span buttonLabel = new Span("Actions"); // Label for alignment with combobox labels
        buttonLabel.getStyle().set("font-size", "var(--lumo-font-size-s)");
        buttonLabel.getStyle().set("color", "var(--lumo-secondary-text-color)");
        buttonLabel.getStyle().set("font-weight", "500");
        buttonLabel.getStyle().set("padding-bottom", "0.5em");
        refreshButtonLayout.add(buttonLabel, refreshButton);

        selectionLayout.add(teamCombo, memberCombo, periodCombo, refreshButtonLayout);

        // Individual performance metrics
        Board board = new Board();

        // First row - Key metrics
        Component tasksCompletedCard = createMetricCard("Tasks Completed", "156", "12% ↑", "var(--lumo-success-color)");
        Component avgResolutionCard = createMetricCard("Avg. Resolution Time", "3.8 hrs", "0.4 hrs ↓", "var(--lumo-success-color)");
        Component slaComplianceCard = createMetricCard("SLA Compliance", "92%", "3% ↑", "var(--lumo-success-color)");
        Component customerSatisfactionCard = createMetricCard("Customer Satisfaction", "4.8/5", "0.3 ↑", "var(--lumo-success-color)");

        Row metricsRow = board.addRow(tasksCompletedCard, avgResolutionCard, slaComplianceCard, customerSatisfactionCard);

        // Second row - Performance charts
        Row chartsRow = board.addRow(
            createIndividualPerformanceChart(),
            createTaskTypeDistributionChart()
        );

        // Team member performance grid
        H4 performanceHeader = new H4("Team Member Performance");

        Grid<TeamMemberPerformance> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        grid.addColumn(TeamMemberPerformance::getName).setHeader("Name").setAutoWidth(true);
        grid.addColumn(TeamMemberPerformance::getTeam).setHeader("Team").setAutoWidth(true);
        grid.addColumn(TeamMemberPerformance::getTasksCompleted).setHeader("Tasks Completed").setAutoWidth(true);
        grid.addColumn(TeamMemberPerformance::getAvgResolutionTime).setHeader("Avg. Resolution Time").setAutoWidth(true);
        grid.addColumn(TeamMemberPerformance::getSlaCompliance).setHeader("SLA Compliance").setAutoWidth(true);
        grid.addColumn(TeamMemberPerformance::getCustomerSatisfaction).setHeader("Customer Satisfaction").setAutoWidth(true);
        grid.addColumn(TeamMemberPerformance::getEfficiencyScore).setHeader("Efficiency Score").setAutoWidth(true);

        grid.addColumn(new ComponentRenderer<>(member -> {
            // Create a progress bar to show performance score
            Div progressBarContainer = new Div();
            progressBarContainer.getStyle().set("width", "100%");
            progressBarContainer.getStyle().set("height", "20px");
            progressBarContainer.getStyle().set("background-color", "var(--lumo-contrast-10pct)");
            progressBarContainer.getStyle().set("border-radius", "var(--lumo-border-radius)");

            int performanceScore = member.getPerformanceScore();
            String color = performanceScore > 80 ? "var(--lumo-success-color)" : 
                          performanceScore > 60 ? "var(--lumo-primary-color)" : 
                          "var(--lumo-error-color)";

            Div progressBar = new Div();
            progressBar.getStyle().set("width", performanceScore + "%");
            progressBar.getStyle().set("height", "100%");
            progressBar.getStyle().set("background-color", color);
            progressBar.getStyle().set("border-radius", "var(--lumo-border-radius)");

            progressBarContainer.add(progressBar);

            HorizontalLayout layout1 = new HorizontalLayout(progressBarContainer, new Span(performanceScore + "%"));
            layout1.setAlignItems(com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER);
            layout1.setSpacing(true);

            return layout1;
        })).setHeader("Performance Score").setAutoWidth(true);

        // Add sample data
        List<TeamMemberPerformance> members = new ArrayList<>();
        members.add(new TeamMemberPerformance("John Smith", "Customer Support", 156, "3.8 hrs", "92%", "4.8/5", "High", 88));
        members.add(new TeamMemberPerformance("Maria Garcia", "Customer Support", 142, "4.1 hrs", "89%", "4.7/5", "High", 85));
        members.add(new TeamMemberPerformance("Ahmed Khan", "Operations", 128, "4.5 hrs", "85%", "4.5/5", "Medium", 78));
        members.add(new TeamMemberPerformance("Sarah Johnson", "Operations", 138, "4.2 hrs", "87%", "4.6/5", "High", 82));
        members.add(new TeamMemberPerformance("Michael Brown", "Risk Management", 112, "5.1 hrs", "82%", "4.4/5", "Medium", 75));
        members.add(new TeamMemberPerformance("Lisa Wong", "Risk Management", 124, "4.8 hrs", "84%", "4.5/5", "Medium", 77));
        members.add(new TeamMemberPerformance("David Miller", "Document Processing", 148, "3.9 hrs", "90%", "4.7/5", "High", 86));
        members.add(new TeamMemberPerformance("Emma Wilson", "Document Processing", 132, "4.3 hrs", "86%", "4.6/5", "Medium", 80));

        grid.setItems(members);

        layout.add(selectionLayout, board, performanceHeader, grid);
        layout.expand(grid);

        contentArea.add(layout);
    }

    private void showTeamComparisonTab() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setSizeFull();

        // Time period selection
        HorizontalLayout periodLayout = new HorizontalLayout();
        periodLayout.setWidthFull();
        periodLayout.setPadding(true);
        periodLayout.setSpacing(true);
        periodLayout.getStyle().set("background-color", "var(--lumo-base-color)");
        periodLayout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        periodLayout.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");

        ComboBox<String> periodCombo = new ComboBox<>("Time Period");
        periodCombo.setItems("Today", "Yesterday", "Last 7 Days", "Last 30 Days", "Last Quarter", "Custom");
        periodCombo.setValue("Last 30 Days");

        Button refreshButton = UIUtils.createPrimaryButton("Refresh");
        refreshButton.setIcon(VaadinIcon.REFRESH.create());

        Button exportButton = UIUtils.createTertiaryButton("Export");
        exportButton.setIcon(VaadinIcon.DOWNLOAD.create());

        // Create a horizontal layout for both buttons
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

        periodLayout.add(periodCombo, buttonsVerticalLayout);

        // Team comparison charts
        Board board = new Board();

        // First row - Tasks completed and Resolution time
        Row row1 = board.addRow(
            createTeamTasksCompletedChart(),
            createTeamResolutionTimeChart()
        );

        // Second row - SLA compliance and Customer satisfaction
        Row row2 = board.addRow(
            createTeamSLAComplianceChart(),
            createTeamCustomerSatisfactionChart()
        );

        // Team performance grid
        H4 teamPerformanceHeader = new H4("Team Performance Comparison");

        Grid<TeamPerformanceData> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        grid.addColumn(TeamPerformanceData::getTeam).setHeader("Team").setAutoWidth(true);
        grid.addColumn(TeamPerformanceData::getMemberCount).setHeader("Team Size").setAutoWidth(true);
        grid.addColumn(TeamPerformanceData::getTasksCompleted).setHeader("Tasks Completed").setAutoWidth(true);
        grid.addColumn(TeamPerformanceData::getTasksPerMember).setHeader("Tasks/Member").setAutoWidth(true);
        grid.addColumn(TeamPerformanceData::getAvgResolutionTime).setHeader("Avg. Resolution Time").setAutoWidth(true);
        grid.addColumn(TeamPerformanceData::getSlaCompliance).setHeader("SLA Compliance").setAutoWidth(true);
        grid.addColumn(TeamPerformanceData::getCustomerSatisfaction).setHeader("Customer Satisfaction").setAutoWidth(true);
        grid.addColumn(TeamPerformanceData::getEfficiencyRank).setHeader("Efficiency Rank").setAutoWidth(true);

        // Add sample data
        List<TeamPerformanceData> teams = new ArrayList<>();
        teams.add(new TeamPerformanceData("Customer Support", 12, 1845, 153.8, "3.9 hrs", "91%", "4.7/5", "1"));
        teams.add(new TeamPerformanceData("Operations", 15, 1920, 128.0, "4.3 hrs", "86%", "4.5/5", "3"));
        teams.add(new TeamPerformanceData("Risk Management", 8, 945, 118.1, "4.9 hrs", "83%", "4.4/5", "4"));
        teams.add(new TeamPerformanceData("Document Processing", 10, 1420, 142.0, "4.1 hrs", "88%", "4.6/5", "2"));

        grid.setItems(teams);

        layout.add(periodLayout, board, teamPerformanceHeader, grid);
        layout.expand(grid);

        contentArea.add(layout);
    }

    private void showTrendsTab() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setSizeFull();

        // Filter controls
        HorizontalLayout filterLayout = new HorizontalLayout();
        filterLayout.setWidthFull();
        filterLayout.setPadding(true);
        filterLayout.setSpacing(true);
        filterLayout.getStyle().set("background-color", "var(--lumo-base-color)");
        filterLayout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        filterLayout.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");

        ComboBox<String> teamCombo = new ComboBox<>("Team");
        teamCombo.setItems("All Teams", "Customer Support", "Operations", "Risk Management", "Document Processing");
        teamCombo.setValue("All Teams");

        ComboBox<String> metricCombo = new ComboBox<>("Metric");
        metricCombo.setItems("Tasks Completed", "Resolution Time", "SLA Compliance", "Customer Satisfaction");
        metricCombo.setValue("Tasks Completed");

        ComboBox<String> periodCombo = new ComboBox<>("Time Range");
        periodCombo.setItems("Last 7 Days", "Last 30 Days", "Last Quarter", "Last Year");
        periodCombo.setValue("Last 30 Days");

        Button refreshButton = UIUtils.createPrimaryButton("Refresh");
        refreshButton.setIcon(VaadinIcon.REFRESH.create());

        // Create vertical layout with label for alignment
        VerticalLayout refreshButtonLayout = new VerticalLayout();
        refreshButtonLayout.setPadding(false);
        refreshButtonLayout.setSpacing(false);
        Span buttonLabel = new Span("Actions"); // Label for alignment with combobox labels
        buttonLabel.getStyle().set("font-size", "var(--lumo-font-size-s)");
        buttonLabel.getStyle().set("color", "var(--lumo-secondary-text-color)");
        buttonLabel.getStyle().set("font-weight", "500");
        buttonLabel.getStyle().set("padding-bottom", "0.5em");
        refreshButtonLayout.add(buttonLabel, refreshButton);

        filterLayout.add(teamCombo, metricCombo, periodCombo, refreshButtonLayout);

        // Trend charts
        Board board = new Board();

        // First row - Daily trend
        Row row1 = board.addRow(createDailyTrendChart());

        // Second row - Weekly and Monthly trends
        Row row2 = board.addRow(
            createWeeklyTrendChart(),
            createMonthlyTrendChart()
        );

        // Performance factors
        H4 factorsHeader = new H4("Performance Factors Analysis");

        Grid<PerformanceFactor> factorsGrid = new Grid<>();
        factorsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        factorsGrid.addColumn(PerformanceFactor::getFactor).setHeader("Factor").setAutoWidth(true);
        factorsGrid.addColumn(PerformanceFactor::getImpact).setHeader("Impact").setAutoWidth(true);
        factorsGrid.addColumn(PerformanceFactor::getTrend).setHeader("Trend").setAutoWidth(true);
        factorsGrid.addColumn(PerformanceFactor::getRecommendation).setHeader("Recommendation").setAutoWidth(true);

        // Add sample data
        List<PerformanceFactor> factors = new ArrayList<>();
        factors.add(new PerformanceFactor("Task Volume", "High", "Increasing", "Redistribute workload or increase staffing"));
        factors.add(new PerformanceFactor("Task Complexity", "Medium", "Stable", "Provide additional training for complex tasks"));
        factors.add(new PerformanceFactor("Team Size", "Medium", "Decreasing", "Review resource allocation"));
        factors.add(new PerformanceFactor("System Performance", "Low", "Improving", "Continue system optimization"));
        factors.add(new PerformanceFactor("Process Efficiency", "High", "Improving", "Document and share best practices"));

        factorsGrid.setItems(factors);

        layout.add(filterLayout, board, factorsHeader, factorsGrid);
        layout.expand(factorsGrid);

        contentArea.add(layout);
    }

    private HorizontalLayout createFilterControls() {
        HorizontalLayout filterLayout = new HorizontalLayout();
        filterLayout.setWidthFull();
        filterLayout.setPadding(true);
        filterLayout.setSpacing(true);
        filterLayout.getStyle().set("background-color", "var(--lumo-base-color)");
        filterLayout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        filterLayout.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");

        ComboBox<String> periodCombo = new ComboBox<>("Time Period");
        periodCombo.setItems("Today", "Yesterday", "Last 7 Days", "Last 30 Days", "Custom");
        periodCombo.setValue("Last 7 Days");

        ComboBox<String> teamCombo = new ComboBox<>("Team");
        teamCombo.setItems("All Teams", "Customer Support", "Operations", "Risk Management", "Document Processing");
        teamCombo.setValue("All Teams");

        Button refreshButton = UIUtils.createPrimaryButton("Refresh");
        refreshButton.setIcon(VaadinIcon.REFRESH.create());

        Button exportButton = UIUtils.createTertiaryButton("Export");
        exportButton.setIcon(VaadinIcon.DOWNLOAD.create());

        // Create a horizontal layout for both buttons
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

        filterLayout.add(periodCombo, teamCombo, buttonsVerticalLayout);
        return filterLayout;
    }

    private Component createMetricCard(String title, String value, String change, String changeColor) {
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

        card.add(titleElement, valueElement);

        if (!change.isEmpty()) {
            Span changeElement = new Span(change);
            changeElement.getStyle().set("color", changeColor);
            changeElement.getStyle().set("font-weight", "500");
            card.add(changeElement);
        }

        return card;
    }

    private Chart createTeamPerformanceChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Team Performance Score");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Customer Support", "Operations", "Risk Management", "Document Processing");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Performance Score");
        yAxis.setMin(0);
        yAxis.setMax(100);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        conf.setTooltip(tooltip);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("Performance Score");
        series.setData(87, 82, 75, 84);
        conf.addSeries(series);

        return chart;
    }

    private Chart createTaskDistributionChart() {
        Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Tasks by Team");
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
        series.add(new DataSeriesItem("Customer Support", 35.0));
        series.add(new DataSeriesItem("Operations", 28.5));
        series.add(new DataSeriesItem("Risk Management", 15.5));
        series.add(new DataSeriesItem("Document Processing", 21.0));

        conf.addSeries(series);

        return chart;
    }

    private Chart createResolutionTimeChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-success-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Average Resolution Time by Team (hours)");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Customer Support", "Operations", "Risk Management", "Document Processing");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Hours");
        yAxis.setMin(0);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix(" hrs");
        conf.setTooltip(tooltip);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("Resolution Time");
        series.setData(3.9, 4.3, 4.9, 4.1);
        conf.addSeries(series);

        return chart;
    }

    private Chart createPerformanceTrendChart() {
        Chart chart = new Chart(ChartType.SPLINE);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Performance Trend (Last 30 Days)");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        String[] days = new String[30];
        LocalDate date = LocalDate.now().minusDays(29);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d");

        for (int i = 0; i < 30; i++) {
            days[i] = date.format(formatter);
            date = date.plusDays(1);
        }

        xAxis.setCategories(days);
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Performance Score");
        yAxis.setMin(70);
        yAxis.setMax(100);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        conf.setTooltip(tooltip);

        ListSeries series = new ListSeries("Performance Score");
        Number[] data = generateRandomData(30, 75, 90);
        series.setData(data);
        conf.addSeries(series);

        return chart;
    }

    private Chart createIndividualPerformanceChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Performance Metrics");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Tasks Completed", "Avg. Resolution Time (hrs)", "SLA Compliance (%)", "Customer Satisfaction (x20)");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Value");
        yAxis.setMin(0);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        conf.setTooltip(tooltip);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries individualSeries = new ListSeries("John Smith");
        individualSeries.setData(156, 3.8, 92, 96); // Customer satisfaction multiplied by 20 for scale

        ListSeries teamAvgSeries = new ListSeries("Team Average");
        teamAvgSeries.setData(142, 4.1, 89, 94); // Customer satisfaction multiplied by 20 for scale

        conf.addSeries(individualSeries);
        conf.addSeries(teamAvgSeries);

        return chart;
    }

    private Chart createTaskTypeDistributionChart() {
        Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Tasks by Type");
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
        series.add(new DataSeriesItem("Account Verification", 28.0));
        series.add(new DataSeriesItem("Payment Issues", 22.5));
        series.add(new DataSeriesItem("Customer Inquiries", 32.0));
        series.add(new DataSeriesItem("Document Processing", 17.5));

        conf.addSeries(series);

        return chart;
    }

    private Chart createTeamTasksCompletedChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Tasks Completed by Team");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Customer Support", "Operations", "Risk Management", "Document Processing");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Number of Tasks");
        yAxis.setMin(0);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        conf.setTooltip(tooltip);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries totalSeries = new ListSeries("Total Tasks");
        totalSeries.setData(1845, 1920, 945, 1420);

        ListSeries perMemberSeries = new ListSeries("Tasks per Member");
        perMemberSeries.setData(153.8, 128.0, 118.1, 142.0);

        conf.addSeries(totalSeries);
        conf.addSeries(perMemberSeries);

        return chart;
    }

    private Chart createTeamResolutionTimeChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-success-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Average Resolution Time by Team");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Customer Support", "Operations", "Risk Management", "Document Processing");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Hours");
        yAxis.setMin(0);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix(" hrs");
        conf.setTooltip(tooltip);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("Resolution Time");
        series.setData(3.9, 4.3, 4.9, 4.1);
        conf.addSeries(series);

        return chart;
    }

    private Chart createTeamSLAComplianceChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("SLA Compliance by Team");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Customer Support", "Operations", "Risk Management", "Document Processing");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Compliance (%)");
        yAxis.setMin(0);
        yAxis.setMax(100);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix("%");
        conf.setTooltip(tooltip);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("SLA Compliance");
        series.setData(91, 86, 83, 88);
        conf.addSeries(series);

        return chart;
    }

    private Chart createTeamCustomerSatisfactionChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-success-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Customer Satisfaction by Team");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Customer Support", "Operations", "Risk Management", "Document Processing");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Rating (out of 5)");
        yAxis.setMin(0);
        yAxis.setMax(5);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
        conf.setTooltip(tooltip);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("Customer Satisfaction");
        series.setData(4.7, 4.5, 4.4, 4.6);
        conf.addSeries(series);

        return chart;
    }

    private Chart createDailyTrendChart() {
        Chart chart = new Chart(ChartType.SPLINE);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");
        chart.getElement().getStyle().set("--vaadin-charts-color-2", "var(--lumo-success-color)");
        chart.getElement().getStyle().set("--vaadin-charts-color-3", "var(--lumo-error-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Daily Performance Trend (Last 30 Days)");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        String[] days = new String[30];
        LocalDate date = LocalDate.now().minusDays(29);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d");

        for (int i = 0; i < 30; i++) {
            days[i] = date.format(formatter);
            date = date.plusDays(1);
        }

        xAxis.setCategories(days);
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Value");
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        conf.setTooltip(tooltip);

        ListSeries tasksSeries = new ListSeries("Tasks Completed");
        tasksSeries.setData(generateRandomData(30, 30, 60));

        ListSeries timeSeries = new ListSeries("Avg. Resolution Time (min)");
        timeSeries.setData(generateRandomData(30, 200, 300));

        ListSeries slaSeries = new ListSeries("SLA Compliance (%)");
        slaSeries.setData(generateRandomData(30, 75, 95));

        conf.addSeries(tasksSeries);
        conf.addSeries(timeSeries);
        conf.addSeries(slaSeries);

        return chart;
    }

    private Chart createWeeklyTrendChart() {
        Chart chart = new Chart(ChartType.SPLINE);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Weekly Performance Trend");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        String[] weeks = new String[12];
        LocalDate date = LocalDate.now().minusWeeks(11);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Week 'w");

        for (int i = 0; i < 12; i++) {
            weeks[i] = date.format(formatter);
            date = date.plusWeeks(1);
        }

        xAxis.setCategories(weeks);
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Performance Score");
        yAxis.setMin(70);
        yAxis.setMax(100);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        conf.setTooltip(tooltip);

        ListSeries series = new ListSeries("Performance Score");
        Number[] data = {78, 80, 82, 81, 83, 85, 87, 86, 88, 87, 89, 90};
        series.setData(data);
        conf.addSeries(series);

        return chart;
    }

    private Chart createMonthlyTrendChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Monthly Performance Trend");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Performance Score");
        yAxis.setMin(70);
        yAxis.setMax(100);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        conf.setTooltip(tooltip);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries series = new ListSeries("Performance Score");
        series.setData(76, 78, 80, 82, 83, 85, 87, 88, 86, 84, 87, 89);
        conf.addSeries(series);

        return chart;
    }

    private Number[] generateRandomData(int count, double min, double max) {
        Number[] data = new Number[count];
        for (int i = 0; i < count; i++) {
            data[i] = min + (random.nextDouble() * (max - min));
        }
        return data;
    }

    // Team Member Performance data class
    public static class TeamMemberPerformance {
        private String name;
        private String team;
        private int tasksCompleted;
        private String avgResolutionTime;
        private String slaCompliance;
        private String customerSatisfaction;
        private String efficiencyScore;
        private int performanceScore;

        public TeamMemberPerformance(String name, String team, int tasksCompleted, String avgResolutionTime,
                                    String slaCompliance, String customerSatisfaction, String efficiencyScore,
                                    int performanceScore) {
            this.name = name;
            this.team = team;
            this.tasksCompleted = tasksCompleted;
            this.avgResolutionTime = avgResolutionTime;
            this.slaCompliance = slaCompliance;
            this.customerSatisfaction = customerSatisfaction;
            this.efficiencyScore = efficiencyScore;
            this.performanceScore = performanceScore;
        }

        public String getName() { return name; }
        public String getTeam() { return team; }
        public int getTasksCompleted() { return tasksCompleted; }
        public String getAvgResolutionTime() { return avgResolutionTime; }
        public String getSlaCompliance() { return slaCompliance; }
        public String getCustomerSatisfaction() { return customerSatisfaction; }
        public String getEfficiencyScore() { return efficiencyScore; }
        public int getPerformanceScore() { return performanceScore; }
    }

    // Team Performance data class
    public static class TeamPerformanceData {
        private String team;
        private int memberCount;
        private int tasksCompleted;
        private double tasksPerMember;
        private String avgResolutionTime;
        private String slaCompliance;
        private String customerSatisfaction;
        private String efficiencyRank;

        public TeamPerformanceData(String team, int memberCount, int tasksCompleted, double tasksPerMember,
                                  String avgResolutionTime, String slaCompliance, String customerSatisfaction,
                                  String efficiencyRank) {
            this.team = team;
            this.memberCount = memberCount;
            this.tasksCompleted = tasksCompleted;
            this.tasksPerMember = tasksPerMember;
            this.avgResolutionTime = avgResolutionTime;
            this.slaCompliance = slaCompliance;
            this.customerSatisfaction = customerSatisfaction;
            this.efficiencyRank = efficiencyRank;
        }

        public String getTeam() { return team; }
        public int getMemberCount() { return memberCount; }
        public int getTasksCompleted() { return tasksCompleted; }
        public double getTasksPerMember() { return tasksPerMember; }
        public String getAvgResolutionTime() { return avgResolutionTime; }
        public String getSlaCompliance() { return slaCompliance; }
        public String getCustomerSatisfaction() { return customerSatisfaction; }
        public String getEfficiencyRank() { return efficiencyRank; }
    }

    // Performance Factor data class
    public static class PerformanceFactor {
        private String factor;
        private String impact;
        private String trend;
        private String recommendation;

        public PerformanceFactor(String factor, String impact, String trend, String recommendation) {
            this.factor = factor;
            this.impact = impact;
            this.trend = trend;
            this.recommendation = recommendation;
        }

        public String getFactor() { return factor; }
        public String getImpact() { return impact; }
        public String getTrend() { return trend; }
        public String getRecommendation() { return recommendation; }
    }
}
