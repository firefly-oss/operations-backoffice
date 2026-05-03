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

import java.time.LocalDate;

public class BankAccount {

	private Long id;
	private String bank;
	private String account;
	private String owner;
	private Double availability;
	private LocalDate updated;
	private String path;

	public BankAccount(Long id, String bank, String account, String company,
	                   Double availability, LocalDate updated, String path) {
		this.id = id;
		this.bank = bank;
		this.account = account;
		this.owner = company;
		this.availability = availability;
		this.updated = updated;
		this.path = path;
	}

	public Long getId() {
		return id;
	}

	public String getBank() {
		return bank;
	}

	public String getAccount() {
		return account;
	}

	public String getOwner() {
		return owner;
	}

	public Double getAvailability() {
		return availability;
	}

	public LocalDate getUpdated() {
		return updated;
	}

	public String getLogoPath() {
	    return path;
	}
}
