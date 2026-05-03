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

public class Client {

	private Long id;
	private String name;
	private String email;
	private String phone;
	private String address;
	private Double balance;
	private LocalDate registered;
	private String path;

	public Client(Long id, String name, String email, String phone, String address,
	              Double balance, LocalDate registered, String path) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.balance = balance;
		this.registered = registered;
		this.path = path;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public Double getBalance() {
		return balance;
	}

	public LocalDate getRegistered() {
		return registered;
	}

	public String getLogoPath() {
	    return path;
	}
}