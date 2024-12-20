package com.thesis.recipease.model.recipe;

public class RecipeIngredient {
    private int recipeId;
    private String component;
    private Double quantity;
    private String measurement;
    private String preparation;

    public RecipeIngredient(){}
    public RecipeIngredient(int recipeId, String component, Double quantity, String measurement, String preparation) {
        this.recipeId = recipeId;
        this.component = component;
        this.quantity = quantity;
        this.measurement = measurement;
        this.preparation = preparation;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
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
        return "RecipeIngredient{" +
                "component='" + component + '\'' +
                ", quantity=" + quantity +
                ", measurement='" + measurement + '\'' +
                ", preparation='" + preparation + '\'' +
                '}';
    }
}
