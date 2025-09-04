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

public class Item {

	private Category category;
	private String name;
	private String desc;
	private double price;
	private String vendor;
	private int stock;
	private int sold;

	public enum Category {
		HEALTHCARE("Healthcare"), DENTAL("Dental"), CONSTRUCTION(
				"Construction");

		private String name;

		Category(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public Item(Category category, String name, String desc, String vendor,
	            double price, int stock, int sold) {
		this.category = category;
		this.name = name;
		this.desc = desc;
		this.price = price;
		this.vendor = vendor;
		this.stock = stock;
		this.sold = sold;
	}

	public Category getCategory() {
		return category;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public double getPrice() {
		return price;
	}

	public String getVendor() {
		return vendor;
	}

	public int getStock() {
		return stock;
	}

	public int getSold() {
		return sold;
	}

}
