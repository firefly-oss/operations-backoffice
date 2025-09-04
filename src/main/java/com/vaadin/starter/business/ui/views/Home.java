/*
 * Copyright 2025 Firefly Software Solutions Inc
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


package com.vaadin.starter.business.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Uniform;

@PageTitle("Operations Backoffice")
@Route(value = "", layout = MainLayout.class)
public class Home extends ViewFrame {

	public Home() {
		setId("home");
		setViewContent(createContent());
	}

	private Component createContent() {
		Html welcome = new Html("<h1>Welcome to Firefly Operations Backoffice</h1>");

		Html intro = new Html("<p>Your comprehensive hub for monitoring, executing, and optimizing daily banking operations in real-time.</p>");

		Html instructions = new Html("<p>Use the navigation menu on the left to access different operational modules. Each section provides real-time data, controls, and workflows designed to ensure smooth and efficient banking operations.</p>");

		FlexBoxLayout content = new FlexBoxLayout(welcome, intro, instructions);
		content.setFlexDirection(FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO);
		content.setMaxWidth("840px");
		content.setPadding(Uniform.RESPONSIVE_L);
		return content;
	}

}
