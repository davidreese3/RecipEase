package com.thesis.recipease.model;

public class WebRecipe {
    private String recipeName;
    private String description;
    private double servings;
    private String unitOfServings;
    private int prepMin;
    private int prepHr;
    private int cookMin;
    private int cookHr;
    private int totalMin;
    private int totalHr;

    public WebRecipe(){}
    public WebRecipe(String recipeName, String description, double servings, String unitOfServings, int prepMin, int prepHr, int cookMin, int cookHr, int totalMin, int totalHr) {
        this.recipeName = recipeName;
        this.description = description;
        this.servings = servings;
        this.unitOfServings = unitOfServings;
        this.prepMin = prepMin;
        this.prepHr = prepHr;
        this.cookMin = cookMin;
        this.cookHr = cookHr;
        this.totalMin = totalMin;
        this.totalHr = totalHr;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getServings() {
        return servings;
    }

    public void setServings(double servings) {
        this.servings = servings;
    }

    public String getUnitOfServings() {
        return unitOfServings;
    }

    public void setUnitOfServings(String unitOfServings) {
        this.unitOfServings = unitOfServings;
    }

    public int getPrepMin() {
        return prepMin;
    }

    public void setPrepMin(int prepMin) {
        this.prepMin = prepMin;
    }

    public int getPrepHr() {
        return prepHr;
    }

    public void setPrepHr(int prepHr) {
        this.prepHr = prepHr;
    }

    public int getCookMin() {
        return cookMin;
    }

    public void setCookMin(int cookMin) {
        this.cookMin = cookMin;
    }

    public int getCookHr() {
        return cookHr;
    }

    public void setCookHr(int cookHr) {
        this.cookHr = cookHr;
    }

    public int getTotalMin() {
        return totalMin;
    }

    public void setTotalMin(int totalMin) {
        this.totalMin = totalMin;
    }

    public int getTotalHr() {
        return totalHr;
    }

    public void setTotalHr(int totalHr) {
        this.totalHr = totalHr;
    }
}
