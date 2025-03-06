package com.thesis.recipease.model.domain.recipe.util;

public class RecipeRating {
    private int recipeId;
    private int ratingUserId;
    private int ratingValue;

    public RecipeRating(){}
    public RecipeRating(int recipeId, int ratingUserId, int ratingValue){
        this.recipeId = recipeId;
        this.ratingUserId = ratingUserId;
        this.ratingValue = ratingValue;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRatingUserId() {
        return ratingUserId;
    }

    public void setRatingUserId(int ratingUserId) {
        this.ratingUserId = ratingUserId;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }
}
