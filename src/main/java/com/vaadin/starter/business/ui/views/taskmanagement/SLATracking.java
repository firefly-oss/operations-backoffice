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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.taskmanagement.NotificationSettingDTO;
import com.vaadin.starter.business.backend.dto.taskmanagement.SLAPolicyDTO;
import com.vaadin.starter.business.backend.dto.taskmanagement.SLATaskDTO;
import com.vaadin.starter.business.backend.service.TaskManagementService;
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

@PageTitle(NavigationConstants.SLA_TRACKING)
@Route(value = "task-management/sla-tracking", layout = MainLayout.class)
public class SLATracking extends ViewFrame {

    private Div contentArea;
    private final TaskManagementService taskManagementService;

    public SLATracking(TaskManagementService taskManagementService) {
        this.taskManagementService = taskManagementService;
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
        H3 header = new H3("SLA Tracking");
        header.getStyle().set("margin", "0");
        header.getStyle().set("padding", "1rem");
        return header;
    }

    private Component createTabs() {
        Tab dashboardTab = new Tab("SLA Dashboard");
        Tab taskListTab = new Tab("SLA Task List");
        Tab configurationTab = new Tab("SLA Configuration");
        Tab reportsTab = new Tab("SLA Reports");

        Tabs tabs = new Tabs(dashboardTab, taskListTab, configurationTab, reportsTab);
        tabs.getStyle().set("margin", "0 1rem");

        contentArea = new Div();
        contentArea.setSizeFull();
        contentArea.getStyle().set("padding", "1rem");

        // Show dashboard tab by default
        showDashboardTab();

        tabs.addSelectedChangeListener(event -> {
            contentArea.removeAll();
            if (event.getSelectedTab().equals(dashboardTab)) {
                showDashboardTab();
            } else if (event.getSelectedTab().equals(taskListTab)) {
                showTaskListTab();
            } else if (event.getSelectedTab().equals(configurationTab)) {
                showConfigurationTab();
            } else if (event.getSelectedTab().equals(reportsTab)) {
                showReportsTab();
            }
        });

        VerticalLayout tabsLayout = new VerticalLayout(tabs, contentArea);
        tabsLayout.setPadding(false);
        tabsLayout.setSpacing(false);
        tabsLayout.setSizeFull();
        return tabsLayout;
    }

    private void showDashboardTab() {
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
        Component slaMetCard = createMetricCard("SLA Met", "87%", "2% ↑", "var(--lumo-success-color)");
        Component slaMissedCard = createMetricCard("SLA Missed", "13%", "2% ↓", "var(--lumo-success-color)");
        Component avgResolutionCard = createMetricCard("Avg. Resolution Time", "4.2 hrs", "0.3 hrs ↓", "var(--lumo-success-color)");
        Component atRiskCard = createMetricCard("Tasks at Risk", "24", "5 ↓", "var(--lumo-success-color)");

        Row metricsRow = board.addRow(slaMetCard, slaMissedCard, avgResolutionCard, atRiskCard);
        metricsRow.setComponentSpan(slaMetCard, 1);
        metricsRow.setComponentSpan(slaMissedCard, 1);
        metricsRow.setComponentSpan(avgResolutionCard, 1);
        metricsRow.setComponentSpan(atRiskCard, 1);

        // Second row - SLA Performance and Task Distribution
        Row chartsRow1 = board.addRow(
            createSLAPerformanceChart(),
            createTaskDistributionChart()
        );

        // Third row - Resolution Time and SLA Trend
        Row chartsRow2 = board.addRow(
            createResolutionTimeChart(),
            createSLATrendChart()
        );

        layout.add(board);
        layout.expand(board);

        contentArea.add(layout);
    }

    private void showTaskListTab() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setSizeFull();

        // Add filter controls
        HorizontalLayout filterLayout = new HorizontalLayout();
        filterLayout.setWidthFull();
        filterLayout.setPadding(true);
        filterLayout.setSpacing(true);
        filterLayout.getStyle().set("background-color", "var(--lumo-base-color)");
        filterLayout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        filterLayout.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");

        TextField searchField = new TextField();
        searchField.setPlaceholder("Search tasks...");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.setPlaceholder("SLA Status");
        statusFilter.setItems("All", "Met", "At Risk", "Breached");
        statusFilter.setValue("All");

        ComboBox<String> priorityFilter = new ComboBox<>();
        priorityFilter.setPlaceholder("Priority");
        priorityFilter.setItems("All", "High", "Medium", "Low");
        priorityFilter.setValue("All");

        ComboBox<String> typeFilter = new ComboBox<>();
        typeFilter.setPlaceholder("Type");
        typeFilter.setItems("All", "Customer Service", "Account Management", "Loan Processing", "Fraud Investigation", "Document Review");
        typeFilter.setValue("All");

        Button refreshButton = UIUtils.createPrimaryButton("Refresh");
        refreshButton.setIcon(VaadinIcon.REFRESH.create());

        // Create vertical layout with empty label for alignment
        VerticalLayout refreshButtonLayout = new VerticalLayout();
        refreshButtonLayout.setPadding(false);
        refreshButtonLayout.setSpacing(false);
        Span buttonLabel = new Span(" "); // Empty label for spacing
        refreshButtonLayout.add(buttonLabel, refreshButton);

        filterLayout.add(searchField, statusFilter, priorityFilter, typeFilter, refreshButtonLayout);

        // Create SLA task grid
        Grid<SLATaskDTO> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();

        grid.addColumn(SLATaskDTO::getId).setHeader("ID").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(SLATaskDTO::getSubject).setHeader("Subject").setAutoWidth(true).setFlexGrow(1);

        grid.addColumn(new ComponentRenderer<>(task -> {
            Span span = new Span(task.getSlaStatus());
            span.getElement().getThemeList().add("badge");

            switch (task.getSlaStatus()) {
                case "Met":
                    span.getElement().getThemeList().add("success");
                    break;
                case "At Risk":
                    span.getElement().getThemeList().add("contrast");
                    break;
                case "Breached":
                    span.getElement().getThemeList().add("error");
                    break;
            }

            return span;
        })).setHeader("SLA Status").setAutoWidth(true).setFlexGrow(0);

        grid.addColumn(new ComponentRenderer<>(task -> {
            Span span = new Span(task.getPriority());
            span.getElement().getThemeList().add("badge");

            switch (task.getPriority()) {
                case "High":
                    span.getElement().getThemeList().add("error");
                    break;
                case "Medium":
                    span.getElement().getThemeList().add("contrast");
                    break;
                case "Low":
                    span.getElement().getThemeList().add("success");
                    break;
            }

            return span;
        })).setHeader("Priority").setAutoWidth(true).setFlexGrow(0);

        grid.addColumn(SLATaskDTO::getType).setHeader("Type").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(SLATaskDTO::getAssignee).setHeader("Assignee").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(SLATaskDTO::getCreatedDate, "MMM dd, yyyy"))
            .setHeader("Created Date").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(SLATaskDTO::getSlaTarget).setHeader("SLA Target").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(SLATaskDTO::getTimeRemaining).setHeader("Time Remaining").setAutoWidth(true).setFlexGrow(0);

        grid.addColumn(new ComponentRenderer<>(task -> {
            HorizontalLayout actions = new HorizontalLayout();
            actions.setSpacing(true);

            Button viewButton = new Button(VaadinIcon.EYE.create());
            viewButton.getElement().setAttribute("title", "View task details");
            viewButton.addThemeVariants(com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL, com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY);

            Button escalateButton = new Button(VaadinIcon.ARROW_UP.create());
            escalateButton.getElement().setAttribute("title", "Escalate task");
            escalateButton.addThemeVariants(com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL, com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY);

            actions.add(viewButton, escalateButton);
            return actions;
        })).setHeader("Actions").setAutoWidth(true).setFlexGrow(0);

        // Load data
        grid.setItems(taskManagementService.getSLATasks(50));

        layout.add(filterLayout, grid);
        layout.expand(grid);

        contentArea.add(layout);
    }

    private void showConfigurationTab() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setSizeFull();

        // SLA Policy Configuration
        H4 policyHeader = new H4("SLA Policy Configuration");

        Grid<SLAPolicyDTO> policyGrid = new Grid<>();
        policyGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        policyGrid.addColumn(SLAPolicyDTO::getTaskType).setHeader("Task Type").setAutoWidth(true);
        policyGrid.addColumn(SLAPolicyDTO::getPriority).setHeader("Priority").setAutoWidth(true);
        policyGrid.addColumn(SLAPolicyDTO::getResponseTime).setHeader("Response Time").setAutoWidth(true);
        policyGrid.addColumn(SLAPolicyDTO::getResolutionTime).setHeader("Resolution Time").setAutoWidth(true);
        policyGrid.addColumn(SLAPolicyDTO::getEscalationTime).setHeader("Escalation Time").setAutoWidth(true);

        policyGrid.addColumn(new ComponentRenderer<>(policy -> {
            HorizontalLayout actions = new HorizontalLayout();
            actions.setSpacing(true);

            Button editButton = new Button(VaadinIcon.EDIT.create());
            editButton.getElement().setAttribute("title", "Edit policy");
            editButton.addThemeVariants(com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL, com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY);

            Button deleteButton = new Button(VaadinIcon.TRASH.create());
            deleteButton.getElement().setAttribute("title", "Delete policy");
            deleteButton.addThemeVariants(com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL, com.vaadin.flow.component.button.ButtonVariant.LUMO_ERROR, com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY);

            actions.add(editButton, deleteButton);
            return actions;
        })).setHeader("Actions").setAutoWidth(true);

        // Load data
        policyGrid.setItems(taskManagementService.getSLAPolicies());

        // Add new policy button
        Button addPolicyButton = UIUtils.createPrimaryButton("Add New Policy");
        addPolicyButton.setIcon(VaadinIcon.PLUS.create());

        // Notification settings
        H4 notificationHeader = new H4("SLA Notification Settings");

        Grid<NotificationSettingDTO> notificationGrid = new Grid<>();
        notificationGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        notificationGrid.addColumn(NotificationSettingDTO::getEvent).setHeader("Event").setAutoWidth(true);
        notificationGrid.addColumn(NotificationSettingDTO::getRecipients).setHeader("Recipients").setAutoWidth(true);
        notificationGrid.addColumn(NotificationSettingDTO::getChannel).setHeader("Channel").setAutoWidth(true);
        notificationGrid.addColumn(NotificationSettingDTO::getEnabled).setHeader("Enabled").setAutoWidth(true);

        notificationGrid.addColumn(new ComponentRenderer<>(setting -> {
            Button editButton = new Button(VaadinIcon.EDIT.create());
            editButton.getElement().setAttribute("title", "Edit notification");
            editButton.addThemeVariants(com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL, com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY);
            return editButton;
        })).setHeader("Actions").setAutoWidth(true);

        // Load data
        notificationGrid.setItems(taskManagementService.getNotificationSettings());

        // Add new notification button
        Button addNotificationButton = UIUtils.createPrimaryButton("Add Notification");
        addNotificationButton.setIcon(VaadinIcon.PLUS.create());

        // Business hours configuration
        H4 businessHoursHeader = new H4("Business Hours Configuration");

        HorizontalLayout businessHoursLayout = new HorizontalLayout();
        businessHoursLayout.setWidthFull();
        businessHoursLayout.setPadding(true);
        businessHoursLayout.setSpacing(true);
        businessHoursLayout.getStyle().set("background-color", "var(--lumo-base-color)");
        businessHoursLayout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        businessHoursLayout.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");

        ComboBox<String> startTimeCombo = new ComboBox<>("Start Time");
        startTimeCombo.setItems("8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM");
        startTimeCombo.setValue("9:00 AM");

        ComboBox<String> endTimeCombo = new ComboBox<>("End Time");
        endTimeCombo.setItems("5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM");
        endTimeCombo.setValue("5:00 PM");

        ComboBox<String> timeZoneCombo = new ComboBox<>("Time Zone");
        timeZoneCombo.setItems("UTC", "EST", "CST", "PST", "GMT");
        timeZoneCombo.setValue("EST");

        Button saveBusinessHoursButton = UIUtils.createPrimaryButton("Save");
        saveBusinessHoursButton.addClickListener(e -> 
            Notification.show("Business hours updated", 3000, Notification.Position.BOTTOM_CENTER)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS)
        );

        // Create vertical layout with label for alignment
        VerticalLayout saveButtonLayout = new VerticalLayout();
        saveButtonLayout.setPadding(false);
        saveButtonLayout.setSpacing(false);
        Span buttonLabel = new Span("Actions"); // Label for alignment with combobox labels
        buttonLabel.getStyle().set("font-size", "var(--lumo-font-size-s)");
        buttonLabel.getStyle().set("color", "var(--lumo-secondary-text-color)");
        buttonLabel.getStyle().set("font-weight", "500");
        buttonLabel.getStyle().set("padding-bottom", "0.5em");
        saveButtonLayout.add(buttonLabel, saveBusinessHoursButton);

        businessHoursLayout.add(startTimeCombo, endTimeCombo, timeZoneCombo, saveButtonLayout);

        // Add all components to the layout
        layout.add(policyHeader, policyGrid, addPolicyButton, notificationHeader, 
                  notificationGrid, addNotificationButton, businessHoursHeader, businessHoursLayout);

        contentArea.add(layout);
    }

    private void showReportsTab() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(true);
        layout.setSizeFull();

        // Report filters
        HorizontalLayout filterLayout = new HorizontalLayout();
        filterLayout.setWidthFull();
        filterLayout.setPadding(true);
        filterLayout.setSpacing(true);
        filterLayout.getStyle().set("background-color", "var(--lumo-base-color)");
        filterLayout.getStyle().set("border-radius", "var(--lumo-border-radius)");
        filterLayout.getStyle().set("box-shadow", "0 0 0 1px var(--lumo-contrast-10pct)");

        ComboBox<String> reportTypeCombo = new ComboBox<>("Report Type");
        reportTypeCombo.setItems("SLA Performance", "Team Performance", "Task Resolution Time", "SLA Breach Analysis");
        reportTypeCombo.setValue("SLA Performance");

        ComboBox<String> periodCombo = new ComboBox<>("Time Period");
        periodCombo.setItems("Today", "Yesterday", "Last 7 Days", "Last 30 Days", "Last Quarter", "Custom");
        periodCombo.setValue("Last 30 Days");

        ComboBox<String> teamCombo = new ComboBox<>("Team");
        teamCombo.setItems("All Teams", "Customer Support", "Operations", "Risk Management", "Document Processing");
        teamCombo.setValue("All Teams");

        Button generateButton = UIUtils.createPrimaryButton("Generate Report");
        generateButton.setIcon(VaadinIcon.CHART.create());

        Button exportButton = UIUtils.createTertiaryButton("Export");
        exportButton.setIcon(VaadinIcon.DOWNLOAD.create());

        // Create a horizontal layout for both buttons
        HorizontalLayout buttonsHorizontalLayout = new HorizontalLayout(generateButton, exportButton);
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

        filterLayout.add(reportTypeCombo, periodCombo, teamCombo, buttonsVerticalLayout);

        // Report content - using Board for layout
        Board board = new Board();
        board.setSizeFull();

        // First row - Summary metrics
        Component totalTasksCard = createMetricCard("Total Tasks", "1,245", "", "");
        Component slaMetCard = createMetricCard("SLA Met", "1,083 (87%)", "", "");
        Component slaMissedCard = createMetricCard("SLA Missed", "162 (13%)", "", "");
        Component avgResolutionCard = createMetricCard("Avg. Resolution", "4.2 hrs", "", "");

        Row metricsRow = board.addRow(totalTasksCard, slaMetCard, slaMissedCard, avgResolutionCard);

        // Second row - SLA Performance by Team and Task Type
        Row chartsRow1 = board.addRow(
            createSLAByTeamChart(),
            createSLAByTaskTypeChart()
        );

        // Third row - SLA Trend and Resolution Time Distribution
        Row chartsRow2 = board.addRow(
            createSLATrendReportChart(),
            createResolutionTimeDistributionChart()
        );

        layout.add(filterLayout, board);
        layout.expand(board);

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

        ComboBox<String> taskTypeCombo = new ComboBox<>("Task Type");
        taskTypeCombo.setItems("All Types", "Customer Service", "Account Management", "Loan Processing", "Fraud Investigation", "Document Review");
        taskTypeCombo.setValue("All Types");

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

        filterLayout.add(periodCombo, teamCombo, taskTypeCombo, buttonsVerticalLayout);
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

    private Chart createSLAPerformanceChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-success-color)");
        chart.getElement().getStyle().set("--vaadin-charts-color-2", "var(--lumo-error-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("SLA Performance by Priority");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("High", "Medium", "Low");
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

        ListSeries metSeries = new ListSeries("SLA Met");
        metSeries.setData(120, 350, 450);

        ListSeries missedSeries = new ListSeries("SLA Missed");
        missedSeries.setData(30, 45, 25);

        conf.addSeries(metSeries);
        conf.addSeries(missedSeries);

        return chart;
    }

    private Chart createTaskDistributionChart() {
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
        series.add(new DataSeriesItem("Customer Service", 35.0));
        series.add(new DataSeriesItem("Account Management", 22.8));
        series.add(new DataSeriesItem("Loan Processing", 18.5));
        series.add(new DataSeriesItem("Fraud Investigation", 15.2));
        series.add(new DataSeriesItem("Document Review", 8.5));

        conf.addSeries(series);

        return chart;
    }

    private Chart createResolutionTimeChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Average Resolution Time by Task Type (hours)");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Customer Service", "Account Management", "Loan Processing", "Fraud Investigation", "Document Review");
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
        series.setData(3.2, 5.8, 8.4, 12.6, 4.5);
        conf.addSeries(series);

        return chart;
    }

    private Chart createSLATrendChart() {
        Chart chart = new Chart(ChartType.SPLINE);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-success-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("SLA Performance Trend (Last 30 Days)");
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
        yAxis.setTitle("SLA Met (%)");
        yAxis.setMin(70);
        yAxis.setMax(100);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix("%");
        conf.setTooltip(tooltip);

        ListSeries series = new ListSeries("SLA Met");
        Number[] data = generateRandomData(30, 75, 95);
        series.setData(data);
        conf.addSeries(series);

        return chart;
    }

    private Chart createSLAByTeamChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-success-color)");
        chart.getElement().getStyle().set("--vaadin-charts-color-2", "var(--lumo-error-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("SLA Performance by Team");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Customer Support", "Operations", "Risk Management", "Document Processing");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Percentage (%)");
        yAxis.setMin(0);
        yAxis.setMax(100);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix("%");
        conf.setTooltip(tooltip);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries metSeries = new ListSeries("SLA Met");
        metSeries.setData(92, 85, 78, 88);

        ListSeries missedSeries = new ListSeries("SLA Missed");
        missedSeries.setData(8, 15, 22, 12);

        conf.addSeries(metSeries);
        conf.addSeries(missedSeries);

        return chart;
    }

    private Chart createSLAByTaskTypeChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-success-color)");
        chart.getElement().getStyle().set("--vaadin-charts-color-2", "var(--lumo-error-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("SLA Performance by Task Type");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Customer Service", "Account Management", "Loan Processing", "Fraud Investigation", "Document Review");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Percentage (%)");
        yAxis.setMin(0);
        yAxis.setMax(100);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix("%");
        conf.setTooltip(tooltip);

        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        plotOptions.setBorderRadius(4);
        conf.setPlotOptions(plotOptions);

        ListSeries metSeries = new ListSeries("SLA Met");
        metSeries.setData(90, 88, 82, 75, 92);

        ListSeries missedSeries = new ListSeries("SLA Missed");
        missedSeries.setData(10, 12, 18, 25, 8);

        conf.addSeries(metSeries);
        conf.addSeries(missedSeries);

        return chart;
    }

    private Chart createSLATrendReportChart() {
        Chart chart = new Chart(ChartType.AREASPLINE);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-success-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("SLA Performance Trend (Last 12 Weeks)");
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
        yAxis.setTitle("SLA Met (%)");
        yAxis.setMin(70);
        yAxis.setMax(100);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix("%");
        conf.setTooltip(tooltip);

        ListSeries series = new ListSeries("SLA Met");
        Number[] data = {82, 84, 86, 85, 88, 90, 92, 91, 89, 87, 88, 90};
        series.setData(data);
        conf.addSeries(series);

        return chart;
    }

    private Chart createResolutionTimeDistributionChart() {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.getElement().getStyle().set("--vaadin-charts-color-1", "var(--lumo-primary-color)");

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Resolution Time Distribution");
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("< 1 hour", "1-4 hours", "4-8 hours", "8-24 hours", "> 24 hours");
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

        ListSeries series = new ListSeries("Tasks");
        series.setData(245, 385, 320, 180, 115);
        conf.addSeries(series);

        return chart;
    }


    private Number[] generateRandomData(int count, double min, double max) {
        return taskManagementService.generateRandomData(count, min, max);
    }
}
