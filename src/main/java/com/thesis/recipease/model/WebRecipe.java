package com.thesis.recipease.model;

public class WebRecipe {
    private String name;
    private String description;
    private double servings;
    private String unitOfServings;
    private int prepMin;
    private int prepHr;
    private int processMin;
    private int processHr;

    public WebRecipe(){}
    public WebRecipe(String name, String description, double servings, String unitOfServings, int prepMin, int prepHr, int processMin, int processHr) {
        this.name = name;
        this.description = description;
        this.servings = servings;
        this.unitOfServings = unitOfServings;
        this.prepMin = prepMin;
        this.prepHr = prepHr;
        this.processMin = processMin;
        this.processHr = processHr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getProcessMin() {
        return processMin;
    }

    public void setProcessMin(int processMin) {
        this.processMin = processMin;
    }

    public int getProcessHr() {
        return processHr;
    }

    public void getProcessHr(int processHr) {
        this.processHr = processHr;
    }

    @Override
    public String toString() {
        return "WebRecipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", servings=" + servings +
                ", unitOfServings='" + unitOfServings + '\'' +
                ", prepMin=" + prepMin +
                ", prepHr=" + prepHr +
                ", processMin=" + processMin +
                ", processHr=" + processHr +
                '}';
    }
}
