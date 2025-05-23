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