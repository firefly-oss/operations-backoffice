package com.vaadin.starter.business.backend.dto.taskmanagement;

/**
 * Data Transfer Object for Performance Factor.
 */
public class PerformanceFactorDTO {

    private String factor;
    private String impact;
    private String trend;
    private String recommendation;

    /**
     * Default constructor.
     */
    public PerformanceFactorDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param factor         the performance factor
     * @param impact         the impact level
     * @param trend          the trend direction
     * @param recommendation the recommendation
     */
    public PerformanceFactorDTO(String factor, String impact, String trend, String recommendation) {
        this.factor = factor;
        this.impact = impact;
        this.trend = trend;
        this.recommendation = recommendation;
    }

    // Getters and setters
    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}