package com.thesis.recipease.model.web.recipe.tag;

public class WebAllergen {
    private String allergen;

    public WebAllergen() {}
    public WebAllergen(String allergen) {
        this.allergen = allergen;
    }

    public String getAllergen() {
        return allergen;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }
}
