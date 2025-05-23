package com.vaadin.starter.business.ui.views.dashboard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.dto.dashboard.AlertDTO;
import com.vaadin.starter.business.backend.dto.dashboard.AlertSummaryDTO;
import com.vaadin.starter.business.backend.dto.dashboard.IncidentDTO;
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

@PageTitle(NavigationConstants.OPERATIONAL_ALERTS)
@Route(value = "dashboard/operational-alerts", layout = MainLayout.class)
public class OperationalAlerts extends ViewFrame {

    private static final String CLASS_NAME = "operational-alerts";
    public static final String MAX_WIDTH = "1024px";

    private final DashboardService dashboardService;

    @Autowired
    public OperationalAlerts(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
        setViewContent(createContent());
    }

    private Component createContent() {
        Component alertSummary = createAlertSummary();
        Component activeAlerts = createActiveAlerts();
        Component recentIncidents = createRecentIncidents();

        FlexBoxLayout content = new FlexBoxLayout(alertSummary, activeAlerts, recentIncidents);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.setFlexDirection(FlexDirection.COLUMN);
        return content;
    }

    private Component createAlertSummary() {
        FlexBoxLayout alertSummary = new FlexBoxLayout(
                createHeader(VaadinIcon.BELL, "Alert Summary"),
                createAlertSummaryCards());
        alertSummary.setBoxSizing(BoxSizing.BORDER_BOX);
        alertSummary.setDisplay(Display.BLOCK);
        alertSummary.setMargin(com.vaadin.starter.business.ui.layout.size.Top.L);
        alertSummary.setMaxWidth(MAX_WIDTH);
        alertSummary.setPadding(Horizontal.RESPONSIVE_L);
        alertSummary.setWidthFull();
        return alertSummary;
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

    private Component createAlertSummaryCards() {
        Row cards = new Row();
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, cards);
        UIUtils.setBorderRadius(BorderRadius.S, cards);
        UIUtils.setShadow(Shadow.XS, cards);

        List<AlertSummaryDTO> summaries = dashboardService.getAlertSummaries();
        for (AlertSummaryDTO summary : summaries) {
            cards.add(createAlertCard(summary.getSeverity(), summary.getCount(), summary.getColor()));
        }

        return cards;
    }

    private Component createAlertCard(String severity, int count, String color) {
        Icon icon = VaadinIcon.EXCLAMATION_CIRCLE.create();
        icon.setColor(color);
        icon.setSize("2em");

        H3 countLabel = new H3(String.valueOf(count));
        countLabel.getStyle().set("margin", "0.2em 0");

        Span severityLabel = new Span(severity);
        severityLabel.getStyle().set("font-weight", "bold");

        FlexBoxLayout card = new FlexBoxLayout(icon, countLabel, severityLabel);
        card.setFlexDirection(FlexDirection.COLUMN);
        card.setAlignItems(FlexComponent.Alignment.CENTER);
        card.setPadding(Uniform.M);
        card.setSpacing(Vertical.S);
        card.setHeight("120px");

        return card;
    }

    private Component createActiveAlerts() {
        FlexBoxLayout activeAlerts = new FlexBoxLayout(
                createHeader(VaadinIcon.BELL_O, "Active Alerts"),
                createActiveAlertsGrid());
        activeAlerts.setBoxSizing(BoxSizing.BORDER_BOX);
        activeAlerts.setDisplay(Display.BLOCK);
        activeAlerts.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        activeAlerts.setMaxWidth(MAX_WIDTH);
        activeAlerts.setPadding(Horizontal.RESPONSIVE_L);
        activeAlerts.setWidthFull();
        return activeAlerts;
    }

    private Component createActiveAlertsGrid() {
        Grid<AlertDTO> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeight("300px");

        grid.addColumn(AlertDTO::getTimestamp).setHeader("Timestamp").setFlexGrow(1);
        grid.addColumn(AlertDTO::getSeverity).setHeader("Severity").setFlexGrow(1);
        grid.addColumn(AlertDTO::getSystem).setHeader("System").setFlexGrow(1);
        grid.addColumn(AlertDTO::getMessage).setHeader("Message").setFlexGrow(3);

        List<AlertDTO> alerts = dashboardService.getActiveAlerts();
        grid.setItems(alerts);

        FlexBoxLayout card = new FlexBoxLayout(grid);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }


    private Component createRecentIncidents() {
        FlexBoxLayout recentIncidents = new FlexBoxLayout(
                createHeader(VaadinIcon.EXCLAMATION_CIRCLE_O, "Recent Incidents"),
                createRecentIncidentsGrid());
        recentIncidents.setBoxSizing(BoxSizing.BORDER_BOX);
        recentIncidents.setDisplay(Display.BLOCK);
        recentIncidents.setMargin(com.vaadin.starter.business.ui.layout.size.Top.XL);
        recentIncidents.setMaxWidth(MAX_WIDTH);
        recentIncidents.setPadding(Horizontal.RESPONSIVE_L);
        recentIncidents.setWidthFull();
        return recentIncidents;
    }

    private Component createRecentIncidentsGrid() {
        Grid<IncidentDTO> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeight("300px");

        grid.addColumn(IncidentDTO::getId).setHeader("ID").setFlexGrow(1);
        grid.addColumn(IncidentDTO::getStartTime).setHeader("Start Time").setFlexGrow(1);
        grid.addColumn(IncidentDTO::getEndTime).setHeader("End Time").setFlexGrow(1);
        grid.addColumn(IncidentDTO::getSystem).setHeader("System").setFlexGrow(1);
        grid.addColumn(IncidentDTO::getDescription).setHeader("Description").setFlexGrow(3);
        grid.addColumn(IncidentDTO::getStatus).setHeader("Status").setFlexGrow(1);

        List<IncidentDTO> incidents = dashboardService.getRecentIncidents();
        grid.setItems(incidents);

        FlexBoxLayout card = new FlexBoxLayout(grid);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }


}
