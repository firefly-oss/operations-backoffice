package com.vaadin.starter.business.backend.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object for Client.
 */
public class ClientDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Double balance;
    private LocalDate registered;
    private String logoPath;

    /**
     * Default constructor.
     */
    public ClientDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id         the client ID
     * @param name       the client name
     * @param email      the client email
     * @param phone      the client phone
     * @param address    the client address
     * @param balance    the client balance
     * @param registered the registration date
     * @param logoPath   the logo path
     */
    public ClientDTO(Long id, String name, String email, String phone, String address,
                     Double balance, LocalDate registered, String logoPath) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.registered = registered;
        this.logoPath = logoPath;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}