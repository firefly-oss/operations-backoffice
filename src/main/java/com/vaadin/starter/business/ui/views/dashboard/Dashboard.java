package com.vaadin.starter.business.ui.views.dashboard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Uniform;
import com.vaadin.starter.business.ui.views.ViewFrame;

@PageTitle("Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
public class Dashboard extends ViewFrame {

    public Dashboard() {
        setId("dashboard");
        setViewContent(createContent());
    }

    private Component createContent() {
        Html welcome = new Html("<h1>Operations Dashboard</h1>");

        Html intro = new Html("<p>Welcome to the Operations Dashboard. This central hub provides real-time insights into your operational metrics and performance indicators.</p>");

        Html instructions = new Html("<p>Use the navigation menu to explore different dashboard sections including Operations Overview, Daily Performance Metrics, Service Level Indicators, and Operational Alerts.</p>");

        FlexBoxLayout content = new FlexBoxLayout(welcome, intro, instructions);
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO);
        content.setMaxWidth("840px");
        content.setPadding(Uniform.RESPONSIVE_L);
        return content;
    }
}