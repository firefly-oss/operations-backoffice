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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@PageTitle(NavigationConstants.OPERATIONAL_ALERTS)
@Route(value = "dashboard/operational-alerts", layout = MainLayout.class)
public class OperationalAlerts extends ViewFrame {

    private static final String CLASS_NAME = "operational-alerts";
    public static final String MAX_WIDTH = "1024px";

    public OperationalAlerts() {
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

        cards.add(createAlertCard("Critical", 2, "red"));
        cards.add(createAlertCard("Warning", 5, "orange"));
        cards.add(createAlertCard("Info", 12, "blue"));
        cards.add(createAlertCard("Resolved", 8, "green"));

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
        Grid<Alert> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeight("300px");

        grid.addColumn(Alert::getTimestamp).setHeader("Timestamp").setFlexGrow(1);
        grid.addColumn(Alert::getSeverity).setHeader("Severity").setFlexGrow(1);
        grid.addColumn(Alert::getSystem).setHeader("System").setFlexGrow(1);
        grid.addColumn(Alert::getMessage).setHeader("Message").setFlexGrow(3);

        List<Alert> alerts = createMockAlerts();
        grid.setItems(alerts);

        FlexBoxLayout card = new FlexBoxLayout(grid);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }

    private List<Alert> createMockAlerts() {
        List<Alert> alerts = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        alerts.add(new Alert(
                LocalDateTime.now().minusMinutes(5).format(formatter),
                "Critical",
                "Payment Gateway",
                "Connection timeout to payment processor"
        ));
        
        alerts.add(new Alert(
                LocalDateTime.now().minusMinutes(15).format(formatter),
                "Critical",
                "Database Cluster",
                "High CPU usage on primary database node"
        ));
        
        alerts.add(new Alert(
                LocalDateTime.now().minusMinutes(30).format(formatter),
                "Warning",
                "API Gateway",
                "Increased latency in API responses"
        ));
        
        alerts.add(new Alert(
                LocalDateTime.now().minusMinutes(45).format(formatter),
                "Warning",
                "Authentication Service",
                "Increased failed login attempts"
        ));
        
        alerts.add(new Alert(
                LocalDateTime.now().minusHours(1).format(formatter),
                "Warning",
                "Storage System",
                "Disk space below 20% threshold"
        ));
        
        alerts.add(new Alert(
                LocalDateTime.now().minusHours(2).format(formatter),
                "Info",
                "Monitoring System",
                "Scheduled maintenance starting in 1 hour"
        ));
        
        alerts.add(new Alert(
                LocalDateTime.now().minusHours(3).format(formatter),
                "Info",
                "Load Balancer",
                "New instance added to server pool"
        ));
        
        return alerts;
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
        Grid<Incident> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeight("300px");

        grid.addColumn(Incident::getId).setHeader("ID").setFlexGrow(1);
        grid.addColumn(Incident::getStartTime).setHeader("Start Time").setFlexGrow(1);
        grid.addColumn(Incident::getEndTime).setHeader("End Time").setFlexGrow(1);
        grid.addColumn(Incident::getSystem).setHeader("System").setFlexGrow(1);
        grid.addColumn(Incident::getDescription).setHeader("Description").setFlexGrow(3);
        grid.addColumn(Incident::getStatus).setHeader("Status").setFlexGrow(1);

        List<Incident> incidents = createMockIncidents();
        grid.setItems(incidents);

        FlexBoxLayout card = new FlexBoxLayout(grid);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setBoxSizing(BoxSizing.BORDER_BOX);
        card.setPadding(Uniform.M);
        card.setShadow(Shadow.XS);
        return card;
    }

    private List<Incident> createMockIncidents() {
        List<Incident> incidents = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        incidents.add(new Incident(
                "INC-001",
                LocalDateTime.now().minusDays(1).format(formatter),
                LocalDateTime.now().minusDays(1).plusHours(2).format(formatter),
                "Payment Gateway",
                "Payment processor connection failure",
                "Resolved"
        ));
        
        incidents.add(new Incident(
                "INC-002",
                LocalDateTime.now().minusDays(2).format(formatter),
                LocalDateTime.now().minusDays(2).plusHours(1).format(formatter),
                "Authentication Service",
                "Increased authentication failures due to misconfiguration",
                "Resolved"
        ));
        
        incidents.add(new Incident(
                "INC-003",
                LocalDateTime.now().minusDays(3).format(formatter),
                LocalDateTime.now().minusDays(3).plusHours(4).format(formatter),
                "Database Cluster",
                "Database replication lag causing data inconsistency",
                "Resolved"
        ));
        
        incidents.add(new Incident(
                "INC-004",
                LocalDateTime.now().minusDays(5).format(formatter),
                LocalDateTime.now().minusDays(5).plusHours(1).format(formatter),
                "API Gateway",
                "Rate limiting misconfiguration causing API throttling",
                "Resolved"
        ));
        
        incidents.add(new Incident(
                "INC-005",
                LocalDateTime.now().minusHours(1).format(formatter),
                "",
                "Storage System",
                "Disk space critical on backup server",
                "In Progress"
        ));
        
        return incidents;
    }

    // Data classes for the grids
    public static class Alert {
        private String timestamp;
        private String severity;
        private String system;
        private String message;

        public Alert(String timestamp, String severity, String system, String message) {
            this.timestamp = timestamp;
            this.severity = severity;
            this.system = system;
            this.message = message;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getSeverity() {
            return severity;
        }

        public String getSystem() {
            return system;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class Incident {
        private String id;
        private String startTime;
        private String endTime;
        private String system;
        private String description;
        private String status;

        public Incident(String id, String startTime, String endTime, String system, String description, String status) {
            this.id = id;
            this.startTime = startTime;
            this.endTime = endTime;
            this.system = system;
            this.description = description;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getSystem() {
            return system;
        }

        public String getDescription() {
            return description;
        }

        public String getStatus() {
            return status;
        }
    }
}