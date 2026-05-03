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


package com.vaadin.starter.business.backend.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object for BankAccount.
 */
public class BankAccountDTO {

    private Long id;
    private String bank;
    private String account;
    private String owner;
    private Double availability;
    private LocalDate updated;
    private String logoPath;

    /**
     * Default constructor.
     */
    public BankAccountDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id           the bank account ID
     * @param bank         the bank name
     * @param account      the account number
     * @param owner        the account owner
     * @param availability the account availability
     * @param updated      the last update date
     * @param logoPath     the logo path
     */
    public BankAccountDTO(Long id, String bank, String account, String owner,
                        Double availability, LocalDate updated, String logoPath) {
        this.id = id;
        this.bank = bank;
        this.account = account;
        this.owner = owner;
        this.availability = availability;
        this.updated = updated;
        this.logoPath = logoPath;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Double getAvailability() {
        return availability;
    }

    public void setAvailability(Double availability) {
        this.availability = availability;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}