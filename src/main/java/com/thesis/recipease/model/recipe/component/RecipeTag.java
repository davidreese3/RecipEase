package com.thesis.recipease.model.recipe.component;

public class RecipeTag {
    private int recipeId;
    private String field;

    public RecipeTag(){}
    public RecipeTag(int recipeId, String field) {
        this.recipeId = recipeId;
        this.field = field;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}

