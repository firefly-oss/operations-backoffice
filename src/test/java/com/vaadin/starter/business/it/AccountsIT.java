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


package com.vaadin.starter.business.it;

import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.grid.testbench.GridTHTDElement;
import com.vaadin.testbench.TestBenchElement;
import org.junit.Before;
import org.junit.Test;

public class AccountsIT extends AbstractIT {

    @Before
    public void init() {
        String url = getBaseURL().replace(super.getBaseURL(),
                super.getBaseURL() + "/accounts");
        getDriver().get(url);
    }

    @Test
    public void accountDetailsCorrect() {
        GridElement accountsGrid = $(GridElement.class).id("accounts");

        GridTHTDElement availability = accountsGrid.getCell(0, 3);
        String gridViewAvailability = availability.getText();

        accountsGrid.getCell(0, 0).click();

        TestBenchElement availabilityDetails = $(TestBenchElement.class)
                .id("availability").$("label").withAttributeContainingWord("class", "h2")
                .first();
        assertNumbers(gridViewAvailability, availabilityDetails.getText());
    }

}
