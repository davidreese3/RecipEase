package com.thesis.recipease.model.web.recipe.component;

public class WebIngredient {
    private String component;
    private Integer wholeNumberQuantity;
    private String fractionQuantity;
    private String measurement;
    private String preparation;

    public WebIngredient(){}

    public WebIngredient(String component, int wholeNumberQuantity, String fractionQuantity, String measurement, String preparation) {
        this.component = component;
        this.wholeNumberQuantity = wholeNumberQuantity;
        this.fractionQuantity = fractionQuantity;
        this.measurement = measurement;
        this.preparation = preparation;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Integer getWholeNumberQuantity() {
        return wholeNumberQuantity;
    }

    public void setWholeNumberQuantity(Integer wholeNumberQuantity) {
        this.wholeNumberQuantity = wholeNumberQuantity;
    }

    public String getFractionQuantity() {
        return fractionQuantity;
    }

    public void setFractionQuantity(String fractionQuantity) {
        this.fractionQuantity = fractionQuantity;
    }


    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    @Override
    public String toString() {
        return "WebIngredient{" +
                "component='" + component + '\'' +
                ", wholeNumberQuantity=" + wholeNumberQuantity +
                ", fractionQuantity='" + fractionQuantity + '\'' +
                ", measurement='" + measurement + '\'' +
                ", preparation='" + preparation + '\'' +
                '}';
    }
}
