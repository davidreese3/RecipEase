package com.thesis.recipease.model.recipe;

public class RecipeIngredient {
    private int recipeId;
    private String component;
    private Integer wholeNumberQuantity;
    private String fractionQuantity;
    private String measurement;
    private String preparation;

    public RecipeIngredient(){}
    public RecipeIngredient(int recipeId, String component, Integer wholeNumberQuantity, String fractionQuantity, String measurement, String preparation) {
        this.recipeId = recipeId;
        this.component = component;
        this.wholeNumberQuantity = wholeNumberQuantity;
        this.fractionQuantity = fractionQuantity;
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

    public Integer getWholeNumberQuantity() {
        return wholeNumberQuantity;
    }

    public void setWholeNumberQuantity(Integer wholeNumberQuantity) {
        this.wholeNumberQuantity = wholeNumberQuantity;
    }

    public String getFractionQuantity() {
        return fractionQuantity;
    }

    public void setFractionQuantity(String fractionQuantity) {
        this.fractionQuantity = fractionQuantity;
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
                "recipeId=" + recipeId +
                ", component='" + component + '\'' +
                ", wholeNumberQuantity=" + wholeNumberQuantity +
                ", fractionQuantity='" + fractionQuantity + '\'' +
                ", measurement='" + measurement + '\'' +
                ", preparation='" + preparation + '\'' +
                '}';
    }
}
