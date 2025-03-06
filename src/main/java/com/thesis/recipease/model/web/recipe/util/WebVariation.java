package com.thesis.recipease.model.web.recipe.util;

public class WebVariation {
    private int originalrecipeid;
    private boolean isVariation;

    public WebVariation(){}

    public WebVariation(int originalrecipeid, boolean isVariation) {
        this.originalrecipeid = originalrecipeid;
        this.isVariation = isVariation;
    }

    public int getOriginalrecipeid() {
        return originalrecipeid;
    }

    public void setOriginalrecipeid(int originalrecipeid) {
        this.originalrecipeid = originalrecipeid;
    }

    public boolean isVariation() {
        return isVariation;
    }

    public void setVariation(boolean variation) {
        isVariation = variation;
    }
}
