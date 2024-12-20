package com.thesis.recipease.model.web.recipe;

public class WebIngredient {
    private String component;
    private Double quantity;
    private String measurement;
    private String preparation;

    public WebIngredient(){}
    public WebIngredient(String component, Double quantity, String measurement, String preparation) {
        this.component = component;
        this.quantity = quantity;
        this.measurement = measurement;
        this.preparation = preparation;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    @Override
    public String toString() {
        return "WebIngredient{" +
                "component='" + component + '\'' +
                ", quantity=" + quantity +
                ", measurement='" + measurement + '\'' +
                ", preparation='" + preparation + '\'' +
                '}';
    }

}
