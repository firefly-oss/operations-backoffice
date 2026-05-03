/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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