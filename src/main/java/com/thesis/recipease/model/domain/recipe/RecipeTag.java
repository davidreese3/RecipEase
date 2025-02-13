package com.thesis.recipease.model.domain.recipe;

public class RecipeTag {
    private int recipeId;
    private String field;
    private String value;

    public RecipeTag(){}

    public RecipeTag(int recipeId, String field, String value) {
        this.recipeId = recipeId;
        this.field = field;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

