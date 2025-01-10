package com.thesis.recipease.model.domain.recipe;

public class RecipeUserSubstitutionEntry {
    private int recipeId;
    private String originalComponent;
    private Integer originalWholeNumberQuantity;
    private String originalFractionQuantity;
    private String originalMeasurement;
    private String originalPreparation;
    private String substitutedComponent;
    private Integer substitutedWholeNumberQuantity;
    private String substitutedFractionQuantity;
    private String substitutedMeasurement;
    private String substitutedPreparation;

    public RecipeUserSubstitutionEntry(){}

    public RecipeUserSubstitutionEntry(int recipeId, String originalComponent, Integer originalWholeNumberQuantity, String originalFractionQuantity, String originalMeasurement, String originalPreparation, String substitutedComponent, Integer substitutedWholeNumberQuantity, String substitutedFractionQuantity, String substitutedMeasurement, String substitutedPreparation) {
        this.recipeId = recipeId;
        this.originalComponent = originalComponent;
        this.originalWholeNumberQuantity = originalWholeNumberQuantity;
        this.originalFractionQuantity = originalFractionQuantity;
        this.originalMeasurement = originalMeasurement;
        this.originalPreparation = originalPreparation;
        this.substitutedComponent = substitutedComponent;
        this.substitutedWholeNumberQuantity = substitutedWholeNumberQuantity;
        this.substitutedFractionQuantity = substitutedFractionQuantity;
        this.substitutedMeasurement = substitutedMeasurement;
        this.substitutedPreparation = substitutedPreparation;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getOriginalComponent() {
        return originalComponent;
    }

    public void setOriginalComponent(String originalComponent) {
        this.originalComponent = originalComponent;
    }

    public Integer getOriginalWholeNumberQuantity() {
        return originalWholeNumberQuantity;
    }

    public void setOriginalWholeNumberQuantity(Integer originalWholeNumberQuantity) {
        this.originalWholeNumberQuantity = originalWholeNumberQuantity;
    }

    public String getOriginalFractionQuantity() {
        return originalFractionQuantity;
    }

    public void setOriginalFractionQuantity(String originalFractionQuantity) {
        this.originalFractionQuantity = originalFractionQuantity;
    }

    public String getOriginalMeasurement() {
        return originalMeasurement;
    }

    public void setOriginalMeasurement(String originalMeasurement) {
        this.originalMeasurement = originalMeasurement;
    }

    public String getOriginalPreparation() {
        return originalPreparation;
    }

    public void setOriginalPreparation(String originalPreparation) {
        this.originalPreparation = originalPreparation;
    }

    public String getSubstitutedComponent() {
        return substitutedComponent;
    }

    public void setSubstitutedComponent(String substitutedComponent) {
        this.substitutedComponent = substitutedComponent;
    }

    public Integer getSubstitutedWholeNumberQuantity() {
        return substitutedWholeNumberQuantity;
    }

    public void setSubstitutedWholeNumberQuantity(Integer substitutedWholeNumberQuantity) {
        this.substitutedWholeNumberQuantity = substitutedWholeNumberQuantity;
    }

    public String getSubstitutedFractionQuantity() {
        return substitutedFractionQuantity;
    }

    public void setSubstitutedFractionQuantity(String substitutedFractionQuantity) {
        this.substitutedFractionQuantity = substitutedFractionQuantity;
    }

    public String getSubstitutedMeasurement() {
        return substitutedMeasurement;
    }

    public void setSubstitutedMeasurement(String substitutedMeasurement) {
        this.substitutedMeasurement = substitutedMeasurement;
    }

    public String getSubstitutedPreparation() {
        return substitutedPreparation;
    }

    public void setSubstitutedPreparation(String substitutedPreparation) {
        this.substitutedPreparation = substitutedPreparation;
    }

    @Override
    public String toString() {
        return "RecipeUserSubstitutionEntry{" +
                "recipeId=" + recipeId +
                ", originalComponent='" + originalComponent + '\'' +
                ", originalWholeNumberQuantity=" + originalWholeNumberQuantity +
                ", originalFractionQuantity='" + originalFractionQuantity + '\'' +
                ", originalMeasurement='" + originalMeasurement + '\'' +
                ", originalPreparation='" + originalPreparation + '\'' +
                ", substitutedComponent='" + substitutedComponent + '\'' +
                ", substitutedWholeNumberQuantity=" + substitutedWholeNumberQuantity +
                ", substitutedFractionQuantity='" + substitutedFractionQuantity + '\'' +
                ", substitutedMeasurement='" + substitutedMeasurement + '\'' +
                ", substitutedPreparation='" + substitutedPreparation + '\'' +
                '}';
    }
}
