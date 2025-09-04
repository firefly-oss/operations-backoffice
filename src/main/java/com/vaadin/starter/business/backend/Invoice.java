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


package com.vaadin.starter.business.backend;

import java.time.LocalDate;

public class Invoice {

	private final Long id;
	private final Status status;
	private final Order order;
	private final LocalDate invoiceDate;
	private final LocalDate dueDate;

	public enum Status {
		OUTSTANDING("Outstanding"), OPEN("Open"), PAID("Paid"), OVERDUE(
				"Overdue");

		private String name;

		Status(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public Invoice(Long id, Status status, Order order, LocalDate invoiceDate,
	               LocalDate dueDate) {
		this.id = id;
		this.status = status;
		this.order = order;
		this.invoiceDate = invoiceDate;
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	public Status getStatus() {
		return status;
	}

	public Order getOrder() {
		return order;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}
}
