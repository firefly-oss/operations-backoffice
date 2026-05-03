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

public class Person {

	public enum Role {
		DESIGNER, DEVELOPER, MANAGER, TRADER, PAYMENT_HANDLER, ACCOUNTANT
	}

	private Long id;
	private String firstName;
	private String lastName;
	private Role role;
	private String email;
	private boolean randomBoolean;
	private int randomInteger;
	private LocalDate lastModified;

	public Person(Long id, String firstName, String lastName, Role role,
	              String email, boolean randomBoolean, int randomInteger,
	              LocalDate lastModified) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.email = email;
		this.randomBoolean = randomBoolean;
		this.randomInteger = randomInteger;
		this.lastModified = lastModified;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		return firstName + " " + lastName;
	}

	public String getInitials() {
		return (firstName.substring(0, 1) + lastName.substring(0, 1))
				.toUpperCase();
	}

	public Role getRole() {
		return role;
	}

	public String getEmail() {
		return email;
	}

	public boolean getRandomBoolean() {
		return randomBoolean;
	}

	public int getRandomInteger() {
		return randomInteger;
	}

	public LocalDate getLastModified() {
		return lastModified;
	}

}
