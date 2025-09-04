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


package com.vaadin.starter.business.ui.util;

public enum FontWeight {

	LIGHTER("lighter"),
	NORMAL("normal"),
	BOLD("bold"),
	BOLDER("bolder"),
	_100("100"),
	_200("200"),
	_300("300"),
	_400("400"),
	_500("500"),
	_600("600"),
	_700("700"),
	_800("800"),
	_900("900");

	private String value;

	FontWeight(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

