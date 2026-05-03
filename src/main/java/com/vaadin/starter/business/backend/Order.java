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


package com.vaadin.starter.business.backend;

import com.vaadin.starter.business.ui.util.css.lumo.BadgeColor;

import java.time.LocalDate;
import java.util.Collection;

public class Order {

	private final Long id;
	private final Status status;
	private final Collection<Item> items;
	private final String customer;
	private final LocalDate date;
	private final Double value;

	public enum Status {
		PENDING("Pending", "Order received, payment pending.",
				BadgeColor.CONTRAST.getThemeName()), OPEN("Open",
				"Order received, not yet billed.",
				BadgeColor.NORMAL.getThemeName()), SENT("Sent",
				"Order shipped.",
				BadgeColor.SUCCESS.getThemeName()), FAILED(
				"Failed", "Payment unsuccessful",
				BadgeColor.ERROR.getThemeName());

		private String name;
		private String desc;
		private String theme;

		Status(String name, String desc, String theme) {
			this.name = name;
			this.desc = desc;
			this.theme = theme;
		}

		public String getName() {
			return name;
		}

		public String getDesc() {
			return desc;
		}

		public String getTheme() {
			return theme;
		}
	}

	public Order(Long id, Status status, Collection<Item> items,
	             String customer, LocalDate date) {
		this.id = id;
		this.status = status;
		this.items = items;
		this.customer = customer;
		this.date = date;
		this.value = items.stream().mapToDouble(Item::getPrice).sum();
	}

	public Long getId() {
		return id;
	}

	public Status getStatus() {
		return status;
	}

	public Collection<Item> getItems() {
		return items;
	}

	public int getItemCount() {
		return items.size();
	}

	public String getCustomer() {
		return customer;
	}

	public LocalDate getDate() {
		return date;
	}

	public Double getValue() {
		return this.value;
	}
}
