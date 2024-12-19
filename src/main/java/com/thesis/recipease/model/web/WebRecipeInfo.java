package com.thesis.recipease.model.web;

import java.util.List;

public class WebRecipeInfo {
    private String name;
    private String description;
    private Double yield;
    private String unitOfYield;
    private Integer prepMin;
    private Integer prepHr;
    private Integer processMin;
    private Integer processHr;
    private List<WebIngredient> ingredients;


    public WebRecipeInfo(){}
    public WebRecipeInfo(String name, String description, Double yield, String unitOfYield, Integer prepMin, Integer prepHr, Integer processMin, Integer processHr, List<WebIngredient> ingredients) {
        this.name = name;
        this.description = description;
        this.yield = yield;
        this.unitOfYield = unitOfYield;
        this.prepMin = prepMin;
        this.prepHr = prepHr;
        this.processMin = processMin;
        this.processHr = processHr;
        this.ingredients = ingredients;
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

    public Double getYield() {
        return yield;
    }

    public void setYield(Double yield) {
        this.yield = yield;
    }

    public String getUnitOfYield() {
        return unitOfYield;
    }

    public void setUnitOfYield(String unitOfYield) {
        this.unitOfYield = unitOfYield;
    }

    public Integer getPrepMin() {
        return prepMin;
    }

    public void setPrepMin(Integer prepMin) {
        this.prepMin = prepMin;
    }

    public Integer getPrepHr() {
        return prepHr;
    }

    public void setPrepHr(Integer prepHr) {
        this.prepHr = prepHr;
    }

    public Integer getProcessMin() {
        return processMin;
    }

    public void setProcessMin(Integer processMin) {
        this.processMin = processMin;
    }


    public Integer getProcessHr() {
        return processHr;
    }

    public void setProcessHr(Integer processHr) {
        this.processHr = processHr;
    }

    public List<WebIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<WebIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "WebRecipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", yield=" + yield +
                ", unitOfYield='" + unitOfYield + '\'' +
                ", prepMin=" + prepMin +
                ", prepHr=" + prepHr +
                ", processMin=" + processMin +
                ", processHr=" + processHr +
                '}';
    }
}
