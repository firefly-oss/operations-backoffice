package com.vaadin.starter.business.backend.dto.transactions;

import java.util.List;

/**
 * Data Transfer Object for payment volume data.
 */
public class PaymentVolumeDTO {
    private List<String> categories;
    private List<SeriesData> series;

    public PaymentVolumeDTO() {
    }

    public PaymentVolumeDTO(List<String> categories, List<SeriesData> series) {
        this.categories = categories;
        this.series = series;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<SeriesData> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesData> series) {
        this.series = series;
    }

    /**
     * Data Transfer Object for series data.
     */
    public static class SeriesData {
        private String name;
        private List<Number> data;

        public SeriesData() {
        }

        public SeriesData(String name, List<Number> data) {
            this.name = name;
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Number> getData() {
            return data;
        }

        public void setData(List<Number> data) {
            this.data = data;
        }
    }
}