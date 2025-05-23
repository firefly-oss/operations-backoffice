package com.vaadin.starter.business.backend.dto.transactions;

import java.util.List;

/**
 * Data Transfer Object for payment status summary.
 */
public class PaymentStatusSummaryDTO {
    private List<PaymentStatusDTO> statuses;

    public PaymentStatusSummaryDTO() {
    }

    public PaymentStatusSummaryDTO(List<PaymentStatusDTO> statuses) {
        this.statuses = statuses;
    }

    public List<PaymentStatusDTO> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<PaymentStatusDTO> statuses) {
        this.statuses = statuses;
    }

    /**
     * Data Transfer Object for payment status.
     */
    public static class PaymentStatusDTO {
        private String status;
        private int count;
        private String color;

        public PaymentStatusDTO() {
        }

        public PaymentStatusDTO(String status, int count, String color) {
            this.status = status;
            this.count = count;
            this.color = color;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}