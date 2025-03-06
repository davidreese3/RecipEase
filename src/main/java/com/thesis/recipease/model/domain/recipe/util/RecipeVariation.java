package com.thesis.recipease.model.domain.recipe.util;

public class RecipeVariation {
    private int originalRecipeId;
    private int variationRecipeId;

    public RecipeVariation(){}

    public RecipeVariation(int originalRecipeId, int variationRecipeId) {
        this.originalRecipeId = originalRecipeId;
        this.variationRecipeId = variationRecipeId;
    }

    public int getOriginalRecipeId() {
        return originalRecipeId;
    }

    public void setOriginalRecipeId(int originalRecipeId) {
        this.originalRecipeId = originalRecipeId;
    }

    public int getVariationRecipeId() {
        return variationRecipeId;
    }

    public void setVariationRecipeId(int variationRecipeId) {
        this.variationRecipeId = variationRecipeId;
    }
}
