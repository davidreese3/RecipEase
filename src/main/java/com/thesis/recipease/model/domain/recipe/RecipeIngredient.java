package com.thesis.recipease.model.domain.recipe;

public class RecipeIngredient {
    private int recipeId;
    private String component;
    private String quantity;
    private String measurement;
    private String preparation;

    public RecipeIngredient(){}

    public RecipeIngredient(int recipeId, String component, String quantity, String measurement, String preparation) {
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
