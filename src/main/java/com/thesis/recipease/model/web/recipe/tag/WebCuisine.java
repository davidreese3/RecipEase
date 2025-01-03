package com.thesis.recipease.model.web.recipe.tag;

public class WebCuisine {
    private String cuisine;

    public WebCuisine() {}
    public WebCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
}
