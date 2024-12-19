package com.thesis.recipease.model.web;

public class WebIngredient {
    private String component;
    private Integer quantity;
    private String measurement;
    private String preparation;

    public WebIngredient(){}
    public WebIngredient(String component, Integer quantity, String measurement, String preparation) {
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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
