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


package com.vaadin.starter.business.ui.components.detailsdrawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;

@CssImport("./styles/components/details-drawer.css")
public class DetailsDrawer extends FlexBoxLayout {

	private String CLASS_NAME = "details-drawer";

	private FlexBoxLayout header;
	private FlexBoxLayout content;
	private FlexBoxLayout footer;

	public enum Position {
		BOTTOM, RIGHT
	}

	public DetailsDrawer(Position position, Component... components) {
		setClassName(CLASS_NAME);
		setPosition(position);

		header = new FlexBoxLayout();
		header.setClassName(CLASS_NAME + "__header");

		content = new FlexBoxLayout(components);
		content.setClassName(CLASS_NAME + "__content");
		content.setFlexDirection(FlexDirection.COLUMN);

		footer = new FlexBoxLayout();
		footer.setClassName(CLASS_NAME + "__footer");

		add(header, content, footer);
	}

	public void setHeader(Component... components) {
		this.header.removeAll();
		this.header.add(components);
	}

	public FlexBoxLayout getHeader() {
		return this.header;
	}

	public void setContent(Component... components) {
		this.content.removeAll();
		this.content.add(components);
	}

	public void setFooter(Component... components) {
		this.footer.removeAll();
		this.footer.add(components);
	}

	public void setPosition(Position position) {
		getElement().setAttribute("position", position.name().toLowerCase());
	}

	public void hide() {
		getElement().setAttribute("open", false);
	}

	public void show() {
		getElement().setAttribute("open", true);
	}
}
