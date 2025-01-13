package com.thesis.recipease.model.web.recipe;

public class WebRating {
    private int recipeId;
    private int ratingValue;

    public WebRating(){}
    public WebRating(int recipeId, int ratingValue) {
        this.recipeId = recipeId;
        this.ratingValue = ratingValue;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }
}
