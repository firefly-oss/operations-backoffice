package com.vaadin.starter.business.backend.dto.transactions;

import java.util.List;

/**
 * Data Transfer Object for payment methods data.
 */
public class PaymentMethodsDTO {
    private List<PaymentMethodData> methodData;

    public PaymentMethodsDTO() {
    }

    public PaymentMethodsDTO(List<PaymentMethodData> methodData) {
        this.methodData = methodData;
    }

    public List<PaymentMethodData> getMethodData() {
        return methodData;
    }

    public void setMethodData(List<PaymentMethodData> methodData) {
        this.methodData = methodData;
    }

    /**
     * Data Transfer Object for payment method data.
     */
    public static class PaymentMethodData {
        private String name;
        private double value;

        public PaymentMethodData() {
        }

        public PaymentMethodData(String name, double value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}