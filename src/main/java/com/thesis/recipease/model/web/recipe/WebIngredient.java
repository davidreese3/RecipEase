package com.thesis.recipease.model.web.recipe;

public class WebIngredient {
    private String component;
    private String quantity;
    private String measurement;
    private String preparation;

    public WebIngredient(){}

    public WebIngredient(String component, String quantity, String measurement, String preparation) {
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
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
}
