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

public class Report {

	private Long id;
	private String source;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private Double balance;

	public Report(Long id, String source, String name, LocalDate startDate,
	              LocalDate endDate, Double balance) {
		this.id = id;
		this.source = source;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public String getSource() {
		return source;
	}

	public String getName() {
		return name;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Double getBalance() {
		return balance;
	}

}
