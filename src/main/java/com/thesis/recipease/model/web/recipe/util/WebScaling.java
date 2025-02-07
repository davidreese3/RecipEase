package com.thesis.recipease.model.web.recipe.util;

public class WebScaling {
    private int recipeId;
    private double scalingValue;

    public WebScaling(){}
    public WebScaling(int recipeId, double scalingValue) {
        this.recipeId = recipeId;
        this.scalingValue = scalingValue;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public double getScalingValue() {
        return scalingValue;
    }

    public void setScalingValue(double scalingValue) {
        this.scalingValue = scalingValue;
    }
}
