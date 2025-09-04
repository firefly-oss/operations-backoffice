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


package com.vaadin.starter.business.ui.components.navigation.drawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;

import java.util.List;
import java.util.stream.Collectors;

@CssImport("./styles/components/navi-menu.css")
public class NaviMenu extends Div {

	private String CLASS_NAME = "navi-menu";

	public NaviMenu() {
		setClassName(CLASS_NAME);
	}

	protected void addNaviItem(NaviItem item) {
		add(item);
	}

	protected void addNaviItem(NaviItem parent, NaviItem item) {
		parent.addSubItem(item);
		addNaviItem(item);
	}

	public void filter(String filter) {
		getNaviItems().forEach(naviItem -> {
			boolean matches = ((NaviItem) naviItem).getText().toLowerCase()
					.contains(filter.toLowerCase());
			naviItem.setVisible(matches);
		});
	}

	public NaviItem addNaviItem(String text,
	                            Class<? extends Component> navigationTarget) {
		NaviItem item = new NaviItem(text, navigationTarget);
		addNaviItem(item);
		return item;
	}

	public NaviItem addNaviItem(VaadinIcon icon, String text,
	                            Class<? extends Component> navigationTarget) {
		NaviItem item = new NaviItem(icon, text, navigationTarget);
		addNaviItem(item);
		return item;
	}

	public NaviItem addNaviItem(Image image, String text,
	                            Class<? extends Component> navigationTarget) {
		NaviItem item = new NaviItem(image, text, navigationTarget);
		addNaviItem(item);
		return item;
	}

	public NaviItem addNaviItem(NaviItem parent, String text,
	                            Class<? extends Component> navigationTarget) {
		NaviItem item = new NaviItem(text, navigationTarget);
		addNaviItem(parent, item);
		return item;
	}

	public List<NaviItem> getNaviItems() {
		List<NaviItem> items = (List) getChildren()
				.collect(Collectors.toList());
		return items;
	}

}
